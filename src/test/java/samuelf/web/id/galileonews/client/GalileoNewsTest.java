package samuelf.web.id.galileonews.client;

/*
 * Copyright 2015 Samuel Franklyn <sfranklyn@gmail.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import galileonews.api.Msg;
import galileonews.api.NewsInput;
import galileonews.api.NewsOutput;
import java.util.Date;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import static javax.ws.rs.core.MediaType.APPLICATION_XML_TYPE;
import javax.ws.rs.core.Response;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Samuel Franklyn <sfranklyn@gmail.com>
 */
public class GalileoNewsTest {

    private static Client client;
    private NewsInput news;

    public GalileoNewsTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        client = ClientBuilder.newClient();
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        news = new NewsInput();
    }

    @After
    public void tearDown() {
    }

    @Test
    public void logInTest1() {
        Entity<NewsInput> newsInput = Entity.entity(news, APPLICATION_XML_TYPE);
        Response response = client.target("http://localhost:8080/galileonews/news/")
                .request().post(newsInput, Response.class);
        NewsOutput newsOutput = response.readEntity(NewsOutput.class);
        assertTrue(newsOutput.getErrorList().getError().size() == 2);
        assertEquals("User Name required", newsOutput.getErrorList().getError().get(0));
        assertEquals("Password required", newsOutput.getErrorList().getError().get(1));
    }

    @Test
    public void logInTest2() {
        news.setUserName("user3");
        news.setPassword("user3");
        Entity<NewsInput> newsInput = Entity.entity(news, APPLICATION_XML_TYPE);
        Response response = client.target("http://localhost:8080/galileonews/news/")
                .request().post(newsInput, Response.class);
        NewsOutput newsOutput = response.readEntity(NewsOutput.class);
        assertTrue(newsOutput.getErrorList().getError().size() == 1);
        assertEquals("User Name not registered", newsOutput.getErrorList().getError().get(0));
    }

    @Test
    public void logInTest3() {
        news.setUserName("user1");
        news.setPassword("user3");
        Entity<NewsInput> newsInput = Entity.entity(news, APPLICATION_XML_TYPE);
        Response response = client.target("http://localhost:8080/galileonews/news/")
                .request().post(newsInput, Response.class);
        NewsOutput newsOutput = response.readEntity(NewsOutput.class);
        assertTrue(newsOutput.getErrorList().getError().size() == 1);
        assertEquals("User Password not match", newsOutput.getErrorList().getError().get(0));
    }

    @Test
    public void newsTest1() {
        news.setUserName("user1");
        news.setPassword("user1");
        news.setImportant(Boolean.TRUE);
        news.setAscending(Boolean.TRUE);
        news.setToday(new Date());
        Entity<NewsInput> newsInput = Entity.entity(news, APPLICATION_XML_TYPE);
        Response response = client.target("http://localhost:8080/galileonews/news/")
                .request().post(newsInput, Response.class);
        NewsOutput newsOutput = response.readEntity(NewsOutput.class);
        assertTrue(newsOutput != null);
        assertTrue(newsOutput.getMsg().size() == 3);
        assertTrue(newsOutput.getMsg().get(0).getId().longValue() == 7);
        assertTrue(newsOutput.getMsg().get(1).getId().longValue() == 8);
        assertTrue(newsOutput.getMsg().get(2).getId().longValue() == 3);
        for (Msg msg : newsOutput.getMsg()) {
            System.out.println("id:".concat(msg.getId().toString()));
            for (String line : msg.getLine()) {
                System.out.println("line:".concat(line));
            }
            System.out.println();
        }
    }

    @Test
    public void newsTest2() {
        news.setUserName("user1");
        news.setPassword("user1");
        news.setImportant(Boolean.FALSE);
        news.setAscending(Boolean.TRUE);
        news.setToday(new Date());
        Entity<NewsInput> newsInput = Entity.entity(news, APPLICATION_XML_TYPE);
        Response response = client.target("http://localhost:8080/galileonews/news/")
                .request().post(newsInput, Response.class);
        NewsOutput newsOutput = response.readEntity(NewsOutput.class);
        assertTrue(newsOutput != null);
        assertTrue(newsOutput.getMsg().size() == 2);
        assertTrue(newsOutput.getMsg().get(0).getId().longValue() == 1);
        assertTrue(newsOutput.getMsg().get(1).getId().longValue() == 2);
        for (Msg msg : newsOutput.getMsg()) {
            System.out.println("id:".concat(msg.getId().toString()));
            for (String line : msg.getLine()) {
                System.out.println("line:".concat(line));
            }
            System.out.println();
        }
    }

    @Test
    public void newsTest3() {
        news.setUserName("user1");
        news.setPassword("user1");
        news.setImportant(Boolean.TRUE);
        news.setAscending(Boolean.TRUE);
        DateTime today = new DateTime();
        news.setToday(today.plusDays(2).toDate());
        Entity<NewsInput> newsInput = Entity.entity(news, APPLICATION_XML_TYPE);
        Response response = client.target("http://localhost:8080/galileonews/news/")
                .request().post(newsInput, Response.class);
        NewsOutput newsOutput = response.readEntity(NewsOutput.class);
        assertTrue(newsOutput != null);
        assertTrue(newsOutput.getMsg().size() == 3);
        assertTrue(newsOutput.getMsg().get(0).getId().longValue() == 7);
        assertTrue(newsOutput.getMsg().get(1).getId().longValue() == 8);
        assertTrue(newsOutput.getMsg().get(2).getId().longValue() == 6);
        for (Msg msg : newsOutput.getMsg()) {
            System.out.println("id:".concat(msg.getId().toString()));
            for (String line : msg.getLine()) {
                System.out.println("line:".concat(line));
            }
            System.out.println();
        }
    }

    @Test
    public void newsTest4() {
        news.setUserName("user1");
        news.setPassword("user1");
        news.setImportant(Boolean.FALSE);
        news.setAscending(Boolean.TRUE);
        DateTime today = new DateTime();
        news.setToday(today.plusDays(1).toDate());
        Entity<NewsInput> newsInput = Entity.entity(news, APPLICATION_XML_TYPE);
        Response response = client.target("http://localhost:8080/galileonews/news/")
                .request().post(newsInput, Response.class);
        NewsOutput newsOutput = response.readEntity(NewsOutput.class);
        assertTrue(newsOutput != null);
        assertTrue(newsOutput.getMsg().size() == 4);
        assertTrue(newsOutput.getMsg().get(0).getId().longValue() == 1);
        assertTrue(newsOutput.getMsg().get(1).getId().longValue() == 2);
        assertTrue(newsOutput.getMsg().get(2).getId().longValue() == 4);
        assertTrue(newsOutput.getMsg().get(3).getId().longValue() == 5);
        for (Msg msg : newsOutput.getMsg()) {
            System.out.println("id:".concat(msg.getId().toString()));
            for (String line : msg.getLine()) {
                System.out.println("line:".concat(line));
            }
            System.out.println();
        }
    }

    @Test
    public void newsTest5() {
        news.setUserName("user1");
        news.setPassword("user1");
        news.setImportant(Boolean.FALSE);
        DateTime today = new DateTime();
        news.setToday(today.plusDays(1).toDate());
        news.setAscending(Boolean.FALSE);
        Entity<NewsInput> newsInput = Entity.entity(news, APPLICATION_XML_TYPE);
        Response response = client.target("http://localhost:8080/galileonews/news/")
                .request().post(newsInput, Response.class);
        NewsOutput newsOutput = response.readEntity(NewsOutput.class);
        assertTrue(newsOutput != null);
        assertTrue(newsOutput.getMsg().size() == 4);
        assertTrue(newsOutput.getMsg().get(0).getId().longValue() == 4);
        assertTrue(newsOutput.getMsg().get(1).getId().longValue() == 5);
        assertTrue(newsOutput.getMsg().get(2).getId().longValue() == 1);
        assertTrue(newsOutput.getMsg().get(3).getId().longValue() == 2);
        for (Msg msg : newsOutput.getMsg()) {
            System.out.println("id:".concat(msg.getId().toString()));
            for (String line : msg.getLine()) {
                System.out.println("line:".concat(line));
            }
            System.out.println();
        }
    }

    @Test
    public void newsTest6() {
        news.setUserName("user1");
        news.setPassword("user1");
        news.setToday(new Date());
        Entity<NewsInput> newsInput = Entity.entity(news, APPLICATION_XML_TYPE);
        Response response = client.target("http://localhost:8080/galileonews/news/")
                .request().post(newsInput, Response.class);
        NewsOutput newsOutput = response.readEntity(NewsOutput.class);
        assertTrue(newsOutput != null);
        assertTrue(newsOutput.getMsg().size() == 5);
        assertTrue(newsOutput.getMsg().get(0).getId().longValue() == 7);
        assertTrue(newsOutput.getMsg().get(1).getId().longValue() == 8);
        assertTrue(newsOutput.getMsg().get(2).getId().longValue() == 1);
        assertTrue(newsOutput.getMsg().get(3).getId().longValue() == 2);
        assertTrue(newsOutput.getMsg().get(4).getId().longValue() == 3);
        for (Msg msg : newsOutput.getMsg()) {
            System.out.println("id:".concat(msg.getId().toString()));
            for (String line : msg.getLine()) {
                System.out.println("line:".concat(line));
            }
            System.out.println();
        }
    }

    @Test
    public void newsTest7() {
        Response response = client.target("http://localhost:8080/galileonews/news/id1")
                .request().get();
        assertTrue(response.getHeaderString("Content-Disposition").contains("Spesifikasi_Galileo_News.txt"));
    }

}
