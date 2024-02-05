package GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

public class SplashPage {

    private VariablePage variablePage;
    private JButton startButton;
    private JPanel panel1;
    private JLabel imageLabel;
    private JLabel header;

    static  JFrame frame;

    public SplashPage() {
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                variablePage = new VariablePage();
                variablePage.startVariablePage();
                frame.dispose();
            }
        });
    }

    private void createUIComponents() {

    }

    public JPanel getPanel(){
        return this.panel1;
    }

    public void displayGUI(){
        frame = new JFrame("SplashPage");
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Assets/favicon.png")));
        frame.setTitle("Solveur du problème de localisation");
        frame.setContentPane(new SplashPage().getPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}