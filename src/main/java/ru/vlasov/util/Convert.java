package ru.vlasov.util;

import ru.vlasov.entity.Element;
import ru.vlasov.entity.ElementType;
import ru.vlasov.entity.Repetition;
import ru.vlasov.entity.Type;

import java.util.Arrays;


public class Convert {
    public Element fromLinesToElement(String line) {
        String[] lines = line.split("\t");

        Element element = new Element();

        if (lines[1].startsWith("mt_")) {
            element.setLevel(lines[0].split("\\.").length);
            element.setElementType(ElementType.HEADER);
            element.setName(lines[1].substring(3).replace("_", ""));
        } else {
            element.setLevel(lines[0].split("\\.").length);
            element.setName(lines[1]);
            element.setType(getType(getClearType(lines[3])));
            element.setRepetition(getRepetitionFromValue(lines[4]));
            element.setDescription(String.join(" ", Arrays.copyOfRange(lines, 5, lines.length)));
            if (element.getType().equals(Type.EMPTY)
                    && (element.getRepetition().equals(Repetition.ZERO_TO_ONE)
                                    || element.getRepetition().equals(Repetition.ONE))) {
                element.setElementType(ElementType.OBJECT);
            } else if(element.getType().equals(Type.EMPTY)
                    && (element.getRepetition().equals(Repetition.ZERO_TO_UNBOUNDED)
                    || element.getRepetition().equals(Repetition.ONE_TO_UNBOUNDED))) {
                element.setElementType(ElementType.ARRAY);
            } else {
                element.setElementType(ElementType.FIELD);
            }
        }
        System.out.println(element.toString());
        return element;
    }

    private String getClearType(String line) {
        String type = "";
        if (!line.isEmpty()) {
            String[] lines = line.split(":");
            if (lines.length > 1) {
                type = lines[1];
            } else {
                type = lines[0];
            }
        }
        return type;
    }

    private Type getType(String line) {
        Type type = null;
        if (!line.isEmpty()) {
            try {
                type = Type.valueOf(line.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("No enum constant " + Type.class + " with value " + line);
            }
        } else {
            type = Type.EMPTY;
        }

        return type;
    }

    private Repetition getRepetitionFromValue(String line) {
        for (Repetition item : Repetition.values()) {
            if (item.getValue().equals(line.toLowerCase())) {
                return item;
            }
        }
        throw new IllegalArgumentException("No enum constant " + Repetition.class + " with value " + line);
    }
}
