package RegisterDetailViewProps;
import SpecificViews.*;

public class PlanosEstudioRegisterProps extends RegisterDetail{

    public PlanosEstudioRegisterProps(OperationInfoPanel infoPanel) {
        super(infoPanel);
        overRideDefOp("eliminar", new DeletePlanosOp(infoPanel));
        overRideDefOp("modificar", new PlanosModificationOp(infoPanel));
        addPill(new PlanosMateriasPills());
        addPill(new PlanoGrupoViewerPill());

    }


}
