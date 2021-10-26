package SpecificViews;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class GrupoEvacalWindow extends GrupoCalifChoserWindow {

    public GrupoEvacalWindow(String grupo, String materia, String eva,String view,String procedure) {
        super(new GrupoCalifChargOpCont(
                grupo,
                materia,
                encapsulateMap(eva),
                view,
                encapsulate("faltas"),
                encapsulate("Faltas"),
                procedure
        ));
    }

    private static Map<String, String> encapsulateMap(String eva) {
        Map<String, String> enc = new HashMap<>();
            enc.put("evaluacion", eva);
        return enc;
    }

    private static ArrayList<String> encapsulate(String eva) {
        ArrayList<String> list = new ArrayList<>();
        list.add(eva);

        return list;
    }

}
