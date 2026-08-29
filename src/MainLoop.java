import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class MainLoop {
    private volatile boolean running = true;
    private final JFrame window;
    private final ColorPanel colorPanel;

    private final Runnable task;
    private final Thread mainLoopThread;

    private final PixelRenderer renderer;

    final double FIXED_DT = 1.0 / 60.0; // сек на один шаг симуляции

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
            long lastTime = System.nanoTime();
            double accumulator = 0.0;
            while (running) {
                long newTime = System.nanoTime();
                accumulator += (newTime - lastTime) / 1_000_000_000.0; // делим, чтобы перевести наносекунды в секунды

                while (accumulator >= FIXED_DT) {
                    Simulation.step(FIXED_DT);
                    accumulator -= FIXED_DT;
                }

                render(this.window);
                this.colorPanel.repaint();

                newTime = System.nanoTime();

                try {
                    Thread.sleep(Math.max((int) Math.floor((FIXED_DT - ((newTime - lastTime) / 1_000_000_000.0)) * 1000.0), 0));
                } catch (InterruptedException e) {
                    running = false;
                    Thread.currentThread().interrupt();
                }

                lastTime = System.nanoTime();
                ticks++;
            }
        };

        mainLoopThread = new Thread(task);
        mainLoopThread.start();
    }

    public Thread getMainLoopThread() {
        return mainLoopThread;
    }

    private void render(JFrame window) {
        SwingUtilities.invokeLater(() -> {
            window.setTitle(Integer.toString(ticks));
        });

        colorPanel.setImageToDraw(renderer.getImageFromGrid());
    }
}
