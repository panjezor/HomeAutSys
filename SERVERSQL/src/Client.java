	import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
	import java.io.DataInputStream;
	import java.io.DataOutputStream;
	import java.io.IOException;
	import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import javax.sound.sampled.Line;
public class Client implements Runnable{
		//vars for connection and what to
		private boolean read = false;
		private String serverName = null;
		private int portConnection ;
		private String command ;
		private ArrayList<String> data;
		//sets up the connection (there are 2 so that if needed it can be used to get the data from the connection)
		public Client(String ip, int port, String comm) {
			this.serverName = ip;
			this.portConnection = port;
			this.command = comm;
		}
		//if needed to get the data from the connection
		public Client(String ip, int port, String comm, boolean read) {
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
				client = new Socket(serverName, portConnection);
				
				BufferedReader in ;
			
				PrintWriter out ;
				
				
				System.out.println(client.isConnected());
					in = new BufferedReader(new InputStreamReader(client.getInputStream()));
					
					
					
					System.out.println("command");
					//if data is needed to be recived
				out = new PrintWriter(client.getOutputStream());
				out.print(command);
				System.out.println("command");
				if(read) {
					System.out.println("command");
					while(in.ready()) {}
					ArrayList<String> array = new ArrayList<String>();
					String line;
					while((line = in.readLine()) != null) {
					System.out.println(line);
					array.add(line);
					}
					setReceivedArray(array);
					
				}
				
					
					
					out.close();
				
				System.out.println("System");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		//sets the data from server
		public void setReceivedArray(ArrayList<String> input) {
			this.data = input;
		}
		//fetechs data from server
		public ArrayList<String> getReceivedArray(){
			return data;
		}
		public static void main(String[] args) {
			//can be used as a thread but can be used as a simple
				
				Client a = new Client("127.0.0.1", 8888, "ard;791;0\n");
				a.run();
			
				
				
			//	Thread t = new Thread(new Client("192.168.1.2", 23, "21\n");
			//	t.start();
				
			
		
		}
	}

