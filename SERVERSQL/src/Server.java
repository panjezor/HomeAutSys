import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.ArrayList;
import java.util.ArrayList;
import java.util.Properties;

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
				s = input.readLine(); 
				System.out.println("dank");
				System.out.println(s);
					String[] array = s.split(";");
					String compared = array[0].trim();
					String command = array[1].trim();
boolean checker = accountCheck(compared, command);
if(checker) {
	out.write(Integer.parseInt("1"));
}
			System.out.println(checker);
					//if the array is londer than 2 then include it(for rw and check)
//					int arg0 = 0;
//					if(2<array.length) {
//					arg0 = Integer.parseInt(array[2]);
//					}
//
//					System.out.println(array);
//
//
//
//							//sql
//					if(compared=="sql") {
//						//gets the database to change
//						String database = array[3];
//						SQL connect = new SQL("localhost", "3306", "root", "", database, false);
//						//get the arguments(such as table and read and write)
//						String arg1 = "";
//						String arg2 = "";
//						if(array.length <=5) {
//							arg1 = array[4];
//							arg2 = array[5];
//						}
//
//						//to write to database
//
//						switch(arg0) {
//						//gets the data and outputs it in a string array
//						case 0:
//							ArrayList<String> output = connect.returnCommand(command, Integer.parseInt(arg1), Boolean.parseBoolean(arg2));
//
//								for(String data : output) {
//									out.writeUTF(data);
//								}
//							break;
//						//just runs a command
//						case 1:
//							connect.runCommand(command);
//							break;
//						//checks account
//						case 2:
//							int acc = (connect.accountCheck(arg1, arg2) ? 1:0);
//							out.write(acc);
//						}
//
//						//to senddata to arduino
//					}else if(compared.contains("ard")) {
//						System.out.println();
//						System.out.println("hello");
//						System.out.println("Everybody wants to rule the world");
//
//						Client t = new Client(settings.getIPArd().trim(), 23, array[1].trim()+"\n" );
//						t.run();
//
//					}//to send data to andriod
//					else if(compared == "and") {
//
//					}
//					//alarm system
//					else if(compared =="ale"){
//
//					}else {
//						System.out.println("server closed");
//					//server.close();
//					}
//
//
			
			
			
			
			//FileInputStream input = new FileInputStream();
					
			//String equation = "";
			//equation.trim();
			
			
		}catch(IOException e) {}
		}
	}
		   //returns value
		  
		   public boolean accountCheck(String user, String password) {
				String query = "SELECT COUNT(1) FROM account WHERE user = '"+user+"' AND pass = '"+password+"'";
			   
				try {
					Properties connectionProps = new Properties();
					connectionProps.put("user", "root");
					connectionProps.put("password", "");
					java.sql.Statement stmt1;
					java.sql.Statement stmt2;
					Connection conn;
					conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", connectionProps);
					stmt1 = conn.createStatement();
					stmt2 = conn.createStatement();
					stmt1.execute("use account");
						ResultSet rs = stmt2.executeQuery(query);

						if (rs != null) {
							return true;
						}

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


	
	

