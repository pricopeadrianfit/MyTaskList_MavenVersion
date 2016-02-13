package org.fasttrackit.dev.todolist.servlet;


import org.fasttrackit.dev.todolist.MyListOfToDoMock;
import org.fasttrackit.dev.todolist.ToDoBean;
import org.fasttrackit.dev.todolist.db.DBOperations;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;


/**
 * Created by condor on April 04, 2015
 * FastTrackIT, 2015
 */


/* didactic purposes

Some items are intentionally not optimized or not coded in the right way

FastTrackIT 2015

*/

public class ToDoListServlet extends HttpServlet {

    private static final String ACTION = "action";
    private static final String LIST_ACTION = "list";
    private static final String ADD_ACTION = "add";
    private static final String DONE_ACTION = "done";
    private static final String ID_TASK = "id";
    private static final String VALUE_NEWTASK = "value";
    private static final String DUEDATE_NEWTASK = "dueDate";

    public void service(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("mytask list service called now.");


        HttpSession session = request.getSession(true);
        String username=(String)session.getAttribute("username");
        if (username == null) {
            System.out.println("Nu esti logat");
        }
        else{

            String action = request.getParameter(ACTION);


            if (action != null && action.equals(LIST_ACTION)) {
                listAction(request, response);
            } else if (action != null && action.equals(ADD_ACTION)) {
                addAction(request, response);
            } else if (action != null && action.equals(DONE_ACTION)) {
                doneAction(request, response);
            }

        }
    }



    private void listAction(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("list action");
        HttpSession session = request.getSession(true);

        // call db
        List l = new LinkedList();
        try {
            l=DBOperations.readItems();
            System.out.println("lungime din db:"+l.size());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // put the list in a json
        JsonObjectBuilder jObjBuilder = Json.createObjectBuilder();
        JsonArrayBuilder jArrayBuilder = Json.createArrayBuilder();
        for (ListIterator<ToDoBean> iterator = l.listIterator(); iterator.hasNext(); ) {
            ToDoBean element = iterator.next();
            if (!element.isDone()) {
               //System.out.print(element.getId() + ":");
                System.out.println(element.getWhatToDo());
                jArrayBuilder.add(Json.createObjectBuilder()
                                .add("name", element.getWhatToDo())
                                .add("dueDate", element.getDueDate())
                                .add("done", false)
                                .add("id", element.getId())
                );

            }
        }
        jObjBuilder.add("tasks", jArrayBuilder);
        JsonObject jSonFinal = jObjBuilder.build();

        System.out.println("json pe list: " + jSonFinal.toString());

        returnJsonResponse(response, jSonFinal.toString());
        System.out.println("end list action");
    }


    private void doneAction(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("enter pe done");

        HttpSession session = request.getSession(true);

        String idS = request.getParameter(ID_TASK);
        int id = Integer.parseInt(idS);

        try {
            DBOperations.updateItem(id);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("i am done");
    }

    private void addAction(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("add action");

        HttpSession session = request.getSession(true);

        String value = request.getParameter(VALUE_NEWTASK);
        String dueDate = request.getParameter(DUEDATE_NEWTASK);



        MyListOfToDoMock myListObject = MyListOfToDoMock.getInstance();
       // myListObject.printList();

        myListObject.addItem(value,dueDate);

        try {
            DBOperations.addItem(value,dueDate,false);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("now I am done");

    }

    /**/
    private void returnJsonResponse(HttpServletResponse response, String jsonResponse) {
        response.setContentType("application/json");
        PrintWriter pr = null;
        try {
            pr = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert pr != null;
        pr.write(jsonResponse);
        pr.close();
    }
}
