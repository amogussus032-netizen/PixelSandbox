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
                if (simulatedGrid.getPixel(x, y) == (byte) 1) {
                    if (simulatedGrid.getPixel(x, y + 1) == (byte) 0) {
                        simulatedGrid.setPixel(x, y, (byte) 0);
                        simulatedGrid.setPixel(x, y + 1, (byte) 1);
                    }
                }
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
}
