import javax.swing.*;
import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;
import com.jgoodies.forms.factories.CC;

import java.awt.*;
/*
 * Created by JFormDesigner on Wed Apr 24 16:40:26 EDT 2013
 */



/**
 * @author unknown
 */
public class TomPanel extends JPanel {
    public TomPanel() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - James Huang
        label1 = new JLabel();
        button1 = new JButton();
        slider1 = new JSlider();
        progressBar2 = new JProgressBar();
        progressBar1 = new JProgressBar();

        //======== this ========

        // JFormDesigner evaluation mark
        setBorder(new javax.swing.border.CompoundBorder(
            new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                "JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
                javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
                java.awt.Color.red), getBorder())); addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent e) {
                if ("border".equals(e.getPropertyName())) throw new RuntimeException();
            }
        });

        setLayout(new FormLayout(
                "30*(default, $lcgap), default",
                "17*(default, $lgap), default"));

        //---- label1 ----
        label1.setText("Noise value:");
        add(label1, CC.xywh(21, 3, 9, 1));
        Graphics g;

        //---- button1 ----
        button1.setText("Run");
        add(button1, CC.xywh(41, 3, 8, 1));
        add(slider1, CC.xywh(25, 5, 13, 1));
        add(progressBar2, CC.xywh(21, 31, 11, 1));
        add(progressBar1, CC.xywh(37, 31, 9, 1));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - James Huang
    private JLabel label1;
    private JButton button1;
    private JSlider slider1;
    private JProgressBar progressBar2;
    private JProgressBar progressBar1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
