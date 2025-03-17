package com.chat.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import com.chat.dao.DbHandler;
import com.chat.dao.UserInfo;
import com.chat.dao.UserTransfer;

public class AuthWindow {
	
	private JFrame userAuthWindow;
	private JTextField useridTxt;
	private JPasswordField passwordField;
	private JButton btEnregistrer;

	public static void main(String[] args) throws UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel( new NimbusLookAndFeel() );
		
		AuthWindow window = new AuthWindow();
		window.userAuthWindow.setVisible(true);

	}
	
	public AuthWindow() {initialize();}
	
	private void initialize() {
		userAuthWindow = new JFrame();
		//mon ajout
		userAuthWindow.setSize(500, 400);
		userAuthWindow.setResizable(false);
		userAuthWindow.setTitle("Formulaire d'authentification / inscription");
		// ajout
		userAuthWindow.setLocationRelativeTo(null);
		userAuthWindow.getContentPane().setLayout(null);
		
		JLabel lblTitre = new JLabel("Enregistrement / Connexion");
		lblTitre.setHorizontalAlignment(10);		
		lblTitre.setFont(new Font("Arial", Font.BOLD, 20));
		lblTitre.setBounds(130, 23, 280, 35);
		userAuthWindow.getContentPane().add(lblTitre);
		
		JLabel lblLogin = new JLabel("Login : ");
		lblLogin.setFont(new Font("Arial", Font.PLAIN, 16));
		lblLogin.setBounds(24, 100, 125, 26);
		userAuthWindow.getContentPane().add(lblLogin);
	
		JLabel lblPassword = new JLabel("Password :");
		lblPassword.setFont(new Font("Arial", Font.PLAIN, 16));
		lblPassword.setBounds(24, 150, 125, 26);
		userAuthWindow.getContentPane().add(lblPassword);
		
		useridTxt = new JTextField();
		useridTxt.setFont(new Font("Arial", Font.PLAIN, 16));
		useridTxt.setBounds(160, 100, 180, 30);
		userAuthWindow.getContentPane().add(useridTxt);

		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
		passwordField.setBounds(160, 150, 180, 30);
		userAuthWindow.getContentPane().add(passwordField);
		
		JButton btLogin = new JButton("Se connecter");
		btLogin.setBounds(160, 200, 180, 30);
		userAuthWindow.getContentPane().add(btLogin);
		btLogin.setForeground(Color.WHITE);
		btLogin.setBackground(Color.BLUE);
		btLogin.setFont(new Font("Arial", Font.BOLD, 15));
		btLogin.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {doLogin();}});
		
		JButton btEnregistrer = new JButton(" Créer un nouveau compte ");
		btEnregistrer.setBounds(160, 280, 180, 26);
		userAuthWindow.getContentPane().add(btEnregistrer);
		btEnregistrer.setForeground(Color.WHITE);
		btEnregistrer.setBackground(Color.green);
		btEnregistrer.setFont(new Font("Arial", Font.BOLD, 11));
		btEnregistrer.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) 
		{try {
			register();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} }});
	} // fin initialize
	
	DbHandler dbHandler = new DbHandler();
	private void doLogin() {
		String userid = useridTxt.getText();
		String password = passwordField.getText();
		//String password = passwordField.getSelectedText();
		
		UserTransfer userTransfer = new UserTransfer(userid, password);
		
		try {
			String msg = "";
			if(dbHandler.isUserAuthenticated(userTransfer)) {
				msg = "Welcome " +userid;
				UserInfo.USER_NAME = userid;
				System.out.println("USER_NAME "+UserInfo.USER_NAME);
				JOptionPane.showMessageDialog(null, msg,"Message",JOptionPane.INFORMATION_MESSAGE);
				userAuthWindow.setVisible(false);
				userAuthWindow.dispose();
				StartChatWindow startChatWindow = new StartChatWindow(msg);
				startChatWindow.setVisible(true);
			}
			else {
				msg = "Invalid userid or password";
				JOptionPane.showMessageDialog(null, msg,"Message",JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (SQLException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}		
	} //Fin doLogin
	
	private void register() throws ClassNotFoundException, SQLException, Exception {
		String userid = useridTxt.getText();
		String password = passwordField.getText();
		//String password = passwordField.getSelectedText();
		DbHandler dbHandler = new DbHandler();
		UserTransfer userTransfer = new UserTransfer(userid,password );
		String msg = "" + userid;
        msg += " \n";
        if(userid.length()==0 || password.length()==0 ) {
        	
        	useridTxt.setText("");
			passwordField.setText("");
			JOptionPane.showMessageDialog(btEnregistrer, "Champs Login et Password ne doient as être vides !!!");				
        }else {
        	try {
        		if(!dbHandler.fetchUser(userTransfer)) {
        			int res= dbHandler.storeUser(userTransfer); 
        			if(res>0) {
        				useridTxt.setText("");
        				passwordField.setText("");
        				msg = msg+" : Enregistrement Réussie";
        				JOptionPane.showMessageDialog(null, msg,"Message",JOptionPane.INFORMATION_MESSAGE);
        			}else {
        				useridTxt.setText("");
        				passwordField.setText("");
        				JOptionPane.showMessageDialog(null, "L'enregistrement a échoué ","Message",JOptionPane.WARNING_MESSAGE);
        			}
        		}
        	}catch(ClassNotFoundException | SQLException ex) {
    			System.out.println("Problème de base de données ...");
    			ex.printStackTrace();
    		}catch(Exception e) {
    			System.out.println("Une exception générique a été levée ...");
    			e.printStackTrace();
    		}
        }
		
	}//Fin register
}
