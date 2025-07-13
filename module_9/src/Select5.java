/*
 *
 * Professor Darrell Payne
 * Bellevue University
 * Modified by Jess Monnier
 * CSD-420 Module 9 Programming Assignment
 * 12 July 2025
 * The original didn't actually limit the selection to 5 so I added that
 *
 */
import java.sql.*;

public class Select5{

  public static final String URL = "jdbc:mysql://localhost:3306/databasedb?";

  public static void main(String args[]){

    try (Connection con = DriverManager.getConnection(URL + "user=student1&password=pass"); 
         Statement stmt = con.createStatement();
         ResultSet rs = stmt.executeQuery("SELECT * FROM address33 LIMIT 5");){

      System.out.println("Connection established - now executing a select");

      System.out.println("Received Results:");

      int i = rs.getMetaData().getColumnCount();

      while(rs.next()){

        for(int x = 1; x <= i; ++x){

          System.out.println(rs.getString(x));
        }

        System.out.println();
      }
    }
    catch(SQLException e) {
      // This is a catch-all that will print the specific SQL error generated in the event of failure
      System.out.println("Database operation failed: " + e.getMessage());
    }
  }
}