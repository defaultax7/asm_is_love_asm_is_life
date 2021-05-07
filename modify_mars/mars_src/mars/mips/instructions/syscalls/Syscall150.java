package mars.mips.instructions.syscalls;

import mars.ProcessingException;
import mars.ProgramStatement;
import mars.ext.game.GameScreen;
import mars.util.SystemIO;

public class Syscall150 extends AbstractSyscall {
    public Syscall150() {
        super(150, "create survive");
    }

    @Override
    public void simulate(ProgramStatement paramProgramStatement) throws ProcessingException {
        try {
            GameScreen gs = GameScreen.createIntance("/game/survive_properties.txt");
            gs.finish_waiting();
        } catch (Exception exception) {
            SystemIO.printString("In Creating Game, internal error has happened. Try restarting the MARS!");
            throw new ProcessingException();
        }
    }
}
