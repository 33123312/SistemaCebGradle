package SpecificViews;

import java.util.ArrayList;

public class GrupoPasserInfoStorage {
    private ArrayList<String> selectedAluIndexes;
    private String selectedNextGrupo;
    private String grupo;

    public GrupoPasserInfoStorage(String grupo,String nextGrupo, ArrayList<String> selectedAlu){
        selectedAluIndexes = selectedAlu;
        selectedNextGrupo = nextGrupo;
        this.grupo = grupo;

    }


    public String getGrupo() {
        return grupo;
    }

    public ArrayList<String> getSelectedAluIndexes() {
        return selectedAluIndexes;
    }

    public String getSelectedNextGrupo() {
        return selectedNextGrupo;
    }
}

