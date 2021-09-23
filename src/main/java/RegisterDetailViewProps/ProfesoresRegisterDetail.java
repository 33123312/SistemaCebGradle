package RegisterDetailViewProps;

import SpecificViews.AnalisisProfesorOperation;
import SpecificViews.OperationInfoPanel;
import SpecificViews.ProfesorHorario;

public class ProfesoresRegisterDetail extends RegisterDetail{
    public ProfesoresRegisterDetail(OperationInfoPanel infoPanel) {
        super(infoPanel);
        addOperation(new AnalisisProfesorOperation(infoPanel));
        addOperation(new ProfesorHorario(infoPanel));
    }
}
