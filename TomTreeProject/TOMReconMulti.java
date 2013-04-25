import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 *
 * @author Ke Chen
 */
public class TOMReconMulti{
	// FILEDS
	double[][] img;
	int dicSize;
	final double Delta;
	final TOMTree TOM;
	private double[][] CoeffM;
	long duration = 0;
	// METHODS
	/*
	 * constructor - perform tree-based matching pursuit reconstruction using multi-threads
	 * */
	public double[][] Result(){
		return CoeffM; // return coefficient matrix
	}

	public TOMReconMulti(TOMTree TOM, double Delta, double[][] img){
		this.TOM = TOM;
		this.Delta = Delta;
		this.img = img;
		dicSize = TOM.dictionary.dicSize;
		CoeffM = new double[img.length][dicSize];
	}


	public void RunTOM(){
		ExecutorService executor = Executors.newCachedThreadPool();
		final long startTime1 = System.currentTimeMillis();

		for(int i =0; i< img.length; i++ ){
			executor.execute(new MyRunnable(i, TOM));
		}
		executor.shutdown();
		while(!executor.isTerminated()){
		}
		final long endTime1 = System.currentTimeMillis();
		duration = endTime1 - startTime1;
		System.out.println("Total execution time of TOM: " + duration);
	}
	public long time(){
		return duration;
	}


	class MyRunnable implements Runnable{
		private final int pp;
		private TOMTree tommy;
		MyRunnable(int pp, TOMTree tommy){
			this.pp = pp;
			this.tommy = tommy;
		}
		public void run(){
			// f initialize
			double [] residual = img[pp];
			double resnorm = norm(residual);
			int count = 0;
			while(resnorm > Delta & count < tommy.dictionary.Dim)
			{
				Node curr = tommy.root;
				while(!curr.isLeaf){
					Node next = curr;
					double maxval = 0;
					for(Node s: curr.children()){
						double currval = product(s.value(), residual);
						if(Math.abs(currval)>maxval){
							maxval = Math.abs(currval);
							next = s;								
						}
					}
					curr = next;
				}
				double coeff = product(curr.value(), residual);
				CoeffM[pp][curr.key] = coeff;
				residual = minus(residual, dot(tommy.dictionary.Dic.get(curr.key), coeff));
				resnorm = norm(residual);
				count ++;

			}


		}

		private double product(double[] d1, double[] d2){
			double result = 0;		
			for(int i = 0 ; i < d1.length; i++)
				result += d1[i]*d2[i];
			return result;
		}

		private double[] minus(double[] d1, double[] d2){
			double[] result = new double[d1.length];		
			for(int i =0 ; i<d1.length; i++)
				result[i]= d1[i]-d2[i];
			return result;
		}

		private double[] dot(double[] d1, double a){
			double[] result = new double[d1.length];
			for(int i =0; i<d1.length; i++)
				result[i] = a*d1[i];
			return result;
		}

		private double norm(double[] d1){
			double result = 0;
			for(int i =0; i<d1.length; i++)
				result += d1[i]*d1[i];
			return result;
		}
	}


}


