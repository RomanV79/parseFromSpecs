package ru.vlasov.menu;

import ru.vlasov.dto.Scheme;
import ru.vlasov.entity.Element;
import ru.vlasov.parse.ParseFromConsole;

import java.util.List;

public class MenuOptional_1 implements MenuOptional {
    private final ParseFromConsole parseFromConsole = new ParseFromConsole();
    private final Scheme scheme = new Scheme();

    @Override
    public void handle() {
        System.out.println("Please enter model and type \"end\" after");
        Element element = parseFromConsole.parse();
        System.out.println(scheme.generate(element));
    }
}
