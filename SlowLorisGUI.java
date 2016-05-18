import com.sun.prism.paint.Color;
import javafx.animation.Animation;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

public class SlowLorisGUI extends Application {

    final static int SCENE_WIDTH = 300;
    final static int SCENE_HEIGHT = 400;
    Image atackAnimationImage = new Image("file:///D:/JavaWorkspace/SlowLoris/res/explosion1.png");

    Stage window;
    Scene scene;
    BorderPane borderPane;
    ArrayList<SlowLoris> lorises = new ArrayList<SlowLoris>();
    static ThreadGroup group = new ThreadGroup("thread group");

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        borderPane = new BorderPane();
        scene = new Scene(borderPane, SCENE_WIDTH, SCENE_HEIGHT);


        borderPane.setCenter(genCenterSide());
        window.setScene(scene);
        window.show();
    }

    public GridPane genCenterSide(){
        GridPane grid = new GridPane();
        grid.setStyle("-fx-background-color: #BCD2EE");
        VBox vbox = new VBox(5);
        TextField hostTF = new TextField("127.0.0.1");
        TextField portTF = new TextField("8888");
        TextField timeoutTF = new TextField("100");
        TextField connectionsTF = new TextField("50");
        Button attackBtn = new Button("ATTACK");
        attackBtn.setStyle("-fx-text-fill: dodgerblue");
        ImageView logoImageView = new ImageView(new Image("file:///D:/JavaWorkspace/SlowLoris/res/title.png"));
        ImageView attackBtnImageView = new ImageView(new Image("file:///D:/JavaWorkspace/SlowLoris/res/attackBtn.png"));
        StackPane stackPane = new StackPane(logoImageView);
        stackPane.setPadding(new Insets(0, 0, 50, 0));


        attackBtnImageView.setOnMouseClicked(e -> {
            window.setScene(attackScene());
            int threads = 50;

            for(int i = 0; i < threads; i++) {
                SlowLoris loris =  new SlowLoris(group, hostTF.getText(),
                                            Integer.parseInt(portTF.getText()),
                                            Integer.parseInt(timeoutTF.getText()) * 1000,
                                            Integer.parseInt(connectionsTF.getText()));
                loris.start();
                lorises.add(loris);
            }
        });

        HBox buttonsHBox = new HBox(10);
        buttonsHBox.setAlignment(Pos.CENTER);
        buttonsHBox.getChildren().addAll(attackBtn);

        grid.add(stackPane, 0, 0, 2, 1);
        grid.add(new Label("Host:"), 0, 1);  		grid.add(hostTF, 1, 1);
        grid.add(new Label("Port:"), 0, 2);  		grid.add(portTF, 1, 2);
        grid.add(new Label("Timeout:"), 0, 3);  	grid.add(timeoutTF, 1, 3);
        grid.add(new Label("Connections:"), 0, 4);  grid.add(connectionsTF, 1, 4);
        grid.add(new StackPane(attackBtnImageView), 0, 5, 2, 1);					//grid.add(stopBtn, 1, 4);
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(10);
        grid.setHgap(5);

        return grid;
    }


    public Scene attackScene(){

        ImageView imageView = new ImageView(atackAnimationImage);
        imageView.setViewport(new Rectangle2D(0, 0, 128, 128));

        final Animation animation = new SpriteAnimation(
                imageView,
                Duration.millis(2000),
                24, 8,
                0, 0,
                128, 128
        );
        animation.setCycleCount(Animation.INDEFINITE);

        VBox vbox = new VBox(50);
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-background-color: #C67171");
        Scene attack_scene = new Scene(vbox, SCENE_WIDTH, SCENE_HEIGHT);

        ImageView stopBtnImageView = new ImageView(new Image("file:///D:/JavaWorkspace/SlowLoris/res/stopBtn.png"));
        Label activeThreads = new Label("Active threads: ");

        System.out.println(group.activeCount());

        stopBtnImageView.setOnMouseClicked(e -> {
            for (SlowLoris sl: lorises) {
                sl.stopFunc();
            }
            //group.interrupt();
            window.setScene(scene);
        });

        animation.play();
        vbox.getChildren().addAll(new Label("Server is under attack..."), imageView, stopBtnImageView);

        return attack_scene;
    }

}