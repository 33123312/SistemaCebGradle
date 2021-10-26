package SpecificViews;
import JDBCController.dataType;
import sistemaceb.form.Formulario;

public class GrupoSemCalChoserWindow extends GrupoCalifChoserWindow{

    public GrupoSemCalChoserWindow(String grupo, String materia) {
        super(getManager(grupo,materia));
    }

    private static GrupoCalifChargOpCont getManager(String grupo, String materia) {
        GrupoCalifChargOpCont manager =
                new GrupoCalifChargOpCont(
                        grupo,
                        materia,
                        "calificaciones_semestrales",
                        "sem_calif_con_pro"
                );
        return manager;
    }

    @Override
    protected Formulario createForm() {
        VerticalFormulario formulario = new VerticalFormulario();
            formulario.addTitle("Ingresar Valores");
            addSelectionInput(formulario.addInput("Calificación", dataType.FLOAT).setTrueTitle("calificacion").addErrorChecker(getMaxCalifErrorComp()));;


        return formulario;
    }
}
