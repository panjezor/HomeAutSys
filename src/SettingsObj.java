import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class SettingsObj {
	//settings configfile
	private LoadFile settingFile = new LoadFile("settings.txt");
	//extacted settings from file
	private String IPHost;
	private String fileset;
	private String IPArd;
	private String email;
	private int tempMax;
	private int light;
	private boolean alarm;
	private boolean tempState;
	//settings
	
	SQL settings = null;
	public SettingsObj(Boolean state) {
		if(state) {
			settings = new SQL("localhost", "3306", "root", "", "settings", true);
			try {
				InetAddress localHost = InetAddress.getLocalHost();
				ArrayList<String> localIP = new ArrayList<String>(); 
				String[] pureHost=localHost.toString().split(".");
				
				for(int i=0; i<pureHost.length; i++) {
					int j = pureHost[i].length();
					if(j==1) {
						localIP.add("00"+pureHost[i]);
					}else if(j==2) {
						localIP.add("0"+pureHost[i]);
					}else {
						localIP.add(pureHost[i]);
					}
				}
				IPHost = localIP.toString().replaceAll(",", "");
				System.out.println("Local IP: " + IPHost);
				setIPHost(IPHost);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
		fileset = settings.returnCommandSingle("-s * -f settings", 3, false);
		System.out.println(fileset);
		String[] line = fileset.split(",");
		setIPArd(line[1].trim());
		setAlarm(line[2].trim());
		setLight(line[3].trim());
		setMaxTemp(line[4].trim());
		setTempState(line[5].trim());
		
	}
	
	//get and set data///
	//get the raw data
	public String[] getSettings() {
		String[] s = fileset.split(",");
		return s;
	}
	//set Arduino ip address
	private void setIPArd(String in) {
		IPArd = in;
	}
	public String getIPArd() {
		return IPArd;
	}
	//IPs of host device
	private void setIPHost(String in) {
		IPHost = in;
	}
	public String getIPHost() {
		return IPHost;
	}
	//set temp that the heater can go to,
	private void setMaxTemp(String in) {
		tempMax = Integer.parseInt(in);
	}
	public int getMaxTemp() {
		return tempMax;
	}
	//set if the temp is on or off
	private void setTempState(String in) {
		tempState = Boolean.parseBoolean(in);
	}
	public boolean getTempState() {
		return tempState;
	}
	//sets the alarm state
	private void setAlarm(String in) {
		alarm = Boolean.parseBoolean(in);
	}
	public boolean getAlarm() {
		return alarm;
	}
	//set lights
	private void setLight(String in) {
		light = Integer.parseInt(in);
	}
	public int getLight() {
		return light;
	}
	//set email
	private void setEmail(String in) {
		email = in;
	}
	public String getEmail() {
		return email;
	}
	public static void main(String[] args) {
		SettingsObj n = new SettingsObj(false);
	}


}
