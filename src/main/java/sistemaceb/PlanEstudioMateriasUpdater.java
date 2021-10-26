package sistemaceb;

import JDBCController.ViewSpecs;
import SpecificViews.GrupoOperator;

import java.sql.SQLException;
import java.util.ArrayList;

public class PlanEstudioMateriasUpdater {
    ArrayList<String> newPlanMaterias;
    ArrayList<String > oldPlanMaterias;
    ArrayList<String> pushedMaterias;
    String grupo;

    public PlanEstudioMateriasUpdater(String grupo){
        this.grupo = grupo;
        oldPlanMaterias = getMaterias(grupo);

    }
    private ArrayList<String> getMaterias(String grupo){
        GrupoOperator op = new GrupoOperator(grupo);
        return op.getMaterias().getColumn(0);
    }

    private void definePushedMaterias(){
        pushedMaterias = new ArrayList<>();

        for (String newMateria:oldPlanMaterias)
            if(!newPlanMaterias.contains(newMateria))
                pushedMaterias.add(newMateria);

    }

    private void deletefromAsignturas(){
        deleteMateriaGrupoFromTable("asignaturas");

    }

    private void deletefromHorarios(){
        deleteMateriaGrupoFromTable("horarios");

    }

    private void deleteMateriaGrupoFromTable(String table){
        for (String value: pushedMaterias){
            ArrayList<String> cond = new ArrayList<>();
                cond.add("grupo");
                cond.add("materia");

            ArrayList<String> values = new ArrayList<>();
                values.add(grupo);
                values.add(value);

            try {
                new ViewSpecs(table).getUpdater().delete(cond,values);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void makeUpdate(){
        newPlanMaterias = getMaterias(grupo);
        definePushedMaterias();
        deletefromAsignturas();
        deletefromHorarios();
    }
}
