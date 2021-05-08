package mars.mips.instructions.syscalls;

import mars.ProcessingException;
import mars.ProgramStatement;
import mars.ext.mine.MenuScreen;
import mars.mips.hardware.RegisterFile;

public class Syscall129 extends AbstractSyscall{
    public Syscall129() {
        super(129, "show selected tank");
    }

    @Override
    public void simulate(ProgramStatement paramProgramStatement) throws ProcessingException {

        MenuScreen ms  = MenuScreen.getInstance();
        int a0 = RegisterFile.getValue(4); // action
        ms.showSelectedTank(a0);

    }
}
