import java.util.Random;
import javax.swing.*;

public class Recon {
	// field
	private myImg Data;
	int[][] input; // read-able size
	int Dim, dim; // atom dimension
	private double[][] CoeffOM, CoeffTOM;
	//constructor
	public Recon(int[][] input, int Dim){
		this.input = input;
		this.Dim = Dim;
		dim = (int)Math.sqrt(Dim);
		Data = new myImg(input, dim);
	}

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


	private double[][] FormImg(Dictionary dictionary, double[][] CoeffM){
		double[][] newImg = new double[CoeffM.length][Dim];

		for(int i = 0; i<CoeffM.length; i++)
			for(int jj = 0; jj<dictionary.dicSize; jj++){
				if(CoeffM[i][jj]!=0.0)
					newImg[i] = add(newImg[i], dot(dictionary.Dic.get(jj), CoeffM[i][jj]));
			}
		return newImg;
	}

	//	private void transform(){ 
	//		// organize image into vectors of size dim-by-dim
	//		// input-->myimg
	//
	//		int numC = (int) imgX/dim;
	//		int numR = (int) imgY/dim;
	//		double[][] imgT= new double[numC*numR][Dim];
	//		int count = 0;
	//
	//		for(int ii =0; ii<numR; ii++)
	//			for(int jj = 0; jj<numC; jj++){
	//				int z =0;
	//				int Y = ii*dim, X = jj*dim;
	//				for(int xx = X; xx<X+dim; xx++)
	//					for(int yy = Y; yy<Y+dim; yy++){
	//						imgT[count][z] =(double) input[yy][xx];
	//						z++;
	//					}
	//				count++;
	//			}
	//		myimg = imgT;
	//	}
	//
	//	private int[][] invtransform(double[][] newImg){
	//		// organize image into read-able image of size imgX-by-imgY
	//		//myimg-->output
	//		int[][] img = new int[imgY][imgX];
	//
	//		int numC = (int) imgX/dim;
	//		int numR = (int) imgY/dim;
	//
	//		int count = 0;
	//
	//		for(int ii =0; ii<numR; ii++)
	//			for(int jj = 0; jj<numC; jj++){
	//				int z =0;
	//				int Y = ii*dim, X = jj*dim;
	//				for(int xx = X; xx<X+dim; xx++)
	//					for(int yy = Y; yy<Y+dim; yy++){
	//						img[yy][xx]=(int)newImg[count][z];
	//						z++;
	//					}
	//				count++;
	//			}
	//		return img;
	//	}
	//
	//	private int[][] OMReconJF(final Dictionary dictionary, final double Delta) throws InterruptedException{
	//		int dicSize = dictionary.dicSize;
	//
	//		class OMbar extends JPanel{
	//			/**
	//			 * 
	//			 */
	//			private static final long serialVersionUID = 1L;
	//			JProgressBar pbar;
	//			public OMbar(){
	//				pbar = new JProgressBar();
	//				pbar.setMinimum(0);
	//				pbar.setMaximum(100);
	//				add(pbar);
	//			}
	//			public void updateBar(int newValue){
	//				pbar.setValue(newValue);
	//				pbar.repaint();
	//			}
	//		}
	//		
	//		final OMbar it = new OMbar();
	//		JFrame frame = new JFrame("OM processing...");
	//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	//		frame.setContentPane(it);
	//		frame.pack();
	//		frame.setVisible(true);
	//
	//		if(Data.img!=null){
	//			int numPatch = Data.img.length;
	//			CoeffOM = new double[numPatch][dicSize];
	//
	//			for(int pp = 0; pp<numPatch; pp++){
	//				int percent = (int) pp/numPatch *100;
	//				double[] residual = Data.img[pp];
	//
	//				// f initialize
	//						it.updateBar(percent);
	//
	//						double resnorm = norm(residual); 
	//						int count = 0;
	//						while( resnorm >Delta & count < dictionary.Dim)
	//						{
	//							//find next atom
	//							int index = 0; 
	//							double maxval = 0;
	//							double coeff = 0;
	//							for(int i = 0; i<dicSize; i++){
	//								//inner product of residual and atom candidate
	//								double currval = product(residual, dictionary.Dic.get(i));
	//								if (Math.abs(currval)>maxval){
	//									maxval = Math.abs(currval);
	//									index = i;
	//									coeff = currval;
	//								}
	//							}
	//							CoeffOM[pp][index] = coeff;
	//							residual = minus(residual, dot(dictionary.Dic.get(index), coeff));
	//							resnorm = norm(residual);
	//							count++;
	//						}
	//			}		
	//		}
	//		frame.setVisible(false);
	//
	//		Data.update(FormImg(dictionary, CoeffOM));
	//		return Data.invTransform();
	//		
	//
	//	}

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

