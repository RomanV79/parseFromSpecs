package ru.vlasov.util;

import ru.vlasov.entity.Element;
import ru.vlasov.entity.Repetition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Convert {

    public Element createObject(List<String> strings) {
        Element rootElement = new Element();
        int index = 0;
        int level = 1;
        rootElement.set$schema("http://json-schema.org/draft-04/schema#");
        rootElement.setTitle(getTitleName(strings.get(index)));
        rootElement.setType("object");
        rootElement.setAdditionalProperties(false);
        createElement(strings, rootElement, index, level);

        return rootElement;
    }

    private void createElement(List<String> strings, Element rootElement, int index, int level) {
        index++;
        level++;
        while (index < strings.size() && level == getStringLevel(strings.get(index))) {
            if (isField(strings.get(index))) {
                Element element = new Element();
                element.setDescription(getDescription(strings.get(index)));

                element.setType(getType(strings.get(index)));
                checkAndFixNotSupportedType(element);

                if (rootElement.getProperties() == null) {
                    Map<String, Element> properties = new HashMap<>();
                    rootElement.setProperties(properties);
                }
                rootElement.getProperties().put(getName(strings.get(index)), element);
                index++;
            }
        }
    }

    private void checkAndFixNotSupportedType(Element element) {
        if (element.getType().equals("long")) {
            element.setType("integer");
            element.setFormat("int64");
        }
        if (element.getType().equals("decimal")) {
            element.setType("number");
            element.setFormat("decimal");
        }
        if (element.getType().equals("date")) {
            element.setType("string");
            element.setFormat("date");
        }
    }

    private int getStringLevel(String line) {
        return line.split("\t")[0].split("\\.").length;
    }

    private boolean isField(String line) {
        String[] parts = line.split("\t");
        return !parts[3].isEmpty();
    }

    private String getName(String line) {
        return line.split("\t")[1];
    }

    private String getDescription(String line) {
        return line.split("\t")[5];
    }

    private String getType(String line) {
        String type = line.split("\t")[3];
        if (type.contains("xsd:")) {
            type = type.split(":")[1];
        }
        return type;
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

//    private Type getType(String line) {
//        Type type = null;
//        if (!line.isEmpty()) {
//            try {
//                type = Type.valueOf(line.toUpperCase());
//            } catch (IllegalArgumentException e) {
//                throw new IllegalArgumentException("No enum constant " + Type.class + " with value " + line);
//            }
//        } else {
//            type = Type.EMPTY;
//        }
//
//        return type;
//    }

    private Repetition getRepetitionFromValue(String line) {
        for (Repetition item : Repetition.values()) {
            if (item.getValue().equals(line.toLowerCase())) {
                return item;
            }
        }
        throw new IllegalArgumentException("No enum constant " + Repetition.class + " with value " + line);
    }

    private String getTitleName(String line) {
        String rootName = line.split("\t")[1];
        String[] parts = rootName.split("_");
        return parts[1] + parts[2];
    }
}
