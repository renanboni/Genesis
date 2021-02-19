package ui;

import core.Position;
import core.Size;

public class VerticalContainer extends UIContainer {

    public VerticalContainer(Size windowSize) {
        super(windowSize);
    }

    @Override
    protected Size calculateContentSize() {
        int combinedChildrenHeight = 0;
        int tallestChildWidth = 0;

        for (UiComponent component : children) {
            combinedChildrenHeight += component.getSize().getHeight() + component.getMargin().getVertical();

            if (component.getSize().getWidth() > tallestChildWidth) {
                tallestChildWidth = component.getSize().getWidth();
            }
        }

        return new Size(tallestChildWidth, combinedChildrenHeight);
    }

    @Override
    protected void calculateContentPosition() {
        int currentX = padding.getLeft();
        int currentY = padding.getTop();

        for (UiComponent component : children) {
            if (centerChildren) {
                currentX = getSize().getWidth() / 2 - component.getSize().getWidth() / 2;
            }
            currentY += component.getMargin().getTop();
            component.setRelativePosition(new Position(currentX, currentY));
            component.setAbsolutePosition(new Position(currentX + absolutePosition.intX(), currentY + absolutePosition.intY()));
            currentY += component.getSize().getHeight();
            currentY += component.getMargin().getBottom();
        }
    }
}
