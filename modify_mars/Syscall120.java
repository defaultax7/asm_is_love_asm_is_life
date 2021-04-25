   package mars.mips.instructions.syscalls;
   import mars.util.*;
   import mars.mips.hardware.*;
   import mars.*;
   import javax.swing.JOptionPane;

   public class Syscall120 extends AbstractSyscall {
   /**
    * Build an instance of the Close syscall.  Default service number
    * is 16 and name is "Close".
    */
       public Syscall120() {
         super(120, "Testing");
      }
      
   /**
   * Performs syscall function to close file descriptor given in $a0.
   */
       public void simulate(ProgramStatement statement) throws ProcessingException {
         try{
            JOptionPane.showMessageDialog(null, "alert", "alert", JOptionPane.ERROR_MESSAGE); 
		 }catch(Exception e){
			 throw e;
		 }
      }
   }