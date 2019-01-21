import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class Client {
	
	private static String serverName = null;
	private static int portConnection ;
	
	public static void main(String[] args) {
			
		serverName = null;
		portConnection = 6666;
		String argsin = args[0];
		
		System.out.println("running");
		
		Socket client;
		try {
			//create connecitiion
			client = new Socket(serverName, portConnection);
			//states it connected debug
			System.out.print("connected to: " + client);
			//creates input stream
			InputStream inFromServer = client.getInputStream();
			
			DataInputStream in = new DataInputStream(inFromServer);
			
			
			
			
			DataOutputStream out = new DataOutputStream(client.getOutputStream());
			
			out.writeUTF(argsin);
			
			
			
			
		    System.out.println(in.readUTF());
		    client.close();
		    
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Client(String ip, int port) {
		serverName = ip;
		portConnection = port;
		
	}
	public void run() {
		
		
	}
}
