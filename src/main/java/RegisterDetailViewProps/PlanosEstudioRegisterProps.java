package RegisterDetailViewProps;

import JDBCController.ViewSpecs;
import SpecificViews.*;
import sistemaceb.RegisterDetailTable;

public class PlanosEstudioRegisterProps extends RegisterDetail{

    public PlanosEstudioRegisterProps(OperationInfoPanel infoPanel) {
        super(infoPanel);
        overRideDefOp("modificar",new PlanosModificationOp(infoPanel));
        addPill(new RegisterDetailTableTrigerer("planes_estudio_materias","Materias") {
            @Override
            public RegisterDetailTable getTable(String critKey, ViewSpecs dadSpecs) {
                return new newMateriPlanos(relatedTable,critKey,dadSpecs);
            }
        });

        addPill(    new RegisterDetailTableTrigerer("plan_grupo","Plan de Estudios - Grupos") {
            @Override
            public RegisterDetailTable getTable(String critKey, ViewSpecs dadSpecs) {
                return new PlanoGrupoViewerPill(relatedTable,critKey,dadSpecs);
            }
        });

    }


}
