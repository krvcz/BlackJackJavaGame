

import java.util.*;

public class Deck {
    private LinkedList<Card> cards;

    public Deck() throws NoValidFigure, NoValidColor {
        this.cards = generateDeck();
    }

    public LinkedList<Card> getCards() {
        return cards;
    }

    public void setCards(LinkedList<Card> cards) {
        this.cards = cards;
    }

    public Card popCard() throws NoValidFigure, NoValidColor {
        try {
            return cards.pop();
        } catch (NoSuchElementException e) {
            cards = generateDeck();
            Collections.shuffle(cards);
            return cards.pop();
        }
    }


    private static LinkedList<Card> generateDeck() throws NoValidFigure, NoValidColor {
        LinkedList <Card> cards = new LinkedList<>();
        ArrayList <String> colors = new ArrayList<>(Arrays.asList("C", "D", "H", "S"));
        ArrayList <String> figures = new ArrayList<>(Arrays.asList("A","2","3","4","5","6","7","8","9","10","J","Q","K"));
        for (String color: colors ) {
            for (String figure: figures) {
                cards.push(new Card(figure, color));
            }

        }




    return cards;
    }

}
