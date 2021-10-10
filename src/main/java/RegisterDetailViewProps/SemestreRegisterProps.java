package RegisterDetailViewProps;

import SpecificViews.CuadroDeHonorOperation;
import SpecificViews.CuadroResumenOP;
import SpecificViews.HorariosPlanosBuilder;
import SpecificViews.OperationInfoPanel;

public class SemestreRegisterProps extends RegisterDetail{
    public SemestreRegisterProps(OperationInfoPanel infoPanel) {
        super(infoPanel);
        addOperation(new HorariosPlanosBuilder(infoPanel));
        addOperation(new CuadroResumenOP(infoPanel));
        addOperation(new CuadroDeHonorOperation(infoPanel));
    }


}
