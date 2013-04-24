import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.Random;

import javax.imageio.*;
import javax.swing.*;

import java.lang.Math;

public class tom3 extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static int Dim;
	static Dictionary mydic;
	static TOMTree mytree;
	static double delta = 0.1;
	static int noiselevel=35;
	JPanel originalpanel, noisedpanel, TOMpanel, OMpanel;
	static String method = "distinct"; // method: distinct/sliding

	static int[][] input, noised, TOMresult, OMresult;

	public static void main(String args[]) throws Exception{
		mydic = new Dictionary();

		try{
			mydic.readDic("Dictionary.txt");
			Dim = mydic.Dim;
			mytree = new TOMTree(delta, mydic);
			System.out.println(mytree.number);
		} 					
		catch(IOException e1) {}

		tom3 mf = new tom3();
		mf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mf.setVisible(true);
	}

	public tom3(){

		final JFileChooser chooser;
		final JLabel label = new JLabel();

		JMenuBar menubar;
		JMenu fileMb;
		final JMenuItem openButton;
		final JMenuItem exitButton;

		menubar = new JMenuBar(); setJMenuBar(menubar);
		fileMb = new JMenu("File");
		openButton = new JMenuItem("Open"); 
		exitButton = new JMenuItem("Exit"); 

		menubar.add(fileMb); 
		fileMb.add(openButton); 
		fileMb.add(exitButton);

		chooser = new JFileChooser();

		ActionListener action=new ActionListener()
		{


			public void actionPerformed(ActionEvent e) {
				BufferedImage img;

				if (e.getSource()==openButton) {
					chooser.showOpenDialog(null);
					File file = chooser.getSelectedFile();

					try {
						img=ImageIO.read(file);
						ImageIcon icon=new ImageIcon(img);
						label.setIcon(icon);

						int imgWidth = icon.getIconWidth();
						int imgHeight = icon.getIconHeight();

						input = new int[imgWidth][imgHeight];
						noised = new int[imgWidth][imgHeight];
						Random generator = new Random(0);
						for (int i = 0; i < imgWidth; i++)
							for(int j = 0; j < imgHeight; j++){
								int rgb = img.getRGB(i, j); //always returns TYPE_INT_ARGB
								int red =   (rgb >> 16) & 0xFF;
								int green = (rgb >>  8) & 0xFF;
								int blue =  (rgb      ) & 0xFF;
								// take luminosity approach method
								// 0.21R + 0.71G + 0.07 B
								input[i][j] = (int) Math.floor(0.21*red+0.71*green+0.07*blue);	
								noised[i][j] = input[i][j] + (int) (generator.nextInt(noiselevel) - noiselevel/2);
							}

						originalpanel = panelBuilder(input);
						noisedpanel = panelBuilder(noised);

						/* (non-Javadoc)
						 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
						 */
						Recon rt1 = new Recon(noised, Dim, method);
						int[][] OMresult = rt1.OMRecon(mydic, mydic.Dim *noiselevel*noiselevel*0.025);		
						int[][] TOMresult = rt1.TOMRecon(mytree, mydic.Dim *noiselevel*noiselevel*0.025);							

						TOMpanel = panelBuilder(TOMresult);
						OMpanel = panelBuilder(OMresult);

						JPanel container = new JPanel();
						container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
						container.add(originalpanel);
						container.add(noisedpanel);
						container.add(OMpanel);
						container.add(TOMpanel);

						add(container);
						setSize(new Dimension(4*input.length, input[0].length+50));
						setVisible(true);


					}
					catch(IOException e1) {}
				}
				if(e.getSource() == exitButton){
					System.exit(0);
				}				
			}	

		};

		openButton.addActionListener(action);
		exitButton.addActionListener(action);
	}


	protected double imgEvaluator(int[][] img1, int[][] img2) {
		// TODO Auto-generated method stub
		int dimY = img1.length;
		int dimX = img1[0].length;
		double SNR = 0;

		int sum = 0;
		for(int i = 0; i < dimY; i++)
			for(int j = 0; j < dimX; j++)
				sum += Math.pow(img1[i][j] - img2[i][j], 2);

		SNR = (double) 20*Math.log10(255/Math.sqrt(sum/(dimX*dimY)));
		return SNR;
	}

	private JPanel panelBuilder(int[][] img){
		int dimY = img.length;
		int dimX = img[0].length;

		final BufferedImage bf = new BufferedImage(dimX, dimY, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D)bf.getGraphics();
		for(int i = 0; i < dimY; i++ )
			for(int j = 0; j < dimX; j++){
				int c = img[i][j];
				if(c > 255) 
					c = 255;				
				if(c < 0) 
					c = 0;
				g.setColor(new Color(c, c, c));
				g.fillRect(i, j, 1, 1);
			}

		JPanel newpanel = new JPanel(){
			protected void paintComponent(Graphics g){
				g.drawImage(bf, 0, 0, this);
			}
		};
		newpanel.setPreferredSize(new Dimension(dimX, dimY));
		return newpanel;

	}
}



