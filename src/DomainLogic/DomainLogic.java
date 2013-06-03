package DomainLogic;

import java.sql.*;
import java.util.Random;

import DataBase.CustomerDB;
import DataBase.TransactionDB;
import DataBase.VendorDB;


public class DomainLogic {
	
	// Japanese Venture Capitalist "Guy Kawasaki"
	private static String url = "jdbc:mysql://127.0.0.1:3306/";
	private static String dataBase = "getgigsavvy";
	private static String user = "gigsavvy";
	private static String password = "67bah67";
	private static String driver = "com.mysql.jdbc.Driver";
	
	
	public static int numberOfCustomers, numberOfVendors, numberOfTransactions;
	private static String [] areas = {"Area 1","Area 2","Area 3","Area 4","Area 5"};
	private Connection con = createConnection();
	private Statement statement;
	private ResultSet results;
	
	public enum reason { CLEARED, NOT_ENOUGH_FUNDS }
	
	// Let's make this class singleton to not eat up memory
	private static final DomainLogic instance = new DomainLogic();
	
	private DomainLogic() {
		
		// Let's get the number of vendors and customers so we can generate the tables
		try {
			Statement state = con.createStatement();
			
			results = state.executeQuery("SELECT ID FROM customers;");
			results.last();
			numberOfCustomers = results.getRow();
			
			results = state.executeQuery("SELECT ID FROM vendors;");
			results.last();
			numberOfVendors = results.getRow();
			
			results = state.executeQuery("SELECT ID FROM transactions;");
			results.last();
			numberOfTransactions = results.getRow();
			
			// TODO count transactions
		} catch (Exception e) {
			
			System.out.println("Problem in the constructor");
			e.printStackTrace();
		
		}
		
	}
	
	
	public static DomainLogic getInstance() {
		return instance;
	}
	
	/**
	 * Creates a connection to the mySql database
	 * @return connection to mySQL Database
	 */
	public Connection createConnection() {
		Connection tempCon = null;
		try {
			
			Class.forName(driver).newInstance();
			tempCon = DriverManager.getConnection(url+dataBase,user,password);
			
		} catch (Exception e) {
			
			System.out.println("Problem in Create Connection");
			e.printStackTrace();
			return tempCon;
		
		}
		
		return tempCon;
	}
	
	protected void closeConnection() {
		if(con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean generateCustomers(int customerSlider) {
		
		try {
			
			
			statement = con.createStatement();
			
			statement.execute("DROP TABLE IF EXISTS customers;");
			statement.execute("CREATE TABLE customers (ID VARCHAR(40), first_name VARCHAR(50), last_name VARCHAR(50)," +
					"cell_number VARCHAR(10), pre_auth SMALLINT, email VARCHAR(60), KEY (ID));");
			
			CustomerDB tempCust;
			for(int i = 0; i < customerSlider; i++) {
				
				if(Thread.interrupted()) {
					//closeConnection();
					return false;
				}
				
				tempCust = randomCust(i);
				statement.executeUpdate("INSERT INTO customers VALUES ('" + tempCust.ID + "','" + tempCust.firstName +
						"','" + tempCust.lastName + "','" + tempCust.cellNumber + "','"
						+ tempCust.preAuth + "', NULL);");
				
			}
			
			numberOfCustomers = customerSlider;
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
	public CustomerDB randomCust(int i) {
		CustomerDB temp = new CustomerDB();
		Random randomGenerator = new Random();
		
		temp.ID = new Integer(i).toString();
		temp.preAuth = randomGenerator.nextInt(400) + 1;
		
		temp.firstName = "FirstName " + (randomGenerator.nextInt(400) + 1);
		temp.lastName = "LastName " + (randomGenerator.nextInt(400) + 1);
		
		for(int j = 0; j < 10 ; j++) {
			temp.cellNumber = temp.cellNumber + ((char)(randomGenerator.nextInt(10) + 48));
		}
		
		return temp;
		
	}
	
	public Object[][] getCustomers() {
		
		Object [][] setReturn = null;
		
		try {
			statement = con.createStatement();
			results = statement.executeQuery("SELECT * FROM customers;");
			
			// Count columns without email data
			int cols = results.getMetaData().getColumnCount();
			
			// Count rows
			results.last();
			int rows = results.getRow();
			
			// Set pointer back at first element
			results.first();
			
			setReturn = new Object[rows][cols];
			
			// Using rows as counter here
			rows = 0;
			
			do {
				setReturn[rows][0] = results.getString("ID");
				setReturn[rows][1] = results.getString("first_name");
				setReturn[rows][2] = results.getString("last_name");
				setReturn[rows][3] = results.getString("cell_number");
				setReturn[rows][4] = results.getInt("pre_auth");
				rows++;
			} while (results.next());
			
		} catch (Exception e) { 
			e.printStackTrace();
		}

		return setReturn;
		
	}
	
	
	public Object[][] getVendors() {
		
		Object [][] setReturn = null;
		
		try {
			statement = con.createStatement();
			results = statement.executeQuery("SELECT * FROM vendors;");
			
			// Count columns without email data
			int cols = results.getMetaData().getColumnCount();
			
			// Count rows
			results.last();
			int rows = results.getRow();
			
			// Set pointer back at first element
			results.first();
			
			setReturn = new Object[rows][cols];
			
			// Using rows as a counter
			rows = 0;
			do {
				setReturn[rows][0] = results.getString("ID");
				setReturn[rows][1] = results.getString("name");
				setReturn[rows][2] = results.getString("location");
				setReturn[rows][3] = results.getString("cashier");
				setReturn[rows][4] = results.getInt("employees");
				rows++;
			} while(results.next());
			
		} catch (Exception e) { 
			e.printStackTrace();
		}
		
		return setReturn;
		
	}
	
	public boolean generateVendors(int vendorSlider) {

		try {
			
			statement = con.createStatement();
			
			statement.execute("DROP TABLE IF EXISTS vendors;");
			statement.execute("CREATE TABLE vendors (ID VARCHAR(40), name VARCHAR(50), location VARCHAR(50)," +
					"cashier VARCHAR(11), employees SMALLINT, KEY (ID));");
			
			VendorDB tempVend;
			for(int i = 0; i < vendorSlider; i++) {
				
				if(Thread.interrupted()) {
					//closeConnection();
					return false;
				}
				
				tempVend = randomVend(i);
				statement.executeUpdate("INSERT INTO vendors VALUES ('" + tempVend.ID + "','" + tempVend.name +
						"','" + tempVend.location + "','" + tempVend.cashier + "','" + tempVend.numberOfEmployees + "');");
			}
			
			numberOfVendors = vendorSlider;
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
	public VendorDB randomVend(int i) {
		
		VendorDB temp = new VendorDB();
		Random randomGenerator = new Random();
		
		temp.ID = new Integer(i).toString();
		temp.numberOfEmployees = (randomGenerator.nextInt(10) + 1 );
		temp.location = areas[randomGenerator.nextInt(5)];
	
		temp.name = "Vendor " + i;
		temp.cashier = "Cashier " + randomGenerator.nextInt(temp.numberOfEmployees);
		
		return temp;
		
	}
	
	public Object[][] getTransactions() {
		
		Object [][] setReturn = null;
		
		try {
			statement = con.createStatement();
			results = statement.executeQuery("SELECT * FROM transactions;");
			
			// Count columns without email data
			int cols = results.getMetaData().getColumnCount();
			
			// Count rows
			results.last();
			int rows = results.getRow();
			
			// Set pointer back at first element
			results.first();
			
			setReturn = new Object[rows][cols];
			
			// Using rows as a counter
			rows = 0;
			do {
				setReturn[rows][0] = results.getString("ID");
				setReturn[rows][1] = results.getString("customer_ID");
				setReturn[rows][2] = results.getString("vendor_ID");
				setReturn[rows][3] = results.getInt("amount_charged");
				setReturn[rows][4] = results.getString("reason");
				setReturn[rows][5] = results.getBoolean("resolved");
				setReturn[rows][6] = results.getTimestamp("time");
				rows++;
			} while(results.next());
			
		} catch (Exception e) { 
			e.printStackTrace();
		}
		
		return setReturn;
		
	}
	
	public TransactionDB runTransactionSimulation() {
		
		Random randomGenerator = new Random();
		TransactionDB tempTransaction = new TransactionDB();
		int tempInt;
		String format = "%1$-15s %2$-15s", update;
		
		try {
			
			statement = con.createStatement();
			
			statement.execute("CREATE TABLE IF NOT EXISTS transactions (ID VARCHAR(40), time TIMESTAMP," +
				"amount_charged INT, reason VARCHAR(50), customer_ID VARCHAR(40), vendor_ID VARCHAR(40), resolved BOOLEAN, KEY (ID));");
			
			tempTransaction.setID(new Integer(numberOfTransactions).toString());
			
			// VENDOR INFORMATION
			tempInt = randomGenerator.nextInt(numberOfVendors);
			results = statement.executeQuery("SELECT * FROM vendors WHERE ID='" + tempInt + "';");
			results.first();
			tempTransaction.setVendName(results.getString("name"));
			tempTransaction.setLocation(results.getString("location"));
			tempTransaction.setVendorID(new Integer(tempInt).toString());
			
			// CUSTOMER INFORMATION
			tempInt = randomGenerator.nextInt(numberOfCustomers);
			results = statement.executeQuery("SELECT * FROM customers WHERE ID='" + tempInt + "';");
			results.first();
			tempTransaction.setCustomerID(new Integer(tempInt).toString());
			update = String.format(format, results.getString("first_name"),results.getString("last_name"));
			tempTransaction.setCustName(update);
			
			tempTransaction.setAmountCharged((randomGenerator.nextInt(10)+1));
			
			if(results.getInt("pre_auth") < tempTransaction.getAmountCharged()) {
				
				tempTransaction.setResolved(true);
				// TODO make this an enum type
				tempTransaction.setReason("Insufficient Funds");
				
			} else {
				
				tempTransaction.setResolved(true);
				// TODO make this an enum type
				tempTransaction.setReason("Cleared");
				statement.executeUpdate("UPDATE customers SET pre_auth='" + (results.getInt("pre_auth") - tempTransaction.getAmountCharged())
						+ "' WHERE ID='"+ results.getString("ID") + "';");
				
			} 
			
			if(tempTransaction.getResolved())
				tempInt = 1;
			else
				tempInt = 0;
				
			statement.executeUpdate("INSERT INTO transactions VALUES ('" + tempTransaction.getID() + "','" + tempTransaction.getTimestamp() +
					"','" + tempTransaction.getAmountCharged() + "','" + tempTransaction.getReason() + "','" 
					+ tempTransaction.getCustomerID() + "','" + tempTransaction.getVendorID() + "','" + tempInt + "');");
				
			numberOfTransactions++;
		} catch (Exception e) {
			
			e.printStackTrace();
			tempTransaction = null;
		
		}
		
		return tempTransaction;
	}
	
	public void deleteTransactions() {
		try {
			
			statement = con.createStatement();
			statement.execute("DROP TABLE IF EXISTS transactions;");
			numberOfTransactions = 0;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void runCredentialManagementSimulation() {
		// TODO create checkin events
	}
	
}
