import javax.swing.*;
import java.awt.*;

public class Background extends JComponent {
    Image img;

    public Background() {
        super();
        img = new ImageIcon("C:\\Desktop\\blackjack\\src\\main\\resources\\desk.jpg").getImage();


    }

    public void paintComponent(Graphics g) {
        Graphics2D background = (Graphics2D) g;
        background.drawImage(img, 0, 0, 800, 600, this);

    }

}