import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

public class MainLoop {
    private volatile boolean running = true;
    private final JFrame window;
    private final ColorPanel colorPanel;

    private final Runnable task;
    private final Thread mainLoopThread;

    private final PixelRenderer renderer;

    private volatile int ticks = 0;

    public MainLoop(JFrame window, ColorPanel colorPanel, PixelRenderer renderer) {
        this.window = window;
        this.colorPanel = colorPanel;
        this.renderer = renderer;

        this.window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mainLoopThread.interrupt();
                running = false;
            }
        });

        this.task = () -> {
            while (running) {
                update(this.window);

                this.colorPanel.repaint();

                try {
                    Thread.sleep(16);
                } catch (InterruptedException e) { // Сами проверяем что поток не прерван
                    running = false;
                    Thread.currentThread().interrupt();
                }

                ticks++;
            }
        };

        mainLoopThread = new Thread(task);
        mainLoopThread.start();
    }

    public Thread getMainLoopThread() {
        return mainLoopThread;
    }

    private void update(JFrame window) {
        SwingUtilities.invokeLater(() -> {
            window.setTitle(Integer.toString(ticks));
        });

        colorPanel.setImageToDraw(renderer.getImageFromGrid());
    }
}
