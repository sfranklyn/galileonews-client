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
package samuelf.web.id.galileonews.client;

import galileonews.api.NewsXML;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import static javax.ws.rs.core.MediaType.APPLICATION_XML_TYPE;

/**
 *
 * @author Samuel Franklyn <sfranklyn@gmail.com>
 */
public class Main {

    public static void main(String[] args) {
        Client client = ClientBuilder.newClient();
        NewsXML news = new NewsXML();
        news.setUserName("BAH");
        Entity<NewsXML> newsEntity = Entity.entity(news, APPLICATION_XML_TYPE);
        Response response = client.target("http://localhost:8080/galileonews/news/")
                .request().post(newsEntity, Response.class);
        System.out.println(response.readEntity(String.class));
    }
}
