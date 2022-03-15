import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Memory
{
    // the memory array must be declared public static as it will be called and written into
    // by other classes, and it needs to hold its values outside of scope of any class
    public static int memoryArray[] = new int[4096];

    public static void main(String[] args) throws IOException {

        // initialization of variables
        File file = new File(args[0]);
        Scanner sc = new Scanner(file);

        // this while will run until the file does not have a next line. Its purpose is to read through the
        // input.txt file and parse out all lines of "instruction" in the form [xxx xxxx], i.e. [400 1940]. It
        // will load it into the memoryArray
        while (sc.hasNextLine())
        {
            // All code until the do-while is reading the file and putting it into the memory array
            // The first line is put into a string to start to check if the line has instructions.
            String str = sc.nextLine();
            // The first scanner has already moved past the line with the instruction, so a second scanner
            // is initialized to read if the line has instruction
            Scanner sc2 = new Scanner(str);
            // The if will move past all lines in input.txt that do not start with a sequence number
            if (str.isEmpty() || str.charAt(0) == '=')
            {
                sc.nextLine();
            }
            else
            {
                // the first next will grab the sequence number and period.
                // the second next will grab the address.
                // the third next will grab the opcode and operand and place it into the address in memoryArray
                sc2.next();
                int address = Integer.parseInt(sc2.next(), 16);
                memoryArray[address] = Integer.parseInt(sc2.next(), 16);

            } // end of if-else
        } // end of while

        // The memoryArray is now loaded and needs to be executed. A CPU class object "processor" is initialized
        // and InstructionExecution method called. All fetch and execute steps should take place in this call.
        CPU processor = new CPU(args[1]);
        processor.IntructionExecution();

    } // end of main method
} // end of Memory class
