import java.util.Random;

public class Customer extends Thread {
    private String id;
    private FoodType firstChoice;
    private FoodType secondChoice;
    private Restaurant restaurant; //Keeps track of restaurant instance
    private Table table;
    private boolean hasFood;
    private boolean waiterIsReadyToTakeOrder;


    public Customer(String id, Restaurant restaurant){
        this.id = id;
        this.restaurant = restaurant;
        this.hasFood = false;

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
    }

    private void enterRestaurantThroughDoor(Restaurant restaurant) throws InterruptedException {
        restaurant.doorsSemaphore.acquire();
        Thread.sleep(10); //Wait (be in door) for 10ms
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
        boolean madeAnnouncement = false;
        while(!this.waiterIsReadyToTakeOrder){
            if(!madeAnnouncement) {
                System.out.println(getCustomerId() + " is waiting for their waiter to take their order.");
                madeAnnouncement = true;
                this.table.waiter.takeOrder(getCustomerId());
            }
        }
    }

    private void eatFood() throws InterruptedException { //Takes 200ms to 1 second according to requirements, make it 300ms for simplicity
        boolean madeAnnouncement = false;
        while(!this.hasFood){
            if(!madeAnnouncement) {
                System.out.println(getCustomerId() + " is waiting on their food.");
                madeAnnouncement = true;
            }
        }
        this.table.waiter.currentlyServing.release(); //Finally release waiter after receiving food
        long timeToSpendEating = new Random().nextInt(800) + 200; //Spends anywhere from 100 to 500 ms in kitchen
        Thread.sleep(timeToSpendEating);
        System.out.println(getCustomerId() + " ate their food.");
    }

    private void leaveTable(){
        System.out.println(this.id + " left their table.");
        this.table.leave(this);
        this.table.tableSemaphore.release();
    }

    private void payBill() throws InterruptedException {
        System.out.println(this.id + " paid their bill.");
        this.restaurant.payBill(this);
    }

    private void exit() throws InterruptedException {
        System.out.println(this.id + " left the restaurant.");
        this.restaurant.doorsSemaphore.acquire();
        Thread.sleep(10);
        this.restaurant.doorsSemaphore.release();
        this.restaurant.customerHasBeenServed();
        System.out.println("There are " + this.restaurant.getNumOfCustomersServed() + " that have been served.");
    }

    public String getCustomerId(){
        return this.id;
    }

    public void waiterIsReady() {
        this.waiterIsReadyToTakeOrder = true;
    }

    public void receiveFoodFromWaiter() {
        System.out.println(this.id + " got their food.");
        this.hasFood = true;
    }
}
