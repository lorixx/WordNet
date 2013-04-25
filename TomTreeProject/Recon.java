import java.util.Random;

public class Recon {
	// field
	private myImg Data;
	int[][] input; // read-able size
	int Dim, dim; // atom dimension
	private double[][] CoeffOM, CoeffTOM;
	int coeffCounter;
	private long time;
	//constructor

	public Recon(int[][] input, int Dim, String method){
		this.input = input;
		this.Dim = Dim;
		dim = (int)Math.sqrt(Dim);
		Data = new myImg(input, dim, method);
	}

	//methods
	public int[][] OMRecon(Dictionary dictionary, double Delta){
		int dicSize = dictionary.dicSize;
		final long startTime = System.currentTimeMillis();
		if(Data.img!=null){
			int numPatch = Data.img.length;
			CoeffOM = new double[numPatch][dicSize];

			for(int pp = 0; pp<numPatch; pp++){
				// f initialize
				double[] residual = Data.img[pp];

				double resnorm = norm(residual); 
				int count = 0;
				while( resnorm >Delta & count < dictionary.Dim)
				{
					//find next atom
					int index = 0; 
					double maxval = 0;
					double coeff = 0;
					for(int i = 0; i<dicSize; i++){
						//inner product of residual and atom candidate
						double currval = product(residual, dictionary.Dic.get(i));
						if (Math.abs(currval)>maxval){
							maxval = Math.abs(currval);
							index = i;
							coeff = currval;
						}
					}
					CoeffOM[pp][index] = coeff;
					residual = minus(residual, dot(dictionary.Dic.get(index), coeff));
					resnorm = norm(residual);
					count++;
				}	
			}
		}		

		Data.update(FormImg(dictionary, CoeffOM));
		final long endTime = System.currentTimeMillis();
		System.out.println("Total execution time of OM: " + (endTime-startTime));
		return Data.invTransform();
	}

	public int[][] OMReconMulti(Dictionary dictionary, double Delta){
		OMReconMulti inner = new OMReconMulti(dictionary, Delta, Data.img);
		inner.RunOM();
		time = inner.time();
		Data.update(FormImg(dictionary, inner.Result()));
		return Data.invTransform();
	}
	
	
	public int[][]TOMReconMulti(TOMTree TOM, double Delta){
		TOMReconMulti inner = new TOMReconMulti(TOM, Delta, Data.img);
		inner.RunTOM();
		Data.update(FormImg(TOM.dictionary, inner.Result()));
		time = inner.time();
		return Data.invTransform();
	}

	
	public int[][] TOMRecon(TOMTree TOM, double Delta){
		int dicSize = TOM.dictionary.dicSize;
		final long startTime1 = System.currentTimeMillis();

		//myimg = Data.img;
		if(Data.img!=null){
			int numPatch = Data.img.length;
			CoeffTOM = new double[numPatch][dicSize];
			for(int pp = 0; pp<numPatch; pp++){
				// f initialize
				double [] residual = Data.img[pp];
				double resnorm = norm(residual);
				int count = 0;
				while(resnorm > Delta & count < TOM.dictionary.Dim)
				{
					Node curr = TOM.root;
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
					CoeffTOM[pp][curr.key] = coeff;
					residual = minus(residual, dot(TOM.dictionary.Dic.get(curr.key), coeff));
					resnorm = norm(residual);
					count ++;
				}

			}
		}
		Data.update(FormImg(TOM.dictionary, CoeffTOM));
		final long endTime1 = System.currentTimeMillis();
		System.out.println("Total execution time of TOM: " + (endTime1-startTime1));
		return Data.invTransform();

	}

	public void addNoise(int scale){
		Random generator = new Random();

		for(int i = 0; i < input.length; i++)
			for(int j = 0; j < input[0].length; j++)
				input[i][j]+= generator.nextInt(scale) - scale/2;
	}

	public long Time(){
		return time;
	}
	
	
	private double[][] FormImg(Dictionary dictionary, double[][] CoeffM){
		double[][] newImg = new double[CoeffM.length][Dim];
		coeffCounter = 0;
		for(int i = 0; i<CoeffM.length; i++)
			for(int jj = 0; jj<dictionary.dicSize; jj++){
				if(CoeffM[i][jj]!=0.0){
					coeffCounter++;
					newImg[i] = add(newImg[i], dot(dictionary.Dic.get(jj), CoeffM[i][jj]));			
				}
			}
		return newImg;
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

	private double[] add(double[] d1, double[] d2){
		double[] result = new double[d1.length];		
		for(int i =0 ; i<d1.length; i++)
			result[i]= d1[i]+d2[i];
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

