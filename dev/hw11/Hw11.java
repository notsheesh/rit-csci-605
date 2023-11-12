
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
	public Hw11 (String info) {
		// 2. But they still share the same sync object 'o'
		this.o = new Object();
		this.info    = info;
	}
	public void run () {
		synchronized ( o ) {
			// 3. This does nothing at first since no thread asked to wait
			// 4. Problem: Notify is called by both threads BEFORE the wait call
			o.notify();
			System.out.println(info);
			try {
				// 5. The threads will be stuck in a blocked state after
				// executing the wait, and there is no subsequent notification to wake them
				o.wait();
			} catch ( Exception e ) {
				e.printStackTrace();
			}
		}
	}
	public static void main (String args []) {
		// 1. Two instances of Hw11 are created
		new Hw11("0").start();
		new Hw11("1").start();
	}
}