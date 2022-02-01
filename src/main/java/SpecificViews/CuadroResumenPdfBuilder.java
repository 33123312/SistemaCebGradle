package SpecificViews;

import JDBCController.Table;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.Cell;
import sistemaceb.SemestreOperator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CuadroResumenPdfBuilder extends PDFPlantillaTable{

    int evaluacion;
    String semestre;
    private Map<String,ArrayList<MateriaGrupoInfo>> gruposInfoCollection;

    public CuadroResumenPdfBuilder(String semestre,String evaluacion) {
        super("Cuadro Resumen");
        this.evaluacion = getEvaNumberFromString(evaluacion);
        this.semestre = semestre;
        gruposInfoCollection = new HashMap<>();
        addMembreteLargo();
        buildPdf();
    }

    private void setParams(){
        Map<String,String> params = new HashMap<>();
            params.put("Semestre",semestre);
            params.put("Evaluación",evaluacion + "");

        addParams(params);
    }

    private void buildPdf(){
        SemestreOperator operator = new SemestreOperator(semestre);
        ArrayList<String> grupos =  operator.getGrupos();
        for (String grupo: grupos)
            addGrupoRow(grupo);
        showPdf();
    }

    private void addGrupoRow(String grupo){
        GrupoOperator operator = new GrupoOperator(grupo);
        GrupoOperator.GrupoBoletaOperator boleta = operator.getGrupoBoletaOperator();

        Table materias = operator.getMaterias("Numérica");

        if(!materias.isEmpty()){
            ArrayList<String> materiasKeys = materias.getColumn(0);
            ArrayList<String> materiasNombres = materias.getColumn(1);

            for (int i = 0; i < materiasKeys.size();i++) {
                String key = materiasKeys.get(i);
                String materiaName = materiasNombres.get(i);

                setGrupoMateriaInfo(boleta,key,materiaName);
            }
        }
    }

    private void addRregister(String name, MateriaGrupoInfo infoPackage) {
        if (!gruposInfoCollection.containsKey(name))
            gruposInfoCollection.put(name,new ArrayList<>());

        gruposInfoCollection.get(name).add(infoPackage);
    }

    private void setGrupoMateriaInfo(GrupoOperator.GrupoBoletaOperator boleta,String materia,String name){
        MateriaGrupoInfo info = new MateriaGrupoInfo(boleta,materia);
        addRregister(name,info);

    }

    private int getEvaNumberFromString(String eva) {
        if (eva.equals("1ra"))
            return 0;
        if (eva.equals("2ra"))
            return 1;
        if (eva.equals("3ra"))
            return 2;

        return -21;
    }

    private void showPdf(){
        setParams();
        addTables();
        close();

    }

    private void addTables(){
        for (Map.Entry<String,ArrayList<MateriaGrupoInfo>> entry: gruposInfoCollection.entrySet())
            addNewTable(entry.getKey(),entry.getValue());

        if (rowCount != 0)
        add(currentSiderTable);
    }

    com.itextpdf.layout.element.Table currentSiderTable = redefineSlide();
    int rowCount;

    private com.itextpdf.layout.element.Table redefineSlide(){
        com.itextpdf.layout.element.Table currentSiderTable = new com.itextpdf.layout.element.Table(new float[]{1,1});
            currentSiderTable.setBorder(Border.NO_BORDER).
            setWidthPercent(100);

        rowCount = 0;
        return  currentSiderTable;
    }

    private void addToSider(com.itextpdf.layout.element.Table newTable) {
        if (rowCount == 2){
            add(currentSiderTable);
            currentSiderTable = redefineSlide();
        }

        Cell newCell = new Cell().add(newTable).setBorder(Border.NO_BORDER);
        currentSiderTable.addCell(newCell);

        rowCount++;

    }

    private void addNewTable(String materia,ArrayList<MateriaGrupoInfo> grupoInfo){
        ArrayList<String> promedios = new ArrayList<>();
        ArrayList<String> porcentajesRepr = new ArrayList<>();
        ArrayList<String> sumaRepr = new ArrayList<>();

        com.itextpdf.layout.element.Table newTable =
                new com.itextpdf.layout.element.Table(new float[]{1,1,1,1});

        newTable.addCell(addTitleStyle(getCellDeftyle(materia,new Cell(0,4))));
            newTable.addCell(getDefCell("Grupo"));
            newTable.addCell(getDefCell("Promedio"));
            newTable.addCell(getDefCell("Porc. de Repr."));
            newTable.addCell(getDefCell("Num. Reprob."));

        for (MateriaGrupoInfo info: grupoInfo){

            newTable.addCell(getDefCell(info.getGrupo()));

            promedios.add(info.getPromedioTotal());
            newTable.addCell(getDefCell(info.getPromedioTotal()));

            porcentajesRepr.add(info.getPorcentajeRepro());
            newTable.addCell(getDefCell(info.getPorcentajeRepro()));

            sumaRepr.add(info.getReprobados() );
            newTable.addCell(getDefCell(info.getReprobados()));
        }

        newTable.addCell(addTitleStyle(getDefCell("Totales ")));
        newTable.addCell(PromsOperations.getProm(promedios));
        newTable.addCell(PromsOperations.getProm(porcentajesRepr));
        newTable.addCell(PromsOperations.getSumaFaltas(sumaRepr));

        newTable.setMarginBottom(5);

        addToSider(newTable);


    }

    private class MateriaGrupoInfo{
        String grupo;
        String promedioTotal;
        String porcentajeRepro;
        String reprobados;

        MateriaGrupoInfo(GrupoOperator.GrupoBoletaOperator operator, String materia) {
            this.grupo = operator.grupo;

            GrupoOperator.GrupoMateriaOperator operator1 = operator.getGrupoMateriaOp(materia);

            this.porcentajeRepro = operator1.getPorcentajeReprobadosEnUnidad(evaluacion);
            this.promedioTotal = operator1.getPromedioUnidad(evaluacion);
            this.reprobados = operator1.getReprobadosEnUnidad(evaluacion) + "";

        }

        @Override
        public String toString() {
            return "MateriaGrupoInfo{" +
                    "grupo='" + grupo + '\'' +
                    ", promedioTotal='" + promedioTotal + '\'' +
                    ", porcentajeRepro='" + porcentajeRepro + '\'' +
                    ", reprobados='" + reprobados + '\'' +
                    '}';
        }

        public String getReprobados() {
            return reprobados;
        }

        public String getPromedioTotal() {
            return promedioTotal;
        }

        public String getGrupo() {
            return grupo;
        }

        public String getPorcentajeRepro() {
            return porcentajeRepro;
        }
    }


    }


