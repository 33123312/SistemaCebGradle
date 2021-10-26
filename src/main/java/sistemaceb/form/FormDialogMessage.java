package sistemaceb.form;

import sistemaceb.SubmitFrame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;


public class FormDialogMessage extends SubmitFrame {
    public FormDialogMessage(String title,String mesage) {
        super(title);
        setSize(new Dimension(600,300));
        addTextMessage(mesage);
    }

    private void addTextMessage(String mesage){
        JLabel label = new JLabel(getMessageWWithFormat(mesage));
            label.setForeground(new Color(100,100,100));
            label.setBorder(new EmptyBorder(20,30,20,30));
            label.setFont(new Font("arial", Font.PLAIN, 17));

        addBody(label);
    }

    private String getMessageWWithFormat(String message){
        return "<html><body>" + message + "</body></html>";
    }

}
