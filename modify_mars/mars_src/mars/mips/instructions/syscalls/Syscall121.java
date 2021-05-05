package mars.mips.instructions.syscalls;

import mars.ProcessingException;
import mars.ProgramStatement;
import mars.ext.mine.MenuScreen;
import mars.mips.hardware.RegisterFile;

public class Syscall121 extends AbstractSyscall {
    public Syscall121() {
        super(121, "move cursor");
    }

    @Override
    public void simulate(ProgramStatement paramProgramStatement) throws ProcessingException {
//        get $a0
        int i = RegisterFile.getValue(4);

        MenuScreen menu = MenuScreen.getInstance();

        menu.move(i);

    }
}
