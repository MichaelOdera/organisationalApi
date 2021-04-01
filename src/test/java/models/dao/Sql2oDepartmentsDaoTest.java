package models.dao;


import models.Departments;
import models.Users;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.Arrays;

import static org.junit.Assert.*;

public class Sql2oDepartmentsDaoTest {

        private static Connection conn;
        private static Sql2oDepartmentsDao departmentsDao;
        private static Sql2oNewsDao newsDao;
        private static Sql2oUsersDao usersDao;

        @Before
        public void setUp() throws Exception {
            String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
            Sql2o sql2o = new Sql2o(connectionString, "", "");
            departmentsDao = new Sql2oDepartmentsDao(sql2o);
            newsDao = new Sql2oNewsDao(sql2o);
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
        public void savesOneInstanceCorrectlyAndGetsRightId_true(){
            Departments testDepartment = new Departments("Servicing", "Repairs", 208);
            assertEquals(0, testDepartment.getId());
        }

        @Test
        public void getsTotalSizeCorrectly_true(){
            Departments testDepartment = new Departments("Servicing", "Repairs", 208);
            departmentsDao.add(testDepartment);
            assertEquals(1, departmentsDao.getAll().size());
        }

        @Test
        public void returnsZeroIfNoInstanceOfDepartmentExists_0(){
            assertEquals(0, departmentsDao.getAll().size());
        }

        @Test
        public void findByIdReturnsCorrectDepartment(){
            Departments testDepartment = setUpDepartment();
            Departments testSecondDepartment = new Departments("visits", "ushering", 123);
            assertEquals(testDepartment, departmentsDao.findById(testDepartment.getId()));
        }

        @Test
        public void update_UpdatesCorrectlyUpdatesAllFieldsCorrectly_true(){
            Departments testDepartment = setUpDepartment();
            departmentsDao.update(testDepartment.getId(), "People", "Crowd", 125);
            Departments foundDepartment = departmentsDao.findById(testDepartment.getId());
            assertEquals("People", foundDepartment.getDepartmentName());
            assertEquals("Crowd", foundDepartment.getDepartmentDescription());
            assertEquals(125, foundDepartment.getDepartmentEmployeesNumber());
        }

        @Test
        public void deleteDepartmentByIdDeletesCorrectDepartment(){
            Departments testDepartment = setUpDepartment();
            departmentsDao.deleteById(testDepartment.getId());
            assertEquals(0, departmentsDao.getAll().size());
        }

        @Test
        public void clearAll_deletesAllTheDataPresentInTheDepartmentsTable_true(){
            Departments firstDepartment = setUpDepartment();
            Departments secondDepartment = setUpAltDepartment();
            departmentsDao.clearAll();
            assertEquals(0, departmentsDao.getAll().size());

        }

        @Test
        public void addsBothUserIdAndDepartmentIdToDB_Correctly(){
            Departments testDepartment = setUpDepartment();

            departmentsDao.add(testDepartment);
            Users testUser = setUpUser();
            Users anotherUser = setUpAltUser();

            departmentsDao.addDepartmentToUser(testDepartment, testUser);
            departmentsDao.addDepartmentToUser(testDepartment, anotherUser);

            Users[] listOfEmployees = {testUser, anotherUser};

            assertEquals(Arrays.asList(listOfEmployees), departmentsDao.getAllUsersByDepartment(testDepartment.getId()));

        }

        @Test
        public void deletingADepartmentAlsoUpdatesTheJointTable(){
            Departments testDepartment = setUpDepartment();
            departmentsDao.add(testDepartment);
            Departments altTestDepartment = setUpAltDepartment();
            departmentsDao.add(altTestDepartment);

            Users testUser = setUpUser();
            usersDao.add(testUser);

            departmentsDao.addDepartmentToUser(testDepartment, testUser);
            departmentsDao.addDepartmentToUser(altTestDepartment, testUser);

            departmentsDao.deleteById(testDepartment.getId());

            assertEquals(0, departmentsDao.getAllUsersByDepartment(testDepartment.getId()).size());
        }







        public Departments setUpDepartment (){
            Departments department =  new Departments("Servicing", "Repairs", 205);
            departmentsDao.add(department);
            return department;
        }

        public Departments setUpAltDepartment(){
            Departments altDepartment = new Departments("Shipping", "Sailing", 329);
            departmentsDao.add(altDepartment);
            return altDepartment;
        }

        public Users setUpUser(){
            Users user = new Users("Michelle", "Secretary", "Writing", 2);
            usersDao.add(user);
            return user;
        }

        public Users setUpAltUser(){
            Users altUser = new Users("Gideon","Manager", "Oversight", 2);
            usersDao.add(altUser);
            return altUser;
        }




}

