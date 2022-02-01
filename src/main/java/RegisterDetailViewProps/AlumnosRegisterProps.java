package RegisterDetailViewProps;

import SpecificViews.*;

public class AlumnosRegisterProps extends  RegisterDetail{
    public AlumnosRegisterProps(OperationInfoPanel infoPanel) {
        super(infoPanel);
        overRideDefOp("eliminar",new AlumnoDeleteOp(infoPanel));
        addOperation(new BoletaConsulter(infoPanel));
        addOperation(new BoletaBuilder(infoPanel));
        addOperation(new DeleteAluOperation(infoPanel));
        addOperation(new GrupoPassOp(infoPanel));

    }
}
