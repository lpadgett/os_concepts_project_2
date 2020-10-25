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
            System.out.println(getCustomerId() + " tried pushing the entry door open when they should have pulled.");
            e.printStackTrace();
        }

        try {
            sitAtTable();
        } catch (InterruptedException e) {
            System.out.println(getCustomerId() + " completely missed the chair while trying to sit down.");
            e.printStackTrace();
        }

        try {
            callWaiter(this.table.waiter);
        } catch (InterruptedException e) {
            System.out.println(this.table.waiter.getWaiterId() + " did not hear " + getCustomerId() + " calling them.");
            e.printStackTrace();
        }

        order();

        try {
            eatFood();
        } catch (InterruptedException e) {
            System.out.println(getCustomerId() + " dropped their food on the floor.");
            e.printStackTrace();
        }

        leaveTable();

        try {
            payBill();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            exit();
        } catch (InterruptedException e) {
            System.out.println(getCustomerId() + " tried to pull the exit door open when they should have pushed.");
            e.printStackTrace();
        }

        System.out.println(getCustomerId() + " left the restaurant.");
    }

    private void enterRestaurantThroughDoor(Restaurant restaurant) throws InterruptedException {
        restaurant.doorsSemaphore.acquire();
        wait(10); //Wait (be in door) for 10ms //TODO: Choose randomly
        restaurant.doorsSemaphore.release(); //Inside restaurant
        System.out.println(getCustomerId() + " entered the restaurant.");
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

        this.table.sit(this);
        System.out.println(getCustomerId() + " chose a table and sat down.");
    }

    private void callWaiter(Waiter waiter) throws InterruptedException {
        waiter.currentlyServing.acquire(); //Waits until waiter is available. The waiter will be released right before the customer begins to eat in the eatFood() method.
        System.out.println(getCustomerId() + " called the waiter for their table.");
    }

    private void order(){
        this.table.waiter.takeOrder(getCustomerId());
        System.out.println(getCustomerId() + " ordered their food.");
    }

    private void eatFood() throws InterruptedException { //Takes 200ms to 1 second according to requirements, make it 300ms for simplicity
        this.table.waiter.currentlyServing.release(); //Finally release waiter after receiving food
        wait(300); //TODO: Choose randomly
        System.out.println(getCustomerId() + " ate their food.");
    }

    private void leaveTable(){
        this.table.leave(this);
        this.table.tableSemaphore.release();
    }

    private void payBill() throws InterruptedException {
        this.restaurant.payBill(this);
    }

    private void exit() throws InterruptedException {
        this.restaurant.doorsSemaphore.acquire();
        wait(10); //TODO: choose randomly
        this.restaurant.doorsSemaphore.release();
    }

    public String getCustomerId(){
        return this.id;
    }
}
