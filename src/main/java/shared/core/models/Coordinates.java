package shared.core.models;

import server.core.Invoker;
import shared.core.exceptions.FieldValueIsNotCorrectException;

import java.io.Serializable;
import java.util.Objects;

public class Coordinates implements Serializable {
    private final double Y_MAX_LIMIT = 3000;
    private final double X_MAX_LIMIT = 3000;
    private int x;
    private double y;

    private Coordinates(){}

    public Coordinates(int x, double y) {
        setX(x);
        setY(y);
    }

    public int getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(int x) {
        if ((x<=X_MAX_LIMIT && x > 0) || Invoker.getIsDataLoading()){
            this.x = x;
        }
        else {
            throw new FieldValueIsNotCorrectException();
        }
    }

    public void setY(double y) {
        if ((y<=Y_MAX_LIMIT && y > 0) || Invoker.getIsDataLoading()){
            this.y = y;
        }
        else {
            throw new FieldValueIsNotCorrectException();
        }
    }

    @Override
    public String toString() {
        return String.format("\n---X: %s\n---Y: %s",x,y);
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }
        if (this.getClass() != obj.getClass()){
            return false;
        }
        if (obj == null){
            return false;
        }
        Coordinates coordinates = (Coordinates)obj;
        return coordinates.x == this.x
                && coordinates.y == this.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x,y);
    }
}
