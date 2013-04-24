import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.*;
import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;
import com.jgoodies.forms.factories.CC;

/*
 * Created by JFormDesigner on Wed Apr 24 17:11:52 EDT 2013
 */



/**
 * @author James Huang
 */
public class TomFrame extends JFrame implements ActionListener {

    private JMenuBar menuBar;
    private JMenu loadImageMenu;

    private JMenuItem loadImageButton;
    private JMenuItem exitButton;

    private JFileChooser chooser;

    private JSlider slider;



    //-------------- The following are Model data -----------------//

    private BufferedImage img;
    private static final long serialVersionUID = 1L;
    static int Dim;
    static Dictionary dictionary;
    static TOMTree tomTree;
    static double delta = 0.1;
    static int noiselevel=35;
    JPanel originalpanel, noisedpanel, TOMpanel, OMpanel;
    static String method = "sliding"; // method: distinct/sliding

    static int[][] input, noised, TOMresult, OMresult;

    public static final String DICTIONARY_PATH = "C:\\repositories\\Algorithms\\TomTreeProject\\Dictionary.txt";

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
                int[][] OMresult = rt1.OMRecon(dictionary, dictionary.Dim *noiselevel*noiselevel*0.025);
                int[][] TOMresult = rt1.TOMRecon(tomTree, dictionary.Dim *noiselevel*noiselevel*0.025);

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
            catch(IOException e1) {

            }
        } else if (e.getSource() == exitButton) {
             System.exit(0);
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

//        JPanel sliderPanel = new JPanel();
//        sliderPanel.setBounds();

        slider = new JSlider(1, 10);
        slider.setBounds(10, 10, 100, 20);



        this.add(slider);




        chooser = new JFileChooser();

        // add event handler
        loadImageButton.addActionListener(this);
        exitButton.addActionListener(this);
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

    public static void main(String[] args) {
        TomFrame tf = new TomFrame();
        tf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Display the window
        tf.setSize(new Dimension(320, 480));
        //tf.pack();
        tf.setVisible(true);

    }

}
