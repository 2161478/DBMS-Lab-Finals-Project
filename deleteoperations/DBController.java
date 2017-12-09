package group.one;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBController {
    private Connection connection;
    private Statement statement;
    private PreparedStatement ps;
    private ResultSet resultSet;
    private String sql;

    public void dbaseConnect(String url, String user, String pass) throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection(url, user, pass);
    }

    // ================================= employee ===============================
    public ResultSet searchEmployeeInfo(String name) throws Exception {
        sql = "select * from employee where empid like ?;";
        ps = connection.prepareStatement(sql);
        ps.setString(1, "%" + name + "%");
        return ps.executeQuery();
    }

    public void deleteEmployee(String name) throws Exception {
        sql = "delete from employee where empid = ?";
        ps = connection.prepareStatement(sql);
        ps.setString(1, name);
        ps.executeUpdate();
    }

    // ================================= department ===============================
    public ResultSet searchDepartmentInfo(String name) throws Exception {
        sql = "select * from department where deptid like ?;";
        ps = connection.prepareStatement(sql);
        ps.setString(1, "%" + name + "%");
        return ps.executeQuery();
    }

    public void deleteDepartment(String name) throws Exception {
        sql = "delete from department where deptid = ?";
        ps = connection.prepareStatement(sql);
        ps.setString(1, name);
        ps.executeUpdate();
    }

    // ================================= project ===============================

    public ResultSet searchProjectInfo(String name) throws Exception {
        sql = "select * from project where projid like ?;";
        ps = connection.prepareStatement(sql);
        ps.setString(1, "%" + name + "%");
        return ps.executeQuery();
    }

    public void deleteProject(String name) throws Exception {
        sql = "delete from project where projid = ?";
        ps = connection.prepareStatement(sql);
        ps.setString(1, name);
        ps.executeUpdate();
    }

    // ================================= employee- project ===============================

    public ResultSet searchEmpProjInfo(String name1, String name2) throws Exception {
        sql = "select * from empproj where empid like ? and projid like ? ;";
        ps = connection.prepareStatement(sql);
        ps.setString(1, "%" + name1 + "%");
        ps.setString(2, "%" + name2 + "%");
        return ps.executeQuery();
    }

    public void deleteEmpProj(String name1, String name2) throws Exception {
        sql = "delete from empproj where empid = ? and projid = ?";
        ps = connection.prepareStatement(sql);
        ps.setString(1, name1);
        ps.setString(2, name2);
        ps.executeUpdate();
    }

    public void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {

        }
    }

}
