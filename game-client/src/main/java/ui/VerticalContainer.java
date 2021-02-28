package ui;

import core.Position;
import core.Size;

public class VerticalContainer extends UIContainer {

    @Override
    protected Size calculateContentSize() {
        int combinedChildHeight = 0;
        int widestChildWidth = 0;

        for(UiComponent uiComponent : children) {
            combinedChildHeight += uiComponent.getSize().getHeight() + uiComponent.getMargin().getVertical();

            if(uiComponent.getSize().getWidth() > widestChildWidth) {
                widestChildWidth = uiComponent.getSize().getWidth();
            }
        }

        return new Size(widestChildWidth, combinedChildHeight);
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
