	import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
	import java.io.DataInputStream;
	import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.util.ArrayList;

import javax.sound.sampled.Line;
public class Client implements Runnable{
		//vars for connection and what to do
		private int read = 0;
		private String serverName = null;
		private int portConnection ;
		private String command;
		//if it needs a response array or string
		private ArrayList<String> dataArray;
		private String dataString;
		//sets up the connection (there are 2 so that if needed it can be used to get the data from the connection)
		public Client(String ip, int port, String comm) {
			this.serverName = ip;
			this.portConnection = port;
			this.command = comm;
		}
		//if needed to get the data from the connection
		public Client(String ip, int port, String comm, int read) {
			this.serverName = ip;
			this.portConnection = port;
			this.command = comm;
			this.read = read;
		}
	
		//runs the command
		public void run() {
			
			//sets the command
			String argsin = command;
			//states running debug
			System.out.println("running");
			
			//creates the socket
			Socket client;
			try {
				//create connection
				System.out.println("connecting....");
				try {
				client = new Socket(serverName, portConnection);
				
				
		        OutputStream serverOut = client.getOutputStream();
		        DataOutputStream out = new DataOutputStream(serverOut);

		         InputStream inServer = client.getInputStream();
		         DataInputStream in = new DataInputStream(inServer);
		         
				
				System.out.println(command);
				out.writeUTF(command);
			
				System.out.println("outputing command");
				
				//if needing to read any data that needs to be sent back
				String line;
				
					System.out.println(read);
					switch(read) {
						case 0:
							System.out.println("command send waiting for response(array)");
							
							ArrayList<String> array = new ArrayList<String>();
							
							try {
							while((line = in.readUTF()) != null) {
				
							System.out.println(line);
							array.add(line);
							}
							
							setReceivedArray(array);
							}catch(EOFException e) {
								
							}
							break;
						case 1:
							System.out.println("command send waiting for response(String)");
							
							
							System.out.println("String recived: " + in.readUTF());
							//setReceivedString(line);
							
							break;
						//if just to test connection
						case 3:
							if(client.isConnected()) {
								setReceivedString("1");
							}
							break;
						default:
			
							break;
					}
				
				
					
					
				client.close();
				
				System.out.println("System");
				}catch(ConnectException e) {
					System.out.println("could not connect");
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		//sets the data from server(Array)
		public void setReceivedArray(ArrayList<String> input) {
			this.dataArray = input;
		}
		//fetechs data from server(array)
		public ArrayList<String> getReceivedArray(){
			return dataArray ;
		}
		//sets the data from server(String)
		public void setReceivedString(String input) {
			this.dataString = input;
		}
		//fetechs data from server(String)
		public String getReceivedString(){
			return dataString;
		}
		
		public static void main(String[] args) {
			//can be used as a thread but can be used as a simple
				
			Client a = new Client(args[0], Integer.parseInt(args[1]), args[2],  Integer.parseInt(args[3])/*"ard;791;0\n"*/);
			a.run();
		
			
				
				
				//Thread t = new Thread(new Client("192.168.1.4", 8888, "ard;81;0\n"));
				//t.start();
				
			
		
		}
	}

