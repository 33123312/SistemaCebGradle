package sistemaceb.form;

import SpecificViews.LinearHorizontalLayout;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class HorizontalFormPanel extends Formulario{
    public LinearHorizontalLayout elementsArea;
    JPanel generalArea;

    public HorizontalFormPanel(){
        super();

        setLayout(new GridLayout(1,1));
        setBorder(new EmptyBorder(10,0,0,0));
        generalArea =  new JPanel(new BorderLayout());
        generalArea.setForeground(Color.white);
        add(generalArea);
        deploy();

    }

    private void  deploy(){
        setBackground(Color.white);
        setBorder(BorderFactory.createMatteBorder(1,0,1,0,new Color(240,240,240)));
        generalArea.add(buildForm(),BorderLayout.CENTER);

    }

    public void addLateral(JComponent component){
        generalArea.add(component,BorderLayout.WEST);

        laterals = component;
    }

    JComponent laterals;

    public void addTitle(String hora){

        JLabel title = getTitle(hora);
        addLateral(title);
    }

    public JComponent getLateral(){
        return laterals;
    }

    private JLabel getTitle(String hora){
        JLabel horaLabel = new JLabel(hora);
        horaLabel.setFont(new Font("arial", Font.PLAIN, 20));
        horaLabel.setBorder(new EmptyBorder(0,10,0,10));
        horaLabel.setForeground(new Color(100,100,100));

        return horaLabel;
    }


    private JPanel buildForm(){
        elementsArea = new LinearHorizontalLayout();
            elementsArea.setBackground(Color.white);
            elementsArea.setBorder(new EmptyBorder(10,0,0,0));

        return elementsArea;
    }

    @Override
    protected void addElement(FormElement element) {
        elementsArea.addElement(element);
    }


}

