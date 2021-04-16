package models.dao;

import models.DB;
import models.Departments;
import models.News;
import org.sql2o.Connection;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oNewsDao implements NewsDao {

    public Sql2oNewsDao() {
    }

    @Override
    public void add(News news) {
        String sql = "INSERT INTO news(id, newsTitle, newsContent, departmentId) VALUES (DEFAULT, :newsTitle, :newsContent, :departmentId)";
        try (Connection con = DB.sql2o.open()) {
            int id = (int) con.createQuery(sql, true)
                    .bind(news)
                    .executeUpdate()
                    .getKey();
            news.setId(id);

        } catch (Sql2oException ex) {
            throw new Sql2oException(ex);
        }

    }

    @Override
    public void addNewsToDepartment(News news, Departments department) {
        String sql = "INSERT INTO departments_news (departmentId, newsId) VALUES (:departmentId, :newsId)";
        try (Connection con = DB.sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("departmentId", department.getId())
                    .addParameter("newsId", news.getId())
                    .executeUpdate();
        } catch (Sql2oException ex){
            throw new Sql2oException(ex);
        }

    }

    @Override
    public List<News> getAll() {
        try (Connection con = DB.sql2o.open()) {
            return con.createQuery("SELECT * FROM news")
                    .executeAndFetch(News.class);
        }
    }

    @Override
    public News findById(int id) {
        try(Connection con = DB.sql2o.open()){
            return con.createQuery("SELECT * FROM news WHERE id=:id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(News.class);
        }
    }

    @Override
    public List<News> getNewsByDepartment(int departmentId) {
        List<News> news; //empty list
        String joinQuery = "SELECT * FROM news WHERE departmentId = :departmentId";

        try (Connection con = DB.sql2o.open()) {
            news = con.createQuery(joinQuery)
                    .addParameter("departmentId", departmentId)
                    .executeAndFetch(News.class);
        } catch (Sql2oException ex){
            throw new Sql2oException(ex);
        }
        return news;
    }

    public News update(int id, News news) {
        String sql = "UPDATE news SET (newsTitle, newsContent, departmentId) = (:newsTitle, :newsContent, :departmentId) WHERE id=:id"; //CHECK!!!
        try (Connection con = DB.sql2o.open()) {
            con.createQuery(sql)
                    .bind(news)
                    .addParameter("id", id)
                    .executeUpdate();
        } catch (Sql2oException ex) {
            throw new Sql2oException(ex);
        }

        return news;
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE from news WHERE id = :id"; //raw sql
        String deleteJoin = "DELETE from departments_news WHERE departmentId = :departmentId";
        try (Connection con = DB.sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("id", id)
                    .executeUpdate();
            con.createQuery(deleteJoin)
                    .addParameter("departmentId", id)
                    .executeUpdate();
        } catch (Sql2oException ex){
            throw new Sql2oException(ex);
        }

    }

    public void clearAll() {
        String sql = "DELETE FROM news";
        try(Connection con = DB.sql2o.open()){
            con.createQuery(sql).executeUpdate();
        }catch (Sql2oException ex) {
            throw new Sql2oException(ex);
        }

    }



}
