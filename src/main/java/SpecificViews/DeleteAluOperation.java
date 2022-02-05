package SpecificViews;

import sistemaceb.AlumnoRemover;
import sistemaceb.form.FormDialogMessage;
import sistemaceb.form.Global;
import sistemaceb.genericEvents;

import java.util.ArrayList;

public class DeleteAluOperation extends Operation{

    public DeleteAluOperation(OperationInfoPanel registerDetail) {
        super(registerDetail);
        operation = "Desaparecer Alumno";
    }

    @Override
    public void buildOperation() {
        FormDialogMessage form = new FormDialogMessage(
                "Advertencia",
                "Esta acci�n borrar� toda la informaci�n del alumno, incluyendo sus calificaciones en el periodo actual y anteriores," +
                        " considere el dar de baja al alumno en lugar de borrarlo, lo cual s�lo cambiar� su grupo a un valor nulo, de esa " +
                        "manera, se conservar� su informaci�n y el alumno quedara efectivamente fuera del sistema. <br><br> Presione ACEPTAR si a�n as� desea borrar al alumno.");
        form.addCloseButton();
        form.addAcceptButton();
        form.addOnAcceptEvent(new genericEvents() {
            @Override
            public void genericEvent() {
                ArrayList<String> alumnos = new ArrayList<>();
                    alumnos.add(keyValue);
                new AlumnoRemover(alumnos);
                form.closeForm();
                Global.view.currentWindow.cut();
            }
        });


    }
}
