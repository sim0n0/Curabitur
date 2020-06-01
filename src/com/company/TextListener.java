package com.company;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


/* ON VERIFIE SI LES CHAMPS NE SONT PAS VIDE */
  public class TextListener implements DocumentListener{
    JTextField chdt1;
    JTextField chdt2;
    JTextField chdt3;
    JButton btn;
	
	//méthode des lectures des champs de texte et bouton.
    public TextListener(JTextField chdt1, JTextField chdt2, JTextField chdt3, JButton btn){
      this.chdt1 = chdt1;
      this.chdt2 = chdt2;
      this.chdt3 = chdt3;
      this.btn = btn;
    }
	
	//méthode de modification des champs de texte et suppression des espacements.
	public void removeUpdate(DocumentEvent e) {
      if(chdt1.getText().trim().equals("") ||
          chdt2.getText().trim().equals("") ||
          chdt3.getText().trim().equals("")
          ){
        btn.setEnabled(false);
      }else{
        btn.setEnabled(true);
      }
    }
	
	//méthode d'insertion des modification des champs de texte.
	public void insertUpdate(DocumentEvent e) {
      if(chdt1.getText().trim().equals("") ||
          chdt2.getText().trim().equals("") ||
          chdt3.getText().trim().equals("")
          ){
        btn.setEnabled(false);
      }else{
        btn.setEnabled(true);
      }
    }

    public void changedUpdate(DocumentEvent e) {}


  }

  