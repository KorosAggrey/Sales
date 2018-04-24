package controller;

import Main.Signin;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * Created by Aggrey on 10/27/2017.
 */
public class Subcategories implements Initializable {

    @FXML
    private Label categoryname;

    @FXML
    private Label subcategoryname;

    @FXML
    private AnchorPane salesAnchor;

    @FXML
    private ScrollPane scrollFx;

    @FXML
    private TilePane tilepanefx;

    Signin su;
    Stage stage;
    public void Main(Signin su, Stage stage) {
        this.stage = stage;
        this.su =su;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadSubCat();
    }

    private void loadSubCat() {

        try
        {

            final Random rng = new Random();
            System.out.println(rng);

            tilepanefx.setPadding(new Insets(10, 10, 10, 10));
            tilepanefx.setVgap(4);
            tilepanefx.setHgap(5);
            Button btn = new Button();

            for (int i = 0; i < 40; i++) {

                String style = String.format(" -fx-border-color: rgb(%d, %d, %d); -fx-border-width: 4 0 0 0; ",
                        rng.nextInt(256),
                        rng.nextInt(256),
                        rng.nextInt(256));
                btn.setStyle(style);

                System.out.println(style);

                btn = new Button("Chocklate Brownie");
                btn.setStyle("-fx-font-size:12px;-fx-effect:  dropshadow(gaussian, rgb(0.0, 0.0, 0.0 ,0.15), 6.0, 0.7, 0.0,1.5); -fx-background-color: #E1E6DF; -fx-text-fill:#000100; -fx-font-family: 'RobotoDraft', 'Roboto', 'sans-serif';");
                btn.setPrefSize(118, 100);
                tilepanefx.getChildren().add(btn);
                scrollFx.setContent(tilepanefx);

                /*
                btn.setOnAction( e ->
                {
                    su.subWindow();
                });*/

            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }


}
