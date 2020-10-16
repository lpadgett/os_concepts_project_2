import java.util.ArrayList;
import java.util.HashSet;

public class Table {
    private HashSet<Customer> table;
    private ArrayList<Customer> line;
    public Waiter waiter;

    public Table() {
        this.table = new HashSet<Customer>();
        this.line = new ArrayList<Customer>();
    }

    public boolean tableOrLine(Customer customer, boolean override){
        if(table.size() < 4){ //If table can immediately seat customer, sit at table
            table.add(customer);
            return true;
        } else if(line.size() < 7 || override) { //If line < 7 or customer's other chosen food has a long line and this is the customer's first choice
            line.add(customer);
            return true;
        }

        return false; //Only return false if line is >= 7 and customer's second choice hasn't been considered or has too long of a line. This makes a customer choose their second choice
    }

    public void leaveTable(Customer customer){
        this.table.remove(customer);
    }
}
