import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;


public class GameTest {

    static Game game;

    static Human mockedPlayer;

    static Croupier mockedCroupier;

    static Deck mockedDeck;


    @BeforeAll
    public static void initGame() throws NoValidFigure, NoValidColor, IOException {
        mockedDeck = mock(Deck.class);
        mockedCroupier = mock(Croupier.class);
        mockedPlayer = mock(Human.class);
        mockedCroupier.cards = new ArrayList<>();
        mockedPlayer.cards = new ArrayList<>();


        doAnswer(invocationOnMock -> {
           Croupier croupier = (Croupier) invocationOnMock.getMock();
            croupier.cards = new ArrayList<>(List.of(new Card("A", "C"), new Card("2", "D")));
            return null;
        }).when(mockedCroupier).takeCard(mockedDeck, 2);


        doAnswer(invocationOnMock -> {
       Human player = (Human) invocationOnMock.getMock();
            player.cards = new ArrayList<>(List.of(new Card("A", "C"), new Card("2", "D")));
            return null;
        }).when(mockedPlayer).takeCard(mockedDeck, 2);


        game = new Game(mockedCroupier, mockedPlayer, mockedDeck);
    }

    @Test
    public void shouldPrepareToNewHandingOut() throws NoValidFigure, NoValidColor {
        game.initGame();
        verify(mockedCroupier, times(1)).removeCard();
        verify(mockedPlayer, times(1)).removeCard();
        verify(mockedCroupier, times(1)).shuffleCards(mockedDeck);
        verify(mockedCroupier, times(1)).takeCard(mockedDeck, 2);
        verify(mockedPlayer, times(1)).takeCard(mockedDeck, 2);
        assertFalse(game.summaryFlag);
        assertFalse(game.blackjackFlag);

    }

    @Test
    public void shouldReturn21Points() throws NoValidFigure, NoValidColor {
        Human player = new Human();
        Croupier croupier = new Croupier();


        croupier.cards.add(new Card("K", "D"));
        croupier.cards.add(new Card("A", "D"));
        player.cards.add(new Card("A", "D"));
        player.cards.add(new Card("K", "D"));


        assertEquals("21", Game.showScore(player));
        assertEquals("21", Game.showScore(croupier));

        croupier.cards.add(new Card("A", "D"));
        player.cards.add(new Card("A", "D"));

        assertEquals("21", Game.showScore(player));
        assertEquals("21", Game.showScore(croupier));

        croupier.removeCard();
        player.removeCard();

        croupier.cards.add(new Card("10", "D"));
        croupier.cards.add(new Card("8", "D"));
        croupier.cards.add(new Card("3", "D"));

        player.cards.add(new Card("10", "D"));
        player.cards.add(new Card("8", "D"));
        player.cards.add(new Card("3", "D"));

        assertEquals("21", Game.showScore(player));
        assertEquals("21", Game.showScore(croupier));

        croupier.removeCard();
        player.removeCard();

        player.cards.add(new Card("A", "D"));
        croupier.cards.add(new Card("A", "D"));

        player.cards.add(new Card("K", "D"));
        croupier.cards.add(new Card("K", "D"));

        player.cards.add(new Card("K", "D"));
        croupier.cards.add(new Card("K", "D"));

        assertEquals("21", Game.showScore(player));
        assertEquals("21", Game.showScore(croupier));


    }

    @Test
    public void shouldReturn0Points() throws NoValidFigure, NoValidColor {

        Human player = new Human();
        Croupier croupier = new Croupier();


        assertEquals("0", Game.showScore(player));
        assertEquals("0", Game.showScore(croupier));

        player.cards.add(new Card("A", "D"));
        croupier.cards.add(new Card("A", "D"));
        player.removeCard();
        croupier.removeCard();

        assertEquals("0", Game.showScore(player));
        assertEquals("0", Game.showScore(croupier));

    }


    @Test
    public void shouldReturnLessThan21Points() throws NoValidFigure, NoValidColor {

        Human player = new Human();
        Croupier croupier = new Croupier();

        player.cards.add(new Card("A", "D"));
        croupier.cards.add(new Card("A", "D"));

        player.cards.add(new Card("K", "D"));
        croupier.cards.add(new Card("K", "D"));

        player.cards.add(new Card("3", "D"));
        croupier.cards.add(new Card("3", "D"));


        assertEquals("14", Game.showScore(player));
        assertEquals("14", Game.showScore(croupier));

    }


    @Test
    public void shouldReturnMoreThan21Points() throws NoValidFigure, NoValidColor {

        Human player = new Human();
        Croupier croupier = new Croupier();

        player.cards.add(new Card("A", "D"));
        croupier.cards.add(new Card("A", "D"));

        player.cards.add(new Card("K", "D"));
        croupier.cards.add(new Card("K", "D"));

        player.cards.add(new Card("Q", "D"));
        croupier.cards.add(new Card("Q", "D"));

        player.cards.add(new Card("10", "D"));
        croupier.cards.add(new Card("10", "D"));


        assertEquals("31", Game.showScore(player));
        assertEquals("31", Game.showScore(croupier));

    }

    @Test
    public void shouldReturnWinStatement() throws NoValidFigure, NoValidColor {
        String result;
        mockedPlayer.cards.clear();
        mockedCroupier.cards.clear();

        mockedPlayer.cards.add(new Card("A", "D"));
        mockedPlayer.cards.add(new Card("K", "D"));

        result = game.gameSummary();

        assertEquals("YOU WON!", result);


        mockedCroupier.cards.add(new Card("A", "D"));
        mockedCroupier.cards.add(new Card("K", "D"));
        mockedCroupier.cards.add(new Card("10", "D"));

        assertEquals("YOU WON!", result);

        mockedPlayer.cards.clear();
        mockedCroupier.cards.clear();

        mockedPlayer.cards.add(new Card("A", "D"));
        mockedPlayer.cards.add(new Card("K", "D"));
        mockedPlayer.cards.add(new Card("Q", "D"));
        mockedPlayer.cards.add(new Card("3", "D"));


        mockedCroupier.cards.add(new Card("A", "D"));
        mockedCroupier.cards.add(new Card("K", "D"));
        mockedCroupier.cards.add(new Card("Q", "D"));
        mockedCroupier.cards.add(new Card("2", "D"));

        assertEquals("YOU WON!", result);

    }

}
