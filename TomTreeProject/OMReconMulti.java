import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
*
* @author Ke Chen
*/
public class OMReconMulti{
	
	// FIELDS
	double[][] img;
	int dicSize;
	final double Delta;
	final Dictionary dictionary;
	private double[][] CoeffM;
	private long duration;

	
	// METHODS
	/*
	 * constructor - perform matching pursuit reconstruction using multi-threads
	 * */
	public double[][] Result(){
		return CoeffM; // return coefficient matrix
	}

	public OMReconMulti(Dictionary dictionary, double Delta, double[][] img){
		this.dictionary = dictionary;
		this.Delta = Delta;
		this.img = img;
		dicSize = dictionary.dicSize;
		CoeffM = new double[img.length][dicSize];
	}


	public void RunOM(){
		ExecutorService executor = Executors.newCachedThreadPool();
		final long startTime1 = System.currentTimeMillis();

		for(int i =0; i< img.length; i++ ){
			executor.execute(new MyRunnable(i, dictionary));
		}
		executor.shutdown();
		while(!executor.isTerminated()){
		}
		final long endTime1 = System.currentTimeMillis();
		duration = endTime1 - startTime1;
		System.out.println("Total execution time of OM: " + duration);
	}

	public long time(){
		return duration;
	}

	private class MyRunnable implements Runnable{
		private final int pp;
		private Dictionary dd;
		MyRunnable(int pp, Dictionary dd){
			this.pp = pp;
			this.dd = dd;
		}
		public void run(){
			// f initialize
			double[] residual = img[pp];

			double resnorm = norm(residual); 
			int count = 0;
			while( resnorm >Delta & count < dd.Dim)
			{
				//find next atom
				int index = 0; 
				double maxval = 0;
				double coeff = 0;
				for(int i = 0; i<dicSize; i++){
					//inner product of residual and atom candidate
					double currval = product(residual, dd.Dic.get(i));
					if (Math.abs(currval)>maxval){
						maxval = Math.abs(currval);
						index = i;
						coeff = currval;
					}
				}
				CoeffM[pp][index] = coeff;
				residual = minus(residual, dot(dd.Dic.get(index), coeff));
				resnorm = norm(residual);
				count++;
			
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


