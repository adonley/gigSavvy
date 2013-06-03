package DataBase;

public class CustomerDB {

	public String ID, wristbandID, firstName, lastName, cellNumber, email;
	public int preAuth, accessLevel;
	
	public CustomerDB() {
		//Let's just initialize these here so we can use them elsewhere without doing so.
		ID = new String("");
		firstName = new String("");
		lastName = new String("");
		cellNumber = new String("");
	}
	
}
