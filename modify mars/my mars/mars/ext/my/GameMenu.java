package mars.ext.my;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.nio.Buffer;

public class GameMenu extends JFrame {
    private int height = 223*2;
    private int width = 256*2;
    private String title = "Battle city (menu)";

    class PanelBG extends JPanel {

        Image bg;

        PanelBG(Image bg) {
            this.bg = bg;
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public void init(){
        File bgImg = new File("game/images/menu.png");
        BufferedImage bg = null;
        try {
            bg = ImageIO.read(bgImg);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Dimension d = new Dimension(width, height);

        setSize(d);

        JPanel jPanel = new PanelBG(bg);
        add(jPanel);
//        pack();
        setVisible(true);
        setTitle(title);
    }
}