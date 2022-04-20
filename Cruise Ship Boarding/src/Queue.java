public class Queue {
    private int front;
    private int rear;
    private int size;
    private final Passenger[] queue = new Passenger[5];

    public void enQueue(Passenger data) {
        if (!isFull()) {
            queue[rear] = data;
            rear = (rear + 1) % 5;
            size++;
        }
    }

    public Passenger deQueue() {
        Passenger data = null;
        if (!isEmpty()) {
            data = queue[front];
            front = (front + 1) % 5;
            size--;
        }
        return data;

    }

    public void show() {
        int count = 1;
        System.out.println("\n                                             Queue list\n");
        for (int i = 0; i < size; i++){
            System.out.println("                                          " + count + ". " + queue[(front+i)%5].getFirstName() + " " + queue[(front+i)%5].getSurName());
            count++;
        }
        System.out.println("_____________________________________________________________________________________________________");
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == 5;
    }
}
