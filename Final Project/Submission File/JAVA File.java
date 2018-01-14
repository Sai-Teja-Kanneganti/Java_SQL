package kanneganti;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class Saiteja {

public static Scanner scan = new Scanner(System.in);
private static Connection dbConnection;
private static Statement stmt;

// to connect to database
public static void initdb() {
	try { 
		Class.forName("oracle.jdbc.OracleDriver"); 
} catch(Exception x){ 
		System.out.println( "Unable to load the driver class!" );
		
	}
	try{
		dbConnection = DriverManager.getConnection ("jdbc:oracle:thin:@//oracle.cs.ou.edu:1521/pdborcl.cs.ou.edu"," kann4040 ", " DGkw3Jp4 "); 
		stmt = dbConnection.createStatement(); 
		} 
	catch(Exception e) { 
		System.out.println (e.getMessage()); 
		System.out.println ("Exception occurred in executing the statement");
	} 
}
// to take user input
public static String getInput(String prompt) {
	System.out.println(prompt);
	return scan.nextLine();
}
// to prompt user input
public static void main(String[] args){ 
	initdb();
	boolean shouldQuit = false;
	String bigPrompt = "------------------------------------------------------------------------------------------------\n" + 
	                                        "	WELCOME TO THE DATABASE OF PAN\n" + 
			"------------------------------------------------------------------------------------------------\n" +
            "Please Enter your option(1-20):\n" + 
			"1. Enter a new team into the database \n" +
			"2. Enter a new client into the database and associate him or her with one or more teams \n" +
			"3. Enter a new volunteer into the database and associate him or her with one or more teams \n" +
			"4. Enter the number of hours a volunteer worked this month for a particular team \n" + 
			"5. Enter a new employee into the database and associate him or her with one or more teams \n" +
			"6. Enter an expense charged by an employee \n" +
			"7. Enter a new organization and associate it to one or more PAN teams \n" +
			"8. Enter a new donor and associate him or her with several donations  \n" +
			"9. Enter a new organization and associate it with several donations \n" +
			"10. Retrieve the name and phone number of the doctor of a particular client  \n" +
			"11. Retrieve the total amount of expenses charged by each employee for a particular period of time.  The list should be sorted by the total amount of expenses  \n" +
			"12. Retrieve the list of volunteers that are members of teams that support a particular client \n" + 
			"13. Retrieve the names and contact information of the clients that are supported by teams sponsored by an organization whose name starts with a letter between B and K.  The client list should be sorted by name  \n" +
			"14. Retrieve the name and total amount donated by donors that are also employees.  The list should be sorted by the total amount of the donations, and indicate if each donor wishes to remain anonymous  \n" +
			"15. For each team, retrieve the name and associated contact information of the volunteer that has worked the most total hours between March and June  \n" +
			"16. Increase the salary by 10% of all employees to whom more than one team must report  \n" +
			"17. Delete all clients who do not have health insurance and whose value of importance for transportation is less than 5  \n" +
			"18. Import: Enter new teams from a data file until the file is empty (the user should be asked to enter the input file name)  \n" +
			"19. Export:  Retrieve names and mailing addresses of all people on the mailing list and output them to a data file instead of screen (the user should be asked to enter the output file name) \n" +
			"20. Quit \n" +
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
					option4();
					break;
				case 5:
					option5();
					break;
				case 6:
					option6();
					break;
				case 7:
					option7();
					break;
				case 8:
					option8();
					break;
				case 9:
					option9();
					break;
				case 10:
					option10();
					break;
				case 11:
					option11();
					break;
				case 12:
					option12();
					break;
				case 13:
					option13();
					break;
				case 14:
					option14();
					break;
				case 15:
					option15();
					break;
				case 16:
					option16();
					break;
				case 17:
					option17();
					break;
				case 18:
					option18();
					break;
				case 19:
					option19();
					break;
				case 20:
					shouldQuit = true;
					break;
				default:
					System.out.println("Sorry, Unrecognized input");
					break;
				}
			}
			System.out.println("Thank You for using the Program");
	}
    // function to take date from the user and convert to date format
	public static String getDateSQL() {
		String month = getInput("month(mm):");
		String day = getInput("day(dd):");
		String year = getInput("year(yyyy):");
		String dateString = "TO_DATE('" + month +"-"+day+"-"+year+"', 'MM-DD-YYYY')";
		return dateString;
		
	}
	
	public static void option1() {
        String T_name = getInput("Enter Team name");
        String T_type = getInput("Enter Team type: Emergency/NonEmergency");
        System.out.println("Enter the date when team was formed");
        String DateFormed = getDateSQL();
        // inserting tuple into Team table
        String sql1 = "Insert into Team values ('"+T_name+"','"+T_type+"',"+DateFormed+")";
        // select team table
        String sql2 = "select * from Team";
        try {
        	stmt.executeQuery(sql1);
			ResultSet rs = stmt.executeQuery(sql2);
			System.out.println("T_name | T_type | DateFormed"); // printing columns in team table
			while(rs.next()) {
				// printing team table
				System.out.println(rs.getString(1) + "|" + rs.getString(2) + "|" + rs.getDate(3).toString());
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}	
	}
	//Select all from Book Table
	public static void option2() {
		// select person table
		String sqlp = "SELECT * FROM Person";
		
		try {
			ResultSet rsp = stmt.executeQuery(sqlp);
			//  Printing column names in person table
			System.out.println("SSN | P_name | BirthDate | Race | Gender | Profession | MailAddress | Email | HomeNumber | WorkNumber | CellNumber | MailingList");
			while(rsp.next()) {
				// printing person table
				System.out.println(rsp.getInt(1) + "|" + rsp.getString(2) + "|" + rsp.getDate(3).toString() + "|" + rsp.getString(4) + "|" + rsp.getString(5) + "|" + rsp.getString(6) + "|" + rsp.getString(7) +"|"+ rsp.getString(8) +"|"+ rsp.getInt(9) +"|"+ rsp.getInt(10)+"|"+ rsp.getInt(11)+ "|" + rsp.getString(12).charAt(0));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		String coptionc = getInput("Enter 1 if client you are entering is a person in database or Enter 2 if client is not in the database ");
		int optionc = Integer.parseInt(coptionc.trim());
		
		// If client is already in database
		if (optionc == 1) {
			String cSSN = getInput("Enter SSN of Client");
			int SSN = Integer.parseInt(cSSN.trim());
			String Doctor_Name = getInput("Enter Doctor Name of Client");
			String cDoctor_Number = getInput("Enter Doctor Number of Client");
			int Doctor_Number = Integer.parseInt(cDoctor_Number.trim());
			String Attorney_Name = getInput("Enter Attorney Name of Client");
			String cAttorney_Number = getInput("Enter Attorney Number of Client");
			int Attorney_Number = Integer.parseInt(cAttorney_Number.trim());
			System.out.println("Enter the date when client was assigned to organization");
	        String Date_Assigned = getDateSQL();
			
	        //To insert tuple into client
			String sql5 = "Insert into Client values ("+SSN+",'"+Doctor_Name+"',"+Doctor_Number+",'"+Attorney_Name+"',"+Attorney_Number+","+Date_Assigned+")"; 
			// selecting client table
			String sql6 = "SELECT * FROM Client";
			try {
	        	stmt.executeQuery(sql5);
				ResultSet rs2 = stmt.executeQuery(sql6);
				// Printing columns in client table
				System.out.println("SSN | Doctor_Name | Doctor_Number | Attorney_Name | Attorney_Number | Date_Assigned");
				while(rs2.next()) {
					// Printing client table
					System.out.println(rs2.getInt(1) + "|" + rs2.getString(2) + "|" + rs2.getInt(3) + "|" + rs2.getString(4) + "|" + rs2.getInt(5) + "|" + rs2.getDate(6).toString());
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
			
			String cNT  = getInput("Enter Number of Teams this client is assosciated with");
			// variable for Number of teams
			int NT = Integer.parseInt(cNT.trim()); 
			// for loop runs NT(No of teams) times
			for(int j=0;j<NT;j= j+1)
			{
				String T_name = getInput("Enter Team name client is associated with:");
				String cActive = getInput("Enter Y if client is Active in this team or else enter N");
				// converting string to character
				char Active = cActive.charAt(0); 
				// Inserting tuple into care table
				String sql7 = "Insert into Care values ("+SSN+",'"+T_name+"','"+Active+"')";
				try {
				    stmt.executeQuery(sql7);
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
			}
			// selecting care table
			String sql8 = "SELECT * FROM Care";
			try {
				// result of execution of sql8 is stored in rs3  
	        	ResultSet rs3 = stmt.executeQuery(sql8);
	        	// print columns in care table
				System.out.println("SSN | T_Name | Active");
				while(rs3.next()) {
					// prints care table 
					System.out.println(rs3.getInt(1) + "|" + rs3.getString(2) + "|" + rs3.getString(3).charAt(0));
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}  
			// variable for Number of needs
			String cNN  = getInput("Enter Number of needs client has");
			// converting string to integer
			int NN = Integer.parseInt(cNN.trim());
			// for loop iterates NN(No of needs) times
			for(int K=0;K<NN;K= K+1)
			{
				String Need = getInput("Enter a Need of client");
				String cN_value = getInput("Enter value associated with this need");
				// converting string to integer
				int N_value = Integer.parseInt(cN_value.trim());
				// insert tuple into Client_Need table
				String sql9 = "Insert into Client_Need values ("+SSN+",'"+Need+"',"+N_value+")";
				try {
				    stmt.executeQuery(sql9);
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
			}
			// select clientNeed table as a string
			String sql10 = "SELECT * FROM Client_Need";
			try {
	        	ResultSet rs4 = stmt.executeQuery(sql10);
	        	// To display columns in ClientNeed
				System.out.println("SSN | Need | Value");
				while(rs4.next()) {
					// To print ClientNeed table
					System.out.println(rs4.getInt(1) + "|" + rs4.getString(2) + "|" + rs4.getInt(3));
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
			// variable for number of insurance policies client has
			String cNI  = getInput("Enter Number of Insurance Policies client has");
			// converting string to integer
			int NI = Integer.parseInt(cNI.trim());
			// for loop iterates NI times (Number of Insurance policies)
			for(int l=0;l<NI;l= l+1)
			{
				String Policy_ID = getInput("Enter a Policy_ID");
				String Provider_ID = getInput("Enter a Provider_ID");
				String ProviderAddress = getInput("Enter Provider Address");
				String I_type = getInput("Enter insurance type");
				// inserting tuple into Insurance
				String sql11 = "Insert into Insurance values ('"+Policy_ID+"',"+SSN+",'"+Provider_ID+"','"+ProviderAddress+"','"+I_type+"')";
				try {
				    stmt.executeQuery(sql11);
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
			}
			// selecting insurance table as string
			String sql12 = "SELECT * FROM Insurance";
			try {
	        	ResultSet rs5 = stmt.executeQuery(sql12);
	        	// to print attributes in insurance table
				System.out.println("Policy_ID | SSN | Provider_ID | ProviderAddress | I_type");
				while(rs5.next()) {
					// to print insurance table
					System.out.println(rs5.getString(1) + "|" + rs5.getInt(2) + "|" + rs5.getString(3) + "|" + rs5.getString(4)+ "|"+ rs5.getString(5));
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}						
		}
		else {
			String cSSN = getInput("Enter SSN of Client");
			int SSN = Integer.parseInt(cSSN.trim());
			String P_name = getInput("Enter name of Client");
			System.out.println("Enter the date of birth of client");
			String BirthDate = getDateSQL();
			String Race = getInput("Enter Race of Client");
			String Gender = getInput("Enter Gender of Client: Male/Female");
			String Profession = getInput("Enter profession of Client");
			String MailAddress = getInput("Enter MailAddress of Client");
			String Email = getInput("Enter Email of Client");
			String cHomeNumber = getInput("Enter HomeNumber of Client");
			int HomeNumber = Integer.parseInt(cHomeNumber.trim());
			String cWorkNumber = getInput("Enter WorkNumber of Client");
			int WorkNumber = Integer.parseInt(cWorkNumber.trim());
			String cCellNumber = getInput("Enter CellNumber of Client");
			int CellNumber = Integer.parseInt(cCellNumber.trim());
			String cMailingList = getInput("Enter Y if client is in mailing list or else enter N");
			char MailingList = cMailingList.charAt(0);
			// insert tuple into person table
			String sql1 = "Insert into Person values ("+SSN+",'"+P_name+"',"+BirthDate+",'"+Race+"','"+Gender+"','"+Profession+"','"+MailAddress+"','"+Email+"',"+HomeNumber+","+WorkNumber+","+CellNumber+",'"+MailingList+"')";
			// selecting person table as string
			String sql2 = "SELECT * FROM Person";
			
			try {
	        	stmt.executeQuery(sql1);
				ResultSet rs = stmt.executeQuery(sql2);
				// print columns in person table
				System.out.println("SSN | P_name | BirthDate | Race | Gender | Profession | MailAddress | Email | HomeNumber | WorkNumber | CellNumber | MailingList");
				while(rs.next()) {
					// printing values of person table on screen
					System.out.println(rs.getInt(1) + "|" + rs.getString(2) + "|" + rs.getDate(3).toString() + "|" + rs.getString(4) + "|" + rs.getString(5) + "|" + rs.getString(6) + "|" + rs.getString(7) +"|"+ rs.getString(8) +"|"+ rs.getInt(9) +"|"+ rs.getInt(10)+"|"+ rs.getInt(11)+ "|" + rs.getString(12).charAt(0));
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
			// variable representing number of emergency contacts
			String cNEC = getInput("Enter Number of Emergency Contacts this person has:");
			// converting string to integer
			int NEC = Integer.parseInt(cNEC.trim());
			// for loop iterates NEC(Number of emergency contacts) times
			for(int j=0;j<NEC;j= j+1)
			{
				String EE_name = getInput("Enter name of Emergency contact");
				String EMailAddress = getInput("Enter MailAddress of Emergency contact");
				String EEmail = getInput("Enter Email of Emergency contact");						
				String cEHomeNumber = getInput("Enter HomeNumber of Emergency contact");
				int EHomeNumber = Integer.parseInt(cEHomeNumber.trim());
				String cEWorkNumber = getInput("Enter WorkNumber of Emergency contact");
				int EWorkNumber = Integer.parseInt(cEWorkNumber.trim());
				String cECellNumber = getInput("Enter CellNumber of Emergency contact");
				int ECellNumber = Integer.parseInt(cECellNumber.trim());
				String ERelation = getInput("Enter Relation of Emergency contact");
				// to insert a tuple in E_Contact table
				String sqlEC1 = "Insert into E_Contact values ("+SSN+",'"+EE_name+"','"+EMailAddress+"','"+EEmail+"',"+EHomeNumber+","+EWorkNumber+","+ECellNumber+",'"+ERelation+"')";
				try {
				    stmt.executeQuery(sqlEC1);
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
			}
			// selecting contents of E_Contact as string
			String sqlEC2 = "SELECT * FROM E_Contact";
			try {
	        	ResultSet rsEC = stmt.executeQuery(sqlEC2);
	        	// displaying columns of E_Contact on screen
				System.out.println("SSN | Emer. Contact Name | Mail Address | Email | Home Number | Work Number | Cell Number | Relation");
				while(rsEC.next()) {
					System.out.println(rsEC.getInt(1) + "|" + rsEC.getString(2) + "|" + rsEC.getString(3) + "|" + rsEC.getString(4) + "|" + rsEC.getInt(5) + "|" + rsEC.getInt(6) + "|" + rsEC.getInt(7) + "|" + rsEC.getString(8) );
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
			
			// taking client values as user input								
			String Doctor_Name = getInput("Enter Doctor Name of Client");
			String cDoctor_Number = getInput("Enter Doctor Number of Client");
			int Doctor_Number = Integer.parseInt(cDoctor_Number.trim());
			String Attorney_Name = getInput("Enter Attorney Name of Client");
			String cAttorney_Number = getInput("Enter Attorney Number of Client");
			int Attorney_Number = Integer.parseInt(cAttorney_Number.trim());
			System.out.println("Enter the date when client was assigned to organization");
	        String Date_Assigned = getDateSQL();
			// insert a tuple into client table
			String sql5 = "Insert into Client values ("+SSN+",'"+Doctor_Name+"',"+Doctor_Number+",'"+Attorney_Name+"',"+Attorney_Number+","+Date_Assigned+")"; 
			// select contents of client table as a string
			String sql6 = "SELECT * FROM Client";
			try {
	        	stmt.executeQuery(sql5);
				ResultSet rs2 = stmt.executeQuery(sql6);
				// to display columns of client table
				System.out.println("SSN | Doctor_Name | Doctor_Number | Attorney_Name | Attorney_Number | Date_Assigned");
				while(rs2.next()) {
					// to display values of client table
					System.out.println(rs2.getInt(1) + "|" + rs2.getString(2) + "|" + rs2.getInt(3) + "|" + rs2.getString(4) + "|" + rs2.getInt(5) + "|" + rs2.getDate(6).toString());
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
			// variable to store number of teams
			String cNT  = getInput("Enter Number of Teams this client is assosciated with");
			// convert string to integer
			int NT = Integer.parseInt(cNT.trim());
			// for loop iterates NT(No of teams) times
			for(int j=0;j<NT;j= j+1)
			{
				String T_name = getInput("Enter Team name client is associated with:");
				String cActive = getInput("Enter Y if client is Active in this team or else enter N");
				char Active = cActive.charAt(0);
				// insert a tuple into Care
				String sql7 = "Insert into Care values ("+SSN+",'"+T_name+"','"+Active+"')";
				try {
				    stmt.executeQuery(sql7);
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
			}
			// to select content of care as string
			String sql8 = "SELECT * FROM Care";
			try {
	        	ResultSet rs3 = stmt.executeQuery(sql8);
	        	// Display columns in care
				System.out.println("SSN | T_Name | Active");
				while(rs3.next()) {
					System.out.println(rs3.getInt(1) + "|" + rs3.getString(2) + "|" + rs3.getString(3).charAt(0));
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}  
			// variable to store number of needs client has
			String cNN  = getInput("Enter Number of needs client has");
			int NN = Integer.parseInt(cNN.trim());
			// for loop iterates NN(No ofneeds) times
			for(int K=0;K<NN;K= K+1)
			{
				String Need = getInput("Enter a Need of client");
				String cN_value = getInput("Enter value associated with this need");
				int N_value = Integer.parseInt(cN_value.trim());
				String sql9 = "Insert into Client_Need values ("+SSN+",'"+Need+"',"+N_value+")";
				try {
				    stmt.executeQuery(sql9);
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
			}
			// select values in Client_Need as string
			String sql10 = "SELECT * FROM Client_Need";
			try {
	        	ResultSet rs4 = stmt.executeQuery(sql10);
	        	// displaying columns in Client_Need table
				System.out.println("SSN | Need | Value");
				while(rs4.next()) {
					System.out.println(rs4.getInt(1) + "|" + rs4.getString(2) + "|" + rs4.getInt(3));
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
			// variable to represent number of insurance policies
			String cNI  = getInput("Enter Number of Insurance Policies client has");
			int NI = Integer.parseInt(cNI.trim());
			// for loop iterates NI times
			for(int l=0;l<NI;l= l+1)
			{
				String Policy_ID = getInput("Enter a Policy_ID");
				String Provider_ID = getInput("Enter a Provider_ID");
				String ProviderAddress = getInput("Enter Provider Address");
				String I_type = getInput("Enter insurance type");
				String sql11 = "Insert into Insurance values ('"+Policy_ID+"',"+SSN+",'"+Provider_ID+"','"+ProviderAddress+"','"+I_type+"')";
				try {
				    stmt.executeQuery(sql11);
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
			}
			// select contents of Insurance as string
			String sql12 = "SELECT * FROM Insurance";
			try {
	        	ResultSet rs5 = stmt.executeQuery(sql12);
	        	// displaying columns in Insurane
				System.out.println("Policy_ID | SSN | Provider_ID | ProviderAddress | I_type");
				while(rs5.next()) {
					System.out.println(rs5.getString(1) + "|" + rs5.getInt(2) + "|" + rs5.getString(3) + "|" + rs5.getString(4)+ "|"+ rs5.getString(5));
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
			
		}							
	}
	
	public static void option3() {
		// select contents of person table as string
        String sqlp = "SELECT * FROM Person";
		
		try {
			ResultSet rsp = stmt.executeQuery(sqlp);
			//display column names in person
			System.out.println("SSN | P_name | BirthDate | Race | Gender | Profession | MailAddress | Email | HomeNumber | WorkNumber | CellNumber | MailingList");
			while(rsp.next()) {
				System.out.println(rsp.getInt(1) + "|" + rsp.getString(2) + "|" + rsp.getDate(3).toString() + "|" + rsp.getString(4) + "|" + rsp.getString(5) + "|" + rsp.getString(6) + "|" + rsp.getString(7) +"|"+ rsp.getString(8) +"|"+ rsp.getInt(9) +"|"+ rsp.getInt(10)+"|"+ rsp.getInt(11)+ "|" + rsp.getString(12).charAt(0));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		String coptionv = getInput("Enter 1 if volunteer you are entering is a person in database or Enter 2 if volunteer is not in the database ");
		int optionv = Integer.parseInt(coptionv.trim());
		// if volunteer is existing in database
		if (optionv == 1) {
			String cSSN = getInput("Enter SSN of volunteer");
			int SSN = Integer.parseInt(cSSN.trim());
			System.out.println("Enter the date when volunteer joined PAN");
			String Date_Joined = getDateSQL();
			System.out.println("Enter the recent training date of volunteer");
			String Recent_Date = getDateSQL();
			String Recent_Location = getInput("Enter Recent training location of volunteer");
			// inserting a tuple in volunteer
			String sql5 = "Insert into Volunteer values ("+SSN+","+Date_Joined+","+Recent_Date+",'"+Recent_Location+"')"; 
			// selecting contents of volunteer as string
			String sql6 = "SELECT * FROM Volunteer";
			
			try {
	        	stmt.executeQuery(sql5);
				ResultSet rs2 = stmt.executeQuery(sql6);
				// To display columns in volunteer table
				System.out.println("SSN | Date_Joined | Recent Training Date | Recent Training Location");
				while(rs2.next()) {
					System.out.println(rs2.getInt(1) + "|" +  rs2.getDate(2).toString() + "|" + rs2.getDate(3).toString() +  "|"+rs2.getString(4));
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}		
			// variable to represent number of teams
			String cNT  = getInput("Enter Number of Teams this volunteer is assosciated with");
			int NT = Integer.parseInt(cNT.trim());
			// for loop iterates NT(Number of teams) times
			for(int j=0;j<NT;j= j+1)
			{
				String T_name = getInput("Enter Team name volunteer is associated with:");
				String cS_month = getInput("Enter Month in integer: 1 for January, 2 for February,..12 for December");
				int S_month = Integer.parseInt(cS_month.trim());
				String cNo_of_hours = getInput("Enter Number of hours volunteer worked");
				int No_of_hours = Integer.parseInt(cNo_of_hours.trim());
				String cActive = getInput("Enter Y if volunteer is Active in this team or else enter N");
				char Active = cActive.charAt(0);
				// insert a tuple into serve table
				String sql7 = "Insert into Serve values ("+SSN+",'"+T_name+"',"+S_month+","+No_of_hours+",'"+Active+"')";
				try {
				    stmt.executeQuery(sql7);
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
			}
			// to select contents of serve table							
			String sql8 = "SELECT * FROM Serve";		
			try {
	        	ResultSet rs3 = stmt.executeQuery(sql8);
	        	// to display columns in serve table
				System.out.println("SSN | T_Name | Month | No of hours | Active");
				while(rs3.next()) {
					System.out.println(rs3.getInt(1) + "|" + rs3.getString(2) + "|" + rs3.getInt(3) + "|" +rs3.getInt(4)+ "|" + rs3.getString(5).charAt(0));
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
			
		}
		// if volunteer you are entering is not in database
		else {
			String cSSN = getInput("Enter SSN of volunteer");
			int SSN = Integer.parseInt(cSSN.trim());
			String P_name = getInput("Enter name of volunteer");
			System.out.println("Enter the date of birth of volunteer");
			String BirthDate = getDateSQL();
			String Race = getInput("Enter Race of volunteer");
			String Gender = getInput("Enter Gender of volunteer: Male/Female");
			String Profession = getInput("Enter profession of volunteer");
			String MailAddress = getInput("Enter MailAddress of volunteer");
			String Email = getInput("Enter Email of volunteer");
			String cHomeNumber = getInput("Enter HomeNumber of volunteer");
			int HomeNumber = Integer.parseInt(cHomeNumber.trim());
			String cWorkNumber = getInput("Enter WorkNumber of volunteer");
			int WorkNumber = Integer.parseInt(cWorkNumber.trim());
			String cCellNumber = getInput("Enter CellNumber of volunteer");
			int CellNumber = Integer.parseInt(cCellNumber.trim());
			String cMailingList = getInput("Enter Y if volunteer is in mailing list or else enter N");
			char MailingList = cMailingList.charAt(0);
			// insert a tuple into person
			String sql1 = "Insert into Person values ("+SSN+",'"+P_name+"',"+BirthDate+",'"+Race+"','"+Gender+"','"+Profession+"','"+MailAddress+"','"+Email+"',"+HomeNumber+","+WorkNumber+","+CellNumber+",'"+MailingList+"')";
			String sql2 = "SELECT * FROM Person";
			
			try {
	        	stmt.executeQuery(sql1);
				ResultSet rs = stmt.executeQuery(sql2);
				// to print columns in person table
				System.out.println("SSN | P_name | BirthDate | Race | Gender | Profession | MailAddress | Email | HomeNumber | WorkNumber | CellNumber | MailingList");
				while(rs.next()) {
					// to display content/values in person table
					System.out.println(rs.getInt(1) + "|" + rs.getString(2) + "|" + rs.getDate(3).toString() + "|" + rs.getString(4) + "|" + rs.getString(5) + "|" + rs.getString(6) + "|" + rs.getString(7) +"|"+ rs.getString(8) +"|"+ rs.getInt(9) +"|"+ rs.getInt(10)+"|"+ rs.getInt(11)+ "|" + rs.getString(12).charAt(0));
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
			// variable that represents number of emergency contacts
			String cNEC = getInput("Enter Number of Emergency Contacts this person has:");
			int NEC = Integer.parseInt(cNEC.trim());
			// for loop iterates NEC(Number of Emergency contacts) times
			for(int j=0;j<NEC;j= j+1)
			{
				String EE_name = getInput("Enter name of Emergency contact");
				String EMailAddress = getInput("Enter MailAddress of Emergency contact");
				String EEmail = getInput("Enter Email of Emergency contact");						
				String cEHomeNumber = getInput("Enter HomeNumber of Emergency contact");
				int EHomeNumber = Integer.parseInt(cEHomeNumber.trim());
				String cEWorkNumber = getInput("Enter WorkNumber of Emergency contact");
				int EWorkNumber = Integer.parseInt(cEWorkNumber.trim());
				String cECellNumber = getInput("Enter CellNumber of Emergency contact");
				int ECellNumber = Integer.parseInt(cECellNumber.trim());
				String ERelation = getInput("Enter Relation of Emergency contact");
				// inserting a tuple into emergency contacts
				String sqlEC1 = "Insert into E_Contact values ("+SSN+",'"+EE_name+"','"+EMailAddress+"','"+EEmail+"',"+EHomeNumber+","+EWorkNumber+","+ECellNumber+",'"+ERelation+"')";
				try {
				    stmt.executeQuery(sqlEC1);
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
			}
			// select content/values from E_Contact table 
			String sqlEC2 = "SELECT * FROM E_Contact";
			try {
	        	ResultSet rsEC = stmt.executeQuery(sqlEC2);
	        	// to display columns of person
				System.out.println("SSN | Emer. Contact Name | Mail Address | Email | Home Number | Work Number | Cell Number | Relation");
				while(rsEC.next()) {
					System.out.println(rsEC.getInt(1) + "|" + rsEC.getString(2) + "|" + rsEC.getString(3) + "|" + rsEC.getString(4) + "|" + rsEC.getInt(5) + "|" + rsEC.getInt(6) + "|" + rsEC.getInt(7) + "|" + rsEC.getString(8) );
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
			
			
			System.out.println("Enter the date when volunteer joined PAN");
			// getDateSQL() function is called to take date as user input and convert to SQL dataframe
			String Date_Joined = getDateSQL();
			System.out.println("Enter the recent training date of volunteer");
			String Recent_Date = getDateSQL();
			String Recent_Location = getInput("Enter Recent training location of volunteer");
			// insert a tuple into volunteer table
			String sql5 = "Insert into Volunteer values ("+SSN+","+Date_Joined+","+Recent_Date+",'"+Recent_Location+"')"; 
			// select content/values of volunteer
			String sql6 = "SELECT * FROM Volunteer";
			
			try {
	        	stmt.executeQuery(sql5);
				ResultSet rs2 = stmt.executeQuery(sql6);
				System.out.println("SSN | Date_Joined | Recent Training Date | Recent Training Location");
				while(rs2.next()) {
					System.out.println(rs2.getInt(1) + "|" +  rs2.getDate(2).toString() + "|" + rs2.getDate(3).toString() +  "|"+rs2.getString(4));
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}		
			// variable that represents number of teams
			String cNT  = getInput("Enter Number of Teams this volunteer is assosciated with");
			int NT = Integer.parseInt(cNT.trim());
			// for loop iterates NT(Number of team) times
			for(int j=0;j<NT;j= j+1)
			{
				String T_name = getInput("Enter Team name volunteer is associated with:");
				String cS_month = getInput("Enter Month as integer: 1 for January, 2 for February,...12 for December");
				int S_month = Integer.parseInt(cS_month.trim());
				String cNo_of_hours = getInput("Enter Number of hours volunteer worked");
				int No_of_hours = Integer.parseInt(cNo_of_hours.trim());
				String cActive = getInput("Enter Y if volunteer is Active in this team or else enter N");
				char Active = cActive.charAt(0);
				// inserting a tuple into serve table
				String sql7 = "Insert into Serve values ("+SSN+",'"+T_name+"',"+S_month+","+No_of_hours+",'"+Active+"')";
				try {
				    stmt.executeQuery(sql7);
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
			}
			// select content/values of serve as string							
			String sql8 = "SELECT * FROM Serve";		
			try {
	        	ResultSet rs3 = stmt.executeQuery(sql8);
	        	// to display columns in serve table
				System.out.println("SSN | T_Name | Month | No of hours | Active");
				while(rs3.next()) {
					// to print values of serve on screen
					System.out.println(rs3.getInt(1) + "|" + rs3.getString(2) + "|" + rs3.getInt(3) + "|" +rs3.getInt(4)+ "|" + rs3.getString(5).charAt(0));
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}						
		}																															
	}
	
	public static void option4() {
		String cSSN = getInput("Enter SSN of volunteer");
		int SSN = Integer.parseInt(cSSN.trim());
		// variable representing number of teams associated with volunteers
		String cNT  = getInput("Enter Number of Teams this volunteer is assosciated with");
		int NT = Integer.parseInt(cNT.trim());
		// for loop iterates NT(No of teams) times
		for(int j=0;j<NT;j= j+1)
		{
			String T_name = getInput("Enter Team name volunteer is associated with:");
			// Taking month as an integer
			String cS_month = getInput("Enter Month as integer: 1 for January, 2 for February,...,12 for December");
			int S_month = Integer.parseInt(cS_month.trim());
			String cNo_of_hours = getInput("Enter Number of hours volunteer worked");
			// converting string to integer
			int No_of_hours = Integer.parseInt(cNo_of_hours.trim());
			String cActive = getInput("Enter Y if volunteer is Active in this team or else enter N");
			// converting string to character
			char Active = cActive.charAt(0);
			// insert a tuple into serve table
			String sql1 = "Insert into Serve values ("+SSN+",'"+T_name+"',"+S_month+","+No_of_hours+",'"+Active+"')";
			System.out.println(sql1);
			try {
			    stmt.executeQuery(sql1);
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
		// select content/ values of serve as string							
		String sql2 = "SELECT * FROM Serve";		
		try {
        	ResultSet rs3 = stmt.executeQuery(sql2);
        	// display columns in serve table
			System.out.println("SSN | T_Name | Month | No of hours | Active");
			while(rs3.next()) {
				// to display values of serve table
				System.out.println(rs3.getInt(1) + "|" + rs3.getString(2) + "|" + rs3.getInt(3) + "|" +rs3.getInt(4)+ "|" + rs3.getString(5).charAt(0));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}		
	}
	
	
	public static void option5() {
        String sqlp = "SELECT * FROM Person";
		
		try {
			ResultSet rsp = stmt.executeQuery(sqlp);
			// display columns in person table 
			System.out.println("SSN | P_name | BirthDate | Race | Gender | Profession | MailAddress | Email | HomeNumber | WorkNumber | CellNumber | MailingList");
			while(rsp.next()) {
				System.out.println(rsp.getInt(1) + "|" + rsp.getString(2) + "|" + rsp.getDate(3).toString() + "|" + rsp.getString(4) + "|" + rsp.getString(5) + "|" + rsp.getString(6) + "|" + rsp.getString(7) +"|"+ rsp.getString(8) +"|"+ rsp.getInt(9) +"|"+ rsp.getInt(10)+"|"+ rsp.getInt(11)+ "|" + rsp.getString(12).charAt(0));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		String coptione = getInput("Enter 1 if Employee you are entering is a person in database or Enter 2 if Employee is not in the database ");
		int optione = Integer.parseInt(coptione.trim());
		// If employee is existing in database
		if (optione == 1) {
			String cSSN = getInput("Enter SSN of Employee");
			int SSN = Integer.parseInt(cSSN.trim());
			String cSalary = getInput("Enter Salary of Employee");
			int Salary = Integer.parseInt(cSalary.trim());
			String Marital_Status = getInput("Enter Marital status of employee (single/married/separated/divorced/widowed)");
			System.out.println("Enter the Hire date of employee");
			String HireDate = getDateSQL();		
			// insert a tuple into employee table
			String sql5 = "Insert into Employee values ("+SSN+","+Salary+",'"+Marital_Status+"',"+HireDate+")"; 
			String sql6 = "SELECT * FROM Employee";
			
			try {
	        	stmt.executeQuery(sql5);
				ResultSet rs2 = stmt.executeQuery(sql6);
				// display columns in employee table
				System.out.println("SSN | Salary | Marital_Status | HireDate");
				while(rs2.next()) {
					System.out.println(rs2.getInt(1) + "|" +  rs2.getInt(2) + "|" + rs2.getString(3) +  "|"+rs2.getDate(4).toString());
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}		
			// variable to represent number of teams employee is associated with
			String cNT  = getInput("Enter Number of Teams this employee is assosciated with");
			int NT = Integer.parseInt(cNT.trim());
			// for loop iterates NT(Number of teams) times
			for(int j=0;j<NT;j= j+1)
			{
				String T_name = getInput("Enter Team name Employee is associated with:");			
				System.out.println("Enter the date when team reported");
				String R_date = getDateSQL();
				String R_description = getInput("Enter description of Report");
				// insert a tuple in Report table
				String sql7 = "Insert into Report values ('"+T_name+"',"+SSN+","+R_date+",'"+R_description+"')";
				try {
				    stmt.executeQuery(sql7);
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
			}
										
			String sql8 = "SELECT * FROM Report";		
			try {
	        	ResultSet rs3 = stmt.executeQuery(sql8);
	        	// display columns in report table
				System.out.println("Team Name | SSN | Report Date | Report Description");
				while(rs3.next()) {
					System.out.println(rs3.getString(1) + "|" + rs3.getInt(2) + "|" + rs3.getDate(3).toString() + "|" +rs3.getString(4));
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
									
		}
		// If employee is new is database 
		else {
			String cSSN = getInput("Enter SSN of Employee");
			int SSN = Integer.parseInt(cSSN.trim());
			String P_name = getInput("Enter name of Employee");
			System.out.println("Enter the date of birth of Employee");
			String BirthDate = getDateSQL();
			String Race = getInput("Enter Race of Employee");
			String Gender = getInput("Enter Gender of Employee: Male/Female");
			String Profession = getInput("Enter profession of Employee");
			String MailAddress = getInput("Enter MailAddress of Employee");
			String Email = getInput("Enter Email of Employee");
			String cHomeNumber = getInput("Enter HomeNumber of Employee");
			int HomeNumber = Integer.parseInt(cHomeNumber.trim());
			String cWorkNumber = getInput("Enter WorkNumber of Employee");
			int WorkNumber = Integer.parseInt(cWorkNumber.trim());
			String cCellNumber = getInput("Enter CellNumber of Employee");
			int CellNumber = Integer.parseInt(cCellNumber.trim());
			String cMailingList = getInput("Enter Y if Employee is in mailing list or else enter N");
			char MailingList = cMailingList.charAt(0);
			// insert a tuple into person table
			String sql1 = "Insert into Person values ("+SSN+",'"+P_name+"',"+BirthDate+",'"+Race+"','"+Gender+"','"+Profession+"','"+MailAddress+"','"+Email+"',"+HomeNumber+","+WorkNumber+","+CellNumber+",'"+MailingList+"')";
			String sql2 = "SELECT * FROM Person";
			
			try {
	        	stmt.executeQuery(sql1);
				ResultSet rs = stmt.executeQuery(sql2);
				System.out.println("SSN | P_name | BirthDate | Race | Gender | Profession | MailAddress | Email | HomeNumber | WorkNumber | CellNumber | MailingList");
				while(rs.next()) {
					System.out.println(rs.getInt(1) + "|" + rs.getString(2) + "|" + rs.getDate(3).toString() + "|" + rs.getString(4) + "|" + rs.getString(5) + "|" + rs.getString(6) + "|" + rs.getString(7) +"|"+ rs.getString(8) +"|"+ rs.getInt(9) +"|"+ rs.getInt(10)+"|"+ rs.getInt(11)+ "|" + rs.getString(12).charAt(0));
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
			// variable to represent number of emergency contacts
			String cNEC = getInput("Enter Number of Emergency Contacts this person has:");
			int NEC = Integer.parseInt(cNEC.trim());
			// for loop iterates NEC(Number of Emergency Contacts) times
			for(int j=0;j<NEC;j= j+1)
			{
				String EE_name = getInput("Enter name of Emergency contact");
				String EMailAddress = getInput("Enter MailAddress of Emergency contact");
				String EEmail = getInput("Enter Email of Emergency contact");						
				String cEHomeNumber = getInput("Enter HomeNumber of Emergency contact");
				int EHomeNumber = Integer.parseInt(cEHomeNumber.trim());
				String cEWorkNumber = getInput("Enter WorkNumber of Emergency contact");
				int EWorkNumber = Integer.parseInt(cEWorkNumber.trim());
				String cECellNumber = getInput("Enter CellNumber of Emergency contact");
				int ECellNumber = Integer.parseInt(cECellNumber.trim());
				String ERelation = getInput("Enter Relation of Emergency contact");
				// insert a tuple into E_Contact table
				String sqlEC1 = "Insert into E_Contact values ("+SSN+",'"+EE_name+"','"+EMailAddress+"','"+EEmail+"',"+EHomeNumber+","+EWorkNumber+","+ECellNumber+",'"+ERelation+"')";
				try {
				    stmt.executeQuery(sqlEC1);
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
			}
			// Select values/content of E_Contact
			String sqlEC2 = "SELECT * FROM E_Contact";
			try {
	        	ResultSet rsEC = stmt.executeQuery(sqlEC2);
	        	// prints columns in E_Contact
				System.out.println("SSN | Emer. Contact Name | Mail Address | Email | Home Number | Work Number | Cell Number | Relation");
				while(rsEC.next()) {
					System.out.println(rsEC.getInt(1) + "|" + rsEC.getString(2) + "|" + rsEC.getString(3) + "|" + rsEC.getString(4) + "|" + rsEC.getInt(5) + "|" + rsEC.getInt(6) + "|" + rsEC.getInt(7) + "|" + rsEC.getString(8) );
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
			
			// Taking salary as string
			String cSalary = getInput("Enter Salary of Employee");
			// converting string to integer
			int Salary = Integer.parseInt(cSalary.trim());
			String Marital_Status = getInput("Enter Marital status of employee (single/married/separated/divorced/widowed)");
			System.out.println("Enter the Hire date of employee");
			String HireDate = getDateSQL();		
			// inserting a tuple into Employees
			String sql5 = "Insert into Employee values ("+SSN+","+Salary+",'"+Marital_Status+"',"+HireDate+")"; 
			// select content/values of employee as string
			String sql6 = "SELECT * FROM Employee";
			
			try {
	        	stmt.executeQuery(sql5);
				ResultSet rs2 = stmt.executeQuery(sql6);
				System.out.println("SSN | Salary | Marital_Status | HireDate");
				while(rs2.next()) {
					System.out.println(rs2.getInt(1) + "|" +  rs2.getInt(2) + "|" + rs2.getString(3) +  "|"+rs2.getDate(4).toString());
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}		
			// variable representing number of teams employee is associated with
			String cNT  = getInput("Enter Number of Teams this employee is assosciated with");
			int NT = Integer.parseInt(cNT.trim());
			// for loop iterates NT(Number of Teams) times
			for(int j=0;j<NT;j= j+1)
			{
				String T_name = getInput("Enter Team name Employee is associated with:");			
				System.out.println("Enter the date when team reported");
				String R_date = getDateSQL();
				String R_description = getInput("Enter description of Report");
				// insert a tuple into report table
				String sql7 = "Insert into Report values ('"+T_name+"',"+SSN+","+R_date+",'"+R_description+"')";
				try {
				    stmt.executeQuery(sql7);
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
			}
			// select content/values in report tables							
			String sql8 = "SELECT * FROM Report";		
			try {
	        	ResultSet rs3 = stmt.executeQuery(sql8);
				System.out.println("Team Name | SSN | Report Date | Report Description");
				while(rs3.next()) {
					System.out.println(rs3.getString(1) + "|" + rs3.getInt(2) + "|" + rs3.getDate(3).toString() + "|" +rs3.getString(4));
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}		
		}
				
	}
	
	
	
	public static void option6() {
		String cSSN = getInput("Enter SSN of Employee whose expense is to be stored");
		int SSN = Integer.parseInt(cSSN.trim());
		// variable representing number of expenses associated with employee
		String cNE  = getInput("Enter Number of Expenses to be stored associated with this employee");
		int NE = Integer.parseInt(cNE.trim());
		// for loop iterates NE(Number of expenses) times
		for(int j=0;j<NE;j= j+1)
		{
			System.out.println("Enter Expense date");
			String E_date = getDateSQL();			
			String cAmount = getInput("Enter Expense Amount: (integer value)");
			int Amount = Integer.parseInt(cAmount.trim());
			String E_description = getInput("Enter Expense description");
			// insert a tuple into expense
			String sql1 = "Insert into Expenses values ("+SSN+","+E_date+","+Amount+",'"+E_description+"')";
			try {
			    stmt.executeQuery(sql1);
			}
			catch(SQLException e) {
				e.printStackTrace();
			}
		}
		// select content/values from expense table as string							
		String sql2 = "SELECT * FROM Expenses";		
		try {
        	ResultSet rs3 = stmt.executeQuery(sql2);
        	// display columns in expense table
			System.out.println("SSN | Exp date | Amount | Exp description");
			while(rs3.next()) {
				System.out.println(rs3.getInt(1) + "|" + rs3.getDate(2).toString() + "|" + rs3.getInt(3) + "|" +rs3.getString(4));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}		
	}
	
	
	
	public static void option7() {
		String sqlO = "SELECT * FROM Ext_Organization";
		try {
			ResultSet rs = stmt.executeQuery(sqlO);
			System.out.println("Org.Name | Mailing Address | Contact Person | Phone Number | Anonymous");
			while(rs.next()) {
				System.out.println(rs.getString(1) + "|" + rs.getString(2) + "|" + rs.getString(3) + "|" + rs.getInt(4) +  "|" + rs.getString(5).charAt(0));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		String coptionOE = getInput("Enter 1 if Organization you are entering is in database or Enter 2 if Organization is not in the database ");
		int optionOE = Integer.parseInt(coptionOE.trim());
		// If external organization is in database
		if (optionOE == 1) {
			String O_name = getInput("Enter Organization name");
			// variable representing number of teams organization sponsor
			String cNT  = getInput("Enter Number of Teams this organization is associated with:");
			// for loop iterates NT(Number of teams times) 
			int NT = Integer.parseInt(cNT.trim());
			for(int i=0;i<NT;i= i+1)
			{
				String T_name = getInput("Enter Team name");
				// insert a tuple into sponsor table
				String sql3 = "Insert into Sponsor values ('"+O_name+"','"+T_name+"')";
				// select content/values from sponsor table
				String sql4 = "SELECT * FROM Sponsor";
				
				try {
	            	stmt.executeQuery(sql3);
					ResultSet rs1 = stmt.executeQuery(sql4);
					System.out.println("Org. Name | Team Name");
					while(rs1.next()) {
						System.out.println(rs1.getString(1) + "|" + rs1.getString(2));
					}
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}						
		}
		// If external organization is not in database
		else {
			String O_name = getInput("Enter Organization name");
			String Mailing_Address = getInput("Enter Mailing Address of Organization");
			String ContactPerson = getInput("Enter name of Contact person");
			String cPhoneNumber = getInput("Enter PhoneNumber of Organization");
			int PhoneNumber = Integer.parseInt(cPhoneNumber.trim());
			String cAnonymous = getInput("Enter Y if organization makes Anonymous donations or else enter N");
			char Anonymous = cAnonymous.charAt(0);
			// insert a tuple into Ext_Organization table
			String sql1 = "Insert into Ext_Organization values ('"+O_name+"','"+Mailing_Address+"','"+ContactPerson+"',"+PhoneNumber+",'"+Anonymous+"')";
			String sql2 = "SELECT * FROM Ext_Organization";
			
			try {
	        	stmt.executeQuery(sql1);
				ResultSet rs = stmt.executeQuery(sql2);
				// Listing columns in Ext.Organization table
				System.out.println("Org.Name | Mailing Address | Contact Person | Phone Number | Anonymous");
				while(rs.next()) {
					System.out.println(rs.getString(1) + "|" + rs.getString(2) + "|" + rs.getString(3) + "|" + rs.getInt(4) +  "|" + rs.getString(5).charAt(0));
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
			// variable to determine type of organization
			String cOrgType = getInput("Enter Organization Type: 1 for Business, 2 for Church");
			int OrgType = Integer.parseInt(cOrgType.trim());
			// If organization is Business
			if (OrgType == 1) {
				String B_type = getInput("Enter Business Type");
				String cB_size = getInput("Enter size of company");
				int B_size = Integer.parseInt(cB_size.trim());
				String Company_website = getInput("Enter company website");
				
				// Insert a tuple into Business table
				String sql5 = "Insert into Business values ('"+O_name+"','"+B_type+"',"+B_size+",'"+Company_website+"')"; 
				// Select content/values from business table
				String sql6 = "SELECT * FROM Business";
				try {
		        	stmt.executeQuery(sql5);
					ResultSet rs2 = stmt.executeQuery(sql6);
					// display columns in Business table
					System.out.println("Org. Name | Buss. type | Buss. size | Company website");
					while(rs2.next()) {
						System.out.println(rs2.getString(1) + "|" + rs2.getString(2) + "|" + rs2.getInt(3) + "|" + rs2.getString(4));
					}
				}catch(SQLException e) {
					e.printStackTrace();
				}						
			}
			// If external Organization is Church
			else {
				String Religious_Affiliation = getInput("Enter Religious Affiliation of Church");
				// Insert a tuple in church table			
				String sql7 = "Insert into Church values ('"+O_name+"','"+Religious_Affiliation+"')"; 
				// select content/values of Church table
				String sql8 = "SELECT * FROM Church";
				try {
		        	stmt.executeQuery(sql7);
					ResultSet rs3 = stmt.executeQuery(sql8);
					// to display columns in church table
					System.out.println("Org. Name | Religious Affiliation");
					while(rs3.next()) {
						System.out.println(rs3.getString(1) + "|" + rs3.getString(2));
					}
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}	
			
			// variable to represent number of teams organization is associated with
			String cNT  = getInput("Enter Number of Teams this organization is associated with:");
			// converting string to integer
			int NT = Integer.parseInt(cNT.trim());
			// for loop iterates NT(Number of Teams) times
			for(int i=0;i<NT;i= i+1)
			{
				String T_name = getInput("Enter Team name");
				// insert a tuple into sponsor table
				String sql3 = "Insert into Sponsor values ('"+O_name+"','"+T_name+"')";
				// select content/values of sponsor table
				String sql4 = "SELECT * FROM Sponsor";
				
				try {
	            	stmt.executeQuery(sql3);// to execute sql3 querry
					ResultSet rs1 = stmt.executeQuery(sql4);
					// displaying column names in sponsor table
					System.out.println("Org. Name | Team Name");
					while(rs1.next()) {
						// to display contents of sponsor table
						System.out.println(rs1.getString(1) + "|" + rs1.getString(2));
					}
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}	
			
		}									
	}
		
	public static void option8() {
        String sqlp = "SELECT * FROM Person";
		
		try {
			ResultSet rsp = stmt.executeQuery(sqlp);
			// to display coulumns in person table
			System.out.println("SSN | P_name | BirthDate | Race | Gender | Profession | MailAddress | Email | HomeNumber | WorkNumber | CellNumber | MailingList");
			while(rsp.next()) {
				System.out.println(rsp.getInt(1) + "|" + rsp.getString(2) + "|" + rsp.getDate(3).toString() + "|" + rsp.getString(4) + "|" + rsp.getString(5) + "|" + rsp.getString(6) + "|" + rsp.getString(7) +"|"+ rsp.getString(8) +"|"+ rsp.getInt(9) +"|"+ rsp.getInt(10)+"|"+ rsp.getInt(11)+ "|" + rsp.getString(12).charAt(0));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		String coptiond = getInput("Enter 1 if Donor you are entering is a person in database or Enter 2 if Donor is not in the database ");
		int optiond = Integer.parseInt(coptiond.trim());
		// if donor is existing in database
		if (optiond == 1) {
			String cSSN = getInput("Enter SSN of Donor");
			int SSN = Integer.parseInt(cSSN.trim());
			String cAnonymous = getInput("Enter Y if Donor makes Anonymous donations or else enter N");
			char Anonymous = cAnonymous.charAt(0);
			// Insert a tuple into donor
			String sql5 = "Insert into Donor values ("+SSN+",'"+Anonymous+"')";
			String sql6 = "SELECT * FROM Donor";
			
			try {
	        	stmt.executeQuery(sql5);
				ResultSet rs2 = stmt.executeQuery(sql6);
				// Displays columns in Donor
				System.out.println("Donor SSN | Anonymous");
				while(rs2.next()) {
					System.out.println(rs2.getInt(1) + "|" + rs2.getString(2).charAt(0));
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
			// variable that represents number of donations made by donor
			String cND  = getInput("Enter Number of Donations made by this Donor");
			// convert string to integer
			int ND = Integer.parseInt(cND.trim());
			// for loop iterates ND(Number of donations)
			for(int i=0;i<ND;i= i+1)
			{
				System.out.println("Enter date of donation");
				String D_date = getDateSQL();
				String cAmount = getInput("Enter Amount Donated by Donor");
				int Amount = Integer.parseInt(cAmount.trim());
				String D_type = getInput("Enter Donation type");
				String Campaign = getInput("Enter name of Fund Raising Campaign");
				// inserting a tuple into donations table
				String sql7 = "Insert into Donations values ("+SSN+","+D_date+","+Amount+",'"+D_type+"','"+Campaign+"')";
				// select content/values from donations table
				String sql8 = "SELECT * FROM Donations";
				
				try {
	            	stmt.executeQuery(sql7);
					ResultSet rs3 = stmt.executeQuery(sql8);
					// displays columns in Donations table
					System.out.println("SSN | Donation Date | Amount | Donation Type | Campaign");
					while(rs3.next()) {
						// displays values/content in Donations table
						System.out.println(rs3.getInt(1) + "|" + rs3.getDate(2).toString()  + "|" + rs3.getInt(3) + "|" + rs3.getString(4) + "|" + rs3.getString(5) );
					}
				}catch(SQLException e) {
					e.printStackTrace();
				}
				
				// variable to determine mode of payment
				String cD_mode = getInput("Enter mode of payment: 1 for card , 2 for check");
				int D_mode = Integer.parseInt(cD_mode.trim());
				// If payment is made by card
				if (D_mode == 1) {					
					String Card_No = getInput("Enter card number");
					String Card_Type = getInput("Enter card Type");
					System.out.println("Enter Expiry date on card");
					String Exp_Date = getDateSQL();
					// Insert a tuple into D_Card table
					String sqlc1 = "Insert into D_Card values ("+SSN+","+D_date+","+Amount+",'"+D_type+"','"+Card_No+"','"+Card_Type+"',"+Exp_Date+")";
					String sqlc2 = "SELECT * FROM D_Card";
					try {
		            	stmt.executeQuery(sqlc1);
						ResultSet rsca = stmt.executeQuery(sqlc2);
						// display columns in D_Card table
						System.out.println("SSN | Donation Date | Amount | Donation Type | Card No | Card Type | Exp.Date");
						while(rsca.next()) {
							System.out.println(rsca.getInt(1) + "|" + rsca.getDate(2).toString()  + "|" + rsca.getInt(3) + "|" + rsca.getString(4) + "|" + rsca.getString(5) + "|" + rsca.getString(6) + "|" + rsca.getDate(7).toString());
						}
					}catch(SQLException e) {
						e.printStackTrace();
					}
				} 
				// If mode of payment is by check
				else {
					String Check_No = getInput("Enter check number");
					// Insert a tuple into D_Check
					String sqlch1 = "Insert into D_Check values ("+SSN+","+D_date+","+Amount+",'"+D_type+"','"+Check_No+"')";
					// select content/values of D_Check
					String sqlch2 = "SELECT * FROM D_Check";
					try {
		            	stmt.executeQuery(sqlch1);
						ResultSet rsch = stmt.executeQuery(sqlch2);
						// Display columns in D_Check table
						System.out.println("SSN | Donation Date | Amount | Donation Type | Check No ");
						while(rsch.next()) {
							System.out.println(rsch.getInt(1) + "|" + rsch.getDate(2).toString()  + "|" + rsch.getInt(3) + "|" + rsch.getString(4) + "|" + rsch.getString(5));
						}
					}catch(SQLException e) {
						e.printStackTrace();
					}
				}		
																
																
			}									
		}
		// If donor is not existing in database
		else {
			String cSSN = getInput("Enter SSN of Donor");
			int SSN = Integer.parseInt(cSSN.trim());
			String P_name = getInput("Enter name of Donor");
			System.out.println("Enter the date of birth of Donor");
			String BirthDate = getDateSQL();
			String Race = getInput("Enter Race of Donor");
			String Gender = getInput("Enter Gender of Donor: Male/Female");
			String Profession = getInput("Enter profession of Donor");
			String MailAddress = getInput("Enter MailAddress of Donor");
			String Email = getInput("Enter Email of Donor");
			String cHomeNumber = getInput("Enter HomeNumber of Donor");
			int HomeNumber = Integer.parseInt(cHomeNumber.trim());
			String cWorkNumber = getInput("Enter WorkNumber of Donor");
			int WorkNumber = Integer.parseInt(cWorkNumber.trim());
			String cCellNumber = getInput("Enter CellNumber of Donor");
			int CellNumber = Integer.parseInt(cCellNumber.trim());
			String cMailingList = getInput("Enter Y if Donor is in mailing list or else enter N");
			char MailingList = cMailingList.charAt(0);
			// Insert a tuple into person table
			String sql1 = "Insert into Person values ("+SSN+",'"+P_name+"',"+BirthDate+",'"+Race+"','"+Gender+"','"+Profession+"','"+MailAddress+"','"+Email+"',"+HomeNumber+","+WorkNumber+","+CellNumber+",'"+MailingList+"')";
			String sql2 = "SELECT * FROM Person";
			
			try {
	        	stmt.executeQuery(sql1);
				ResultSet rs = stmt.executeQuery(sql2);
				// display columns in person table
				System.out.println("SSN | P_name | BirthDate | Race | Gender | Profession | MailAddress | Email | HomeNumber | WorkNumber | CellNumber | MailingList");
				while(rs.next()) {
					// display content/values in person table
					System.out.println(rs.getInt(1) + "|" + rs.getString(2) + "|" + rs.getDate(3).toString() + "|" + rs.getString(4) + "|" + rs.getString(5) + "|" + rs.getString(6) + "|" + rs.getString(7) +"|"+ rs.getString(8) +"|"+ rs.getInt(9) +"|"+ rs.getInt(10)+"|"+ rs.getInt(11)+ "|" + rs.getString(12).charAt(0));
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
			// variable representing Number of emergency contacts
			String cNEC = getInput("Enter Number of Emergency Contacts this person has:");
			int NEC = Integer.parseInt(cNEC.trim());
			// nor loop iterates NEC(Number of Emergency contact) times
			for(int j=0;j<NEC;j= j+1)
			{
				String EE_name = getInput("Enter name of Emergency contact");
				String EMailAddress = getInput("Enter MailAddress of Emergency contact");
				String EEmail = getInput("Enter Email of Emergency contact");						
				String cEHomeNumber = getInput("Enter HomeNumber of Emergency contact");
				int EHomeNumber = Integer.parseInt(cEHomeNumber.trim());
				String cEWorkNumber = getInput("Enter WorkNumber of Emergency contact");
				int EWorkNumber = Integer.parseInt(cEWorkNumber.trim());
				String cECellNumber = getInput("Enter CellNumber of Emergency contact");
				int ECellNumber = Integer.parseInt(cECellNumber.trim());
				String ERelation = getInput("Enter Relation of Emergency contact");
				// Insert a tuple into E_Contact values
				String sqlEC1 = "Insert into E_Contact values ("+SSN+",'"+EE_name+"','"+EMailAddress+"','"+EEmail+"',"+EHomeNumber+","+EWorkNumber+","+ECellNumber+",'"+ERelation+"')";
				try {
				    stmt.executeQuery(sqlEC1);
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
			}
			
			String sqlEC2 = "SELECT * FROM E_Contact";
			try {
	        	ResultSet rsEC = stmt.executeQuery(sqlEC2);
	        	// To display columns in E_Contact
				System.out.println("SSN | Emer. Contact Name | Mail Address | Email | Home Number | Work Number | Cell Number | Relation");
				while(rsEC.next()) {
					System.out.println(rsEC.getInt(1) + "|" + rsEC.getString(2) + "|" + rsEC.getString(3) + "|" + rsEC.getString(4) + "|" + rsEC.getInt(5) + "|" + rsEC.getInt(6) + "|" + rsEC.getInt(7) + "|" + rsEC.getString(8) );
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
			
			// variable to determine if Donor remains Anonymous or not
			String cAnonymous = getInput("Enter Y if Donor makes Anonymous donations or else enter N");
			char Anonymous = cAnonymous.charAt(0);
			// insert a tuple into Donor table 
			String sql5 = "Insert into Donor values ("+SSN+",'"+Anonymous+"')";
			// Display content/value from donor table
			String sql6 = "SELECT * FROM Donor";
			
			try {
	        	stmt.executeQuery(sql5);
				ResultSet rs2 = stmt.executeQuery(sql6);
				// display columns in donor table
				System.out.println("Donor SSN | Anonymous");
				while(rs2.next()) {
					System.out.println(rs2.getInt(1) + "|" + rs2.getString(2).charAt(0));
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
			// variable to determine number of donations made by donor
			String cND  = getInput("Enter Number of Donations made by this Donor");
			int ND = Integer.parseInt(cND.trim());
			// for loop iterates ND(Number of Donations) times
			for(int i=0;i<ND;i= i+1)
			{
				System.out.println("Enter date of donation");
				String D_date = getDateSQL();
				String cAmount = getInput("Enter Amount Donated by Donor");
				int Amount = Integer.parseInt(cAmount.trim());
				String D_type = getInput("Enter Donation type");
				String Campaign = getInput("Enter name of Fund Raising Campaign");
				// Insert a tuple into Donations tables
				String sql7 = "Insert into Donations values ("+SSN+","+D_date+","+Amount+",'"+D_type+"','"+Campaign+"')";
				String sql8 = "SELECT * FROM Donations";
				
				try {
	            	stmt.executeQuery(sql7);
					ResultSet rs3 = stmt.executeQuery(sql8);
					// to display columns in donations table
					System.out.println("SSN | Donation Date | Amount | Donation Type | Campaign");
					while(rs3.next()) {
						System.out.println(rs3.getInt(1) + "|" + rs3.getDate(2).toString()  + "|" + rs3.getInt(3) + "|" + rs3.getString(4) + "|" + rs3.getString(5) );
					}
				}catch(SQLException e) {
					e.printStackTrace();
				}
				
				
				String cD_mode = getInput("Enter mode of payment: 1 for card , 2 for check");
				int D_mode = Integer.parseInt(cD_mode.trim());
				// if mode of payment is by card
				if (D_mode == 1) {					
					String Card_No = getInput("Enter card number");
					String Card_Type = getInput("Enter card Type");
					System.out.println("Enter Expiry date on card");
					String Exp_Date = getDateSQL();
					// insert a tuple into D_Card
					String sqlc1 = "Insert into D_Card values ("+SSN+","+D_date+","+Amount+",'"+D_type+"','"+Card_No+"','"+Card_Type+"',"+Exp_Date+")";
					String sqlc2 = "SELECT * FROM D_Card";
					try {
		            	stmt.executeQuery(sqlc1);
						ResultSet rsca = stmt.executeQuery(sqlc2);
						// display columns in D_Card table
						System.out.println("SSN | Donation Date | Amount | Donation Type | Card No | Card Type | Exp.Date");
						while(rsca.next()) {
							System.out.println(rsca.getInt(1) + "|" + rsca.getDate(2).toString()  + "|" + rsca.getInt(3) + "|" + rsca.getString(4) + "|" + rsca.getString(5) + "|" + rsca.getString(6) + "|" + rsca.getDate(7).toString());
						}
					}catch(SQLException e) {
						e.printStackTrace();
					}
				} 
				// If mode of payment is by check
				else {
					String Check_No = getInput("Enter check number");
					// Insert a tuple into D_Check
					String sqlch1 = "Insert into D_Check values ("+SSN+","+D_date+","+Amount+",'"+D_type+"','"+Check_No+"')";
					String sqlch2 = "SELECT * FROM D_Check";
					try {
		            	stmt.executeQuery(sqlch1);
						ResultSet rsch = stmt.executeQuery(sqlch2);
						// To display columns in D_Check table
						System.out.println("SSN | Donation Date | Amount | Donation Type | Check No ");
						while(rsch.next()) {
							System.out.println(rsch.getInt(1) + "|" + rsch.getDate(2).toString()  + "|" + rsch.getInt(3) + "|" + rsch.getString(4) + "|" + rsch.getString(5));
						}
					}catch(SQLException e) {
						e.printStackTrace();
					}
				}														
			}	
			
		}																				
	}
	
		
	public static void option9() {
		String sqlO = "SELECT * FROM Ext_Organization";
		try {
			ResultSet rs = stmt.executeQuery(sqlO);
			// To display columns in Ext_Organization
			System.out.println("Org.Name | Mailing Address | Contact Person | Phone Number | Anonymous");
			while(rs.next()) {
				System.out.println(rs.getString(1) + "|" + rs.getString(2) + "|" + rs.getString(3) + "|" + rs.getInt(4) +  "|" + rs.getString(5).charAt(0));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		String coptionOE = getInput("Enter 1 if Organization you are entering is in database or Enter 2 if Organization is not in the database ");
		int optionOE = Integer.parseInt(coptionOE.trim());
		// If organization is in database
		if (optionOE == 1) {
			String O_name = getInput("Enter Organization name");
			// Variable that represents number of donations
			String cND  = getInput("Enter Number of Donations made by this Organization");
			int ND = Integer.parseInt(cND.trim());
			// for loop iterates ND(Number of donations) times
			for(int i=0;i<ND;i= i+1)
			{
				System.out.println("Enter date of donation");
				String OD_date = getDateSQL();
				String cAmount = getInput("Enter Amount Donated by Donor");
				int Amount = Integer.parseInt(cAmount.trim());
				String OD_type = getInput("Enter Donation type");
				String Campaign = getInput("Enter name of Fund Raising Campaign");
				// tuple added to Org_Donations
				String sql3 = "Insert into Org_Donations values ('"+O_name+"',"+OD_date+","+Amount+",'"+OD_type+"','"+Campaign+"')";
				String sql4 = "SELECT * FROM Org_Donations";
				
				try {
	            	stmt.executeQuery(sql3);
					ResultSet rs3 = stmt.executeQuery(sql4);
					// Display columns in Org_Donations
					System.out.println("Org. Name | Donation Date | Amount | Donation Type | Campaign");
					while(rs3.next()) {
						System.out.println(rs3.getString(1) + "|" + rs3.getDate(2).toString()  + "|" + rs3.getInt(3) + "|" + rs3.getString(4) + "|" + rs3.getString(5));
					}
				}catch(SQLException e) {
					e.printStackTrace();
				}
				
                String sOD_mode = getInput("Enter mode of payment: 1 for card, 2  for check");
                int OD_mode = Integer.parseInt(sOD_mode.trim());
                // If mode of payment is by card
				if (OD_mode == 1) {					
					String Card_No = getInput("Enter card number");
					String Card_Type = getInput("Enter card Type");
					System.out.println("Enter Expiry date on card");
					String Exp_Date = getDateSQL();
					// Insert tuple into Org_Card
					String sqlc1 = "Insert into Org_Card values ('"+O_name+"',"+OD_date+","+Amount+",'"+OD_type+"','"+Card_No+"','"+Card_Type+"',"+Exp_Date+")";
					String sqlc2 = "SELECT * FROM Org_Card";
					try {
		            	stmt.executeQuery(sqlc1);
						ResultSet rsca = stmt.executeQuery(sqlc2);
						// Display columns in Org_Card
						System.out.println("Org. Name | Donation Date | Amount | Donation Type | Card No | Card Type | Exp.Date");
						while(rsca.next()) {
							System.out.println(rsca.getString(1) + "|" + rsca.getDate(2).toString()  + "|" + rsca.getInt(3) + "|" + rsca.getString(4) + "|" + rsca.getString(5) + "|" + rsca.getString(6) + "|" + rsca.getDate(7).toString());
						}
					}catch(SQLException e) {
						e.printStackTrace();
					}
				} 
				// If mode of payment is by check
				else {
					String Check_No = getInput("Enter check number");
					// Insert a tuple into Org_Check
					String sqlch1 = "Insert into Org_Check values ('"+O_name+"',"+OD_date+","+Amount+",'"+OD_type+"','"+Check_No+"')";
					String sqlch2 = "SELECT * FROM Org_Check";
					try {
		            	stmt.executeQuery(sqlch1);
						ResultSet rsch = stmt.executeQuery(sqlch2);
						// To print columns in Org_Check tables
						System.out.println("Org. Name | Donation Date | Amount | Donation Type | Check No ");
						while(rsch.next()) {
							System.out.println(rsch.getString(1) + "|" + rsch.getDate(2).toString()  + "|" + rsch.getInt(3) + "|" + rsch.getString(4) + "|" + rsch.getString(5));
						}
					}catch(SQLException e) {
						e.printStackTrace();
					}
				}																							
			}							
		}
		// If organization is not in database
		else {
			String O_name = getInput("Enter Organization name");
			String Mailing_Address = getInput("Enter Mailing Address of Organization");
			String ContactPerson = getInput("Enter name of Contact person");
			String cPhoneNumber = getInput("Enter PhoneNumber of Organization");
			int PhoneNumber = Integer.parseInt(cPhoneNumber.trim());
			String cAnonymous = getInput("Enter Y if organization makes Anonymous donations or else enter N");
			char Anonymous = cAnonymous.charAt(0);
			// Insert a tuple into Ext_Organization
			String sql1 = "Insert into Ext_Organization values ('"+O_name+"','"+Mailing_Address+"','"+ContactPerson+"',"+PhoneNumber+",'"+Anonymous+"')";
			String sql2 = "SELECT * FROM Ext_Organization";
			
			try {
	        	stmt.executeQuery(sql1);
				ResultSet rs = stmt.executeQuery(sql2);
				// Display columns in Ext_Organization
				System.out.println("Org.Name | Mailing Address | Contact Person | Phone Number | Anonymous");
				while(rs.next()) {
					System.out.println(rs.getString(1) + "|" + rs.getString(2) + "|" + rs.getString(3) + "|" + rs.getInt(4) +  "|" + rs.getString(5).charAt(0));
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
			
			String cOrgType = getInput("Enter Organization Type: 1 for Business, 2 for Church");
			int OrgType = Integer.parseInt(cOrgType.trim());
			// If organization is of business type
			if (OrgType == 1) {
				String B_type = getInput("Enter Business Type");
				String cB_size = getInput("Enter size of company");
				int B_size = Integer.parseInt(cB_size.trim());
				String Company_website = getInput("Enter company website");
				
				// Enter tuple into Business
				String sql5 = "Insert into Business values ('"+O_name+"','"+B_type+"',"+B_size+",'"+Company_website+"')"; 
				String sql6 = "SELECT * FROM Business";
				try {
		        	stmt.executeQuery(sql5);
					ResultSet rs2 = stmt.executeQuery(sql6);
					// To print columns in Business table
					System.out.println("Org. Name | Buss. type | Buss. size | Company website");
					while(rs2.next()) {
						System.out.println(rs2.getString(1) + "|" + rs2.getString(2) + "|" + rs2.getInt(3) + "|" + rs2.getString(4));
					}
				}catch(SQLException e) {
					e.printStackTrace();
				}						
			}
			// If external organization is Church
			else {
				String Religious_Affiliation = getInput("Enter Religious Affiliation of Church");
				// Insert a tuple into Church			
				String sql7 = "Insert into Church values ('"+O_name+"','"+Religious_Affiliation+"')"; 
				String sql8 = "SELECT * FROM Church";
				try {
		        	stmt.executeQuery(sql7);
					ResultSet rs3 = stmt.executeQuery(sql8);
					// Display columns in Church table
					System.out.println("Org. Name | Religious Affiliation");
					while(rs3.next()) {
						System.out.println(rs3.getString(1) + "|" + rs3.getString(2));
					}
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}	
			
			// variable representing number of donations made
			String cND  = getInput("Enter Number of Donations made by this Organization");
			int ND = Integer.parseInt(cND.trim());
			// For loop iterates ND(Number of donations) times
			for(int i=0;i<ND;i= i+1)
			{
				System.out.println("Enter date of donation");
				String OD_date = getDateSQL();
				String cAmount = getInput("Enter Amount Donated by Donor");
				int Amount = Integer.parseInt(cAmount.trim());
				String OD_type = getInput("Enter Donation type");
				String Campaign = getInput("Enter name of Fund Raising Campaign");
				// insert a tuple into Org_Donations table
				String sql3 = "Insert into Org_Donations values ('"+O_name+"',"+OD_date+","+Amount+",'"+OD_type+"','"+Campaign+"')";
				String sql4 = "SELECT * FROM Org_Donations";
				
				try {
	            	stmt.executeQuery(sql3);
					ResultSet rs3 = stmt.executeQuery(sql4);
					// To print columns of Org_Donations
					System.out.println("Org. Name | Donation Date | Amount | Donation Type | Campaign");
					while(rs3.next()) {
						System.out.println(rs3.getString(1) + "|" + rs3.getDate(2).toString()  + "|" + rs3.getInt(3) + "|" + rs3.getString(4) + "|" + rs3.getString(5));
					}
				}catch(SQLException e) {
					e.printStackTrace();
				}
				
				String cOD_mode = getInput("Enter mode of payment: 1 for card , 2 for check");
				int OD_mode = Integer.parseInt(cOD_mode.trim());
				// If mode of payment is by card
				if (OD_mode == 1) {					
					String Card_No = getInput("Enter card number");
					String Card_Type = getInput("Enter card Type");
					System.out.println("Enter Expiry date on card");
					String Exp_Date = getDateSQL();
					// Insert a tuple into Org_Card table
					String sqlc1 = "Insert into Org_Card values ('"+O_name+"',"+OD_date+","+Amount+",'"+OD_type+"','"+Card_No+"','"+Card_Type+"',"+Exp_Date+")";
					String sqlc2 = "SELECT * FROM Org_Card";
					try {
		            	stmt.executeQuery(sqlc1);
						ResultSet rsca = stmt.executeQuery(sqlc2);
						// To display column names in Org_card
						System.out.println("Org. Name | Donation Date | Amount | Donation Type | Card No | Card Type | Exp.Date");
						while(rsca.next()) {
							System.out.println(rsca.getString(1) + "|" + rsca.getDate(2).toString()  + "|" + rsca.getInt(3) + "|" + rsca.getString(4) + "|" + rsca.getString(5) + "|" + rsca.getString(6) + "|" + rsca.getDate(7).toString());
						}
					}catch(SQLException e) {
						e.printStackTrace();
					}
				}  
				// If mode of payment is by check
				else {
					String Check_No = getInput("Enter check number");
					// Insert a tuple into Org_Check table
					String sqlch1 = "Insert into Org_Check values ('"+O_name+"',"+OD_date+","+Amount+",'"+OD_type+"','"+Check_No+"')";
					String sqlch2 = "SELECT * FROM Org_Check";
					try {
		            	stmt.executeQuery(sqlch1);
						ResultSet rsch = stmt.executeQuery(sqlch2);
						// To print columns in Org_Check tables
						System.out.println("Org. Name | Donation Date | Amount | Donation Type | Check No ");
						while(rsch.next()) {
							System.out.println(rsch.getString(1) + "|" + rsch.getDate(2).toString()  + "|" + rsch.getInt(3) + "|" + rsch.getString(4) + "|" + rsch.getString(5));
						}
					}catch(SQLException e) {
						e.printStackTrace();
					}
				}													
			}			
		}
																
	}	
	
	public static void option10() {
		String cSSN  = getInput("Enter SSN of Client whose Doctor's Name, Doctor's Number is to be retrieved:");
		// To convert string to Integer
		int SSN = Integer.parseInt(cSSN.trim());
		// Main Querry 
		String sql10 = "SELECT Doctor_Name, Doctor_Number from client where SSN="+SSN+"";
				 
		try {
			// result of query stored in rs
			ResultSet rs = stmt.executeQuery(sql10);
			// To display Column names in resulting output
			System.out.println("Doctor Name | Doctor Phone Number");
			while(rs.next()) {
				// To print the result
				System.out.println(rs.getString(1) + "|" + rs.getInt(2));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
		
		public static void option11() {
			System.out.println("Enter start date of expense");
			// To get date from user
			String st_date = getDateSQL();
			
			System.out.println("Enter end date of expense");
			String end_date = getDateSQL();									
			// Main Querry
			String sql11 = "select SSN, sum(Amount)as Total from Expenses where E_date between "+st_date+" and "+end_date+" group by SSN order by Total";
					 
			try {
				// result of query
				ResultSet rs = stmt.executeQuery(sql11);
				// To display Column names in resulting output
				System.out.println("SSN | Total Expense");
				while(rs.next()) {
					// To print the result
					System.out.println(rs.getInt(1) + "|" + rs.getInt(2));
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
	
		}
		
		public static void option12() {
			String cSSN  = getInput("Enter SSN of Client to get list of SSN of volunteers ");
			int SSN = Integer.parseInt(cSSN.trim());																
			// Mian Querry
			String sql12 = "select distinct SSN from serve where T_name in (select T_name from care where SSN = "+SSN+")";
					 
			try {
				// result of query
				ResultSet rs = stmt.executeQuery(sql12);
				// To display Column name in resulting output
				System.out.println("SSN of volunteers");
				while(rs.next()) {
					// To print the result
					System.out.println(rs.getInt(1));
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
	
		}
		
		public static void option13() {								
			// Main Querry
			String sql13 = "SELECT P_NAME,MAILADDRESS,EMAIL,HOMENUMBER,WORKNUMBER,CELLNUMBER FROM PERSON where SSN in(select SSN from care where T_Name in(SELECT UNIQUE(T_NAME) FROM SPONSOR WHERE O_NAME BETWEEN 'b%' AND 'k%')) ORDER BY P_NAME";
					 
			try {
				// result of query
				ResultSet rs = stmt.executeQuery(sql13);
				// To display Column names in resulting output
				System.out.println("Client Name | Mail Address | Email | Home Number | Work Number | Cell Number");
				while(rs.next()) {
					// To print the result
					System.out.println(rs.getString(1) + "|" + rs.getString(2) + "|" + rs.getString(3) + "|" + rs.getInt(4) + "|" + rs.getInt(5) + "|" + rs.getInt(6));
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
	
		}
		
		
		public static void option14() {								
			// main querry
			String sql14 = "SELECT P.P_NAME, SUM(DO.Amount) as Total,D.Anonymous FROM DONOR D, DONATIONS DO, EMPLOYEE E, PERSON P WHERE  D.SSN=DO.SSN AND D.SSN = E.SSN and P.SSN = E.SSN GROUP BY P.P_NAME, D.Anonymous order by total";
					 
			try {
				// result of query
				ResultSet rs = stmt.executeQuery(sql14);
				// To display Column names in resulting output
				System.out.println("Name | Total Amount | Anonymous");
				while(rs.next()) {
					// To print the result
					System.out.println(rs.getString(1) + "|" + rs.getInt(2) + "|" +rs.getString(3).charAt(0));
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
	
		}
		
		
		public static void option15() {		
			// main querry
			String sql15 = "Select P_Name,MailAddress,Email,HomeNumber,WorkNumber,CellNumber from Person where SSN in (Select SSN from serve where No_of_hours in (Select Max(No_of_hours) from serve where S_MONTH in (3,4,4,6) group by T_NAME))";
					 
			try {
				// result of query
				ResultSet rs = stmt.executeQuery(sql15);
				// To display Column names in resulting output
				System.out.println("Vol. Name | Mail Address | Email | Home Number | WorkNumber | Cell Number ");
				while(rs.next()) {
					// To print the result
					System.out.println(rs.getString(1) + "|" + rs.getString(2) + "|" + rs.getString(3) + "|" + rs.getInt(4) + "|" +rs.getInt(5) + "|" + rs.getInt(6));
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
	
		}
		
		
		public static void option16() {	
			String sqlsal = "select SSN, salary from employee";
			
			try {
				// result of query before update
				ResultSet rs = stmt.executeQuery(sqlsal);
				// To display Column names in resulting output
				System.out.println("SSN | Salary before update");
				while(rs.next()) {
					// To print the result
					System.out.println(rs.getInt(1) + "|" + rs.getInt(2));
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
			// main querry
			String sql16 = "Update Employee set salary = salary *1.10 where SSN in (select SSN from report group by SSN having count(SSN)>1)";
					 
			try {
				stmt.executeQuery(sql16);
				// result of query after update
				ResultSet rs1 = stmt.executeQuery(sqlsal);
				// To display Column names in resulting output
				System.out.println("SSN | Salary after update");
				while(rs1.next()) {
					// To print the result
					System.out.println(rs1.getInt(1) + "|" + rs1.getInt(2));
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
	
		}
		
		public static void option17() {
            String sqlcl = "select * from client";
            
			try {
				ResultSet rs = stmt.executeQuery(sqlcl);
				System.out.println("SSN | Doctor Name | Doctor Number | Attorney Name | Attorney Number | Date Assigned");
				while(rs.next()) {
					// To print the result
					System.out.println(rs.getInt(1) + "|" + rs.getString(2)+ "|"+ rs.getInt(3) + "|" + rs.getString(4) + "|" + rs.getInt(5) +"|"+ rs.getDate(6).toString());
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
			// Main Querry
			String sql17 = "Delete from Client where SSN in(Select SSN from CLIENT_Need where SSN in (Select SSN from Insurance where I_Type='health') and Need='transportation' and N_value<5)";
					 
			try {
				stmt.executeQuery(sql17);
				// result of query
				ResultSet rs1 = stmt.executeQuery(sqlcl);
				// To display Column names in resulting output
				System.out.println("SSN | Doctor Name | Doctor Number | Attorney Name | Attorney Number | Date Assigned");
				while(rs1.next()) {
					// To print the result
					System.out.println(rs1.getInt(1) + "|" + rs1.getString(2)+ "|"+ rs1.getInt(3) + "|" + rs1.getString(4) + "|" + rs1.getInt(5) + "|"+ rs1.getDate(6).toString());
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
	
		}
		
		public static void option18() {
			// to import file
			// path of file we want to import
			String path = getInput ("Please enter the file path of the file to be imported");
			File myfile = new File (path); // file that is imported
			FileReader f_r = null; // To read file
			try{
				f_r = new FileReader(myfile.getAbsoluteFile());
				BufferedReader b_r = new BufferedReader (f_r);
				String line;
				// while loop continues as long as it encounters null character 
				while ((line = b_r.readLine()) != null) {
					// Splitting string into different attributes
					String[] parts = line.split(","); // split used is ","
					String Team = parts[0];
					String Type = parts[1];
					String date = parts[2];
					// insert into team
					String sql = "Insert into team values ('"+Team+"','"+Type+"','"+date+"')";
					try {
						stmt.executeUpdate(sql);
					}catch(SQLException ex) {
						System.err.println("SQLException:" + ex.getMessage());
					}
				}
				// while loop ends 
				System.out.println("Import completed");
			}catch (IOException e) {
				System.out.println("Error in reading file");
				e.printStackTrace(); 
			}
			// To select content/values from Team table
			String sqlT = "SELECT * FROM Team";
			try {
				// result of query
				ResultSet rs3 = stmt.executeQuery(sqlT);
				// Display columns in resulting querry
				System.out.println("Team Name | Team type | Date Formed");
				while(rs3.next()) {
					// printing result
					System.out.println(rs3.getString(1) + "|" + rs3.getString(2)+ "|" + rs3.getDate(3).toString());
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
	
		}
		
		public static String quote (String s)
		{
			return "'" + s + "'";
		}
		
		
		public static void option19() {
			// Export to a file
			// file path where it need to be exported
			String path = getInput ("Please enter the file path of the file to export");
			File myfile = new File (path); // file that is exported
			FileWriter f_w = null; // to write ina file
			try{
				f_w = new FileWriter(myfile.getAbsoluteFile());
				BufferedWriter b_w = new BufferedWriter (f_w);				
				String sql = "select P_name, mailaddress from person";
				try {
					ResultSet rs4 = stmt.executeQuery(sql);
					//System.out.println(" Person Name | Mail Address");
					while(rs4.next()) {
						//System.out.println(rs4.getString(1) + "|" + rs4.getString(2));
						b_w.write(quote(rs4.getString(1) +"|"+ rs4.getString(2) + "\n"));
					}
				}catch(SQLException e) {
					e.printStackTrace();
				}
				b_w.close(); // writing done
																		
				System.out.println("Export Completed- Printed into text file");
			}
			catch (IOException e){
				System.out.println("Error while writing file");
				e.printStackTrace();
			}
																						
		}
		
}


