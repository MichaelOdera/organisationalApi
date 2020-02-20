import com.google.gson.Gson;
import models.Departments;
import models.News;
import models.Users;
import models.dao.Sql2oDepartmentsDao;
import models.dao.Sql2oNewsDao;
import models.dao.Sql2oUsersDao;
import org.sql2o.Connection;
import org.sql2o.Sql2o;


import exceptions.ApiException;
import spark.Spark;

import java.util.List;

import static spark.Spark.post;
import static spark.Spark.get;
import static spark.Spark.staticFileLocation;
import static spark.route.Routes.*;

public class App{
    public static void main(String[] args) {
        Sql2oDepartmentsDao departmentsDao;
        Sql2oNewsDao newsDao;
        Sql2oUsersDao usersDao;

        Connection conn;
        Gson gson = new Gson();

        staticFileLocation("/public");
        String connectionString = "jdbc:h2:~/jadle.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");

        departmentsDao = new Sql2oDepartmentsDao(sql2o);
        newsDao = new Sql2oNewsDao(sql2o);
        usersDao = new Sql2oUsersDao(sql2o);

        conn = sql2o.open();

        get("/", "application/json", (req, res) -> {
            return "{\"message\":\"Welcome to the main page of ORGANISATIONAL API.\"}";
        });


        post("/departments/new", "application/json", (req, res)->{
            Departments department = gson.fromJson(req.body(), Departments.class);
            departmentsDao.add(department);
            res.status(201);
            return gson.toJson(department);
        });

        get("/departments", "application/json", (req, res) -> {
            System.out.println(departmentsDao.getAll());

            if(departmentsDao.getAll().size() > 0){
                return gson.toJson(departmentsDao.getAll());
            }

            else {
                return "{\"message\":\"I'm sorry, but no departments are currently listed in the database.\"}";
            }

        });

        get("/departments/:id", "application/json", (req, res) -> { //accept a request in format JSON from an app
            int departmentId = Integer.parseInt(req.params("id"));
            Departments departmentToFind = departmentsDao.findById(departmentId);
            if (departmentToFind == null){
                throw new ApiException(404, String.format("No department with the id: \"%s\" exists", req.params("id")));
            }
            return gson.toJson(departmentToFind);
        });


        get("/department/:id/news", "application/json", (req, res) -> {
            int departmentId = Integer.parseInt(req.params("id"));

            Departments departmentToFind = departmentsDao.findById(departmentId);
            List<News> allNews;

            if (departmentToFind == null){
                throw new ApiException(404, String.format("No department with the id: \"%s\" exists", req.params("id")));
            }

            allNews = newsDao.getNewsByDepartment(departmentId);

            return gson.toJson(allNews);
        });



        post("/users/new", "application/json", (req, res)->{
            Users user = gson.fromJson(req.body(), Users.class);
            usersDao.add(user);
            res.status(201);
            return gson.toJson(user);
        });

        get("/users", "application/json", (req, res) -> {
            System.out.println(usersDao.getAllUsers());

            if(usersDao.getAllUsers().size() > 0){
                return gson.toJson(usersDao.getAllUsers());
            }

            else{
                return "{\"message\":\"I'm sorry, but no users are currently listed in the database.\"}";
            }
        });


        get("/users/:id", "application/json", (req, res) -> { //accept a request in format JSON from an app
            int userId = Integer.parseInt(req.params("id"));
            Users userToFind = usersDao.findUserById(userId);
            if (userToFind == null){
                throw new ApiException(404, String.format("No user with the id: \"%s\" exists", req.params("id")));
            }
            return gson.toJson(userToFind);
        });



        post("/news/new", "application/json", (req, res)->{
            News news = gson.fromJson(req.body(), News.class);
            newsDao.add(news);
            res.status(201);
            return gson.toJson(news);
        });


        get("/news", "application/json", (req, res) -> {
            System.out.println(newsDao.getAll());

            if(newsDao.getAll().size() > 0) {
               return gson.toJson(newsDao.getAll());
            }
            else{
                return "{\"message\":\"I'm sorry, but no news items are currently listed in the database.\"}";
            }

        });


        get("/news/:id", "application/json", (req, res) -> { //accept a request in format JSON from an app
            int newsId = Integer.parseInt(req.params("id"));
            News newsToFind = newsDao.findById(newsId);
            if (newsToFind == null){
                throw new ApiException(404, String.format("No news item with the id: \"%s\" exists", req.params("id")));
            }
            return gson.toJson(newsToFind);
        });




    }
}