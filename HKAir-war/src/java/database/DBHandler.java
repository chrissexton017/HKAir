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
        else if(rsmd.getColumnType(i)==java.sql.Types.DATE){
         obj.put(column_name, rs.getDate(column_name));
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

    
}
