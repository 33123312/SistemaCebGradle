package SpecificViews;

import JDBCController.ViewSpecs;
import RegisterDetailViewProps.RegisterDetail;
import sistemaceb.Window;
import sistemaceb.form.Global;

public class OperationWindow extends Operation{
    Window thisWindow;

    public OperationWindow(OperationInfoPanel infoPanlel) {
        super(infoPanlel);
    }

    @Override
    public void buildOperation() {
        thisWindow = new Window();
        Global.view.currentWindow.newView(thisWindow);
    }

    public void cutOperation(){
        Global.view.currentWindow.cut();

    }
}
