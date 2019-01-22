import java.util.ArrayList;
import java.util.List;

public class SettingsObj {
	private LoadFile settingFile = new LoadFile("settings.txt");
	private ArrayList<String> fileset;
	SQL settings = null;
	public SettingsObj(Boolean state) {
		if(state) {
			settings = new SQL("localhost", "3306", "root", "", "settings", true);
			
			ArrayList<String> com = settingFile.getArr(true);
			ArrayList<String> arg = settingFile.getArr(false);
			
			String query ="";
			for(int i = 0; i<com.size(); i++) {
				if(com.get(i).contains("sn")) {
					query=arg.get(i);	
					settings.runCommand(query, true);
				}
			}
		}else {
			settings = new SQL("localhost", "3306", "root", "", "settings", false);
		}
		
		
		settings.runCommand("-u settings", false);
		fileset = settings.returnCommand("-s * -f settings", 3, false);
		System.out.println(fileset);
		
		
		System.out.println(fileset.get(0));
		
	}
	public ArrayList<String> getSettings() {
		return fileset;
	}
	public static void main(String[] args) {
		SettingsObj n = new SettingsObj(false);
	}


}
