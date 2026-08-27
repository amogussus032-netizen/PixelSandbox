import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ColorPanel extends JPanel {
    private volatile BufferedImage imageToDraw;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Super означает вызов оригинального метода(без оверрайд). Оригинальный метод заливает фоновым цветом\очищает холст.

        if (imageToDraw == null) return;

        double scaleX = (double) getWidth() / imageToDraw.getWidth();
        double scaleY = (double) getHeight() / imageToDraw.getHeight();
        double scale = (int) Math.min(scaleX, scaleY); // один коэффициент чтобы пиксели сохранили квадратность

        int drawWidth = (int) (imageToDraw.getWidth() * scale);
        int drawHeight = (int) (imageToDraw.getHeight() * scale);
        int offsetX = (getWidth() - drawWidth) / 2;
        int offsetY = (getHeight() - drawHeight) / 2; // считаем где рисовать изображение так как из-за одного коэффициента возникают пустые поля

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
}
