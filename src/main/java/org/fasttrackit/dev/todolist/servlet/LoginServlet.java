package org.fasttrackit.dev.todolist.servlet;

import org.fasttrackit.dev.todolist.db.DBOperations;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by condor on 04/04/15.
 * FastTrackIT, 2015
 */
public class LoginServlet extends HttpServlet {

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException{


        HttpSession session = request.getSession(true);
//
       String success ="/todolist.html";


// citesc inputul din browser
        String u = request.getParameter("username");
        String p = request.getParameter("password");

        int found = -1;
        try {
            found = DBOperations.login(u,p);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (found !=-1) {

            //daca fieldul din browser nu este gol se atribuie valoarea din input
            session.setAttribute("username",u);
            session.setAttribute("userid", found);

        }

        RequestDispatcher d= getServletContext().getRequestDispatcher(success);
        d.forward(request,response);

//        System.out.println("service called...");
//        String myValue  = request.getParameter("myValue");
        System.out.println("HELLO");

    }
}
