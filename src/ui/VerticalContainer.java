package ui;

import core.Position;
import core.Size;

public class VerticalContainer extends UIContainer {

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
            component.setPosition(new Position(padding.getLeft(), currentY));
            currentY += component.getSize().getHeight();
            currentY += component.getMargin().getBottom();
        }
    }
}
