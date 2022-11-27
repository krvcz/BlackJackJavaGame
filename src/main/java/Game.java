

import java.io.*;

import java.util.Map;



import static java.util.Map.entry;



    public class Game  {

    public static final Map<String, Integer> SCOREPOINTS =  Map.ofEntries(
    entry("K",        10),
    entry("Q",        10),
    entry("J",        10),
    entry("10",       10),
    entry("9",       9),
    entry("8",       8),
    entry("7",       7),
    entry("6",       6),
    entry("5",       5),
    entry("4",       4),
    entry("3",       3),
    entry("2",       2),
    entry("A",       1)
    );

    protected final Human player;
    protected final Croupier croupier;
    protected final Deck deck;
    protected boolean summaryFlag;
    protected boolean blackjackFlag;




    public Game() throws  NoValidFigure, NoValidColor {
        croupier = new Croupier();
        player = new Human();
        deck = new Deck();

        checkStatus();


    }

    public Game(Croupier croupier, Human player, Deck deck) {
        this.croupier = croupier;
        this.player = player;
        this.deck = deck;
       checkStatus();


    }



        public void initGame() throws NoValidFigure, NoValidColor {
        summaryFlag = false;
        player.removeCard();
        croupier.removeCard();
        croupier.shuffleCards(deck);
        croupier.takeCard(deck, 2 );
        player.takeCard(deck, 2 );
        summaryFlag = false;
        blackjackFlag = false;
        checkStatus();

    }




    public static String showScore(Player player) {


       long special_figures_number = player.cards.stream()
                .filter(card -> card.getFigure().equals("K")
                        || card.getFigure().equals("Q")
                        || card.getFigure().equals("J"))
               .count();

        long aces_number = player.cards.stream()
                .filter(card -> card.getFigure().equals("A"))
                .count();

        if (special_figures_number == 1 && aces_number == 1 && player.cards.size() == 2) {
            return "21";
        }

        if (aces_number > 1) {
            return "21";
        }

        return String.valueOf(player.cards.stream()
                .map(card -> SCOREPOINTS.get(card.getFigure()))
                .reduce(0, Integer::sum));

    }

    public String gameSummary() {
        summaryFlag = true;
        String result =  "YOU LOSE!";
        if (showScore(player).equals("21"))
        {
            result =  "YOU WON!";
            return result;
        }
        if (Integer.parseInt(showScore(croupier)) > 21  && Integer.parseInt(showScore(player)) < 21)  {
            result =  "YOU WON!";
            return result;
        }
        if (Integer.parseInt(showScore(croupier)) ==  Integer.parseInt(showScore(player))) {
            result =  "TIE!";
            return result;
        }
        if (Integer.parseInt(showScore(player)) > Integer.parseInt(showScore(croupier)) && Integer.parseInt(showScore(player)) < 21) {
            result =  "YOU WON!";
            return result;
        }
        return result;

    }

    public void checkStatus() {
        if (Integer.parseInt(showScore(croupier)) > 21 || Integer.parseInt(showScore(player)) > 21 ) {
            summaryFlag = true;
        }
        if (Integer.parseInt(showScore(player)) == 21 ) {
            blackjackFlag = true;
        }

    }





    public static void main(String[] args) throws IOException, NoValidFigure, NoValidColor {
         new GameInterface(new Game());


    }

}
