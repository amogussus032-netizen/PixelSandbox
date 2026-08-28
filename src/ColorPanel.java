import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ColorPanel extends JPanel {
    private volatile BufferedImage imageToDraw;
    private int scale = 1;
    private int offsetX = 0;
    private int offsetY = 0;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Super означает вызов оригинального метода(без оверрайд). Оригинальный метод заливает фоновым цветом\очищает холст.

        if (imageToDraw == null) return;

        double scaleX = (double) getWidth() / imageToDraw.getWidth();
        double scaleY = (double) getHeight() / imageToDraw.getHeight();
        scale = (int) Math.min(scaleX, scaleY); // один коэффициент чтобы пиксели сохранили квадратность

        int drawWidth = (int) (imageToDraw.getWidth() * scale);
        int drawHeight = (int) (imageToDraw.getHeight() * scale);
        offsetX = (getWidth() - drawWidth) / 2;
        offsetY = (getHeight() - drawHeight) / 2; // считаем где рисовать изображение так как из-за одного коэффициента возникают пустые поля

        Graphics2D g2D = (Graphics2D) g;
        g2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR); // настраиваем растяжение без размытия

        g2D.drawImage(imageToDraw, offsetX, offsetY, drawWidth, drawHeight, null);
    }

    public ColorPanel(String name) {
        this.setName(name);
    }

    public void setImageToDraw(BufferedImage imageToDraw) {
        this.imageToDraw = imageToDraw;
    }

    public Point panelCordsToGridCords(int x, int y) {
        if (imageToDraw == null) return null;
        if (!(x >= offsetX && y >= offsetY && x < imageToDraw.getWidth() * scale + offsetX && y < imageToDraw.getHeight() * scale + offsetY && scale != 0)) return null;

        int gridX = (x - offsetX) / scale;
        int gridY = (y - offsetY) / scale;

        return new Point(gridX, gridY);
    }
}
