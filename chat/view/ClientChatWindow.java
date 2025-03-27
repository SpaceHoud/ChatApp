package fr.chat.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import fr.chat.dao.DbHandler;
import fr.chat.model.InfoUser;
import fr.chat.model.MessageTransfer;
import fr.chat.net.Client;

public class ClientChatWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextArea textArea ;
	private Client client;
	private JLabel jlMessage;
	
	public static void main(String[] args) {	
		try {
				UIManager.setLookAndFeel( new NimbusLookAndFeel() );
				ClientChatWindow frame = new ClientChatWindow();
			} catch (UnsupportedLookAndFeelException | IOException e) {
				e.printStackTrace();
			}
	}
	
	//Envoie et enregistrement des messages
	
	private void sendIt() throws Exception {
		String message = textField.getText();
		MessageTransfer messageTransfer = new MessageTransfer(InfoUser.USER_NAME, message);
		
		if(message.contains("=>")) {
			String[] requestParams = message.split("=>");
			if(requestParams.length == 2) {
				String myMessage = requestParams[1];
				
				int numeroClient = Integer.parseInt(requestParams[0]);
				try {
					client.sendMessage(numeroClient+"=>"+InfoUser.USER_NAME+" - "+myMessage);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} //fin if
		}else {
			try {
				client.sendMessage(InfoUser.USER_NAME+" - "+message);
				DbHandler.storeMessage(messageTransfer);
			} catch (IOException e) {
				e.printStackTrace();
			}																							
		}//fin else			
	}
    
	//Constructeur
	
	public ClientChatWindow() throws UnknownHostException, IOException {
		
		textArea = new JTextArea();
		client = new Client(textArea);
		setTitle("On chat");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(70, 70, 703, 421);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(3, 3, 3, 3));

		setContentPane(contentPane);
		contentPane.setLayout(null);
			
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(15, 6,662, 334);
		contentPane.add(scrollPane);
		
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
		textArea.setBounds(34, 25, 714, 294);
		textArea.setForeground(Color.blue);
		scrollPane.setViewportView(textArea);
		
		textField = new JTextField();
		textField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		textField.setBounds(100, 345, 450, 30);
		contentPane.add(textField);
	
		jlMessage = new JLabel("Message");
		jlMessage.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		jlMessage.setBounds(25, 345, 100, 30);
		contentPane.add(jlMessage);
		
		JButton sendit = new JButton("Envoyer");
		sendit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					sendIt();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		sendit.setFont(new Font("Times New Roman", Font.BOLD, 14));
		sendit.setBounds(575, 345, 100, 30);
		sendit.setForeground(Color.WHITE);
		sendit.setBackground(Color.GRAY);
		contentPane.add(sendit);
		setVisible(true);
	}
}