/**
 * Created by Mantas_MSI on 5/11/2017.
 */
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import java.awt.*;
import java.awt.List;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class MotorhomeModification {

    public static void addMotorHome(String theBrand, String theModel, String thePrice, String theBed, String theMileage) {//fix this to be a double and int

        System.out.println("Name ->" + theBrand + "<-");

        try {
            Connection con = DBConnection.getConnection();
            Statement stmt = con.createStatement();

            String sql = "INSERT INTO nordic_rv " +
                    "VALUES " +
                    "(NULL, "
                    +"'"+ "Available"  +"'  ,"
                    +"'"+ theBrand     +"'  ,"
                    +"'"+ theModel     +"'  ,"
                    +"'"+ thePrice     +"'  ,"
                    +"'"+ theBed       +"'  ,"
                    +100               +"   ,"
                    +"'"+ theMileage   +"'   "
                    +");                     ";

            System.out.println(sql);
            stmt.executeUpdate(sql);
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updatingMotorHomne(String ID,String brand,String model,String price, String beds, String availability) {


        try {
            Connection con = DBConnection.getConnection();
            Statement stmt = con.createStatement();

            String sql = "UPDATE nordic_rv " +
                         "SET "         +
                         "status    = " +"'" + availability +"'" + ", " +
                         "mark      = " +"'" + brand        +"'" + ", " +
                         "model     = " +"'" + model        +"'" + ", " +
                         "beds      = " +"'" + beds         +"'" + ", " +
                         "price     = " +"'" + price        +"'" + "  " +
                         "WHERE rvID= " + ID +               ";"        ;

            System.out.println(sql);
            stmt.executeUpdate(sql);
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void DeleteMotorHome(String ID) {
        try {
            String sql = "DELETE FROM `nordic_rv`   " +
                         "WHERE `nordic_rv`.`rvID` =" + ID;

            System.out.println(sql);

            Connection con = DBConnection.getConnection();
            Statement stmt = con.createStatement();
            stmt.executeUpdate(sql);
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public java.util.List<String> Load(String ID) {

      java.util.List<String> MH = new ArrayList<String>();

        try {
            Connection con = DBConnection.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT      " +
                                                 "`status`  , " +
                                                 "`mark`    , " +
                                                 "`model`   , " +
                                                 "`price`   , " +
                                                 "`beds`      " +
                                                 "FROM `nordic_rv` " +
                                                 "WHERE `rvID`  =  " + ID);

            while (rs.next()) {

                MH.add(rs.getString(1));
                MH.add(rs.getString(2));
                MH.add(rs.getString(3));
                MH.add(rs.getString(4));
                MH.add(rs.getString(5));
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return MH;
    }
}
