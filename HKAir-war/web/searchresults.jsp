<%-- 
    Document   : searchresults
    Created on : 09-Jan-2017, 07:24:32
    Author     : Lokesh
--%>

<%@page import="org.json.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
        <link rel="stylesheet" href="/resources/demos/style.css">
        <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
        <script>
        $( function() {
          $( "#tabs" ).tabs();
          $( "#return" ).tabs();
        } );
        </script>
        <script>
        $( function() {
          $( "input" ).checkboxradio();
        } );
        </script>
        <style>
        table {
            font-family: arial, sans-serif;
            border-collapse: collapse;
            width: 100%;
        }

        td, th {
            border: 1px solid #dddddd;
            text-align: left;
            padding: 8px;
        }

        tr:nth-child(even) {
            background-color: #dddddd;
        }
        </style>
        <title>JSP Page</title>
    </head>
    <body>
        <%
            int radioIndex = 0;
            
            String outbound = request.getParameter("outbound"); 
            String inbound = request.getParameter("inbound");
            
            JSONArray outArr = new JSONArray(outbound);
            JSONArray inboundArr = new JSONArray(inbound);
            
            
        %>
        <h1>Select a Flight</h1>
        <br>
        <h2>Outbound</h2>
        <div id="tabs">
        <ul>
          <li><a href="#tabs-1">Nunc tincidunt</a></li>
          <li><a href="#tabs-2">Proin dolor</a></li>
          <li><a href="#tabs-3">Aenean lacinia</a></li>
        </ul>
        <div id="tabs-1">
          <table>
        <tr>
          <th>Depart</th>
          <th>Arrive</th>
          <th>Flight</th>
          <th>Economy</th>
          <th>Business</th>
          <th>First</th>
        </tr>
        <%
          for(int i=0; i<outArr.length(); i++) {
              JSONObject obj = outArr.getJSONObject(i);
              String flight_no = obj.getString("flight_number");
              String dep = obj.getString("departure");
              String arr = obj.getString("arrival");
              int rate = obj.getInt("base_rate");
              String radioID = "radio-"+radioIndex;
              radioIndex++;
              %>
              <tr>
                <td><%= dep %></td>
                <td><%= arr %></td>
                <td><%= flight_no %></td>
                <td>
                <label for=<%= radioID %>><%= rate %></label>
                <input type="radio" name="radio-one" id=<%= radioID %>>
                </td>
                <td><%= rate %></td>
                <td><%= rate %></td>
              </tr> 
              <%
          }  
        %>    
        
      </table>
        
        </div>
        <div id="tabs-2">
          <p>Morbi tincidunt, dui sit amet facilisis feugiat, odio metus gravida ante, ut pharetra massa metus id nunc. Duis scelerisque molestie turpis. Sed fringilla, massa eget luctus malesuada, metus eros molestie lectus, ut tempus eros massa ut dolor. Aenean aliquet fringilla sem. Suspendisse sed ligula in ligula suscipit aliquam. Praesent in eros vestibulum mi adipiscing adipiscing. Morbi facilisis. Curabitur ornare consequat nunc. Aenean vel metus. Ut posuere viverra nulla. Aliquam erat volutpat. Pellentesque convallis. Maecenas feugiat, tellus pellentesque pretium posuere, felis lorem euismod felis, eu ornare leo nisi vel felis. Mauris consectetur tortor et purus.</p>
        </div>
        <div id="tabs-3">
          <p>Mauris eleifend est et turpis. Duis id erat. Suspendisse potenti. Aliquam vulputate, pede vel vehicula accumsan, mi neque rutrum erat, eu congue orci lorem eget lorem. Vestibulum non ante. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Fusce sodales. Quisque eu urna vel enim commodo pellentesque. Praesent eu risus hendrerit ligula tempus pretium. Curabitur lorem enim, pretium nec, feugiat nec, luctus a, lacus.</p>
          <p>Duis cursus. Maecenas ligula eros, blandit nec, pharetra at, semper at, magna. Nullam ac lacus. Nulla facilisi. Praesent viverra justo vitae neque. Praesent blandit adipiscing velit. Suspendisse potenti. Donec mattis, pede vel pharetra blandit, magna ligula faucibus eros, id euismod lacus dolor eget odio. Nam scelerisque. Donec non libero sed nulla mattis commodo. Ut sagittis. Donec nisi lectus, feugiat porttitor, tempor ac, tempor vitae, pede. Aenean vehicula velit eu tellus interdum rutrum. Maecenas commodo. Pellentesque nec elit. Fusce in lacus. Vivamus a libero vitae lectus hendrerit hendrerit.</p>
        </div>
      </div>
        <h2>Return</h2>
        <div id="return">
        <ul>
          <li><a href="#tabs-1">Nunc tincidunt</a></li>
          <li><a href="#tabs-2">Proin dolor</a></li>
          <li><a href="#tabs-3">Aenean lacinia</a></li>
        </ul>
        <div id="tabs-1">
          <table>
         <tr>
          <th>Depart</th>
          <th>Arrive</th>
          <th>Flight</th>
          <th>Economy</th>
          <th>Business</th>
          <th>First</th>
        </tr>
        <%
          for(int i=0; i<inboundArr.length(); i++) {
              JSONObject obj = inboundArr.getJSONObject(i);
              String flight_no = obj.getString("flight_number");
              String dep = obj.getString("departure");
              String arr = obj.getString("arrival");
              int rate = obj.getInt("base_rate");
              String radioID = "radio-"+radioIndex;
              radioIndex++;
              %>
              <tr>
                <td><%= dep %></td>
                <td><%= arr %></td>
                <td><%= flight_no %></td>
                <td>
                <label for=<%= radioID %>><%= rate %></label>
                <input type="radio" name="radio-two" id=<%= radioID %>>
                </td>
                <td><%= rate %></td>
                <td><%= rate %></td>
              </tr> 
              <%
          }  
        %> 
      </table>
        </div>
        <div id="tabs-2">
          <p>Morbi tincidunt, dui sit amet facilisis feugiat, odio metus gravida ante, ut pharetra massa metus id nunc. Duis scelerisque molestie turpis. Sed fringilla, massa eget luctus malesuada, metus eros molestie lectus, ut tempus eros massa ut dolor. Aenean aliquet fringilla sem. Suspendisse sed ligula in ligula suscipit aliquam. Praesent in eros vestibulum mi adipiscing adipiscing. Morbi facilisis. Curabitur ornare consequat nunc. Aenean vel metus. Ut posuere viverra nulla. Aliquam erat volutpat. Pellentesque convallis. Maecenas feugiat, tellus pellentesque pretium posuere, felis lorem euismod felis, eu ornare leo nisi vel felis. Mauris consectetur tortor et purus.</p>
        </div>
        <div id="tabs-3">
          <p>Mauris eleifend est et turpis. Duis id erat. Suspendisse potenti. Aliquam vulputate, pede vel vehicula accumsan, mi neque rutrum erat, eu congue orci lorem eget lorem. Vestibulum non ante. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Fusce sodales. Quisque eu urna vel enim commodo pellentesque. Praesent eu risus hendrerit ligula tempus pretium. Curabitur lorem enim, pretium nec, feugiat nec, luctus a, lacus.</p>
          <p>Duis cursus. Maecenas ligula eros, blandit nec, pharetra at, semper at, magna. Nullam ac lacus. Nulla facilisi. Praesent viverra justo vitae neque. Praesent blandit adipiscing velit. Suspendisse potenti. Donec mattis, pede vel pharetra blandit, magna ligula faucibus eros, id euismod lacus dolor eget odio. Nam scelerisque. Donec non libero sed nulla mattis commodo. Ut sagittis. Donec nisi lectus, feugiat porttitor, tempor ac, tempor vitae, pede. Aenean vehicula velit eu tellus interdum rutrum. Maecenas commodo. Pellentesque nec elit. Fusce in lacus. Vivamus a libero vitae lectus hendrerit hendrerit.</p>
        </div>
      </div>
    </body>
</html>
