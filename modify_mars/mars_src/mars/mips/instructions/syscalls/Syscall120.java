package mars.mips.instructions.syscalls;

import mars.ProcessingException;
import mars.ProgramStatement;
import mars.ext.mine.MenuScreen;

public class Syscall120 extends AbstractSyscall {
    public Syscall120() {
        super(120, "Start menu screen");
    }

    @Override
    public void simulate(ProgramStatement paramProgramStatement) throws ProcessingException {
//        MenuScreen menu = new MenuScreen();
        try {
            MenuScreen.createInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
