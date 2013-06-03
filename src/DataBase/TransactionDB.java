package DataBase;

import java.sql.Timestamp;
import java.util.Date;

public class TransactionDB {

	String ID, productCategory, reason, custID, vendID, custName, vendName, location;
	Timestamp time;
	Date date;
	int amountCharged;
	boolean resolved;
	
	// Basic Constructor which constructs timestamp in string form
	public TransactionDB() {
		date = new Date();
		time = new Timestamp(date.getTime());
	}
	
	public String getID() { return ID;}
	public String getProductCategory() { return productCategory; } 
	public String getReason() { return reason; }
	public Timestamp getTimestamp() { return time; }
	public int getAmountCharged() { return amountCharged; }
	public boolean getResolved() { return resolved; }
	public String getCustomerID() { return custID; }
	public String getVendorID() { return vendID; }
	public String getCustName() { return custName; }
	public String getVendName() { return vendName; }
	public String getLocation() { return location; }
	public Date getDate() { return date; }
	
	public void setID(String temp) { ID = temp; }
	public void setProductCategory(String temp) { productCategory = temp; }
	public void setTimestamp(Timestamp temp) { time = temp; } 
	public void setAmountCharged(int temp) { amountCharged = temp; } 
	public void setResolved(boolean temp) { resolved = temp; } 
	public void setCustomerID(String temp) { custID = temp; }
	public void setVendorID(String temp) { vendID = temp; }
	public void setReason(String temp) { reason = temp; }
	public void setCustName(String temp) { custName = temp; }
	public void setVendName(String temp) { vendName = temp; }
	public void setLocation(String temp) { location = temp; }
	
}