
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class PlayerTest {

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
    public void checkNumberOfCardsOnHandBeforeInitializeGame() {
        int numberOfCardsPlayer = player.cards.size();
        int numberOfCardsCroupier = croupier.cards.size();
        assertEquals(0, numberOfCardsPlayer);
        assertEquals(0, numberOfCardsCroupier);
    }


    @Test
    public void checkNumberOfCardsOnHandAfterInitializeGame() throws NoValidFigure, NoValidColor {
        player.takeCard(deck, 2);
        croupier.takeCard(deck, 2);
        int numberOfCardsPlayer = player.cards.size();
        int numberOfCardsCroupier = croupier.cards.size();
        assertEquals(2, numberOfCardsPlayer);
        assertEquals(2, numberOfCardsCroupier);
    }

    @Test
    public void checkCardOnHandAfterHit() throws NoValidFigure, NoValidColor {
        player.takeCard(deck, 2);
        croupier.takeCard(deck, 2);
        player.takeCard(deck);
        croupier.takeCard(deck);
        int numberOfCardsPlayer = player.cards.size();
        int numberOfCardsCroupier = croupier.cards.size();
        assertEquals(3, numberOfCardsPlayer);
        assertEquals(3, numberOfCardsCroupier);
    }

    @Test
    public void checkCardOnHandBeforeNewDeal() throws NoValidFigure, NoValidColor {
        player.takeCard(deck, 2);
        croupier.takeCard(deck, 2);
        player.takeCard(deck);
        croupier.takeCard(deck);
        player.removeCard();
        croupier.removeCard();
        int numberOfCardsPlayer = player.cards.size();
        int numberOfCardsCroupier = croupier.cards.size();
        assertEquals(0, numberOfCardsPlayer);
        assertEquals(0, numberOfCardsCroupier);

    }

    @Test
    public void shouldHitCardFromEmptyDeck() throws NoValidFigure, NoValidColor {
        player.takeCard(deck, 52);
        int numberOfCardsInEmptyDeck = deck.getCards().size();
        assertEquals(0, numberOfCardsInEmptyDeck );
        player.takeCard(deck);
        int numberOfCardsOnHandPlayer = player.cards.size();
        assertEquals(53, numberOfCardsOnHandPlayer);

    }

    @Test
    public void shouldShuffleCards() {
        List<Card> notShuffledCards =  deck.getCards().stream().toList();
        croupier.shuffleCards(deck);
        List<Card> shuffledCards =  deck.getCards().stream().toList();

        boolean isTheSameList = notShuffledCards.equals(shuffledCards);

        assertFalse(isTheSameList);


    }

    @Test
    public void shouldHitBelowOrEquals17ScorePoints() throws NoValidFigure, NoValidColor {
        int scorePoints = 16;
        croupier.autoDecision(deck, scorePoints);
        assertEquals(1, croupier.cards.size());
        scorePoints = 1;
        croupier.autoDecision(deck, scorePoints);
        assertEquals(2, croupier.cards.size());
        scorePoints = 17;
        croupier.autoDecision(deck, scorePoints);
        assertEquals(3, croupier.cards.size());
        scorePoints = 0;
        croupier.autoDecision(deck, scorePoints);
        assertEquals(4, croupier.cards.size());
    }

    @Test
    public void shouldStandAbove17ScorePoints() throws NoValidFigure, NoValidColor {
        int scorePoints = 18;
        croupier.autoDecision(deck, scorePoints);
        assertEquals(0, croupier.cards.size());
        scorePoints = 21;
        croupier.autoDecision(deck, scorePoints);
        assertEquals(0, croupier.cards.size());
        scorePoints = 20;
        croupier.autoDecision(deck, scorePoints);
        assertEquals(0, croupier.cards.size());
    }

}
