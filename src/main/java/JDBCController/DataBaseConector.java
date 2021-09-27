/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JDBCController;

import sistemaceb.form.Global;

import java.sql.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author escal
 */
public class DataBaseConector {
    
    private Statement myStatment;
    private Connection myCon;
    private boolean isChecked;

    public DataBaseConector(){
        makeConection();
    }

    public boolean checkConection(){
        if(!isChecked){
            isChecked = true;
            activateTimer();
            if (!makeTestConsult()){
                makeConection();
                return false;
            }
        }
        return true;
    }

    private void activateTimer(){
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                isChecked = false;
            }
        },600000);

    }


    private boolean makeTestConsult(){

        System.out.println("makeConsult");
        try {
            DataBaseConsulter consulter = new DataBaseConsulter("viewsspecs.tags");
            consulter.bringTable();
            return true;
        } catch(Exception e){
            return false;
        }
    }

    public void endConection(){
        try {
            myCon.close();
        } catch (SQLException ex) {
            Logger.getLogger(DataBaseConector.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public Statement getMyStatment() {
        checkConection();
        return myStatment;
    }

    public Connection getMyCon() {
        return myCon;
    }

    private void makeConection(){
        String user = "remote";
        String pass = "Kinareth41ñ$";
        String connSpecs = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        String URL = "jdbc:mysql://147.182.129.199:3306/";

        try {
             myCon =
                     DriverManager.getConnection(
                        URL + "cebdatabase" + connSpecs,
                             user,
                             pass);
            myStatment = myCon.createStatement();

        } catch (SQLException ex) {
            Logger.getLogger(DataBaseConector.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    

    

}
