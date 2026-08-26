import javax.swing.*;
import java.awt.*;

public class ColorPanel extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Super означает вызов оригинального метода(без оверрайд). Оригинальный метод заливает фоновым цветом\очищает холст.
        g.setColor(Color.ORANGE);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    public ColorPanel(String name) {
        this.setName(name);
    }
}
