import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    private static DBController controller;
    private static Scanner kbd = new Scanner(System.in);

    public static void main(String[] args) {
        controller = new DBController();
        try {
            int choice = 0;
            String url = "jdbc:mysql://localhost/group1?useSSL=false";
            controller.dbaseConnect(url, "root", null);
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
                //deleteContact();
                break;
            default:
                System.out.println("From numbers 1 to 7 only!");
        }
    }

    private static int showMenu() {
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
            System.out.println("| 6. Show all projects                      |");
            System.out.println("| 7. Exit program                           |");
            System.out.println("+-------------------------------------------+");
            System.out.print("Enter your choice: ");
            try {
                choice = Integer.parseInt(kbd.nextLine());
            } catch (Exception e) {
                System.out.println("error: input a valid value...");
                System.out.print("Press enter key to continue...");
                kbd.nextLine();
            }
            System.out.println();
        } while (choice < 1 || choice > 7);
        return choice;
    }

    private static void printAllEmployees(){
        printDivider();
        try {
            ResultSet rs = controller.getAllEmployee();
            if (getResTotal(rs) == 0) {
                System.out.println("Error: no records found!!!");
            } else {
                System.out.println("Employee listing:");
                System.out.printf("     %-20s %-20s %-20s %-20s %-20s %-20s%n",
                        "Name","Job","Hire Date","Birth Day", "Manager", "Department");
                int row = 1;
                while (rs.next()) {
                    String name = rs.getString(1);
                    String job = filterNull(rs.getString(2));
                    String hiredate = filterNull(rs.getString(3));
                    String bday = rs.getString(4);
                    String manager = filterNull(rs.getString(5));
                    String department = filterNull(rs.getString(6));
                    System.out.printf("%-4d %-20s %-20s %-20s %-20s %-20s %-20s%n",
                            row++,name,job, hiredate, bday, manager, department);
                }
            }
            printDivider();
            System.out.println();
        } catch (Exception e) {
            System.err.println("error: " + e.getClass() + "\n" + e.getMessage());
        }
    }

    private static void printAllProjects(){
        printDivider();
        try {
            ResultSet rs = controller.getAllProject();
            if (getResTotal(rs) == 0) {
                System.out.println("Error: no records found!!!");
            } else {
                System.out.println("Project listing:");
                System.out.printf("     %-20s %-20s %-20s%n",
                        "Project Name", "Status", "Department");
                int row = 1;
                while (rs.next()) {
                    String name = rs.getString(1);
                    String status = filterNull(rs.getString(2));
                    String department = filterNull(rs.getString(3));
                    System.out.printf("%-4d %-20s %-20s %-20s%n", row++,name,status, department);
                }
            }
            printDivider();
            System.out.println();
        } catch (Exception e) {
            System.err.println("error: " + e.getClass() + "\n" + e.getMessage());
        }
    }

    private static void printAllDepartments(){
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
                    System.out.printf("%-4d %-20s%n", row++,name);
                }
            }
            printDivider();
            System.out.println();
        } catch (Exception e) {
            System.err.println("error: " + e.getClass() + "\n" + e.getMessage());
        }
    }

    private static void printAllProjectEmployee(){ //prints all employees working in a project
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
                    System.out.printf("%-4d %-20s %-20s %-20s%n", row++,name, dateStarted, hourlyRate);
                }
            }
            printDivider();
            System.out.println();
        } catch (Exception e) {
            System.err.println("error: " + e.getClass() + "\n" + e.getMessage());
        }
    }

    private static void printAllEmployeeProject(){ //prints all projects an employee is working in
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
                    System.out.printf("%-4d %-20s %-20s %-20s%n", row++,project, dateStarted, hourlyRate);
                }
            }
            printDivider();
            System.out.println();
        } catch (Exception e) {
            System.err.println("error: " + e.getClass() + "\n" + e.getMessage());
        }
    }

    private static void addEmployee() {
        printDivider();
        System.out.print("Enter name: ");
        String name = kbd.nextLine();
        System.out.print("Enter job: ");
        String job = kbd.nextLine();
        System.out.print("Enter hiredate: ");
        String hiredate = kbd.nextLine();
        System.out.print("Enter bday: ");
        String bday = kbd.nextLine();
        System.out.print("Enter manager: ");
        String manager = kbd.nextLine();
        /*ResultSetMetaData rsmd;
        String mgrID = "";
        try {
            rsmd = (controller.getEmployee(manager)).getMetaData();
            mgrID = rsmd.getColumnName(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        System.out.print("Enter department: ");
        String departmnet = kbd.nextLine();
        /*String deptID = "";
        try {
            rsmd = (controller.getDepartment(departmnet)).getMetaData();
            deptID = rsmd.getColumnName(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        try {
            controller.createEmployee(name, job, hiredate, bday, manager, departmnet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(name + " was successfully added in the contact list.");
            printDivider();
            System.out.println();
    }

    private static int getResTotal(ResultSet rs) throws Exception {
        int count = 0;
        rs.last();
        count = rs.getRow();
        rs.beforeFirst();
        return count;
    }

    private static String filterNull(String str) {
        return str == null ? "" : str;
    }

    private static void printDivider() {
        for (int i = 1; i <= 120; i++) {
            System.out.print("*");
        }
        System.out.println();
    }
}
