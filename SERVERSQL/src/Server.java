import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread{
	private ServerSocket serverSocket;
	private String task = null;
	public String result;
	
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
		serverSocket = new ServerSocket(port);
		//serverSocket.setSoTimeout(10000);
		
	}
	
	public void sendfile() {
		
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
			
			
			
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			//FileInputStream input = new FileInputStream();
					
			//String equation = "";
			//equation.trim();
			
			
		}catch(IOException e) {}
	}
	
	
}
