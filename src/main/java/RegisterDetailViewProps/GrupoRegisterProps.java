package RegisterDetailViewProps;

import JDBCController.DataBaseConsulter;
import JDBCController.TableRegister;
import JDBCController.ViewSpecs;
import SpecificViews.*;
import sistemaceb.RegisterDetailTable;

import java.util.ArrayList;

public class GrupoRegisterProps extends RegisterDetail{
    public GrupoRegisterProps(OperationInfoPanel infoPanel) {
        super(infoPanel);
        overRideDefOp("modificar", new GrupoModificatorOp(infoPanel));
        addPills();
        addOps();

    }

    private void addPills(){
        addPill(new RegisterDetailTableTrigerer("asignaturas","Asignaturas") {
            @Override
            public RegisterDetailTable getTable(String critKey, ViewSpecs dadSpecs) {

                return new AsignturasPillChoser(relatedTable,critKey,dadSpecs);
            }
        });

        addPill(            new RegisterDetailTableTrigerer("plan_grupo","Plan de Estudios - Grupos") {
            @Override
            public RegisterDetailTable getTable(String critKey, ViewSpecs dadSpecs) {
                return new PlanoGrupoViewerPill(relatedTable,critKey,dadSpecs);
            }
        });

    }

    private void addOps(){

        addOperation(new HorarioBuilder(infoPanel));
        addOperation(new HorarioConsulter(infoPanel));
        addOperation(new GrupoCalifConsulter(infoPanel));
        addOperation(new ListadoAlumnosOp(infoPanel));
        addOperation(new ConcentradoTodasMateriasOp(infoPanel));
        addOperation(new BitacoraMateriaGrupo(infoPanel));
        addOperation(new PlanesEstudioChoserOp(infoPanel));


    }

    TableRegister registerInfo;



}
