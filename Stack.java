public class Stack
{
    static final int MAX = 0x3FF;
    int top;

    boolean isEmpty()
    {
        return (top < 0);
    }

    Stack()
    {
        top = -1;
    }

    boolean push(int x)
    {
        Memory.memoryArray[++top] = x;
        return true;
    }

    int pop()
    {
        int x = Memory.memoryArray[top--];
        return x;
    }
}
