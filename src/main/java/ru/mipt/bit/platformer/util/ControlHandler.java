package ru.mipt.bit.platformer.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

import com.badlogic.gdx.Input;

import ru.mipt.bit.platformer.entity.interfaces.Command;

public class ControlHandler {
    private final Collection<ButtonAction> buttonActions;

    public ControlHandler() {
        this.buttonActions = new ArrayList<>();
    }

    public void addButtonAction(Collection<Integer> buttons, Command command, boolean toggleOnEveryRender) {
        buttonActions.add(new ButtonAction(buttons, command, toggleOnEveryRender));
    }

    public void handle(Input input) {
        for (ButtonAction buttonAction : buttonActions) {
            if (buttonAction.isToggleOnEveryRender()) {
                executeCommand(buttonAction, input::isKeyPressed);
            } else {
                executeCommand(buttonAction, input::isKeyJustPressed);
            }
        }
    }

    private void executeCommand(ButtonAction buttonAction, Predicate<Integer> keyPressPredicate) {
        if (buttonAction.getButtons().stream().anyMatch(keyPressPredicate)) {
            buttonAction.getCommand().execute();
        }
    }

    private static class ButtonAction {
        private final Collection<Integer> buttons;
        private final Command command;
        private final boolean toggleOnEveryRender;

        public ButtonAction(Collection<Integer> buttons, Command command, boolean toggleOnEveryRender) {
            this.buttons = List.copyOf(buttons);
            this.command = command;
            this.toggleOnEveryRender = toggleOnEveryRender;
        }

        public Collection<Integer> getButtons() {
            return buttons;
        }

        public Command getCommand() {
            return command;
        }

        public boolean isToggleOnEveryRender() {
            return toggleOnEveryRender;
        }
    }
}