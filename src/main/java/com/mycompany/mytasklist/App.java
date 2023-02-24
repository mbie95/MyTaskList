package com.mycompany.mytasklist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.webapp.Configuration;
import org.eclipse.jetty.annotations.AnnotationConfiguration;
import org.eclipse.jetty.webapp.WebInfConfiguration;
import org.eclipse.jetty.webapp.WebXmlConfiguration;
import org.eclipse.jetty.webapp.MetaInfConfiguration;
import org.eclipse.jetty.webapp.FragmentConfiguration;
import org.eclipse.jetty.plus.webapp.EnvConfiguration;
import org.eclipse.jetty.plus.webapp.PlusConfiguration;
import org.eclipse.jetty.webapp.JettyWebXmlConfiguration;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.util.component.AbstractLifeCycle;
import org.eclipse.jetty.util.component.LifeCycle;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws Exception {
        //System.out.println("Hello World");
        //Dodanie loggera, sluzocego do wyswietlania danych
        /*Logger logger = LoggerFactory.getLogger(App.class);
        logger.info("Hello World message");*/
        
        //uchwyt do kontekstu aplikacji webowej, do niego mozemy podpinac servlety (komentarz na dole)
        WebAppContext webapp = new WebAppContext();
        //sciezka do plikow obslugujacych aplikacje webowa
        //w tym web.xml, ktory deklaruje filtry i serwlety, wykorzystywane przez serwis
        webapp.setResourceBase("src/main/webapp");
        //aby wszystkie requesty zaczynaly sie od slasha
        webapp.setContextPath("/");
        //potrzebne konfiguracje i adnotacje
        webapp.setConfigurations(new Configuration[] {
            new AnnotationConfiguration(),
            new WebInfConfiguration(),
            new WebXmlConfiguration(),
            new MetaInfConfiguration(),
            new FragmentConfiguration(),
            new EnvConfiguration(),
            new PlusConfiguration(),
            new JettyWebXmlConfiguration()
        });
        webapp.setAttribute("org.eclipse.jetty.server.webapp.ContainerIncludeJarPattern", ".*/classes/.*");
        //webapp.addServlet(MyServlet.class, "/api/*");
        
        //tworzenie serwera na porcie 8080
        Server server = new Server(8080);
        server.setHandler(webapp);
        //nasluchiwacz cyklu zycia serwera, obusluguje, co ma sie stac w przypadku
        //zakonczenia pracy serwera (jetty w naszym przypadku), tutaj zamyka
        //polaczenia z baza danych
        server.addLifeCycleListener(new AbstractLifeCycle.AbstractLifeCycleListener() {
            @Override
            public void lifeCycleStopped(LifeCycle event) {
                HibernateUtil.close();
            }
        });
        
        //odpalenie serwera i watku
        server.start();
        server.join();
    }
}

//ServletRequest to interface do HttpServletRequest