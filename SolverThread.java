package mensa;

public class SolverThread extends Thread {
    Solver solver;

    SolverThread(Solver solver) {
        this.solver = solver;
    }

    public void run() {
        try {
            solver.solve();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
