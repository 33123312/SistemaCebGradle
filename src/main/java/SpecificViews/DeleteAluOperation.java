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
                "Esta acción borrará toda la información del alumno, incluyendo sus calificaciones en el periodo actual y anteriores," +
                        " considere el dar de baja al alumno en lugar de borrarlo, lo cual sólo cambiará su grupo a un valor nulo, de esa " +
                        "manera, se conservará su información y el alumno quedara efectivamente fuera del sistema. <br><br> Presione ACEPTAR si aún así desea borrar al alumno.");
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
