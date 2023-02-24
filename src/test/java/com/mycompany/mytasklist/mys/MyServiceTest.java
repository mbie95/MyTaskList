package com.mycompany.mytasklist.mys;

import com.mycompany.mytasklist.mys.MyService;
import com.mycompany.mytasklist.language.Language;
import com.mycompany.mytasklist.language.LanguageRepository;

import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
/**
 *
 * @author marcin
 */
public class MyServiceTest {
    private final static String MOCK_WELCOME = "Hi";
    
    private LanguageRepository getMockLanguageRepository() {
        //tworzymy obiekt, w ktorym podmieniamy dane z LanguageRepository
        //specjalnie skonfigurowane do testow
        return new LanguageRepository() {
            //przeciazenie metody, aby zawsze zwracala konkretny lang
            @Override
            public Optional<Language> findById(Integer id) {
                //optional of, a nie ofNullable, bo mamy pewnosc, ze obiekt
                //istnieje, bo wlasnie go tworzymy
                return Optional.of(new Language(null, MOCK_WELCOME, null));
            }
        };
    }
    
    @Test
    public void testGreeting_nullNamenullLang_returnsGreetingWithDefaultNameMockLang() throws Exception {
        //given
        var mockRepository = getMockLanguageRepository();
        //SUT - system under test
        //tworzymy serwis z mockowany repozytorium, aby miec pewnosc,
        //ze ono istnieje
        MyService SUT = new MyService(mockRepository);
        
        //when
        String result = SUT.greeting(null, "-1");
        
        //then
        assertEquals(MOCK_WELCOME + ' ' + MyService.defaultName + '!', result);
    }
    
    @Test
    public void testGreeting_gotNamenullLang_returnsGreetingWithNameMockLang() throws Exception {
        //given
        var mockRepository = getMockLanguageRepository();
        MyService SUT = new MyService(mockRepository);
        String name = "TwojeImie";
        
        //when
        String result = SUT.greeting(name, "-1");
        
        //then
        assertEquals(MOCK_WELCOME + ' ' + name + '!', result);
    }
    
    @Test
    //przypadek, w ktorym uzytkownik poda tekstowy id jezyka
    public void testGreeting_gotNametextLang_returnsGreetingWithNameMockLang() throws Exception {
        //given
        var mockRepository = getMockLanguageRepository();
        MyService SUT = new MyService(mockRepository);
        String name = "TwojeImie";
        
        //when
        String result = SUT.greeting(name, "a");
        
        //then
        assertEquals(MOCK_WELCOME + ' ' + name + '!', result);
    }
    
    @Test
    //przypadek, w ktorym repozytorium nie dziala
    public void testGreeting_gotNamenotExistingLang_returnsGreetingWithNameDefaultLang() throws Exception {
        //given
        var emptyRepository = new LanguageRepository() {
            //przeciazenie metody, aby nie zwracala jezyka
            @Override
            public Optional<Language> findById(Integer id) {
                //nie zwracamy zadnego jezyka
                return Optional.empty();
            }
        };
        MyService SUT = new MyService(emptyRepository);
        String name = "TwojeImie";
        
        //when
        String result = SUT.greeting(name, "-1");
        
        //then
        //zahardkodowany domyslny jezyk w serwisie, metoda zwraca slowo powitalne
        assertEquals(SUT.defaultLanguage.getMessage() + ' ' + name + '!', result);
    }
    
}
