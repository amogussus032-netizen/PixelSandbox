public class PixelGrid {
    private final int sizeX;
    private final int sizeY;
    private final byte[][] grid;
    private static final java.util.Random random = new java.util.Random();

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

    public synchronized boolean setPixel(int x, int y, int materialId) {
        if (inBounds(x, y)) {
            int variant = random.nextInt(3);

            byte packed = (byte) (materialId | (variant << 5)); // Первые 3 бита - вариант, остальные материал
            grid[x][y] = packed;
            return true;
        }
        else {
            return false;
        }
    }

    public synchronized int getMaterial(int x, int y) {
        if (inBounds(x, y)) {
            return grid[x][y] & 0b00011111;
        }
        else {
            return -1;
        }
    }

    public synchronized boolean swapPixels(int x1, int y1, int x2, int y2) {
        if (inBounds(x1, y1) && inBounds(x2, y2)) {
            byte buffer = grid[x1][y1];
            grid[x1][y1] = grid[x2][y2];
            grid[x2][y2] = buffer;
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
