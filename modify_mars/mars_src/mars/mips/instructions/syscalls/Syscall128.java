package mars.mips.instructions.syscalls;

import mars.ProcessingException;
import mars.ProgramStatement;
import mars.ext.game.GameScreen;
import mars.ext.game.MazeGameObject;
import mars.mips.hardware.RegisterFile;

public class Syscall128 extends AbstractSyscall{
    public Syscall128() {
        super(128, "change player image");
    }

    @Override
    public void simulate(ProgramStatement paramProgramStatement) throws ProcessingException {

        int a0 = RegisterFile.getValue(4); // selected tank index

        GameScreen gs = GameScreen.getInstance();
        MazeGameObject player = (MazeGameObject) gs.getGameObject(0,1);
        player.changeSprite(a0);
    }
}
