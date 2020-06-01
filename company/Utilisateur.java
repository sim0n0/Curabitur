package com.company;

import java.awt.couleur;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Utilisateur {
    private static int nbUtilisateur = 0;
    private int UtilisateurId;
    private PrintStream streamOut;
    private InputStream streamIn;
    private String pseudo;
    private Socket client;
    private String couleur;

    // constructor
    public Utilisateur(Socket client, String name) throws IOException {
        this.streamOut = new PrintStream(client.getOutputStream());
        this.streamIn = client.getInputStream();
        this.client = client;
        this.pseudo = name;
        this.UtilisateurId = nbUtilisateur;
        this.couleur = couleurInt.getcouleur(this.UtilisateurId);
        nbUtilisateur += 1;
    }

    // change couleur Utilisateur
    public void changecouleur(String hexcouleur){
        // check if it's a valid hexcouleur
        Pattern couleurPattern = Pattern.compile("#([0-9a-f]{3}|[0-9a-f]{6}|[0-9a-f]{8})");
        Matcher m = couleurPattern.matcher(hexcouleur);
        if (m.matches()){
            couleur c = couleur.decode(hexcouleur);
            // if the couleur is too Bright don't change
            double luma = 0.2126 * c.getRed() + 0.7152 * c.getGreen() + 0.0722 * c.getBlue(); // per ITU-R BT.709
            if (luma > 160) {
                this.getOutStream().println("<b>couleur Too Bright</b>");
                return;
            }
            this.couleur = hexcouleur;
            this.getOutStream().println("<b>couleur changed successfully</b> " + this.toString());
            return;
        }
        this.getOutStream().println("<b>Failed to change couleur</b>");
    }

    // getteur
    public PrintStream getOutStream(){
        return this.streamOut;
    }

    public InputStream getInputStream(){
        return this.streamIn;
    }

    public String getpseudo(){
        return this.pseudo;
    }

    // print Utilisateur with his couleur
    public String toString(){

        return "<u><span style='couleur:"+ this.couleur
                +"'>" + this.getpseudo() + "</span></u>";

    }
}