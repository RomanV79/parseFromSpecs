package ru.vlasov.parse;

import ru.vlasov.entity.Element;
import ru.vlasov.util.Convert;
import ru.vlasov.util.Util;

import java.util.List;

public class ParseFromConsole implements Parsing {
    private final Convert convert = new Convert();

    @Override
    public Element parse() {
        List<String> strings = Util.readLines();
        System.out.println("read " + strings.size() + " lines");
        Element element = convert.createObject(strings);

        return element;
    }
}
