package mars.mips.instructions.syscalls;

import mars.ProcessingException;
import mars.ProgramStatement;
import mars.ext.mine.MenuScreen;

public class Syscall122 extends AbstractSyscall {
    public Syscall122() {
        super(122, "mock");
    }

    @Override
    public void simulate(ProgramStatement paramProgramStatement) throws ProcessingException {
        MenuScreen menu = MenuScreen.getInstance();
        menu.terminate();
    }
}
