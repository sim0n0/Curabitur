

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Classe du serveur
 * @author SHANTHIRABALAN Aravinth
 */

public class CurabiturServer {

    // Indique le port d'écoute
    private int port;

    private ServerSocket server;

    // Utilisateurs du serveur
    private List<User> clients;


    /**
     * Constructeur de CurabiturServer : recupère le port et crée une liste d'User vide
     * @param port
     */
    public CurabiturServer(int port)
    {
        this.port = port;
        this.clients = new ArrayList<User>();
    }

    /**
     * Boucle de lancement du serveur
     * @throws IOException
     */
    public void run() throws IOException {
        server = new ServerSocket(port) {
            protected void finalize() throws IOException {
                this.close();
            }
        };
        System.out.println("CurabiturServer a ouvert et écoute le port "+port);

    }

    public static void main(String[] args) throws IOException
    {
        new CurabiturServer(12345).run();
    }


}
