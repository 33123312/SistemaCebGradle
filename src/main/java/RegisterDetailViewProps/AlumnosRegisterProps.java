package RegisterDetailViewProps;

import SpecificViews.AlumnoDeleteOp;
import SpecificViews.BoletaBuilder;
import SpecificViews.BoletaConsulter;
import SpecificViews.OperationInfoPanel;

public class AlumnosRegisterProps extends  RegisterDetail{
    public AlumnosRegisterProps(OperationInfoPanel infoPanel) {
        super(infoPanel);
        overRideDefOp("eliminar",new AlumnoDeleteOp(infoPanel));
        addOperation(new BoletaConsulter(infoPanel));
        addOperation(new BoletaBuilder(infoPanel));
    }
}
