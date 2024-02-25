package ru.vlasov.menu;

import ru.vlasov.action.Action;

public class MenuOptionalExit implements MenuOptional {
    @Override
    public void handle() {
        System.out.println("Application stopped");
        Action.setExitTrue();
    }
}
