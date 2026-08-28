import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseManager {
    private final PixelGrid grid;
    private final ColorPanel panel;

    private MouseAdapter adapter = new MouseAdapter() {
        @Override
        public void mouseDragged(MouseEvent e) {
            replaceSquareOfPixels(e.getX(), e.getY(), 6, (byte) 1);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            replaceSquareOfPixels(e.getX(), e.getY(), 6, (byte) 1);
        }
    };

    public MouseManager(ColorPanel panel, PixelGrid grid) {
        this.grid = grid;
        this.panel = panel;
        this.panel.addMouseMotionListener(adapter);
        this.panel.addMouseListener(adapter);
    }

    private void replaceSquareOfPixels(int mouseX, int mouseY, int size, byte material) {
        Point cords = panel.panelCordsToGridCords(mouseX, mouseY);

        if (cords == null) return;

        for (int x = cords.x - size / 2; x < (cords.x - size / 2) + size; x++) {
            for (int y = cords.y - size / 2; y < (cords.y - size / 2) + size; y++) {
                grid.setPixel(x, y, material);
            }
        }
    }
}
