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


/*
 * Created by JFormDesigner on Wed Apr 24 17:11:52 EDT 2013
 */


/**
 * @author James Huang
 */
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

    private JLabel waitSpinner;

    private JButton runButton;

    private JLabel originalImageLabel;

    private JLabel noisyImageLabel;

    private JLabel omImageLabel; // new picture using algorithm 1

    private JLabel tomImageLabel; // new picture using algorithm 2


    //-------------- The following are Model data -----------------//

    private BufferedImage img;
    private static final long serialVersionUID = 1L;
    static int Dim;
    static Dictionary dictionary;
    static TOMTree tomTree;
    static double delta = 0.1;
    static int noiselevel = 35;
    static String method = "sliding"; // method: distinct/sliding

    static int[][] input, noised, TOMresult, OMresult;

    public static final String DICTIONARY_PATH = "/Users/Zhisheng/algs4/WordNet-intelliJ/TomTreeProject/Dictionary.txt";
    //"C:\\repositories\\Algorithms\\TomTreeProject\\Dictionary.txt";

    public static final String SPINNER_PATH = "/Users/Zhisheng/algs4/WordNet-intelliJ/TomTreeProject/spinner.gif";

    public TomFrame() {

        initComponents(); // init the UI components
        initData();       // init the model data
    }


    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loadImageButton) {
            chooser.showOpenDialog(null);
            File file = chooser.getSelectedFile();  // This is a block method

            try {
                waitSpinner.setVisible(true);  //show the wait spinner
                img = ImageIO.read(file);
                ImageIcon icon = new ImageIcon(img);

                int imgWidth = icon.getIconWidth();
                int imgHeight = icon.getIconHeight();

                input = new int[imgWidth][imgHeight];
                noised = new int[imgWidth][imgHeight];
                Random generator = new Random(0);
                for (int i = 0; i < imgWidth; i++) {
                    for (int j = 0; j < imgHeight; j++) {
                        int rgb = img.getRGB(i, j); //always returns TYPE_INT_ARGB
                        int red = (rgb >> 16) & 0xFF;
                        int green = (rgb >> 8) & 0xFF;
                        int blue = (rgb) & 0xFF;
                        // take luminosity approach method
                        // 0.21R + 0.71G + 0.07 B
                        input[i][j] = (int) Math.floor(0.21 * red + 0.71 * green + 0.07 * blue);
                        noised[i][j] = input[i][j] + (int) (generator.nextInt(noiselevel) - noiselevel / 2);
                    }
                }

                Image originalImage = imageBuilder(input);
                Image noiseImage = imageBuilder(noised);
                originalImage = originalImage.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                noiseImage = noiseImage.getScaledInstance(200, 200, Image.SCALE_SMOOTH);

                // clean up old images
                originalImageLabel.setVisible(false);
                noisyImageLabel.setVisible(false);
                remove(originalImageLabel);
                remove(noisyImageLabel);

                originalImageLabel = new JLabel(new ImageIcon(originalImage));
                noisyImageLabel = new JLabel(new ImageIcon(noiseImage));
                originalImageLabel.setBounds(5, 200, 200, 200);
                noisyImageLabel.setBounds(250, 200, 200, 200);
                add(originalImageLabel);
                add(noisyImageLabel);


                this.runButton.setEnabled(true);  //enable the button

            } catch (IOException e1) {

            } finally {
                waitSpinner.setVisible(false);
            }
        } else if (e.getSource() == exitButton) {
            System.exit(0);
        } else if (e.getSource() == roughRadioButton || e.getSource() == fineRadioButton) {
            // set current radioButton state
            System.out.println(roughRadioButton.isSelected() + "  " + fineRadioButton.isSelected());
        } else if (e.getSource() == runButton) {
            // run button clicked, we need to start threading and process two images
            try {
                System.out.println("Run algorithms");
                runButton.setEnabled(false);

                waitSpinner.setVisible(true);
                Recon rt1 = new Recon(noised, Dim, method);
                int[][] OMresult = rt1.OMRecon(dictionary, dictionary.Dim * noiselevel * noiselevel * 0.025);
                int[][] TOMresult = rt1.TOMRecon(tomTree, dictionary.Dim * noiselevel * noiselevel * 0.025);

                Image omImage = imageBuilder(OMresult);
                Image tomImage = imageBuilder(TOMresult);
                omImage = omImage.getScaledInstance(200, 200, Image.SCALE_SMOOTH);   // scale
                tomImage = tomImage.getScaledInstance(200, 200, Image.SCALE_SMOOTH); // scale

                // clean up old images
                omImageLabel.setVisible(false);
                tomImageLabel.setVisible(false);
                remove(omImageLabel);
                remove(tomImageLabel);

                omImageLabel = new JLabel(new ImageIcon(omImage));
                tomImageLabel = new JLabel(new ImageIcon(tomImage));
                omImageLabel.setBounds(505, 200, 200, 200);
                tomImageLabel.setBounds(755, 200, 200, 200);
                add(omImageLabel);
                add(tomImageLabel);
            } catch (Exception ex) {
                System.out.println(ex);
            } finally {
                waitSpinner.setVisible(false);
                runButton.setEnabled(true);
            }




        }

    }

    public void stateChanged(ChangeEvent e) {
        if (e.getSource() == slider) {
            deltaValueLabel.setText(Integer.toString(slider.getValue()));
            noiselevel = slider.getValue();    // update noise level
        }
    }

    private void initComponents() {

        // Set this before layout anything else, since it will get rid of other constraint
        // http://stackoverflow.com/questions/3195666/how-to-place-a-jbutton-at-a-desired-location-in-a-jframe-using-java
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
        slider = new JSlider(1, 100);
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
        waitSpinner = new JLabel(new ImageIcon(SPINNER_PATH));
        waitSpinner.setBounds(20, 150, 60, 60);
        add(waitSpinner);
        waitSpinner.setVisible(false); //invisibale now

        // create image labels
        JLabel label1 = new JLabel("Original Image:");
        JLabel label2 = new JLabel("Noisy Image:");
        JLabel label3 = new JLabel("Apply OM algorithm:");
        JLabel label4 = new JLabel("Apply Tree-OM algorithm");

        label1.setBounds(5, 180, 150, 20);
        label2.setBounds(255, 180, 150, 20);
        label3.setBounds(505, 180, 150, 20);
        label4.setBounds(755, 180, 200, 20);
        add(label1);add(label2);add(label3);add(label4);


        // create picture
        originalImageLabel = new JLabel();
        noisyImageLabel = new JLabel();
        omImageLabel = new JLabel();
        tomImageLabel = new JLabel();

        originalImageLabel.setBounds(5, 200, 200, 200);
        noisyImageLabel.setBounds(255, 200, 200, 200);
        omImageLabel.setBounds(505, 200, 200, 200);
        tomImageLabel.setBounds(755, 200, 200, 200);
        add(originalImageLabel);
        add(noisyImageLabel);
        add(omImageLabel);
        add(tomImageLabel);


        chooser = new JFileChooser();


    }

    private void initData() {
        try {
            dictionary = new Dictionary();
            dictionary.readDic(this.DICTIONARY_PATH);
            Dim = dictionary.Dim;
            tomTree = new TOMTree(delta, dictionary);
            System.out.println(tomTree.number);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Build a panel for image and put it into the main content panel
     *
     * @param img
     * @return
     */
    private JPanel panelBuilder(int[][] img) {
        int dimY = img.length;
        int dimX = img[0].length;

        final BufferedImage bf = new BufferedImage(dimX, dimY, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) bf.getGraphics();
        for (int i = 0; i < dimY; i++) {
            for (int j = 0; j < dimX; j++) {
                int c = img[i][j];
                if (c > 255)
                    c = 255;
                if (c < 0)
                    c = 0;
                g.setColor(new Color(c, c, c));
                g.fillRect(i, j, 1, 1);
            }
        }


        JPanel newpanel = new JPanel() {
            protected void paintComponent(Graphics g) {
                g.drawImage(bf, 0, 0, this);
            }
        };


        newpanel.setPreferredSize(new Dimension(200, 200));
        return newpanel;

        //Image scaledImage = originalImage.getScaledInstance(jPanel.getWidth(),jPanel.getHeight(),Image.SCALE_SMOOTH);


    }

    private Image imageBuilder(int[][] img) {
        int dimY = img.length;
        int dimX = img[0].length;

        BufferedImage bf = new BufferedImage(dimX, dimY, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) bf.getGraphics();
        for (int i = 0; i < dimY; i++) {
            for (int j = 0; j < dimX; j++) {
                int c = img[i][j];
                if (c > 255)
                    c = 255;
                if (c < 0)
                    c = 0;
                g.setColor(new Color(c, c, c));
                g.fillRect(i, j, 1, 1);
            }
        }

        return bf;

        //Image scaledImage = originalImage.getScaledInstance(jPanel.getWidth(),jPanel.getHeight(),Image.SCALE_SMOOTH);

    }


    public static void main(String[] args) {
        TomFrame tf = new TomFrame();
        tf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Display the window
        tf.setSize(new Dimension(1100, 600));
        //tf.pack();
        tf.setVisible(true);

    }

}
