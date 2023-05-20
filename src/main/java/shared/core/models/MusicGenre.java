package shared.core.models;

import java.io.Serializable;
import java.util.Arrays;

public enum MusicGenre implements Serializable {
    ROCK("Rock"),
    BLUES("Blues"),
    POP("Pop"),
    POST_ROCK("Post rock");
    private String name;
    MusicGenre(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
