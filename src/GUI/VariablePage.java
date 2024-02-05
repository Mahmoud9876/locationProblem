package GUI;

import ilog.concert.IloException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class VariablePage {
    private JPanel panel;
    private JTextField budget;
    private JTextField niveauAffectation;
    private JButton calculer;
    private JLabel budgetLabel;
    private JLabel niveauLabel;
    private JTextField nombreClients;
    private JLabel nombreClientLabel;
    private JLabel niveauCapaciteLabel;
    private JTextField niveauCapacite;
    private ResultPage resultPage;

    public VariablePage(){
        budget.setPreferredSize(new Dimension(150,30));
        niveauAffectation.setPreferredSize(new Dimension(150,30));
        nombreClients.setPreferredSize(new Dimension(150,30));
        niveauCapacite.setPreferredSize(new Dimension(150,30));
        panel.setPreferredSize(new Dimension(480,400));
        calculer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    resultPage = new ResultPage();
                } catch (IloException ex) {
                    throw new RuntimeException(ex);
                }
                resultPage.startResultPage(Float.valueOf(budget.getText()), Integer.valueOf(niveauAffectation.getText()), Integer.valueOf(nombreClients.getText()), Integer.valueOf(niveauCapacite.getText()));
            }
        });
    }

    public void startVariablePage(){
        JFrame frame = new JFrame("VariablePage");
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Assets/favicon.png")));
        frame.setTitle("Solveur du problème de localisation");
        frame.setContentPane(new VariablePage().getPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public JPanel getPanel(){
        return this.panel;
    }
}
