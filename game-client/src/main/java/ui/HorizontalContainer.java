package ui;

import core.Position;
import core.Size;

public class HorizontalContainer extends UIContainer {

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
        int currentY = padding.getTop();

        for (UiComponent component: children) {
            if (centerChildren) {
                currentY = getSize().getHeight() / 2 - component.getSize().getHeight() / 2;
            }
            currentX += component.getMargin().getLeft();
            component.setRelativePosition(new Position(currentX, currentY));
            component.setAbsolutePosition(new Position(currentX + absolutePosition.intX(), currentY + absolutePosition.intY()));
            currentX += component.getSize().getWidth();
            currentX += component.getMargin().getRight();
        }
    }
}
