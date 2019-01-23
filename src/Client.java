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
			System.out.println(serverName + ":"+portConnection+" "+ command);
			//creates the socket
			Socket client;
			try {
				//create connection
				System.out.println("connecting....");
				client = new Socket(serverName, portConnection);

				BufferedReader in ;
			
				PrintWriter out ;
				
				
				System.out.println(client.isConnected());
				if(client.isConnected() == true){
					out = new PrintWriter(client.getOutputStream());
					out.print(command);
					out.close();
				}
				//if data is needed to be recived
				if(read) {
					in = new BufferedReader(new InputStreamReader(client.getInputStream()));
					ArrayList<String> array = new ArrayList<String>();
					String line;
					while((line = in.readLine()) != null) {
					System.out.println(line);
					array.add(line);
					}
					setRecievedArray(array);
				}
				
				System.out.println("helloworld");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		//sets the data from server
		public void setRecievedArray(ArrayList<String> input) {
			this.data = input;
		}
		//fetechs data from server
		public ArrayList<String> getRecievedArray(){
			return data;
		}
		public static void main(String[] args) {
			//can be used as a thread but can be used as a simple
				Client a = new Client("192.168.1.2", 23, "011\n");
				a.run();
				
				
			//	Thread t = new Thread(new Client("192.168.1.2", 23, "21\n", false));
			//	t.start();
				
			
		
		}
	}

