package com.company;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Classe du serveur
 * @author SHANTHIRABALAN Aravinth
 */

public class CurabiturServer {

    // Indique le port d'écoute
    private static int port=4444;

    private ServerSocket server;

    // Utilisateurs du serveur
    private List<Utilisateur> clients;


    /**
     * Constructeur de CurabiturServer : recupère le port et crée une liste d'User vide
     * @param port
     */
    public CurabiturServer(int port)
    {
        this.port = port;
        this.clients = new ArrayList<Utilisateur>();
    }

    /**
     * Affiche la liste des clients inscrits dans le serveur, si la liste est vide affiche un message
     */
    public void removeUser(Utilisateur user)
    {
        this.clients.remove(user);
    }

    /**
     * Ajoute un user donné en paramètre de la liste des clients
     * @param user
     */
    public void addUser(Utilisateur user)
    {
        this.clients.add(user);
    }

    /**
     * Affiche la liste des clients inscrits dans le serveur, si la liste est vide affiche un message
     */
    public void showUser()
    {

        if(clients.size()==0)
        {
            System.out.println("Le serveur est vide");
        }
        else
        {
            String results=" ";
            for(int i =0; i < clients.size(); i++)
            {
                results+=clients.get(i).getpseudo();
                results+="\n";
            }
            System.out.println("Voici la liste des personnes connectés au serveur : \n" );
        }
    }

    /**
     * Boucle de lancement du serveur
     * @throws IOException
     */
    public void run() throws IOException {
        try {
            server = new ServerSocket(port);
        } catch (IOException e) {
            System.out.println("Erreur à la création :\n"+ e);
            server.close();
        }
        System.out.println("CurabiturServer a ouvert et écoute le port "+port);


        while(true)
        {
            // Ajout client
            Socket client = server.accept();
            showUser();

            // 
            String nickname = (new Scanner ( client.getInputStream() )).nextLine();

//Création et ajout du user depuis client
            Utilisateur newUser = new Utilisateur(client, nickname);
            this.clients.add(newUser);

            new Thread(new GestionUtilisateur(this, newUser)).start();
        }
    }


    public void broadcastMessages(String msg, Utilisateur userSender) {
        for (Utilisateur client : this.clients) {
            client.getOutStream().println(
                    userSender.toString() + "<span>: " + msg+"</span>");
        }
    }

    // send list of clients to all Users
    public void broadcastAllUsers(){
        for (Utilisateur client : this.clients) {
            client.getOutStream().println(this.clients);
        }
    }

    // send message to a User (String)
    public void sendMessageToUser(String msg, Utilisateur userSender, String user){
        boolean find = false;
        for (Utilisateur client : this.clients) {
            if (client.getpseudo().equals(user) && client != userSender) {
                find = true;
                userSender.getOutStream().println(userSender.toString() + " -> " + client.toString() +": " + msg);
                client.getOutStream().println(
                        "(<b>Private</b>)" + userSender.toString() + "<span>: " + msg+"</span>");
            }
        }
        if (!find) {
            userSender.getOutStream().println(userSender.toString() + " -> (<b>no one!</b>): " + msg);
        }
    }


    public static void main(String[] args) throws IOException
    {
        new CurabiturServer(port).run();
    }
}
