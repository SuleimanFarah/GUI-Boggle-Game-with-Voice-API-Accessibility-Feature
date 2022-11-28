package boggle;

import javafx.application.Application;
import javafx.stage.Stage;

public class DemoBoggleApp extends Application {
    DemoView view;
    BoggleTimer timer;
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Start method.  Control of application flow is dictated by JavaFX framework
     *
     * @param primaryStage stage upon which to load GUI elements
     */
    @Override
    public void start(Stage primaryStage) {
        this.timer = new BoggleTimer();
        this.view = new DemoView(timer, primaryStage);

    }
}