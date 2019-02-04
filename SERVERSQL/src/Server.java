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
		System.out.println(settings.getIPArd());
	}
	
	//listens for connection
	public void run() {
	while(true) {
		try {
			//waits for connection
			System.out.println("Running Thread...");
			System.out.println("Waiting for client on port: "+ serverSocket.getLocalPort());
			Socket server = serverSocket.accept();
			System.out.println("CONNECTED TO: " + server.getRemoteSocketAddress());
			DataOutputStream out = new DataOutputStream(server.getOutputStream());
			DataInputStream input = new DataInputStream(server.getInputStream());
			

				System.out.println("connected");

				String s;
				s = input.readUTF(); 
				
				System.out.println(s);
					String[] array = s.split(";");
					String compared = array[0].trim();
					String command = array[1];
					//if the array is londer than 2 then include it(for rw and check)
					int arg0 = 0;
					if(2<array.length) {
					arg0 = Integer.parseInt(array[2]);
					}
					System.out.println(array);
					
					
					
							//sql
					if(compared=="sql") {
						//gets the database to change
						String database = array[3];
						SQL connect = new SQL("localhost", "3306", "root", "", database, false);
						//get the arguments(such as table and read and write)
						String arg1 = "";
						String arg2 = "";
						if(array.length <=5) {
							arg1 = array[4];
							arg2 = array[5];
						}
						
						//to write to database
						switch(arg0) {
						//gets the data and outputs it in a string array
						case 0:
							ArrayList<String> output = connect.returnCommandArray(command, Integer.parseInt(arg1), Boolean.parseBoolean(arg2));		
								for(String data : output) {
									out.writeUTF(data);
								}
							break;
						case 1:
							String stringOutput = connect.returnCommandSingle(command, Integer.parseInt(arg1), Boolean.parseBoolean(arg2));
							out.writeUTF(stringOutput);
							break;
						//just runs a command
						case 2:
							connect.runCommand(command);
							break;
						//checks account
						
						case 3:	
							int acc = (connect.accountCheck(arg1, arg2) ? 1:0);
							out.write(acc);
							break;
						
						}
						
						//to senddata to arduino
					}else if(compared.contains("ard")) {
						for(String S : array) {
						System.out.println("output to ard" + S);
						}
						Client t = new Client(settings.getIPArd().trim(), 23, command+"\n" );
						t.run();
									
					}//to send data to andriod
					else if(compared == "and") {
						
					}
					//alarm system
					else if(compared =="ale"){
						Email email = new Email(settings.getEmail(), command);
					}else {
						System.out.println("server closed");
					//server.close();
					}
					
				
			
			
			
			
			//FileInputStream input = new FileInputStream();
					
			//String equation = "";
			//equation.trim();
			
			
		}catch(IOException e) {}
		}
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


	
	

