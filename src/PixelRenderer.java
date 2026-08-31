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

    private int byteToRGB(byte packedPixelData) {
        int materialId = packedPixelData & 0b00011111;
        int variant = (packedPixelData >> 5) & 0b111;

        switch (materialId) {
            case 0: {
                return Color.LIGHT_GRAY.getRGB();
            }
            case 1: {
                switch (variant) {
                    case 0: return RGBAtoInt(209, 192, 105, 255);
                    case 1: return RGBAtoInt(219, 201, 110, 255);
                    case 2: return RGBAtoInt(199, 182, 99,  255);
                }
            }
            default: {
                return Color.MAGENTA.getRGB();
            }
        }
    }

    public PixelGrid getPixelGrid() {
        return pixelGrid;
    }

    private int RGBAtoInt( int r, int g, int b, int a) {
        if (!(r <= 255 && g <= 255 && b <= 255 && a <= 255)) return Color.MAGENTA.getRGB();

        return (a << 24) | (r << 16) | (g << 8) | b;
    }
}
