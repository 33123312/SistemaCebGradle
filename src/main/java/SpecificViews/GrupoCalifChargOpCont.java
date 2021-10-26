package SpecificViews;

import JDBCController.DataBaseConsulter;
import JDBCController.Table;
import JDBCController.TableRegister;
import JDBCController.ViewSpecs;
import sistemaceb.form.Global;

import java.sql.SQLException;
import java.util.*;

public class GrupoCalifChargOpCont {

    private String grupo, materia, view;
    private ArrayList<String> colsToSet;
    private ArrayList<String> colsToSetHuman;
    private Map<String,String> extraSearchCond;
    private ArrayList<String> cols;
    private ViewSpecs specs;
    private ArrayList<AlumnoManager> managers;
    private String procedure;

    public GrupoCalifChargOpCont(
            String grupo,
            String materia,
            Map<String,String>extraSearchCond,
            String view,
            ArrayList<String> colsToSet,
            ArrayList<String> colsToSethuman,
            String procedure) {

        this(grupo, materia, view,procedure);
        this.extraSearchCond.putAll(extraSearchCond);
        this.colsToSet.addAll(colsToSet);
        this.colsToSetHuman.addAll(colsToSethuman);
    }

    public GrupoCalifChargOpCont(
            String grupo,
            String materia,
            String view,
            String procedure){
        this.procedure = procedure;
        managers = new ArrayList<>();
        specs = new ViewSpecs(view);
        extraSearchCond = new HashMap<>();
        this.grupo = grupo;
        this.materia = materia;
        this.view = view;

        colsToSet = new ArrayList<>();
            colsToSet.add("calificacion");
        colsToSetHuman = new ArrayList<>();
            colsToSetHuman.add("Calificación");
    }

    public void init(){
        initCols();
        initManagers();
    }

    public ArrayList<AlumnoManager> getManagers() {
        return managers;
    }

    public ArrayList<String> getHumanCols(){
        ArrayList<String> core = new ArrayList<>();
        core.add("Matrícula");
        core.add("Nombre Completo");
        core.addAll(colsToSetHuman);
        return core;
    }

    private void  initManagers(){
        Table alumnosCalifas = consultCalifas();
        for (int i = 0; i < alumnosCalifas.rowCount();i++)
            managers.add(new AlumnoManager(alumnosCalifas.getRegister(i),i));

    }

    public boolean haveChanges(){
        for (AlumnoManager manager:managers)
            if (manager.hasBeenModified)
                return true;

        return false;
    }

    private void initCols(){
        ArrayList<String> initialRegiter = new ArrayList<>();
            initialRegiter.add("numero_control");
            initialRegiter.add("nombre_completo");
            initialRegiter.add("calificacion_clave");
            initialRegiter.addAll(colsToSet);

        cols = initialRegiter;
    }

    private Table consultCalifas() {
        DataBaseConsulter consulter = new DataBaseConsulter(view);

        ArrayList<String> val = new ArrayList<>();
            val.add(grupo);
            val.add(materia);
            val.add(Global.conectionData.loadedPeriodo);

        for (Map.Entry<String,String> stringEntry:extraSearchCond.entrySet()){
            val.add(stringEntry.getValue());
        }

        return consulter.getFProcedure(procedure,val.toArray(new String[val.size()]));
    }

    public void submit(){
        for (AlumnoManager manager:managers)
            manager.submit();
    }

    public ArrayList<ArrayList<String>> getRegisters(){
        ArrayList<ArrayList<String>> reg = new ArrayList<>();
        for (AlumnoManager register: managers)
            reg.add(register.getCoreInfo());

        return reg;
    }

    public class AlumnoManager{
        private Map<String,String> alumnoInfo;
        private boolean hasBeenModified;
        private boolean califasWereNull;
        private int index;

        public AlumnoManager(TableRegister alumnoInfo,int index){
            this.index = index;
            hasBeenModified = false;
            this.alumnoInfo = alumnoInfo.toMap();
            califasWereNull = califasWereNUll();
            changeNullsToEmpty();
        }

        public ArrayList<String> getValuesSetted(){
            ArrayList<String>  values = new ArrayList<>();
            for (String col:colsToSet)
                values.add(alumnoInfo.get(col));

            return values;
        }

        private boolean califasWereNUll(){
            for (String col:colsToSet)
                if(alumnoInfo.get(col) != null)
                    return false;

            return true;
        }

        private void changeNullsToEmpty(){
            for (Map.Entry<String,String> entry: alumnoInfo.entrySet())
                if(entry.getValue() == null)
                    alumnoInfo.replace(entry.getKey(), "");

        }

        private void changeEmptyToNULL(){
            for (Map.Entry<String,String> entry: alumnoInfo.entrySet())
                if(entry.getValue().isEmpty())
                    alumnoInfo.replace(entry.getKey(),"NULL");
        }

        private void changeNULLToEmpty(){
            for (Map.Entry<String,String> entry: alumnoInfo.entrySet())
                if(entry.getValue().equals("NULL"))
                    alumnoInfo.replace(entry.getKey(),"");
        }

        public int getIndex() {
            return index;
        }

        public ArrayList<String> getCoreInfo(){
         ArrayList<String> coreInfo= new ArrayList<>();
            coreInfo.add(alumnoInfo.get("numero_control"));
            coreInfo.add(alumnoInfo.get("nombre_completo"));
            for (String colToSet:colsToSet)
                coreInfo.add(alumnoInfo.get(colToSet));
            return coreInfo;
        }

        public void set(String cond,String val){
            hasBeenModified = true;
                alumnoInfo.replace(cond,val);
        }

        public String get(String cond){
            return alumnoInfo.get(cond);
        }

        private Boolean califasAreNull(){
            boolean califasWereSettedToNull = false;
            for (String col:colsToSet)
                califasWereSettedToNull = valueIsNull(col);

            return califasWereSettedToNull;
        }

        private Boolean hasCalifKey(){
            return !valueIsNull("calificacion_clave");
        }

        private boolean valueIsNull(String col){
            return alumnoInfo.get(col).equals("NULL");
        }

        public void submit(){
            changeEmptyToNULL();
            if (hasBeenModified){
                if(hasCalifKey())
                    if (califasAreNull())
                        deleteCalifa();
                    else if (califasWereNull)
                        insertValues();
                    else
                        updateValues();
                else {
                    createCalifaKey();
                    insertValues();
                }
            }

            reset();
        }

        private void reset(){
            hasBeenModified = false;
            changeNULLToEmpty();

        }

        private void deleteCalifa(){
            ArrayList<String> cond = new ArrayList<>();
                cond.add("calificacion_clave");
                cond.addAll(extraSearchCond.keySet());

            ArrayList<String> val = new ArrayList<>();
                val.add(get("calificacion_clave"));
                val.addAll(extraSearchCond.values());

            try {
                specs.getUpdater().delete(cond,val);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            califasWereNull = true;
        }

        private void createCalifaKey(){
            String[] cond = new String[]{
                "clave_alumno",
                "semestre",
                "periodo",
                "materia"
            };
            String[] val = new String[]{
                alumnoInfo.get("numero_control"),
                alumnoInfo.get("semestre"),
                Global.conectionData.loadedPeriodo,
                materia
            };

            try {
                new ViewSpecs("calificaciones").getUpdater().insert(new ArrayList<>(Arrays.asList(cond)),new ArrayList<>(Arrays.asList(val)));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            set("calificacion_clave",new DataBaseConsulter("calificaciones").bringTable(cond,val).getUniqueValue());
        }

        private void insertValues(){
            ArrayList<String> cond = new ArrayList<>();
            ArrayList<String> val = new ArrayList<>();
            fillWithInsertValues(cond,val);

            try {
                new ViewSpecs(view).getUpdater().insert(cond,val);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        private void updateValues(){
            ArrayList<String> cond = new ArrayList<>();
            ArrayList<String> val = new ArrayList<>();
            fillWithUpdateValues(cond,val);

            ArrayList<String> califK = new ArrayList<>();
                califK.add("calificacion_clave");
            ArrayList<String> valK = new ArrayList<>();
                valK.add(alumnoInfo.get("calificacion_clave"));
                addEvas(califK,valK);

            try {
                new ViewSpecs(view).
                    getUpdater().
                    update(
                            cond,
                            val,
                            califK,
                            valK
                    );
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        private void addEvas(ArrayList<String> cond,ArrayList<String> val){
            cond.addAll(extraSearchCond.keySet());

            val.addAll(extraSearchCond.values());

        }

        private void fillWithUpdateValues(ArrayList<String> cond,ArrayList<String> val){
            addColSet(cond,val);
        }

        private void fillWithInsertValues(ArrayList<String> cond,ArrayList<String> val){
            cond.add("calificacion_clave");
            val.add(alumnoInfo.get("calificacion_clave"));
            addEvas(cond,val);

            fillWithUpdateValues(cond,val);
        }

        private void addColSet(ArrayList<String> cond,ArrayList<String> val){
            int size = colsToSet.size();
            for (int i = 0; i < size;i++){
                String col = colsToSet.get(i);
                cond.add(col);
                val.add(alumnoInfo.get(col));
            }
        }
    }
}
