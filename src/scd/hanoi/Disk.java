package scd.hanoi;

class Disk {
	private int width[]  = new int[6];
	private int height[] = new int[6];
	private int x[]      = new int[6];
	private int y[]      = new int[6];
	private int oldX[]   = new int[6];
	private int oldY[]   = new int[6];
	private int pos[]    = new int[6];
	private int disks[]  = new int[6];
                
	public Disk(int numDisk, int width, int height, int diskH){
            for(int i=0; i < numDisk; i++) {
                    this.width[i]  = 40 + (int)(0.3*(width-90)/numDisk)*i;
                    this.height[i] = (int)(diskH/(numDisk+2));
                    x[i]      = (int)((width-50)/6.0) + 15;
                    y[i]      = height-this.height[i]*(numDisk-i);
                    oldX[i]   = x[i];
                    oldY[i]   = y[i];
                    pos[i]    = numDisk-i;
                    disks[i]  = 0;
            }
	}

	void reset(int numDisk, int width, int height, int diskHeiht) {
            for(int i=0;i < numDisk;i++) {
                    this.width[i]  = 40 + (int)(0.3 * (width-90)/numDisk) * i;
                    this.height[i] = (int)(height/(numDisk+2));
                    x[i]     = (int)((width - 50)/6.0)+15;
                    y[i]     = height - this.height[i]*(numDisk-i);
                    oldX[i]  = x[i];
                    oldY[i]  = y[i];
                    pos[i]   = numDisk-i;
                    disks[i] = 0;
            }
	}
        
        //getters and setters
        public int getWidth(int i){ return width[i]; }        
        public void setWidth(int i, int val){ width[i] = val; }
        public int getHeight(int i){ return height[i]; }
        public void setHeight(int i, int val){ height[i] = val; }
        public int getX(int i){ return x[i]; }
        public void setX(int i, int val){ x[i] = val; }
        public int getY(int i){ return y[i]; }
        public void setY(int i, int val){ y[i] = val; }
        public int getOldX(int i){ return oldX[i]; }
        public void setOldX(int i, int val){ oldX[i] = val; }
        public int getOldY(int i){ return oldY[i]; }
        public void setOldY(int i, int val){ oldY[i] = val; }
        public int getPos(int i){ return pos[i]; }
        public void setPos(int i, int val){ pos[i] = val; }
        public int getDisk(int i){ return disks[i]; }
        public void setDisk(int i, int val){ disks[i] = val; }
        
} 