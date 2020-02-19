package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class NewsTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void instantiatesNewsCorrectly_true(){
        News newsTest = new News("Finance", "The finance department is fine", 12);
        assertTrue(newsTest instanceof News);
    }

    @Test
    public void getNewsTitle_returnsCorrectNewsTitle_Finance(){
        News testNews = setupNews();
        assertEquals("Finance", testNews.getNewsTitle());

    }

    @Test
    public void getNewsContent_returnNewsContentCorrectly_The_Finance_department_is_fine(){
        News newsTest = setupNews();
        assertEquals("The finance department is fine", newsTest.getNewsContent());
    }

    @Test
    public void getDepartmentId_returnsTheDepartmentIdCorrectly_12(){
        News newsTest = setupAltNews();
        assertEquals(12, newsTest.getDepartmentId());
    }


    @Test
    public void setNewsTitle_setsNewsTitleCorrectly_Now(){
        News newsTest = setupNews();
        newsTest.setNewsTitle("Now");
        assertEquals("Now", newsTest.getNewsTitle());
    }

    @Test
    public void setNewsContent_setNewsContentCorrectly_He_is_coming(){
        News newsTest = setupNews();
        newsTest.setNewsContent("He is coming");
        assertEquals("He is coming", newsTest.getNewsContent());
    }

    @Test
    public void setDepartmentId_setNewsDepartmentIdCorrectly_14(){
        News newsAltTest = setupAltNews();
        newsAltTest.setDepartmentId(14);
        assertEquals(14, newsAltTest.getDepartmentId());
    }

    public News setupNews (){
        return new News("Finance", "The finance department is fine");
    }

    public News setupAltNews (){
        return new News("Finance", "The finance department is doing fine", 12);
    }


}