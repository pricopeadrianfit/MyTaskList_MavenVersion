package org.fasttrackit.dev.todolist.db;
import org.fasttrackit.dev.todolist.ToDoBean;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by condor on 26/02/15.
 * FastTrackIT, 2015
 * <p/>
 * DEMO ONLY PURPOSES, IT MIGHT CONTAINS INTENTIONALLY ERRORS OR ESPECIALLY BAD PRACTICES
 *
 * make sure you refactor it and remove lots of bad practices like loading the driver multiple times or
 * repeating the same common code multiple times
 *
 * BTW, exercise 1: how we reorg this/refactor in small pieces
 */
public class DBOperations {


    public static void main(String[] a) {
        try {
//            DBOperations.addItem("ana are pere","2016-03-04",false);
//            DBOperations.readItems();
            boolean f;
            f=DBOperations.login("adrian","123");
            System.out.println(f);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        }

    }


    public static void addItem(String whatToDo, String dueDate, boolean isDone) throws ClassNotFoundException, SQLException {

        // 1. load driver
        Class.forName("org.postgresql.Driver");

        // 2. define connection params to db
        final String URL = "jdbc:postgresql://54.93.65.5:5432/Agenda_Adrian";
        final String USERNAME = "fasttrackit_dev";
        final String PASSWORD = "fasttrackit_dev";

        // 3. obtain a connection
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        // 4. create a query statement
        PreparedStatement pSt = conn.prepareStatement("INSERT INTO ToDoo (whatToDo, dueDate, isDone) VALUES (?,?,?)");
        pSt.setString(1, whatToDo);
        pSt.setDate(2, java.sql.Date.valueOf(dueDate)); //assumes a certain format
        pSt.setBoolean(3, isDone);



        // 5. execute a prepared statement
        int rowsInserted = pSt.executeUpdate();

        // 6. close the objects
        pSt.close();
        conn.close();
    }

    public static List readItems() throws ClassNotFoundException, SQLException {
        // 1. load driver
        Class.forName("org.postgresql.Driver");

        // 2. define connection params to db
        final String URL = "jdbc:postgresql://54.93.65.5:5432/Agenda_Adrian";
        final String USERNAME = "fasttrackit_dev";
        final String PASSWORD = "fasttrackit_dev";

        // 3. obtain a connection
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        // 4. create a query statement
        Statement st = conn.createStatement();

        // 5. execute a query
        ResultSet rs = st.executeQuery("SELECT * FROM ToDoo where isDone is false");

        // 6. iterate the result set and print the values

        List returnValues = new LinkedList();
        while (rs.next()) {

            ToDoBean tb = new ToDoBean();
            tb.setId(rs.getInt("id"));
            tb.setWhatToDo(rs.getString("whatToDO").trim());
            tb.setDueDate(rs.getDate("dueDate").toString());
            returnValues.add(tb);
            System.out.println("uite randu:"+tb.getWhatToDo());
        }

        // 7. close the objects
        rs.close();
        st.close();
        conn.close();

        return returnValues;
    }

    public static void updateItem(int id) throws ClassNotFoundException, SQLException {

        // 1. load driver
        Class.forName("org.postgresql.Driver");

        // 2. define connection params to db
        final String URL = "jdbc:postgresql://54.93.65.5:5432/Agenda_Adrian";
        final String USERNAME = "fasttrackit_dev";
        final String PASSWORD = "fasttrackit_dev";

        // 3. obtain a connection
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        // 4. create a query statement
        PreparedStatement pSt = conn.prepareStatement("UPDATE ToDoo SET isDone=true WHERE id=?");
        pSt.setInt(1, id);



        // 5. execute a prepared statement
        int rowsUpdated = pSt.executeUpdate();

        // 6. close the objects
        pSt.close();
        conn.close();
    }

    public static boolean login(String user , String password) throws ClassNotFoundException, SQLException {
        // 1. load driver
        Class.forName("org.postgresql.Driver");

        // 2. define connection params to db
        final String URL = "jdbc:postgresql://54.93.65.5:5432/Agenda_Adrian";
        final String USERNAME = "fasttrackit_dev";
        final String PASSWORD = "fasttrackit_dev";

        // 3. obtain a connection
        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        // 4. create a query statement
        Statement st = conn.createStatement();

        // 5. execute a que0
        ResultSet rs = st.executeQuery("SELECT * FROM users where username='"+user+ "' AND password='"+password+ "'");

        // 6. iterate the result set and print the value

     boolean found=false;
        while (rs.next()) {

            found=true;
        }

        // 7. close the objects
        rs.close();
        st.close();
        conn.close();

        return found;
    }

//    private static void demoDelete() throws ClassNotFoundException, SQLException {
//
//        // 1. load driver
//        Class.forName("org.postgresql.Driver");
//
//        // 2. define connection params to db
//        final String URL = "jdbc:postgresql://IP:5432/fast1";
//        final String USERNAME = "fasttrackit_dev";
//        final String PASSWORD = "fasttrackit_dev";
//
//        // 3. obtain a connection
//        Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
//
//        // 4. create a query statement
//        // PreparedStatement pSt = conn.prepareStatement("DELETE FROM USERS WHERE PK_USER=?");
//        //pSt.setLong(1, 1);
//
//        // 5. execute a prepared statement
//        //int rowsDeleted = pSt.executeUpdate();
//
//        String cititTastatura="'ionel'";
//        String delete = "DELETE FROM USERS WHERE name="+cititTastatura;
//        Statement s = conn.createStatement();
//        s.execute(delete);
//
//        System.out.println(rowsDeleted + " rows were deleted.");
//        // 6. close the objects
//        pSt.close();
//        conn.close();
//    }
}

