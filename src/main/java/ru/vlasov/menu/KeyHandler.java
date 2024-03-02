package ru.vlasov.menu;

import ru.vlasov.util.Util;

import java.util.HashMap;
import java.util.Map;


public class KeyHandler {

    private final Map<String, MenuOptional> menuHandlers = new HashMap<>();

    public KeyHandler() {
        menuHandlers.put("1", new MenuOptional_1());
        menuHandlers.put("2", new MenuOptional_2());
        menuHandlers.put("0", new MenuOptionalExit());
    }

    public void handleMenu() {
        String command = Util.readLine();
        if (menuHandlers.containsKey(command)) {
            menuHandlers.get(command).handle();
        } else {
            handleDefault();
        }
    }

    private void handleDefault() {
        System.out.println("Unknown command, please try one more...");
    }


}
