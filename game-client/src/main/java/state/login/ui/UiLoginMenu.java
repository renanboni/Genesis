package state.login.ui;

import game.settings.Value;
import main.Client;
import ui.Alignment;
import ui.Spacing;
import ui.UIContainer;
import ui.VerticalContainer;
import ui.clickable.UiButton;
import ui.input.UiTextInput;

public class UiLoginMenu extends VerticalContainer {

    private final Value<String> email = new Value<>("");
    private final Value<String> password = new Value<>("");;

    public UiLoginMenu(Client client) {
        alignment = new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER);

        UIContainer container = new VerticalContainer();
        container.setMargin(new Spacing(0));
        container.setPadding(new Spacing(0));

        container.addUiComponent(new UiTextInput("Email", email));
        container.addUiComponent(new UiTextInput("Password", password));

        container.addUiComponent(new UiButton("Login", state -> client.login(email.get(), password.get())));

        addUIComponent(container);
    }
}
