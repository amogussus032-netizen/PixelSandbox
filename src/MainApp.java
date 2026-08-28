import javax.swing.*;
public class MainApp {
    public static void main(String[] args) {
        // invokeLater позволяет выполнять создание окна в нужном потоке(автоматически выполняет Runnable в потоке EDT)
        ColorPanel panel = new ColorPanel("Panel"); // ColorPanel лишь панель, которая отображается внутри окна т.к. наследует Jpanel
        JFrame window = new JFrame("Window");
        PixelGrid mainGrid = new PixelGrid(320, 180);

        SwingUtilities.invokeLater(() -> {
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.add(panel);
            window.setSize(500, 400);
            window.setVisible(true);

            PixelRenderer mainRenderer = new PixelRenderer(mainGrid);

            MouseManager mouseManager = new MouseManager(panel, mainGrid);

            MainLoop mainLoop = new MainLoop(window, panel, mainRenderer);
        });

        mainGrid.setPixel(100, 100, (byte) 1);
        mainGrid.setPixel(120, 110, (byte) 1);
        mainGrid.setPixel(150, 80, (byte) 1);


    }
}