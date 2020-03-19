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
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import java.util.ArrayList;
import java.util.List;
@WebServlet("/data")
public class DataServlet extends HttpServlet {
  List<String> commentList = new ArrayList<>();

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Query query = new Query("Input");
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);

    List<String> comments = new ArrayList<>();
    for (Entity entity : results.asIterable()) {
      String comment = (String)entity.getProperty("Comment");
      comments.add(comment);
    }
    Gson gson = new Gson();
    response.setContentType("application/json;");
    response.getWriter().println(convToJson(comments));
  }
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
      String input = request.getParameter("commentArea");
      commentList.add(input);
      Entity entity = new Entity("Input");
      entity.setProperty("Comment", input);
      DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
      datastore.put(entity);
      response.sendRedirect("/index.html");
  }
  private String convToJson(Object object){
       Gson gson = new Gson();
       return gson.toJson(object);
   }

}
