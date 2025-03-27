package fr.chat.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class StartChatWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	
	public StartChatWindow(String message) {
	
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1110, 585);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		//Menu chat
		JMenu chatmenu = new JMenu("Chat");
		menuBar.add(chatmenu);
		
		//Sous menu
		
		JMenuItem StartChat = new JMenuItem("Démarrer le chat");
		JMenuItem StartGroup = new JMenuItem("Créer un groupe");
		
		StartChat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					new ClientChatWindow();
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		//Instanciation d'un JMenu Item
		JMenuItem CreateGroup = new JMenuItem("Créer un group");
		CreateGroup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					new CreateGroup();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
		
		chatmenu.add(StartChat);
		chatmenu.add(CreateGroup);
		
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
				
		setContentPane(contentPane);
		setTitle(message);
		
		//Instanciation d'un JLabel
		
		JLabel lblLabel = new JLabel("");
		lblLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(lblLabel);		
	}
}
