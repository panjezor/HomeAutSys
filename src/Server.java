import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Server extends Thread{
	SettingsObj settings;
	private int port;
	private ServerSocket serverSocket;
	private String task = null;
	public String result;
	private SQL account, home;
	
	
	public static void main(String[] args) {
		
		
	}
	
	
	//creates the server
	public Server(int port) throws IOException{
		

		//creates the server
		serverSocket = new ServerSocket(port);
		settings = new SettingsObj(false) ;
	}
	
	//listens for connection
	public void run() {
		
		try {
			//waits for connection
			System.out.println("Running Thread...");
			System.out.println("Waiting for client on port: "+ serverSocket.getLocalPort());
			Socket server = serverSocket.accept();
			System.out.println("CONNECTED TO: " + server.getRemoteSocketAddress());
			DataOutputStream out = new DataOutputStream(server.getOutputStream());
			DataInputStream input = new DataInputStream(server.getInputStream());
			

				System.out.println("connected");
				String s = input.readUTF();
				if(s != null) {
					
					String[] array = s.split(";");
					String compared = array[0].trim();
					String command = array[1];
					int value = Integer.parseInt(array[2]);
					
					
					System.out.println(array);
					for(int i=0; i<array.length; i++) {
					System.out.println(array[i]);
					}
					
							//sql
					if(compared=="sql") {
						//gets the database to change
						String database = array[3];
						SQL connect = new SQL("localhost", "3306", "root", "", database, false);
						
						//to write to database
						if(value == 1) {
							connect.runCommand(command);
						}else {
							String table = array[4];
							String type = array[5];
							ArrayList<String> output = connect.returnCommand(command, Integer.parseInt(table), Boolean.parseBoolean(type));
											
								for(String data : output) {
									out.writeUTF(data);
								}
							
						}
						//to senddata to arduino
					}else if(compared.contains("ard")) {
						System.out.println("hello");
						
						Thread t = new Thread(new Client("192.168.1.2"/*settings.getIPArd()*/, 23, array[1].trim()+"\n" ));
						t.start();
					
						
									
					}//to send data to andriod
					else if(compared == "and") {
						
					}
					//alarm system
					else if(compared =="ale"){
						
					}else {
					//server.close();
					}
					
				}
			
			
			
			
			//FileInputStream input = new FileInputStream();
					
			//String equation = "";
			//equation.trim();
			
			
		}catch(IOException e) {}
	
	}
		   //returns value
		  
		   public boolean accountCheck(String user, String password) {
				String query = "SELECT COUNT(1) FROM accounts WHERE user ==" + user +" AND pass=="+password;
			   
				try {
					java.sql.Statement stmt;
					Connection conn = null;
					stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery(query);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			   return false;
		   }

		private String convertArray(ArrayList input) {
			StringBuilder s = new StringBuilder();
			for(int i = 0; i<=input.size()-1; i++) {
				s.append(input.get(i));
			}
			return s.toString();
		}
	
	}


	
	
