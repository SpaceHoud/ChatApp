package fr.chat.view;

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

import fr.chat.dao.DbHandler;
import fr.chat.model.InfoUser;
import fr.chat.model.UserTransfer;

public class AuthWindow {
	
	//Déclaration des paramètres
	
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
	
	//Méthode pour l'initialisation des composants
	
	private void initialize() {
		userAuthWindow = new JFrame();
		userAuthWindow.setSize(500, 400);
		userAuthWindow.setResizable(false);
		userAuthWindow.setTitle("Formulaire d'authentification / inscription");
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
			e1.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		} }});
	} // fin initialize
	
	//Méthode permettant l'authentification de l'utilisateur
	
	private void doLogin() {
		DbHandler dbHandler = new DbHandler();
		String userid = useridTxt.getText();
		String password = passwordField.getText();		
		UserTransfer userTransfer = new UserTransfer(userid, password);
		
		try {
			String msg = "";
			if(DbHandler.isUserAuthenticated(userTransfer)) {
				msg = "Welcome " +userid;
				InfoUser.USER_NAME = userid;
				System.out.println("USER_NAME "+InfoUser.USER_NAME);
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
		}catch (Exception e) {
			e.printStackTrace();
		}
	} //Fin doLogin
	
	//Méthode permettant d'enregistrer un nouvel utilisateur
	
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
        		if(!DbHandler.isUserAuthenticated(userTransfer)) {
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
        	
    		}catch(Exception e) {
    			System.out.println("Une exception générique a été levée ...");
    			e.printStackTrace();
    		}
        }		
	}//Fin register
}
