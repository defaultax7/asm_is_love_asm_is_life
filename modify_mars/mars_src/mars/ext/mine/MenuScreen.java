package mars.ext.mine;

import mars.ext.game.MMIOInput;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MenuScreen extends JFrame {
    private int height = 446;
    private int width = 512;
    private String title = "Battle city(menu)";
    private MMIOInput keyListener;
    private Bg panel;

    private static MenuScreen instance = null;

    public static MenuScreen getInstance() {
        return instance;
    }

    public static synchronized MenuScreen createInstance() throws Exception {
//        kill the old one when creating new
        if (instance != null) {
            instance.dispose();
        }
        instance = new MenuScreen();
        return instance;
    }

    private class Bg extends JPanel {
        private Image cursorImg;
        private Image image;
        private int cursorIndex = 0;
        private int cursorX = 115;
        private int cursorY = 237;
        private int gapY = 42;
        private int maxindex = 3;

        public Bg(Image image) {
//            setup background image (assume selection screen must have background)
            this.image = image;

        }

        public void setCursorImg(Image cursorImg) {
//            setup cursor image
            this.cursorImg = cursorImg;
        }

        public void cursorUp() {
            cursorIndex = (cursorIndex - 1);
            if(cursorIndex < 0){
                cursorIndex += maxindex;
            }
            paintComponent(getGraphics());
            playSoundEffect();
        }

        public void cursorDown() {
            cursorIndex = (cursorIndex + 1) % maxindex;
            paintComponent(getGraphics());
            playSoundEffect();
        }

        private void playSoundEffect(){
            AudioClip clip = Applet.newAudioClip(getClass().getResource("/game/sounds/cursor.wav"));
            clip.play();
        }


        @Override
        protected void paintComponent(Graphics g) {
//            super.paintComponent(g);
//            draw the background
            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);

//            draw the cursor
            g.drawImage(cursorImg, cursorX , cursorY + cursorIndex * gapY, null);
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


    public void move(int action) {
        if (action == 0) {
            panel.cursorUp();
        } else if (action == 1) {
            panel.cursorDown();
        }
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

        panel = new Bg(bg);
        panel.setCursorImg(cursor);
        add(panel);

        setVisible(true);
        setTitle(title);

//        for getting the input
        keyListener = new MMIOInput(true);
        this.addKeyListener(keyListener);
    }
}
