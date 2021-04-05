package models.dao;

import models.Departments;
import models.News;
import models.Users;

import java.util.ArrayList;
import java.util.List;

public interface NewsDao {

    //create
    void add(News news);
    void addNewsToDepartment(News news, Departments department);

    //read
    List<News> getAll();
    News findById(int id);
    List<News> getNewsByDepartment(int departmentId);

    //update
    News update(int id, News news);

    //delete
    void deleteById(int id);
    void clearAll();

}
