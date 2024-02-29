package ru.vlasov.util;

import ru.vlasov.entity.Element;
import ru.vlasov.entity.Repetition;

import java.util.ArrayList;
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

    private void createElement(List<String> strings, Element parentElement, int index, int level) {
        index++;
        level++;
        List<String> required = new ArrayList<>();

        while (index < strings.size() && level == getStringLevel(strings.get(index))) {
            if (isField(strings.get(index))) {
                Element element = new Element();
                element.setDescription(getDescription(strings.get(index)));

                element.setType(getType(strings.get(index)));
                checkAndFixNotSupportedType(element);

                if (parentElement.getProperties() == null) {
                    Map<String, Element> properties = new HashMap<>();
                    parentElement.setProperties(properties);
                }
                parentElement.getProperties().put(getName(strings.get(index)), element);

                if(isRequired(strings.get(index))) {
                    required.add(getName(strings.get(index)));
                }
                parentElement.setRequired(required);

                index++;
            }

            if (isObject(strings.get(index))) {
                Element element = new Element();
                element.setDescription(getDescription(strings.get(index)));
                element.setType("object");
                element.setAdditionalProperties(false);

                if (parentElement.getProperties() == null) {
                    Map<String, Element> properties = new HashMap<>();
                    parentElement.setProperties(properties);
                }
                parentElement.getProperties().put(getName(strings.get(index)), element);

                createElement(strings, element, index, level);
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

    private boolean isObject(String line) {
        String[] parts = line.split("\t");
        return parts[3].isEmpty()
                && (parts[4].equals("0..1")
                        || parts[4].equals("1..1")
                        || parts[4].equals("1"));
    }

    private boolean isRequired(String line) {
        String value = line.split("\t")[4];
        return value.equals("1")
                || value.equals("1..1")
                || value.equals("1..неограниченно");
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
