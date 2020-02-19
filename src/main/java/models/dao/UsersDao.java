package models.dao;

import models.Departments;
import models.Users;

import java.util.List;

public interface UsersDao {

    //create
    void add (Users user);

    //read
    List<Users> getAllUsers();
    Users findUserById(int id);

    //update
    void update(int id, String userName,  String userCompanyPosition, String useCompanyRole, int departmentId);

    //delete
    void deleteById(int id);
    void clearAll();
}
