import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class LoadFile {
	private File f = null;

	private ArrayList<String> com = new ArrayList<String>();
	private ArrayList<String> arg = new ArrayList<String>();
	
	public LoadFile(String filePath){
		f = new File(filePath);
		try {
			BufferedReader in = new BufferedReader(new FileReader(f));
			String line;
			while(((line = in.readLine()) != null)) {

				String[] s = line.split("=");
				System.out.println(line);
				try {	
					com.add(s[0].trim());
					arg.add(s[1].trim());
				}catch(ArrayIndexOutOfBoundsException e) {
					System.out.println("Line");
				}
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public ArrayList getArr(boolean i) {
		if(i) {
			return com;
		}
		else {
			return arg;
		}
	}
	public void command() {	
	
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LoadFile read = new LoadFile("SQL.txt");
		System.out.println(read.getArr(true));
		System.out.println(read.getArr(false));
	}

}
