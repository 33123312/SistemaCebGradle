package SpecificViews;

import Generals.BtnFE;
import JDBCController.ViewSpecs;
import RegisterDetailViewProps.RegisterDetail;
import Tables.AdapTableFE;
import Tables.RowsFactory;
import Tables.StyleCellModel;
import Tables.StyleRowModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public abstract class TableViewerOperation extends OperationWindow {
    LinearVerticalLayout body;
    ArrayList<tableToPrint> tables;

    public TableViewerOperation(OperationInfoPanel infoPanlel){
        super(infoPanlel);
    }

    @Override
    public void buildOperation(){
        super.buildOperation();
        thisWindow.addBody(getBody());
        tables = new ArrayList<>();

    }


    private JScrollPane getBody(){
        JScrollPane scroll = new JScrollPane();
            body = new LinearVerticalLayout();
            scroll.setViewportView(body);

        return scroll;

    }

    protected  void removeAllTables(){
        tables = new ArrayList<>();
        body.setVisible(false);
        body.removeAll();
        body.setVisible(true);
    }


    protected void addLateralTable(String title,ArrayList<String> titles,ArrayList<String> lateralTitles,ArrayList<ArrayList<String>> registers){
        tables.add(new tableToPrint(title,titles,lateralTitles,registers));
        AdapTableFE outputTable = getLateralTable();

        outputTable.setTitles(titles);
        outputTable.setLateralTitles(lateralTitles);
        outputTable.addNShow(registers);

        addTableContainer(title, outputTable);
    }

    protected void addNormalTable(String title,ArrayList<String> titles,ArrayList<ArrayList<String>> registers){
        tables.add(new tableToPrint(title,titles,registers));
        AdapTableFE outputTable = getOutPutTable();
        outputTable.setTitles(titles);
        outputTable.addNShow(registers);
        addTableContainer(title, outputTable);

    }

    private AdapTableFE getLateralTable(){
        AdapTableFE resT  = getOutPutTable();
            resT.addLateralStyle(new StyleCellModel() {
                @Override
                public void setCellStyle(JLabel a) {
                    a.setForeground(Color.white);
                    a.setOpaque(true);
                    a.setBackground(new Color(116, 185, 255));
                }
            });

        return resT;
    }

    private AdapTableFE getOutPutTable(){
        AdapTableFE outputTable = new AdapTableFE();

        outputTable.addRowStyle(new StyleRowModel() {
            @Override
            public RowsFactory.row setStyleModel(RowsFactory.row row) {
                row.setBackground(Color.white);
                row.setBorder(BorderFactory.createMatteBorder(0,0,1,0,new Color(240,240,240)));
                return row;
            }
        });

        outputTable.addTitleStyle(new StyleRowModel() {
            @Override
            public RowsFactory.row setStyleModel(RowsFactory.row row) {
                row.setTextColor(Color.white);
                row.setBackground(new Color(116, 185, 255));
                return row;

            }
        });

        return outputTable;
    }

    private JPanel addTableContainer(String tableTitle, AdapTableFE tableFE){

        JPanel container = new JPanel(new BorderLayout());
        container.setBorder(BorderFactory.createEmptyBorder(15,30,15,30));


        JLabel titleLabel = new JLabel(tableTitle);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20,0,20,0));

        container.add(titleLabel,BorderLayout.NORTH);
        container.add(tableFE,BorderLayout.CENTER);

        body.addElement(container);

        return  container;
    }

    protected class tableToPrint{
        String title;
        ArrayList<String> titles;
        ArrayList<String> lateralTitles;
        ArrayList<ArrayList<String>> registers;

        public tableToPrint(String title,
                ArrayList<String> titles,
                ArrayList<String> lateralTitles,
                ArrayList<ArrayList<String>> registers){

            this.title = title;
            this.titles = titles;
            this.lateralTitles = lateralTitles;
            this.registers = registers;

        }

        public tableToPrint(String title,
                            ArrayList<String> titles,
                            ArrayList<ArrayList<String>> registers){
            this.title = title;
            this.titles = titles;
            this.registers = registers;

        }




    }
}
