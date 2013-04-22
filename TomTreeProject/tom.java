//import java.awt.*;
//import java.awt.event.*;
//import java.awt.image.*;
//import java.io.*;
//
//import javax.imageio.*;
//import javax.swing.*;
//
//import java.lang.Math;
////matlab import/export
//import com.jmatio.io.*;
//import com.jmatio.types.*;
////matrix multiplier
//import Jama.*;
//
//public class tom{
//	static int [][] img_original = null;
//
//	public static void main(String args[]) throws Exception{
//		TomDictionary mydic = new TomDictionary();
//		try{
//			mydic.readDic("TomDictionary.txt");
//		}
//		catch(IOException e1) {}
//		//		myFrame mf = new myFrame();
//		//		img_original = mf.readin;
//		//		mf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		//		mf.setVisible(true);
//	}
//}
//
//
//class myFrame extends JFrame {
//	JFileChooser chooser;
//	BufferedImage img;
//	File file ;
//	JLabel label = new JLabel();
//	JPanel secpanel = new JPanel();
//
//	JMenuBar menubar;
//	JMenu fileMb;
//	JMenuItem openButton, exitButton;
//
//	int [][] readin;
//
//	public myFrame() {
//
//		menubar = new JMenuBar(); setJMenuBar(menubar);
//		fileMb = new JMenu("File");
//		openButton = new JMenuItem("Open");
//		exitButton = new JMenuItem("Exit");
//
//		menubar.add(fileMb);
//		fileMb.add(openButton);
//		fileMb.add(exitButton);
//
//		chooser = new JFileChooser();
//
//		ActionListener action=new ActionListener()
//		{
//			public void actionPerformed(ActionEvent e) {
//				if (e.getSource()==openButton) {
//					chooser.showOpenDialog(null);
//					file = chooser.getSelectedFile();
//
//					try {
//						img=ImageIO.read(file);
//						ImageIcon icon=new ImageIcon(img);
//						label.setIcon(icon);
//
//						int imgWidth = icon.getIconWidth();
//						int imgHeight = icon.getIconHeight();
//						System.out.println(imgWidth + " + " + imgHeight);
//
//						//	label.setPreferredSize(new Dimension(imgWidth, imgHeight));
//						setSize(new Dimension(imgWidth+20, imgHeight+20));
//						setVisible(true);
//
//						int [][] input = new int[imgWidth][imgHeight];
//						for (int i = 0; i<imgWidth; i++)
//							for(int j = 0; j<imgHeight; j++){
//								int rgb = img.getRGB(i, j); //always returns TYPE_INT_ARGB
//								int red =   (rgb >> 16) & 0xFF;
//								int green = (rgb >>  8) & 0xFF;
//								int blue =  (rgb      ) & 0xFF;
//
//								input[i][j] = (int) Math.floor((red+green+blue)/3);
//								//		System.out.println(input[i][j]);
//							}
//						readin = input;
//					}
//					catch(IOException e1) {}
//				}
//				if(e.getSource()==exitButton){
//					System.exit(0);
//				}
//			}
//
//		};
//
//		setLayout(new BorderLayout());
//		secpanel.add(label,BorderLayout.CENTER);
//		add(secpanel);
//		openButton.addActionListener(action);
//		exitButton.addActionListener(action);
//	}
//}