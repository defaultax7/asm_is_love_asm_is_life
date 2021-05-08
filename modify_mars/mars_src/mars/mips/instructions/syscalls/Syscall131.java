package mars.mips.instructions.syscalls;

import mars.ProcessingException;
import mars.ProgramStatement;
import mars.ext.game.GameConfigFile;
import mars.ext.game.GameObject;
import mars.ext.game.GameScreen;
import mars.ext.game.MazeGameObject;
import mars.mips.hardware.RegisterFile;

public class Syscall131 extends AbstractSyscall{
    public Syscall131() {
        super(131, "create enemy with location");
    }

    @Override
    public void simulate(ProgramStatement paramProgramStatement) throws ProcessingException {
        int a0 = RegisterFile.getValue(4); // id of enemy
        int a1 = RegisterFile.getValue(5); // x location of enemy
        int a2 = RegisterFile.getValue(6); // x location of enemy

        System.out.println(a1);
        System.out.println(a2);
        System.out.println();

        GameConfigFile.properties.setProperty("numEnemy",""+1);

        GameScreen gameScreen = GameScreen.getInstance();
        MazeGameObject enemy1 = new MazeGameObject(a0, a1+32, a2+16, 2);
        enemy1.direction_=2;
        enemy1.incImageIndex();
        enemy1.pair=a0;
        enemy1.enemyBulletNum=1;
        MazeGameObject bullet1=new MazeGameObject(a0,1000,1000,4);
        gameScreen.addGameObject(a0, (GameObject)enemy1, 2);
        gameScreen.addGameObject(a0, (GameObject)bullet1, 4);
    }
}
