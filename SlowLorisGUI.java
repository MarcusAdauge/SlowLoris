import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SlowLorisGUI extends Application {

    Stage window;
    Scene scene;
    BorderPane borderPane;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
        borderPane = new BorderPane();
        scene = new Scene(borderPane, 400, 500);


        borderPane.setCenter(genCenterSide());
        window.setScene(scene);
        window.show();
    }

    public GridPane genCenterSide(){
        GridPane grid = new GridPane();
        VBox vbox = new VBox(5);
        TextField hostTF = new TextField();
        TextField portTF = new TextField();
        TextField timeoutTF = new TextField();
        TextField tcpTimeoutTF = new TextField();
        TextField connectionsTF = new TextField();
        CheckBox cacheCB = new CheckBox("Cache");
        Button attackBtn = new Button("Atack");
        Button stopBtn = new Button("Stop");

        attackBtn.setOnAction(e -> {
            new SlowLoris(hostTF.getText(),
                    Integer.parseInt(portTF.getText()),
                    Integer.parseInt(timeoutTF.getText()),
                    Integer.parseInt(tcpTimeoutTF.getText()),
                    Integer.parseInt(connectionsTF.getText())).start();
        });

        grid.add(new Label("Host:"), 0, 0);  		grid.add(hostTF, 1, 0);
        grid.add(new Label("Port:"), 0, 1);  		grid.add(portTF, 1, 1);
        grid.add(new Label("Timeout:"), 0, 2);  	grid.add(timeoutTF, 1, 2);
        grid.add(new Label("TCP Timeout:"), 0, 3);  grid.add(tcpTimeoutTF, 1, 3);
        grid.add(new Label("Connections:"), 0, 4);  grid.add(connectionsTF, 1, 4);
        grid.add(attackBtn, 0, 5);					grid.add(stopBtn, 1, 5);
        grid.setAlignment(Pos.CENTER);
        grid.setVgap(5);
        grid.setHgap(5);

        return grid;
    }
}