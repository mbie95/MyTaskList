package com.mycompany.mytasklist.tasklist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 *
 * @author marcin
 */
@WebServlet(name = "TaskServlet", urlPatterns = {"/api/tasks/*"})
public class TaskServlet extends HttpServlet {
    private static final String NAME_PARAM = "name";
    private final Logger logger = LoggerFactory.getLogger(TaskServlet.class);
    private TaskService taskService = new TaskService();
    
    @SuppressWarnings("unused")
    public TaskServlet(){
        this(new TaskService());
    }
    
    TaskServlet(TaskService taskService) {
        this.taskService = taskService;
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Request got! Sending tasks...");
        resp.setContentType("application/json;charset=UTF-8");
        resp.getWriter().write(taskService.getTasks());
    }
    
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Request got! Updating task...");
        resp.setContentType("application/json;charset=UTF-8");
        //getPathInfo zwraca koncowke adresu przekierowujacego do servletu
        resp.getWriter().write(taskService.putTask(req.getPathInfo()));
    }
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Request got! Inserting task...");
        resp.setContentType("application/json;charset=UTF-8");
        resp.getWriter().write(taskService.addTask(req.getInputStream()));
    }
}
