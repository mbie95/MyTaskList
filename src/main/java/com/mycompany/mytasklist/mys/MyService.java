package com.mycompany.mytasklist.mys;

import com.mycompany.mytasklist.language.Language;
import com.mycompany.mytasklist.language.LanguageRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 *
 * @author marcin
 */
//tworzenie serwisu, reprezentuje on kolejna wartwe, odpowiadajaca za logike biznesowa
//przygotowuje nasze powitanie
public class MyService {
    //zmienna przechowujaca, co zwrocic w przypadku braku parametru name
    public static final String defaultName = "world";
    public static final Language defaultLanguage = new Language(1, "Hello", "en");
    private LanguageRepository repository;
    private final Logger logger = LoggerFactory.getLogger(MyService.class);
    
    public MyService() {
        this(new LanguageRepository());
    }
    
    public MyService(LanguageRepository repository) {
        this.repository = repository;
    }

    public String greeting(String name, String langId) {
        //konwersja na Long w przypadku Stringa nie nullowego
        //w przeciwnym wypadku wartosc domysla id jezyka
        Integer id;
        try {
            id = Optional.ofNullable(langId).map(Integer::valueOf).orElse(defaultLanguage.getId());
        }
        //w przypadku podania tekstowego id jezyka, zwracany jest wyjatek, ze
        //nie mozna skonwertowac na Longa
        catch (NumberFormatException e) {
            //warning
            logger.warn("Non numeric language id used: " + langId);
            id = defaultLanguage.getId();
        }
        //pobieramy z repozytorium odpowiednia wiadomosc dla naszego jezyka
        String welcomeMsg = repository.findById(id).orElse(defaultLanguage).getMessage();
        //optional chroni przed wprowadzeniem wartosci NULL
        String welcomeName = Optional.ofNullable(name).orElse(defaultName);
        return welcomeMsg + " " + welcomeName + "!";
    }
}
