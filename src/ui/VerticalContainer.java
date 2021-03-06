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
        int currentY = padding.getTop();

        for (UiComponent component : children) {
            currentY += component.getMargin().getTop();
            component.setRelativePosition(new Position(padding.getLeft(), currentY));
            component.setAbsolutePosition(new Position(padding.getLeft() + absolutePosition.intX(), currentY + absolutePosition.intY()));
            currentY += component.getSize().getHeight();
            currentY += component.getMargin().getBottom();
        }
    }
}
