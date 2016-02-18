package scd.hanoi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class HanoiWindow extends JFrame {
	private int numDisks = 3;
	private HanoiGame hg;
	private JPanel pnlCmd;
	private JButton btnDisp;
	private JTextField tfdNumDisk;
	private JLabel str;

	public HanoiWindow(int nd) {
		setTitle("Torre de Hanoi");
		numDisks = nd;
		hg = new HanoiGame(numDisks);

		setLayout(new BorderLayout(5, 5));
		setSize(700, 500);

		add(hg, BorderLayout.CENTER);

		setLocationRelativeTo(null);
		setVisible(true);
		initHanoi();
	}

	public void initHanoi() {
		int src = 1, dst = 2, tmp = 3;
		Hanoi h = new Hanoi(hg);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		h.executeHanoi(numDisks, src, dst, tmp, true);
	}

	public static void main(String args[]) {
		int n = 6;
		if (args.length == 1) {
			if (args[0] != null)
				try {
					n = Integer.parseInt(args[0].trim());
				} catch (Exception e) {
				}
		}
		HanoiWindow hw = new HanoiWindow(n);
		System.gc();
	}
}