import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;


public class DeckTest {

    private static Deck deck;
    private static Human player;
    private static Croupier croupier;

    @BeforeEach
    public void init() throws NoValidFigure, NoValidColor {
        deck = new Deck();
        player = new Human();
        croupier = new Croupier();

    }

    @Test
    public void shouldReloadDeckWhenIsEmpty() throws NoValidFigure, NoValidColor {
        player.takeCard(deck, 52);
        int numberOfCardsInEmptyDeck = deck.getCards().size();
        assertEquals(0, numberOfCardsInEmptyDeck );
        player.takeCard(deck);
        int numberOfCardsInDeckAfterHitFromEmptyDeck = deck.getCards().size();
        assertEquals(51, numberOfCardsInDeckAfterHitFromEmptyDeck);

    }
    @Test
    public void shouldHave52Cards()  {

        assertEquals(52, deck.getCards().size());

    }

    @Test
    public void shouldHave4Colors() {

        long numberOfColors = deck.getCards().stream().map(Card::getColor).distinct().count();

        assertEquals(4, numberOfColors );
    }

    @Test
    public void shouldHave13Figures(){

        long numberOfFigures = deck.getCards().stream().map(Card::getFigure).distinct().count();

        assertEquals(13, numberOfFigures );
    }

    @Test
    public void eachFigureShouldHave4ColorsOnly(){

        Map<String, Long> collect = deck.getCards().stream()
                .collect(Collectors.toMap(Card::getFigure, x -> deck.getCards()
                        .stream()
                        .filter(y -> y.getFigure().equals(x.getFigure()))
                        .map(Card::getColor)
                        .distinct()
                        .count(), (first, last) -> first));

        assertEquals(4, Collections.max(collect.values()));
        assertEquals(4, Collections.min(collect.values()));


    }
}
