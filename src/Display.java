import javax.swing.*;
import java.awt.*;

public class Display extends JFrame {

    private final Canvas canvas;

    public Display(int width, int height) {
        setTitle("Genesis");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setFocusable(false);
        add(canvas);
        pack();

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
