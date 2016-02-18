/*
 * Customer.java
 *
 * Created on 3 de Dezembro de 2006, 02:33
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package scd.sleepingBarber;

/**
 * @author Jôse Bandeira
 * @author Ulisses Mainart
 * @see java.lang.Thread
 * @see scd.utils.Semaphore
 * @see scd.sleepingBarber.BarberShop
 * @see scd.sleepingBarber.Barber
 * @see scd.sleepingBarber.SleepingBarberWindow
 * @since SCD2006.02
 */
public class Customer extends Thread {
	private int id;
	private SleepingBarberWindow sbw;

	public Customer(int id, SleepingBarberWindow sbw) {
		this.id = id;
		this.sbw = sbw;
	}

	public void run() {
		/* região crítica para atualização de waiting e da tela */
		BarberShop.mutex.down();
		if (BarberShop.waiting < BarberShop.nChairs) { // se existem cadeiras

			BarberShop.waiting = BarberShop.waiting + 1;
			// atualização da tela
			sbw.setWaitingCustomers(BarberShop.waiting);
			sbw.busyChair("Ocupada pelo cliente " + id);

			/* acorda barbeiro, se necessário */
			BarberShop.customers.up();
			/* libera região crítica */
			BarberShop.mutex.up();
			/* espera na cadeira se barbeiros estiverem ocupados (==0) */
			BarberShop.barbers.down();

			getHaircut();

		} else {
			/* se não existem cadeiras, então cliente vai embora */
			sbw.setCustomerNotService(BarberShop.goAway++);
			BarberShop.mutex.up(); /* libera região crítica */
		}
	}

	/* Atualização da tela, quando cliente tem seu cabelo cortado */
	public void getHaircut() {
		sbw.freeChair(String.valueOf(id));
		sbw.busyCustomer("Cliente " + id + " corta o cabelo.");
		System.out.println("Cliente " + id + " está cortando o cabelo.");
		try {
			sleep(10000);
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
		sbw.freeCustomer(String.valueOf(id));
	}
}
