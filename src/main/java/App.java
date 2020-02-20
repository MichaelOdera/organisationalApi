import com.google.gson.Gson;
import models.Departments;
import models.dao.Sql2oDepartmentsDao;
import models.dao.Sql2oNewsDao;
import models.dao.Sql2oUsersDao;
import org.sql2o.Connection;
import org.sql2o.Sql2o;


import exceptions.ApiException;
import spark.Spark;

import static spark.Spark.post;
import static spark.Spark.staticFileLocation;
import static spark.route.HttpMethod.get;
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


        post("/departments/new", "application/json", (req, res)->{
            Departments department = gson.fromJson(req.body(), Departments.class);
            departmentsDao.add(department);
            res.status(201);
            return gson.toJson(department);
        });

        Spark.get("/departments", "application/json", (req, res) -> {
            System.out.println(departmentsDao.getAll());

            if(departmentsDao.getAll().size() > 0){
                return gson.toJson(departmentsDao.getAll());
            }

            else {
                return "{\"message\":\"I'm sorry, but no departments are currently listed in the database.\"}";
            }

        });



    }
}