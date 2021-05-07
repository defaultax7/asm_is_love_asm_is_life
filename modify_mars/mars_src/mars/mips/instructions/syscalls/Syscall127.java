package mars.mips.instructions.syscalls;

import mars.ProcessingException;
import mars.ProgramStatement;
import mars.ext.game.GameScreen;

public class Syscall127 extends AbstractSyscall{
    public Syscall127() {
        super(127, "danger!");
    }

    @Override
    public void simulate(ProgramStatement paramProgramStatement) throws ProcessingException {
        GameScreen gs = GameScreen.getInstance();
//        change bgm
        gs.stopSound(7);
        gs.playSound(8,true);
    }
}
