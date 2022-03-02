package RegisterDetailViewProps;

import SpecificViews.AnalisisProfesorOperation;
import SpecificViews.OperationInfoPanel;
import SpecificViews.ProfPassOp;
import SpecificViews.ProfesorHorario;

public class ProfesoresRegisterDetail extends RegisterDetail{
    public ProfesoresRegisterDetail(OperationInfoPanel infoPanel) {
        super(infoPanel);
        //addOperation(new AnalisisProfesorOperation(infoPanel));
        addOperation(new ProfesorHorario(infoPanel));
        addOperation(new ProfPassOp(infoPanel));
    }
}
