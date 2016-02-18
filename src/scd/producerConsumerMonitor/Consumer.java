/*
 * Consumer.java
 *
 * Created on 14 de Outubro de 2006, 12:35
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package scd.producerConsumerMonitor;

import java.util.*;

public class Consumer extends Thread {
	private Vector<Buffer> buffer;

	/** Creates a new instance of Consumer */
	public Consumer(String id, Vector<Buffer> b) {
		super("Consumer " + id);
		buffer = b;
	}

	public void run() {
		Buffer btemp;

		while (!ProducerConsumerWindow.STOP_THREADS) {
			Iterator<Buffer> it = buffer.iterator();

			while (it.hasNext()) {
				btemp = it.next();
				Data data = btemp.remove();
				data.setConsumer(getName());
			}

		}
	}

}
