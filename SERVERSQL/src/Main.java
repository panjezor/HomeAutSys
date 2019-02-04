import java.io.IOException;
import java.util.ArrayList;

public class Main {
	public static void main(String[] arg) throws IOException {
		SQL account, home;
		SettingsObj settings;
		
		LoadFile file = new LoadFile("state.txt");
		System.out.println(file);
		//sets up the system on startup
		if(file.verfieystate()){
			settings = new SettingsObj(true);
			account = new SQL("localhost", "3306", "root", "", "account", true);
			home = new SQL("localhost", "3306", "root", "", "HOMESMART", true);	
			account.runCommand("-u account");
			account.runCommand("INSERT INTO `account` (`id`, `user`, `pass`)"
					+ " VALUES (NULL, 'admin', 'password')");
		}else {
			settings = new SettingsObj(false);
			account = new SQL("localhost", "3306", "root", "", "account", false);
			home = new SQL("localhost", "3306", "root", "", "HOMESMART", false);
			
		}
	
			try {
				IPchecker arduinoIP = new IPchecker();
				String[] addressArray = arduinoIP.getIPs();
			boolean ardFound = true;
			while(ardFound) {
				for (String address : addressArray) {
					System.out.println(address);
					Client client = new Client(address.trim(), 23, "900", 3);
					client.run();
					//reply from arduino
					String res = client.getReceivedString();
					System.out.println("The reply was: "+ res);
					if(res != null) {
						System.out.println("we got one!!!");
						
						String iplocal = arduinoIP.getHostIP();	

						System.out.println("IP address "+ iplocal);
						ArduinoClient aClient = new ArduinoClient(address, 23, iplocal+"\n");
						aClient.run();
						ardFound =false;
						break;
					}
//					System.out.println(reply.size());
//					if(reply.size()-1 >0) {
//						System.out.println("we got one!!!");
//						break;
//					}
				}
			}
				Thread t = new Thread(new Server(8888, false));
				t.start();
				Thread t1 = new Thread(new Server(8889, true));
				t1.start();
					
			
				
			}catch(IOException e ) {
				e.printStackTrace();
			}
		
	}
}
