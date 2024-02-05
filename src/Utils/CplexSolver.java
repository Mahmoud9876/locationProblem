package Utils;

import ilog.concert.IloException;
import ilog.concert.IloLinearNumExpr;
import ilog.concert.IloNumVar;
import ilog.opl.IloCplex;

public class CplexSolver {
    IloCplex cPlex;
    public CplexSolver() throws IloException {
        cPlex  = new IloCplex();
    }

    public IloNumVar[][][] setX(int niveauAffectation, int[] nombreClients, IloNumVar[][][] x) throws IloException {
        for (int l = 0; l < niveauAffectation; l++) {
            x = new IloNumVar[niveauAffectation][nombreClients[l]][nombreClients[l+1]];
            for (int i = 0; i < nombreClients[l]; i++) {
                for (int j = 0; j < nombreClients[l+1]; j++) {
                    x[l][i][j] = cPlex.numVar(0, Double.MAX_VALUE);
                }
            }
        }

        return x;
    }

    public IloNumVar[][][] setY(int niveauAffectation, int[] nombreClients, int niveauCapacite, IloNumVar[][][] y) throws IloException {
        y = new IloNumVar[niveauAffectation][][];
        for (int l = 0; l < niveauAffectation; l++) {
            y[l] = new IloNumVar[nombreClients[l+1]][];
            for (int j = 0; j < nombreClients[l+1]; j++) {
                y[l][j] = new IloNumVar[niveauCapacite];
                for (int k = 0; k < niveauCapacite; k++) {
                    y[l][j][k] = cPlex.boolVar();
                }
            }
        }

        return y;
    }

    public IloLinearNumExpr setObjective(int niveauAffectation, int[] nombreClients, double[][][] coutAffectation, IloLinearNumExpr objective, IloNumVar[][][] x) throws IloException {
        for (int l = 0; l < niveauAffectation; l++) {
            for (int i = 0; i < nombreClients[l]; i++) {
                for (int j = 0; j < nombreClients[l+1]; j++) {
                    try {
                        objective.addTerm(coutAffectation[l][i][j], x[l][i][j]);
                    } catch(IloException exception) {
                        System.out.println("aff: " + coutAffectation[l][i][j] + "x: " + x[l][i][j]);
                    }
                }
            }
        }

        return objective;
    }
}
