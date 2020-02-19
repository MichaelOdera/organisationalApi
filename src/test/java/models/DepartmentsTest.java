package models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DepartmentsTest {


    @Test
    public void departmentInstantiatesCorrectly_true(){
        Departments department = new Departments("Human Resource", "Management", 200);
        assertTrue(department instanceof Departments);
    }


    @Test
    public void getDepartmentName_returnsDepartmentNameCorrectly() {
        Departments department = new Departments("Human Resource", "Management", 200);
        assertEquals("Human Resource", department.getDepartmentName());

    }


    @Test
    public void getDepartmentEmployeeNumber_returnsEmployeeCorrectly() {
        Departments department = new Departments("Human Resource", "Management", 200);
        assertEquals("Management", department.getDepartmentDescription());
    }


    @Test
    public void getDepartDescription_returnsDepartmentDescriptionCorrectly() {
        Departments department = new Departments("Human Resource", "Management", 200);
        assertEquals(200, department.getDepartmentEmployeesNumber());
    }

    @Test
    public void setDepartmentName_setUpsDepartmentNameCorrectly_Managerial(){
        Departments department = new Departments("Human Resource", "Management", 200);
        department.setDepartmentName("Managerial");
        assertEquals("Managerial", department.getDepartmentName());
    }

    @Test
    public void setDescription_setsUpDepartmentDescriptionCorrectly_Leadership(){
        Departments department = new Departments("Human Resource", "Management", 200);
        department.setDepartmentDescription("Leadership");
        assertEquals("Leadership", department.getDepartmentDescription());
    }

    @Test
    public void setEmployeeNumber_setsUpEmployeeNumberCorrectly_78(){
        Departments department = new Departments("Human Resource", "Management", 200);
        department.setDepartmentEmployeesNumber(78);
        assertEquals(78, department.getDepartmentEmployeesNumber());
    }



}