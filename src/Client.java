import java.util.Hashtable;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Client {

    //Create ConcurrentHashMap instead of normal hashmap for extra thread safety.
    //Use UUIDs to identify person type
    public static ConcurrentHashMap<UUID, Person> threadIdLookup;

    public static void main(String[] args){
        initialize();
    }

    public static void initialize(){
        threadIdLookup = new ConcurrentHashMap<>();
    }
}
