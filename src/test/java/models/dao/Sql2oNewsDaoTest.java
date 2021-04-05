package models.dao;

import models.Departments;
import models.News;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.Arrays;

import static org.junit.Assert.*;

public class Sql2oNewsDaoTest {


    private static Connection conn;
    private static Sql2oDepartmentsDao departmentsDao;
    private static Sql2oNewsDao newsDao;
    private static Sql2oUsersDao usersDao;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        departmentsDao = new Sql2oDepartmentsDao();
        newsDao = new Sql2oNewsDao();
        usersDao = new Sql2oUsersDao();
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        departmentsDao.clearAll();
        newsDao.clearAll();
        usersDao.clearAll();
        System.out.println("clearing database");
        conn.close();
    }


    @Test
    public void returnsTheIdOfNewsInstanceCorrectly(){
        News testNews = setUpNews();
        assertEquals(1, testNews.getId());
    }

    @Test
    public void news_instantiatesCorrectly_true(){
        News testNews = setUpNews();
        assertNotNull(testNews);
    }

    @Test
    public void returnsTheCorrectTotalInstancesOfTheClass(){
        News testNews = setUpNews();
        assertEquals(1, newsDao.getAll().size());
    }

    @Test
    public void returnsZeroIfNoInstanceOfNewsExists(){
        assertEquals(0, newsDao.getAll().size());
    }

    @Test
    public void findByIdReturnsCorrectNewsInstance(){
        News testNews = setUpNews();
        newsDao.add(testNews);
        News alternateNews = setUpAltNews();
        newsDao.add(alternateNews);
        assertEquals(testNews, newsDao.findById(testNews.getId()));
    }

    @Test
    public void update_UpdatesCorrectlyUpdatesAllFieldCorrectly_true(){
        News testAltNews = setUpAltNews();
        News updatedInfoNews = new News("mango", "farming is fine", 12);
        newsDao.update(testAltNews.getId(), updatedInfoNews);
        News foundNews = newsDao.findById(testAltNews.getId());
        assertEquals("mango", foundNews.getNewsTitle());
        assertEquals("farming is fine", foundNews.getNewsContent());
        assertEquals(12, foundNews.getDepartmentId());
    }


    @Test
    public void deletesTheRightNewsByIdCorrectly(){
        News testNews = setUpNews();
        newsDao.deleteById(testNews.getId());
        assertEquals(0, newsDao.getAll().size());
    }

    @Test
    public void clearsAllNewsAfterClearAllMethodIsCalled(){
        News firstNews = setUpNews();
        News anotherNews = setUpAltNews();
        newsDao.clearAll();
        assertEquals(0, newsDao.getAll().size());
    }


    @Test
    public void addsNewsToADepartmentCorrectly(){
        News testNews = setUpNews();
        News testAltNews = setUpAltNews();

        newsDao.add(testNews);
        newsDao.add(testAltNews);
        Departments testDepartment = new Departments("HR", "Relations", 1245);


        newsDao.addNewsToDepartment(testNews, testDepartment);
        newsDao.addNewsToDepartment(testAltNews, testDepartment);

        News[] listOfNewsItems = {testNews, testAltNews};

        assertEquals(Arrays.asList(listOfNewsItems), newsDao.getNewsByDepartment(testDepartment.getId()));
    }

    @Test
    public void deletingADepartmentAlsoUpdatesTheNewDepartmentJointTable(){
        Departments testDepartment = new Departments("Finance", "Money", 1232);
        departmentsDao.add(testDepartment);
        Departments altTestDepartment = new Departments("Food", "catering", 100);
        departmentsDao.add(altTestDepartment);

        News testNews = setUpNews();
        newsDao.add(testNews);

        newsDao.addNewsToDepartment(testNews, testDepartment);
        newsDao.addNewsToDepartment(testNews, altTestDepartment);

        newsDao.deleteById(testDepartment.getId());
        assertEquals(0, newsDao.getNewsByDepartment(testDepartment.getId()).size());

    }


    public News setUpNews(){
        News testNews = new News("Money", "The finances are crazy");
        newsDao.add(testNews);
        return testNews;
    }

    public News setUpAltNews(){
        News altTestNews = new News("Terms", "Conditions", 2);
        newsDao.add(altTestNews);
        return altTestNews;
    }


}