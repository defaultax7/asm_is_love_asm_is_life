package mars.ext.mine;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class MenuScreen extends JFrame {
    private int height = 446;
    private int width = 512;
    private String title = "Battle city(menu)";

    private class Bg extends JPanel {
        private Image cursorImg;
        private Image image;
        private int cursorIndex = 0;
        private int cursorX = 118;
        private int cursorY = 237;

        public Bg(Image image) {
//            setup background image (assume selection screen must have background)
            this.image = image;
        }

        public void setCursorImg(Image cursorImg) {
//            setup cursor image
            this.cursorImg = cursorImg;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
//            draw the background
            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);

//            draw the cursor
            g.drawImage(cursorImg, cursorX, cursorY, null);
        }
    }

    public MenuScreen() throws HeadlessException {
        init();
        playIntroMusic();
    }

    private void playIntroMusic() {
        AudioClip clip = Applet.newAudioClip(getClass().getResource("/game/sounds/start.wav"));
        clip.play();
    }

    public void init() {
        File bgImg = new File("game/images/menu.jpg");
        File cursorImg = new File("game/images/cursor.png");
        BufferedImage bg = null;
        BufferedImage cursor = null;
        try {
            bg = ImageIO.read(bgImg);
            cursor = ImageIO.read(cursorImg);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Can not load background image");
        }

        Dimension d = new Dimension(width, height);
        setSize(d);

        Bg panel = new Bg(bg);
        panel.setCursorImg(cursor);
        add(panel);

        setVisible(true);
        setTitle(title);
    }
}
