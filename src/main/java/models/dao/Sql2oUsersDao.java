package models.dao;

import models.DB;
import models.Users;
import org.sql2o.Connection;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oUsersDao implements UsersDao {



    public Sql2oUsersDao() {
    }

    @Override
    public void add(Users user) {

        try(Connection con = DB.sql2o.open()){
            String sql = "INSERT INTO users (id, userName, userCompanyPosition, userCompanyRole, departmentId) VALUES (DEFAULT, :userName, :userCompanyPosition, :userCompanyRole, :departmentId)";
            con.createQuery(sql, true)
                    .bind(user)
                    .executeUpdate();
//                    .getKey();
//            user.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex + "Could not save data to the database");
        }

    }



    @Override
    public List<Users> getAllUsers() {
        try(Connection con = DB.sql2o.open()){
            return con.createQuery("SELECT * FROM users")
                    .executeAndFetch(Users.class);
        }
    }

    @Override
    public Users findUserById(int id) {
        try(Connection con = DB.sql2o.open()){
            return con.createQuery("SELECT * FROM users WHERE id=:id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(Users.class);
        }
    }


    @Override
    public void update(int id, String newUserName, String newUserCompanyPosition, String newUserCompanyRole, int departmentId) {
        //CHECK!!!
        try (Connection con = DB.sql2o.open()) {
            String sql = "UPDATE users SET (userName, userCompanyPosition, userCompanyRole, departmentId) = (:userName, :userCompanyPosition, :userCompanyRole, :departmentId) WHERE id=:id";
            con.createQuery(sql)
                    .addParameter("userName", newUserName)
                    .addParameter("userCompanyPosition", newUserCompanyPosition)
                    .addParameter("userCompanyRole", newUserCompanyRole)
                    .addParameter("departmentId", departmentId)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }

    }

    @Override
    public void deleteById(int id) {
        try(Connection con = DB.sql2o.open()){
            String sql = "DELETE from users WHERE id = :id";
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
        }catch (Sql2oException ex){
            System.out.println(ex);
        }

    }

    @Override
    public void clearAll() {
        try(Connection con = DB.sql2o.open()){
            String sql = "DELETE FROM users";
            con.createQuery(sql).executeUpdate();
        }catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
}
