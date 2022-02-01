/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JDBCController;

import sistemaceb.form.Global;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
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
    private Timer timer;

    public boolean checkConection(){
        boolean wasConn = isChecked;
        if(!isChecked){
            reConect();
            activateTimer();
        }

        return wasConn;
    }

    private void activateTimer(){
        isChecked = true;
        if (timer != null)
            timer.cancel();

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                isChecked = false;
            }
        },60000);

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

    public void reConect(){
        if (myCon != null)
            endConection();

        makeConection();
    }

    private void makeConection(){

        String connSpecs = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        String URL = "jdbc:mysql:// " + DBSTate.serverDir + "/";
        myCon = null;
        try {
             myCon =
                     DriverManager.getConnection(
                        URL + DBSTate.currentDatabase + connSpecs,
                             Global.conectionData.user,
                             Global.conectionData.password);
            myStatment = myCon.createStatement();

        } catch (SQLException ex) {
            Logger.getLogger(DataBaseConector.class.getName()).log(Level.SEVERE, null, ex);
        }

        activateTimer();
        
    }
}
