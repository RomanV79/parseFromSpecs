package ru.vlasov.action;

import ru.vlasov.menu.KeyHandler;
import ru.vlasov.menu.Table;
import ru.vlasov.util.Util;

public class Action {
    private static boolean isExit = false;
    private final Table table;
    private final KeyHandler keyHandler;

    public Action() {
        this.table = new Table();
        this.keyHandler = new KeyHandler();
    }

    public static void setExitTrue() {
        isExit = true;
    }

    public void start() {

        while (!isExit) {
            table.render();
            keyHandler.handleMenu(this);
        }

        Util.scanner.close();
    }
}
