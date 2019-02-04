import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.mysql.jdbc.Statement;


public class SQL {
		   private  ArrayList<ArrayList> masterDatabase = null;
		//install drivers 
		   private final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
		   private String dbUrl;
		   private static LoadFile sqlf = new LoadFile("sql.txt"), dbInfo = null;
		   //  Database credentials
		   private  String user;
		   private  String pass;
		//   private static int[] col = ;
		   
		   //global vars 

		   public String dbName;
		   private static  java.sql.Statement stmt;
		   private static  Connection conn = null;
		
	
    public SQL(String ip, String port, String username, String password, String database, boolean createT) {	    	
    			   
    		try {
    			this.dbName = database; 
    			this.dbInfo = new LoadFile(dbName+".txt");
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
			if(createT) {
			createDatabase();
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
	   }
	    //use the SQL
	   public void runCommand(String command) {
//		   colum=col;
		   String[] array = command.split(" ");
		   String state = SQLInput(array);
			try {
				stmt = (Statement) conn.createStatement();
	
				   stmt.executeUpdate(state.toString());
				   
		
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	   }
	   //returns value single
	   public String  returnCommandSingle(String command, int table, boolean type) {
		   ArrayList<String> array = returnCommandArray( command, table, type);
		   System.out.println(array);
		   return array.get(array.size()-1);
}	
	   //returns the full string
	   public ArrayList<String> returnCommandArray(String command, int table, boolean type){
	   ArrayList<String>  address = new ArrayList<String>();
//	   colum=col;
	  
	   	ArrayList<String> out = new ArrayList<String>();
		String[] query = command.split(" ");
		String state = SQLInput(query);
		try {
			stmt = (Statement) conn.createStatement();
			System.out.println(state);
			ResultSet rs = stmt.executeQuery(state);
			
			while(rs.next()) {
				
				out.add(rs.getString("id"));
				switch(table) {

				case 0:
					out.add(rs.getString("foreginid"));
					break;
				case 1:
					out.add(rs.getString("value"));
					break;
				case 2:
					out.add(rs.getString("deviceID"));
					out.add(rs.getString("state"));
					break;
				case 3:
					out.add(rs.getString("ipard"));
					out.add(rs.getString("alarm"));
					out.add(rs.getString("lightnumber"));
					out.add(rs.getString("maxtemp"));
					out.add(rs.getString("tempstate"));
					out.add(rs.getString("email"));
					break;
					}
				
				if(type) {
					out.add(rs.getString("deviceID"));
					out.add(rs.getString("timelog"));
					
				}
			System.out.println("hello");
			address.add(out.toString().replaceAll("\\[", "").replaceAll("\\]", ""));
			out.clear();
			}   
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
}
			return address;
		   
	   }
	    
	   public boolean accountCheck(String user, String password) {
			String query = "SELECT COUNT(1) FROM account WHERE user ==" + user +" AND pass=="+password;
		   
			try {
				stmt = (Statement) conn.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				int check = 0;
					while(rs.next()) {
						if(user == rs.getString("user") && password == rs.getString("pass")) {
							return true;
						}
					}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		   return false;
	   }
	 
	   
	   
	   public boolean verfiy(String db) {
		   
		   String query = "use "+db+";";
			try {
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query);
				System.out.println(rs);
				return rs.next();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return false;
		   
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
				s.append(" "+a+" ");
				
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
	
		 //database names and args 
		 ArrayList<String>tdname = new ArrayList<String>(), 
		 value = new ArrayList<String>();
		//change updates
		for(int i = 0; i<filec.size(); i++) { 
			String compare = filec.get(i);
			String arg = filea.get(i);
			
			if(compare.contains("tname")) {
				tdname.add(arg);
			}else if(compare.contains("v")) {
				value.add(arg);
			}
			System.out.println(tdname);
		
		}

		//allows to view databases globally

		//cteates the database
		int count = tdname.size();
		String query = null;
		//checks if exists
			try {
				query = "CREATE DATABASE " + dbName +";";
				stmt = conn.createStatement();
				stmt.executeUpdate(query);
		    } catch (SQLException e1) {
				// TODO Auto-generated catch block
				System.out.println(dbName + " Database Already exists");
			}
		 //tries to use
		    try { 
		    	query = " use "+ dbName + ";";
				stmt.executeUpdate(query);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				System.out.println(dbName +" alreading using");
			}
		//tries to create tables
		    for(int j = 0; j<count; j++) {  
		    	String values = value.get(j);
		    	
		    	try {
					query = "CREATE TABLE " +  dbName +"."+ tdname.get(j) + "(" +
								value.get(j)
							+")";
					System.out.println("QUERY " + query);
					stmt.executeUpdate(query);
				} catch (SQLException e) {
			// TODO Auto-generated catch block
					e.printStackTrace();
			
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
	
}

