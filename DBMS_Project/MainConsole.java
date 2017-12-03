package mypackage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class MainConsole {
    private static DBController controller;
    private static Scanner kb = new Scanner(System.in);
    
    public static void main(String[] args) {
        controller = new DBController();
        try {   
        	int choice = 0;
        	String url = "jdbc:mysql://localhost/contact?useSSL=false";
            controller.dbaseConnect(url,"root",null);
        	while (true) {        		
                choice = showMenu();
                if (choice == 7) {
                	System.out.println("Thank your for trying this program...");
                	break;
                }
                processChoice(choice);
        	}        	
        } catch (SQLException e) {
        	System.err.println("error: " + e.getClass() + "\n" + e.getMessage());
        } catch (Exception e) {
        	System.err.println("error: " + e.getClass() + "\n" + e.getMessage());
        } finally {
            controller.close();
        }
    }
    
    private static void processChoice(int option) {
    	switch (option) {
	    	case 1: // Enter contact person
	    		enterContact();
	    		break;
	    	case 2: // Enter contact number(s)
	    		// do something here
	    		break;
	    	case 3: // Show all contacts	    		
	    		printAllContacts();	    		
	    		break;
	    	case 4: // Search contact person
	    		searchContact();
	    		break;
	    	case 5: // Edit contact info
	    		editContact();
	    		break;
	    	case 6: // Delete contact
	    		deleteContact();
	    		break;
    	}
    }
    
    private static int showMenu() {    	
    	int choice = 0;
		do {
    		System.out.println("+----------------------------+");
            System.out.println("|   M E N U  O P T I O N S   |");
            System.out.println("+----------------------------+");
            System.out.println("| 1. Enter contact person    |");
            System.out.println("| 2. Enter contact number/s  |");
            System.out.println("| 3. Show all contacts       |");
            System.out.println("| 4. Search contact person   |");
            System.out.println("| 5. Edit contact info       |");
            System.out.println("| 6. Delete contact          |");
            System.out.println("| 7. Exit program            |");
            System.out.println("+----------------------------+");
            System.out.print("Enter your choice: ");
            try {
            	choice = Integer.parseInt(kb.nextLine());
            } catch (Exception e) {
            	System.out.println("error: input a valid value...");
            	System.out.print("Press enter key to continue...");
            	kb.nextLine();
            }
        	System.out.println();
    	} while (choice < 1 || choice > 7);
        return choice;
    }
    
    private static void editContact() {
    	printDivider();
    	try {
    		String name = null;
    		ResultSet rs = null;
    		do {
    			name = inputName("Enter exact name of contact to be edited: ");
        		rs = controller.searchContactInfo(name);       		
        		if (getResTotal(rs) == 0) {
        			System.out.println("error: Name not found. Please enter an existing contact name.");
        		}
    		} while (getResTotal(rs) == 0);
    		rs = controller.searchContactInfo(name);
    		printContact(rs);
    		rs.beforeFirst();
    		String col = enterColumn();
    		String rValue = null;
    		if (col.equals("number") || col.equals("home")) {
    			int row = 1;
    			if (getResTotal(rs) > 1) {    			
        			do {
        				System.out.print("Enter the row to be edited: ");
        				row = Integer.parseInt(kb.nextLine());
        			} while (row < 0 || row > getResTotal(rs));
        		}
        		System.out.print("Enter replacement value: ");
        		rValue = kb.nextLine();
        		controller.updateNumberInfo(name, col, row, rValue);
    		} else {
    			System.out.print("Enter replacement value: ");
        		rValue = kb.nextLine();
        		controller.updateContactInfo(name, col, rValue);
    		}
    		rs = null;
    		if (col.equals("name")) {
    			rs = controller.searchContactInfo(rValue);
    		} else {
    			rs = controller.searchContactInfo(name);
    		}    		
    		printContact(rs);
    	} catch (Exception e) {
    		System.err.println("error: " + e.getClass() + "\n" + e.getMessage());
    	}
    }
    
    private static String enterColumn() {
    	String col = null;
    	boolean valid = false;
    	do {
    		System.out.print("Enter the column name you would like edit: ");
    		col = kb.nextLine().toLowerCase();
    		switch (col) {
    			case "name":
    			case "address":
    			case "e-mail":
    			case "gender":
    			case "contact":
    			case "type":
    				valid = true;
    		}
    	} while (!valid);
    	col = reformatColumn(col);
    	return col;
    }
    
    private static String reformatColumn(String col) {
    	switch(col) {
    	case "address":
    		col = "addr";
    		break;
    	case "e-mail":
    		col = "email";
    		break;
    	case "contact":
    		col = "number";
    		break;
    	case "type":
    		col = "home";
    	}
    	return col;
    }
    
    private static void deleteContact() {
    	printDivider();
    	try {
    		String name = inputName("Enter the exact contact name to be deleted: ");
    		ResultSet rs = controller.searchContactInfo(name);
    		if (getResTotal(rs) == 0) {
    			System.out.println("error: Name not found. Please enter an existing contact name.");
    			deleteContact();
    		}
			controller.deleteContact(name);
			System.out.println(name + " was successfully deleted from the contact list.");
    		printDivider();
    		System.out.println();
    	} catch (Exception e) {
    		System.err.println("error: " + e.getClass() + "\n" + e.getMessage());
    	}    	
    }
    
    private static void enterContact() {
    	printDivider();
    	System.out.print("Enter name: ");
    	String name = kb.nextLine();
    	System.out.print("Enter address: ");
    	String addr = kb.nextLine();
    	System.out.print("Enter e-mail: ");
    	String email = kb.nextLine();
    	int gender;
    	do {
    		System.out.println("Select gender:");
    		System.out.println("\t1. Male");
    		System.out.println("\t2. Female");
    		System.out.print("Enter gender: ");
    		gender = Integer.parseInt(kb.nextLine());
    	} while (gender < 1 || gender > 2);
    	
    	try {
    		controller.createContact(name,addr,email,gender == 1 ? "Male" : "Female");
    		System.out.println(name + " was successfully added in the contact list.");
    		char cho;
    		do {
    			System.out.println("Do you want to enter contact's number(s) [y/n]? ");
    			cho = Character.toLowerCase(kb.nextLine().charAt(0));
    		} while (cho != 'y' && cho != 'n');
    		if (cho == 'y') {
    			// enter contact number: call the appropriate method to do the job
    		}
    		printDivider();
    		System.out.println();
    	} catch (MySQLIntegrityConstraintViolationException e) {
    		// duplicate name
    		System.out.println("error: Name already exists.  Please enter another name.");
    		enterContact();
    	} catch (Exception e) {
    		System.err.println("error: " + e.getClass() + "\n" + e.getMessage());
    	}
    }
    
    private static void printAllContacts() {
    	printDivider();
    	try {
    		ResultSet rs = controller.getAllContacts();
    		if (getResTotal(rs) == 0) {
    			System.out.println("Error: no records found!!!");
    		} else {   
    			System.out.println("Contact listing:");    			
    	        System.out.printf("     %-15s %-20s %-15s %-10s %-15s %-10s%n",
    	        		"Name","Address","E-mail","Gender", "Contact", "Type");
    	        int row = 1;
    	        while (rs.next()) {
    	            // It is possible to get the columns via name
    	            // also possible to get the columns via the column number
    	            // which starts at 1
    	            // e.g. resultSet.getSTring(2);            
    	            String name = rs.getString(1); // 'name' column
    	            String address = filterNull(rs.getString(2)); // 'addr' column
    	            String email = filterNull(rs.getString("email"));
    	            String gender = rs.getString("gender");
    	            String contact = filterNull(rs.getString("number"));
    	            int type = rs.getInt("home");
    	            String conType = contact == "" ? "" : (type != 0 ? "Home" : "Work");
    	            System.out.printf("%-4d %-15s %-20s %-15s %-10s %-15s %-10s%n",
    	            		row++,name,address,email,gender,contact,conType);
    	        }
    		}		
    		printDivider();
	        System.out.println();
    	} catch (Exception e) {
    		System.err.println("error: " + e.getClass() + "\n" + e.getMessage());
    	}
    }
    
    private static int getResTotal(ResultSet rs) throws Exception {
    	int count = 0;
    	rs.last();
    	count = rs.getRow();
    	rs.beforeFirst();
    	return count;    	
    }
    
    private static void searchContact() {
    	try {
    		String name = inputName("Enter name to be searched: ");
    		ResultSet result = controller.searchContactInfo(name);
    		printContact(result);
    	} catch (Exception e) {
    		System.err.println("error: " + e.getClass() + "\n" + e.getMessage());
    	}		
    }
    
    private static void printContact(ResultSet rs) {
    	try {
    		if (getResTotal(rs) == 0) { 
    			System.out.println("Error: name not found!!!");
    		} else {
    			printDivider();
    			System.out.printf("     %-15s %-20s %-15s %-10s %-15s %-10s%n",
    	        		"Name","Address","E-mail","Gender", "Contact", "Type");
    			int row = 1;
    			while (rs.next()) {
    				String name = rs.getString("name");
    	            String address = rs.getString("addr");
    	            String email = rs.getString("email");
    	            String gender = rs.getString("gender");
    	            String contact = rs.getString("number");
    	            int type = rs.getInt("home");
    	            String conType = contact == "" ? "" : (type != 0 ? "Home" : "Work");
    	            System.out.printf("%-4d %-15s %-20s %-15s %-10s %-15s %-10s%n",
    	            		row++,name,address,email,gender,contact,conType);    	        
    			}    			
    		}
    		printDivider();
    		System.out.println();
    	} catch (Exception e) {
    		System.err.println("error: " + e.getClass() + "\n" + e.getMessage());
    	}
    }
    
    private static String filterNull(String str) {
    	return str == null ? "" : str;
    }
    
    private static void printDivider() {
    	for (int i = 1; i <= 90; i++) {
    		System.out.print("*");
    	}
    	System.out.println();
    }
    
    private static String inputName(String prompt) {
    	String input = null;
    	boolean valid;
    	do {
    		System.out.print(prompt);
    		input = kb.nextLine();
    		valid = input.matches("[a-zA-Z_ -.]{1,15}");
    	} while (!valid);
    	return input;
    }
}
