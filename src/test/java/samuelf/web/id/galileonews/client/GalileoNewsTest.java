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
import galileonews.api.NewsInput;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import static javax.ws.rs.core.MediaType.APPLICATION_XML_TYPE;
import javax.ws.rs.core.Response;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

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
        Entity<NewsInput> newsEntity = Entity.entity(news, APPLICATION_XML_TYPE);
        Response response = client.target("http://localhost:8080/galileonews/news/")
                .request().post(newsEntity, Response.class);
        String entity = response.readEntity(String.class);
        System.out.println(entity);
        assertTrue(entity.contains("User Name required"));
        assertTrue(entity.contains("Password required"));
    }

    @Test
    public void logInTest2() {
        news.setUserName("user3");
        news.setPassword("user3");
        Entity<NewsInput> newsEntity = Entity.entity(news, APPLICATION_XML_TYPE);
        Response response = client.target("http://localhost:8080/galileonews/news/")
                .request().post(newsEntity, Response.class);
        String entity = response.readEntity(String.class);
        System.out.println(entity);
        assertTrue(entity.contains("User Name not registered"));
    }

    @Test
    public void logInTest3() {
        news.setUserName("user1");
        news.setPassword("user3");
        Entity<NewsInput> newsEntity = Entity.entity(news, APPLICATION_XML_TYPE);
        Response response = client.target("http://localhost:8080/galileonews/news/")
                .request().post(newsEntity, Response.class);
        String entity = response.readEntity(String.class);
        System.out.println(entity);
        assertTrue(entity.contains("User Password not match"));
    }

    @Test
    public void newsTest1() {
        news.setUserName("user1");
        news.setPassword("user1");
        Entity<NewsInput> newsEntity = Entity.entity(news, APPLICATION_XML_TYPE);
        Response response = client.target("http://localhost:8080/galileonews/news/")
                .request().post(newsEntity, Response.class);
        String entity = response.readEntity(String.class);
        System.out.println(entity);
    }
}
