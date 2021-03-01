package display;

import core.Size;
import game.ResizeCallback;
import input.Input;
import state.State;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferStrategy;

public class Display extends JFrame {

    private final Canvas canvas;

    public Display(int width, int height, Input input, ResizeCallback callback) {
        setTitle("Genesis");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                setPreferredSize(e.getComponent().getSize());
                pack();
                callback.resize(new Size(getContentPane().getWidth(), getContentPane().getHeight()));
                canvas.setPreferredSize(getContentPane().getPreferredSize());
            }
        });

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setFocusable(false);
        canvas.addMouseListener(input);
        canvas.addMouseMotionListener(input);
        add(canvas);
        addKeyListener(input);
        pack();

        canvas.createBufferStrategy(2);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void render(State state, boolean debugMode) {
        BufferStrategy bufferStrategy = canvas.getBufferStrategy();
        Graphics graphics = bufferStrategy.getDrawGraphics();

        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        Renderer.render(state, graphics);

        if (debugMode) {
            DebugRenderer.render(state, graphics);
        }

        graphics.dispose();

        bufferStrategy.show();
    }
}
