import java.util.concurrent.ConcurrentHashMap;

public class Client {
    public static ConcurrentHashMap<String, Person> threadIdLookup; //Create ConcurrentHashMap instead of normal hashmap for extra thread safety.
    public static int numOfWaiters;
    public static int numOfCustomers;

    public static void main(String[] args){
        initialize();
    }

    public static void initialize(){ //Set all constant variables
        threadIdLookup = new ConcurrentHashMap<String, Person>();
        numOfWaiters = 3;
        numOfCustomers = 40;
    }

    public static void createAndLaunch(int quantity, PersonType type){
        String id;
        for(int i = 0; i < quantity; i++){
            if(type == PersonType.WAITER){
                id = new String("Waiter " + i);
                threadIdLookup.put(id, new Waiter(id));
            } else {
                id = new String("Customer " + i);
                threadIdLookup.put(id, new Customer(id));
            }
        }
    }
}
