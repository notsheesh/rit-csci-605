class item{
    int qty;
    boolean produced;

    public item(){
        this.qty = 0;
        this.produced = false;
    }

    public synchronized void produce(){

        while(this.produced){ // if already produced then wait
            try {this.wait();} catch (Exception e) {} // make producer wait
        }

        this.qty++;
        System.out.println("Produce " + String.valueOf(this.qty));     
        this.produced = true; // bc just produced
        this.notify(); // notify consumer to go 
    }

    public synchronized void consume(){
        // this.qty--;
        while(!this.produced){ 
            try {this.wait();} catch (Exception e) {} // make consumer wait
        }
        System.out.println("Consume " + String.valueOf(this.qty));        
        this.produced = false; // bc produced was consumed
        this.notify(); // notify producer to go
    }

    public void setQty(int value) {this.qty = value;}
}

class Producer implements Runnable{

    item anItem;

    public Producer(item anItem){
        this.anItem = anItem;
        new Thread(this, "Producer").start();
    }

    public void run(){
        while (true){
            this.anItem.produce();
            try{Thread.sleep(2000);} catch (Exception e) {}
        }
    }
}

class Consumer implements Runnable{

    item anItem;

    public Consumer(item anItem){
        this.anItem = anItem;
        new Thread(this, "Consumer").start();
    }

    public void run(){
        while (true){
            this.anItem.consume();
            try{Thread.sleep(2000);} catch (Exception e) {}
        }
    }
}

class ProducerConsumer{
    public static void main(String[] args) {
        item anItem = new item();
        new Producer(anItem);
        new Consumer(anItem);
    }
}
