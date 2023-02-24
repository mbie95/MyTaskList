package com.mycompany.mytasklist.mys;

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
//adnotacja sluzaca do konfigurowania servletu
//urlPatterns - adresy url przekierowujace do tego servletu, tablica stringow
@WebServlet(name = "MojServlet", urlPatterns = {"/api"})
public class MyServlet extends HttpServlet {
    //zmienna dla nazwy parametru name, lang
    private static final String NAME_PARAM = "name";
    private static final String LANG_PARAM = "lang";
    //logger
    private final Logger logger = LoggerFactory.getLogger(MyServlet.class);
    //obiekt serwisu, aby klasa spelniala signle responsibility principle
    //reprezentuje on warstwe biznesowa aplikacji
    private MyService service;
    
    //wymagany dla jetty'ego konstruktor domyslny servletu, on go sobie inicjalizuje, kiedy potrzebuje
    //tworzony, aby obslugiwal serwis
    @SuppressWarnings("unused") //Servlet container potrzebuje tej adnotacji
    public MyServlet(){
        this(new MyService());
    }
    
    //konstruktor
    MyServlet(MyService service) {
        this.service = service;
    }
    
    //przeciazenie metody doGet
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //odpowiedz serwera
        logger.info("Request got! with parameters " + req.getParameterMap());
 
        //pobieramy parametr name
        String parameterName = req.getParameter(NAME_PARAM);
        String parameterLang = req.getParameter(LANG_PARAM);
 
        //jezeli taki zostal podany to wypisujemy wiadomosc z imieniem, inaczej hello world
        //nasz service zwraca imie, badz domyslne slowo zamienne
        //drugi parametr oznacza jezyk
        String greeting = service.greeting(parameterName, parameterLang);
        //resp.setContentType("text/plain");
        resp.getWriter().write(greeting);
    }
    
}
