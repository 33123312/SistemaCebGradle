package sistemaceb.form;

import Generals.BtnFE;
import JDBCController.Table;
import sistemaceb.SubmitFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class GroupRelationarForm extends SubmitFrame {
    JList getterList;
    JList adderList;
    ArrayList<String> trueGetterValues;
    ArrayList<String> trueAdderValues;
    private String getterTitle;


    public GroupRelationarForm(String title, String getterTitle) {
        super(title);
        this.getterTitle = getterTitle;
        getterList = new JList();
        adderList =  new JList(new DefaultListModel());
        trueAdderValues = new ArrayList();
        trueGetterValues = new ArrayList();
        setSize(700,500);
        addAcceptButton();
        addCloseButton();
        deploy();
    }

    public void setGetterValues(ArrayList<String> trueGetterValues){
        this.trueGetterValues.addAll(trueGetterValues);
        addModels(trueGetterValues);
    }

    public void setGetterValues(Table trueGetterValues){
        if(trueGetterValues.columnCount() == 2){
            this.trueGetterValues.addAll(trueGetterValues.getColumn(1));
            addModels(getConcactedValues(trueGetterValues));
        }
        else
            setGetterValues(trueGetterValues.getColumn(0));
    }



    private ArrayList<String> getConcactedValues(Table trueGetterValues){
        ArrayList<String> concacted = new ArrayList<>();

        for (ArrayList<String> row: trueGetterValues.getRegisters()){
            String text = row.get(0) + " (" + row.get(1) + ")";
            concacted.add(text);
        }

        return concacted;
    }


    private void addModels(ArrayList<String> list){
        DefaultListModel model = new DefaultListModel();
            model.addAll(list);

        getterList.setModel(model);

    }

    private void deploy(){
        JPanel body = new JPanel(new GridBagLayout());
        body.add(getListContainer(getterTitle,getterList),listCons(0));
        body.add(getButtonsPanel(),getNotFill(1));
        body.add(getListContainer(getterTitle + " a agregar",adderList),listCons(2));
        addBody(body);


    }

    private JPanel getButtonsPanel(){
        JPanel p = new JPanel(new GridBagLayout());
            p.setBorder(BorderFactory.createEmptyBorder(0,5,0,5));
            p.setOpaque(false);

        JPanel centeredPanel = new JPanel(new GridLayout(2,1));
            centeredPanel.add(getAddButton());
            centeredPanel.add(getRemoveButton());

            p.add(centeredPanel);

        return p;
    }

    public ArrayList<String> getAddedValues(){
        return trueAdderValues;
    }

    private BtnFE getAddButton (){
        BtnFE addBtn = getDefaultButton("Añadir");
            addBtn.setBackground(new Color(84, 160, 255));
            addBtn.setForeground(Color.white);
            addBtn.setMargins(0,0,10,0,body.getBackground());
            addBtn.addMouseListener(getSwitchEvent(adderList,getterList,trueAdderValues,trueGetterValues));


        return addBtn;
    }

    private BtnFE getRemoveButton (){
        BtnFE addBtn = getDefaultButton("Eliminar");
        addBtn.setBackground(new Color(200, 200, 200));
        addBtn.setForeground(Color.black);
        addBtn.addMouseListener(getSwitchEvent(getterList,adderList,trueGetterValues,trueAdderValues));

        return addBtn;
    }

    private MouseAdapter getSwitchEvent(JList adderJList,JList getterJList,ArrayList<String> adderList,ArrayList<String> getterList){
        return new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                int selectedvalue = getterJList.getSelectedIndex();
                if(selectedvalue >= 0) {
                    String ListAux = getterList.get(selectedvalue);
                    getterList.remove(selectedvalue);
                    adderList.add(ListAux);

                    String JListAux = (String) getterJList.getSelectedValue();
                    ((DefaultListModel) getterJList.getModel()).remove(selectedvalue);

                    ((DefaultListModel) adderJList.getModel()).addElement(JListAux);
                }
            }
        };

    }

    private BtnFE getDefaultButton(String text){
        BtnFE btn = new BtnFE(text);
            btn.setPadding(10,30,10,30);

            return btn;
    }

    private GridBagConstraints listCons(int place){
        GridBagConstraints cons = getNotFill(place);
        cons.weightx = 1;
        cons.weighty = 1;
        cons.fill = GridBagConstraints.BOTH;

        return cons;
    }

    private GridBagConstraints getNotFill(int place){
        GridBagConstraints cons = new GridBagConstraints();
        cons.gridx = place;
        cons.gridy = 0;

        return cons;
    }

    private JScrollPane getListContainer(String title,JList list){

        JPanel a = new JPanel(new BorderLayout());
            a.setOpaque(false);
            a.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel titleLB = new JLabel(title);
            titleLB.setFont(new Font("Monospaced", Font.PLAIN, 15));
            titleLB.setForeground(new Color(34, 47, 62));
            titleLB.setBorder(BorderFactory.createEmptyBorder(0,0,20,0));
        a.add(titleLB);

        JPanel listContainer = new JPanel(new GridLayout(1,1));
            listContainer.add(list);

        a.add(titleLB,BorderLayout.NORTH);
        a.add(listContainer,BorderLayout.CENTER);

        JScrollPane scroll = new JScrollPane();
            scroll.setBorder(null);
        scroll.setViewportView(a);

        return scroll;
    }



}


