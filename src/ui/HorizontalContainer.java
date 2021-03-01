package ui;

import core.Position;
import core.Size;

public class HorizontalContainer extends UIContainer {

    public HorizontalContainer(Size windowSize) {
        super(windowSize);
    }

    @Override
    protected Size calculateContentSize() {
        int combinedChildrenWidth = 0;
        int tallestChildHeight = 0;

        for (UiComponent component: children) {
            combinedChildrenWidth += component.getSize().getWidth() + component.getMargin().getHorizontal();

            if (component.getSize().getHeight() > tallestChildHeight) {
                tallestChildHeight = component.getSize().getHeight();
            }
        }

        return new Size(combinedChildrenWidth, tallestChildHeight);
    }

    @Override
    protected void calculateContentPosition() {
        int currentX = padding.getLeft();

        for (UiComponent component: children) {
            currentX += component.getMargin().getLeft();
            component.setRelativePosition(new Position(currentX, padding.getTop()));
            component.setAbsolutePosition(new Position(currentX + absolutePosition.intX(), padding.getTop() + absolutePosition.intY()));
            currentX += component.getSize().getWidth();
            currentX += component.getMargin().getRight();
        }
    }
}
