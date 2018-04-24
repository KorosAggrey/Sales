package controller;

import Database.DbHelper;
import Main.Signin;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by Aggrey on 10/23/2017.
 */
public class Login implements Initializable {

    @FXML
    private AnchorPane loginAnchor;

    @FXML
    private JFXTextField usernameFx;

    @FXML
    private JFXPasswordField passwordFX;

    @FXML
    void Register(ActionEvent event) {

    }



    private  DbHelper handler;
    private Connection conn;

    Signin su;
    Stage stage;

    public  void Main( Signin su,Stage stage){
        this.stage=stage;
        this.su=su;
    }

    @FXML
    void Login(ActionEvent event) throws SQLException {

        String username = usernameFx.getText();
        String password = passwordFX.getText();

        Boolean flag1 = username.isEmpty() || password.isEmpty();
        if (flag1) {

            TrayNotification tn = new TrayNotification("Fail", "Fill all the fields ", NotificationType.WARNING);
            tn.setAnimationType(AnimationType.POPUP);
            tn.showAndDismiss(Duration.seconds(2));
          }else{
            String getAdmin = "SELECT * FROM users WHERE username = " + "'"
                    +username+"'" +" AND password = "+"'" +password +"'";
            conn = handler.getConnection();
            PreparedStatement ps = conn.prepareStatement(getAdmin);
            ResultSet result = ps.executeQuery();

            if(result.next())
            {
                if(result.getString("username") !=null && result.getString("password") != null)
                {
                    String  Username = result.getString("username");
                    System.out.println( "Username = " + Username );
                    String Password = result.getString("password");
                    System.out.println("Password = " + Password);
                    int user_id = result.getInt("staff_id");
                    System.out.println("Staff id = " +user_id);

                    TrayNotification tn = new TrayNotification("Success", "Successfully logged in ", NotificationType.SUCCESS);
                    tn.setAnimationType(AnimationType.POPUP);
                    tn.showAndDismiss(Duration.seconds(2));
                    //Sales controller = loader.getController();
                    //controller.showid(user_id);

                     su.closeLogin();
                     su.salesWindow(user_id);
                    //btnLogin.getScene().getWindow().hide();
                }
            }
            else if (!result.next())
            {
                //showError("username or password is invalid ");
                TrayNotification tn = new TrayNotification("Fail", "Wrong Username/Password ", NotificationType.ERROR);
                tn.setAnimationType(AnimationType.POPUP);
                tn.showAndDismiss(Duration.seconds(2));
            }


        }



    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        handler = new DbHelper();
    }
}
