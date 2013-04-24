public class myImg {

	double [][] img;
	String method;

	/**
	 * Each patch of size dim-by-dim
	 * 
	 */
	private int dim;
	private int [][] weight;
	private int X, Y;

	public myImg(int[][] image, int dim, String method ){
		X = image[0].length;
		Y = image.length;

		this.dim = dim;
		this.method = method;
		transform(image);
	}

	public void update(double[][] newimg){
		if(newimg.length == img.length)
			img = newimg;
		else{
			System.out.println("Internal image size mismatch!");
			System.exit(-1);
		}
	}
	

	public myImg(int[][] image, int dim){
		X = image[0].length;
		Y = image.length;

		this.dim = dim;
		method = "distinct";
		transform(image);
	}

	public int[][] invTransform(){
		int[][] newImage = new int[Y][X];

		if(!method.equals("distinct")){
			int nY = Y - dim + 1;
			int nX = X - dim + 1;

			int count = 0;
			for(int xx = 0; xx < nX; xx ++)
				for(int yy = 0; yy < nY; yy ++){
					int z = 0;

					for(int x = xx; x < xx+dim; x++)
						for(int y = yy; y < yy+dim; y++){
							newImage[y][x] += (int)img[count][z];
							z++;
						}
					count++;
				}

			for(int x = 0; x < X; x++)
				for(int y = 0; y< Y; y++)
					newImage[y][x] = (int) newImage[y][x]/weight[y][x];	
		}
		else{

			int count = 0;
			for(int xx = 0; xx < X; xx += dim)
				for(int yy = 0; yy < Y; yy +=dim){
					int z = 0;

					for(int x = xx; x < xx+dim; x++)
						for(int y = yy; y < yy+dim; y++){
							if(x < X & y < Y)
								newImage[y][x] = (int)img[count][z];
							z++;
						}
					count++;
				}
		}
		return newImage;
	}



	private void transform(int[][] image){
		weight = new int [Y][X];
		if(!method.equals("distinct")){
			int nY = Y - dim + 1;
			int nX = X - dim + 1;
			img = new double[nY*nX][dim*dim];

			int count = 0;
			for(int xx = 0; xx < nX; xx ++)
				for(int yy = 0; yy < nY; yy ++){
					int z = 0;

					for(int x = xx; x < xx+dim; x++)
						for(int y = yy; y < yy+dim; y++){
							img[count][z] = (double) image[y][x];
							weight[y][x] = weight[y][x]+1;
							z++;
						}
					count++;
				}
		}
		else{
			int nY = Y/dim + 1*(Y % dim);
			int nX = X/dim + 1*(X % dim);
			img = new double[nY*nX][dim*dim];

			int count = 0;
			for(int xx = 0; xx < X; xx += dim)
				for(int yy = 0; yy < Y; yy +=dim){
					int z = 0;

					for(int x = xx; x < xx+dim; x++)
						for(int y = yy; y < yy+dim; y++){
							if(x >= X | y >= Y)
								img[count][z] = 0;
							else
								img[count][z] = (double)image[y][x];
							z++;
						}
					count++;
				}

		}

	}


}
