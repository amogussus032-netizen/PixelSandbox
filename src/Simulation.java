public class Simulation {
    private Simulation() {

    }

    public static void step(double dt, PixelGrid simulatedGrid) {
        int gridSizeX = simulatedGrid.getSizeX();
        int gridSizeY = simulatedGrid.getSizeY();

        for (int x = 0; x < gridSizeX; x++) {
            for (int y = gridSizeY - 1; y >= 0; y--) {
                if (simulatedGrid.getPixel(x, y) == (byte) 1) {
                    if (simulatedGrid.getPixel(x, y + 1) == (byte) 0) {
                        simulatedGrid.setPixel(x, y, (byte) 0);
                        simulatedGrid.setPixel(x, y + 1, (byte) 1);
                    }
                }
            }
        }
    }
}
