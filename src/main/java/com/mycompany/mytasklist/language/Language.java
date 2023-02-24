package com.mycompany.mytasklist.language;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
/**
 *
 * @author marcin
 */
//adnotacje mapujace obiekt klasy na tabele w bazie danych (ORM)
@Entity
@Table( name = "LANGUAGE" )
//klasa, a dokladniej pojo, ktore umozliwi korzystanie z roznych jezykow
public class Language {
    //long, ktory zawiera nulla w przypadku, kiedy nie ma 
    //jeszcze naszego elementu w tablicy bazy danych
    //id zawiera kod jezyka, w ktorym urzytkownik chce dzialac
    @Id
    //inc to nazwa generatora, ktory informuje o strategii (increment)
    //nadawania id dla Language
    @GeneratedValue(generator="inc")
    @GenericGenerator(name="inc", strategy = "increment")
    protected Integer id;
    @Column(name = "welcome_message")
    private String message; //nasza wiadomosc
    protected String code; //kod jezyka
    
    //trzeba stworzyc konstruktor domyslny dla hibernate'a (jpa - java 
    //persistance api, hibernate jest konkretyzacja dla tego standardu)
    @SuppressWarnings("unused")
    Language() {
        
    }
    
    public Language(Integer id, String code) {
        this.id = id;
        this.code = code;
    }
    
    public Language(Integer id, String message, String code) {
        this.id = id;
        this.message = message;
        this.code = code;
    }
    
    public Integer getId() {
        return id;
    }
    
    public String getMessage() {
        return message;
    }
    
    public String getCode() {
        return code;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
}
