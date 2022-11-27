

import javax.swing.*;
import java.awt.*;

public class Menu extends JComponent {
    Image img;

    public Menu() {
        super();
        img = new ImageIcon("img\\desk.jpg").getImage();


    }


    public void paintComponent(Graphics g) {

        Graphics2D background = (Graphics2D) g;
        background.drawImage(img, 3, 3, 1470, 980, this);






    }
}
