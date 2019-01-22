import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server extends Thread{
	private ServerSocket serverSocket;
	private String task = null;
	public String result;
	private SQL account, home, settings;
	private ArrayList<SQL> sqlArray = new ArrayList<SQL>();
	
	public static void main(String[] args) {
	
		
		int port;
		port = 6666;
		try {
			
			Thread t = new Server(port);
			t.start();
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
	//creates the server
	public Server(int port) throws IOException{
		sqlArray.add(account);
		sqlArray.add(home);
		sqlArray.add(settings);
			
		//creates the server
		serverSocket = new ServerSocket(port);
	
		//serverSocket.setSoTimeout(10000);
		LoadFile file = new LoadFile("state.txt");
		System.out.println(file);
		//sets up the system on startup
		if(file.verfieystate()){
			account = new SQL("localhost", "3306", "root", "", "account", true);
			home = new SQL("localhost", "3306", "root", "", "HOMESMART", true);
			settings = new SQL("localhost", "3306", "root", "", "settings", true);
			account.runCommand("-u account", false);
			account.runCommand("INSERT INTO `accounts` (`id`, `user`, `pass`)"
					+ " VALUES (NULL, 'admin', 'password')", true);
		}else {
			account = new SQL("localhost", "3306", "root", "", "account", false);
			home = new SQL("localhost", "3306", "root", "", "HOMESMART", false);
			settings = new SQL("localhost", "3306", "root", "", "settings", false);
		}
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
			
			while((input.readUTF())!=null) {
				String s = input.readUTF();
				if(s != null) {
					
					String[] array = s.split(";");
					String compared = array[0].trim();
					int value = Integer.parseInt(array[1]);
					String command = array[3];
					System.out.println(array);
					if(compared=="sql") {
						if(value == 1) {
							
							
						}else {
							
						}
					}else if(compared =="ard") {
					
					}else if(compared == "upd") {
						
					}else if(compared =="ale"){
						
					}else {}
					
				}
			}
			
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			//FileInputStream input = new FileInputStream();
					
			//String equation = "";
			//equation.trim();
			
			
		}catch(IOException e) {}
	}
	
	
	
}
