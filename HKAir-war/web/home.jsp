<%-- 
    Document   : home
    Created on : 09-Jan-2017, 19:07:32
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
        <title>HK-Air.com</title>
        <script>
        $( function() {
          $.ajax({
            url: "JSONServlet",
            type: "GET",
            contentType: "application/json",
            dataType: "json",
            data: {value : 'destinations'},
            success: function(received) {
              // init the widget with response data and let it do the filtering
              $("#origin").autocomplete({
                source: received
              });
            },
            error: function(x, t, m) {
              console.trace();
              if (!(console == 'undefined')) {
                console.log("ERROR: " + x + t + m);
              }
              console.log(" At the end");
            }
          });
          
        } );
        </script>
        <script>
            $( function() {
              $( "#departure" ).datepicker({
                  dateFormat: "yy-mm-dd"
              });
              $( "#return" ).datepicker({
                  dateFormat: "yy-mm-dd"
              });
            } );
        </script>
    </head>
    <body>
        <h1>Welcome to HKAir!</h1>
        <h2>Book a Flight</h2>
        <div>
            <form action="ResultsServlet" method="post">
                <p><input type="checkbox" name="oneway" value="OneWay" />One Way</p>
                <label for="origin">Origin: </label>
                <p><input id="origin" type="Text" name="orig" /></p>
                <label for="destination">Destination: </label>
                <p><input id="destination" type="Text" name="dest" /></p>
                <label for="departure">Departing: </label>
                <p><input type="text" id="departure" name="depart"/></p>
                <label for="return">Returning: </label>
                <p><input type="text" id="return" name="return"/></p>
            <p><input type="Submit" name="selection" value="Search" /></p>
            </form>
        </div>
    </body>
</html>
