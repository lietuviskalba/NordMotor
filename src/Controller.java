/**
 * Created by Kompas on 2017-05-11.
 */
import javafx.fxml.FXML;
import java.awt.*;
import java.awt.Button;
import java.awt.TextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.TextFlow;
import javafx.scene.control.Label;
import javafx.scene.text.Font;



public class Controller {

   private boolean userLoggedIn = false ;
    //AdminLogin CLASS variables >>>>>>>>>> for logingin
    @FXML
    private Label statusBar;
    @FXML
    public javafx.scene.control.TextField log;
    @FXML
    private javafx.scene.control.TextField pas;
    @FXML
    private javafx.scene.control.TitledPane startSceenPan;
    @FXML
    private javafx.scene.control.TitledPane motorhomeModPan;
    @FXML
    private javafx.scene.control.TitledPane reservePan;
    @FXML
    private javafx.scene.control.TitledPane customerOrderFunctioPan;
    @FXML
    private javafx.scene.control.TitledPane repairPan;
    //MotorhomeModification CLASS variables >>>>>>>>>> for adding a MH
    @FXML
    private javafx.scene.control.TextField brandTxtField;
    @FXML
    private javafx.scene.control.TextField modelTxtField;
    @FXML
    private javafx.scene.control.TextField priceTxtField;
    @FXML
    private javafx.scene.control.TextField bedTxtField;
    @FXML
    private javafx.scene.control.Label statusBarForSuccessesfullyAddingMH;
    //MotorhomeModification CLASS variables >>>>>>>>>> for updating
    @FXML
    private ComboBox ModifyMHcombo;
    //ReserveMH CLASS variables >>>>>>>>>> for adding extra item
    @FXML
    private javafx.scene.control.TextField extraItemsTxtField;
    @FXML
    private javafx.scene.control.TextArea extraItemsTxtArea;
    @FXML
    private  javafx.scene.control.ComboBox listOfExtraItemsComBox;

    @FXML
    private javafx.scene.control.TextField startDateDAYTxtField;
    @FXML
    private javafx.scene.control.TextField startDateMONTHTxtField;
    @FXML
    private javafx.scene.control.TextField endDateDAYTxtField;
    @FXML
    private javafx.scene.control.TextField endDateMONTHTxtField;
    @FXML
    private javafx.scene.control.TextField whichSeason;
    @FXML
    private javafx.scene.control.TextField ModifyMark;
    @FXML
    private javafx.scene.control.TextField ModifyModel;
    @FXML
    private ComboBox ModifyBeds;
    @FXML
    private ComboBox ModifyAvailability;
    @FXML
    public javafx.scene.control.TextField ModifyPrice;
    @FXML
    public javafx.scene.control.TextField ModifyID;
    //Repair CLASS variables >>>>>>>>>> for loading up the motorhome
    @FXML
    private javafx.scene.control.TextField idForMechanic;
    @FXML
    private javafx.scene.control.TextField brandForMechanic;
    @FXML
    private javafx.scene.control.TextField modelForMechanic;
    @FXML
    private javafx.scene.control.TextField bedForMechanic;
    @FXML
    private javafx.scene.control.TextField fuelForMechanic;
    @FXML
    private javafx.scene.control.TextField mileageForMechanic;


    MotorhomeModification motorhomeModification = new MotorhomeModification();//MotorhomeModification Class
    AdminLogin adminLogin = new AdminLogin();//AdminLogin class
    Repair repair = new Repair();

    public void LoginAction(ActionEvent actionEvent) {

        String LoginInput = log.getText();
        String PassInput = pas.getText();

        boolean status = adminLogin.LoginStatus(LoginInput, PassInput);
        System.out.println(status+ " aa");
        if (status == true) {
            statusBar.setText("You are logged in");
            userLoggedIn = status;
            //Enabale all the tabs after the login is correct
            startSceenPan.setDisable(false);
            motorhomeModPan.setDisable(false);
            reservePan.setDisable(false);
            customerOrderFunctioPan.setDisable(false);
            repairPan.setDisable(false);
        } else {
            statusBar.setText("Wrong password or username");
            userLoggedIn = status;
        }
    }
    @FXML
    public void motorHomeModsAddingMH(ActionEvent actionEvent){

        String theBrand = brandTxtField.getText();
        String theModel = modelTxtField.getText();
        String thePrice = priceTxtField.getText(); /// Needs a fix to be a double not a fcking string
        String theBed   = bedTxtField.getText();   /// Needs a fcking fix so its a combobox with ints NOT a string txt field

        MotorhomeModification mm = new MotorhomeModification();
        MotorhomeModification.addMotorHome(theBrand, theModel, thePrice, theBed);
        statusBarForSuccessesfullyAddingMH.setText("Status: Congratulations! "+theBrand+ " " +theModel+ "has been saved!");
    }
    @FXML
    public void motorHomeModsUpdatingMH(ActionEvent actionEvent){
        String beds = (String) ModifyBeds.getValue();
        System.out.println(beds + "beds!!!!!!!!!!");
        String availability = (String) ModifyAvailability.getValue();
        System.out.println(availability + "available!!!!!!!!!!");

        String ID = ModifyID.getText();
        String brand = ModifyMark.getText();
        String model = ModifyModel.getText();
        String price = ModifyPrice.getText();
        //String beds = (String) ModifyBeds.getValue();

                motorhomeModification.updatingMotorHomne(ID,brand,model,price,beds,availability);
        System.out.println("Shit works");
    }

    @FXML
    public void motorHomeModsDeleteMH(ActionEvent actionEvent){
        String deleteID = ModifyID.getText();
        motorhomeModification.DeleteMotorHome(deleteID);;
    }
    @FXML
    public void motorHomeModLoad(ActionEvent actionEvent){
        String Aidy = ModifyID.getText();
        ModifyAvailability.setValue(motorhomeModification.Load(Aidy).get(0));
        ModifyMark.setText(motorhomeModification.Load(Aidy).get(1));
        ModifyModel.setText(motorhomeModification.Load(Aidy).get(2));
        ModifyPrice.setText(motorhomeModification.Load(Aidy).get(3));
        ModifyBeds.setValue(motorhomeModification.Load(Aidy).get(4));


    }
    @FXML
    public void RefreshMH(MouseEvent mouseEvent){
        ModifyMHcombo.setValue(null);
        System.out.println("rehreshing");
        ObservableList<String> list = FXCollections.observableArrayList();
        String listString = "";
        ModifyMHcombo.setItems(motorhomeModification.refresh());
        ObservableList<String> beds = FXCollections.observableArrayList();
        ObservableList<String> availability = FXCollections.observableArrayList();
        beds.addAll("2","4","6","8");
        availability.addAll("Available","Unavailable");
        ModifyAvailability.setItems(availability);
        ModifyBeds.setItems(beds);

    }

    //FROM RESERVEMH CLASS
    @FXML
    public void extraItemCatalogComBox(MouseEvent mouseEvent){
        ObservableList<String> extraItems = FXCollections.observableArrayList();
        extraItems.addAll("Baby seat", "Bike rack", "Table");
        listOfExtraItemsComBox.setItems(extraItems);
    }


    @FXML
    public void addExtaItemAction(ActionEvent actionEvent){

        String item = (String) listOfExtraItemsComBox.getValue();



        String listString = "";

        for (String s : ReserveMH.addExtraShit(item)) {
            listString += s + "\n";
            System.out.println();

        }
        extraItemsTxtArea.setText(listString);

    }

    @FXML
    public void setItemsToNull(ActionEvent actionEvent){
        ReserveMH.items.clear();
        extraItemsTxtArea.setText("");

    }



    @FXML
    public void removeLastExtraItem(ActionEvent actionEvent){
        ReserveMH.items.remove(ReserveMH.items.size()-1);
        extraItemsTxtArea.setText("");
        String listString = "";

        for (String s : ReserveMH.items) {
            listString += s + "\n";
            System.out.println();

        }
        extraItemsTxtArea.setText(listString);

    }

    public void Autistic(ActionEvent actionEvent){


        System.out.println("Screeching");

        String autistic = (String) ModifyMHcombo.getValue();
        String ID = autistic.substring(0,1);
        System.out.println(autistic);
        ModifyAvailability.setPromptText(motorhomeModification.Load(ID).get(0));
        ModifyMark.setText(motorhomeModification.Load(ID).get(1));
        ModifyModel.setText(motorhomeModification.Load(ID).get(2));
        ModifyPrice.setText(motorhomeModification.Load(ID).get(3));
        ModifyBeds.setPromptText(motorhomeModification.Load(ID).get(4));
        ModifyID.setText(ID);


    }
    @FXML
    public  void calculatePriceAction(ActionEvent actionEvent){
        //Checks which season it is--------------------------------------
        if (startDateMONTHTxtField.getText().isEmpty() || startDateDAYTxtField.getText().isEmpty() || endDateMONTHTxtField.getText().isEmpty() || endDateDAYTxtField.getText().isEmpty()){
            System.out.println(" Ble cyka you need normal value");
            whichSeason.setText("Fill all the dates!");
            whichSeason.setFont(Font.font ("Verdana", 17));

        }else{
            int startDay   = Integer.parseInt(startDateDAYTxtField.getText());
            int startMonth = Integer.parseInt(startDateMONTHTxtField.getText());
            int endDay     = Integer.parseInt(endDateDAYTxtField.getText());
            int endMonth   = Integer.parseInt(endDateMONTHTxtField.getText());
            ReserveMH rmh = new ReserveMH();
            whichSeason.setText(rmh.season(startMonth));
        }
        //----------------------------------------------------------------
    }
    @FXML
    public void loadActionForRepair(MouseEvent mouseEvent){
        String idMH = idForMechanic.getText();
        brandForMechanic.setPromptText(repair.loadItUpForTheMechanic(idMH).get(0));
        modelForMechanic.setText(repair.loadItUpForTheMechanic(idMH).get(1));
        bedForMechanic.setText(repair.loadItUpForTheMechanic(idMH).get(2));
        //ModifyPrice.setText(motorhomeModification.Load(idMH).get(3));
    }


}
