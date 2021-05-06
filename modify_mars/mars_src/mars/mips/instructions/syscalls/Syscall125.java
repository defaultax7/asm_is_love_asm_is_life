package mars.mips.instructions.syscalls;

import mars.Globals;
import mars.ProcessingException;
import mars.ProgramStatement;
import mars.mips.hardware.AddressErrorException;
import mars.mips.hardware.RegisterFile;

import java.io.File;
import java.io.IOException;

public class Syscall125 extends AbstractSyscall {
    public Syscall125() {
        super(125, "create file");
    }

    @Override
    public void simulate(ProgramStatement paramProgramStatement) throws ProcessingException {
        String fileName = new String();
        int i = RegisterFile.getValue(4);
        char[] arrayOfChar = {' '};

        try {
            arrayOfChar[0] = (char) Globals.memory.getByte(i);
            while (arrayOfChar[0] != '\000') {
                fileName = fileName.concat(new String(arrayOfChar));
                i++;
                arrayOfChar[0] = (char) Globals.memory.getByte(i);
            }

            File myObj = new File(fileName);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }

        } catch (AddressErrorException addressErrorException) {

            throw new ProcessingException(paramProgramStatement, addressErrorException);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
