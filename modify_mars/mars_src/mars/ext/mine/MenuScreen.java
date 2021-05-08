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

    public void showSelectedTank(int id) {
        panel.changeSelectedTank(id);
        paintComponents(getGraphics());
    }

    public void toggleTankSelectionScreen() {
        panel.tankSelecting = !panel.tankSelecting;
        paintComponents(getGraphics());
    }

    private class Bg extends JPanel {
        private Image cursorImg;
        private Image image;
        private Image scoreBgImg;
        private Image tankSelectionBgImg;
        private Image selectedTankImg = null;
        private int cursorIndex = 0;
        private int cursorX = 115;
        private int cursorY = 237;
        private int gapY = 42;
        private int maxindex = 3;
        private int tankSelectionX = 180;
        private int tankSelectionY = 170;
        private boolean checkingScore = false;
        private boolean tankSelecting = false;
        private String[] scoreElements = null;
        private int[] elementsX = {27, 127, 227, 315, 27, 127, 227};
        private int[] elementsY = {150, 150, 150, 150, 315, 315, 315};

        public Bg(Image image) {
//            setup background image (assume selection screen must have background)
            this.image = image;

        }

        public void setCursorImg(Image cursorImg) {
//            setup cursor image
            this.cursorImg = cursorImg;
        }

        public void setScoreBgImg(Image scoreBg) {
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
            g.drawImage(cursorImg, cursorX, cursorY, null);

//            draw the score if the score option is choosen
            if (checkingScore) {
                g.drawImage(scoreBgImg, 0, 0, getWidth(), getHeight(), this);

                if (scoreElements != null) {
                    // the text should be white
                    g.setColor(Color.white);
                    g.setFont(new Font("Verdana", Font.BOLD, 14));

                    for (int i = 0; i < scoreElements.length; i++) {
                        g.drawString(scoreElements[i], elementsX[i], elementsY[i]);
                    }
                }
            }
            if (tankSelecting) {
                g.drawImage(tankSelectionBgImg, 0, 0, getWidth(), getHeight(), this);

                g.drawImage(selectedTankImg, tankSelectionX, tankSelectionY, null);
            }
        }

        public void fillScoreScreen(String[] texts) {
            scoreElements = texts;
        }

        public void changeSelectedTank(int id) {

            try {

                selectedTankImg = ImageIO.read(getClass().getResourceAsStream("/game/images/tank" + id + ".png"));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        public void setTankSelectionBgImg(Image tankSelectionImg) {
            tankSelectionBgImg = tankSelectionImg;
        }

        public void setDefaultTank(Image defaultTank) {
            selectedTankImg = defaultTank;
        }
    }

    public void fillScoreScreen(String[] texts) {
        panel.fillScoreScreen(texts);
        paintComponents(getGraphics());
    }

    public void toggleScoreScreen() {
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

    public void terminate() {
        introMusic.stop();
        dispose();
    }

    public void move(int a0, int a1, int action) {
        panel.moveCursor(a0, a1);
        panel.changeAction(action);
    }


    public void init() {
//        File bgImg = new File("game/images/menu.jpg");
//        File cursorImg = new File("game/images/cursor.png");
//        File scoreImg = new File("game/images/score.jpg");
//        File tankSelectionImg = new File("game/images/tank_selection.jpg");
//        File defaultTankImg = new File("game/images/tank0.png");
        BufferedImage bg = null;
        BufferedImage cursor = null;
        BufferedImage scoreBg = null;
        BufferedImage tankSelectionBg = null;
        BufferedImage defaultTank = null;
        try {
            bg = ImageIO.read(getClass().getResourceAsStream("/game/images/menu.jpg"));
            cursor = ImageIO.read(getClass().getResourceAsStream("/game/images/cursor.png"));
            scoreBg = ImageIO.read(getClass().getResourceAsStream("/game/images/score.jpg"));
            tankSelectionBg = ImageIO.read(getClass().getResourceAsStream("/game/images/tank_selection.jpg"));
            defaultTank = ImageIO.read(getClass().getResourceAsStream("/game/images/tank0.png"));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Can not load background image");
        }

        Dimension d = new Dimension(width, height);
        setSize(d);

        panel = new Bg(bg);
        panel.setCursorImg(cursor);
        panel.setScoreBgImg(scoreBg);
        panel.setTankSelectionBgImg(tankSelectionBg);
        panel.setDefaultTank(defaultTank);
        panel.setLayout(null);
        add(panel);

        setVisible(true);
        setTitle(title);
//        setLocationRelativeTo(null); // open in center

//        for getting the input
        keyListener = new MMIOInput(true);
        this.addKeyListener(keyListener);
    }
}
