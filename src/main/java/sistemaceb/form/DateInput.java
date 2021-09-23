/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaceb.form;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

/**
 *
 * @author escal
 */
public class DateInput extends FormElement{
    
    JTextFieldLimit dia;
    JTextFieldLimit mes;
    JTextFieldLimit año;
    
    public DateInput(String title){
        super(title);
        addWrongTypeError();
        add(buildInputsSec(),BorderLayout.CENTER);
    }

    @Override
    public void setResponse(String txt) {

        ArrayList<String> trimedDate = trimDate(txt);
        dia.setText(trimedDate.get(2));
        mes.setText(trimedDate.get(1));
        año.setText(trimedDate.get(0));
    }

    private ArrayList<String> trimDate(String txt){
        ArrayList<String> values = new ArrayList<>();
        String currentWord = "";
        for(int i = 0;i < txt.length();i++){
            char currentChar = txt.charAt(i);
            if(currentChar == '-'){
                values.add(currentWord);
                currentWord = "";
            } else
                currentWord+=currentChar;

        }
        values.add(currentWord);

        String year = values.get(0);
        String newYear = "";
        for(int i = 2;i < year.length();i++){
            newYear+= year.charAt(i);
        }
        values.set(0,newYear);
        return values;
    }

    private JPanel buildInputsSec(){
        JPanel parentCont = new JPanel(new GridLayout(1,3));
        
        dia = new JTextFieldLimit(2);
        mes = new JTextFieldLimit(2);
        año = new JTextFieldLimit(2);
        
        dia.setText("DD");
        mes.setText("MM");
        año.setText("AA");

        parentCont.add(dia);
        parentCont.add(mes);
        parentCont.add(año);
        
        addCloseOnClick(dia);
        addCloseOnClick(mes);
        addCloseOnClick(año);
        

        
        return parentCont;
    }
    
    @Override
    protected String getResponseConfig(){
        if (año.getText().equals("AA") &&
                mes.getText().equals("MM") &&
                dia.getText().equals("DD")
        )
            return "";

        return "20" + addZero(año.getText()) + "-" + addZero(mes.getText()) + "-" + addZero(dia.getText());

    }
    
    private void addWrongTypeError(){
        this.addErrorChecker(new ErrorChecker(){
            
            @Override
            public String checkForError(String response) {

                
                try{
                    Integer.parseInt(año.getText());
                    Integer.parseInt(mes.getText());
                    Integer.parseInt(dia.getText());
            
                }catch(NumberFormatException e){
                    return "Error en el Formato de la Fecha";
                }

                if(Integer.parseInt(mes.getText()) > 12 ||	
                   Integer.parseInt(dia.getText()) >30)
                    return "Error en el Formato de la Fecha";
                
                return "";
                
            }
        });
    }
    
    protected String addZero(String value){
        if(value.length() == 1)
            return "0" + value;
        else
            return value;
    }



    private void addCloseOnClick(JTextField field){
        
        MouseAdapter closeOnClick = new MouseAdapter(){
            
            @Override
            public void mouseClicked(MouseEvent e){
                field.setText("");
            }
        };
        
        field.addMouseListener(closeOnClick);
    }
    
        private class JTextFieldLimit extends JTextField {
        private final int limit;

        public JTextFieldLimit(int limit) {
            super();
            this.limit = limit;
        }

        @Override
        protected Document createDefaultModel() {
            return new LimitDocument();
        }

        private class LimitDocument extends PlainDocument {

            @Override
            public void insertString( int offset, String  str, AttributeSet attr ) throws BadLocationException {
                if (str == null) return;

                if ((getLength() + str.length()) <= limit) {
                    super.insertString(offset, str, attr);
                }
            }       

        }

    }
    
}
