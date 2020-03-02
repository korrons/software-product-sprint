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
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import java.util.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {
List<String> messages;
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
     messages = new ArrayList<>();
     DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    // Convert the messages to JSON
    String json = convertToJson(messages);
    // Send the JSON as the response
    response.setContentType("application/json;");
    response.getWriter().println(json);
  }
    //doPost function to add user input to list of comments
   @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Get the input from the form.
    String message = request.getParameter("text-input");
    messages.add(message);
  //create entity to store comments in datastore
  Entity storedComment = new Entity("Comments");
  storedComment.setProperty("Comment", message);
  DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
  datastore.put(storedComment);
  }

  //function to convert to json
  private String convertToJson(Object object){
       Gson gson = new Gson();
       return gson.toJson(object);
  }
}
