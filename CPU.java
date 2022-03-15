import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Locale;

public class CPU
{
    // initializing variables
    public boolean halt = false;
    public int PC = 0x400;
    public String IR = "";
    public int AC = 0;
    public int REG = 0;
    public int numOfExecutionsTotal = 0;
    FileWriter output = new FileWriter("output.txt");
    public int subroutineTracker = 0;
    Stack stack = new Stack();
    String OutputPath = "";

    public CPU(String outputPath) throws IOException
    {
        OutputPath = outputPath;
    }

    public void IntructionExecution() throws IOException {
        // opening of do. will run all instructions
        // until it detects the halt instruction
        while (halt == false)
        {
            // fetch
            IR = Integer.toHexString(Memory.memoryArray[PC]);
            // increment
            PC++;
            // execute
            // splitting opcode and operand and parsed into hexadecimal so
            // the correct memory locations will be called (decimal vs hexadecimal indexes)
            int opcode = Integer.parseInt(IR.substring(0, 1), 16);
            int operand = Integer.parseInt(IR.substring(1, 4), 16);
            // opcode switch
            numOfExecutionsTotal++;
            switch (opcode) {
                case 0b0001: // memory into AC
                    AC = Memory.memoryArray[operand];
                    break;

                case 0b0010: // AC into memory
                    Memory.memoryArray[operand] = AC;
                    break;

                case 0b0011: // REG into AC
                    AC = REG;
                    break;

                case 0b0100: // AC into REG
                    REG = AC;
                    break;

                case 0b0101: // AC + memory
                    AC += Memory.memoryArray[operand];
                    break;

                case 0b0110: // operand into REG
                    REG = operand;
                    break;

                case 0b0111: // AC + REG
                    AC += REG;
                    break;

                case 0b1000: // AC * REG
                    AC *= REG;
                    break;

                case 0b1001: // AC - REG
                    AC -= REG;
                    break;

                case 0b1010: // AC / REG
                    AC /= REG;
                    break;

                case 0b1011: // Jump to subroutine at address
                    // calling JumpToSubroutine will jump to memory location stored
                    // in operand and rewrite that location into PC so InstructionExecution
                    // will continue using the new PC value
                    JumpToSubroutine(operand);
                    break;

                case 0b1100: // Return from subroutine
                    ReturnFromSubroutine();
                    break;

                case 0b1111: // Halt
                    halt = true;
                    break;

            }

        }
        output.write("======End of Program Status======" + "\n");
        StatusWriter();
        output.close();

    } // end of IntructionExecution

    public void JumpToSubroutine(int operand)
    {
        // pushing registers onto stack
        stack.push(PC);
        stack.push(Integer.parseInt(IR,16));
        stack.push(AC);
        stack.push(REG);
        PC = operand;
        subroutineTracker++;
    }

    public void ReturnFromSubroutine() throws IOException {
        output.write("======Before Return from Subroutine " + subroutineTracker + " Status=====" + "\n");
        StatusWriter();
        REG = stack.pop();
        AC = stack.pop();
        IR = Integer.toHexString(stack.pop());
        PC = stack.pop();
    }

    public void StatusWriter() throws IOException {
        output.write("=============Stack Status=============" + "\n");
        if (stack.isEmpty()) {
            output.write("No data in stack!" + "\n");
        }
        else
        {
            output.write("Stack contents at 0 = " + Integer.toHexString(Memory.memoryArray[0]).toUpperCase() + "\n");
            output.write("Stack contents at 1 = " + Integer.toHexString(Memory.memoryArray[1]).toUpperCase() + "\n");
            output.write("Stack contents at 2 = " + Integer.toHexString(Memory.memoryArray[2]).toUpperCase() + "\n");
            output.write("Stack contents at 3 = " + Integer.toHexString(Memory.memoryArray[3]).toUpperCase() + "\n");
        }
        output.write("=============Registers and Memory Status=============" + "\n");
        output.write("PC = " + Integer.toHexString(PC).toUpperCase() + "\n");
        output.write("IR = " + IR + "\n");
        output.write("AC = " + Integer.toHexString(AC).toUpperCase() + "\n");
        output.write("REG = " + Integer.toHexString(REG).toUpperCase() + "\n");
        output.write("Memory 940 = " + Integer.toHexString(Memory.memoryArray[0x940]).toUpperCase() + "\n");
        output.write("Memory 941 = " + Integer.toHexString(Memory.memoryArray[0x941]).toUpperCase() + "\n");
        output.write("Memory 942 = " + Integer.toHexString(Memory.memoryArray[0x942]).toUpperCase() + "\n");
        output.write("Number of instructions executed = " + numOfExecutionsTotal + "\n");
    }

} // end of CPU class
