package sistemaceb;

import DInamicPanels.DinamicPanel;

public class DInamicWindow extends DinamicPanel {

    public ViewAdapter currentWindow;
    public DInamicWindow(){
        super();

    }

    public void setNewView(ViewAdapter view) {
        genericEvents closeE = new genericEvents() {
            @Override
            public void genericEvent() {
                setView(view);
                view.getWIndow().update();
                currentWindow = view;
            }
        };

        if (currentWindow == null)
            closeE.genericEvent();
        else
            currentWindow.trytoClose(closeE);
    }
}
