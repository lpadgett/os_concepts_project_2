public class Client {
    public static Restaurant restaurant;
    public static SharedDataSingleton sharedData;
    public static final int numOfWaiters = 3;
    public static final int numOfCustomers = 40;

    public static void main(String[] args){
        initialize(numOfWaiters, numOfCustomers); //Initialize with 3 waiters and 40 customers
    }

    public static void initialize(int waiterQuantity, int customerQuantity) {
        restaurant = new Restaurant(waiterQuantity, customerQuantity);
        sharedData = new SharedDataSingleton();
    }
}
