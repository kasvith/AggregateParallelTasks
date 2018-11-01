package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class Controller {
    public ProgressBar progressBar;
    public Button btnStart;
    public Label lblProgress;
    public Button btnStop;
    MyTask task;
    Thread thread;

    public void startThreads(ActionEvent actionEvent) {
        task = new MyTask(1000);
        progressBar.progressProperty().unbind();
        progressBar.progressProperty().bind(task.progressProperty());
        lblProgress.textProperty().bind(task.messageProperty());

        thread = new Thread(task);
        thread.start();
    }

    public void stopThreads(ActionEvent actionEvent) {
        task.cancel();
    }
}
