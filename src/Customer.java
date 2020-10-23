import java.util.Random;

public class Customer extends Thread {
    private String id;
    private FoodType firstChoice;
    private FoodType secondChoice;
    private Restaurant restaurant; //Keeps track of restaurant instance
    private Table table;


    public Customer(String id, Restaurant restaurant){
        this.id = id;
        this.restaurant = restaurant;

        //Generate foodChoices
        int random = new Random().nextInt(100);
        if(random <= 33) {
            this.firstChoice = FoodType.PASTA;
            this.secondChoice = FoodType.SEAFOOD;
        } else if(random > 33 && random <= 66) {
            this.firstChoice = FoodType.SEAFOOD;
            this.secondChoice = FoodType.STEAK;
        } else {
            this.firstChoice = FoodType.STEAK;
            this.secondChoice = FoodType.PASTA;
        }
    }

    @Override
    public void run(){
        try {
            enterRestaurantThroughDoor(this.restaurant);
        } catch (InterruptedException e) {
            System.out.println(this.id + " tried pushing on a pull door.");
        }
        try {
            sitAtTable();
        } catch (InterruptedException e) {
            System.out.println(this.id + " completely missed the chair while trying to sit down.");
        }
        try {
            callWaiter(this.table.waiter);
        } catch (InterruptedException e) {
            System.out.println("Waiter " + this.table.waiter.getWaiterId() + " slipped on the way to the customer.");
        }
        eatFood();
        leaveTable();
        payBill();
        exit();
    }

    private void enterRestaurantThroughDoor(Restaurant restaurant) throws InterruptedException {
        restaurant.doorsSemaphore.acquire();
        wait(5); //Wait (be in door) for 5ms
        restaurant.doorsSemaphore.release(); //Inside restaurant
    }

    private void sitAtTable() throws InterruptedException {
        Table firstChoiceTable = this.restaurant.tableChoice(this.firstChoice);
        Table secondChoiceTable = this.restaurant.tableChoice(this.secondChoice);

        if(firstChoiceTable.tableSemaphore.getQueueLength() >= 7 && secondChoiceTable.tableSemaphore.getQueueLength() < 7) {
            secondChoiceTable.tableSemaphore.acquire();
            this.table = secondChoiceTable;
        } else {
            firstChoiceTable.tableSemaphore.acquire();
            this.table = firstChoiceTable;
        }
    }

    private void callWaiter(Waiter waiter) throws InterruptedException {
        waiter.currentlyServing.acquire(); //Waits until waiter is available;

    }

    private void eatFood(){}

    private void leaveTable(){
        this.table.tableSemaphore.release();
    }

    private void payBill(){

    }

    private void exit(){}

    public String getCustomerId(){
        return this.id;
    }

    public FoodType getFirstChoice(){
        return this.firstChoice;
    }

    public FoodType getSecondChoice(){
        return this.secondChoice;
    }
}
