package models.dao;

import models.DB;
import models.Departments;
import models.Users;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.List;

public class Sql2oDepartmentsDao implements DepartmentsDao {

    public Sql2oDepartmentsDao() {

    }

    @Override
    public void add(Departments department) {
            String sql = "INSERT INTO departments(id, departmentName, departmentDescription, departmentEmployeesNumber) VALUES (DEFAULT, :departmentName, :departmentDescription, :departmentEmployeesNumber)";
            try (Connection con = DB.sql2o.open()) {
                int id = (int) con.createQuery(sql, true)
                        .bind(department)
                        .executeUpdate()
                        .getKey();
                department.setId(id);

            } catch (Sql2oException ex) {
                System.out.println(ex);
            }

    }



    @Override
    public List<Departments> getAll() {
        try (Connection con = DB.sql2o.open()) {
            return con.createQuery("SELECT * FROM departments")
                    .executeAndFetch(Departments.class);
        }
    }


    @Override
    public void addDepartmentToUser(Departments department, Users user) {
        String sql = "INSERT INTO departments_users (departmentId, userId) VALUES (:departmentId, :userId)";
        try (Connection con = DB.sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("departmentId", department.getId())
                    .addParameter("userId", user.getId())
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
    }


    @Override
    public Departments findById(int id) {
        try (Connection con = DB.sql2o.open()) {
            return con.createQuery("SELECT * FROM departments WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Departments.class);
        }
    }


    @Override
    public List<Users> getAllUsersByDepartment(int departmentId) {
        List<Users> users = new ArrayList<>(); //empty list
        String joinQuery = "SELECT * FROM users WHERE departmentId = :departmentId";

        try (Connection con = DB.sql2o.open()) {
            users = con.createQuery(joinQuery)
                    .addParameter("departmentId", departmentId)
                    .executeAndFetch(Users.class);
        } catch (Sql2oException ex){
            System.out.println(ex);
        }
        return users;
    }

    @Override
    public void update(int id, String newDepartmentName, String newDepartmentDescription, int newDepartmentEmployeesNumber) {
        String sql = "UPDATE departments SET (departmentName, departmentDescription, departmentEmployeesNumber) = (:departmentName, :departmentDescription, :departmentEmployeesNumber) WHERE id=:id"; //CHECK!!!
        try (Connection con = DB.sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("departmentName", newDepartmentName)
                    .addParameter("departmentDescription", newDepartmentDescription)
                    .addParameter("departmentEmployeesNumber", newDepartmentEmployeesNumber)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }

    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from departments WHERE id = :id"; //raw sql
        String deleteJoin = "DELETE from departments_users WHERE departmentId = :departmentId";
        try (Connection con = DB.sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
            con.createQuery(deleteJoin)
                    .addParameter("departmentId", id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            System.out.println(ex);
        }

    }

    @Override
    public void clearAll() {
        String sql = "DELETE FROM departments";
        try (Connection con = DB.sql2o.open()) {
            con.createQuery(sql).executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }

    }
}
