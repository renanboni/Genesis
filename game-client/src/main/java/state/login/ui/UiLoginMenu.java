package state.login.ui;

import game.settings.Value;
import main.Client;
import ui.*;
import ui.clickable.UiButton;
import ui.input.UiTextInput;

public class UiLoginMenu extends VerticalContainer {

    private final Value<String> email = new Value<>("");
    private final Value<String> password = new Value<>("");;

    public UiLoginMenu(Client client) {
        alignment = new Alignment(Alignment.Position.CENTER, Alignment.Position.CENTER);

        UiTabContainer container = new UiTabContainer();
        container.addTab("Login", getLoginContent(client));
        container.addTab("Sign up", getSignUpContent(client));

        addUIComponent(container);
    }

    public UIContainer getLoginContent(Client client) {
        UIContainer container = new VerticalContainer();
        container.setMargin(new Spacing(0));
        container.setPadding(new Spacing(0));

        container.addUiComponent(new UiTextInput("Name", email));
        container.addUiComponent(new UiTextInput("Password", password));

        container.addUiComponent(new UiButton("Login", state -> client.loginOrRegister(email.get(), password.get(), true)));

        return container;
    }

    public UIContainer getSignUpContent(Client client) {
        UIContainer container = new VerticalContainer();
        container.setMargin(new Spacing(0));
        container.setPadding(new Spacing(0));

        container.addUiComponent(new UiTextInput("Name", email));
        container.addUiComponent(new UiTextInput("Password", password));

        container.addUiComponent(new UiButton("Sign up", state -> client.loginOrRegister(email.get(), password.get(), false)));

        return container;
    }
}
