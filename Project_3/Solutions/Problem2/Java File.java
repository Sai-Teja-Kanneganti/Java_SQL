package trialrun;
import java.sql.*;
import java.util.*;

public class GP3_Problem2_Group13 {

		public static Scanner scan = new Scanner(System.in);
		private static Connection dbConnection;
		private static Statement stmt;
		
		public static void initdb() {
			try { 
				Class.forName("oracle.jdbc.OracleDriver"); 
		} catch(Exception x){ 
				System.out.println( "Unable to load the driver class!" );
				
			}
			try{
				dbConnection = DriverManager.getConnection ("jdbc:oracle:thin:@//oracle.cs.ou.edu:1521/pdborcl.cs.ou.edu"," kadi0000 ", " XWza6Tc8 "); 
				stmt = dbConnection.createStatement(); 
				} 
			catch(Exception e) { 
				System.out.println (e.getMessage()); 
				System.out.println ("Exception occurred in executing the statement");
			} 
		}
		public static String getInput(String prompt) {
			System.out.println(prompt);
			return scan.nextLine();
		}
		public static void main(String[] args){ 
			initdb();
			boolean shouldQuit = false;
			String bigPrompt = "------------------------------------------------------------------------------------------------\n" + 
			                                        "	WELCOME TO THE DATABASE OF GROUP PROJECT2 PROBLEM2\n" + 
					"------------------------------------------------------------------------------------------------\n" +
	                "Please Enter your option(1-4):\n" + 
					"1. Add the data from the PL-SQL into CUSTOMER\n" +
					"2. Update Translator Salaries\n" +
					"3. See all the data from Translatortable and Customer Table\n" +
					"4. Quit\n" +
					"\nPlease take care. The System is CASE-SENSITIVE\n";
			while(!shouldQuit)
				{
					String inp = getInput(bigPrompt);
					int input = -1;
					try {
						input = Integer.parseInt(inp.trim());
					}
					catch(Exception e) {}
						switch(input) {
						case 1:
							option1();
							break;
						case 2:
							option2();
							break;
						case 3:
							option3();
							break;
						case 4:
							shouldQuit = true;
							break;
						default:
							System.out.println("Sorry, Unrecognized input");
							break;
						}
					}
					System.out.println("Thank You for using the Program");
			}
			public static String getDateSQL() {
				String month = getInput("month(mm):");
				String day = getInput("day(dd):");
				String year = getInput("year(yyyy):");
				String dateString = "TO_DATE('" + month +"-"+day+"-"+year+"', MM-DD-YYYY)";
				return dateString;
				
			}
			//select all from Author Table
			public static void option1() {
	            String sCID = getInput("Insert Customer ID");
	            int cid = Integer.parseInt(sCID.trim());
	            String CNAME = getInput("Insert Customer Name");
	            String CLEVEL = getInput("Insert Customer Level");
	            String sql1 = "SELECT * FROM CUSTOMER";
	            try {
					CallableStatement stmt = dbConnection.prepareCall("{call TEST(?,?,?)}");
					((CallableStatement) stmt).setInt(1, cid);
					((CallableStatement) stmt).setString(2, CNAME);
					((CallableStatement) stmt).setString(3, CLEVEL);
					stmt.executeUpdate();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	            try {
					ResultSet rs = stmt.executeQuery(sql1);
					System.out.println("Customer_ID | Customer_Name | Number of orders | Customer_Level");
					while(rs.next()) {
						System.out.println(rs.getString(1) + "|" + rs.getString(2) + "|" + rs.getString(3) + "|" + rs.getString(4));
					}
				}catch(SQLException e) {
					e.printStackTrace();
				}	
			}
			//Select all from Book Table
			public static void option2() {
				String sql1 = "SELECT ANAME FROM AUTHOR";
				String sql2 = "SELECT * FROM Translator";
				try {
					ResultSet rs = stmt.executeQuery(sql2);
					System.out.println("Translator Table before Update");
					System.out.println("Translator_ID|TRANSLATOR_NAME|SALARY");
					while(rs.next()) {
						System.out.println(rs.getString(1) + "|" + rs.getString(2) + "|" + rs.getString(3));
					}
				}catch(SQLException e) {
					e.printStackTrace();
				}
				try {
					ResultSet rs = stmt.executeQuery(sql1);
					System.out.println("The System is CASE-SENSITIVE");
					System.out.println("Author_Name");
					while(rs.next()) {
						System.out.println(rs.getString(1));
					}
				}catch(SQLException e) {
					e.printStackTrace();
				}
				String ANAME = getInput("Insert Author Name from Above");
				try {
					CallableStatement stmt = dbConnection.prepareCall("{call TEST2(?)}");
					((CallableStatement) stmt).setString(1, ANAME);
					stmt.executeUpdate();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					ResultSet rs = stmt.executeQuery(sql2);
					System.out.println("Translator Table AFTER Update");
					System.out.println("Translator_ID|TRANSLATOR_NAME|SALARY");
					while(rs.next()) {
						System.out.println(rs.getString(1) + "|" + rs.getString(2) + "|" + rs.getString(3));
					}
				}catch(SQLException e) {
					e.printStackTrace();
				}		
			}
			//SELECT ALL FROM TRANSLATOR TABLE
			public static void option3() {
				String sql = "SELECT * FROM TRANSLATOR";
				String sql1 = "SELECT * FROM CUSTOMER";
				//Loop through result set and extract results of the query
				try {
					ResultSet rs = stmt.executeQuery(sql1);
					System.out.println("Customer Table");
					System.out.println("Author_ID | Author_Name | Age | Number of Publications");
					while(rs.next()) {
						System.out.println(rs.getString(1) + "|" + rs.getString(2) + "|" + rs.getString(3) + "|" + rs.getString(4));
					}
				}catch(SQLException e) {
					e.printStackTrace();
				}
				try {
					ResultSet rs = stmt.executeQuery(sql);
					System.out.println("Translator Table");
					System.out.println("Translator_ID|TRANSLATOR_NAME|SALARY");
					while(rs.next()) {
						System.out.println(rs.getString(1) + "|" + rs.getString(2) + "|" + rs.getString(3));
					}
				}catch(SQLException e) {
					e.printStackTrace();
				}	
			}
}
