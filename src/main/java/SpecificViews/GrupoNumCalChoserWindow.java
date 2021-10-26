package SpecificViews;

import JDBCController.dataType;
import sistemaceb.form.Formulario;

public class GrupoNumCalChoserWindow extends GrupoEvacalWindow{
    public GrupoNumCalChoserWindow(String grupo, String materia, String eva) {
        super(grupo, materia, eva,"calificaciones_numericas","num_calif_con_pro");
    }

    @Override
    protected Formulario createForm() {
        VerticalFormulario form = new VerticalFormulario();
        form.addTitle("Ingresar Valores");
        addSelectionInput(
                form.addInput(
                        "Calificación",dataType.FLOAT).setTrueTitle("calificacion").addErrorChecker(getMaxCalifErrorComp()));
        addSelectionInput(
                form.addInput(
                        "Faltas", dataType.INT).setTrueTitle("faltas"));


        return form;
    }

}
