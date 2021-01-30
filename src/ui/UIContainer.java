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

    public UIContainer() {
        super();
        backgroundColor = Color.RED;
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
        position = new Position(margin.getLeft(), margin.getTop());
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
}
