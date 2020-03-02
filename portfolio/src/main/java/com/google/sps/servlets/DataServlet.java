// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import java.io.IOException;
import com.google.gson.Gson;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import java.util.*;
@WebServlet("/data")
public class DataServlet extends HttpServlet {
  private List<String> comments;
  
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    comments = new ArrayList<>();
    Query query = new Query("Comment").addSort("timestamp", SortDirection.DESCENDING);
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);
    for (Entity entity : results.asIterable()) {
      String comment = (String) entity.getProperty("comment");
      comments.add(comment);
    }
    response.setContentType("application/json;");
    response.getWriter().println(convToJson(comments));
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
      String comment = request.getParameter("comment");
      Entity entity = new Entity("comment");
      entity.setProperty("Comment", comment);
      DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
      datastore.put(entity);
      response.sendRedirect("/index.html");
  }

  //function toconvert to json
   private String convToJson(Object object){
       Gson gson = new Gson();
       return gson.toJson(object);
   }
}
