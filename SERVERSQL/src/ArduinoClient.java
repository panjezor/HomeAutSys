	import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
	import java.io.DataInputStream;
	import java.io.DataOutputStream;
	import java.io.IOException;
	import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
public class ArduinoClient implements Runnable{
		//vars for connection and what to
		private static String serverName = null;
		private static int portConnection ;
		String command ;

		//sets up the connection
		public ArduinoClient(String ip, int port, String comm) {
			serverName = ip;
			portConnection = port;
			command = comm;
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
				PrintWriter out ;
				System.out.println(client.isConnected());
				//send command then close
				if(client.isConnected() == true){
					out = new PrintWriter(client.getOutputStream());
					out.print(command);
					out.close();
				}
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
