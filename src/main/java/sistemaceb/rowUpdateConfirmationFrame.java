/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistemaceb;

import Tables.AdapTableFE;
import Tables.RowsFactory;
import Tables.StyleRowModel;
import sistemaceb.form.FormDialogMessage;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 *
 * @author escal
 */
public class rowUpdateConfirmationFrame extends FormDialogMessage {

    public rowUpdateConfirmationFrame (){
        super("Eliminar Registro","¿Está completamente seguro querer de eliminar el registro?");
        addAcceptButton();
    }

    
}
