/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import org.json.*;

/**
 *
 * @author Chris
 */

public abstract class DBHandler {
    
    private static Connection c;
    
    public static void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            // String URL = "jdbc:mysql://194.47.47.18:3306/YOUR_DATABASE_NAME?user=YOUR_USER_NAME&password=YOUR_PASSWORD";
            String URL = "jdbc:mysql://127.0.0.1:3306/hk_air?user=root&password=root";
            c = DriverManager.getConnection(URL);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException ex) {

        }
    }
    
    public static String getAirportList() {
        JSONArray jsonArr = new JSONArray();
        String query = "SELECT * FROM hk_air.airport;";
        try {
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery(query);
            //jsonArr = convert(rs);
            while(rs.next()) {
                JSONObject obj = new JSONObject();
                obj.put("value", rs.getString("iata_code"));
                obj.put("label", rs.getString("city")+" ("+rs.getString("airport_name")+")");
                jsonArr.put(obj);
            }

        } catch (Exception e) {
            System.err.println("ERROR: " + e);
        }
        return jsonArr.toString();
    }
    
    public static int getFlightID(String flight_no, String date) {
        int flight_id = 0;
        String query = "SELECT flight_id FROM hk_air.flight WHERE flight_number = '"+flight_no+"' AND date = '"+date+"';";
        try{
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery(query);
            if(rs.next()) {
                flight_id = rs.getInt("flight_id");
            }
            
        } catch (Exception e) {
            System.err.println("ERROR: " + e);
        }
        return flight_id;
    }
    
    public static int getTicketsSold(int flight_id) {
        int ticketsSold = 0;
        String query = "SELECT count(*) FROM hk_air.booking WHERE flight_id = "+flight_id+";";
        try{
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery(query);
            if(rs.next()) {
                ticketsSold = rs.getInt("count(*)");
            }
            
        } catch (Exception e) {
            System.err.println("ERROR: " + e);
        }
        return ticketsSold;
    }
    
    public static int getTicketsUnsold(int flight_id, int ticketsSold) {
        int unsoldSeats = 0;
        String assignedAircraft = getAssignedAircraft(flight_id);
        int seatingCapacity = getSeatingCapacity(assignedAircraft);
        return seatingCapacity - ticketsSold;
    }
    
    public static String getAssignedAircraft(int flight_id) {
        String aircraftRegistration = "";
        String query = "SELECT aircraft FROM hk_air.flight WHERE flight_id = "+flight_id+";";
         try{
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery(query);
            if(rs.next()) {
                aircraftRegistration = rs.getString("aircraft");
            }
            
        } catch (Exception e) {
            System.err.println("ERROR: " + e);
        }
        return aircraftRegistration;
    }
    
    public static int getSeatingCapacity(String aircraftReg) {
        System.out.println("DB getSeatingCapacity: aircraftReg: "+aircraftReg);
        int seatingCapacity = 0;
        String seatingConfig = "";
        String query = "SELECT seating_config FROM hk_air.aircraft WHERE registration = '"+aircraftReg+"';";
        try{
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery(query);
            if(rs.next()) {
                seatingConfig = rs.getString("seating_config");
            }
            
            System.out.println("DB getSeatingCapacity: seatingConfig: "+seatingConfig);
        } catch (Exception e) {
            System.err.println("ERROR: " + e);
        }
        String capQuery = "SELECT economy FROM hk_air.seating_config WHERE seating_config_id = '"+seatingConfig+"';";
        try{
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery(capQuery);
            if(rs.next()) {
                seatingCapacity = rs.getInt("economy");
            }
            
            System.out.println("DB getSeatingCapacity: seatingCapacity: "+seatingCapacity);
        } catch (Exception e) {
            System.err.println("ERROR: " + e);
        }
        return seatingCapacity;
    }
    
    public static String getServiceDetails(String flightNumber) {
        JSONArray jsonArr = new JSONArray();
        String query = "SELECT * FROM hk_air.service WHERE hk_air.service.flight_number = '"+flightNumber+"';";
        try {
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery(query);
            jsonArr = convert(rs);

        } catch (Exception e) {
            System.err.println("ERROR: " + e);
        }
        return jsonArr.toString();
    }
    
    public static String getFlights(String origin, String date) {
        JSONArray jsonArr = new JSONArray();
        String query = "SELECT hk_air.flight.flight_number, date, aircraft, flight_id, origin, destination, departure, arrival, base_rate"
                + " FROM hk_air.flight, hk_air.service WHERE hk_air.flight.flight_number = "
                + "hk_air.service.flight_number AND hk_air.service.origin = '"+origin+"' AND hk_air.flight.date = '"+date+"';";
        try {
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery(query);
            jsonArr = convert(rs);

        } catch (Exception e) {
            System.err.println("ERROR: " + e);
        }
        return jsonArr.toString();
    }
    
    public static JSONArray convert( ResultSet rs ) throws SQLException, JSONException {
    JSONArray json = new JSONArray();
    ResultSetMetaData rsmd = rs.getMetaData();

    while(rs.next()) {
      int numColumns = rsmd.getColumnCount();
      JSONObject obj = new JSONObject();

      for (int i=1; i<numColumns+1; i++) {
        String column_name = rsmd.getColumnName(i);

        if(rsmd.getColumnType(i)==java.sql.Types.ARRAY){
         obj.put(column_name, rs.getArray(column_name));
        }
        else if(rsmd.getColumnType(i)==java.sql.Types.BIGINT){
         obj.put(column_name, rs.getInt(column_name));
        }
        else if(rsmd.getColumnType(i)==java.sql.Types.BOOLEAN){
         obj.put(column_name, rs.getBoolean(column_name));
        }
        else if(rsmd.getColumnType(i)==java.sql.Types.BLOB){
         obj.put(column_name, rs.getBlob(column_name));
        }
        else if(rsmd.getColumnType(i)==java.sql.Types.DOUBLE){
         obj.put(column_name, rs.getDouble(column_name)); 
        }
        else if(rsmd.getColumnType(i)==java.sql.Types.FLOAT){
         obj.put(column_name, rs.getFloat(column_name));
        }
        else if(rsmd.getColumnType(i)==java.sql.Types.INTEGER){
         obj.put(column_name, rs.getInt(column_name));
        }
        else if(rsmd.getColumnType(i)==java.sql.Types.NVARCHAR){
         obj.put(column_name, rs.getNString(column_name));
        }
        else if(rsmd.getColumnType(i)==java.sql.Types.VARCHAR){
         obj.put(column_name, rs.getString(column_name));
        }
        else if(rsmd.getColumnType(i)==java.sql.Types.TINYINT){
         obj.put(column_name, rs.getInt(column_name));
        }
        else if(rsmd.getColumnType(i)==java.sql.Types.SMALLINT){
         obj.put(column_name, rs.getInt(column_name));
        }
        //put dates into string form for ease when parsing JSON later
        else if(rsmd.getColumnType(i)==java.sql.Types.DATE){
         obj.put(column_name, rs.getDate(column_name).toString());
        }
        else if(rsmd.getColumnType(i)==java.sql.Types.TIMESTAMP){
        obj.put(column_name, rs.getTimestamp(column_name));   
        }
        else{
         obj.put(column_name, rs.getObject(column_name));
        }
      }

      json.put(obj);
    }

    return json;
  }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}

