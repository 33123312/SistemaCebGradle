package SpecificViews;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LinearHorizontalLayout extends JPanel{



    GridBagConstraints cons;
    JLabel filler;
    public LinearHorizontalLayout(){
        super(new GridBagLayout());
        defineCons();
        filler = new JLabel();
        add(filler);
    }

    private void defineCons(){
        cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.fill = GridBagConstraints.BOTH;
        cons.weighty = 1;

    }

    private void addFiller(){
        cons.weightx = 1;
        add(filler,cons);
        cons.weightx = 0;
    }

    public void addElement(Component element) {
        remove(filler);
        add(element,cons);
        cons.gridx++;
        addFiller();
    }

    public void removeElement(JComponent element) {
        List<Component> components =  Arrays.asList(getComponents());
        components.remove(element);
        components.remove(filler);
        removeAll();
        for(Component com: components)
            addElement(com);
        addFiller();

    }
}
