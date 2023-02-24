package com.mycompany.mytasklist.language;

/**
 *
 * @author marcin
 */

//Data Transfer Object, obiekt przechowujace dane do przesylania z serweru
//pobrane z odpowiednich rekordow tablic bazy danych
public class LanguageDTO extends Language {
    
    @SuppressWarnings("unused")
    LanguageDTO() {
        super();
    }

    public LanguageDTO(Language lang) {
        super(lang.getId(), lang.getCode());
    }

    public LanguageDTO(Integer id, String code) {
        super(id, code);
    }

}