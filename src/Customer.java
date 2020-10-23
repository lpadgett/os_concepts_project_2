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
            System.out.println(this.id + "tried pushing on a pull door.");
        }
        sitAtTable();
        callWaiter(this.table.waiter);
        eatFood();
        leaveTable();
        payBill();
        exit();
    }

    private void enterRestaurantThroughDoor(Restaurant restaurant) throws InterruptedException {
        boolean entryAcquired = restaurant.doorsSemaphore.tryAcquire();
        while(entryAcquired == false){
            entryAcquired = restaurant.doorsSemaphore.tryAcquire();
        }
        wait(5); //Wait (be in door) for 5ms
        restaurant.doorsSemaphore.release(); //Inside restaurant
    }

    private void sitAtTable(){
        Table firstChoiceTable = this.restaurant.tableChoice(this.firstChoice);
        Table secondChoiceTable = this.restaurant.tableChoice(this.secondChoice);

        if(firstChoiceTable.tableSemaphore.availablePermits() < 7){

        }
    }

    private void callWaiter(Waiter waiter){

    }

    private void eatFood(){}

    private void leaveTable(){}

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
