package sistemaceb.form;

import sistemaceb.ListButton;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class MenuListsContainer extends JPanel {

    private int gridCount;
    private JPanel pusher;


    public MenuListsContainer(){
        gridCount = 0;
        pusher =getPusher();
        setOpaque(false);
        addPusher();
        setLayout(new GridBagLayout());
    }

    private JPanel getPusher(){
        JPanel p= new JPanel();
        p.setOpaque(false);
        p.setBackground(Color.BLUE);
        return p;
    }

    private GridBagConstraints getCons(){
        GridBagConstraints cons = new GridBagConstraints();
            cons.gridy = gridCount;
            cons.weightx = 1;
            cons.fill = GridBagConstraints.HORIZONTAL;

        gridCount++;
        return cons;
    }

    private void addPusher(){

        GridBagConstraints cons = new GridBagConstraints();
        cons.gridx = 0;
        cons.gridy = gridCount;
        cons.weighty = 1;
        cons.fill = GridBagConstraints.BOTH;

        add(pusher,cons);
    }

    public ListButton addNewSubmenu(String title, String iconUrl)  {
        remove(pusher);
        BufferedImage icon = null;
        try {
            icon = ImageIO.read(getClass().getResource(iconUrl));
        } catch ( IOException e){}

        ListButton menuTablas = new ListButton(title,icon);
        add(menuTablas,getCons());

        addPusher();
        return menuTablas;
    }



}
