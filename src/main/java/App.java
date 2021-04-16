import com.google.gson.Gson;
import exceptions.ApiException;
import models.Departments;
import models.News;
import models.Users;
import models.dao.Sql2oDepartmentsDao;
import models.dao.Sql2oNewsDao;
import models.dao.Sql2oUsersDao;

import java.util.List;

import static spark.Spark.*;

public class App{
    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }
    public static void main(String[] args) {
        port(getHerokuAssignedPort());
        Sql2oDepartmentsDao departmentsDao;
        Sql2oNewsDao newsDao;
        Sql2oUsersDao usersDao;

        Gson gson = new Gson();

       // staticFileLocation("/public");
//        String connectionString = "jdbc:h2:~/jadle.db;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
//        String connectionString = "jdbc:postgresql://localhost/test?user=fred&password=secret&ssl=true";;
//        Sql2o sql2o = new Sql2o(connectionString, "", "");



        departmentsDao = new Sql2oDepartmentsDao();
        newsDao = new Sql2oNewsDao();
        usersDao = new Sql2oUsersDao();

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

        get("/departments/:id/users", "application/json", (req, res) -> {
            int departmentId = Integer.parseInt(req.params("id"));
            List<Users> usersByDepartment = departmentsDao.getAllUsersByDepartment(departmentId);
            if(usersByDepartment.isEmpty()){
                throw new ApiException(404, String.format("No users for the department with id : \"%s\" were found", req.params("id")));
            }

            return gson.toJson(usersByDepartment);
        });


        get("/departments/:id/news", "application/json", (req, res) -> {
            int departmentId = Integer.parseInt(req.params("id"));

            Departments departmentToFind = departmentsDao.findById(departmentId);
            List<News> allNews;

            if (departmentToFind == null){
                throw new ApiException(404, String.format("No department with the id: \"%s\" exists", req.params("id")));
            }

            allNews = newsDao.getNewsByDepartment(departmentId);

            if(allNews == null){
                return gson.toJson("Could not find the required news items");
            }

            return gson.toJson(allNews);
        });

        delete("/departments/:id", "application/json", (req, res) -> {
            int newsId = Integer.parseInt(req.params("id"));

            Departments confirmedDepartmentToDelete = departmentsDao.findById(newsId);
            if(confirmedDepartmentToDelete == null){
                throw new ApiException(404, String.format("No department with the id: \"%s\" exists", req.params("id")));
            }
            departmentsDao.deleteById(newsId);
            Departments deletedDepartment = departmentsDao.findById(newsId);
            if(deletedDepartment != null){
                throw new ApiException(500, String.format("Could not delete department with id: \"%s\" ", req.params("id")));
            }
            return "The Department was deleted successfully";
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

        delete("/users/:id", "application/json", (req, res) -> {
            int userId = Integer.parseInt(req.params("id"));
            Users userToDelete = usersDao.findUserById(userId);
            if(userToDelete == null){
                throw new ApiException(404, String.format("No user with id: \"%s\" exists", req.params("id")));
            }
            usersDao.deleteById(userId);
            Users userStillPresent = usersDao.findUserById(userId);
            if(userStillPresent != null){
                throw new ApiException(500, String.format("Could not delete user with id : \"%s\" from the database", req.params("id")));
            }

            return String.format("The user with id: \"%s\" was deleted successfully.", req.params("id"));
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

        put("news/:id", "application/json", (req, res) -> {
            News news = gson.fromJson(req.body(), News.class);
            int newsId = Integer.parseInt(req.params("id"));
            News UpdatedNews = newsDao.update(newsId, news);
            if (UpdatedNews == null){
                throw new ApiException(404, String.format("No news item with the id: \"%s\" exists", req.params("id")));
            }

            return gson.toJson(UpdatedNews);
        });

        delete("/news/:id", "application/json", (req, res) -> {
            int newsId = Integer.parseInt(req.params("id"));

            News confirmedNewsItemToDelete = newsDao.findById(newsId);
            if(confirmedNewsItemToDelete == null){
                throw new ApiException(404, String.format("No news item with the id: \"%s\" exists", req.params("id")));
            }
            newsDao.deleteById(newsId);
            News deletedNewsItem = newsDao.findById(newsId);
            if(deletedNewsItem != null){
                throw new ApiException(500, String.format("Could not delete news item with id: \"%s\" ", req.params("id")));
            }
            return "News item was deleted successfully";
        });

        delete("/news/delete/all", "application/json", (req, res) -> {
            newsDao.clearAll();
            List<News> allNews = newsDao.getAll();
            if(!allNews.isEmpty()){
                return "Could not delete all news items";
            }
            return "all news items were deleted successfully";
        });




    }
}