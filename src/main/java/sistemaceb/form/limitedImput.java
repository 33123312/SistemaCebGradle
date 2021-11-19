package sistemaceb.form;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import java.util.ArrayList;

public class limitedImput extends JTextField {
    private ArrayList<StringChecker> checkers;

    private int limit;

    public limitedImput() {
        super();
        checkers = new ArrayList<>();
    }

    public limitedImput setLimit(int limit){
        this.limit = limit;
        addChecker(new StringChecker(){
            @Override
            public boolean checkString(PlainDocument doc,String str) {
                if ((doc.getLength() + str.length()) <= limit)
                    return true;
                else
                    return false;
            }
        });

        return this;
    }

    public void OnlyAllowNumbers(){
        addChecker(new StringChecker(){
            @Override
            public boolean checkString(PlainDocument doc,String str){
                try {
                    Integer.parseInt(str);
                    return true;
                } catch (Exception e){
                    return false;
                }

            }
        });
    }

    private boolean fillsCeros;

    public void setFillsCeros(boolean fillsCeros){
        this.fillsCeros = fillsCeros;
    }


    @Override
    public String getText() {
        String txt = super.getText();
        return checkIfFillsNumbers(txt);
    }

    public String checkIfFillsNumbers(String txt){
        if (fillsCeros && !txt.isEmpty()){
            int textLenght = txt.length();
            if (limit > textLenght){
                StringBuilder builder = new StringBuilder();
                int diference = limit-textLenght;
                for (int i = 0;i < diference;i++)
                    builder.append("0");
                txt = builder.append(txt).toString();
            }
        }

        return txt;
    }

    @Override
    protected Document createDefaultModel() {
        return new LimitDocument();
    }

    private class LimitDocument extends PlainDocument {
        @Override
        public void insertString( int offset, String  str, AttributeSet attr ) throws BadLocationException {
            if (str == null) return;
            if (check(this,str))
                super.insertString(offset, str, attr);

        }
    }

    private boolean check(PlainDocument doc,String str){
        for(StringChecker checker: checkers)
            if (!checker.checkString(doc,str))
                return false;

        return true;
    }

    public void addChecker(StringChecker c){
        checkers.add(c);
    }

    public interface StringChecker {
        boolean checkString(PlainDocument doc,String str);
    }

}
