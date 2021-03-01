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
    protected Size fixedSize;
    protected Image sprite;

    protected boolean centerChildren;

    public UIContainer() {
        super();
        backgroundColor = new Color(0, 0, 0, 0);
        centerChildren = false;
        alignment = new Alignment(Alignment.Position.START, Alignment.Position.START);
        padding = new Spacing(5);
        margin = new Spacing(5);
    }

    protected abstract Size calculateContentSize();

    protected abstract void calculateContentPosition();

    private void calculateSize() {
        Size calculatedContentSize = calculateContentSize();
        size = fixedSize != null ? fixedSize : new Size(padding.getHorizontal() + calculatedContentSize.getWidth(),
                padding.getVertical() + calculatedContentSize.getHeight());
    }

    private void calculatePosition() {
        int x = margin.getLeft();

        if (alignment.getHorizontal().equals(Alignment.Position.CENTER)) {
            x = parent.getSize().getWidth() / 2 - size.getWidth() / 2;
        }

        if (alignment.getHorizontal().equals(Alignment.Position.END)) {
            x = parent.getSize().getWidth() - size.getWidth() - margin.getRight();
        }

        int y = margin.getTop();

        if (alignment.getVertical().equals(Alignment.Position.CENTER)) {
            y = parent.getSize().getHeight() / 2 - size.getHeight() / 2;
        }

        if (alignment.getVertical().equals(Alignment.Position.END)) {
            y = parent.getSize().getHeight() - size.getHeight() - margin.getBottom();
        }

        this.relativePosition = new Position(x, y);
        calculateContentPosition();
    }

    @Override
    public Image getSprite() {
        return sprite;
    }

    protected void generateSprite() {
        sprite = ImageUtils.createCompatibleImage(size, ImageUtils.ALPHA_BIT_MASKED);
        Graphics2D graphics = (Graphics2D) sprite.getGraphics();

        graphics.setColor(backgroundColor);
        graphics.fillRect(0, 0, size.getWidth(), size.getHeight());

        for(UiComponent uiComponent : children) {
            graphics.drawImage(
                    uiComponent.getSprite(),
                    uiComponent.getRelativePosition().intX(),
                    uiComponent.getRelativePosition().intY(),
                    null
            );
        }

        graphics.dispose();
    }

    @Override
    public void update(State state) {
        children.forEach(uiComponent -> uiComponent.update(state));
        calculateSize();
        calculatePosition();

        if(state.getTime().secondsDividableBy(0.05)) {
            generateSprite();
        }
    }

    public void addUiComponent(UiComponent component) {
        children.add(component);
        component.setParent(this);
    }

    public void clear() {
        children.clear();
    }

    public void setBackgroundColor(Color color) {
        backgroundColor = color;
    }

    public void setAlignment(Alignment alignment) {
        this.alignment = alignment;
    }

    public void setFixedSize(Size fixedSize) {
        this.fixedSize = fixedSize;
    }

    public Alignment getAlignment() {
        return alignment;
    }

    public void setCenterChildren(boolean centerChildren) {
        this.centerChildren = centerChildren;
    }

    public void addUIComponent(UiComponent uiComponent) {
        children.add(uiComponent);
        uiComponent.setParent(this);
    }

    public boolean hasComponent(UiComponent component) {
        return children.contains(component);
    }

    public void removeComponent(UiComponent component) {
        children.remove(component);
    }

    public List<UiComponent> getComponents() {
        return children;
    }
}
