/*
 *
 * Professor Darrell Payne
 * Bellevue University
 * Modified by Jess Monnier
 * CSD-420 Module 9 Programming Assignment
 * 12 July 2025
 *
 */
// import java.net.URL; // not used
import java.sql.*;

public class InsertData{

  public static final String URL = "jdbc:mysql://localhost:3306/databasedb?";

  public static void insert(){

    try (Connection con = DriverManager.getConnection(URL + "user=student1&password=pass"); 
         Statement stmt = con.createStatement()){
      
      // Thought I could clean up the code with a for loop, putting only the unique data in an array
      String[] rowDataArray = {"55,'Larry','Rich','1111 Redwing Circle888','Bellevue','NE','68123'",
                          "1,'Fine','Ruth','1111 Redwing Circle','Bellevue','NE','68123'",
                          "2,'Howard','Curly','1000 Galvin Road South','Bellevue','NE','68005'",
                          "3,'Howard','Will','2919 Redwing Circle','Bellevue','NE','68123'",
                          "4,'Wilson','Larry','1121 Redwing Circle','Bellevue','NE','68124'",
                          "5,'Johnson','George','1300 Galvin Road South','Bellevue','NE','68006'",
                          "6,'Long','Matthew','2419 Redwing Circle','Bellevue','NE','68127'",
                          "44,'Tom','Matthew','1999 Redwing Circle','Bellevue','NE','68123'"};
      
      for (String rowData : rowDataArray) {
        System.out.println(
        stmt.executeUpdate("INSERT INTO address33 VALUES(" + rowData + ")") + " row updated");
      }

      stmt.executeUpdate("COMMIT");

      System.out.println("Data Inserted");
    }
    catch(SQLException e) {
      // This is a catch-all that will print the specific SQL error generated in the event of failure
      System.out.println("Database operation failed: " + e.getMessage());
    }
  }

  public static void main(String args[]) {

    InsertData.insert();
  }
}