package scd.hanoi;

class Tower {
	private int numTowers[] = new int[3];
	private int size[] = new int[3];

	public Tower(int numTowers, int width) {
		reset(numTowers, width);
	}

	public void reset(int nt, int width) {
		numTowers[0] = nt;
		numTowers[1] = 0;
		numTowers[2] = 0;
		size[0] = width;
		size[1] = width;
		size[2] = width;
	}

	public int getNumTowers(int i) {
		return numTowers[i];
	}

	public int getSize(int i) {
		return size[i];
	}

	public void setNumTowers(int i, int val) {
		numTowers[i] = val;
	}

	public void setSize(int i, int val) {
		size[i] = val;
	}
}