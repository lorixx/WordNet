import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class TomFrame extends JFrame implements ActionListener, ChangeListener {

	private JMenuBar menuBar;
	private JMenu loadImageMenu;

	private JMenuItem loadImageButton;
	private JMenuItem exitButton;

	private JFileChooser chooser;

	private JSlider slider;

	private JLabel deltaValueLabel;

	private JRadioButton roughRadioButton;
	private JRadioButton fineRadioButton;

	private JLabel waitSpinner0, waitSpinner1;

	private JButton runButton;

	private JLabel originalImageLabel;
	private JLabel noisyImageLabel;
	private JLabel omImageLabel; // new picture using algorithm 1
	private JLabel tomImageLabel; // new picture using algorithm 2

	private JLabel resultnoisy;
	private JLabel resultOM;
	private JLabel resultTOM;
	
	//-------------- The following are Model data -----------------//

	static int Dim;
	static Dictionary dictionary;
	static TOMTree tomTree;
	static int MAX_SIZE = 128;

	double delta = 0.09;
	double threshold;
	int noiselevel = 30;
	JPanel originalpanel, noisedpanel, TOMpanel, OMpanel;
	private String method = "distinct"; // method: distinct/sliding
	private BufferedImage img;
	private static final long serialVersionUID = 1L;

	int[][] input, noised, TOMresult, OMresult;

	//    public static final String DICTIONARY_PATH = "_________";
    public static final String DICTIONARY_PATH = "C:\\repositories\\Algorithms\\TomTreeProject\\Dictionary.txt";
    //"/Users/Zhisheng/algs4/WordNet-intelliJ/TomTreeProject/Dictionary.txt";
    //"C:\\repositories\\Algorithms\\TomTreeProject\\Dictionary.txt";

    public static final String SPINNER_PATH = "C:\\repositories\\Algorithms\\TomTreeProject\\Dictionary.txt";
    //"/Users/Zhisheng/algs4/WordNet-intelliJ/TomTreeProject/spinner.gif";

	public TomFrame() {

		initComponents();

		initData();
	}


	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == loadImageButton) {
			chooser.showOpenDialog(null);
			File file = chooser.getSelectedFile();  // This is a block method
			try {
				img=ImageIO.read(file);
				ImageIcon icon=new ImageIcon(img);

				int imgWidth = icon.getIconWidth();
				int imgHeight = icon.getIconHeight();

				int wdratio = imgWidth/(MAX_SIZE+1) +1;
				int hdratio = imgHeight/(MAX_SIZE+1) +1;
				int imgX, imgY;

				if (imgWidth > MAX_SIZE)
					imgX = MAX_SIZE;
				else
					imgX = imgWidth;

				if (imgHeight > MAX_SIZE)
					imgY = MAX_SIZE;
				else
					imgY = imgHeight;
				System.out.println("original image size: X = " + imgWidth + ", Y = "+ imgHeight);

				input = new int[imgY][imgX];
				noised = new int[imgY][imgX];
				System.out.println("converted to X = " + imgX + ", Y = "+ imgY);

				int ii = 0, jj=0;
				for (int i = 0; i < imgWidth; i+=wdratio){
					jj =0;
					for(int j = 0; j < imgHeight; j+=hdratio){
						int rgb = img.getRGB(i, j); //always returns TYPE_INT_ARGB
						int red =   (rgb >> 16) & 0xFF;
						int green = (rgb >>  8) & 0xFF;
						int blue =  (rgb      ) & 0xFF;
						// take luminosity approach method
						// 0.21R + 0.71G + 0.07 B
						input[ii][jj] = (int) Math.floor(0.21*red+0.71*green+0.07*blue);	
						jj ++;
					}
					ii++;
				}

				Image originalImage = imageBuilder(input);
				originalImage = originalImage.getScaledInstance(200, 200, Image.SCALE_SMOOTH);

				// clean up old images
				originalImageLabel.setVisible(false);
				remove(originalImageLabel);
				originalImageLabel = new JLabel(new ImageIcon(originalImage));
				originalImageLabel.setBounds(5, 100, 200, 200);
				
				add(originalImageLabel);
				
				this.runButton.setEnabled(true);  //enable the button

			} catch (IOException e1) {

			} finally {
				//				waitSpinner.setVisible(false);
			}
		} else if (e.getSource() == exitButton) {
			System.exit(0);
		} else if (e.getSource() == roughRadioButton) {
			// set current radioButton state
			method = "distinct";
			System.out.println("Choose distinct patch reconstruction, fast but rough ...");
		} else if (e.getSource() == fineRadioButton) {
			method = "sliding";
			System.out.println("Choose sliding patch reconstruction, slow but looks better ...");
		}
		else if (e.getSource() == runButton) {
			
			Random generator = new Random(0);
			int imgY = input.length;
			int imgX = input[0].length;
			
			if(noiselevel!=0){
				noised = new int[imgY][imgX];
				for(int i = 0 ; i < imgX; i++)
					for(int j = 0; j < imgY; j++)
						noised[i][j] = input[i][j] + (int) (generator.nextInt(noiselevel) - noiselevel/2);
			}
			else
				noised = input;
			
			noisyImageLabel.setVisible(false);
			remove(noisyImageLabel);
			
			Image noiseImage = imageBuilder(noised);
			noiseImage = noiseImage.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
			
			noisyImageLabel = new JLabel(new ImageIcon(noiseImage));			
			noisyImageLabel.setBounds(250, 100, 200, 200);
			add(noisyImageLabel);
			
			remove(omImageLabel);
			remove(tomImageLabel);
		
			try {
				
				System.out.println("Start running the algorithms");
				runButton.setEnabled(false);
				System.out.printf("Before recon SNR: %.2f dB\n", imgEvaluator(input, noised));
				resultnoisy.setText(String.format("SNR: %.2f dB", imgEvaluator(input, noised)));
				
				Recon rt0 = new Recon(noised, Dim, method);
				int[][] OMresult = rt0.OMReconMulti(dictionary, dictionary.Dim *noiselevel*noiselevel*0.025);
				// int[][] OMresult = rt1.OMRecon(dictionary, dictionary.Dim * noiselevel * noiselevel * 0.025);
				double nOM = (double) rt0.coeffCounter/(OMresult.length * OMresult[0].length);
				double time0 =(double) rt0.Time()/1000.0;
				resultOM.setText(String.format("<html>OM recon SNR: %.2f dB <br> "
						+ "Execution time: %.2f s<br> # of coeff. : %.1f </html> "
						+ "", imgEvaluator(input, OMresult), time0, nOM));

				
				Recon rt1 = new Recon(noised, Dim, method);
				// int[][] TOMresult = rt1.TOMReconMulti(tomTree, dictionary.Dim *noiselevel*noiselevel*0.025);
				int[][] TOMresult = rt1.TOMReconMulti(tomTree, dictionary.Dim *noiselevel*noiselevel*0.025);
				double nTOM = (double) rt1.coeffCounter/(TOMresult.length * TOMresult[0].length);
				double time1 =(double) rt1.Time()/1000.0;
				resultTOM.setText(String.format("<html>TOM recon SNR: %.2f dB <br> "
						+ "Execution time: %.2f s<br> # of coeff. : %.1f </html> "
						+ "", imgEvaluator(input, TOMresult), time1, nTOM));
				

				Image omImage = imageBuilder(OMresult);
				Image tomImage = imageBuilder(TOMresult);
				omImage = omImage.getScaledInstance(200, 200, Image.SCALE_SMOOTH);   // scale
				tomImage = tomImage.getScaledInstance(200, 200, Image.SCALE_SMOOTH); // scale

				// clean up old images
				omImageLabel = new JLabel(new ImageIcon(omImage));
				tomImageLabel = new JLabel(new ImageIcon(tomImage));
				omImageLabel.setBounds(505, 100, 200, 200);
				tomImageLabel.setBounds(755, 100, 200, 200);
				add(omImageLabel);
				add(tomImageLabel);
			} catch (Exception ex) {
				System.out.println(ex);
			} finally {
				runButton.setEnabled(true);
			}

		}

	}



	protected double imgEvaluator(int[][] img1, int[][] img2) {
		// Method tha provides a measure of image quality 

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

	public void stateChanged(ChangeEvent e) {
		if (e.getSource() == slider) {
			deltaValueLabel.setText(Integer.toString(slider.getValue()));
			noiselevel = slider.getValue();    // update noise level
		}
	}

	private void initComponents() {

		this.setLayout(null);

		// set up menu bar
		this.menuBar = new JMenuBar();
		setJMenuBar(this.menuBar);
		this.loadImageMenu = new JMenu("File");
		this.loadImageButton = new JMenuItem("Load Image");
		this.exitButton = new JMenuItem("Exit");
		this.menuBar.add(loadImageMenu);
		loadImageMenu.add(loadImageButton);
		loadImageMenu.add(exitButton);
		loadImageButton.addActionListener(this);
		exitButton.addActionListener(this);

		// set label
		JLabel label = new JLabel("Noise level:");
		label.setBounds(15, 1, 300, 20);
		this.add(label);

		// set slider
		slider = new JSlider(15, 50);
		slider.setBounds(1, 30, 200, 20);
		slider.addChangeListener(this);
		this.add(slider);

		// set slider value label
		deltaValueLabel = new JLabel(Integer.toString(slider.getValue()));
		deltaValueLabel.setBounds(210, 30, 50, 20);
		this.add(deltaValueLabel);

		// create group button
		ButtonGroup group = new ButtonGroup();
		roughRadioButton = new JRadioButton("Rough");
		roughRadioButton.addActionListener(this);
		roughRadioButton.setSelected(true);

		fineRadioButton = new JRadioButton("Fine");
		fineRadioButton.addActionListener(this);
		group.add(roughRadioButton);
		group.add(fineRadioButton);

		roughRadioButton.setBounds(320, 5, 100, 20);
		fineRadioButton.setBounds(320, 26, 100, 20);
		add(roughRadioButton);
		add(fineRadioButton);

		// set up run button
		runButton = new JButton("Run");
		runButton.addActionListener(this);
		runButton.setBounds(450, 3, 100, 50);
		add(runButton);
		runButton.setEnabled(false); // Disable the button until user load an image

		// create wait spinner
		waitSpinner0 = new JLabel(new ImageIcon(SPINNER_PATH));
		waitSpinner1 = new JLabel(new ImageIcon(SPINNER_PATH));

		waitSpinner0.setBounds(530, 125, 100, 100);
		waitSpinner1.setBounds(780, 125, 100, 100);

		add(waitSpinner0); waitSpinner0.setVisible(false);
		add(waitSpinner1); waitSpinner1.setVisible(false);


		// create image labels
		JLabel label1 = new JLabel("Original Image:");
		JLabel label2 = new JLabel("Noisy Image:");
		JLabel label3 = new JLabel("Matching Pursuit:");
		JLabel label4 = new JLabel("Tree-based Matching Pursuit:");

		label1.setBounds(5, 80, 150, 20);
		label2.setBounds(255, 80, 150, 20);
		label3.setBounds(505, 80, 150, 20);
		label4.setBounds(755, 80, 200, 20);
		add(label1);add(label2);add(label3);add(label4);

		
		
		// create picture
		originalImageLabel = new JLabel();
		noisyImageLabel = new JLabel();
		omImageLabel = new JLabel();
		tomImageLabel = new JLabel();

		originalImageLabel.setBounds(5, 100, 200, 200);
		noisyImageLabel.setBounds(255, 100, 200, 200);
		omImageLabel.setBounds(505, 100, 200, 200);
		tomImageLabel.setBounds(755, 100, 200, 200);

		add(originalImageLabel);
		add(noisyImageLabel);
		add(omImageLabel);
		add(tomImageLabel);
		
		//crate result display labels
		resultnoisy = new JLabel();
		resultOM = new JLabel();
		resultTOM = new JLabel();
		
		resultnoisy.setBounds(255, 300, 200, 100);
		resultOM.setBounds(505, 300, 200, 100);
		resultTOM.setBounds(755, 300, 200, 100);

		add(resultnoisy);
		add(resultOM);
		add(resultTOM);
		
		chooser = new JFileChooser();

	}

	private void initData() {
		try {
			/*
			 * read the trained dictionary
			 * */
			dictionary = new Dictionary();
			//dictionary.readDic(this.DICTIONARY_PATH);
			dictionary.readDic(DICTIONARY_PATH);
			Dim = dictionary.Dim;
			/*
			 * build the "tree"-dictionary for comparison
			 * */
			tomTree = new TOMTree(delta, dictionary);

		} catch (Exception e) {
			System.out.println(e);
		}
	}


	private Image imageBuilder(int[][] img){
		int dimY = img.length;
		int dimX = img[0].length;

		BufferedImage bf = new BufferedImage(dimX, dimY, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = (Graphics2D)bf.getGraphics();
		for(int i = 0; i < dimY; i++ ){
			for(int j = 0; j < dimX; j++){
				int c = img[i][j];
				if(c > 255)
					c = 255;
				if(c < 0)
					c = 0;
				g.setColor(new Color(c, c, c));
				g.fillRect(i, j, 1, 1);
			}
		}
		return bf;

	}

	public static void main(String[] args) {
		TomFrame tf = new TomFrame();
		tf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Display the window
		tf.setSize(new Dimension(1000, 500));
		//tf.pack();
		tf.setVisible(true);

	}

}