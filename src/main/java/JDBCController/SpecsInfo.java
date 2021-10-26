package JDBCController;

import java.util.ArrayList;

public class SpecsInfo {

    private final String thisTable;

    private ArrayList<String> tags;
    private ArrayList<String> cols;

    private String view;
    private String visible;

    private String humanKey;
    private String humanName;
    private String unityName;

    private ArrayList<String> tableCols;
    private ArrayList<String> viewCols;
    private ArrayList<String> visibleCols;

    private ArrayList<String> rawForeignF;
    private ArrayList<String> rawPForeignF;
    private ArrayList<String> foreignTables;

    private ArrayList<String> autoIncCols;

    private ArrayList<String> primaryKeys;

    private ArrayList<Integer> colsSize;

    private int colCount;


    public SpecsInfo(String table){
        thisTable = table;
        colCount =-1;
    }

    public ArrayList<Integer> getColumnsSize(){
        if(colsSize == null)
            colsSize = new DBTableMetadata(thisTable).getColumnsSize();

        return new ArrayList<>(colsSize);
    }

    public ArrayList<String> getPrimaryKeys() {
        if(primaryKeys == null)
            primaryKeys = new DBTableMetadata(thisTable).getPrimaryKeyColumn();

        return primaryKeys;
    }

    public ArrayList<String> getAutoIncrCols() {
        if (autoIncCols == null)
            autoIncCols = new DBTableMetadata(thisTable).getAutoIncCols();


        return autoIncCols;
    }

    public ArrayList<String> getCols() {
        if (cols == null)
            defineTranslations();

        return new ArrayList<>(cols);
    }

    public ArrayList<String> getTags() {
        if (tags == null)
            defineTranslations();

        return new ArrayList<>(tags);
    }

    private void defineTranslations(){
        Table pack = consultIndexedTags();
        cols = pack.getColumn(0);
        tags = pack.getColumn(1);

    }

    public void flushCount(){
        colCount = -1;
    }

    public int getCount() {
        if (colCount == -1)
            colCount = consultCount();
        return colCount;
    }

    private int consultCount(){
        DataBaseConsulter optionsConsulter = new DataBaseConsulter(thisTable);
        String size = optionsConsulter.bringTable("SELECT COUNT(*) FROM " + thisTable).getUniqueValue();
        return Integer.parseInt(size);

    }

    private Table consultIndexedTags(){
        DataBaseConsulter consulter = new DataBaseConsulter("viewsspecs.tags");

        String[] colsToBring = new String[]{"title_name","alias"};

        String[] con = new String[]{"tag_view"};

        String[] value = new String[]{thisTable};

        Table response = consulter.bringTable(colsToBring,con,value);

        return response;
    }

    public ArrayList<String> getForeignRawCols(){
        if (rawForeignF == null)
            rawForeignF = getMetadata().getForeignKeysMetadata("FKCOLUMN_NAME");
        return new ArrayList<>(rawForeignF);
    }

    public ArrayList<String> getPForeignRawCols(){
        if (rawPForeignF == null)
            rawPForeignF = getMetadata().getForeignKeysMetadata("PKCOLUMN_NAME");
        return new ArrayList<>(rawPForeignF) ;
    }

    public ArrayList<String> getForeignTables(){
        if (foreignTables == null)
            foreignTables = getMetadata().getForeignKeysMetadata("PKTABLE_NAME");
        return new ArrayList<>(foreignTables);
    }

    private DBTableMetadata getMetadata(){
        return new DBTableMetadata(thisTable);
    }

    public ArrayList<String> getTableCols(){

        return getTagList(getTable());
    }

    public ArrayList<String> getViewCols() {

        return getTagList(getView());
    }

    public ArrayList<String> getVisibleCols() {
        return getTagList(getVisibleView());
    }

    private ArrayList<String> consultValue(String table){

        return new DBTableMetadata(table).getColumnsMetadata("COLUMN_NAME");
    }

    public ArrayList<String> conTablecols(){
        if (tableCols == null)
            tableCols = consultValue(getTable());

        return new ArrayList<>(tableCols);
    }

    public ArrayList<String> conViewCols() {
        if (viewCols == null)
            viewCols = consultValue(getView());
        return new ArrayList<>(viewCols);
    }

    public ArrayList<String> conVisibleCols() {
        if (visibleCols == null)
            visibleCols = consultValue(getVisibleView());

        return new ArrayList<>(visibleCols);
    }

    private ArrayList<String> getTagList(String value){

        if (value.equals(getVisibleView()))
            return conVisibleCols();

        if (value.equals(getView()))
            return conViewCols();

        if (value.equals(getTable()))
            return conTablecols();

        return null;
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

        Table response = consulter.bringTable(new String[]{"human_key","tag_view"},new String[]{"1",thisTable});

        if (response.isEmpty())
            return "";
        else
            return response.getUniqueValue();
    }

    private String determinateVisible(){
        String modyfiedview  = thisTable.concat("_visible_view");
        if(consultViewExistance(modyfiedview))
            return modyfiedview;
        else
            return getView();
    }

    public String getTable(){

        return thisTable;
    }

    private String determinateView(){
        String modyfiedview  = thisTable.concat("_view");
        if(consultViewExistance(modyfiedview))
            return modyfiedview;
        else
            return thisTable;
    }

    public  boolean consultViewExistance(String view){
        DataBaseConsulter cons = new DataBaseConsulter("alumnos");
        return !cons.bringTable("SHOW FULL TABLES IN cebdatabase WHERE TABLE_TYPE LIKE '%VIEW%' and  Tables_in_cebdatabase = '" + view + "'").isEmpty();
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
        String[] values = new String[]{thisTable};

        return consulter.bringTable(columnsToBring,conditions,values).getUniqueValue();

    }


}
