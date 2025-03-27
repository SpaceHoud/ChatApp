package fr.chat.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

//Ce serveur est chargé de gérer les messages entrant et sortant.
//Ce serveur sera à l'écoute des connexions entrante des clients et distribura les messages

public class Server extends Thread{
	private Boolean isActiveServer = true;
	private int nombreClients = 0;
	private List<Conversation> Clients = new ArrayList<Conversation>();	
	
	ServerSocket serverSocket;
	
	public static void main(String[] args) {
		try {
			new Server().start();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Server() throws IOException {
		int port=1234;

		try {
			// 
			ServerSocket serverSocket = new ServerSocket(port);
			while(isActiveServer) {		
				Socket socket = serverSocket.accept();
				System.out.println("Server started waiting for client connection");
				++nombreClients;
				Conversation conversation = new Conversation(socket, nombreClients, this);
				Clients.add(conversation);
				conversation.start();																
			}//fin while									
		} catch (IOException e) {
			e.printStackTrace();
		}//fin catch	
	}

	class Conversation extends Thread {

		private Socket socketClient;
		private int numero;
		private InputStream in;
		private OutputStream out;
		private  Server server;
		
		public Conversation(Socket socketClient, int numero, Server server) {
			this.socketClient = socketClient;
			this.numero = numero;	
			this.server = server;
			try {
				in = socketClient.getInputStream();// Data in
				out = socketClient.getOutputStream();// Data out
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}//fin constructeur
		
	public void run() {

		BufferedReader br = new BufferedReader(new InputStreamReader(in)); 
		PrintWriter pw = new PrintWriter(out, true);				
		try {
			String ipClient = socketClient.getRemoteSocketAddress().toString();
			pw.println("Bienvenue, vous êtes le client numero "+numero);
			System.out.println("Connexion du client numéro "+numero+", IP="+ipClient);
			while(true){
				String line = br.readLine();
				if (line != null) {
					if(line.equalsIgnoreCase("quit")) {
						break;
					}else if(line.contains("=>")) {
						System.out.println(line);
						String[] requestParams = line.split("=>");
						if(requestParams.length == 2) {
							String message = requestParams[1];
							
							int numeroClient = Integer.parseInt(requestParams[0]);
							envoyerMessage(message,socketClient, numeroClient);
						} //fin if
					}else {
						envoyerMessage(line,socketClient, -1);																							
					}//fin else											
				  }// fin if
			    } // fin while	
		    }catch (IOException e) {
			    e.printStackTrace();
		  }				
		 }// fin run
	}//fin Conversation

	public void envoyerMessage(String message, Socket socket, int numClient) {
		try {
			for(Conversation client:Clients) {
				if(client.socketClient != socket) {
					if(numClient == client.numero  || numClient == -1) {
						PrintWriter printWriter = new PrintWriter(client.socketClient.getOutputStream(), true);
						printWriter.println(message);
					}//fin if					
				}//Fin if
			}//Fin for
		} catch (IOException e) {
			e.printStackTrace();
		}//fin catch		
	}// Fin diffuserMessage	
}//fin class Server	
