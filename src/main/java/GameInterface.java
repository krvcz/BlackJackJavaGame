import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class GameInterface extends JFrame implements ActionListener {
    protected final JButton startButton = new JButton("START GAME");
    protected final JButton exitButton = new JButton("QUIT");
    protected final JButton standButton = new JButton("STAND");
    protected final JButton hitButton = new JButton("HIT");
    protected final JButton blackjackButton = new JButton("BLACKJACK!");
    protected final JButton playAgainButton = new JButton("PLAY AGAIN");
    protected ArrayList<Card> playerCards = new ArrayList<>();
    protected ArrayList<Card> croupierCards = new ArrayList<>();
//    protected boolean summaryFlag;
    private Optional<String> result;

    private final Game game;

    private final Image backgroundImg = new ImageIcon("C:\\Desktop\\blackjack\\src\\main\\resources\\desk.jpg").getImage();

    private final JPanel panel = new JPanel() {

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            int x1 = 200;
            int x2 = 200;
            boolean on = false;
//       background.paintComponent(g);
            Graphics2D background = (Graphics2D) g;
            background.drawImage(backgroundImg, 0, 0, 800, 600, this);
            if (playerCards.size() > 0) {
                for (Card playerCard: playerCards) {
                    Image  img = new ImageIcon("C:\\Desktop\\blackjack\\src\\main\\resources\\img\\deck2\\" + playerCard.getFigure() + playerCard.getColor() + ".png").getImage();
                    Graphics2D carddraw = (Graphics2D) g;
                    carddraw.drawImage(img, x1, 260, 90, 160, this);
                    x1= x1 + 100;
                }
            } else {
                g.setFont(g.getFont().deriveFont(Font.BOLD,90.0F));
                g.drawString("BLACKJACK", 180, 200);

            }
            if (croupierCards.size() > 0) {
                Image  img;
                String text;
                for (Card croupierCard: croupierCards) {

                    if (!game.summaryFlag) {
                        if (x2 == 200) {
                            img = new ImageIcon("C:\\Desktop\\blackjack\\src\\main\\resources\\img\\deck2\\" + croupierCard.getFigure() + croupierCard.getColor() + ".png").getImage();
                        } else {
                            img = new ImageIcon("C:\\Desktop\\blackjack\\src\\main\\resources\\img\\deck2\\" + "back_card" + ".png").getImage();
                        }
                    }
                    else {
                        img = new ImageIcon("C:\\Desktop\\blackjack\\src\\main\\resources\\img\\deck2\\" + croupierCard.getFigure() + croupierCard.getColor() + ".png").getImage();
                    }

                    Graphics2D carddraw = (Graphics2D) g;
                    carddraw.drawImage(img, x2, 60, 90, 160, this);
                    x2 = x2 + 100;
                }
                if (game.summaryFlag) {
                    g.setFont(g.getFont().deriveFont(60.0F));
                    g.setColor(Color.white);
                    if (result.isPresent()) {
                        g.drawString(result.get(), 230, 50);
                        g.setFont(g.getFont().deriveFont(15.0F));
                        g.drawString("CROUPIER SCORE: " + Game.showScore(game.croupier), 590, 50);
                    } else {
                        g.drawString("", 230, 50);
                    }
                }
                g.setFont(g.getFont().deriveFont(15.0F));
                g.setColor(Color.white);
                g.drawString("YOUR SCORE: " + Game.showScore(game.player), 590, 440);
            }
        }



    };

    public GameInterface(Game game) throws IOException {
        super("BlackJack");

        this.game = game;

        startButton.setBounds(300,250,200, 80);
        exitButton.setBounds(300,350,200, 80);
        standButton.setBounds(50, 450, 200, 80);
        hitButton.setBounds(550, 450, 200, 80);
        blackjackButton.setBounds(300, 450, 200, 80);
        playAgainButton.setBounds(550, 450, 200, 80);
        startButton.setVisible(true);
        exitButton.setVisible(true);
        blackjackButton.setBackground(Color.red );
        blackjackButton.setVisible(false);
        playAgainButton.setVisible(false);
        standButton.setVisible(false);
        hitButton.setVisible(false);
        configureActionButton();
        panel.setLayout(null);
        panel.setSize(800, 600);
        panel.add(exitButton);
        panel.add(startButton);
        panel.add(standButton);
        panel.add(hitButton);
        panel.add(blackjackButton);
        panel.add(playAgainButton);
        add(panel);
        setLayout(null);

        setSize(800,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);


        repaint();
        panel.repaint();




    }


    public void generateCards(ArrayList<Card> playerCards, ArrayList<Card> croupierCards)  {

        this.playerCards.clear();
        this.playerCards.addAll(playerCards);

        this.croupierCards.clear();
        this.croupierCards.addAll(croupierCards);
        repaint();
    }



    public void getSummary(Optional<String> result) {
        game.summaryFlag = true;
        game.blackjackFlag = false;
        this.result = result;
        startButton.setVisible(false);
        exitButton.setVisible(false);
        blackjackButton.setVisible(false);
        standButton.setVisible(false);
        hitButton.setVisible(false);
        playAgainButton.setVisible(true);
        panel.repaint();
    }

    public void initGameInterface() {

        game.summaryFlag = false;
        startButton.setVisible(false);
        exitButton.setVisible(false);
        blackjackButton.setVisible(false);
        standButton.setVisible(true);
        hitButton.setVisible(true);
        playAgainButton.setVisible(false);
        panel.repaint();

    }

    private void configureActionButton(){
        startButton.addActionListener(this);
        startButton.setActionCommand("Start");
        exitButton.addActionListener(this);
        exitButton.setActionCommand("Quit");
        standButton.addActionListener(this);
        standButton.setActionCommand("Stand");
        hitButton.addActionListener(this);
        hitButton.setActionCommand("Hit");
        blackjackButton.addActionListener(this);
        blackjackButton.setActionCommand("BlackJack");
        playAgainButton.addActionListener(this);
        playAgainButton.setActionCommand("PlayAgain");
    }


    @Override
    public void actionPerformed(ActionEvent e) {


        String action = e.getActionCommand();
        if (action.equals("Start")) {
            initGameInterface();
            try {
                game.initGame();
            } catch (NoValidFigure | NoValidColor ex) {
                throw new RuntimeException(ex);
            }
            generateCards(game.player.cards, game.croupier.cards);
            panel.repaint();
        }

        if (action.equals("Quit")) System.exit(0);

        if (action.equals("Stand")) {
            generateCards(game.player.cards, game.croupier.cards);
            getSummary(Optional.ofNullable(game.gameSummary()));
            repaint();

        }

        if (action.equals("Hit")) {
            try {
                game.player.takeCard(game.deck);
            } catch (NoValidFigure | NoValidColor ex) {
                throw new RuntimeException(ex);
            }
            generateCards(game.player.cards, game.croupier.cards);
            game.checkStatus();
            repaint();

        }

        if (action.equals("BlackJack"))
        {

            getSummary(Optional.ofNullable(game.gameSummary()));
            repaint();
        }



        if (action.equals("PlayAgain")) {
            initGameInterface();
            try {
                game.initGame();
            } catch (NoValidFigure | NoValidColor ex) {
                throw new RuntimeException(ex);
            }
            generateCards(game.player.cards, game.croupier.cards);
            repaint();

        }
        if (!(game.summaryFlag)) {
            try {
                game.croupier.autoDecision(game.deck, Integer.parseInt(Game.showScore(game.croupier)));
            } catch (NoValidFigure | NoValidColor ex) {
                throw new RuntimeException(ex);
            }
        } else {
            getSummary(Optional.ofNullable(game.gameSummary()));
            panel.repaint();
        }

        if (game.blackjackFlag) {
            blackjackButton.setVisible(true);
        }
    }
}