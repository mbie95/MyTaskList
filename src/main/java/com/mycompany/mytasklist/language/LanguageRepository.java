package com.mycompany.mytasklist.language;

import com.mycompany.mytasklist.HibernateUtil;

import java.util.Optional;
import java.util.List;

/**
 *
 * @author marcin
 */
//repozytorium, w ktorym przechowujemy obiekty naszych jezykow, kiedy potrzeba
//bedzie sie laczyc z baza danych
public class LanguageRepository {
    //optional daje zabezpieczenie w przypadku, kiedy nie ma jezyka o takim id
    public Optional<Language> findById(Integer id) {
        //otwieranie sesji polaczenia z baza danych
        var session = HibernateUtil.getSessionFactory().openSession();
        //w trasakcji po rozpoczeciu wszystko jest zawieszone, dopoki
        //nie skomitujemy zmian (nie zatwierdzimy, wtedy wszystko idzie do 
        //bazy danych) lub ich nie wycofamy (wtedy instrukcja rollback)
        //kazda trasakcja jest atomowa (przechodzi wszystko z niej albo nic
        //sesja sluzy do zarzadzania transakcjami
        var transaction = session.beginTransaction();
        //wewnatrz funkcji get jest wykonywane zapytanie sql
        //pobieramy obiekt klasy dla szukanego id
        var result = session.get(Language.class, id);
        transaction.commit();
        session.close();
        /*
        try {
            transaction.commit();
        }
        catch (Exception e) {
            transaction.rollback();
        }
        */
        return Optional.ofNullable(result);
    }

    //metoda zwracajaca wszystkie jezyki DTO z tabeli
   // List<LanguageDTO> findAll() {
    List<Language> findAll() {
        var session = HibernateUtil.getSessionFactory().openSession();
        var transaction = session.beginTransaction();
        
        //tworzy zapytanie pobierajace wszystko z tablicy Language
        //kazdy wynik zapisuje jako klase Language
        //nastepnie wrzuca wszystko do listy
        var result = session.createQuery("from Language", Language.class).list();
        
        transaction.commit();
        session.close();
        return result;
    }
}
