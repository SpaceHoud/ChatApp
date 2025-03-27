package fr.chat.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import fr.chat.model.GroupTransfer;
import fr.chat.model.MessageTransfer;
import fr.chat.model.UserTransfer;

public class DbHandler {
   private static Connection connection;
   private static Statement stmtSelect;
   private static Statement stmtInsert;
   
    public DbHandler() {
        try {
        	// Chargement du driver MySQL
            Class.forName("com.mysql.jdbc.Driver");
            
            String url = "jdbc:mysql://localhost/db_chat_app";
            String username = "root";
            String password = "root";
            
            // Création de la connexion
            connection = DriverManager.getConnection(url, username, password);
            
            // Création d'un état de connexion
            stmtSelect = connection.createStatement();
            stmtInsert = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }    
    
    //Existence du compte dans la base
	public static boolean isUserAuthenticated(UserTransfer userTransfer){
    	boolean record= false;
    	ResultSet resultSet;
    	String password= (String) userTransfer.getPassword();
       
    	try {
    		
    		String query = "SELECT username, password FROM users WHERE username="+"\""+  userTransfer.getUsername() +"\""+ "  AND password="+ "\""+password+"\""+";";
    		System.out.println(query);
    		resultSet = stmtSelect.executeQuery(query);
            record = resultSet.next(); 
            System.out.println(record);
           
          }catch(Exception e){
   		 	e.printStackTrace();
    	}
    	return record;
    }//Fin isUserAuthenticated
    
	//Insertion de l'utilisateur dans la base
    public static int storeUser(UserTransfer userTransfer) {
    	int record= 0;
    	try {
	    	String password= (String) userTransfer.getPassword();
	    	String user = userTransfer.getUsername();
	    	String query = "INSERT INTO users(username, password) VALUES ('" + user + "', '" + password + "');";
	    	record = stmtInsert.executeUpdate(query);    	  	
    	}catch(Exception e){
   		 	e.printStackTrace();
    	}
    	return record;  	
    }
  
 // Recherche un groupe
 	public static boolean fetchGroup(GroupTransfer groupTransfer) throws SQLException, ClassNotFoundException, Exception{
 	
  		boolean record=false;
  		ResultSet resultSet;
  		
  		try {
    		String querySQL = "select group_name from groups where group_name=" +"\""+ groupTransfer.getGroupName()+"\""+";";
    		System.out.println(querySQL);
    		resultSet = stmtSelect.executeQuery(querySQL);
            record = resultSet.next(); 
    		
    	}catch(Exception e){
   		 	e.printStackTrace();
    	}
  	   	return record;
 	}// fin fetchGroup
 	
 	//Récupération de l'id du créateur du groupe
 	public static int fetchCreatorId(GroupTransfer groupTransfer) throws SQLException {
    	int creator_id=-1;
    	ResultSet resultSet;
    	try {
    		String sqlSelect = "SELECT user_id FROM users WHERE username = " +"\""+ groupTransfer.getCreatorName()  +"\""+";";
    		resultSet = stmtSelect.executeQuery(sqlSelect);
    		
    		while (resultSet.next()) {
				creator_id = resultSet.getInt(1);
			}    		    		
    	 }catch(Exception e){
   		 	e.printStackTrace();
    	}
    	return creator_id;    	
    }//Fin fetchCreatorId
 	
 	//Enregistrer un groupe
	public static int storeGroup(GroupTransfer groupTransfer) throws Exception {
		
		int record= 0;
		int creator_id = fetchCreatorId(groupTransfer);
    	try {
	    	String groupName = groupTransfer.getGroupName();
	    	String query = "INSERT INTO groups(group_name, creator_id) VALUES ("+"\""+ groupName + "\""+","+creator_id  +");";
	    	System.out.println(query);
	    	record = stmtInsert.executeUpdate(query);  
		    	
	    	return record;   	
		}catch(Exception e){
   		 	e.printStackTrace();
    	}
    	return record;   	
	}//fin storeGroup
	
	//Recherche de l'id du créateur du message
	public static int fetchMessageCreatorId(MessageTransfer messageTransfer) throws SQLException {
    	int creator_id=-1;
    	ResultSet resultSet;
    	try {    		
    		String sqlSelect = "SELECT user_id FROM users WHERE username = "+"\"" + messageTransfer.getSender_username() +"\""+";";
    		resultSet = stmtSelect.executeQuery(sqlSelect);
    		
    		while (resultSet.next()) {
				creator_id = resultSet.getInt(1);
			}
    		
    	 }catch(Exception e){
   		 	e.printStackTrace();
    	}
    	return creator_id;    	
    }//Fin fetchCreatorId
	
	//Enregistrement des messages dans la base
    public static int storeMessage(MessageTransfer messageTransfer) throws Exception {
		
		int record= 0;
		int sender_id = fetchMessageCreatorId(messageTransfer);
		String message = messageTransfer.getContent();
    	try {
																										
    		String query = "INSERT INTO messages(sender_id, recipient_id, group_id, content, is_private) VALUES (" + sender_id + ",1,1,"+"\""+  message +"\""+ ",0);"; 			
	    	record = stmtInsert.executeUpdate(query);
	    	System.out.println(query); 
	    	return record;
	    	
		}catch(Exception e){
   		 	e.printStackTrace();
    	}
    	return record;	   	
	}//fin storeGroup
/*		
	public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}

	try {
        if (statement != null) {
            statement.close();
        }
        if (connection != null) {
            connection.close();
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
   */     
}
