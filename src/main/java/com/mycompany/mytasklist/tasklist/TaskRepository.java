package com.mycompany.mytasklist.tasklist;

import com.mycompany.mytasklist.HibernateUtil;
import org.hibernate.Query;

import java.util.Optional;
import java.util.List;

/**
 *
 * @author marcin
 */
public class TaskRepository {
    //szukanie elementu task indexem
    public Optional<Task> findById(Integer id) {
        var session = HibernateUtil.getSessionFactory().openSession();
        var transaction = session.beginTransaction();
        
        var result = Optional.ofNullable(session.get(Task.class, id));
        
        transaction.commit();
        session.close();
        return result;
    }
    
    //szukanie wszystkich elementow z task list tabeli
    List<Task> findAll() {
        var session = HibernateUtil.getSessionFactory().openSession();
        var transaction = session.beginTransaction();
        
        var result = session.createQuery("from Task", Task.class).list();
        
        transaction.commit();
        session.close();
        return result;
    }
    
    public Task toggleTask(Integer id) {
        var session = HibernateUtil.getSessionFactory().openSession();
        var transaction = session.beginTransaction();
        
        Task task = (Task)session.get(Task.class, id);
        task.setDone(!task.getDone());
        String hql = "UPDATE Task set done = :taskDone " + 
                     "WHERE id = :taskId";
        Query query = session.createQuery(hql);
        query.setParameter("taskDone", task.getDone());
        query.setParameter("taskId", id);
        var result = query.executeUpdate();
        
        if (result != 1 || task.getId() != id)
            transaction.rollback();
        transaction.commit();
        
        session.close();
        return task;
    }
    
    public Task addTask(Task newTask) {
        var session = HibernateUtil.getSessionFactory().openSession();
        var transaction = session.beginTransaction();
        
        try {
            session.persist(newTask);
        }
        catch (Exception e) {
            transaction.rollback();
        }
        
        transaction.commit();
        
        session.close();
        return newTask;
    }
    
}
