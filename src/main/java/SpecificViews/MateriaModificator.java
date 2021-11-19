package SpecificViews;

import JDBCController.TableRegister;
import sistemaceb.form.Formulario;

import java.util.ArrayList;

public class MateriaModificator extends DefaultModifyRegisterOp{
    public MateriaModificator(OperationInfoPanel infoPanlel) {
        super(infoPanlel);
    }

    @Override
    public void buildOperation() {
        super.buildOperation();
    }

    @Override
    protected void addToTagFormBuilder(ArrayList<String> columnsToUpdate) {
        ArrayList<String> tags = viewSpecs.getTableTags();
            //tags.remove(4);
        super.addToTagFormBuilder(tags);
    }

    protected void setDefaultValues(Formulario form){
        TableRegister viewRegiter = registerInfo;

        ArrayList<String> titles = new ArrayList<>(viewRegiter.getColumnTitles());
        ArrayList<String> values = new ArrayList<>(viewRegiter.getValues());

        form.setDefaultValues(titles,values);

    }
}
