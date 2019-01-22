
public class Main {
	public static void main(String[] arg) {
		SQL account, home;
		SettingsObj settings;
		
		LoadFile file = new LoadFile("state.txt");
		System.out.println(file);
		//sets up the system on startup
		if(file.verfieystate()){
			settings = new SettingsObj(true);
			account = new SQL("localhost", "3306", "root", "", "account", true);
			home = new SQL("localhost", "3306", "root", "", "HOMESMART", true);	
			account.runCommand("-u account", false);
			account.runCommand("INSERT INTO `accounts` (`id`, `user`, `pass`)"
					+ " VALUES (NULL, 'admin', 'password')", true);
		}else {
			settings = new SettingsObj(false);
			account = new SQL("localhost", "3306", "root", "", "account", false);
			home = new SQL("localhost", "3306", "root", "", "HOMESMART", false);
			
		}
	}
}
