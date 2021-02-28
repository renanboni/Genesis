package ui;

import state.State;
import ui.clickable.UiClickable;

import java.awt.*;

public class UiTabContainer extends VerticalContainer {

    private UIContainer tabContainer;
    private UIContainer contentContainer;
    private UiTab activeTab;

    public UiTabContainer() {
        tabContainer = new HorizontalContainer();
        contentContainer = new VerticalContainer();

        setMargin(new Spacing());

        tabContainer.setPadding(new Spacing());
        tabContainer.setMargin(new Spacing());

        contentContainer.setMargin(new Spacing());
        contentContainer.setBackgroundColor(Color.GRAY);

        addUIComponent(tabContainer);
        addUIComponent(contentContainer);
    }

    private void activateTab(UiTab uiTab) {
        activeTab = uiTab;
        contentContainer.clear();
        contentContainer.addUiComponent(uiTab.getContents());
    }

    public void addTab(String label, UIContainer contents) {
        UiTab tab = new UiTab(this, label, contents);
        tabContainer.addUiComponent(tab);

        if (activeTab == null) {
            activeTab = tab;
        }
    }

    static class UiTab extends UiClickable {

        private UiTabContainer parent;
        private UIContainer label;
        private UiComponent contents;

        public UiTab(UiTabContainer parent, String label, UiComponent contents) {
            this.parent = parent;
            this.contents = contents;

            contents.setMargin(new Spacing());

            this.label = new VerticalContainer();
            this.label.addUiComponent(new UiText(label));
            this.label.setBackgroundColor(Color.DARK_GRAY);
        }

        @Override
        public void update(State state) {
            super.update(state);
            label.update(state);
            size = label.getSize();

            label.setBackgroundColor(Color.DARK_GRAY);

            if (hasFocus) {
                this.label.setBackgroundColor(Color.LIGHT_GRAY);
            }

            if (parent.activeTab.equals(this)) {
                this.label.setBackgroundColor(Color.GRAY);
            }
        }

        @Override
        protected void onFocus(State state) {

        }

        @Override
        public void onClick(State state) {
            parent.activateTab(this);
        }

        @Override
        public void onDrag(State state) {

        }

        @Override
        public Image getSprite() {
            return label.getSprite();
        }

        public UiComponent getContents() {
            return contents;
        }
    }
}
