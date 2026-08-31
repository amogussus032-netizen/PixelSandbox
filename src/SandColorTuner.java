import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Random;

public class SandColorTuner extends JPanel {
    private static final int GRID_W = 70;
    private static final int GRID_H = 50;
    private static final int CELL_SIZE = 9;
    private static final int[] SPAWN_X = {GRID_W / 4, GRID_W / 2, 3 * GRID_W / 4};

    private final int[][] grid = new int[GRID_W][GRID_H];

    // Индексы: 0 - оттенок (0-360), 1 - насыщенность (0-100), 2 - яркость (0-100)
    private final float[][] hsv = {
            {37f, 61f, 93f},
            {37f, 65f, 86f},
            {37f, 70f, 85f}
    };

    private final Random random = new Random();

    public SandColorTuner() {
        setPreferredSize(new Dimension(GRID_W * CELL_SIZE, GRID_H * CELL_SIZE));
        setBackground(new Color(30, 30, 30));

        Timer timer = new Timer(30, e -> {
            spawn();
            step();
            repaint();
        });
        timer.start();
    }

    private int variantToRGB(int variant) {
        float h = hsv[variant][0] / 360f;
        float s = hsv[variant][1] / 100f;
        float v = hsv[variant][2] / 100f;
        return Color.HSBtoRGB(h, s, v);
    }

    private void spawn() {
        for (int x : SPAWN_X) {
            if (grid[x][0] == 0) {
                grid[x][0] = random.nextInt(3) + 1;
            }
        }
    }

    private void step() {
        for (int y = GRID_H - 1; y >= 0; y--) {
            for (int x = 0; x < GRID_W; x++) {
                if (grid[x][y] == 0) {
                    continue;
                }
                if (get(x, y + 1) == 0) {
                    move(x, y, x, y + 1);
                    continue;
                }
                boolean left = get(x - 1, y + 1) == 0;
                boolean right = get(x + 1, y + 1) == 0;
                if (left && right) {
                    move(x, y, random.nextBoolean() ? x - 1 : x + 1, y + 1);
                } else if (left) {
                    move(x, y, x - 1, y + 1);
                } else if (right) {
                    move(x, y, x + 1, y + 1);
                }
            }
        }
    }

    private int get(int x, int y) {
        if (x < 0 || x >= GRID_W || y < 0 || y >= GRID_H) {
            return -1;
        }
        return grid[x][y];
    }

    private void move(int x1, int y1, int x2, int y2) {
        grid[x2][y2] = grid[x1][y1];
        grid[x1][y1] = 0;
    }

    public void clearGrid() {
        for (int[] column : grid) {
            java.util.Arrays.fill(column, 0);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int x = 0; x < GRID_W; x++) {
            for (int y = 0; y < GRID_H; y++) {
                int variant = grid[x][y];
                if (variant == 0) {
                    continue;
                }
                g.setColor(new Color(variantToRGB(variant - 1)));
                g.fillRect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }
    }

    private JPanel buildControls() {
        JPanel controls = new JPanel();
        controls.setLayout(new BoxLayout(controls, BoxLayout.Y_AXIS));
        controls.setBorder(new EmptyBorder(10, 10, 10, 10));

        String[] channelNames = {"H", "S", "V"};
        int[] maxValues = {360, 100, 100};

        for (int variant = 0; variant < hsv.length; variant++) {
            int v = variant;

            JPanel group = new JPanel();
            group.setLayout(new BoxLayout(group, BoxLayout.Y_AXIS));
            group.setBorder(BorderFactory.createTitledBorder("Вариант " + (variant + 1)));

            JPanel previewRow = new JPanel(new BorderLayout(8, 0));
            JPanel preview = new JPanel();
            preview.setPreferredSize(new Dimension(50, 25));
            preview.setBackground(new Color(variantToRGB(v)));

            JLabel rgbaLabel = new JLabel();
            updateRgbaLabel(rgbaLabel, v);

            previewRow.add(preview, BorderLayout.WEST);
            previewRow.add(rgbaLabel, BorderLayout.CENTER);
            group.add(previewRow);

            for (int channel = 0; channel < 3; channel++) {
                int c = channel;
                JSlider slider = new JSlider(0, maxValues[c], Math.round(hsv[v][c]));
                JLabel label = new JLabel(channelNames[c] + ": " + Math.round(hsv[v][c]));

                slider.addChangeListener(e -> {
                    hsv[v][c] = slider.getValue();
                    label.setText(channelNames[c] + ": " + slider.getValue());
                    preview.setBackground(new Color(variantToRGB(v)));
                    updateRgbaLabel(rgbaLabel, v);
                });

                group.add(label);
                group.add(slider);
            }
            controls.add(group);
        }

        JButton restartButton = new JButton("Очистить кучу");
        restartButton.addActionListener(e -> clearGrid());
        controls.add(restartButton);

        return controls;
    }

    private void updateRgbaLabel(JLabel label, int variant) {
        Color color = new Color(variantToRGB(variant));
        label.setText(String.format(
                "<html>RGBAtoInt(%d, %d, %d, 255)</html>",
                color.getRed(), color.getGreen(), color.getBlue()
        ));
    }

    public static void main(String[] args) {
        SandColorTuner tuner = new SandColorTuner();

        JFrame frame = new JFrame("Подбор цветов песка");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(tuner, BorderLayout.CENTER);
        frame.add(tuner.buildControls(), BorderLayout.EAST);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
