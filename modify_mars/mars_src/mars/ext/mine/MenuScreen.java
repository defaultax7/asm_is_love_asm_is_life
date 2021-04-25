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
        private Image image;

        public Bg(Image image) {
            this.image = image;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
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
        BufferedImage bg = null;
        try {
            bg = ImageIO.read(bgImg);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Can not load background image");
        }

        Dimension d = new Dimension(width, height);
        setSize(d);

        JPanel panel = new Bg(bg);
        add(panel);

        setVisible(true);
        setTitle(title);
    }
}
