package DataBase;

public class VendorDB {

	public class FootPrint {
		
		private int sizex, sizey;
		public int getSizex(){ return sizex; }
		public int getSizey(){ return sizey; }
		public void setSizex(int x) { sizex = x; }
		public void setSizey(int y) { sizey = y; }
		
	}
	
	public String ID, deviceID, cashier, location, typeOfFood, name;
	public int locationCost, numberOfEmployees;
	public FootPrint footPrint;
	
	public VendorDB() {
		ID = new String();
		deviceID = new String();
		cashier = new String();
		location = new String();
		typeOfFood = new String();
		name = new String();
	}
	
}
