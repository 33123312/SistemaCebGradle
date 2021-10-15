package sistemaceb;

public abstract class keyedBuildBehavior extends KeyedTableBehavior{

    protected ConsultTableBuild build;

    public  keyedBuildBehavior(ConsultTableBuild build) {
        super(build.viewSpecs.getTable(),build.getOutputTable());
        this.build = build;
    }
}
