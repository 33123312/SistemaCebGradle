package sistemaceb;

import JDBCController.Table;
import java.util.ArrayList;

public class BoletaSorter {

    private Table order;
    private ArrayList<String> mixedMat;
    private ArrayList<ArrayList<String>> mixedReg;

    public BoletaSorter(Table numCalif,Table oblCalif, Table order){
        this.order = order;
        mixArrays(numCalif, oblCalif);

    }

    public Table getSortedAndNamed(){
        ArrayList<String> order = this.order.getColumn(0);
        ArrayList<String> names = this.order.getColumn(1);

        ArrayList<ArrayList<String>> sorted = new ArrayList<>();

        System.out.println(mixedMat);
        System.out.println(mixedReg);

        for (String mat:order){
            int matIndex = mixedMat.indexOf(mat);
            sorted.add(mixedReg.get(matIndex));
        }

        return new Table(names,sorted);
    }

    private void mixArrays(Table numCalif,Table bolCalif){
        mixedMat = numCalif.getColumnTitles();
            mixedMat.addAll(bolCalif.getColumnTitles());

        mixedReg = numCalif.getRegisters();
            mixedReg.addAll(bolCalif.getRegisters());

    }

}
