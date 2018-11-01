package sample;

import java.util.Random;

public class Counter implements Runnable {
    private int id, from, to, sleep;
    private ProgressReporter reporter;
    Random random = new Random();

    public Counter(int id, int from, int to, int sleep, ProgressReporter reporter) {
        this.from = from;
        this.to = to;
        this.sleep = sleep;
        this.id = id;
        this.reporter = reporter;

        System.out.println("Thread #" + id + " started delay=" + sleep);
    }

    @Override
    public void run() {
        for (int i = from; i < to ; i++) {
            try {
                Thread.sleep(sleep);
                reporter.accumulateProgress(1);
                //System.out.println("Thread #" + id + " is at " + i);
            } catch (InterruptedException e){

            }
        }

        System.out.println("Thread #" + id + " is completed");

        // exit thread with status of success or failure
    }
}
