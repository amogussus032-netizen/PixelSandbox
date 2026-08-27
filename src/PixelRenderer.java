import java.awt.*;
import java.awt.image.BufferedImage;

public class PixelRenderer {
    private final PixelGrid pixelGrid;
    private final int sizeX;
    private final int sizeY;
    private final BufferedImage image;

    public PixelRenderer(PixelGrid pixelGrid) {
        this.pixelGrid = pixelGrid;
        this.sizeX = pixelGrid.getSizeX();
        this.sizeY = pixelGrid.getSizeY();
        this.image = new BufferedImage(sizeX, sizeY, BufferedImage.TYPE_INT_ARGB);
    }

    public BufferedImage getImageFromGrid() {

        for (int x = 0; x < sizeX; x++) {
            for (int y = 0; y < sizeY; y++) {
                image.setRGB(x, y, byteToRGB(pixelGrid.getPixel(x, y)));
            }
        }

        return image;
    }

    private int byteToRGB(byte material) {
        switch (material) {
            case 0: return Color.GRAY.getRGB();
            case 1: return Color.BLACK.getRGB();
            default: return Color.MAGENTA.getRGB();
        }
    }
}
