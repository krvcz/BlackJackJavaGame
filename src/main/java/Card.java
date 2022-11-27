import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class NoValidFigure extends Exception {

    NoValidFigure() {
        super("This type of Figure doesn't exist in accepted list");
    }
}

class NoValidColor extends Exception {

    NoValidColor() {
        super("This Color doesn't exist in accepted list");
    }
}



public class Card {
    private String figure;
    private String color;

    public static final List<String> VALID_FIGURES = List.of("A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K");
    public static final  List<String> VALID_COLORS = List.of("C", "D", "H", "S");


    public String getFigure() {
        return figure;
    }

    public void setFigure(String figure) throws NoValidFigure {

        if(VALID_FIGURES.contains(figure))
        {
            this.figure = figure;
        }
        else {
            throw new NoValidFigure();
        }

    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) throws NoValidColor {

        if (VALID_COLORS.contains(color)) {
            this.color = color;
        } else {
            throw new NoValidColor();
        }
    }

    @Override
    public String toString() {
        return "Card{" +
                "figure='" + figure + '\'' +
                ", color='" + color + '\'' +
                '}';
    }

    public Card(String figure, String color) throws NoValidFigure, NoValidColor {

        if(VALID_FIGURES.contains(figure))
        {
            this.figure = figure;
        }
        else {
            throw new NoValidFigure();
        }
        if (VALID_COLORS.contains(color)) {
            this.color = color;
        } else {
            throw new NoValidColor();
        }




    }



    }

