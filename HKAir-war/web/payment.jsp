<%-- 
    Document   : payment
    Created on : 12-Jan-2017, 14:09:20
    Author     : Lokesh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script>
        $( function() {
          $( "#dialog" ).dialog();
        } );
        </script>
        <style>
        p {
            border-bottom: 6px solid greenyellow;
            background-color: lightgrey;
        }
        </style>
        <title>JSP Page</title>
    </head>
    <body>
        <%
            String outID = request.getParameter("outID");
            String backID = request.getParameter("backID");
            String title = request.getParameter("title");
            String firstname = request.getParameter("firstname");
            String lastname = request.getParameter("lastname");
            String dob = request.getParameter("dob");
            String email = request.getParameter("email");
            String nationality = request.getParameter("nationality");
            String chosenSeat = request.getParameter("chosenSeat");
            double total = Double.parseDouble(request.getParameter("total"));
            
            if(request.getParameter("error")!=null) { 
            String error = request.getParameter("error");
        %>
            <div id="dialog" title="Payment Error">
            <p><%= error %></p>
            </div>
        <%  
            }
            %>
        <h1>Welcome to Payment</h1>
        
        <form action="PaymentServlet" method="post">
            
        <fieldset>
            <p>Payment Method</p>
          <div>
            <label for="card-type">Type of Card</label>
            <select name="card-type" id="card-type">
            <option>Mastercard</option>
            <option>Visa</option>
            <option>American Express</option>
            </select>
          </div>
          <div>
            <label for="card-no">Card Number</label>
            <input id="card-no" name="card-no" title="As stated on passport">
          </div>
          <div>
            <label for="month">Expiry Date</label>  
            <select name="month" id="month">
                <option value="0">January</option>
                <option value="1">February</option>
                <option value="2">March</option>
                <option value="3">April</option>
                <option value="4">May</option>
                <option value="5">June</option>
                <option value="6">July</option>
                <option value="7">August</option>
                <option value="8">September</option>
                <option value="9">October</option>
                <option value="10">November</option>
                <option value="11">December</option>
            </select>
            <select id="year" name="year">
                <option>2017</option>
                <option>2018</option>
                <option>2019</option>
                <option>2020</option>
                <option>2021</option>
                <option>2022</option>
                <option>2023</option>
                <option>2024</option>
                <option>2025</option>
                <option>2026</option>
                <option>2027</option>
            </select>
          </div>
          <div>
            <label for="security">Security Code</label>
            <input id="security" name="security" title="Your home or work address.">
          </div>
            <p>Billing Address</p>
          <div>
             <label for="address1">Address Line 1</label>
            <input type="text" name="address1" id="address1">
           </div>  
          <div>  
            <label for="address2">Address Line 2</label>
            <input type="text" name="address2" id="address1">
          </div>  
          <div>  
            <label for="address3">Address Line 3</label>
            <input type="text" name="address3" id="address1">
          </div>  
          <div>  
            <label for="postcode">Post Code</label>
            <input type="text" name="postcode" id="postcode">
          </div>  
            <div>  
            <label for="country">Country</label>
            <input type="text" name="country" id="country">
            
          </div>
            <div>
                <input type="checkbox" name="terms" value="terms"> I agree to the terms and conditions<br>
            </div>  
        </fieldset>
            <input type="hidden" name="title" value= <%= title %> />
        <input type="hidden" name="firstname" value= <%= firstname %> />
        <input type="hidden" name="lastname" value= <%= lastname %> />
        <input type="hidden" name="dob" value= <%= dob %> />
        <input type="hidden" name="email" value= <%= email %> />
        <input type="hidden" name="nationality" value= <%= nationality %> />
        <input type="hidden" name="outID" value= <%= outID %> />
        <input type="hidden" name="backID" value= <%= backID %> />
        <input type="hidden" name="total" value= <%= total %> />
        <input type="hidden" name="total" value= <%= chosenSeat %> />
            <input type="Submit" name="bookingButton" value="Book" />
      </form>
    </body>
</html>
