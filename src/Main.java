import java.io.IOException;

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
			account.runCommand("INSERT INTO `accounts` (`id`, `user`, `pass`)"
					+ " VALUES (NULL, 'admin', 'password')");
		}else {
			settings = new SettingsObj(false);
			account = new SQL("localhost", "3306", "root", "", "account", false);
			home = new SQL("localhost", "3306", "root", "", "HOMESMART", false);
			
		}
		try {
			
			Thread t = new Server(23);
			
			t.start();
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
}
