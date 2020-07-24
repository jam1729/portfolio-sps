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

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

@WebServlet("/data")
public class DataServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String url = "https://codeforces.com/api/user.info?handles=jam1729";
    HttpClient client = new DefaultHttpClient();
    HttpGet req = new HttpGet(url);
    HttpResponse res = client.execute(req);
    
    BufferedReader rd = new BufferedReader
    (new InputStreamReader(
    res.getEntity().getContent()));

    String line = "";
    response.setContentType("text/html;");
    while ((line = rd.readLine()) != null) {
      response.getWriter().println(line);
    }
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String text = getParameter(request, "handle", "");
    
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    Entity userEntity = new Entity("User");
    userEntity.setProperty("handle", text);
    datastore.put(userEntity);

    String url = "https://codeforces.com/api/user.info?handles=" + text;
    
    HttpClient client = new DefaultHttpClient();
    HttpGet req = new HttpGet(url);
    HttpResponse res = client.execute(req);
    
    BufferedReader rd = new BufferedReader
    (new InputStreamReader(
    res.getEntity().getContent()));

    String line = "";
    response.setContentType("text/html;");
    while ((line = rd.readLine()) != null) {
      response.getWriter().println(line);
    }
  }

  private String getParameter(HttpServletRequest request, String name, String defaultValue) {
    String value = request.getParameter(name);
    if (value == null) {
      return defaultValue;
    }
    return value;
  }
}
