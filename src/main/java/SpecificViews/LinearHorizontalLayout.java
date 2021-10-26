package SpecificViews;

import javax.swing.*;
import java.awt.*;
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
        setVisible(false);
        remove(filler);
        super.add(element,cons);
        cons.gridx++;
        addFiller();
        setVisible(true);
    }

    public void removeElement(int element) {
        List<Component> components =  Arrays.asList(getComponents());
        removeElement(components.get(element));

    }

    public void removeElement(Component element) {
        setVisible(false);
            remove(element);
            remove(filler);
        addFiller();
        setVisible(true);
    }
}
