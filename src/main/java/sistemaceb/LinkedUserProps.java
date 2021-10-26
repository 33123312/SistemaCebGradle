package sistemaceb;

import JDBCController.Table;
import JDBCController.ViewSpecs;
import sistemaceb.form.Global;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class LinkedUserProps extends JPanel {
    ViewSpecs specs;
    Table visibleInfo;
    GridBagConstraints constraints;

    ArrayList<String> linkTags;
    ArrayList<String> linkTable;

    public LinkedUserProps(ViewSpecs specs, Table registerInfo){
        super(new GridBagLayout());
        setOpaque(false);
        this.specs =specs;
        getForeignTables();
        this.visibleInfo = registerInfo;

        initConstraints();
        addProps();

    }

    private void getForeignTables() {
        ArrayList<String> foreignColumns;
        ArrayList<String> foreignTables;

        foreignColumns = specs.getForeignTags();
        foreignTables = specs.getInfo().getForeignTables();

        linkTags = new ArrayList<>();
        linkTable = new ArrayList<>();

        for(int i = 0;i < foreignColumns.size();i++)
            if (new ViewSpecs(foreignTables.get(i)).isMain()){
                linkTags.add(foreignColumns.get(i));
                linkTable.add(foreignTables.get(i));
            }


    }

    private void initConstraints(){
        constraints = new GridBagConstraints();
        constraints.gridy = 0;
        constraints.gridx = 0;
        constraints.weightx =  1;
        constraints.fill = GridBagConstraints.HORIZONTAL;

    }


    private void addProps(){
        ArrayList<String> visibleTags = visibleInfo.getColumnTitles();
        ArrayList<String> visibleTagsValues =  visibleInfo.getRegisters().get(0);

        for(int i = 0;i < visibleTags.size();i++){
            String currentTitle = visibleTags.get(i);
            String currentValue = visibleTagsValues.get(i);

            JPanel newRow = buildRow(currentTitle,currentValue);

            add(newRow,constraints);
            searchForLink(currentTitle,currentValue,newRow);
            constraints.gridy++;
        }

    }

    private void gotoView(String currentTitle,String currentValue){
        String foreignTable = getForeignTable(currentTitle);
        Global.view.currentWindow.newView(
                new RegisterDetailView(foreignTable, currentValue)
        );
    }

    private void searchForLink(String currentTitle, String currentValue, JPanel title){
        if(linkTags.contains(currentTitle)) {

            title.setCursor(new Cursor(Cursor.HAND_CURSOR));

            Component component = title.getComponent(1);
            component.setForeground(new Color(52, 152, 219));

            component.addMouseListener(new MouseAdapter() {
                Color originalColor = component.getForeground();
                Color transitionColoe = new Color(41, 128, 185);

                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    gotoView(currentTitle,currentValue);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    super.mouseEntered(e);
                    component.setForeground(transitionColoe);

                }

                @Override
                public void mouseExited(MouseEvent e) {
                    super.mouseExited(e);
                    component.setForeground(originalColor);
                }
            });


        }

    }

    private String getForeignTable(String column){
        return linkTable.get(linkTags.indexOf(column));
    }

    private JPanel buildRow(String currentTitle, String currentValue){
        Border propertysMargins = BorderFactory.createEmptyBorder(2, 7, 2, 2);
        Color propertysFontColor = new Color(100,100,100);

        JPanel propertyContainer = new JPanel(new BorderLayout());
            propertyContainer.setOpaque(false);

        JLabel titleLabel= new JLabel(currentTitle + ":");
            titleLabel.setForeground(propertysFontColor);
            titleLabel.setFont(new Font("Arial", Font.BOLD , 17));
            titleLabel.setBorder(propertysMargins);

        propertyContainer.add(titleLabel,BorderLayout.WEST);

        JLabel valueLabel= new JLabel(currentValue);
            valueLabel.setForeground(propertysFontColor);
            valueLabel.setFont(new Font("Arial", Font.PLAIN , 19));
            valueLabel.setBorder(propertysMargins);

        propertyContainer.add(valueLabel,BorderLayout.CENTER);

        return propertyContainer;
    }
}
