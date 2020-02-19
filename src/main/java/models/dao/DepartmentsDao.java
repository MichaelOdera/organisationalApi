package models.dao;

import models.Departments;
import models.Users;

import java.util.List;

public interface DepartmentsDao {

    //create
    void add(Departments department);
    void addDepartmentToUser(Departments department, Users user);

    //read
    List<Departments> getAll();
    Departments findById(int id);
    List<Users> getAllUsersByDepartment(int departmentId);

    //update
    void update(int id, String departmentName, String departmentDescription, int departmentEmployeesNumber);

    //delete
    void deleteById(int id);
    void clearAll();
}
