package SpecificViews;

import RegisterDetailViewProps.RegisterDetail;

public class MateriasRegisterProps extends RegisterDetail {
    public MateriasRegisterProps(OperationInfoPanel infoPanel) {
        super(infoPanel);
        //overRideDefOp("modificar",new MateriaModificator(infoPanel));
        addPill(new PlanesestudioViewerMat());
        addRemovedPills("asignaturas");
    }
}
