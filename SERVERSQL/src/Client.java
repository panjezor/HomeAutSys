import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class Client {
    //vars for connection and what to
    private static String serverName = null;
    private static int portConnection;
    private boolean srvopt;

    //sets up the connection
    public Client(String ip, int port, boolean opt) {
        serverName = ip;
        portConnection = port;
        srvopt = opt;
    }

    //runs the command
    public String run(String command) {
        //sets the command
        String argsin = command;
        //states running debug
        System.out.println("running");
        //creates the socket
        Socket client;
        try {
            //create connection
            client = new Socket(serverName, portConnection);
            //states it connected debug
            System.out.print("connected to: " + client);
            //creates input stream from the server
            InputStream inFromServer = client.getInputStream();

            //sets up the stream
            DataInputStream in = new DataInputStream(inFromServer);


            //to arduino
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            //if just to send data to arduino
            if (srvopt) {
                out.writeUTF(argsin);
            }
            //else
            else {

            }


            //what is received
            String reading = in.readUTF();
            client.close();
            return reading;

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "error";
        }

    }
}
