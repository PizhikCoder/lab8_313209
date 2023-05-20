package shared.core.models;

import java.io.Serializable;

public enum Country implements Serializable {
    RUSSIA("Russia"),
    GERMANY("Germany"),
    VATICAN("Vatican"),
    ITALY("Italy");
    private String name;
    Country(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
}
