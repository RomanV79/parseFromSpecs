package ru.vlasov.parse;

import ru.vlasov.entity.Element;
import ru.vlasov.util.Convert;
import ru.vlasov.util.Util;

import java.util.ArrayList;
import java.util.List;

public class ParseFromConsole implements Parsing {
    private final Convert convert = new Convert();

    @Override
    public List<Element> parse() {
        List<String> strings = Util.readLines();
        List<Element> elements = new ArrayList<>();
        System.out.println("read " + strings.size() + " lines");
        strings.forEach(line -> elements.add(convert.fromLinesToElement(line)));
        return elements;
    }
}
