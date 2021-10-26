package RegisterDetailViewProps;

import JDBCController.DataBaseConsulter;
import JDBCController.TableRegister;
import JDBCController.ViewSpecs;
import SpecificViews.DefaultDeleteRegisterOp;
import SpecificViews.DefaultModifyRegisterOp;
import SpecificViews.Operation;
import SpecificViews.OperationInfoPanel;
import sistemaceb.RegisterDetailTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegisterDetail {

    public OperationInfoPanel infoPanel;
    public Map<String,OperationBuilder> defaultOperations;
    public ArrayList<Operation> operations;
    private ArrayList<RegisterDetailTable> pills;
    private ArrayList<String> removedPills;

    public void addRemovedPills(String pill){

        removedPills.add(pill);
    }

    public ArrayList<String> getRemovedPills() {
        return removedPills;
    }

    public RegisterDetail(OperationInfoPanel infoPanel){
        removedPills = new ArrayList<>();
        this.infoPanel = infoPanel;
        if (!infoPanel.hasRegisterDetail())
            infoPanel.setRegisterDetail(getRegisterInfo());

        operations = new ArrayList<>();
        pills  = new ArrayList();
        detDefaultOperations();
    }

    protected TableRegister getRegisterInfo(){

        ViewSpecs specs = infoPanel.getViewSpecs();
        DataBaseConsulter consulter = new DataBaseConsulter(specs.getTable());

        String[] cond = new String[]{specs.getCol(specs.getPrimarykey())};

        String[] values = new String[]{infoPanel.getKeyValue()};

        return consulter.bringTable(cond,values).getRegister(0);

    }

    protected void addPill(RegisterDetailTable pill){
        pills.add(pill);
    }

    protected void addOperation(Operation op){
        operations.add(op);
    }

    public ArrayList<RegisterDetailTable> getPills() {
        return pills;
    }

    public ArrayList<Operation> getOperations(){
        operations.addAll(getDefaultOperations());
        return operations;
    };

    private ArrayList<Operation> getDefaultOperations(){
        ArrayList<Operation> opList = new ArrayList<>();

        for (OperationBuilder builder:defaultOperations.values())
            opList.add(builder.getOperation());

        return opList;
    }

    private void detDefaultOperations(){
        defaultOperations = new HashMap<>();
        defaultOperations.put("eliminar", new OperationBuilder() {
            @Override
            public Operation getOperation() {
                return new DefaultDeleteRegisterOp(infoPanel);
            }
        });
        defaultOperations.put("modificar", new OperationBuilder() {
            @Override
            public Operation getOperation() {
                return new DefaultModifyRegisterOp(infoPanel);
            }
        });
    }

    protected void overRideDefOp(String operation,Operation op){
        defaultOperations.replace(operation, new OperationBuilder() {
            @Override
            public Operation getOperation() {
                return op;
            }
        });
    }

    private interface OperationBuilder{
        public Operation getOperation();

    }




}
