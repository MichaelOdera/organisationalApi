package models.dao;

import models.Users;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.*;

public class Sql2oUsersDaoTest {

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
    public void instantiatesCorrectly(){
        Users testUser = setUpUser();
        assertEquals(true, testUser instanceof Users);
    }

    @Test
    public void returnsTheRightId(){
        Users testUser = setUpUser();
        assertEquals(1, testUser.getId());
    }

    @Test
    public void returnsCorrectSizeAfterInstantiation(){
        Users testUser = setUpUser();
        assertEquals(1, usersDao.getAllUsers().size());
    }

    @Test
    public void returnNothingWhenThereIsNoInstance(){
        assertEquals(0, usersDao.getAllUsers().size());
    }


    @Test
    public void findByIdReturnsCorrectInstanceOfClass(){
        Users testUsers = setUpUser();
        Users testAltUser = setUpAltUser();
        assertEquals(testAltUser, usersDao.findUserById(testAltUser.getId()));
    }

    @Test
    public void updatesCorrectlyUpdatesAllFieldsCorrectly(){
        Users testUser = setUpUser();
        usersDao.update(testUser.getId(), "Hampton", "Banker", "Teller", 20);
        Users findUser = usersDao.findUserById(testUser.getId());
        assertEquals("Hampton", findUser.getUserName());
        assertEquals("Banker", findUser.getUserCompanyPosition());
        assertEquals("Teller", findUser.getUserCompanyRole());
        assertEquals(20, findUser.getDepartmentId());
    }


    @Test
    public void deletesUserByIdCorrectly(){
        Users testUser = setUpUser();
        usersDao.deleteById(testUser.getId());
        assertEquals(0, usersDao.getAllUsers().size());
    }

    @Test
    public void clearAll_clearsAllInstancesFromTheDatabaseCorrectly_true(){
        Users testUser = setUpUser();
        Users testAltUser = setUpAltUser();
        usersDao.clearAll();
        assertEquals(0, usersDao.getAllUsers().size());
    }


    public Users setUpUser(){
        Users testUser = new Users("Millie", "Secretary", "Writer", 12);
        usersDao.add(testUser);
        return testUser;
    }

    public Users setUpAltUser(){
        Users testAltUser = new Users("Mildred", "Janitor", "Recording", 8);
        usersDao.add(testAltUser);
        return testAltUser;
    }

}