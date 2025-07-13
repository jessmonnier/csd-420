/*
 *
 * Professor Darrell Payne
 * Bellevue University
 * Modified by Jess Monnier
 * CSD-420 Module 9 Programming Assignment
 * 11 July 2025
 * I removed the line registering the Driver since it's not needed in
 * Java 6+ and consolidated the database actions in a try-with-resources block.
 * Also updated the database and user info to match the assignment prompt,
 * and some other little changes
 *
 */
// import java.net.URL; // Not used
import java.sql.*;

public class CreateTable{

  public static final String URL = "jdbc:mysql://localhost:3306/databasedb?";

  public static void create(){

    // // Separating out the driver registration; not required in Java 6+
    // try {
    //     Class.forName("com.mysql.cj.jdbc.Driver");
    // } catch (ClassNotFoundException e) {
    //     System.out.println("MySQL JDBC Driver not found");
    //     e.printStackTrace();
    //     return; // or handle appropriately
    // }

    // Consolidated try-with-resources block
    try (Connection con = DriverManager.getConnection(URL + "user=student1&password=pass"); 
         Statement stmt = con.createStatement()){

        // These commands are the same except I added the "IF EXISTS" for the drop table statement
        stmt.executeUpdate("DROP TABLE IF EXISTS address33");
        System.out.println("Database prepared for new table address33");
        stmt.executeUpdate("CREATE TABLE address33(ID int PRIMARY KEY,LASTNAME varchar(40)," +
                          "FIRSTNAME varchar(40), STREET varchar(40), CITY varchar(40), STATE varchar(40)," +
                          "ZIP varchar(40))");
        System.out.println("Table address33 created");
    }
    catch(SQLException e) {
      // This is a catch-all that will print the specific SQL error generated in the event of failure
      System.out.println("Database operation failed: " + e.getMessage());
    }
  }

  public static void main(String args[]) {

    // I changed this to reference a static method because VSCode was cranky about how it was originally
    // I know I can ignore the warning squiggles but they bug me!
    CreateTable.create();
  }
}