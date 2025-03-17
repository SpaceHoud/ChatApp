package com.chat.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JTextArea;

public class Client {
	Socket socket;
	OutputStream out;
	InputStream in;
	ClientHandler clientHandler;
	JTextArea textArea;
	
	public Client(JTextArea textArea) throws UnknownHostException, IOException {
		int port=1234;
		
		socket = new Socket("localhost",port);
		out = socket.getOutputStream();
		in = socket.getInputStream();
		this.textArea = textArea;
		readMessage();
		
	}//Fin Constructeur
	public void readMessage() {
		clientHandler = new ClientHandler(in,textArea);// calling a read thread
		clientHandler.start();
	}
	public void sendMessage(String message) throws IOException {
		
		//BufferedReader br = new BufferedReader(new InputStreamReader(in)); 
		PrintWriter pw = new PrintWriter(out, true);
		
		//out.write(message.getBytes());
		pw.println(message);
		
		
	}	
		//===========================================================
	class ClientHandler extends Thread{
		private InputStream in;
		private JTextArea textArea;
		public ClientHandler(InputStream in, JTextArea textArea) {
			super();
			this.in = in;
			this.textArea = textArea;
		}
		public void run() {
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line;
			try {
				while(true) {
					line = br.readLine();
					textArea.setText(textArea.getText()+line +"\n");					
				}
			}
			catch(Exception e) {
				e.printStackTrace();
			}
			finally {
				try {
//					if(br!=null)br.close();
					if(in!=null)in.close();
					}
					catch(Exception e){
						e.printStackTrace(); 
					}
			}
		}				
	}

}//fin class ClientCat
