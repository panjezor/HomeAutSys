import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class SQL {
		   private static ArrayList<ArrayList> masterDatabase = null;
		//install drivers 
		   private final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
		   private String dbUrl;
		   private static LoadFile sqlf = new LoadFile("sql.txt"), dbInfo = null;
		   //  Database credentials
		   private  String user;
		   private  String pass;
		//   private static int[] col = ;
		   
		   //global vars 

		   private String dbName;
		   private static java.sql.Statement stmt;
		   private static Connection conn = null;
		
	
    public SQL(String ip, String port, String username, String password, String database, boolean createT) {	    	
    			   
    		try {
    			this.dbName = database; 
    			this.dbInfo = new LoadFile(database+".txt");
		//gets the sql class
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
		   //account data
		   dbUrl ="jdbc:mysql://"+ip+":"+port+"/";
		   user = username;
		   pass = password; 
		   
		 try {
			 //connects to the database
			conn = DriverManager.getConnection(dbUrl, user, pass); 
			createDatabase();

			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
	   }
	    //use the SQL
	   public static void runCommand(String command) {
//		   colum=col;
		   String[] array = command.split(" ");
			String state = SQLInput(array);
			try {
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(state.toString());
				
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	   }
	   
	   //returns value
	   public static ArrayList returnCommand(String command, int databasename) {
//		   colum=col;
		   	String database =  (String) masterDatabase.get((masterDatabase.size()-1)).get(databasename);
		    System.out.println(database);
		    
		   	ArrayList<String> out = new ArrayList<String>();
			String[] array = command.split(" ");
			String state = SQLInput(array);
			try {
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(state);
				while(rs.next()) {
					out.add(rs.getString("id"));
					out.add(rs.getString("name"));
					out.add(rs.getString("sensorID"));
					switch(databasename) {
							
					case 0:
						
						break;
					case 1:
						out.add(rs.getString("value"));
						out.add(rs.getString("logs"));
						break;
						}
				}
		} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
				
				
			
			return out;
	   }
	   //changes input to a real command
	public static String SQLInput(String[] Value) {
		ArrayList<String> comname = sqlf.getArr(false);
		ArrayList<String> comarg = sqlf.getArr(true);
	//combines the strings into one string
		StringBuilder s = new StringBuilder();
		
		for(String a : Value) {
			if(a.contains("-")) {
				for(int i =0; i<comarg.size()-1; i++) {
					if(a.contains(comarg.get(i))) {
					s.append(" "+comname.get(i) +" ");
					}
				}
			}else {
				s.append(a);
				
			}
		}
		s.append(";\n");
		//returns the command ready to go
		return s.toString();  
	}
	
	
	
	//creates the databases if they don't exist
	private void createDatabase() {
		//get the database values and args from folder
		ArrayList<String> filec = dbInfo.getArr(true);
		ArrayList<String> filea = dbInfo.getArr(false);
		//2darray
		 ArrayList<ArrayList> databaseFull = new ArrayList<ArrayList>(); 
		 //database names and args 
		 ArrayList<String>tdname = new ArrayList<String>(), 
		 value = new ArrayList<String>();
		//change updates
		for(int i = 0; i<filec.size(); i++) { 
			String compare = filec.get(i);
			String arg = filea.get(i);
			
			if(compare.contains("tname")) {
				databaseFull.add(value);
				tdname.add(arg);
				value.clear();
			}else if(compare.contains("v")) {
				value.add(arg);
			}
			System.out.println();
			
		}
		databaseFull.add(tdname);
		
	
		//allows to view databases globally
		masterDatabase = databaseFull;
		//cteates the database
		int count = (databaseFull.get((databaseFull.size()-1)).size());
		String query = null;
		//checks if exists
			try {
				query = "CREATE DATABASE " + dbName +";";
				stmt = conn.createStatement();
				stmt.executeUpdate(query);
		    } catch (SQLException e1) {
				// TODO Auto-generated catch block
				System.out.println("Already exists");
			}
		 //tries to use
		    try { 
		    	query = " use "+ dbName + ";";
				stmt.executeUpdate(query);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				System.out.println("alreading using");
			}
		//tries to create tables
		    for(int j = 0; j<count; j++) {  
		    	try {
					String values = databaseFull.get(databaseFull.size()-1).get(j).toString();
					System.out.println(values);
					query = "CREATE TABLE " + values + "(" +
							convertArray(databaseFull.get(j))
							+")";
					stmt.executeUpdate(query);
				} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Already Exists");
		}
	}
		
		

	}
	private String convertArray(ArrayList input) {
		StringBuilder s = new StringBuilder();
		for(int i = 0; i<=input.size()-1; i++) {
			s.append(input.get(i));
		}
		return s.toString();
	}
	public static void main(String[] args) {
		SQL hello = new SQL("localhost", "3306", "root", "", "HOMESMART", false);
		
		hello.runCommand("-u HOMESMART");
	//	hello.runCommand(" -s 'tempName' -f  ");	
		int database=1;
		System.out.println(dbInfo);
		ArrayList<String> out = hello.returnCommand("-s  * -f lightdata",1);
		System.out.println(out);
	}
}

