<%-- 
    Document   : seatselector
    Created on : 11-Jan-2017, 18:54:56
    Author     : Chris
--%>

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
          $( "input" ).checkboxradio();
        } );
        </script>
        <style>
        table {
            font-family: arial, sans-serif;
            border-collapse: collapse;
            width: 50%;
        }

        td, th {
            border: 1px solid #dddddd;
            text-align: center;
            padding: 4px;
            width: 10%;
        }

        tr:nth-child(even) {
            background-color: #dddddd;
        }
        </style>
        <title>JSP Page</title>
    </head>
    <body>
        <%
            int seats = 156;
            int rows = 26;
            int abreast = 3;
            String[] seatChars = {"A", "B", "C", "D", "E", "F", "G", "H"};
            String occupied = "12A";
            /*String[] strings = new String[seats+rows]; //we need one blank place per row for aisle
            for(int i=0; i<rows; i++) {
                for(int x=0; x<abreast; x++) {
                    System.out.println(i+seatChars[x]);
                }    
            }    */
            
            
        %>
        <h1>Select your seat</h1>
        
        <form action="SeatServlet" method="post">
        <table>
        
        <%
          for(int i=1; i<=rows; i++) {
              int seatsMade = 0;
              %>
              <tr>
              <%
              for(int x=0; x<abreast; x++) {
                  String s = i+seatChars[seatsMade];
                  if(s.equalsIgnoreCase(occupied)) {
                      %>
                      <td>
              <button>X</button>
                      </td>
                      <%
                  } else {


                %>
                <td>
                <label for=<%= i+seatChars[seatsMade] %>><%= i+seatChars[seatsMade] %></label>
                <input type="radio" name="radio-1" value=<%= i+seatChars[seatsMade] %> id=<%= i+seatChars[seatsMade] %>>
                </td>
             <%  }
                 seatsMade++;
              }
              %>
                <td>AISLE</td>    
              <%
              for(int x=0; x<abreast; x++) {
                %>
                <td>
                <label for=<%= i+seatChars[seatsMade] %>><%= i+seatChars[seatsMade] %></label>
                <input type="radio" name="radio-1" value=<%= i+seatChars[seatsMade] %> id=<%= i+seatChars[seatsMade] %>>
                </td>
                <%
                    seatsMade++;
              }      
                 %>   
              </tr>
              <% 
          }  
        %>    
        
      </table>
        <input type="Submit" name="seatButton" value="Confirm Selection" />
        </form>
        
    </body>
</html>
