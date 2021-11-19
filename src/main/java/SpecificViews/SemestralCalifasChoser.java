package SpecificViews;

import JDBCController.DataBaseConsulter;
import JDBCController.Table;
import JDBCController.ViewSpecs;
import JDBCController.dataType;
import sistemaceb.FormResponseManager;
import sistemaceb.form.Formulario;
import sistemaceb.form.HorizontalFormPanel;
import sistemaceb.form.Input;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SemestralCalifasChoser extends  HorizontalFormPanel{

    ArrayList<String> materiasKeys;
    ArrayList<String> materiasNombres;

    ArrayList<String> defaultConditions;
    ArrayList<String> defaultValues;

    public SemestralCalifasChoser(Table materias,ArrayList<String> cond,ArrayList<String> values){
        super();
        defaultConditions = cond;
        defaultValues = values;
        materiasKeys = new ArrayList<>(materias.getColumn(0));
        materiasNombres = new ArrayList<>(materias.getColumn(1));
        addLateral(getLateralLabel());
        deployInput();
        addResponseManager();

    }

    private void addResponseManager(){
        addDataManager(new FormResponseManager() {
            @Override
            public void manageData(Formulario form) {
                addValues(form.getData());
            }
        });
    }

    private void addValues(Map<String,String> response){

        for(Map.Entry<String,String> entry:response.entrySet()){

            String maeriaKey = materiasKeys.get(materiasNombres.indexOf(entry.getKey()));

            if(!entry.getValue().isEmpty()) {
                addValue(maeriaKey, entry.getValue());
            }
            else
                setEmpty(maeriaKey);
        }

    }

    public void setEmpty(String materiaKey){
        Table response = registerExists(materiaKey);
        if(!response.isEmpty()){
            String califa = response.getUniqueValue();
            update("NULL",califa);
        }

    }


    private void addValue(String materiaKey,String califa){
        Table res = registerExists(materiaKey);
        String currentKey = getKey(materiaKey);

        if (res.isEmpty())
            insertValues(califa,currentKey);
        else
            update(califa,currentKey);

    }
    public void insertValues(String califa,String califaClave) {
        ArrayList<String> insertColA= new ArrayList<>();
            insertColA.add("calificacion_clave");
            insertColA.add("calificacion");

        ArrayList<String> insertValA= new ArrayList<>();
            insertValA.add(califaClave);
            insertValA.add(califa);

        try {
            new ViewSpecs("calificaciones_semestrales").getUpdater().insert(insertColA,insertValA);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

    private void update(String newValue,String currentKey){
        ArrayList<String> insertColA= new ArrayList<>();
            insertColA.add("calificacion");

        ArrayList<String> insertValA= new ArrayList<>();
            insertValA.add(newValue);

        ArrayList<String> insertiveCols = new ArrayList<>();
            insertiveCols.add("calificacion_clave");

        ArrayList<String> insertiveValues = new ArrayList<>();
            insertiveValues.add(currentKey);

        try {
            new ViewSpecs("calificaciones_semestrales").getUpdater().update(insertColA,insertValA,insertiveCols,insertiveValues);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    private void deployInput(){
        int size = materiasKeys.size();
        for (int i = 0; i < size; i++){
            Input in = (Input) addInput(materiasNombres.get(i), dataType.FLOAT).
                    setRequired(false);
            Table currentCalifa = registerExists(materiasKeys.get(i));
            if (!currentCalifa.isEmpty())
                in.setDefaultValue(currentCalifa.getColumn("calificacion").get(0));
        }
    }



    private JLabel getLateralLabel(){
        JLabel horaLabel = new JLabel("Semestrales");
            horaLabel.setFont(new Font("arial", Font.PLAIN, 20));
            horaLabel.setBorder(new EmptyBorder(0,10,0,10));

        return horaLabel;

    }

    private String getKey(String materiaClave){

        DataBaseConsulter cons = new DataBaseConsulter("calificaciones");

        String[] colsToBring = new String[]{"calificacion_clave"};

        List<String> cols = combinedConditions();
        List<String> val = combinedValues(materiaClave);

        String key = cons.bringTable(colsToBring,cols.toArray(new String[cols.size()]),val.toArray(new String[val.size()])).getUniqueValue();

        return key;
    }

    private Table registerExists(String materia){
        DataBaseConsulter consulter = new DataBaseConsulter("calificaciones_semestrales");

        String[] cond = new String[]{"calificacion_clave"};

        String key = getKey(materia);

        String[] val = new String[]{key};

        if(key == null)
            return new Table(new ArrayList<>(),new ArrayList<>());
        else
            return consulter.bringTable(cond,val);

    }

    private ArrayList<String> combinedConditions(){
        ArrayList<String> cond = new ArrayList<>();
            cond.add("materia");
            cond.addAll(defaultConditions);
        return cond;
    }

    private ArrayList<String> combinedValues(String materia){
        ArrayList<String> cond = new ArrayList<>();
        cond.add(materia);
        cond.addAll(defaultValues);
        return cond;
    }


}

