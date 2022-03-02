package sistemaceb;

import Generals.BtnFE;
import JDBCController.DataBaseConsulter;
import JDBCController.Table;
import SpecificViews.LinearHorizontalLayout;
import sistemaceb.form.Global;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SemestrePasser {

    public int semestre;
    private ArrayList<String> grupos;
    private ArrayList<String> turnos;
    private ArrayList<String> nextGrupos;
    private Table alumnos;
    private ArrayList<GrupoPasserWindow> grupoPassers;
    private Map<String,Map<String,ArrayList<String>>> currentPasadoresInfoPackage;



    public SemestrePasser(
            int semestre,Table alumnos,Map<String,Map<String,ArrayList<String>>> currentPasadoresInfoPackage)
    {
        this.currentPasadoresInfoPackage = currentPasadoresInfoPackage;
        this.grupoPassers = new ArrayList<>();
        this.semestre = semestre;
        this.alumnos = alumnos;
        grupos = getGrupos();
        turnos = getTurnos();
        if(semestre+1 == 7)
            nextGrupos = new ArrayList<>();
        else
            nextGrupos = getNextGrupos();

    }

    public LinearHorizontalLayout getSemestreGruposLine() {
        LinearHorizontalLayout gruposPanel = new LinearHorizontalLayout();
        gruposPanel.setOpaque(false);

        int i = 0;
        for (String grupo : grupos) {
            BtnFE grupoBtn = getBtnGrupoPasser(grupo,alumnos,nextGrupos,i);
            gruposPanel.addElement(grupoBtn);
            i++;
        }

        return gruposPanel;
    }

    private ArrayList<String> getGrupos(){
        SemestreOperator operator = new SemestreOperator(""+semestre);
        return operator.getGrupos();

    }

    private ArrayList<String> getTurnos(){
        DataBaseConsulter consulter = new DataBaseConsulter("grupos");

        String[] coltToB = new String[]{"turno"};

        String[] cond = new String[]{"semestre"};

        String[] values = new String[]{semestre +""};

        return consulter.bringTable(coltToB,cond,values).getColumn(0);

    }

    public Map<String,Map<String,ArrayList<String>>> getInfo(){
        Map<String,Map<String,ArrayList<String>>> infos = new HashMap<>();
        for (GrupoPasserWindow window:grupoPassers)
            if (window.hasInfo()){
                infos.put(window.grupo,window.getSelectionsInfo());
            }

        return infos;
    }

    private BtnFE getBtnGrupoPasser(String grupo,Table alumnos,ArrayList<String> nextGrupos,int nextGrupo){
        BtnFE btn = new BtnFE(grupo);
        btn.setPadding(3,10,3,10);
        btn.setBackground(new Color(41, 128, 185));
        btn.setTextColor(Color.white);
        btn.setMargins(5,0,5,10,Color.white);
        btn.setFuente(new Font("arial", Font.PLAIN, 15));

        GrupoPasserWindow passer = new GrupoPasserWindow(grupo,"" + semestre, alumnos,nextGrupos,nextGrupo);
        Map<String,ArrayList<String>> storage = gotDefInfo(passer.grupo);
        if (storage != null)
            passer.setDefValues(storage);

        grupoPassers.add(passer);
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                Global.view.currentWindow.newView(passer);
            }
        });

        return btn;
    }

    private Map<String,ArrayList<String>> gotDefInfo(String grupo){

        return  currentPasadoresInfoPackage.get(grupo);
    }

    private ArrayList<String> getNextGrupos(){
        ArrayList<String> newGrupos = new ArrayList<>();
        for (String grupo:grupos){
            int nextSemestre = Character.getNumericValue(grupo.charAt(0)) + 1;
            String newGrupo = nextSemestre + grupo.substring(1,3);
            newGrupos.add(newGrupo);
        }

        return newGrupos;
    }

    public ArrayList<String> getBajas(){
        ArrayList<String> bajas  = new ArrayList<>();

        for (GrupoPasserWindow passerWindow:grupoPassers)
            bajas.addAll(passerWindow.getBajas());

        return bajas;
    }

    public void submit(){

        if(semestre==6){
            removeAllSemestreAlumnos();
            return;
        }

        for(GrupoPasserWindow pasador:grupoPassers)
            pasador.submit();

    }

    private void removeAllSemestreAlumnos(){
        ArrayList<String> alumnos =
                new DataBaseConsulter("alumnos").
                        bringTable(
                                new String[]{"numero_control"},
                                new String[]{"semestre"},
                                new String[]{"6"}
                                ).getColumn(0);
        new AlumnoRemover(alumnos);
    }

    public ArrayList<ArrayList<String>> getNewGrupos(){
        ArrayList<ArrayList<String>> grupos = new ArrayList<>();

        int i = 0;
        for (String newGrupo:nextGrupos){
            ArrayList<String > newGrupoReg = new ArrayList<>();
                newGrupoReg.add(newGrupo);
                newGrupoReg.add("" + (semestre + 1));
                newGrupoReg.add(turnos.get(i));

            grupos.add(newGrupoReg);
        }

        return grupos;
    }

}
