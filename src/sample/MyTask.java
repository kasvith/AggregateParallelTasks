package sample;


import javafx.concurrent.Task;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyTask extends Task<Integer> {
    int iter;
    Random random = new Random();
    ProgressReporter reporter = new ProgressReporter();

    public MyTask(int iter) {
        this.iter = iter;
    }

    @Override
    protected Integer call() throws Exception {
        // Simply divide work load to each thread
        int workers = 8;
        int limit = iter/workers;
        int rem = iter % workers;


        reporter.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                //System.out.println("I got" + (int) evt.getNewValue());
                updateProgress((int) evt.getNewValue(), iter);
                updateMessage((int) evt.getNewValue()+ "/" + iter);
            }
        });

        // Creates a executor
        ExecutorService executorService = Executors.newFixedThreadPool(workers);
        for (int i = 0; i < workers; i++) {
            int start = limit * i;
            int end = limit * (i + 1);
            if (i == workers - 1) end += rem;

            Counter counter = new Counter(i ,start, end, 10 + random.nextInt(1000), reporter);
            executorService.submit(counter); // Submit work to be done
        }

        System.out.println("Executor shutdown");
        executorService.shutdown(); // start the execution


        System.out.println("came here");
        while (!executorService.isTerminated()){
            if (isCancelled()){
                executorService.shutdownNow();
            }
        }


        // Get their progress, update overall progress to UI
        // After each child finished in either state stop

        return 1;
    }
}
