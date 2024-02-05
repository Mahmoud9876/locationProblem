package Utils;

import java.io.File;
import java.util.Random;

public class InstanceGenerator {
    private Random rand;
    private FileManager fileManager;
    public InstanceGenerator(){
        rand = new Random();
        fileManager = new FileManager();
    }
    public double[][][] getCoutAffectation(int niveauAffectation, int[] nombreClients, double[][][] coutAffectation){
        System.out.println("Les couts d'affectation:");
        fileManager.writeIntances("\n\n\n\n\n");
        fileManager.writeIntances("------------------------------------------Les couts d'affectation------------------------------------------");

        for (int l = 0; l < niveauAffectation; l++) {
            coutAffectation[l] = new double[nombreClients[l]][];
            for (int i = 0; i < nombreClients[l]; i++) {
                coutAffectation[l][i] = new double[nombreClients[l+1]];
                for (int j = 0; j < nombreClients[l+1]; j++) {
                    coutAffectation[l][i][j] = rand.nextDouble() * 180 + 20;
                    System.out.println("coutAffectation[" + l + "][" + i + "][" + j + "] = " + coutAffectation[l][i][j]);
                    fileManager.writeIntances("[" + l + "][" + i + "][" + j + "] = " + coutAffectation[l][i][j] + "\n");
                }
            }
        }

        return coutAffectation;
    }

    public double[][][] getCapaciteInstallation(int niveauAffectation, int[] nombresClients, int niveauCapacite, double[][][] capaciteInstallation){
        fileManager.writeIntances("\n\n\n\n\n");
        fileManager.writeIntances("------------------------------------------Capacité des installation------------------------------------------");
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
                    fileManager.writeIntances("[" + l + "][" + i + "][" + k + "] = " + capaciteInstallation[l][i][k] + "\n");
                }
            }
        }

        return  capaciteInstallation;
    }

    public double[][][] getCoutOuvertureInstallation(int niveauAffectation, int[] nombresClients, int niveauCapacite, double[][][] capaciteInstallation, double[][][] coutOuvertureInstallation){
        System.out.println("Les valeurs de cout ouverture des installations sont: ");
        fileManager.writeIntances("\n\n\n\n\n");
        fileManager.writeIntances("------------------------------------------Les valeurs de cout ouverture des installations------------------------------------------");
        int coutOuvertureInstallationTotal = 0;
        for (int l = 0; l < niveauAffectation; l++) {
            coutOuvertureInstallation[l] = new double[nombresClients[l+1]][];
            for (int j = 0; j < nombresClients[l+1]; j++) {
                coutOuvertureInstallation[l][j] = new double[niveauCapacite];
                for (int k = 0; k < niveauCapacite; k++) {
                    coutOuvertureInstallation[l][j][k] = rand.nextDouble() * 90 + (100 + rand.nextDouble() * 10) * Math.sqrt(capaciteInstallation[l][j][k]);
                    coutOuvertureInstallationTotal += coutOuvertureInstallation[l][j][k];
                    System.out.println("coutOuvertureInstallation[" + l + "][" + j + "][" + k + "] = " + coutOuvertureInstallation[l][j][k]);
                    fileManager.writeIntances("[" + l + "][" + j + "][" + k + "] = " + coutOuvertureInstallation[l][j][k] + "\n");
                }
            }
        }
        System.out.println("cout Ouverture Installation Totale  = " + coutOuvertureInstallationTotal);
        fileManager.writeIntances("cout Ouverture Installation Totale  = " + coutOuvertureInstallationTotal+ "\n");

        return coutOuvertureInstallation;
    }

    public double[] getDemande(int nombreClients, double[] demande){
        fileManager.writeIntances("------------------------------------------Demandes Clients------------------------------------------");
        for (int i = 0; i < nombreClients; i++) {
            demande[i] = rand.nextDouble() * 30 + 5;
            fileManager.writeIntances(i + " " + demande[i] + "\n");
        }

        return demande;
    }

    public int[] getNombreClients(int nombreClient, int niveauAffectation){
        int[] nombreClients = new int[niveauAffectation + 1];
        nombreClients[0] = nombreClient;
        for(int i=1; i < niveauAffectation + 1; i++) {
            nombreClients[i] = rand.nextInt(1, 15);
        }

        return nombreClients;
    }
}
