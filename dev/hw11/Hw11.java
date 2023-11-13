
// Author: Kyle Burke, Shreesh Tripathi

// The program will never terminate due to improper usage of notify and wait 

// The first thread (created by new Hw11("0").start()) calls o.notify(), which 
// notifies any thread waiting on o.

// It then prints the info string and waits on o using o.wait().

// The second thread (created by new Hw11("1").start()) also enters the synchronized 
// block and calls o.notify() before printing its info string and waiting on o.

// Here's where the problem lies:

// Both threads are waiting for a notification on the same object o. 

// However, notify() only wakes up one of the waiting threads, and which thread 
// gets awakened is not guaranteed. It depends on the scheduler.

// If the scheduler happens to notify the second thread first, it prints "1" and waits, 
// and the first thread keeps waiting for a notification that will never come. 
// If the scheduler notifies the first thread first, it prints "0" and waits, and the second thread keeps waiting. 
// In either case, at least one thread is stuck waiting, and the program won't terminate.

// Here's the modified code to fix this problem:


public class Hw11 extends Thread {

	private String info;
	static Object o = new Object();
	static boolean first = true;
	public Hw11 (String info) {
		
		/* 2. But they still share the same sync object 'o'
		 2.1. Creating a new object in the constructor can lead 
		 to an unintentional permanent wait
		 Since the object monitor for each thread will be different,
		 thread A calling notify will not wake up thread B 
		 Commenting this line ensures that both objects are always 
		 using the same Object 'o'
		 */

		//this.o = new Object(); 
		this.info    = info;
	}
	public void run () {


		// By forcing the first thread to wait
		// ensure that there will always be a thread to call notify
		
		synchronized ( o ) {
			// 3. This does nothing at first since no thread asked to wait
			// 4. Problem: Notify is called by both threads BEFORE the wait call
			if (first){
				first = false;
				try{
					o.wait();
				}
				catch (InterruptedException e){

				}
			}
			else{
				
				o.notify();
			}
			System.err.println(info);
		}
	}
	public static void main (String args []) {
		// 1. Two instances of Hw11 are created
		new Hw11("0").start();
		new Hw11("1").start();
		
	}
}