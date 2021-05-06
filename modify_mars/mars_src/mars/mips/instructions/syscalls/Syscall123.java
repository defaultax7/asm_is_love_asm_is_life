package mars.mips.instructions.syscalls;

import mars.ProcessingException;
import mars.ProgramStatement;
import mars.ext.game.GameScreen;

public class Syscall123 extends AbstractSyscall{
    public Syscall123() {
        super(123, "close waiting");
    }

    @Override
    public void simulate(ProgramStatement paramProgramStatement) throws ProcessingException {
        GameScreen gs = GameScreen.getInstance();
        gs.finish_waiting();
    }
}
