package SpecificViews;

import JDBCController.DataBaseConsulter;
import JDBCController.TableRegister;
import JDBCController.ViewSpecs;
import sistemaceb.FormResponseManager;
import sistemaceb.form.Formulario;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class GrupoModificatorOp extends DefaultModifyRegisterOp{

    public GrupoModificatorOp(OperationInfoPanel infoPanlel) {
        super(infoPanlel);
    }

    @Override
    public void buildOperation() {
        GrupoModificatorListener listener = new GrupoModificatorListener();
        super.buildOperation();
        updateForm.addDataManager(new FormResponseManager() {
            @Override
            public void manageData(Formulario form) {
                Map<String,String> data = form.getData();
                listener.checkValues(data.get("Semestre"),data.get("Turno"));
            }
        });
    }

    private class GrupoModificatorListener{

        String oldSemestre;
        String oldTurno;
        String grupo;

        GrupoModificatorListener(){
            grupo = keyValue;
            getOldValues();

        }

        private void getOldValues(){
            TableRegister response = consultGrupoInfo();

            oldSemestre = response.get("semestre");
            oldTurno = response.get("turno");

        }

        private TableRegister consultGrupoInfo(){
            DataBaseConsulter consulter = new DataBaseConsulter("grupos");

            ArrayList<String> cond = new ArrayList();
            cond.add("grupo");

            ArrayList<String> values = new ArrayList<>();
            values.add(grupo);
            TableRegister register = consulter.bringTable(new String[]{"grupo"},new String[]{grupo}).getRegister(0);
            return register;
        }

        public void checkValues(String newSemestre,String newTurno){

            if(!newSemestre.equals(oldSemestre) || !newTurno.equals(oldTurno))
                deleteHorarios();

        }

        private void deleteHorarios(){
            ArrayList<String> cond = new ArrayList();
                cond.add("grupo");

            ArrayList<String> values = new ArrayList<>();
                values.add(grupo);

            try {
                new ViewSpecs("horarios").getUpdater().delete(cond,values);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }
}
