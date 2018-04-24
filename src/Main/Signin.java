package Main;

import controller.Login;
import controller.Sales;
import controller.Subcategories;
import controller.splashcontroller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Signin extends Application {

    static Stage  splash , stageprim, sales,subcat;
    public String Posname = "";

    @Override
    public void start(Stage stage) throws Exception{

        this.splash = stage ;
        splashWindow();
        //salesWindow();
        
        //Parent root = FXMLLoader.load(getClass().getResource("../controller/sample.fxml"));
        //primaryStage.setTitle("Hello World");
        //primaryStage.setScene(new Scene(root, 300, 275));
        //primaryStage.show();
    }

    private void splashWindow() {

        try
        {

            FXMLLoader loader = new FXMLLoader(Signin.class.getResource("/view/splashfxml.fxml"));
            AnchorPane pane = loader.load();
            splashcontroller controller = loader.getController();
            controller.Main(this,splash);
            Scene scene = new Scene(pane);
            splash.initStyle(StageStyle.UNDECORATED);
            scene.getStylesheets().add(Signin.class.getResource("/style/StyleSheet.css").toExternalForm());
            splash.setScene(scene);
            splash.show();

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void splashWindowClose() {
        splash.close();
    }

    public void signinWindow() {

        try {
            FXMLLoader loader = new FXMLLoader(Signin.class.getResource("/view/login.fxml"));
            AnchorPane pane = loader.load();
            Login controller = loader.getController();
            stageprim = new Stage();
            controller.Main(this, stageprim);
            Scene scene = new Scene(pane);
            stageprim.setTitle("Login Screen");
            scene.getStylesheets().add(Signin.class.getResource("/style/StyleSheet.css").toExternalForm());
            stageprim.setResizable(false);
            stageprim.setScene(scene);
            stageprim.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void closeLogin() {
        stageprim.close();
    }

    public void salesWindow(int user_id)
    {

        try
        {
            FXMLLoader loader = new FXMLLoader(Signin.class.getResource("/view/Salesdashboard.fxml"));
            AnchorPane pane = loader.load();
            Sales controller =  loader.getController();
            sales = new Stage();
            controller.Main(this, sales);
            controller.showid(user_id);
            Scene scene = new Scene(pane);
            sales.setTitle("Sales Dashboard");
            scene.getStylesheets().add(Signin.class.getResource("/style/StyleSheet.css").toExternalForm());
            sales.setResizable(false);
            sales.setScene(scene);
            sales.show();

        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void subWindow() {

        try
        {
            FXMLLoader loader = new FXMLLoader(Signin.class.getResource("/view/subcategories.fxml"));
            AnchorPane pane = loader.load();
            Subcategories controller =  loader.getController();
            subcat = new Stage();
            controller.Main(this, subcat);
            Scene scene = new Scene(pane);
            subcat.setTitle("Subcategories");
            scene.getStylesheets().add(Signin.class.getResource("/style/StyleSheet.css").toExternalForm());
            subcat.setResizable(false);
            subcat.setScene(scene);
            subcat.show();

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }



}
