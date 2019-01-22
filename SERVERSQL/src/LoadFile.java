import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class LoadFile {
	//private Vars for the file
	private File f = null;
	//commands and args
	private ArrayList<String> com = new ArrayList<String>();
	private ArrayList<String> arg = new ArrayList<String>();
	public LoadFile(String filePath){
		//reads the file
		f = new File(filePath);
		try {
			//reads the file
			BufferedReader in = new BufferedReader(new FileReader(f));
			String line;
			while(((line = in.readLine()) != null)) {
				String[] s = line.split("=");
				try {	
					//adds to the array lists
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
	//returns one of the arrays depending on the value
	public ArrayList getArr(boolean i) {
		if(i) {
			return com;
		}
		else {
			return arg;
		}
	}
	public boolean verfieystate() {	
		String val = null;
		val = arg.get(0);
		
		if(Boolean.parseBoolean(val)) {
			try {
				FileWriter out = new FileWriter(f);
				out.write("state = false");
				
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}else {return false;}
	
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LoadFile read = new LoadFile("SQL.txt");
		//System.out.println(read.getArr(true));
		//System.out.println(read.getArr(false));
	}

}
