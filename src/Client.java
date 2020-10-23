public class Client {
    public static Restaurant restaurant;
    private static Customer[] customers;
    public static final int numOfWaiters = 3;
    public static final int numOfCustomers = 40;

    public static void main(String[] args){
        initialize(numOfWaiters, numOfCustomers); //Initialize with 3 waiters and 40 customers
    }

    public static void initialize(int waiterQuantity, int customerQuantity) {
        customers = new Customer[customerQuantity];
        restaurant = new Restaurant(waiterQuantity);
        createCustomers(customerQuantity);
    }

    public static void createCustomers(int customerQuantity) {
        for (int i = 0; i < numOfCustomers; i++) {
            customers[i] = new Customer(new String("Customer " + i), restaurant);
            customers[i].start();
        }
    }
}
