import java.awt.*;
import java.awt.image.BufferedImage;

public class PixelRenderer {
    private final PixelGrid pixelGrid;
    private final int sizeX;
    private final int sizeY;
    private final BufferedImage[] buffersArray;
    private int indexCounter = 0;

    public PixelRenderer(PixelGrid pixelGrid) {
        this.pixelGrid = pixelGrid;
        this.sizeX = pixelGrid.getSizeX();
        this.sizeY = pixelGrid.getSizeY();
        this.buffersArray = new BufferedImage[]{new BufferedImage(sizeX, sizeY, BufferedImage.TYPE_INT_ARGB), new BufferedImage(sizeX, sizeY, BufferedImage.TYPE_INT_ARGB)};
    }

    public BufferedImage getImageFromGrid() {
        indexCounter++;
        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                buffersArray[indexCounter % 2].setRGB(x, y, byteToRGB(pixelGrid.getPixel(x, y)));
            }
        }
        return buffersArray[indexCounter % 2];
    }

    private int byteToRGB(byte material) {
        switch (material) {
            case 0: return Color.GRAY.getRGB();
            case 1: return Color.BLACK.getRGB();
            default: return Color.MAGENTA.getRGB();
        }
    }
}
