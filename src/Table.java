import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class Table {
    public Semaphore tableSemaphore;
    public Waiter waiter;

    public Table() {
        this.tableSemaphore = new Semaphore(4); //Only 4 seats per table
    }
}
