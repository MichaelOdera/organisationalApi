package models;

import org.h2.engine.User;
import org.junit.Test;

import static org.junit.Assert.*;

public class UsersTest {


    @Test
    public void instantiatesCorrectly_true(){
        Users userTest = new Users("Gregory", "Secretary", "Reporting", 12);
        assertTrue(userTest instanceof Users);
    }

    @Test
    public void getUserName_returnsUserNameCorrectly_Gregory(){
        Users userTest = new Users("Gregory", "Secretary", "Reporting", 12);
        assertEquals("Gregory",userTest.getUserName());
    }

    @Test
    public void getUserCompanyPosition_returnsCorrectlyUserPosition_Secretary(){
        Users userTest = new Users("Gregory", "Secretary", "Reporting", 12);
        assertEquals("Secretary", userTest.getUserCompanyPosition());
    }

    @Test
    public void getUserCompanyRole_returnsCorrectlyUserRole_Reporting(){
        Users userTest = new Users("Gregory", "Secretary", "Reporting", 12);
        assertEquals("Reporting", userTest.getUserCompanyRole());
    }

    @Test
    public void getDepartmentId_returnsCorrectlyDepartmentIdOfUser_12(){
        Users userTest = new Users("Gregory", "Secretary", "Reporting", 12);
        assertEquals(12, userTest.getDepartmentId());
    }

    @Test
    public void setUserName_setsUpUserNameCorrectly_pointer(){
        Users userTest = new Users("Gregory", "Secretary", "Reporting", 12);
        userTest.setUserName("Pointer");
        assertEquals("Pointer", userTest.getUserName());
    }

    @Test
    public void setCompanyPosition_theUserCompanyPositionSetsUpCorrectly_Writer(){
        Users userTest = new Users("Gregory", "Secretary", "Reporting", 12);
        userTest.setUserCompanyPosition("Writer");
        assertEquals("Writer", userTest.getUserCompanyPosition());
    }

    @Test
    public void setsCompanyRole_setsUpUserCompanyRoleCorrectly_Writing(){
        Users userTest = new Users("Gregory", "Secretary", "Reporting", 12);
        userTest.setUserCompanyRole("Writing");
        assertEquals("Writing", userTest.getUserCompanyRole());
    }

    @Test
    public void setDepartmentId_setsUpUserCompanyDepartmentIdCorrectly_14(){
        Users userTest = new Users("Gregory", "Secretary", "Reporting", 12);
        userTest.setDepartmentId(14);
        assertEquals(14, userTest.getDepartmentId());
    }



}