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
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import fr.chat.dao.DbHandler;
import fr.chat.model.GroupTransfer;
import fr.chat.model.InfoUser;
import fr.chat.net.Client;

public class CreateGroup extends JFrame {
	
	private JPanel contentPane;
	private JTextField grpTextField;
	private Client client;
	private JLabel lblGroup;
	JButton btnCreate;
	
	public static void main(String[] args) {	
		try {
				UIManager.setLookAndFeel( new NimbusLookAndFeel() );
				CreateGroup frame = new CreateGroup();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
    
	//Constructeur
	
	public CreateGroup()  {
		
		setTitle("Création d'un group");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(70, 70, 350, 100);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(3, 3, 3, 3));
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
				
		lblGroup = new JLabel("Nom du Groupe :");
		lblGroup.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblGroup.setBounds(10, 20, 120, 30);
		contentPane.add(lblGroup);
		
		grpTextField = new JTextField();
		grpTextField.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		grpTextField.setBounds(120, 20, 120, 30);
		contentPane.add(grpTextField);
				
		btnCreate = new JButton("Créer");
		btnCreate.setBounds(250, 20, 80, 30);
		btnCreate.setForeground(Color.WHITE);
		btnCreate.setBackground(Color.BLUE);
		btnCreate.setFont(new Font("Arial", Font.BOLD, 15));
		
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					register();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		contentPane.add(btnCreate);
		setVisible(true);
	}//fin CreateGroup
	
	//Enregistrement d'un nouveau groupe
	
	private void register() throws ClassNotFoundException, SQLException, Exception {
		
		String groupName = grpTextField.getText();
		DbHandler dbHandler = new DbHandler();
		
		GroupTransfer groupTransfer = new GroupTransfer(groupName,InfoUser.USER_NAME);
		String msg = "";
        
        if(groupName.length()==0) {
        	
        	grpTextField.setText("");
			JOptionPane.showMessageDialog(btnCreate, "Non du groupe  ne doit pas être vides !!!");				
        }else {
        	try {
        		if(!dbHandler.fetchGroup(groupTransfer)) {
        			int res= dbHandler.storeGroup(groupTransfer); 
        			if(res>0) {
        				grpTextField.setText("");
        				msg = " Enregistrement Réussie";
        				JOptionPane.showMessageDialog(null, msg,"Message",JOptionPane.INFORMATION_MESSAGE);
        				this.setDefaultCloseOperation(CreateGroup.DISPOSE_ON_CLOSE);
        			}else {
        				grpTextField.setText("");
        				JOptionPane.showMessageDialog(null, "L'enregistrement a échoué ","Message",JOptionPane.WARNING_MESSAGE);
        			}
        		}else {
        			grpTextField.setText("");
    				JOptionPane.showMessageDialog(null, "Le groupe existe ","Message",JOptionPane.WARNING_MESSAGE);
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
	//==========================================	
}