
public class Clientcheck {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Client credcheck = new Client("192.168.1.4", 8888, "sql;"+null+";3;account;admin;password", 2);
        credcheck.run();
        String c = credcheck.getReceivedString();
        System.out.println(c);
	}

}
