import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBController {
    private Connection connection;
    private Statement statement;
    private PreparedStatement pStatement;
    private ResultSet resultSet;
    private String sql;

    public void dbaseConnect(String url, String user, String pass) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection(url, user, pass);
    }
/*======================================================================================================================
    Department
======================================================================================================================*/

    /**
     * Inserts new row into the table department
     * @param depid id of the department to be added
     * @param dname name of the department to be added
     * @throws SQLException Thrown when an SQLException occurs.
     */
    public void createDepartment(String depid, String dname) throws SQLException {
        sql = "insert into department values(?,?);";
        pStatement = connection.prepareStatement(sql);
        pStatement.setString(1, depid);
        pStatement.setString(2, dname);
        pStatement.executeUpdate();
    }

    /**
     * Returns all the departments present in the database
     * @return Result set of the query
     * @throws SQLException Thrown when an SQLException occurs
     */
    public ResultSet getAllDepartment() throws SQLException {
        statement = connection.createStatement();
        sql = "select * from departments";
        return statement.executeQuery(sql);
    }

    /**
     * Returns the information about department
     *
     * @param deptName the department name to be searched.
     * @throws SQLException Thrown when an SQLException occurs
     */
    public ResultSet getDepartment(String deptName) throws SQLException {
        sql = "select * from departments where dname = ?;";
        pStatement = connection.prepareStatement(sql);
        pStatement.setString(1, deptName);
        return statement.executeQuery(sql);
    }

    /**
     * Returns the information about some departments
     *
     * @return result set of the query
     * @param deptName the department name to be searched
     * @throws SQLException Thrown when an SQLException occurs
     */
    public ResultSet searchDepartment(String deptName) throws SQLException {
        sql = "select * from departments where dname like ?;";
        pStatement = connection.prepareStatement(sql);
        pStatement.setString(1, deptName);
        return statement.executeQuery(sql);
    }

    /**
     * Deletes a row from the department table.
     * @param deptName The name of the department to be deleted
     * @throws SQLException
     */
    public void deleteDepartment(String deptName) throws SQLException {
        sql = "delete from department where deptName = ?";
        pStatement = connection.prepareStatement(sql);
        pStatement.setString(1, deptName);
        pStatement.executeUpdate();
    }

    /**
     * Updates a particular department
     * @param deptname The name of the department to be updated
     * @param col The attribute of the department to be updated
     * @param replacement The replacement of the attribute
     * @throws SQLException Thrown when an exception occurs
     */
    public void updateDepartment(String deptname, String col, String replacement) throws SQLException {
        sql = "update department set " + col + " = ? where deptName = ?";
        pStatement = connection.prepareStatement(sql);
        pStatement.setString(1, replacement);
        pStatement.setString(2, deptname);
        pStatement.executeUpdate();
    }

    /**
     * Updates a particular department
     * @param deptname name of the department to be updated
     * @param col attribute of the department to be updated
     * @param row department to be updated
     * @param replacement replacement of the attribute
     * @throws SQLException Thrown when an exception occurs
     */
    public void updateDepartment(String deptname, String col, int row, String replacement) throws SQLException {
        sql = "select * from department where deptname = ?";
        pStatement = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        pStatement.setString(1, deptname);
        resultSet.updateString(col, replacement);
        resultSet = pStatement.executeQuery();
        resultSet.absolute(row);
        resultSet.updateRow();
    }

/*======================================================================================================================
    Employee
======================================================================================================================*/

    /**
     *  Inserts a row into employee table
     * @param empid employee id of the employee
     * @param ename name of the employee
     * @param job job of the employee
     * @param hireDate date when the employee was hired
     * @param bDay birthday of the employee
     * @param mgrid manager of the employee
     * @param deptid department in which the employee belongs to
     * @throws SQLException Thrown when an exception occurs
     */
    public void createEmployee(String empid, String ename, String job,
                               String hireDate, String bDay, String mgrid, String deptid) throws SQLException {
        sql = "insert into employee values(?,?,?,?,?,?,?);";
        pStatement = connection.prepareStatement(sql);
        pStatement.setString(1, empid);
        pStatement.setString(2, ename);
        pStatement.setString(3, job);
        pStatement.setString(4, hireDate);
        pStatement.setString(5, bDay);
        pStatement.setString(6, mgrid);
        pStatement.setString(7, deptid);
        pStatement.executeUpdate();
    }

    /**
     * Returns all of the employees from employee table
     * @return result set of the query
     * @throws SQLException Thrown when an exception occurs
     */
    public ResultSet getAllEmployee() throws SQLException{
        statement = connection.createStatement();
        sql = "select a.empid, ename, job, hiredate,bday, b.empid, deptid from employee a left join employee b " +
                "on mgrid = b.empid;";
        return statement.executeQuery(sql);
    }

    /**
     * Returns the information about a particular employee
     * @param ename name of the employee to be searched
     * @return returns the a resultset about a particular employee
     * @throws SQLException Thrown when an exception occurs
     */
    public ResultSet getEmployee(String ename) throws SQLException{
        sql = "select a.empid, ename, job, hiredate,bday, b.empid, deptid from employee a left join employee b " +
                "on mgrid = b.empid where ename = ?;";
        pStatement = connection.prepareStatement(sql);
        pStatement.setString(1, ename);
        return statement.executeQuery(sql);
    }

    /**
     * Searches for a particular employee
     * @param ename name of the employee to be searched
     * @return a result set about the employees
     * @throws SQLException Thrown when an exception occurs
     */
    public ResultSet searchEmployee(String ename) throws SQLException{
        sql = "select a.empid, ename, job, hiredate,bday, b.empid, deptid from employee a left join employee b " +
                "on mgrid = b.empid where ename like ?;";
        pStatement = connection.prepareStatement(sql);
        pStatement.setString(1, ename);
        return statement.executeQuery(sql);
    }

    /**
     * Deletes a paticular employee from the table employee
     * @param eName name of the employee to be deleted
     * @throws SQLException Thrown when an exception occurs
     */
    public void deleteEmployee(String eName) throws SQLException {
        sql = "delete from employee where ename = ?";
        pStatement = connection.prepareStatement(sql);
        pStatement.setString(1, eName);
        pStatement.executeUpdate();
    }

    /**
     * Updates an information about an employee
     * @param ename name of the employee to be updated
     * @param col attribute to be updated
     * @param replacement replacement of the attribute to be updated
     * @throws SQLException Thrown when an exception occurs
     */
    public void updateEmployee(String ename, String col, String replacement) throws SQLException {
        sql = "update employee set " + col + " = ? where ename = ?";
        pStatement = connection.prepareStatement(sql);
        pStatement.setString(1, replacement);
        pStatement.setString(2, ename);
        pStatement.executeUpdate();
    }

    /**
     * Updates an information about an employee
     * @param ename name of the employee to be updated
     * @param col attribute of the employee to be updated
     * @param row employee to be updated
     * @param replacement replacement of the attribute to be updated
     * @throws SQLException Thrown when an exception occurs
     */
    public void updateEmployee(String ename, String col, int row, String replacement) throws SQLException {
        sql = "select * from employee where ename = ?";
        pStatement = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        pStatement.setString(1, ename);
        resultSet.updateString(col, replacement);
        resultSet = pStatement.executeQuery();
        resultSet.absolute(row);
        resultSet.updateRow();
    }

/*======================================================================================================================
    EmpProj
======================================================================================================================*/

    /**
     * Inserts a new row into the table emoproj
     *
     * @param date date started
     * @param hourlyRate hourly rate for the project
     * @param empid id of an employee working on a project
     * @param projid project in which the employee is currently working on
     * @throws SQLException Thrown when an exception occurs
     */
    public void createEmployeeProject(String date, double hourlyRate, String empid, String projid) throws SQLException {
        sql = "insert into empproj values(?,?,?,?);";
        pStatement = connection.prepareStatement(sql);
        pStatement.setString(1, date);
        pStatement.setDouble(2, hourlyRate);
        pStatement.setString(3, empid);
        pStatement.setString(4, projid);
        pStatement.executeUpdate();
    }

    /**
     * Returns all the employees  with the corresponding projects they are currently working on.
     * @return a resultset of all the employees from the employee table
     * @throws SQLException Thrown when an exception occurs
     */
    public ResultSet getAllEmployeeProject() throws SQLException{
        statement = connection.createStatement();
        sql = "select ename 'Name', projectName 'Project', date, hourlyRate from empproj natural join employee " +
                "natural join project;";
        return statement.executeQuery(sql);
    }

    /**
     * Retruns an employee and the project/projects he/she is currently working on.
     * @param name name of an employee
     * @return a resultset of all the projects an employee is currently working on
     * @throws SQLException Thrown when an exception occurs
     */
    public ResultSet getEmployeeProject(String name) throws SQLException{
        statement = connection.createStatement();
        sql = "select ename 'Name', projectName 'Project', date, hourlyRate from empproj natural join employee " +
                "natural join project where ename = ?;";
        pStatement = connection.prepareStatement(sql);
        pStatement.setString(1, name);
        return statement.executeQuery(sql);
    }

    /**
     * Retruns an employee and the project/projects he/she is currently working on.
     * @param projid project to be searched
     * @return a result set of a project and all of the employees currently working on it
     * @throws SQLException Thrown when an exception occurs
     */
    public ResultSet getProjectEmployee(String projid) throws SQLException{
        statement = connection.createStatement();
        sql = "select  projectName 'Project', ename 'Name', date, hourlyRate from empproj natural join employee " +
                "natural join project where projid = ?;";
        pStatement = connection.prepareStatement(sql);
        pStatement.setString(1, projid);
        return statement.executeQuery(sql);
    }

/*======================================================================================================================
    Project
======================================================================================================================*/

    /**
     * Inserts a new row into the table prject.
     *
     * @param projid project id of the project to be added into table
     * @param projname name of the project
     * @param status status of the project
     * @param deptid department in which the project belongs to
     * @throws SQLException Thrown when an exception occurs
     */
    public void createProject(String projid, String projname, String status, String deptid) throws SQLException {
        sql = "insert into employee values(?,?,?,?);";
        pStatement = connection.prepareStatement(sql);
        pStatement.setString(1, projid);
        pStatement.setString(2, projname);
        pStatement.setString(3, status);
        pStatement.setString(4, deptid);
        pStatement.executeUpdate();
    }

    /**
     * Returns all the prjects in the project table
     * @return a result sset of all the projects
     * @throws SQLException Thrown when an exception occurs
     */
    public ResultSet getAllProject() throws SQLException{
        statement = connection.createStatement();
        sql = "select projid, projname, status, dname from project join department on dept = deptid;";
        return statement.executeQuery(sql);
    }

    /**
     * Returns the information about a project
     * @param projname name of the project to be searched
     * @return a result set of a project
     * @throws SQLException Thrown when an exception occurs
     */
    public ResultSet getProject(String projname) throws SQLException{
        statement = connection.createStatement();
        sql = "select projid, projname, status, dname from project join department " +
                "on dept = deptid where projname = ?;";
        pStatement = connection.prepareStatement(sql);
        pStatement.setString(1, projname);
        return statement.executeQuery(sql);
    }

    /**
     * Returns the information about some projects
     * @param projname name of the project to be searched
     * @return a result set of some projects
     * @throws SQLException Thrown when an exception occurs
     */
    public ResultSet searchtProject(String projname) throws SQLException{
        statement = connection.createStatement();
        sql = "select projid, projname, status, dname from project join department " +
                "on dept = deptid where projname like ?;";
        pStatement = connection.prepareStatement(sql);
        pStatement.setString(1, projname);
        return statement.executeQuery(sql);
    }

    /**
     * Deletes a particular project from the project table
     * @param projName name of the project to be deleted
     * @throws SQLException Thrown when an exception occurs
     */
    public void deleteProject(String projName) throws SQLException {
        sql = "delete from project where projname = ?";
        pStatement = connection.prepareStatement(sql);
        pStatement.setString(1, projName);
        pStatement.executeUpdate();
    }

    /**
     * Updates the information of a project
     * @param projname name of the project to be updated
     * @param col attribute to be updated
     * @param replacement replacement for the attribute
     * @throws SQLException Thrown when an exception occurs
     */
    public void updateProject(String projname, String col, String replacement) throws SQLException {
        sql = "update project set " + col + " = ? where projname = ?";
        pStatement = connection.prepareStatement(sql);
        pStatement.setString(1, replacement);
        pStatement.setString(2, projname);
        pStatement.executeUpdate();
    }

    /**
     * Updates a project
     * @param projname name of the project to be updated
     * @param col attribute to be updated
     * @param row the project to be updated
     * @param replacement replacement for the attribute to be updated
     * @throws SQLException Thrown when an exception occurs
     */
    public void updateProject(String projname, String col, int row, String replacement) throws SQLException {
        sql = "select * from project where projname = ?";
        pStatement = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        pStatement.setString(1, projname);
        resultSet.updateString(col, replacement);
        resultSet = pStatement.executeQuery();
        resultSet.absolute(row);
        resultSet.updateRow();
    }

//======================================================================================================================

    public void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (pStatement != null) {
                pStatement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {

        }
    }
}