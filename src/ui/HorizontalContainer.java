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

        for (UiComponent component: children) {
            currentX += component.getMargin().getLeft();
            component.setPosition(new Position(currentX, padding.getTop()));
            currentX += component.getSize().getWidth();
            currentX += component.getMargin().getRight();
        }
    }
}
