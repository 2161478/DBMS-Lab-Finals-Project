import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Main
{
	private static DBController controller;
	private static Scanner kbd = new Scanner(System.in);

	public static void main(String[] args)
	{
		controller = new DBController();
		try {
			int choice = 0;
			String url = "jdbc:mysql://localhost/group1?useSSL=false";
			controller.dbaseConnect(url, "root", null);
			while (true) {
				choice = showMenu();
				if (choice == 11) {
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

	public static void addEmpProj()
	{
		System.out.print("Enter employee id: ");
		int empID = kbd.nextInt();
		System.out.print("Enter project id: ");
		int projID = kbd.nextInt();
		System.out.print("Enter hour rate: ");
		double hrRate = kbd.nextDouble();
		System.out.print("Enter start date of employee (Ex:2017-05-02): ");
		String startDate = kbd.nextLine();
		try {
			controller.createEmployeeProject(empID, projID, startDate, hrRate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Successfully added in the employee project list.");
		printDivider();
		System.out.println();
	}

	private static void processChoice(int option)
	{
		switch (option) {
		case 1:
			printAllEmployees();
			break;
		case 2:
			printAllProjects();
			break;
		case 3:
			printAllDepartments();
			break;
		case 4:
			printAllEmployeeProject();
			break;
		case 5:
			printAllProjectEmployee();
			break;
		case 6:
			printAllEmployeesOfDepartment();
			break;
		case 7:
			// add an employee
			addEmployee();
			break;
		case 8:
			// add an employee
			addProject();
			break;
		case 9:
			// add an employee
			addDepartment();
			break;
		case 10:
			addEmpProj();
			break;

		default:
			System.out.println("From numbers 1 to 11 only!");
		}
	}

	private static int showMenu()
	{
		int choice = 0;
		do {
			System.out.println("+-------------------------------------------+");
			System.out.println("|   M E N U  O P T I O N S                  |");
			System.out.println("+-------------------------------------------+");
			System.out.println("| 1. Show all Employees                     |");
			System.out.println("| 2. Show all Projects                      |");
			System.out.println("| 3. Show all Departments                   |");
			System.out.println("| 4. Show Employees working in a project    |");
			System.out.println("| 5. Show Projects an Employee is working in|");
			System.out.println("| 6. Print all Employees from a Department  |");
			System.out.println("| 7. Add an Employee                        |");
			System.out.println("| 8. Add a Project                          |");
			System.out.println("| 9. Add a Department                       |");
			System.out.println("| 10.Add an Employee Project                |");
			System.out.println("| 11.Exit program                           |");
			System.out.println("+-------------------------------------------+");
			System.out.print("Enter your choice: ");
			try {
				choice = Integer.parseInt(kbd.nextLine());
			} catch (Exception e) {
				System.out.println("error: input a valid value...");
				System.out.print("Press enter key to continue...");
				// kbd.nextLine();
			}
			System.out.println();
		} while (choice < 1 || choice > 11);
		return choice;
	}

	private static void printAllEmployees()
	{
		printDivider();
		try {
			ResultSet rs = controller.getAllEmployee();
			if (getResTotal(rs) == 0) {
				System.out.println("Error: no records found!!!");
			} else {
				System.out.println("Employee listing:");
				System.out.printf("     %-20s %-20s %-20s %-20s %-20s %-20s%n", "Name", "Job", "Hire Date", "Birth Day",
						"Manager", "Department");
				int row = 1;
				while (rs.next()) {
					String name = rs.getString(1);
					String job = filterNull(rs.getString(2));
					String hiredate = filterNull(rs.getString(3));
					String bday = rs.getString(4);
					String manager = filterNull(rs.getString(5));
					String department = filterNull(rs.getString(6));
					System.out.printf("%-4d %-20s %-20s %-20s %-20s %-20s %-20s%n", row++, name, job, hiredate, bday,
							manager, department);
				}
			}
			printDivider();
			System.out.println();
		} catch (Exception e) {
			System.err.println("error: " + e.getClass() + "\n" + e.getMessage());
		}
	}

	private static void printAllProjects()
	{
		printDivider();
		try {
			ResultSet rs = controller.getAllProject();
			if (getResTotal(rs) == 0) {
				System.out.println("Error: no records found!!!");
			} else {
				System.out.println("Project listing:");
				System.out.printf("     %-20s %-20s %-20s%n", "Project Name", "Status", "Department");
				int row = 1;
				while (rs.next()) {
					String name = rs.getString(1);
					String status = filterNull(rs.getString(2));
					String department = filterNull(rs.getString(3));
					System.out.printf("%-4d %-20s %-20s %-20s%n", row++, name, status, department);
				}
			}
			printDivider();
			System.out.println();
		} catch (Exception e) {
			System.err.println("error: " + e.getClass() + "\n" + e.getMessage());
		}
	}

	private static void printAllDepartments()
	{
		printDivider();
		try {
			ResultSet rs = controller.getAllDepartment();
			if (getResTotal(rs) == 0) {
				System.out.println("Error: no records found!!!");
			} else {
				System.out.println("Department listing:");
				System.out.printf("     %-20s%n", "Department");
				int row = 1;
				while (rs.next()) {
					String name = rs.getString(1);
					System.out.printf("%-4d %-20s%n", row++, name);
				}
			}
			printDivider();
			System.out.println();
		} catch (Exception e) {
			System.err.println("error: " + e.getClass() + "\n" + e.getMessage());
		}
	}

	private static void printAllProjectEmployee()
	{ // prints all employees working in a project
		printDivider();
		try {
			System.out.print("Enter name of the project: ");
			String projName = kbd.nextLine();
			ResultSet rs = controller.getProjectEmployee(projName);
			if (getResTotal(rs) == 0) {
				System.out.println("Error: no records found!!!");
			} else {
				System.out.println("Employees working in Project " + projName + ": ");
				System.out.printf("     %-20s %-20s %-20s%n", "Employees", "Date Started", "Hourly Rate");
				int row = 1;
				while (rs.next()) {
					String name = rs.getString(1);
					String dateStarted = filterNull(rs.getString(2));
					String hourlyRate = filterNull(rs.getString(3));
					System.out.printf("%-4d %-20s %-20s %-20s%n", row++, name, dateStarted, hourlyRate);
				}
			}
			printDivider();
			System.out.println();
		} catch (Exception e) {
			System.err.println("error: " + e.getClass() + "\n" + e.getMessage());
		}
	}

	private static void printAllEmployeeProject()
	{ // prints all projects an employee is working in
		printDivider();
		try {
			System.out.print("Enter name of the employee: ");
			String empName = kbd.nextLine();
			ResultSet rs = controller.getEmployeeProject(empName);
			if (getResTotal(rs) == 0) {
				System.out.println("Error: no records found!!!");
			} else {
				System.out.println("Projects " + empName + " is working on: ");
				System.out.printf("     %-20s %-20s %-20s%n", "Project", "Date Started", "Hourly Rate");
				int row = 1;
				while (rs.next()) {
					String project = rs.getString(1);
					String dateStarted = filterNull(rs.getString(2));
					String hourlyRate = filterNull(rs.getString(3));
					System.out.printf("%-4d %-20s %-20s %-20s%n", row++, project, dateStarted, hourlyRate);
				}
			}
			printDivider();
			System.out.println();
		} catch (Exception e) {
			System.err.println("error: " + e.getClass() + "\n" + e.getMessage());
		}
	}

	private static void printAllEmployeesOfDepartment(){
		printDivider();
		try {
			System.out.print("Enter name of the department: ");
			String deptName = kbd.nextLine();
			ResultSet rs = controller.getEmployeesFromDepartment(deptName);
			if (getResTotal(rs) == 0) {
				System.out.println("Error: no records found!!!");
			} else {
				System.out.println("Employees from " + deptName + " department: ");
				System.out.printf("     %-20s %-20s %-20s%-20s %-20s %-20s%n", "Employee ID", "Name", 
						"Job", "Hire Date", "Birth Date", "Manager");
				int row = 1;
				while (rs.next()) {
					String empid = rs.getString(1);
					String name = rs.getString(2);
					String job = rs.getString(3);
					String hiredate = rs.getString(4);
					String birthdate = rs.getString(5);
					String manager = rs.getString(6);
					System.out.printf("%-4d %-20s %-20s %-20s%-20s %-20s %-20s%n", row++, empid, name, job, hiredate, birthdate, manager);
				}
			}
			printDivider();
			System.out.println();
		} catch (Exception e) {
			System.err.println("error: " + e.getClass() + "\n" + e.getMessage());
		}
	}
	private static void addEmployee()
	{
		// printDivider();

		System.out.print("Enter name: ");
		String name = kbd.nextLine();

		System.out.print("Enter job: ");
		String job = kbd.nextLine();

		System.out.print("Enter hiredate (Ex:2017-05-02) :");
		String hiredate = kbd.nextLine();

		System.out.print("Enter bday: (1998-05-02): ");
		String bday = kbd.nextLine();
		System.out.print("Enter manager id: ");
		String managerid = kbd.nextLine();

		System.out.print("Enter department id: ");
		String department = kbd.nextLine();

		try {
			controller.createEmployee(name, job, hiredate, bday, managerid, department);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(name + " was successfully added in the employee list.");
		printDivider();
		System.out.println();
	}

	private static void addDepartment()
	{
		// printDivider();
		System.out.print("Enter Department Name: ");
		String name = kbd.nextLine();

		try {
			controller.createDepartment(name);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(name + " was successfully added in the department list.");
		printDivider();
		System.out.println();
	}

	private static void addProject()
	{
		// printDivider();
		System.out.print("Enter Project Name: ");
		String name = kbd.nextLine();
		System.out.print("Enter Status of the Project: ");
		String status = kbd.nextLine();
		System.out.print("Enter Department in which the project belongs: ");
		String deptid = kbd.nextLine();
		System.out.print("Enter Project Head: ");
		String projHead = kbd.nextLine();

		try {
			controller.createProject(name, status, deptid, projHead);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(name + " was successfully added in the project list.");
		printDivider();
		System.out.println();
	}

	private static int getResTotal(ResultSet rs) throws Exception
	{
		int count = 0;
		rs.last();
		count = rs.getRow();
		rs.beforeFirst();
		return count;
	}

	private static String filterNull(String str)
	{
		return str == null ? "" : str;
	}

	private static void printDivider()
	{
		for (int i = 1; i <= 120; i++) {
			System.out.print("*");
		}
		System.out.println();
	}
}
