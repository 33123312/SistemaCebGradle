package SpecificViews;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class LinearVerticalLayout extends JPanel {

    GridBagConstraints cons;
    JLabel filler;

    public LinearVerticalLayout(){
        super(new GridBagLayout());
        defineCons();
        filler = new JLabel();
        add(filler);
    }

    private void defineCons(){
        cons = new GridBagConstraints();
        cons.gridy = 0;
        cons.fill = GridBagConstraints.BOTH;
        cons.weightx = 1;

    }

    public boolean containsElement(Component element){
        Component[] components = getComponents();

        for (Component component: components)
            if (component == element)
                return true;

        return false;
    }

    public int getCount(){
        return cons.gridy;
    }

    private void addFiller(){
        cons.weighty = 1;
        add(filler,cons);
        cons.weighty = 0;
    }

    public void addElement(Component element) {
        remove(filler);
        add(element,cons);
        cons.gridy++;
        addFiller();
    }

    protected void removeElement(JComponent element) {
        List<Component> components =  Arrays.asList(getComponents());
        components.remove(element);
        components.remove(filler);
        removeAll();
        for(Component com: components)
            addElement(com);
        addFiller();

    }

}
