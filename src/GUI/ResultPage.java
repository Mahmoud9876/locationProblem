package GUI;

import Utils.CplexSolver;
import Utils.InstanceGenerator;
import ilog.concert.IloException;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.opl.IloCplex;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.Random;

public class ResultPage {
    private JPanel panel;
    private static IloNumVar[][][] x;
    private static InstanceGenerator generator;

    private static CplexSolver cplexSolver;

    public ResultPage() throws IloException {
        panel.setPreferredSize(new Dimension(480, 400));
        generator = new InstanceGenerator();
        cplexSolver = new CplexSolver();
    }

    public JPanel getPanel(){
        return panel;
    }

    public void startResultPage(float budget, int niveauAffectation, int nombreClients, int niveauCapacite){
        JFrame frame = new JFrame("ResultPage");
        solveTSP(frame, budget, niveauAffectation, nombreClients, niveauCapacite);
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Assets/favicon.png")));
    }


    public static void solveTSP(JFrame frame, float budget, int niveauAffectation, int nombreClients, int niveauCapacite) {
        IloCplex cPlex = null;
        try {
            cPlex = new IloCplex();
            Random rand = new Random();

            int[] nombresClients = new int[niveauAffectation + 1];

            nombresClients[0] = nombreClients;
            for(int i=1; i < niveauAffectation + 1; i++) {
                nombresClients[i] = rand.nextInt(5, 30);
                System.out.println("Niveau : " + i);
                System.out.println(nombresClients[i]);
            }   

            double[] demande = new double[nombreClients];
            for (int i = 0; i < nombreClients; i++) {
                demande[i] = rand.nextDouble() * 30 + 5;
            }

            double[][][] coutAffectation = new double[niveauAffectation][][];
            for (int l = 0; l < niveauAffectation; l++) {
                coutAffectation[l] = new double[nombresClients[l]][];
                for (int i = 0; i < nombresClients[l]; i++) {
                    coutAffectation[l][i] = new double[nombresClients[l+1]];
                    for (int j = 0; j < nombresClients[l+1]; j++) {
                        coutAffectation[l][i][j] = rand.nextDouble() * 180 + 20;
                        System.out.println("coutAffectation[" + l + "][" + i + "][" + j + "] = " + coutAffectation[l][i][j]);
                    }
                }
            }


            double[][][] capaciteInstallation = new double[niveauAffectation][][];
            for (int l = 0; l < niveauAffectation; l++) {
                capaciteInstallation[l] = new double[nombresClients[l+1]][];
                for (int i = 0; i < nombresClients[l+1]; i++) {
                    capaciteInstallation[l][i] = new double[niveauCapacite];
                    for (int k = 0; k < niveauCapacite; k++) {
                        if (l == 0) {
                            capaciteInstallation[l][i][k] = rand.nextDouble() * 150 + 10;
                        } else if (l == 1) {
                            capaciteInstallation[l][i][k] = rand.nextDouble() * 680 + 20;
                        } else {
                            capaciteInstallation[l][i][k] = rand.nextDouble() * 970 + 30;
                        }
                    }
                }
            }

            double[][][] coutOuvertureInstallation = new double[niveauAffectation][][];
            for (int l = 0; l < niveauAffectation; l++) {
                coutOuvertureInstallation[l] = new double[nombresClients[l+1]][];
                for (int j = 0; j < nombresClients[l+1]; j++) {
                    coutOuvertureInstallation[l][j] = new double[niveauCapacite];
                    for (int k = 0; k < niveauCapacite; k++) {
                        coutOuvertureInstallation[l][j][k] = rand.nextDouble() * 90 + (100 + rand.nextDouble() * 10) * Math.sqrt(capaciteInstallation[l][j][k]);
                        System.out.println("coutOuvertureInstallation[" + l + "][" + j + "][" + k + "] = " + coutOuvertureInstallation[l][j][k]);
                    }
                }
            }

//            nombresClients = generator.getNombreClients(nombreClients, niveauAffectation);

            // variables
//            x = cplexSolver.setX(niveauAffectation, nombresClients, x);
            x = new IloNumVar[niveauAffectation][][];
            System.out.println("---------------------avant boucle -----------------");

            for (int l = 0; l < niveauAffectation; l++) {
                System.out.println("---------------------" + nombresClients[0] + "-----------------");
                x[l] = new IloNumVar[nombresClients[l]][];
                for (int i = 0; i < nombresClients[l]; i++) {
                    x[l][i] = new IloNumVar[nombresClients[l+1]];
                    for (int j = 0; j < nombresClients[l+1]; j++) {
                        x[l][i][j] = cPlex.numVar(0, Double.MAX_VALUE);
                        System.out.println("--------------------------------------" + x[l][i][j]);
                    }
                }
            }

            System.out.println("--------------------------apres boucle ------------");

            IloNumVar[][][] y = new IloNumVar[niveauAffectation][][];
            y = new IloNumVar[niveauAffectation][][];
            for (int l = 0; l < niveauAffectation; l++) {
                y[l] = new IloNumVar[nombresClients[l+1]][];
                for (int j = 0; j < nombresClients[l+1]; j++) {
                    y[l][j] = new IloNumVar[niveauCapacite];
                    for (int k = 0; k < niveauCapacite; k++) {
                        y[l][j][k] = cPlex.boolVar();
                    }
                }
            }
//            y = cplexSolver.setY(niveauAffectation, nombresClients, niveauCapacite, y);

            // Instances aléatoires
//            demande = generator.getDemande(nombreClients, demande);
//            coutAffectation = generator.getCoutAffectation(niveauAffectation, nombresClients, coutAffectation);
//            capaciteInstallation = generator.getCapaciteInstallation(niveauAffectation, nombresClients, niveauCapacite, capaciteInstallation);
//            coutOuvertureInstallation = generator.getCoutOuvertureInstallation(niveauAffectation, nombresClients, niveauCapacite, capaciteInstallation, coutOuvertureInstallation);

            // expressions
            IloLinearNumExpr objective = cPlex.linearNumExpr();
            objective = cplexSolver.setObjective(niveauAffectation, nombresClients, coutAffectation, objective, x);

            // Définition de la fonction objective
            cPlex.addMinimize(objective);

            // Définition des contraintes
            for (int i = 0; i < nombreClients; i++) {
                IloLinearNumExpr Demande = cPlex.linearNumExpr();
                for (int j = 0; j < nombresClients[1]; j++) {
                    Demande.addTerm(1.0, x[0][i][j]);
                }
                cPlex.addEq(Demande, demande[i]); // Ajoutez la contrainte au modèle
            }

            //contrainte de capacité
            for (int l = 0; l < niveauAffectation; l++) {
                for (int j = 0; j < nombresClients[l+1]; j++) {
                    IloLinearNumExpr capacity = cPlex.linearNumExpr();

                    for (int i = 0; i < nombresClients[l]; i++) {
                        capacity.addTerm(1.0, x[l][i][j]); // Ajoutez les termes correspondants à x
                    }

                    for (int k = 0; k < niveauCapacite; k++) {
                        capacity.addTerm(-capaciteInstallation[l][j][k], y[l][j][k]); // Ajoutez les termes correspondants à la capacité d'installation et y avec un coefficient négatif
                    }

                    cPlex.addLe(capacity, 0.0); // Ajoutez la contrainte au modèle
                }
            }

            // contrainte d'équilibre de flux
            for (int l = 1; l < niveauAffectation; l++) {
                for (int i = 0; i < nombresClients[l]; i++) {
                    IloLinearNumExpr equilibre = cPlex.linearNumExpr();

                    for (int j = 0; j < nombresClients[l+1]; j++) {
                        equilibre.addTerm(1.0, x[l][i][j]);
                    }

                    for (int p = 0; p < nombresClients[l-1]; p++) {
                        equilibre.addTerm(-1.0, x[l - 1][p][i]);
                    }

                    cPlex.addGe(equilibre, 0.0);
                }
            }

            //un seul niveau de capacité ouvert
            for (int l = 0; l < niveauAffectation; l++) {
                for (int j = 0; j < nombresClients[l+1]; j++) {
                    IloLinearNumExpr ouverture = cPlex.linearNumExpr();

                    for (int k = 0; k < niveauCapacite; k++) {
                        ouverture.addTerm(1.0, y[l][j][k]);
                    }

                    cPlex.addLe(ouverture, 1.0);
                }
            }

            //contrainte budgétaire
            IloLinearNumExpr contrainteBudget = cPlex.linearNumExpr();
            for (int l = 0; l < niveauAffectation; l++) {
                for (int j = 0; j < nombresClients[l+1]; j++) {
                    for (int k = 0; k < niveauCapacite; k++) {
                        contrainteBudget.addTerm(y[l][j][k], coutOuvertureInstallation[l][j][k]);
                    }
                }
            }

            DecimalFormat decimalFormat = new DecimalFormat("#.##");

            cPlex.addLe(contrainteBudget, budget);
            // solve
            if (cPlex.solve()) {
                // Affichage de la valeur de la fonction objective
                System.out.println("Objective Value: " + decimalFormat.format(cPlex.getObjValue()));

                JPanel panel = new JPanel();
                panel.setLayout(new BorderLayout());

                JPanel tables = new JPanel();
                tables.setLayout(new GridLayout(1, 4));

                // Create a JTable with a DefaultTableModel
                JTable tableQuantite = new JTable();
                DefaultTableModel modelQuantite = new DefaultTableModel();

                // Add columns to the model
                modelQuantite.addColumn("Installation i");
                modelQuantite.addColumn("Installation j");
                modelQuantite.addColumn("Niveau d'affectation");
                modelQuantite.addColumn("Quantité affectée");
                tableQuantite.setModel(modelQuantite);

                // Print variable values
                for (int l = 0; l < niveauAffectation; l++) {
                    for (int i = 0; i < nombresClients[l]; i++) {
                        for (int j = 0; j < nombresClients[l+1]; j++) {
                            System.out.println("x[" + l + "][" + i + "][" + j + "] = " + cPlex.getValue(x[l][i][j]));
                            if(cPlex.getValue(x[l][i][j]) != 0) {
                                modelQuantite.addRow(new Object[]{i + 1, j + 1, l + 1, decimalFormat.format(cPlex.getValue(x[l][i][j]))});
                            }
                        }
                    }
                }
                // adding it to JScrollPane
                JScrollPane sp = new JScrollPane(tableQuantite);
                panel.add(new Label("La valeur de la fonction objective w = " + decimalFormat.format(cPlex.getObjValue())), BorderLayout.NORTH);

                JPanel quantiteAffectation = new JPanel();
                quantiteAffectation.setLayout(new BorderLayout());

                quantiteAffectation.add(new Label("Tableau des quantités affectées de l'installation j vers l'installation i au niveau l"), BorderLayout.NORTH);
                quantiteAffectation.add(sp, BorderLayout.WEST);

                tables.add(quantiteAffectation);


                JPanel ouvertureInstallation = new JPanel();
                ouvertureInstallation.setLayout(new BorderLayout());

                // Create a JTable with a DefaultTableModel
                JTable tableOuvertureInstallation = new JTable();
                JScrollPane sp1 = new JScrollPane(tableOuvertureInstallation);
                DefaultTableModel modelOuvertureInstallation = new DefaultTableModel();

                ouvertureInstallation.add(new Label("Tableau indiquant les installations utilisées à chaque niveau"), BorderLayout.NORTH);
                ouvertureInstallation.add(sp1, BorderLayout.WEST);

                tables.add(ouvertureInstallation);

                // Add columns to the model
                modelOuvertureInstallation.addColumn("Installation i");
                modelOuvertureInstallation.addColumn("Niveau l");
                modelOuvertureInstallation.addColumn("Niveau k");
                modelOuvertureInstallation.addColumn("Ouverture & Utilisation");
                tableOuvertureInstallation.setModel(modelOuvertureInstallation);

                for (int l = 0; l < niveauAffectation; l++) {
                    for (int j = 0; j < nombresClients[l+1]; j++) {
                        for (int k = 0; k < niveauCapacite; k++) {
                            System.out.println("y[" + l + "][" + j + "][" + k + "] = " + (int) cPlex.getValue(y[l][j][k]));
                            if ((int) cPlex.getValue(y[l][j][k]) == 1) {
                                modelOuvertureInstallation.addRow(new Object[]{j + 1, l + 1, k + 1, "Oui"});
                            } else {
//                                modelOuvertureInstallation.addRow(new Object[]{j + 1, + 1, k + 1, "Non"});
                            }
                        }
                    }
                }

                // Create a JTable with a DefaultTableModel
                JTable tableDemande = new JTable();
                JScrollPane sp2 = new JScrollPane(tableDemande);
                DefaultTableModel modelDemande = new DefaultTableModel();

                modelDemande.addColumn("Client i");
                modelDemande.addColumn("Demade");
                tableDemande.setModel(modelDemande);

                JPanel demandeClient = new JPanel();
                demandeClient.setLayout(new BorderLayout());

                demandeClient.add(new Label("Tableau des demandes clients"), BorderLayout.NORTH);
                demandeClient.add(sp2, BorderLayout.CENTER);


                // Les valeurs de demande
                System.out.println("Les valeurs de la demande sont:");
                for (int i = 0; i < nombresClients[0]; i++) {
                    System.out.println("demande[" + i + "] = " + demande[i]);
                    modelDemande.addRow(new Object[]{i + 1, decimalFormat.format(demande[i])});
                }

                // Create a JTable with a DefaultTableModel
                JTable tableClients = new JTable();
                JScrollPane sp3 = new JScrollPane(tableClients);
                DefaultTableModel modelClients = new DefaultTableModel();

                modelClients.addColumn("Niveau");
                modelClients.addColumn("Nombre de clients");
                tableClients.setModel(modelClients);

                demandeClient.add(sp3, BorderLayout.SOUTH);

                tables.add(demandeClient);

                // Le nombre de clients par niveau
                for (int i = 0; i < niveauAffectation + 1; i++) {
                    System.out.println("clients[" + i + 1 + "] = " + nombresClients[i]);
                    modelClients.addRow(new Object[]{i + 1, decimalFormat.format(nombresClients[i])});
                }

                panel.add(tables, BorderLayout.EAST);

                frame.setTitle("Solveur du problème de localisation");
                frame.setContentPane(panel);
                frame.setPreferredSize(new Dimension(520, 410));
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
            } else {
                frame.setTitle("Solveur du problème de localisation");
                JLabel notSolvedLabel = new JLabel();
                notSolvedLabel.setText("Model not solved");
                JPanel notSolvedPanel = new JPanel();
                notSolvedPanel.add(notSolvedLabel);
                frame.setContentPane(notSolvedPanel);
                frame.setPreferredSize(new Dimension(520, 410));
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.pack();
                frame.setVisible(true);
                System.out.println("Model not solved");
            }

            // Close the cPlex object
            cPlex.end();
        } catch (IloException exc) {
            exc.printStackTrace();
            System.out.println(exc.getMessage());
            frame.setTitle("Solveur du problème de localisation");
            JLabel notSolvedLabel = new JLabel();
            notSolvedLabel.setText(exc.getMessage());
            JPanel notSolvedPanel = new JPanel();
            notSolvedPanel.add(notSolvedLabel);
            frame.setContentPane(notSolvedPanel);
            frame.setPreferredSize(new Dimension(520, 410));
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
            cPlex.end();
        }
    }
}
