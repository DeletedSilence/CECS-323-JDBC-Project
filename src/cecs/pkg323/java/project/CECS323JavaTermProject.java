/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cecs.pkg323.java.project;

import java.io.Console;
import java.sql.*;
import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author Mimi Opkins with some tweaking from Dave Brown
 */
public class CECS323JavaTermProject {
    //  Database credentials
    static String USER;
    static String PASS;
    static String DBNAME;
    //This is the specification for the printout that I'm doing:
    //each % denotes the start of a new field.
    //The - denotes left justification.
    //The number indicates how wide to make the field.
    //The "s" denotes that it's a string.  All of our output in this test are 
    //strings, but that won't always be the case.
    static final String displayFormat="%-5s%-15s%-15s%-15s\n";
// JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";
    static String DB_URL = "jdbc:derby://localhost:1527/";
//            + "testdb;user=";
/**
 * Takes the input string and outputs "N/A" if the string is empty or null.
 * @param input The string to be mapped.
 * @return  Either the input string or "N/A" as appropriate.
 */
    public static String dispNull (String input) {
        //because of short circuiting, if it's null, it never checks the length.
        if (input == null || input.length() == 0)
            return "N/A";
        else
            return input;
    }
    
    public static void main(String[] args) {
        //Prompt the user for the database name, and the credentials.
        //If your database has no credentials, you can update this code to 
        //remove that from the connection string.
        Scanner in = new Scanner(System.in);
        System.out.print("Name of the database (not the user account): ");
        DBNAME = in.nextLine();
        System.out.print("Database user name: ");
        USER = in.nextLine();
        System.out.print("Database password: ");
        PASS = in.nextLine();
        // Change Password:
        // call SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY('derby.user.<my user name>', '<your new password>')
        System.out.println("PasswordL " + PASS);
        //Constructing the database URL connection string
        DB_URL = DB_URL + DBNAME + ";user="+ USER + ";password=" + PASS;
        Connection conn = null; //initialize the connection
        Statement stmt = null;  //initialize the statement that we're using
        try {
            //STEP 2: Register JDBC driver
            Class.forName("org.apache.derby.jdbc.ClientDriver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL);

            //STEP 4: Execute a query
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            
            String sql;
            ResultSet rs;
            
            // START - LIST ALL WRITING GROUPS ////////////////////////////////////////////////////////////////////

            sql = "SELECT groupName FROM writingGroup"; 
            rs = stmt.executeQuery(sql);
            listSpecificAttribute(rs, "writingGroup");

            
            // END ////////////////////////////////////////////////////////////////////////////////////////////////
            
            // START - LIST ALL FOR A SPECIFIC WRITING GROUP PROVIDED BY USER /////////////////////////////////////
            System.out.println("Which writing group do you want to details of?");
            sql = "SELECT * FROM writingGroup WHERE groupName = '?'";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            pstmt.setString(1, rsmd.getColumnClassName(1));
            
            rs = stmt.executeQuery(sql);
            
//            while (rs.next()) {
//                //Retrieve by column name                
//                String actualAttribute = rs.getString(relativeAttribute);
//
//                //Display values
//                System.out.printf("%-10s    ", dispNull(actualAttribute));
//            }
//           } catch (Exception e) {
//            e.printStackTrace();
//        }
            
            
            // END ///////////////////////////////////////////////////////////////////////////////////////////////
            
            // START - LIST ALL PUBLISHERS ///////////////////////////////////////////////////////////////////////
            
            sql = "SELECT publisherName FROM publishers";
            rs = stmt.executeQuery(sql);
            listSpecificAttribute(rs, "publisherName");
            
            // END ///////////////////////////////////////////////////////////////////////////////////////////////
            
            // START - LIST ALL THE DATA FOR A PUBLISHER SPECIFIED BY THE USER ///////////////////////////////////
            
            
            
            // END ///////////////////////////////////////////////////////////////////////////////////////////////
            
            // START - LIST ALL BOOK TITLES //////////////////////////////////////////////////////////////////////
            
            sql = "SELECT bookTitle FROM books";
            rs = stmt.executeQuery(sql);
            listSpecificAttribute(rs, "bookTitle");
            
            // END ///////////////////////////////////////////////////////////////////////////////////////////////
            
            // START - LIST ALL THE DATA FOR A BOOK SPECIFIED BY THE USER ////////////////////////////////////////
            
            
            
            // END ///////////////////////////////////////////////////////////////////////////////////////////////
            
            // START - INSERT A NEW BOOK /////////////////////////////////////////////////////////////////////////
            
            
            
            // END ///////////////////////////////////////////////////////////////////////////////////////////////
            
            // START - INSERT A NEW PULISHER AND UPDATE ALL BOOKS PUBLISHED BY ONE PUBLISHER TO //////////////////
            //         BE THE NEW PUBLISHER. LEAVE THE OLD PUBLISHER ALONE, JUST MODIFY THE BOOKS ////////////////
            //         THAT THEY HAVE PUBLISHED                                                     //////////////
            
            
            
            // END ///////////////////////////////////////////////////////////////////////////////////////////////
            
            // START - REMOVE A BOOK SPECIFIED BY THE USER ///////////////////////////////////////////////////////
            
            
            
            // END ///////////////////////////////////////////////////////////////////////////////////////////////
            
            //STEP 6: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("\nGoodbye!");
    }//end main
    
    public static void listSpecificAttribute(ResultSet rs, String relativeAttribute) {
        try {
            //STEP 5: Extract data from result set            
            System.out.println("\nList All Writing Groups:");
            while (rs.next()) {
                //Retrieve by column name                
                String actualAttribute = rs.getString(relativeAttribute);

                //Display values
                System.out.printf("%-10s    ", dispNull(actualAttribute));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}//end FirstExample

