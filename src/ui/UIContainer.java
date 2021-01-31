package ui;

import core.Position;
import core.Size;
import gfx.ImageUtils;
import state.State;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public abstract class UIContainer extends UiComponent {

    protected Color backgroundColor;

    protected List<UiComponent> children = new ArrayList<>();

    protected Alignment alignment;
    protected Size windowSize;

    public UIContainer(Size windowSize) {
        super();
        backgroundColor = new Color(0, 0, 0, 0);
        this.windowSize = windowSize;
        alignment = new Alignment(Alignment.Position.START, Alignment.Position.START);
        padding = new Spacing(5);
        margin = new Spacing(5);
        calculateSize();
        calculatePosition();
    }

    protected abstract Size calculateContentSize();

    protected abstract void calculateContentPosition();

    private void calculateSize() {
        Size calculatedContentSize = calculateContentSize();
        size = new Size(padding.getHorizontal() + calculatedContentSize.getWidth(),
                padding.getVertical() + calculatedContentSize.getHeight());
    }

    private void calculatePosition() {
        int x = padding.getLeft();

        if (alignment.getHorizontal().equals(Alignment.Position.CENTER)) {
            x = windowSize.getWidth() / 2 - size.getWidth() / 2;
        }

        if (alignment.getHorizontal().equals(Alignment.Position.END)) {
            x = windowSize.getWidth() - size.getWidth() - margin.getRight();
        }

        int y = padding.getTop();

        if (alignment.getVertical().equals(Alignment.Position.CENTER)) {
            y = windowSize.getHeight() / 2 - size.getHeight() / 2;
        }

        if (alignment.getVertical().equals(Alignment.Position.END)) {
            y = windowSize.getHeight() - size.getHeight() - margin.getBottom();
        }

        this.position = new Position(x, y);
        calculateContentPosition();
    }

    @Override
    public Image getSprite() {
        BufferedImage image = (BufferedImage) ImageUtils.createCompatibleImage(size, ImageUtils.ALPHA_BIT_MASKED);
        Graphics2D graphics2D = image.createGraphics();

        graphics2D.setColor(backgroundColor);
        graphics2D.fillRect(0, 0, size.getWidth(), size.getHeight());

        children.forEach(uiComponent -> graphics2D.drawImage(
                uiComponent.getSprite(),
                uiComponent.getPosition().intX(),
                uiComponent.getPosition().intY(),
                null
        ));

        graphics2D.dispose();
        return image;
    }

    @Override
    public void update(State state) {
        children.forEach(uiComponent -> uiComponent.update(state));
        calculateSize();
        calculatePosition();
    }

    public void addUiComponent(UiComponent component) {
        children.add(component);
    }

    public void setBackgroundColor(Color color) {
        backgroundColor = color;
    }

    public void setAlignment(Alignment alignment) {
        this.alignment = alignment;
    }

    public Alignment getAlignment() {
        return alignment;
    }
}
