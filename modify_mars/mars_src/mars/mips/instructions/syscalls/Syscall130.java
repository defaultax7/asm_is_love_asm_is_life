package mars.mips.instructions.syscalls;

import mars.ProcessingException;
import mars.ProgramStatement;
import mars.ext.mine.MenuScreen;

public class Syscall130 extends AbstractSyscall{
    public Syscall130() {
        super(130, "tank selection toggle");
    }

    @Override
    public void simulate(ProgramStatement paramProgramStatement) throws ProcessingException {
        MenuScreen ms = MenuScreen.getInstance();
        ms.toggleTankSelectionScreen();
    }
}
