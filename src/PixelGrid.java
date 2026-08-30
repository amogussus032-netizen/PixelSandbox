public class PixelGrid {
    private final int sizeX;
    private final int sizeY;
    private final byte[][] grid;

    PixelGrid(int sizeX, int sizeY) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.grid = new byte[sizeX][sizeY];
    }

    public synchronized byte getPixel(int x, int y) {
        if (inBounds(x, y)) {
            return grid[x][y];
        }
        else {
            return -1;
        }
    }

    public synchronized boolean setPixel(int x, int y, byte value) {
        if (inBounds(x, y)) {
            grid[x][y] = value;
            return true;
        }
        else {
            return false;
        }
    }

    public synchronized boolean swapPixels(int x1, int y1, int x2, int y2) {
        if (inBounds(x1, y1) && inBounds(x2, y2)) {
            byte buffer = getPixel(x1, y1);
            setPixel(x1, y1, getPixel(x2, y2));
            setPixel(x2, y2, buffer);
            return true;
        }
        else {
            return false;
        }
    }

    private boolean inBounds(int x, int y) {
        return x < sizeX && y < sizeY && x >= 0 && y >= 0;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }
}
