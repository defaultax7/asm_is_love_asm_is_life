package mars.ext.mine;

import mars.ext.game.MMIOInput;

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
    private MMIOInput keyListener;
    private Bg panel;
    private AudioClip introMusic;

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
        private Image scoreBgImg;
        private int cursorIndex = 0;
        private int cursorX = 115;
        private int cursorY = 237;
        private int gapY = 42;
        private int maxindex = 3;
        private boolean checkingScore = false;

        public Bg(Image image) {
//            setup background image (assume selection screen must have background)
            this.image = image;

        }

        public void setCursorImg(Image cursorImg) {
//            setup cursor image
            this.cursorImg = cursorImg;
        }

        public void setScoreBgImg(Image scoreBg){
            this.scoreBgImg = scoreBg;
        }

//        public void cursorUp() {
//            cursorIndex = (cursorIndex - 1);
//            if (cursorIndex < 0) {
//                cursorIndex += maxindex;
//            }
//            paintComponent(getGraphics());
//            playSoundEffect();
//        }
//
//        public void cursorDown() {
//            cursorIndex = (cursorIndex + 1) % maxindex;
//            paintComponent(getGraphics());
//            playSoundEffect();
//        }

        public void moveCursor(int x, int y) {
            cursorX = x;
            cursorY = y;
            paintComponent(getGraphics());
            playSoundEffect();
        }

        public void changeAction(int action) {
            cursorIndex = action;
        }

        private void playSoundEffect() {
            AudioClip clip = Applet.newAudioClip(getClass().getResource("/game/sounds/cursor.wav"));
            clip.play();
        }


        @Override
        protected void paintComponent(Graphics g) {
//            super.paintComponent(g);
//            draw the background
            g.drawImage(image, 0, 0, getWidth(), getHeight(), this);

//            draw the cursor
            g.drawImage(cursorImg, cursorX, cursorY , null);

//            draw the score if the score option is choosen
            if(checkingScore){
                g.drawImage(scoreBgImg, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    public void toggleScoreScreen(){
        panel.checkingScore = !panel.checkingScore;
        paintComponents(getGraphics());
    }

    public MenuScreen() throws HeadlessException {
        init();
        playIntroMusic();
    }

    private void playIntroMusic() {
        introMusic = Applet.newAudioClip(getClass().getResource("/game/sounds/start.wav"));
        introMusic.play();
    }

    public void terminate(){
        introMusic.stop();
        dispose();
    }

    public void move(int a0, int a1, int action) {
        panel.moveCursor(a0, a1);
        panel.changeAction(action);
    }


    public void init() {
        File bgImg = new File("game/images/menu.jpg");
        File cursorImg = new File("game/images/cursor.png");
        File scoreImg = new File("game/images/score.jpg");
        BufferedImage bg = null;
        BufferedImage cursor = null;
        BufferedImage scoreBg = null;
        try {
            bg = ImageIO.read(bgImg);
            cursor = ImageIO.read(cursorImg);
            scoreBg = ImageIO.read(scoreImg);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Can not load background image");
        }

        Dimension d = new Dimension(width, height);
        setSize(d);

        panel = new Bg(bg);
        panel.setCursorImg(cursor);
        panel.setScoreBgImg(scoreBg);
        add(panel);

        setVisible(true);
        setTitle(title);

//        for getting the input
        keyListener = new MMIOInput(true);
        this.addKeyListener(keyListener);
    }
}
