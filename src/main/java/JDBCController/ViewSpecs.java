/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JDBCController;
import sistemaceb.form.Global;

import java.sql.SQLException;
import java.util.ArrayList;

public class ViewSpecs {

    private String table;
    private final SpecsInfo info;

    public ViewSpecs(String view) {
        this.table = view;
        info = getSpecsInfo();

    }

    public SpecsInfo getInfo(){
        return info;
    }

    public String getTable() {
        return table;
    }

    public ArrayList<String> getRelatedTables() {
        DataBaseConsulter tagsConsulter = new DataBaseConsulter(table);
        return tagsConsulter.getRelatedTables();

    }

    public ArrayList<String> getForeignCols() {
        ArrayList<String> foreignCols = info.getForeignRawCols();
        return getTag(foreignCols);
    }


    public ArrayList<String> getFKForeignTags() {

        return getFKForeignTags(defProps()).getColumn("alias");
    }

    private String[] defProps() {

        return new String[]{"alias"};
    }


    public Table getFKForeignTags(String[] propiertysToGet) {

        return getForeignTags(propiertysToGet);
    }

    private Table getForeignTags( String[] propiertysToGet) {

        ArrayList<String> foreigntables = info.getForeignTables();
        DataBaseConsulter consulter = new DataBaseConsulter("viewsspecs.tags");

        String[] keyTypeA = new String[foreigntables.size() + 1];
        String[] keyTypeAValue = new String[]{"1"};

        keyTypeA[0] = "human_key";
        keyTypeAValue[0] = "1";

        int size = foreigntables.size();
        for (int i = 0; i < size; i++) {
            keyTypeA[i] = "tag_view";
            keyTypeAValue[i] = foreigntables.get(i);
        }
        return consulter.bringTable(propiertysToGet, keyTypeA, keyTypeAValue);

    }


    public ArrayList<String> getPrimaryskey() {

        ArrayList<String> primaryKeys = new DBTableMetadata(table).getPrimaryKeyColumn();
        return getTag(primaryKeys);

    }

    public ArrayList<String> getAutoIncrCols() {
        ArrayList<String> cols = new DBTableMetadata(table).getAutoIncCols();
        cols = getTag(cols);

        return cols;
    }

    public String getPrimarykey() {
        ArrayList<String> primaryKeys = getPrimaryskey();
        if (!primaryKeys.isEmpty())
            return primaryKeys.get(0);
        else
            return null;
    }


    public ArrayList<String> getForeignTables(ArrayList<String> foreignTags) {
        ArrayList<String> response = new ArrayList<>();
        for (String foreignTag : foreignTags)
            response.add(getTableFromTag(foreignTag));

        return response;
    }

    public String getTagFromTable(String table) {
        ArrayList<String> foreigntables = info.getForeignTables();
        ArrayList<String> columnNames = getForeignCols();


        return columnNames.get(foreigntables.indexOf(table));
    }

    public String getTableFromTag(String tag) {
        ArrayList<String> foreignTables = info.getForeignTables();
        ArrayList<String> columnNames = getForeignCols();

        return foreignTables.get(columnNames.indexOf(tag));
    }

    public String getTableFromTColumn(String column) {
        ArrayList<String> foreignTables = info.getForeignTables();
        ArrayList<String> columnNames = info.getForeignRawCols();

        return foreignTables.get(columnNames.indexOf(column));
    }

    public String getForeignColumnFromColumn(String column) {

        ArrayList<String> foreignColumns = info.getForeignRawCols();
        ArrayList<String> foreignPColumns = info.getPForeignRawCols();


        return foreignPColumns.get(foreignColumns.indexOf(column));
    }

    public boolean hasHumanKey() {

        return !info.getHumanKey().isEmpty();
    }

    public boolean isMain() {

        return Global.conectionData.getMainTables().getColumn("table_name").contains(getTable());
    }

    public static dataType determinateDataType(String typeIndex) {
        int typeInt = Integer.parseInt(typeIndex);
        switch (typeInt) {
            case 4:
                return dataType.INT;
            case 3:
                return dataType.FLOAT;
            case 91:
                return dataType.DATE;
            default:
                return dataType.VARCHAR;
        }
    }

    public ArrayList<String> getVisibleTags(){
        return getTag(info.getVisibleCols());
    }

    public ArrayList<String> getViewTags(){
        return getTag(info.getViewCols());
    }

    public ArrayList<String> getTableTags(){
        return getTag(info.getTableCols());
    }

    public ArrayList<String> getTag(ArrayList<String> titles) {
        return getCoincident(titles, 0);
    }

    public ArrayList<String> getCol(ArrayList<String> columns) {
        return getCoincident(columns, 1);
    }

    public String getCol(String tag){
        ArrayList<String > tags  = new ArrayList<>();
            tags.add(tag);

        return getCoincident(tags,1).get(0);
    }

    private ArrayList<String> getCoincident(ArrayList<String> columns, int type) {
        ArrayList<String> colsToTranslate = new ArrayList<>(columns);
        ArrayList<String> translations = new ArrayList<>();

        ArrayList<ArrayList<String>> response = getIndexedTags();

        ArrayList<String> indexList = response.get(type);
        ArrayList<String> values = response.get(getContrario(type));

        for (String colToTranslate : colsToTranslate) {
            int indexInt = indexList.indexOf(colToTranslate);
            if (indexInt > -1) {
                translations.add(values.get(indexInt));

                indexList.remove(indexInt);
                values.remove(indexInt);
            } else
                translations.add(colToTranslate);

        }
        return translations;
    }

    private int getContrario(int type) {
        if (type == 0)
            return 1;
        else
            return 0;
    }

    private static ArrayList<SpecsInfo> viewsStates;

    private SpecsInfo getInfoFromLIst(){

        for (SpecsInfo info:viewsStates)
            if (info.getTable().equals(table))
                return info;

        return null;
    }

    private SpecsInfo getSpecsInfo() {
        if (viewsStates == null)
            viewsStates = new ArrayList<>();

        SpecsInfo res = getInfoFromLIst();

        if (res == null) {
            SpecsInfo newInfo = new SpecsInfo(table);
            viewsStates.add(newInfo);
            return newInfo;
        }

        return res;

    }
    private ArrayList<ArrayList<String>> getIndexedTags() {
        ArrayList<ArrayList<String>> netags = new ArrayList<>();
            netags.add(new ArrayList<>(info.getCols()));
            netags.add(new ArrayList<>(info.getTags()));

        return netags;
    }

    public boolean hasOptions() {
        int size = info.getCount();

        return size < 20;
    }



    private DBTableMetadata getMetadata(){
        return new DBTableMetadata(table);
    }

    public dataType getColumnType(String tag) {
        tag = getCol(tag);

        ArrayList<String> columns = getMetadata().getColumnsMetadata("COLUMN_NAME");
        ArrayList<String> types = getMetadata().getColumnsMetadata("DATA_TYPE");

        int columnIndex = columns.indexOf(tag);

        return determinateDataType(types.get(columnIndex));

    }

    public Updater getUpdater(){
        return new Updater();
    }
    public class Updater{

        public void delete(ArrayList<String> columnCondition, ArrayList<String> values) throws SQLException {
            String query = "delete from " + table + " where "  + stringifyConditions(columnCondition,values);
            System.out.println(query);
            info.flushCount();
            Global.SQLConector.getMyStatment().executeUpdate(query);

        }

        public void insert(ArrayList<String> columns,ArrayList<String> values) throws SQLException {

            String query = "insert into " + table + "(" + stringifyColumns(columns) + ") values (" + stringifyValues(values) + ")";
            //System.out.println(query);
            info.flushCount();
            Global.SQLConector.getMyStatment().executeUpdate(query);

        }
        public void update (
                ArrayList<String>colToMod,
                ArrayList<String> newValues,
                ArrayList<String>colCondition,
                ArrayList<String> conditionValue
        ) throws SQLException {
            String primaryCol = getCol(getPrimarykey());

            if(colToMod.contains(primaryCol) && getPrimaryskey().size() == 1){
                int keyIndex = colToMod.indexOf(primaryCol);
                String newPrimaryValue = newValues.get(keyIndex);
                String oldValue = conditionValue.get(0);
                if(!oldValue.equals(newPrimaryValue)) {
                    copyOldRegister(primaryCol, newPrimaryValue, oldValue);
                    updteRelatedTables(newPrimaryValue, oldValue);
                    deleteOldValue(primaryCol, oldValue);
                    conditionValue.remove(0);
                    conditionValue.add(newPrimaryValue);
                }

                colToMod.remove(primaryCol);
                newValues.remove(newPrimaryValue);

            }

            if(!colToMod.isEmpty())
                updateRegister(colToMod,newValues,colCondition,conditionValue);
        }

        private void deleteOldValue(String primaryCol, String oldValue){

            ArrayList<String> condtoDelete = new ArrayList<>();
            condtoDelete.add(primaryCol);

            ArrayList<String> valueToDelete = new ArrayList<>();
            valueToDelete.add(oldValue);

            try {
                delete(condtoDelete,valueToDelete);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        private void copyOldRegister(String primaryKey,String newValue, String oldValue){
            DataBaseConsulter oldRegisterConsulter = new DataBaseConsulter(getTable());

            String[] oldConditions = new String[]{primaryKey};

            String[] oldValues = new String[]{oldValue};

            TableRegister response = oldRegisterConsulter.bringTable(oldConditions,oldValues).getRegister(0);

            ArrayList<String> cols = response.getColumnTitles();
            cols.remove(primaryKey);
            cols.add(primaryKey);

            ArrayList<String> values = response.getValues();
            values.remove(oldValue);
            values.add(newValue);

            try {
                insert(cols,values);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        private void updteRelatedTables(String newPrimaryValue,String oldValue) throws SQLException {
            ArrayList<String> relatedTables = getRelatedTables();
            for(String table: relatedTables){
                ViewSpecs tableSpecs = new ViewSpecs(table);

                ArrayList<String>  foreignKey = new ArrayList<>();
                foreignKey.add(tableSpecs.getTagFromTable(getTable()));

                ArrayList<String> newPrimaryValueArray = new ArrayList<>();
                newPrimaryValueArray.add(newPrimaryValue);

                ArrayList<String> currentPrimaryValueArray = new ArrayList<>();
                currentPrimaryValueArray.add(oldValue);

                foreignKey = new ViewSpecs(table).getCol(foreignKey);
                update(foreignKey,newPrimaryValueArray,foreignKey,currentPrimaryValueArray);
            }
        }

        public void updateRegister (ArrayList<String>colToMod, ArrayList<String> newValues,ArrayList<String>colCondition,ArrayList<String>conditionValue) throws SQLException {
            String stringifiedNewValues = "";
            int i;
            int size = colToMod.size()-1;
            for(i = 0; i < size;i++)
                stringifiedNewValues +=  colToMod.get(i) + " = " + mergeBranches(newValues.get(i)) + ",";

            stringifiedNewValues +=  " " + colToMod.get(i)  + " = " + mergeBranches(newValues.get(i));

            String query = "update " + table + " set "  + stringifiedNewValues + " where " + stringifyConditions(colCondition,conditionValue);
            Global.SQLConector.getMyStatment().executeUpdate(query);

        }

        private String stringifyConditions(ArrayList<String> columnCondition,ArrayList<String> values){
            StringBuilder stringifiedConditions = new StringBuilder();

            int i;
            int size = columnCondition.size()-1;
            for(i = 0; i < size;i++){
                stringifiedConditions.append(columnCondition.get(i));
                stringifiedConditions.append(" = ");
                stringifiedConditions.append(mergeBranches(values.get(i)));
                stringifiedConditions.append( " and ");
            }

            stringifiedConditions.append(columnCondition.get(i));
            stringifiedConditions.append(" = ");
            stringifiedConditions.append(mergeBranches(values.get(i)));

            return stringifiedConditions.toString();
        }

        private String stringifyValues(ArrayList<String> values){
            StringBuilder stringyfiedColumns = new StringBuilder();

            int i;
            int size = values.size()-1;
            for(i = 0;i < size; i++){
                stringyfiedColumns.append(mergeBranches(values.get(i)));
                stringyfiedColumns.append(",");
            }

            stringyfiedColumns.append(mergeBranches(values.get(i)));

            return stringyfiedColumns.toString();
        }

        private String stringifyColumns(ArrayList<String> columns){
            ArrayList<String> convertedColumns = columns;
            int i;
            StringBuilder stringyfiedColumns = new StringBuilder();
            int size = convertedColumns.size()-1;

            for(i = 0; i < size ;i++){
                stringyfiedColumns.append(convertedColumns.get(i));
                stringyfiedColumns.append(",");
            }

            stringyfiedColumns.append(convertedColumns.get(i));

            return stringyfiedColumns.toString();
        }

        private String mergeBranches(String val){
            if(val == null)
                val = "NULL";
            if (!val.equals("NULL"))
                val  ="'"+val+ "'";

            return val;
        }

    }

}