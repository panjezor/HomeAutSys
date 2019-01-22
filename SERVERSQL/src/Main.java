
public class Main {
	public static void main(String[] arg) {
		SQL account, home, settings;
		
		LoadFile file = new LoadFile("state.txt");
		System.out.println(file);
		//sets up the system on startup
		if(file.verfieystate()){
			account = new SQL("localhost", "3306", "root", "", "account", true);
			home = new SQL("localhost", "3306", "root", "", "HOMESMART", true);
			settings = new SQL("localhost", "3306", "root", "", "settings", true);
			account.runCommand("-u account", false);
			account.runCommand("INSERT INTO `accounts` (`id`, `user`, `pass`)"
					+ " VALUES (NULL, 'admin', 'password')", true);
		}else {
			account = new SQL("localhost", "3306", "root", "", "account", false);
			home = new SQL("localhost", "3306", "root", "", "HOMESMART", false);
			settings = new SQL("localhost", "3306", "root", "", "settings", false);
		}
	}
}
