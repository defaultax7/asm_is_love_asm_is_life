package mars.mips.instructions.syscalls;

import mars.Globals;
import mars.ProcessingException;
import mars.ProgramStatement;
import mars.ext.mine.MenuScreen;
import mars.mips.hardware.AddressErrorException;
import mars.mips.hardware.RegisterFile;

public class Syscall126 extends AbstractSyscall {
    public Syscall126() {
        super(126, "fill score screen");
    }

    @Override
    public void simulate(ProgramStatement paramProgramStatement) throws ProcessingException {

        MenuScreen ms = MenuScreen.getInstance();

        String str = new String();
        int a0 = RegisterFile.getValue(4);
        char[] arrayOfChar = {' '};

        try {
            arrayOfChar[0] = (char) Globals.memory.getByte(a0);
            while (arrayOfChar[0] != '\000') {
                str = str.concat(new String(arrayOfChar));
                a0++;
                arrayOfChar[0] = (char) Globals.memory.getByte(a0);
            }

            ms.fillScoreScreen(str.split(" "));


        } catch (AddressErrorException addressErrorException) {

            throw new ProcessingException(paramProgramStatement, addressErrorException);
        }


    }
}
