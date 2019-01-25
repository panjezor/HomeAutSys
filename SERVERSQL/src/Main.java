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
	
//			try {
//				IPchecker arduinoIP = new IPchecker();
//				String[] addressArray = arduinoIP.getIPs();
//				
//				for (String address : addressArray) {
//					System.out.println(address);
//					Client client = new Client(address, 23, "900\n", true);
//					ArrayList<String> reply = client.getReceivedArray();
//					System.out.println(reply);
//					//if(1 <= reply.size()) {
//					//	break;
//					//}
//				}
				Thread t = new Thread(new Server(8888));

				t.start();

					
			
				
//			}catch(IOException e) {
//				e.printStackTrace();
//			}
		
	}
}
