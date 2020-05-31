import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.*;
import java.io.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.html.*;

import java.util.ArrayList;
import java.util.Arrays;


public class InterfaceClient extends Thread{

	final JTextField zoneCommunication = new JTextField();
	final JTextPane blocDiscussion = new JTextPane();
	final JTextPane listeUtilisateurs = new JTextPane();



	private String oldMsg = "";
	private Thread read;
	private String serverName;
	private int PORT;
	private String name;
	BufferedReader input;
	PrintWriter output;
	Socket server;

	public InterfaceClient() {
		this.serverName = "localhost";
		this.PORT = 12345;
		this.name = "Nom";

		String policecCaractere = "Times";
		Font ecriture = new Font(policecCaractere, Font.BOLD, 18);

		//MISE EN PLACE DE L'APPLICATION
		final JFrame nomApplication = new JFrame("CURABITUR");
		nomApplication.getContentPane().setLayout(null);
		nomApplication.setSize(1000, 800);
		nomApplication.setResizable(false);
		nomApplication.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// BLOC DE DISCUSSION 
		blocDiscussion.setBackground(Color.LIGHT_GRAY);
		blocDiscussion.setBounds(30, 30, 800, 350);
		blocDiscussion.setFont(ecriture);
		blocDiscussion.setMargin(new Insets(6, 6, 6, 6));
		blocDiscussion.setEditable(false);
		JScrollPane panneauDiscussion = new JScrollPane(blocDiscussion);
		panneauDiscussion.setBounds(10, 10, 490, 320);

		blocDiscussion.setContentType("text/html");
		blocDiscussion.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, true);

		// LISTE DES UTILSATEURS
		listeUtilisateurs.setBackground(Color.LIGHT_GRAY);
		listeUtilisateurs.setBounds(520, 25, 156, 320);
		listeUtilisateurs.setEditable(true);
		listeUtilisateurs.setFont(ecriture);
		listeUtilisateurs.setMargin(new Insets(6, 6, 6, 6));
		listeUtilisateurs.setEditable(false);
		JScrollPane blocListUtilisateur = new JScrollPane(listeUtilisateurs);
		blocListUtilisateur.setBounds(500, 10, 180, 320);

		listeUtilisateurs.setContentType("text/html");
		listeUtilisateurs.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, true);

		// CREATION DU CHAMPS PERMETTANT D'ECRIRE LES MESSAGE
		zoneCommunication.setBounds(0, 350, 400, 50);
		zoneCommunication.setFont(ecriture);
		zoneCommunication.setMargin(new Insets(6, 6, 6, 6));
		final JScrollPane zoneChat = new JScrollPane(zoneCommunication);
		zoneChat.setBounds(100, 350, 650, 50);

		/******************************************************************************************************************/
		/******************************************************************************************************************/
		/******************************************************************************************************************/

		/* CREATION DES BOUTONS */

		/*  BOUTONS ENVOI */
		final JButton boutonEnvoi = new JButton("Envoyer");
		boutonEnvoi.setFont(ecriture);
		boutonEnvoi.setBounds(550, 410, 100, 35);

		/* BOUTON QUITTER */
		final JButton jsbtndeco = new JButton("Quitter");
		jsbtndeco.setFont(ecriture);
		jsbtndeco.setBounds(25, 400, 130, 35);


		// ENVOI DE MESSAGE 
		zoneCommunication.addKeyListener(new KeyAdapter() {
			// send message on Enter
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					sendMessage();
				}

				/* RECUPERATION DU DERNIER MESSAGE */ 
				if (e.getKeyCode() == KeyEvent.VK_UP) {
					String currentMessage = zoneCommunication.getText().trim();
					zoneCommunication.setText(oldMsg);
					oldMsg = currentMessage;
				}

				if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					String currentMessage = zoneCommunication.getText().trim();
					zoneCommunication.setText(oldMsg);
					oldMsg = currentMessage;
				}
			}
		});

		/* AJOUT ACTION PERMETTANT L'ENVOI GRACE AU BOUTON */
		boutonEnvoi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				sendMessage();
			}
		});

		/************************************************************************************************/
		/************************************************************************************************/
		/************************************************************************************************/

		/* FENETRE DE CONNECTION AVEC SES ELEMENTS*/
		final JTextField nomUtilisateur = new JTextField(this.name);
		nomUtilisateur.setBounds(375, 380, 135, 40);

		final JTextField numeroPort = new JTextField(Integer.toString(this.PORT));
		numeroPort.setBounds(200, 380, 135, 40);


		final JTextField nomServer = new JTextField(this.serverName);
		nomServer.setBounds(25, 380, 135, 40);

		final JButton boutonConnexion = new JButton("CONNEXION");
		boutonConnexion.setBounds(575, 380, 100, 40);
		boutonConnexion.setFont(ecriture);


		/* ON VERIFIE SI LES CHAMPS NE SONT PAS VIDES */
		nomUtilisateur.getDocument().addDocumentListener(new TextListener(nomUtilisateur, numeroPort, nomServer, boutonConnexion));
		numeroPort.getDocument().addDocumentListener(new TextListener(nomUtilisateur, numeroPort, nomServer, boutonConnexion));
		nomServer.getDocument().addDocumentListener(new TextListener(nomUtilisateur, numeroPort, nomServer, boutonConnexion));


		/* AJOUT DES DIFFERENTS BLOC AU SEIN DE L'APPLICATION */
		nomApplication.add(boutonConnexion);
		nomApplication.add(panneauDiscussion);
		nomApplication.add(blocListUtilisateur);
		nomApplication.add(nomUtilisateur);
		nomApplication.add(numeroPort);
		nomApplication.add(nomServer);
		nomApplication.setVisible(true);


		/* INFORMATIONS VISIBLES */
	
		affichageInformationuTilisateur(blocDiscussion, "<img\n" + 
				"	    src=\"clinoeil.jpg\" \n" + 
				"	    height=\"300px\" \n" + 
				"	    width=\"300px\" \n" + 
				"	/>"
				+ "<h4 style=\"color:#178fa5\">Quelques infos utiles :</h4>"
				+"<ul>"
				+"<p style=\"color:#178fa5\"; align=\"center\"><b>@nomUtilisateur</b> : cela permet d'envoyer un messager à votre correcpondant.</p></marquee>"
				+"<p style=\"color:#8b17a5\"; align=\"center\"><b>:)</b> Des émojis sont pré-existants</p>"
				+"<p style=\"color:#33a517\"; align=\"center\"><b>la flèche du haut </b> permet de repondre au dernier message.</p>"
				+"</ul><br/>"
				+ "");

		/* ACTION BOUTON DE CONNEXION */
		boutonConnexion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					name = nomUtilisateur.getText();
					String port = numeroPort.getText();
					serverName = nomServer.getText();
					PORT = Integer.parseInt(port);

					affichageInformationuTilisateur(blocDiscussion, "<span>Connexion au serveur : " + serverName + " sur le port " + PORT + ".</span>");
					server = new Socket(serverName, PORT);

					affichageInformationuTilisateur(blocDiscussion, "<span>Vous êtes connectés à " +
							server.getRemoteSocketAddress()+"</span>");

					input = new BufferedReader(new InputStreamReader(server.getInputStream()));
					output = new PrintWriter(server.getOutputStream(), true);

					/* ENVOI DU NOM VERS LE SERVEUR */
					output.println(name);

					/* CREATION D'UN NOUVEAU THREAD */
					read = new Read();
					read.start();
					nomApplication.remove(nomServer);
					nomApplication.remove(numeroPort);
					nomApplication.remove(nomUtilisateur);
					nomApplication.remove(boutonConnexion);
					nomApplication.add(boutonEnvoi);
					nomApplication.add(zoneChat);
					nomApplication.add(jsbtndeco);
					nomApplication.revalidate();
					nomApplication.repaint();
					blocDiscussion.setBackground(Color.BLACK);
					listeUtilisateurs.setBackground(Color.GRAY);
				} catch (Exception ex) {
					affichageInformationuTilisateur(blocDiscussion, "<span>Impossible de se connecter au server</span>");
					JOptionPane.showMessageDialog(nomApplication, ex.getMessage());
				}
			}

		});


		jsbtndeco.addActionListener(new ActionListener()  {
			public void actionPerformed(ActionEvent ae) {
				nomApplication.add(nomUtilisateur);
				nomApplication.add(numeroPort);
				nomApplication.add(nomServer);
				nomApplication.add(boutonConnexion);
				nomApplication.remove(boutonEnvoi);
				nomApplication.remove(zoneChat);
				nomApplication.remove(jsbtndeco);
				nomApplication.revalidate();
				nomApplication.repaint();
				read.interrupt();

				listeUtilisateurs.setText(null);
				blocDiscussion.setBackground(Color.LIGHT_GRAY);
				listeUtilisateurs.setBackground(Color.LIGHT_GRAY);

				affichageInformationuTilisateur(blocDiscussion, "<span>Vous êtes déconnectés</span>");
				output.close();
			}
		});

	}


	// send html to pane
	private void affichageInformationuTilisateur(JTextPane tp, String msg){
		HTMLDocument doc = (HTMLDocument)tp.getDocument();
		HTMLEditorKit editorKit = (HTMLEditorKit)tp.getEditorKit();
		try {
			editorKit.insertHTML(doc, doc.getLength(), msg, 0, 0, null);
			tp.setCaretPosition(doc.getLength());
		} catch(Exception e){
			e.printStackTrace();
		}
	}


	/* ENVOI DU MESSAGE */
	public void sendMessage() {
		try {
			String message = zoneCommunication.getText().trim();
			if (message.equals("")) {
				return;
			}
			this.oldMsg = message;
			output.println(message);
			zoneCommunication.requestFocus();
			zoneCommunication.setText(null);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
			System.exit(0);
		}
	}
	
	/* LETURE DES MESSAGE ARRIVANTS */
	  class Read extends Thread {
		  
			
	    public void run() {
	      String message;
	      while(!Thread.currentThread().isInterrupted()){
	        try {
	          message = input.readLine();
	          if(message != null){
	            if (message.charAt(0) == '[') {
	              message = message.substring(1, message.length()-1);
	              ArrayList<String> ListUser = new ArrayList<String>(
	                  Arrays.asList(message.split(", "))
	                  );
	              listeUtilisateurs.setText(null);
	              for (String user : ListUser) {
	            	  affichageInformationuTilisateur(listeUtilisateurs, "@" + user);
	              }
	            }else{
	            	affichageInformationuTilisateur(blocDiscussion, message);
	            }
	          }
	        }
	        catch (IOException ex) {
	          System.err.println("Impossible danalyser le message entrant");
	        }
	      }
	    }
	  }

	public static void main(String[] args) throws Exception {
		InterfaceClient client = new InterfaceClient();
	}


}
