package JDBCController;

import java.util.ArrayList;
import java.util.Arrays;

public class QueryParser {

    protected String getPrametrized(String[] params){

        int arraySize = params.length -1;
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < arraySize;i++){
                builder.append(mergeBranches(params[i]));
                builder.append(",");
            }

            builder.append(mergeBranches(params[arraySize]));

            return builder.toString();
    }

    protected String getEqualString(String cond,String val){
        StringBuilder res = new StringBuilder();
        res.append(cond);
        res.append(" = '");
        res.append(val);
        res.append("'");
        return res.toString();
    }

    protected String mergeBranches(String val){
        if(val == null)
            val = "NULL";
        if (!val.equals("NULL"))
            val  ="'"+val+ "'";

        return val;
    }

    protected String stringifyColumns(ArrayList<String> values){
        StringBuilder stringyfiedColumns = new StringBuilder();

        int i;
        int size = values.size()-1;
        for(i = 0;i < size; i++){
            stringyfiedColumns.append(values.get(i));
            stringyfiedColumns.append(",");
        }

        stringyfiedColumns.append(values.get(i));

        return stringyfiedColumns.toString();
    }

    private void removeNulls(ArrayList<String> columnCondition,ArrayList<String> values){
        int size = columnCondition.size();
        for (int i = 0;i < size;i++)
            if (values.get(i) == null){
                columnCondition.remove(i);
                values.remove(i);
                i--;
                size--;
            }

    }

    protected String stringifyConditions(ArrayList<String> columnCondition,ArrayList<String> values,String condition){
        removeNulls(columnCondition,values);
        StringBuilder stringifiedConditions = new StringBuilder();

        int i;
        int size = columnCondition.size()-1;
        for(i = 0; i < size;i++){
            stringifiedConditions.append(columnCondition.get(i));
            stringifiedConditions.append(" = ");
            stringifiedConditions.append(mergeBranches(values.get(i)));
            stringifiedConditions.append( " " + condition + " ");
        }

        stringifiedConditions.append(columnCondition.get(i));
        stringifiedConditions.append(" = ");
        stringifiedConditions.append(mergeBranches(values.get(i)));

        return stringifiedConditions.toString();
    }

    protected String buildConditionalQuery(String[] columnsToSearch, String[] keyWords) {

        int colsNum = columnsToSearch.length;

        if (colsNum > 0) {
            StringBuilder query = new StringBuilder("where BINARY ");
            int iteratons = colsNum - 1;
            for (int i = 0; i < iteratons; i++) {
                query.append(getEqualString(columnsToSearch[i], keyWords[i]));
                query.append(" and ");
            }

            query.append(getEqualString(columnsToSearch[iteratons], keyWords[iteratons]));

            return query.toString();
        }

        return "";
    }

}
