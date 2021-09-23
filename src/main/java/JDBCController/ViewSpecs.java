/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JDBCController;

import com.itextpdf.kernel.pdf.canvas.parser.data.PathRenderInfo;
import com.sun.jdi.PrimitiveValue;
import sistemaceb.form.Global;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.PrimitiveIterator;

public class ViewSpecs {

    protected String table;
    private SpecsInfo info;

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
        ArrayList<String> tables = tagsConsulter.getRelatedTables();
        ArrayList<String> views = new ArrayList();
        for (String table : tables)
            views.add(table);

        return views;
    }

    public ArrayList<String> getForeignCols() {
        ArrayList<String> foreignCols = getForeignRawCols();
        return getTag(foreignCols);
    }


    public ArrayList<String> getFKForeignTags() {

        return getFKForeignTags(defProps()).getColumn("alias");
    }

    private String[] defProps() {

        return new String[]{"alias"};
    }


    public Table getFKForeignTags(String[] propiertysToGet) {

        return getForeignTags("human_key", propiertysToGet);
    }

    private Table getForeignTags(String keyType, String[] propiertysToGet) {

        ArrayList<String> foreigntables = getForeignColsTables();
        DataBaseConsulter consulter = new DataBaseConsulter("viewsspecs.tags");

        String[] keyTypeA = new String[foreigntables.size() + 1];
        String[] keyTypeAValue = new String[]{"1"};

        keyTypeA[0] = keyType;
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
        ArrayList<String> foreigntables = getForeignColsTables();
        ArrayList<String> columnNames = getForeignCols();


        return columnNames.get(foreigntables.indexOf(table));
    }

    public String getTableFromTag(String tag) {
        ArrayList<String> foreignTables = getForeignColsTables();
        ArrayList<String> columnNames = getForeignCols();

        return foreignTables.get(columnNames.indexOf(tag));
    }

    public String getTableFromTColumn(String column) {
        ArrayList<String> foreignTables = getForeignColsTables();
        ArrayList<String> columnNames = getForeignRawCols();

        return foreignTables.get(columnNames.indexOf(column));
    }

    public String getForeignColumnFromColumn(String column) {

        ArrayList<String> foreignColumns = getForeignRawCols();
        ArrayList<String> foreignPColumns = getPForeignRawCols();


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
        return getTag(info.getTags().get(1));
    }

    public ArrayList<String> getViewTags(){
        return getTag(info.getViewCols());
    }

    public ArrayList<String> getTableTags(){
        return getTag(info.getTablecols());
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

    public String getTag(String col){
        ArrayList<String > cols  = new ArrayList<>();
            cols.add(col);

        return getCoincident(cols,0).get(0);
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
            }
        }
        return translations;
    }

    private int getContrario(int type) {
        if (type == 0)
            return 1;
        else
            return 0;
    }

    private static Map<String, SpecsInfo> viewsStates;

    private SpecsInfo getSpecsInfo() {
        if (viewsStates == null)
            viewsStates = new HashMap();

        if (!viewsStates.containsKey(table))
            viewsStates.put(table, new SpecsInfo());

        return viewsStates.get(table);

    }
    private ArrayList<ArrayList<String>> getIndexedTags() {
        return info.getTags();
    }

    public boolean hasOptions() {
        int size = getSize();
        if (size < 20)
            return false;

        return true;
    }

    private int getSize() {
        DataBaseConsulter optionsConsulter = new DataBaseConsulter(table);
        String size = optionsConsulter.bringTable("SELECT COUNT(*) FROM " + table).getUniqueValue();
        return Integer.parseInt(size);
    }

    private DBTableMetadata getMetadata(){
        return new DBTableMetadata(table);
    }

    public dataType getColumnType(String tag) {
        tag = getCol(tag);

        ArrayList<String> columns = getMetadata().getColumnsMetadata("COLUMN_NAME");
        ArrayList<String> types = getMetadata().getColumnsMetadata("DATA_TYPE");

        int columnIndex = columns.indexOf(tag);
        dataType tagType = determinateDataType(types.get(columnIndex));

        return tagType;
    }

    public ArrayList<String> getForeignRawCols(){
        return getMetadata().getForeignKeysMetadata("FKCOLUMN_NAME");
    }

    public ArrayList<String> getPForeignRawCols(){
        return getMetadata().getForeignKeysMetadata("PKCOLUMN_NAME");
    }

    public ArrayList<String> getForeignColsTables(){ return getMetadata().getForeignKeysMetadata("PKTABLE_NAME");
    }

    public class SpecsInfo{
        private String table;
        private ArrayList<ArrayList<String>> tags;
        private String view;
        private String visible;
        private String humanKey;
        private String humanName;
        private String unityName;
        private ArrayList<String> tablecols;
        private ArrayList<String> viewCols;


        SpecsInfo(){
            table = getTable();
        }

        public ArrayList<String> getTablecols(){
            if (tablecols == null)
                tablecols = consultValue(getTable());

            return tablecols;
        }

        public ArrayList<String> getViewCols() {
            if (viewCols == null)
                viewCols = consultValue(getView());
            return viewCols;
        }

        private ArrayList<String> consultValue(String table){
            ArrayList<String> cols = new DBTableMetadata(table).getColumnsMetadata("COLUMN_NAME");
           return cols;
        }



        public String getVisibleView() {
            if (visible == null)
                visible = determinateVisible();

            return visible;
        }

        public String getView(){
            if (view == null)
                view = determinateView();
            return view;
        }

        public String getHumanKey(){
            if (humanKey == null)
                humanKey = consultHumanKey();
            return humanKey;
        }

        private String consultHumanKey(){

            DataBaseConsulter consulter = new DataBaseConsulter("viewsspecs.tags");

            String[] cond = new String[]{"human_key"};
            String[] val = new String[]{"i"};

            Table response = consulter.bringTable(cond,val);

            if (response.isEmpty())
                return "";
            else
                return response.getUniqueValue();
        }

        private String determinateVisible(){
            String modyfiedview  = table.concat("_visible_view");
            if(consultViewExistance(modyfiedview))
                return modyfiedview;
            else
                return getView();
        }

        private String determinateView(){
            String modyfiedview  = table.concat("_view");
            if(consultViewExistance(modyfiedview))
                return modyfiedview;
            else
                return table;
        }

        public  boolean consultViewExistance(String view){
            DataBaseConsulter cons = new DataBaseConsulter("alumnos");
            return !cons.bringTable("SHOW FULL TABLES IN cebdatabase WHERE TABLE_TYPE LIKE '%VIEW%' and  Tables_in_cebdatabase = '" + view + "'").isEmpty();
        }

        public ArrayList<ArrayList<String>> getTags() {
            if (tags == null)
                tags = consultIndexedTags();

            return tags;
        }

        private ArrayList<ArrayList<String>> consultIndexedTags(){
            DataBaseConsulter consulter = new DataBaseConsulter("viewsspecs.tags");

            String[] colsToBring = new String[]{"title_name","alias"};

            String[] con = new String[]{"tag_view"};

            String[] value = new String[]{table};

            Table response = consulter.bringTable(colsToBring,con,value);

            ArrayList<ArrayList<String>> res = new ArrayList<>();
                res.add(response.getColumn(0));
                res.add(response.getColumn(1));

            return res;
        }

        public String  getHumanName(){
            if(humanName == null)
                humanName = getViewInfo("alias");

            return humanName;
        }

        public String  getUnityName(){
            if (unityName == null)
                unityName =getViewInfo("unity_name");

            return unityName;
        }

        private String getViewInfo(String info){
            DataBaseConsulter consulter = new DataBaseConsulter("viewsspecs.views");
            String[] columnsToBring = new String[]{info};
            String[] conditions = new String[]{"table_name"};
            String[] values = new String[]{table};

            return consulter.bringTable(columnsToBring,conditions,values).getUniqueValue();

        }
    }
    
}