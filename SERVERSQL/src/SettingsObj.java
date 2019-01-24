import java.util.ArrayList;
import java.util.List;

public class SettingsObj {
	//settings configfile
	private LoadFile settingFile = new LoadFile("settings.txt");
	//extacted settings from file
	private ArrayList<String> fileset;
	private ArrayList<String> settingscomms = new ArrayList<String>();
	private ArrayList<String> settingsargs = new ArrayList<String>();
	private String IPArd;
	//settings
	
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
					settings.runCommand(query);
				}
			}
		}else {
			settings = new SQL("localhost", "3306", "root", "", "settings", false);
		}
		
		
		settings.runCommand("-u settings");
		fileset = settings.returnCommand("-s * -f settings", 3, false);
		System.out.println(fileset.size());
		for(int i =0; i<fileset.size(); i++) {
			String[] s = fileset.get(i).split(",");
			settingscomms.add(s[1]);
			settingsargs.add(s[2]);
			if(s[1].contains("ipard") ){
				setIPArd(s[2]);		
			}
		}
		System.out.println(settingscomms +" \n"+settingsargs);
		
		
	}
	public ArrayList<String> getSettings() {
		return fileset;
	}
	public ArrayList getComm() {
		return settingscomms;
	}
	public ArrayList getargs() {
		return settingsargs;
	}
	private void setIPArd(String in) {
		IPArd = in;
	}
	public String getIPArd() {
		return IPArd;
	}
	public static void main(String[] args) {
		SettingsObj n = new SettingsObj(false);
	}


}
