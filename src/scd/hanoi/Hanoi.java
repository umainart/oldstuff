package scd.hanoi;

public class Hanoi {
	private HanoiGame gameSpace;

	public Hanoi(HanoiGame gs) {
		gameSpace = gs;
	}

	void executeHanoi(int n, int src, int dst, int tmp, boolean display) {
		if (n == 1) {
			if (display) {
				System.out.println("Disco " + n + " movido da torre " + src + " para " + "torre " + dst);
				gameSpace.moving(n - 1, dst);

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} else {
			// move n-1 discos de src para tmp deixando disco de numero #n em
			// src
			Hanoi.HanoiThread1 ht1 = (new Hanoi(gameSpace)).new HanoiThread1(n, src, dst, tmp, display);
			ht1.start();

			try {
				ht1.join();
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}

			// move disco #n de src para dst
			if (display) {
				System.out.println("Disco " + n + " movido da torre " + src + " para " + "torre " + dst);
				gameSpace.moving(n - 1, dst);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			// move n-1 discos de tmp para Dst sobre disco #n
			Hanoi.HanoiThread2 ht2 = (new Hanoi(gameSpace)).new HanoiThread2(n, src, dst, tmp, display);
			ht2.start();

			try {
				ht2.join();
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}

	// inner-classes
	private class HanoiThread1 extends Thread {
		int n, src, dst, tmp;
		boolean display;

		HanoiThread1(int n, int src, int dst, int tmp, boolean display) {
			this.n = n;
			this.src = src;
			this.dst = dst;
			this.tmp = tmp;
			this.display = display;
		}

		public void run() {
			// move n-1 discos de src para tmp deixando disco de numero #n em
			// src
			executeHanoi(n - 1, src, tmp, dst, display);
		}
	}

	private class HanoiThread2 extends Thread {
		int n, src, dst, tmp;
		boolean display;

		HanoiThread2(int n, int src, int dst, int tmp, boolean display) {
			this.n = n;
			this.src = src;
			this.dst = dst;
			this.tmp = tmp;
			this.display = display;
		}

		public void run() {
			// move n-1 discos de src para tmp deixando disco de numero #n em
			// src
			executeHanoi(n - 1, tmp, dst, src, display);
		}
	}

}
