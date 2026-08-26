import javax.swing.*;
public class MainApp {
    public static void main(String[] args) {
        // invokeLater позволяет выполнять создание окна в нужном потоке(автоматически выполняет Runnable в потоке EDT)
        SwingUtilities.invokeLater(() -> {
            ColorPanel panel = new ColorPanel("Panel"); // ColorPanel лишь панель, которая отображается внутри окна т.к. наследует Jpanel

            JFrame window = new JFrame("Window");
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.add(panel);
            window.setSize(500, 400);
            window.setVisible(true);
        });
    }
}