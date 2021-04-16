package models;

import org.sql2o.Sql2o;

public class DB {
    //public static Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/db_organization", "michael", "michael");
    //postgresql-vertical-96442 -> database url

    public static Sql2o sql2o = new Sql2o("jdbc:postgresql://ec2-23-22-191-232.compute-1.amazonaws.com:5432/dftis94dvivs4i?sslmode=require&ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory", "sdlmyvygvzlogv", "413cbeafac2c5a90adf9a6c2072da44c2c576de51dd492323dff7c6b382b5737");
}
