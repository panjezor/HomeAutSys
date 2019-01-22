import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class FileCreate {
	//gobal var
	File file;
	//declares the file
	public FileCreate(String fileName) {
		file = new File(fileName);
	}
	public void writeTo(ArrayList<String> input) {
		
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			for(String a : input) {
				writer.write(a);
			}
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//read file simlar to loadfile
	public ArrayList readFrom(File input) {
		ArrayList<ArrayList> array = new ArrayList<ArrayList>();
	//loads the file
			LoadFile temp = new LoadFile(input.getAbsolutePath());
			array.add(temp.getArr(true));
			array.add(temp.getArr(false));
		
		return array;
	}
}
