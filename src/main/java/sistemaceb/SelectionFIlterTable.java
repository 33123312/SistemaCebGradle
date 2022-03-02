package sistemaceb;

import JDBCController.Table;
import JDBCController.ViewSpecs;
import SpecificViews.GrupoOperator;
import SpecificViews.LinearHorizontalLayout;
import Tables.AdapTableFE;
import Tables.RowsFactory;
import Tables.StyleRowModel;
import Tables.TableRow;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SelectionFIlterTable extends AdapTableFE {
    ArrayList<ArrayList<String>> possibleSelections;
    String grupo;


    public SelectionFIlterTable(String grupo,Table alumnos) {
        super();
        this.grupo = grupo;
        groupsNames = new ArrayList<>();
        colors = new ArrayList<>();
        groups = new ArrayList<>();
        deployTable(alumnos);


    }

    public void setDefaultSelections(Map<String,ArrayList<String>> selections){
        for (Map.Entry<String,ArrayList<String>> group:selections.entrySet()){
            ArrayList<Integer> selected = new ArrayList<>();
            int groupIndex = groupsNames.indexOf(group.getKey());
            if (groupIndex>-1){
                setCurrentGroup(groupIndex);
                for (String alu:group.getValue()){
                    int aluIndex = getAluIndex(alu);
                    if (aluIndex>-1){
                        addToGroup(aluIndex);
                        selected.add(aluIndex);
                    }
                }
                setSelectedIndex(selected);
            }
        }

    }

    public ArrayList<String> getUnselected(){
        ArrayList<String> alumnos = new ArrayList<>();
        ArrayList<TableRow> unselectedIndex = getDeselectedRow();

        for (TableRow row:unselectedIndex){
            alumnos.add(possibleSelections.get(row.getKey()).get(0));
        }

        return alumnos;
    }

    private int getAluIndex(String key){
        for (int i = 0;i < possibleSelections.size();i++){
            if (possibleSelections.get(i).get(0).equals(key))
                return i;
        }


        return -1;
    }

    public void selectAll(){
        ArrayList<TableRow> a = getRows();

        for (TableRow row: a)
            row.select();
    }

    private void addAlumnos(Table alumnos){

        possibleSelections = alumnos.getSubTable(new String[]{"grupo"},new String[]{grupo}).getRegisters();
        groupsReveerseLink = new ArrayList[possibleSelections.size()];

        setTitles(alumnos.getColumnTitles());
        setRows(possibleSelections);
        showAll();
    }

    private void deployTable(Table alumnos){
        addRememberSelectedRows(new Color(129, 236, 236));
        getFactory().addLeftClickSelectionEvnt(
                new rowSelectionEvnt() {
                    @Override
                    public void whenSelect(TableRow tableRow) {
                        addToGroup(tableRow.getKey());
                    }
                },
                new rowSelectionEvnt() {
                    @Override
                    public void whenSelect(TableRow tableRow) {
                        removeFromGroup(tableRow.getKey());
                    }
                }
        );
        addAlumnos(alumnos);

    }

    private ArrayList<String> groupsNames;
    private ArrayList<ArrayList<String>> groups;
    private ArrayList<Color> colors;
    private ArrayList<String>[] groupsReveerseLink;
    private int currentGroup;

    private void addToGroup(int index){
        ArrayList<String> current = groups.get(currentGroup);
        groupsReveerseLink[index] = current;


    }

    private void removeFromGroup(int index){
        groupsReveerseLink[index] = null;

    }

    public void createGroup(String newGroup){
        groupsNames.add(newGroup);
        groups.add(new ArrayList<>());
        colors.add(detColor(newGroup));

    }

    private Color detColor(String name){
        int red = 150;
        int blue = 150;
        int yellow = 150;
       char[] chars =  name.toCharArray();
       int num = Character.getNumericValue(chars[2]);

       red += (num%3)*50;
       blue += num*10;
       yellow += (num%5)*13;

       return new Color(red,blue,yellow);

    }

    public void setCurrentGroup(String currentGroup) {
        setCurrentGroup(groupsNames.indexOf(currentGroup));

    }

    public void setCurrentGroup(int groupIndex) {

        this.currentGroup = groupIndex;

        if (selectedColorPanels != null){
            setSelectionColor(getColor());
            if(selectedColorPanel != null)
            selectedColorPanel.setBackground(Color.white);
            JPanel container = selectedColorPanels.get(currentGroup);
            selectedColorPanel = container;
            container.setBackground(new Color(116, 185, 255));
        }
    }




    private Color getColor(){
        return colors.get(currentGroup);
    }


    public void updateGrupos(ArrayList<String> groupsN){
        groupsNames = new ArrayList<>();
        colors = new ArrayList<>();
        groups = new ArrayList<>();

        for (String grupo:groupsN)
            createGroup(grupo);

    }


    public void updateEvr(ArrayList<String> groupsNames) {
        updateGrupos(groupsNames);
        updateSelectionBar();
    }

    private LinearHorizontalLayout selectionBar;

    public LinearHorizontalLayout getColorsSelectionBar(){
         selectionBar = new LinearHorizontalLayout();

         return selectionBar;
    }

    public void updateSelectionBar(){
        selectionBar.removeAll();
        selectedColorPanels = new ArrayList<>();
        for (int i = 0;i<groupsNames.size();i++){
            JPanel p = createColorPanel(i);
            selectionBar.addElement(p);
            selectedColorPanels.add(p);
        }


        if (!groupsNames.isEmpty())
            setCurrentGroup(0);

    }

    private JPanel selectedColorPanel;
    private ArrayList<JPanel>  selectedColorPanels;

    private JPanel createColorPanel(int groupIndex){
        LinearHorizontalLayout container = new LinearHorizontalLayout();
            container.setBackground(Color.white);
            container.setBorder(new EmptyBorder(10,20,10,20));
             container.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JPanel colorPanel = new JPanel();
            colorPanel.setBackground(colors.get(groupIndex));
            colorPanel.setSize(new Dimension(10,10));


        JLabel groupLabel = new JLabel(groupsNames.get(groupIndex));
            groupLabel.setFont(new Font("sans-serif", Font.PLAIN, 20));
            groupLabel.setBorder(new EmptyBorder(0,5,0,0));

        container.addElement(colorPanel);
        container.addElement(groupLabel);

        container.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                setCurrentGroup(groupIndex);

            }
        });

        return container;
    }



    public Map<String,ArrayList<String>> getSelectedAlumnos(){
        Map<String,ArrayList<String>> groups = new HashMap<>();

        for (int i = 0;i < groupsReveerseLink.length;i++){
            ArrayList<String> cur = groupsReveerseLink[i];
            if (cur != null)
                cur.add(possibleSelections.get(i).get(0));
        }

        for (int i = 0;i<this.groups.size();i++){

            if (!this.groups.get(i).isEmpty())
                groups.put(groupsNames.get(i),this.groups.get(i));
        }

        return groups;
    }

    public boolean hasSelectedAlumnos() {
        for (ArrayList alu:groupsReveerseLink)
            if(alu!=null)
                return true;

        return false;
    }
}
