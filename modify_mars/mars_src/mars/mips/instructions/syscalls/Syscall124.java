package mars.mips.instructions.syscalls;

import mars.ProcessingException;
import mars.ProgramStatement;
import mars.ext.mine.MenuScreen;

public class Syscall124 extends AbstractSyscall{
    public Syscall124() {
        super(124, "score screen toggle");
    }

    @Override
    public void simulate(ProgramStatement paramProgramStatement) throws ProcessingException {
        MenuScreen ms = MenuScreen.getInstance();
        ms.toggleScoreScreen();
    }
}
