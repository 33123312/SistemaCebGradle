package sistemaceb;

import DInamicPanels.View;
import sistemaceb.form.Global;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ViewAdapter extends  JPanel{

    public ViewAdapter back ;
    public ViewAdapter further ;
    private JPanel buttons = new JPanel (new GridLayout(1,1));
    public Window thisWindow;


    public ViewAdapter(Window view){
        setLayout(new GridLayout(1,1));
        add(view);
        back = null;
        further = null;
        thisWindow = view;
        view.addButtons(getButtons());
        view.update();
    }
    protected void changeView(ViewAdapter nextView){
        addToContainer(nextView);
        Global.view.currentWindow = nextView;

    }


    public Window getWIndow(){
        return thisWindow;
    }

    public void  goBack(){
        changeView(back);
    }

    public void goFurther(){
        changeView(further);

    }

    private void repointBack(ViewAdapter view){
        if(hasBack())
            back.setFurther(view);
        if(view != null)
            view.setBack(back);

    }

    private void repointFurther(ViewAdapter view){
        if (hasFurther())
            further.setBack(view);
        if(view != null)
            view.setFurther(further);
    }

    public void replaceView(Window content){
        ViewAdapter newView = new ViewAdapter(content);
        if(hasBack())
            repointBack(newView);
        if(hasFurther())
            repointFurther(newView);

        addToContainer(newView);

    }

    public void newView(Window content){
        ViewAdapter newView = new ViewAdapter(content);
        setFurther(newView);
        newView.setBack(this);
        changeView(newView);

    }

    public void cut(){
            back.setFurther(null);
            goBack();

    }

    private boolean hasBack(){
        return back != null;
    }

    private boolean hasFurther(){
        return further != null;
    }


    private void setBack(ViewAdapter newView){
        back = newView;
        updateButtons();
    }

    private void setFurther(ViewAdapter newView){
        further = newView;
        updateButtons();
    }

    private void addToContainer(ViewAdapter view){
        Container parent = Global.view;
        parent.setVisible(false);
        parent.removeAll();
        parent.add(view);
        parent.setVisible(true);

    }



    public JPanel getButtons(){
        buttons.setBorder(BorderFactory.createEmptyBorder(0,5,0,5));
        buttons.setPreferredSize(new Dimension(64,32));
        buttons.setOpaque(false);

        return buttons;
    }

    private JPanel updateButtons(){
        JPanel buttonsContainer = new JPanel(new GridLayout(1,2));
        buttonsContainer.setOpaque(false);
        buttonsContainer.add(placeBackButton());
        buttonsContainer.add(placeFurtherButton());

        buttons.removeAll();
        buttons.add(buttonsContainer);

        return buttonsContainer;
    }
    private  JLabel placeFurtherButton(){
        return prepairButton("images/proximo.png",further);

    }

    private  JLabel placeBackButton(){

        return prepairButton("images/atras.png",back);
    }

    private JLabel prepairButton(String url, final ViewAdapter type ){

        JLabel button ;
        if (type == null){
            button = new JLabel();
        } else{
            button = new JLabel(new ImageIcon(this.getClass().getResource(url)));
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            button.addMouseListener(new MouseAdapter(){

                @Override
                public void mousePressed(MouseEvent e){
                    changeView(type);
                }
            });
        }
        return button;
    }

}
