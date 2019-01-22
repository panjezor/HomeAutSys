import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class Client {
	//vars for connection and what to
	private static String serverName = null;
	private static int portConnection ;
	private boolean srvopt;
	public static void main(String[] args) {
		
	}
	public Client(String ip, int port, boolean opt) {
		serverName = ip;
		portConnection = port;
		
	}
	public void run(String command) {
		
		String argsin = command;
		
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
			
			if(srvopt){
			out.writeUTF(argsin);
			}else {
				
			}
			
			
			
		    System.out.println(in.readUTF());
		    client.close();
		    
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
