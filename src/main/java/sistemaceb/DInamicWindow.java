package sistemaceb;

import DInamicPanels.DinamicPanel;

import javax.swing.*;

public class DInamicWindow extends DinamicPanel {

    public ViewAdapter currentWindow;
    public DInamicWindow(){
        super();

    }

    public void setView(ViewAdapter view) {
        super.setView(view);
        currentWindow = view;
    }
}
