package controller;

import Database.DbHelper;
import Main.Signin;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import tray.notification.TrayNotification;

import java.net.URL;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.ResourceBundle;
import controller.OrderList;

/**
 * Created by Aggrey on 10/26/2017.
 */
public class Sales implements Initializable {


    private static Statement stmt = null;
    private static PreparedStatement stat;
    ObservableList<OrderList> data = FXCollections.observableArrayList();
    Signin su;
    Stage stage;
    @FXML
    private JFXButton user_details;
    @FXML
    private TextField customer;
    @FXML
    private TextField disc_g;
    @FXML
    private TextField disc_a;
    @FXML
    private Label total1;
    @FXML
    private JFXButton cashFx;
    @FXML
    private TextField amount_r;
    @FXML
    private TextField prod_name;
    @FXML
    private TextField price;
    @FXML
    private TextField qty;
    @FXML
    private JFXButton Addup;
    @FXML
    private JFXButton Adddown;
    @FXML
    private JFXButton updateFx;
    @FXML
    private JFXButton deleteFx;
    @FXML
    private FontAwesomeIconView avatar;
    @FXML
    private Label adminname;
    @FXML
    private Label notifications;
    @FXML
    private Label alerts;
    @FXML
    private ImageView logo;
    @FXML
    private AnchorPane salesAnchor;
    @FXML
    private TextField barcode;
    @FXML
    private JFXButton search;
    @FXML
    private JFXButton grid;
    @FXML
    private Label sub_total;
    @FXML
    private Label tax;
    @FXML
    private Label label_totalFx;

    @FXML
    private ScrollPane scrollFx;
    @FXML
    private TilePane tilepanefx;
    private DbHelper handler;
    private Connection conn;
    @FXML
    private TableView<OrderList> table_orders;

    @FXML
    private TableColumn<OrderList, Integer> itemidFx;

    @FXML
    private TableColumn<OrderList, String> itemnameFx;

    @FXML
    private TableColumn<OrderList, String> quantityFx;

    @FXML
    private TableColumn<OrderList, String> totalFx;

    @FXML
    private TableColumn<OrderList, String> pricefx;
    private int mClientId;

    @FXML
    void delete(ActionEvent event) {

    }

    @FXML
    void print(ActionEvent event) {

    }

    @FXML
    void update(ActionEvent event) {

    }

    public void Main(Signin su, Stage stage) {
        this.stage = stage;
        this.su = su;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        handler = new DbHelper();

         loadTableColumn();
        loadTable();
        //showid(int user_id);
        setCellValuefromTableToTextfield();
        getTotals();

    }


    private void loadTableColumn() {

        itemidFx.setCellValueFactory(new PropertyValueFactory<OrderList, Integer>("itemid"));
        itemnameFx.setCellValueFactory(new PropertyValueFactory<OrderList, String>("itemname"));
        quantityFx.setCellValueFactory(new PropertyValueFactory<OrderList, String>("quantity"));
        pricefx.setCellValueFactory(new PropertyValueFactory<OrderList, String>("price"));
        totalFx.setCellValueFactory(new PropertyValueFactory<OrderList, String>("total"));

    }

    private void loadTable() {

        table_orders.refresh();
        data.clear();
        try {
            conn = handler.getConnection();
            String query = "SELECT * FROM orders2 GROUP BY item_id";
            stat = conn.prepareStatement(query);
            ResultSet rs = stat.executeQuery(query);
            while (rs.next()) {
                int item_id = rs.getInt("item_id");
                String price = rs.getString("price");
                String queryTwo = "SELECT item_name FROM items WHERE item_id = (?) ";

                stat = conn.prepareStatement(queryTwo);
                stat.setInt(1, item_id);

                ResultSet re = stat.executeQuery();

                String total = "SELECT sum(price) FROM orders2 where status='2' AND item_id=(?) GROUP BY item_id";
                stat = conn.prepareStatement(total);
                stat.setInt(1, item_id);

                ResultSet result = stat.executeQuery();
                result.next();
                String sum = result.getString(1);
                System.out.println(sum);
                //value = Double.parseDouble(sum);

                String getNumber = "SELECT COUNT(*) FROM orders2 WHERE item_id = "+item_id+" ";
                result = stat.executeQuery(getNumber);
                result.next();

                String rowCount = result.getString(1);
                String quantity = rowCount;

                System.out.println("item id count" +quantity);

                re.first();
                data.add(new OrderList(
                        rs.getInt("item_id"),
                        re.getString("item_name"),quantity,price,sum
                ));
            }
            table_orders.setItems(data);            
            clearText();
            getTotals();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void clearText() {
        barcode.clear();
        barcode.requestFocus();
    }


    @FXML
    void Grid(ActionEvent event) {
        //loadItems();
        loadProducts();
    }

    @FXML
    void SaleB(ActionEvent event) {
        tilepanefx.getChildren().clear();
    }

    public void performActionAsync() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //loadItems();
            }
        }).start();
    }

    @FXML
    void searchitem(KeyEvent event) throws SQLException {
        //conn = handler.getConnection();
        String getItem = "SELECT * FROM items WHERE bar_code = (?)";

        stat = conn.prepareStatement(getItem);
        stat.setString(1, barcode.getText());


        ResultSet rs = stat.executeQuery();

        if (rs.next()) {
            int item_id = rs.getInt("item_id");
            String item_name = rs.getString("item_name");

            loadPrice(item_id);
            customer.setText(item_name);

            //System.out.print(item_name);
        }/*else if(!rs.next())
        {
            TrayNotification tn = new TrayNotification("Fail", "Item not Found ", NotificationType.ERROR);
            tn.setAnimationType(AnimationType.POPUP);
            tn.showAndDismiss(Duration.seconds(2));
            clearText();
        }*/
    }

    private void loadPrice(int item_id) {
        try {

            //conn = handler.getConnection();
            String price = "SELECT * FROM prices WHERE item_id = (?)";

            stat = conn.prepareStatement(price);
            stat.setInt(1, item_id);

            ResultSet rs = stat.executeQuery();

            while (rs.next()) {
                //Retrieve by column name
                int price_id = rs.getInt("price_id");
                String shop = rs.getString("shop");


                //Display values
                System.out.println("ID: " + price_id);
                System.out.println("shop price: " + shop); //price
                System.out.println("staff_id " + mClientId); //staff
                System.out.println("item_id " + item_id); //item_id

                insertorder(item_id, mClientId, shop);

                /*String lastorder = "SELECT  *  FROM order WHERE pay_status = 1 AND display_status= 1";

                ResultSet result = stat.executeQuery(lastorder);

                while (result.next())
                {
                    int order_id = rs.getInt("id");
                    int order_no = rs.getInt("order_no");

                    int max_order = order_no + 1;

                    System.out.println(max_order);
                }*/

            }


        } catch (Exception ex) {
            System.out.println("problem occured");
            ex.printStackTrace();
        }
    }

    private void setCellValuefromTableToTextfield()
    {
        table_orders.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                OrderList list = table_orders.getItems().get(table_orders.getSelectionModel().getSelectedIndex());
                prod_name.setText(list.getItemname());
                price.setText(list.getPrice());
                qty.setText(list.getQuantity());
                prod_name.setDisable(true);
                price.setDisable(true);
                qty.setDisable(true);

            }
        });

    }

    private void insertorder(int item_id, int mClientId, String shop) {

        Calendar cal = Calendar.getInstance();
        GetTimeData getTimeData = new GetTimeData(cal);
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        System.out.println(getTimeData.getSysDate());
        System.out.println(getTimeData.getHour());

        String date = getTimeData.getSysDate();
        String time = getTimeData.getHour();
        int order_no = 0;

        try {
            String Insertorder = "INSERT INTO orders2(item_id,order_no,staff_id ,price ,day ) " +
                    "VALUES (?,?,?,?,?)";
            stat = conn.prepareStatement(Insertorder);

            stat.setInt(1, item_id);
            stat.setInt(2, order_no);
            stat.setInt(3, mClientId);
            stat.setString(4, shop);
            stat.setString(5, date);
            //stat.setString(6 ,time);
            stat.executeUpdate();
            table_orders.refresh();
            loadTable();


        } catch (Exception e) {
            e.printStackTrace();
        }



    }


    public void loadProducts() {

        try {

            // Adding TilePane
            /*TilePane tilePane = new TilePane();
            tilePane.setPadding(new Insets(10, 10, 10, 10));
            tilePane.setVgap(4);
            tilePane.setHgap(4);

            ToggleButton btn = new ToggleButton();*/

            Button button = new Button();
            StackPane rootStackPane = new StackPane();
            JFXDialog dialog = new JFXDialog();
            dialog.setContent(new Label("Content"));
            button.setOnAction((action) -> dialog.show(rootStackPane));

            final Random rng = new Random();
            System.out.println(rng);

            tilepanefx.setPadding(new Insets(10, 10, 10, 10));
            tilepanefx.setVgap(4);
            tilepanefx.setHgap(5);
            Button btn = new Button();


            conn = handler.getConnection();
            String items = "SELECT  *  FROM items ";
            ResultSet rs = stat.executeQuery(items);
            int rowCount = 0;
            if (rs.last()) {
                rowCount = rs.getRow();
                rs.beforeFirst();
            }

            System.out.print(rowCount);


            //STEP 5: Extract data from result set
            while (rs.next()) {
                //Retrieve by column name
                String item_no = rs.getString("item_id");
                String item_name = rs.getString("item_name");

                String style = String.format(" -fx-border-color: rgb(%d, %d, %d); -fx-border-width: 4 0 0 0; ",
                        rng.nextInt(256),
                        rng.nextInt(256),
                        rng.nextInt(256));
                btn.setStyle(style);

                System.out.println(style);

                btn = new Button(item_name);
                btn.setStyle("-fx-font-size:12px;-fx-effect:  dropshadow(gaussian, rgb(0.0, 0.0, 0.0 ,0.15), 6.0, 0.7, 0.0,1.5); -fx-background-color: #E1E6DF; -fx-text-fill:#000100; -fx-font-family: 'RobotoDraft', 'Roboto', 'sans-serif';");
                btn.setPrefSize(118, 100);

               /* btn = new ToggleButton(item_name1);

                btn.setContentDisplay(ContentDisplay.TOP);
                //btn.setGraphic(imageview);

                btn.setStyle("-fx-base:#4E3C50;" +
                        " -fx-border-color:blue  white green white; " +
                        "" +
                        "-fx-border-width: 5px 4px 5px 6px; -fx-border-radius: 10px 0px 10px 0px; ");


                btn.setPrefSize(160, 180);*/
                tilepanefx.getChildren().addAll(btn);
                scrollFx.setContent(tilepanefx);
            }


            rs.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        }//end try
    }

    public void showid(int user_id) {

        mClientId = user_id;
        //System.out.println(user_id);
        //label to set admin id
        total1.setText(String.valueOf(user_id));

        try {

            conn = handler.getConnection();
            String admin = "SELECT * FROM users WHERE staff_id = (?)";

            stat = conn.prepareStatement(admin);
            stat.setInt(1, user_id);

            ResultSet rs = stat.executeQuery();

            while (rs.next()) {
                //Retrieve by column name
                int id = rs.getInt("staff_id");
                String staff_name = rs.getString("staff_name");
                String username = rs.getString("username");

                //Display values
                System.out.print("ID: " + id);
                System.out.print(", staff name: " + staff_name);
                System.out.print(", username: " + username);
                adminname.setText(staff_name);

            }

        } catch (Exception ex) {
            System.out.println("problem occured");
            ex.printStackTrace();
        }
    }

    private void getTotals()
    {
        try
        {
            String total = "SELECT sum(price) FROM orders2 where status='2' AND disp_status='2' ";
            stat = conn.prepareStatement(total);
            ResultSet result = stat.executeQuery();
            result.next();
            String sum = result.getString(1);
            System.out.println(sum);
            sub_total.setText(sum);
            label_totalFx.setText(sum);


        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void getAmountDue1()
    {

        table_orders.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                OrderList list = table_orders.getItems().get(table_orders.getSelectionModel().getSelectedIndex());
                prod_name.setText(list.getItemname());
                price.setText(list.getPrice());
                qty.setText(list.getQuantity());
                prod_name.setDisable(true);
                price.setDisable(true);
                qty.setDisable(true);

            }
        });
    }

    @FXML
    void takeAmount(KeyEvent event) {

        Double total23 = Double.valueOf(label_totalFx.getText());
        Double given23 = Double.valueOf(amount_r.getText());

        double Due = total23 - given23;

        amount_r.setText(String.valueOf(Due));

    }


    private void getAmountDue()
    {


    }


}
