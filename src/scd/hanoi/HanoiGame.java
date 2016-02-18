package scd.hanoi;

import java.awt.*;

class HanoiGame extends Panel {

	private boolean isFinished;
	private boolean withError;
	private int numdisks;
	private int widthArea;
	private int heightArea;
	private int towerHeight;
	private int towerWidth;
	private int xTower1;
	private int xTower2;
	private int xTower3;

	private int actualDisk;
	private int accMov;
	private int minMov[];

	private Disk d;
	private Tower t;

	HanoiGame(int nd) {
		setBackground(Color.LIGHT_GRAY);
		isFinished = false;
		withError = false;

		numdisks = nd; // numero de discos
		widthArea = 640; // largura util dentro da area
		heightArea = 380; // altura util dentro da area

		// altura e largura das torres
		towerHeight = (int) (0.7 * heightArea);
		towerWidth = 10;

		// posicao das torres
		xTower1 = 15 + (int) ((widthArea - 50) / 6.0) - (int) (0.5 * towerWidth);
		xTower2 = xTower1 + (int) ((widthArea - 50) / 3.0) + 10;
		xTower3 = xTower2 + (int) ((widthArea - 50) / 3.0) + 10;

		actualDisk = -1;
		accMov = 0;
		minMov = new int[7];

		d = new Disk(numdisks, widthArea, heightArea, towerHeight);
		t = new Tower(numdisks, widthArea);
	}

	public void paint(Graphics g) {
		// desenha torres
		g.setColor(Color.CYAN);
		g.fillRect(xTower1, heightArea - towerHeight, towerWidth, towerHeight);
		g.setColor(Color.YELLOW);
		g.fillRect(xTower2, heightArea - towerHeight, towerWidth, towerHeight);
		g.setColor(Color.ORANGE);
		g.fillRect(xTower3, heightArea - towerHeight, towerWidth, towerHeight);

		// desenha os discos
		for (int j = 0; j < numdisks; j++) {
			g.setColor(new Color(50 + 30 * j, 60 + 30 * j, 55 - 10 * j)); // cores RGB
			g.fillRect(d.getX(j) - d.getWidth(j) / 2, d.getY(j), d.getWidth(j), d.getHeight(j));
		}

		// tratamento do argumento de entrada (numero de discos)
		if (withError) {
			g.setColor(Color.RED);
			g.setFont(new Font("Helvetica", Font.BOLD, 16));
			g.drawString("Informe um numero de discos entre 3 e 6!", 
					(int) (0.1 * widthArea), (int) (0.1 * heightArea));
		}

		// mensagem de fim de jogo
		if (isFinished) {

			// inicializa valores minimos de jogadas
			minMov[0] = 0;
			minMov[1] = 1;
			minMov[2] = 3;
			minMov[3] = 7;
			minMov[4] = 15;
			minMov[5] = 31;
			minMov[6] = 63;
			g.setColor(Color.YELLOW);
			g.setFont(new Font("Helvetica", Font.BOLD, 16));
			if (minMov[numdisks] != accMov) {
				g.drawString("Foram executados " + accMov + " movimentos, mas deveriam ter sido executados "
						+ minMov[numdisks] + " movimentos.", (int) (0.1 * widthArea), (int) (0.2 * heightArea));
			} else {
				g.drawString("   Torre de Hanoi foi terminado com " + accMov + " movimentos.", (int) (0.1 * widthArea),
						(int) (0.2 * heightArea));
			}
		}
	}

	public synchronized void moving(int disk, int tower) {
		setDisk(disk);

		if (tower == 1) {
			drag(115, 125);
			put(115, 125);
		} else if (tower == 2) {
			drag(319, 112);
			put(319, 112);
		} else if (tower == 3) {
			drag(528, 224);
			put(528, 224);
		}

	}

	private boolean drag(int x, int y) {
		if (actualDisk != -1) {
			d.setX(actualDisk, x);
			d.setY(actualDisk, y);
			repaint();
		}
		return true;
	}

	private boolean put(int x, int y) {
		if (actualDisk != -1) {
			if (d.getX(actualDisk) >= 0.0 && d.getX(actualDisk) <= (20.0 + (widthArea - 50) / 3.0)
					&& d.getWidth(actualDisk) < t.getSize(0)) {
				updateTower1();

			} else {
				if (d.getX(actualDisk) >= (20.0 + (widthArea - 50) / 3.0)
						&& d.getX(actualDisk) <= (30.0 + 2.0 * (widthArea - 50) / 3.0)
						&& d.getWidth(actualDisk) < t.getSize(1)) {
					updateTower2();
				} else {
					if (d.getX(actualDisk) >= (30.0 + 2.0 * (widthArea - 50) / 3.0) 
							&& d.getX(actualDisk) <= widthArea
							&& d.getWidth(actualDisk) < t.getSize(2)) {
						updateTower3();
					} else {
						// se nao consegui atualizar volto para o velho x/y
						System.out.println("Nao atualizou nada");
						d.setX(actualDisk, d.getOldX(actualDisk));
						d.setY(actualDisk, d.getOldY(actualDisk));
					}
				}
			}
		}

		d.setOldX(actualDisk, d.getX(actualDisk));
		d.setOldY(actualDisk, d.getY(actualDisk));
		actualDisk = -1;
		accMov++;
		for (int i = 0; i < numdisks; i++) {
			if (d.getPos(i) == t.getNumTowers(d.getDisk(i))) {
				t.setSize(d.getDisk(i), d.getWidth(i));
			}
		}
		for (int i = 0; i < 3; i++) {
			if (t.getNumTowers(i) == 0) {
				t.setSize(i, widthArea);
			}
		}

		for (int j = 0; j < 3; j++) {
			if ((j != 0) && (t.getNumTowers(j) == numdisks)) {
				isFinished = true;
			}
		}
		repaint();
		return true;
	}

	private void updateTower1() {
		// System.out.println("Entrou em update 1");
		t.setNumTowers(0, (t.getNumTowers(0) + 1));
		t.setNumTowers(d.getDisk(actualDisk), t.getNumTowers(d.getDisk(actualDisk)) - 1);
		d.setDisk(actualDisk, 0);
		d.setX(actualDisk, (int) ((widthArea - 50) / 6.0) + 15);
		d.setY(actualDisk, (heightArea - d.getHeight(actualDisk) * t.getNumTowers(0)));
		d.setPos(actualDisk, t.getNumTowers(0));
	}

	private void updateTower2() {
		// System.out.println("Entrou em update 2");
		t.setNumTowers(1, t.getNumTowers(1) + 1);
		t.setNumTowers(d.getDisk(actualDisk), t.getNumTowers(d.getDisk(actualDisk)) - 1);
		d.setDisk(actualDisk, 1);
		d.setX(actualDisk, (int) ((widthArea - 50) / 2.0) + 25);
		d.setY(actualDisk, heightArea - d.getHeight(actualDisk) * t.getNumTowers(1));
		d.setPos(actualDisk, t.getNumTowers(1));
	}

	private void updateTower3() {
		// System.out.println("Entrou em update 3");
		t.setNumTowers(2, t.getNumTowers(2) + 1);
		t.setNumTowers(d.getDisk(actualDisk), t.getNumTowers(d.getDisk(actualDisk)) - 1);
		d.setDisk(actualDisk, 2);
		d.setX(actualDisk, (int) ((widthArea - 50) * 5.0 / 6.0) + 35);
		d.setY(actualDisk, heightArea - d.getHeight(actualDisk) * t.getNumTowers(2));
		d.setPos(actualDisk, t.getNumTowers(2));
	}

	public void reset(int arg) {
		if (arg < 3 || arg > 6) {
			numdisks = 3;
			withError = true;
		} else {
			numdisks = arg;
			withError = false;
		}
		isFinished = false;
		t.reset(numdisks, widthArea);
		d.reset(numdisks, widthArea, heightArea, towerHeight);
		accMov = 0;
		repaint();
	}

	public void setDisk(int i) {
		actualDisk = i;
	}

	public void setNumdisks(int val) {
		numdisks = val;
	}
}
