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
    @FXML
    private  javafx.scene.control.ComboBox startScreenComBox;
    //AdminLogin CLASS variables >>>>>>>>>> for logingin
    @FXML
    private Label statusBarForLogin;
    @FXML
    private javafx.scene.control.TextField log;
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
    private javafx.scene.control.TextField addBrandTxtField;
    @FXML
    private javafx.scene.control.TextField addModelTxtField;
    @FXML
    private javafx.scene.control.TextField addPriceTxtField;
    @FXML
    private javafx.scene.control.ComboBox addBedComBox;
    @FXML
    private javafx.scene.control.TextField addMileageTxtField;
    @FXML
    private javafx.scene.control.TextField finalPrice;
    @FXML
    private javafx.scene.control.Label statusBarForSuccessesfullyAddingMH;
    //ReserveMH CLASS variables >>>>>>>>>> for adding extra item
    @FXML
    private Label totalItems;
    @FXML
    private javafx.scene.control.TextArea extraItemsTxtArea;
    @FXML
    private  javafx.scene.control.ComboBox listOfExtraItemsComBox;
    //ReserveMH CLASS variables >>>>>>>>>> for finding which season it is
    @FXML
    private  javafx.scene.control.ComboBox reserverCombo;
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
    //MotorhomeModification CLASS variables >>>>>>>>>> for updating existing values for database
    @FXML
    private ComboBox listOfMHforUpdating;
    @FXML
    private javafx.scene.control.TextField updateID;
    @FXML
    private javafx.scene.control.TextField updateMark;
    @FXML
    private javafx.scene.control.TextField updateModel;
    @FXML
    private javafx.scene.control.TextField updatePrice;
    @FXML
    private ComboBox updateBeds;
    @FXML
    private ComboBox updateAvailability;
    //Repair CLASS variables >>>>>>>>>> for loading up the motorhome for the mechanic
    @FXML
    private javafx.scene.control.ComboBox repairListOFMHforMechanic;
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
    //check boxes
    @FXML
    private javafx.scene.control.CheckBox oilCheck;
    @FXML
    private javafx.scene.control.CheckBox fuelCheck;
    @FXML
    private javafx.scene.control.CheckBox waterCheck;
    @FXML
    private javafx.scene.control.CheckBox cleanCheck;
    @FXML
    private javafx.scene.control.CheckBox repairNeededCheck;


    //Class instences
    MotorhomeModification motorhomeModification = new MotorhomeModification();
    AdminLogin adminLogin = new AdminLogin();
    Repair repair = new Repair();
    UltimateComboboxRefresher ultCBref = new UltimateComboboxRefresher();

    @FXML
    public void LoginAction(ActionEvent actionEvent) {

        String LoginInput = log.getText();
        String PassInput = pas.getText();

        boolean status = adminLogin.LoginStatus(LoginInput, PassInput);
        System.out.println(status+ " aa");
        if (status == true) {
            statusBarForLogin.setText("You are logged in");
            userLoggedIn = status;
            //Enabale all the tabs after the login is correct
            startSceenPan.setDisable(false);
            motorhomeModPan.setDisable(false);
            reservePan.setDisable(false);
            customerOrderFunctioPan.setDisable(false);
            repairPan.setDisable(false);
        } else {
            statusBarForLogin.setText("Wrong password or username");
            userLoggedIn = status;
        }
    }

    @FXML
    public void motorHomeModsAddingMH(ActionEvent actionEvent){
        //add a motorhome to the data base
        String theBrand   = addBrandTxtField.getText();
        String theModel   = addModelTxtField.getText();
        String thePrice   = addPriceTxtField.getText(); /// Needs a fix to be a double not a fcking string
        String theBed     = (String) addBedComBox.getValue();
        String theMileage = addMileageTxtField.getText();

        motorhomeModification.addMotorHome(theBrand, theModel, thePrice, theBed,theMileage);
        statusBarForSuccessesfullyAddingMH.setText("Status: Congratulations! "+theBrand+ " " +theModel+ " has been saved!");
    }
    @FXML
    public void localValueComboBoxes(MouseEvent mouseEvent){
        //make the beds only available from 2 to 6 in the combo box
        ObservableList<String> beds = FXCollections.observableArrayList();
        beds.addAll("2","3","4","5","6");
        addBedComBox.setItems(beds);
        updateBeds.setItems(beds);
        //make mh unaivalable or available
        ObservableList<String> availability = FXCollections.observableArrayList();
        availability.addAll("Available","Unavailable");
        updateAvailability.setItems(availability);
        //select the extra items you want
        ObservableList<String> extraItems = FXCollections.observableArrayList();
        extraItems.addAll("Baby seat", "Bike rack", "Table");
        listOfExtraItemsComBox.setItems(extraItems);
    }
    @FXML
    public void motorHomeModsUpdatingMH(ActionEvent actionEvent){
        //updates existing motorhomes
        String beds = (String) updateBeds.getValue();
        String availability = (String) updateAvailability.getValue();

        String ID = updateID.getText();
        String brand = updateMark.getText();
        String model = updateModel.getText();
        String price = updatePrice.getText();

        motorhomeModification.updatingMotorHomne(ID,brand,model,price,beds,availability);
    }

    @FXML
    public void motorHomeModsDeleteMH(ActionEvent actionEvent){
        String deleteID = updateID.getText();
        motorhomeModification.DeleteMotorHome(deleteID);
    }

    @FXML
    public void motorHomeModLoad(ActionEvent actionEvent){
        String Aidy = updateID.getText();
        updateAvailability.setValue(motorhomeModification.Load(Aidy).get(0));
        updateMark.setText(motorhomeModification.Load(Aidy).get(1));
        updateModel.setText(motorhomeModification.Load(Aidy).get(2));
        updatePrice.setText(motorhomeModification.Load(Aidy).get(3));
        updateBeds.setValue(motorhomeModification.Load(Aidy).get(4));
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
        String sizes = Integer.toString(ReserveMH.items.size());
        totalItems.setText(sizes);
        System.out.println(sizes);
    }

    @FXML
    public void setItemsToNull(ActionEvent actionEvent){
        ReserveMH.items.clear();
        extraItemsTxtArea.setText("");
       String sizes = Integer.toString(ReserveMH.items.size());
       totalItems.setText(sizes);
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
        String sizes = Integer.toString(ReserveMH.items.size());
        totalItems.setText(sizes);
    }

    public void setAllValuesFromComboboxToTextField(ActionEvent actionEvent){

        String valuesFromComBox = (String) listOfMHforUpdating.getValue();
        String ID = valuesFromComBox.substring(0,1);
        System.out.println(valuesFromComBox);

        updateAvailability.setValue(motorhomeModification.Load(ID).get(0));
        updateMark.setText(motorhomeModification.Load(ID).get(1));
        updateModel.setText(motorhomeModification.Load(ID).get(2));
        updatePrice.setText(motorhomeModification.Load(ID).get(3));
        updateBeds.setValue(motorhomeModification.Load(ID).get(4));
        updateID.setText(ID);
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
            int seasonPercentage =ReserveMH.season(whichSeason.getText());
            String price = (String) reserverCombo.getValue();
            int extraItemPrice = ReserveMH.items.size() *10; //price for extra shiy
            String[] arr = price.split(" ");
            int motorhomePrice = Integer.parseInt(arr[5]);
            int seasonPrice= ((motorhomePrice) / 100 * seasonPercentage ) +extraItemPrice + motorhomePrice;
            finalPrice.setText(Integer.toString(seasonPrice));//bybis
            System.out.println(seasonPercentage);
        }
        //----------------------------------------------------------------
    }
    //FROM REPAIR CLASS ###############################
    @FXML
    public void loadActionForRepair(MouseEvent mouseEvent){
        //loads all the information into the combobox

        repairListOFMHforMechanic.setItems(repair.refreshItForTheMechanic());
    }
    //FROM REPAIR CLASS ###############################
    @FXML
    public void setAllValuesForMechanic(ActionEvent actionEvent){
        //set the text into the text fields from the combo box directly
        String valuesFromComBox = (String) repairListOFMHforMechanic.getValue();
        String ID = valuesFromComBox.substring(0,1);
        System.out.println(valuesFromComBox);

        idForMechanic.setText(ID);
        brandForMechanic.setText(repair.loadItUpForTheMechanic(ID).get(0));
        modelForMechanic.setText(repair.loadItUpForTheMechanic(ID).get(1));
        bedForMechanic.setText(repair.loadItUpForTheMechanic(ID).get(2));
        fuelForMechanic.setText(repair.loadItUpForTheMechanic(ID).get(3)+ " [L]");
        mileageForMechanic.setText(repair.loadItUpForTheMechanic(ID).get(4)+ " [km]");
    }
    //FROM REPAIR CLASS ###############################
    @FXML
    public void checkUpForMechanic(ActionEvent actionEvent) {

        String  repairMHid       = idForMechanic.getText();
        boolean oilSituation     = oilCheck.isSelected();
        boolean fuelSituation    = fuelCheck.isSelected();
        boolean waterSituation   = waterCheck.isSelected();
        boolean cleanSituation   = cleanCheck.isSelected();
        boolean repairsSituation = repairNeededCheck.isSelected();

        repair.serviceComplete(oilSituation,fuelSituation,waterSituation,cleanSituation,repairsSituation, repairMHid);

        oilCheck.setSelected(false);
        fuelCheck.setSelected(false);
        waterCheck.setSelected(false);
        cleanCheck.setSelected(false);
        repairNeededCheck.setSelected(false);
    }
    @FXML
    public  void saveOrder(ActionEvent actionEvent){
        System.out.println("SMD");
    }
    @FXML
    public  void ultimateLoadMotorhomeListForComboBox(MouseEvent mouseEvent){

        startScreenComBox.setItems(ultCBref.myUltimateRefresh("SS"));
        listOfMHforUpdating.setItems(ultCBref.myUltimateRefresh("Update"));
        repairListOFMHforMechanic.setItems(ultCBref.myUltimateRefresh("Repair"));
        reserverCombo.setItems(ultCBref.myUltimateRefresh("Update"));

    }
}
