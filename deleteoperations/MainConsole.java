package group.one;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.mysql.jdbc.StringUtils;

public class MainConsole {
    private static DBController controller;
    private static Scanner kb = new Scanner(System.in);

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
            case 1: // Enter contact person
                deleteEmployee();
                break;
            case 2: // delete a department
                deleteDepartment();
                break;
            case 3: // delete a project
                deleteProject();
                break;
            case 4: // delete a record of employee working on a project
                deleteEmpProj();
                break;
            case 5: // exit
                System.exit(0);
                break;
        }
    }

    private static int showMenu() {
        int choice = 0;
        do {
            System.out.println("+---------------------------------+");
            System.out.println("|      M E N U  O P T I O N S     |");
            System.out.println("+---------------------------------+");
            System.out.println("| 1. Delete an Employee           |");
            System.out.println("| 2. Delete a Department          |");
            System.out.println("| 3. Delete a Project             |");
            System.out.println("| 4. Delete a record of empproj   |");
            System.out.println("| 5. Exit program                 |");
            System.out.println("+---------------------------------+");
            System.out.print("Enter your choice: ");
            try {
                choice = Integer.parseInt(kb.nextLine());
            } catch (Exception e) {
                System.out.println("error: input a valid value...");
                System.out.print("Press enter key to continue...");
                kb.nextLine();
            }
            System.out.println();
        } while (choice < 1 || choice > 5);
        return choice;
    }


    // ================================= employee =======================================
    private static void deleteEmployee() {
        printDivider();
        try {
            String name = inputID("Enter the exact ID of the employee to be deleted: ");
            ResultSet rs = controller.searchEmployeeInfo(name);
            if (getResTotal(rs) == 0) {
                System.out.println("error: ID not found. Please enter an existing employee id.");
                deleteEmployee();
            }
            controller.deleteEmployee(name);
            System.out.println(name + " was successfully deleted from the employee list.");
            printDivider();
            System.out.println();
        } catch (Exception e) {
            System.err.println("error: " + e.getClass() + "\n" + e.getMessage());
        }
    }


    // ================================= department =======================================
    private static void deleteDepartment() {
        printDivider();
        try {
            String name = inputID("Enter the exact ID of the department to be deleted: ");
            ResultSet rs = controller.searchDepartmentInfo(name);
            if (getResTotal(rs) == 0) {
                System.out.println("error: ID not found. Please enter an existing department id.");
                deleteDepartment();
            }
            controller.deleteDepartment(name);
            System.out.println(name + " was successfully deleted from the department list.");
            printDivider();
            System.out.println();
        } catch (Exception e) {
            System.err.println("error: " + e.getClass() + "\n" + e.getMessage());
        }
    }


    // ================================= project =======================================
    private static void deleteProject() {
        printDivider();
        try {
            String name = inputID("Enter the exact ID of the project to be deleted: ");
            ResultSet rs = controller.searchProjectInfo(name);
            if (getResTotal(rs) == 0) {
                System.out.println("error: ID not found. Please enter an existing project id.");
                deleteProject();
            }
            controller.deleteProject(name);
            System.out.println(name + " was successfully deleted from the project list.");
            printDivider();
            System.out.println();
        } catch (Exception e) {
            System.err.println("error: " + e.getClass() + "\n" + e.getMessage());
        }
    }

    // ================================= employee - project =======================================
    private static void deleteEmpProj() {
        printDivider();
        try {
            System.out.println("Enter the necessary information of record.");
            String name1 = inputID("Enter the exact ID of the employee: ");
            String name2 = inputID("Enter the exact ID of the project: ");
            ResultSet rs = controller.searchEmpProjInfo(name1, name2);
            if (getResTotal(rs) == 0) {
                System.out.println("error: Record not found. Please enter an existing record");
                deleteEmpProj();
            }
            controller.deleteEmpProj(name1, name2);
            System.out.println("The record of employee with Id " + name1 + " who has a project with Id " + name2 + " was successfully deleted from the record.");
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


    private static void printDivider() {
        for (int i = 1; i <= 90; i++) {
            System.out.print("*");
        }
        System.out.println();
    }

    private static String inputID(String prompt) {
        String input = null;
        boolean valid;
        do {
            System.out.print(prompt);
            input = kb.nextLine();
            valid = StringUtils.isStrictlyNumeric(input);
        } while (!valid);
        return input;
    }

}

