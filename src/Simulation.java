public class Simulation {
    private static int[] xSimulationOrder;
    private static final java.util.Random random = new java.util.Random();

    private Simulation() {

    }

    public static void step(double dt, PixelGrid simulatedGrid) {
        int gridSizeX = simulatedGrid.getSizeX();
        int gridSizeY = simulatedGrid.getSizeY();

        if (xSimulationOrder == null) {
            xSimulationOrder = new int[gridSizeX];
            java.util.Arrays.setAll(xSimulationOrder, i -> i); // Лямбда принимает i и сразу его выдаёт
        }

        for (int y = gridSizeY - 1; y >= 0; y--) {
            shuffleArray(xSimulationOrder);

            for (int x : xSimulationOrder) {
                sandSimulation(x, y, simulatedGrid);
            }
        }
    }

    private static void shuffleArray(int[] array) {
        for (int i = array.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }

    private static void sandSimulation(int x, int y, PixelGrid simulatedGrid) {
        if (simulatedGrid.getMaterial(x, y) == 1) {
            if (simulatedGrid.getMaterial(x, y + 1) == 0) {
                simulatedGrid.swapPixels(x, y, x, y + 1);
                return;
            }

            int bottomLeftPixel = simulatedGrid.getMaterial(x - 1, y + 1);
            int bottomRightPixel = simulatedGrid.getMaterial(x + 1, y + 1);

            if (random.nextInt(101) < 75) {

                if (bottomLeftPixel == 0 && bottomRightPixel == 0) {
                    if (random.nextInt(2) == 0) {
                        simulatedGrid.swapPixels(x, y, x - 1, y + 1);
                    } else {
                        simulatedGrid.swapPixels(x, y, x + 1, y + 1);
                    }
                } else if (bottomLeftPixel == 0) {
                    simulatedGrid.swapPixels(x, y, x - 1, y + 1);
                } else if (bottomRightPixel == 0) {
                    simulatedGrid.swapPixels(x, y, x + 1, y + 1);
                } else {

                }
            }
        }
    }
}
