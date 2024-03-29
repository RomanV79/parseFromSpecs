package ru.vlasov.util;

import ru.vlasov.entity.Element;

import java.util.*;


public class Convert {


    public Element createObjectFromLines(List<String> strings) {
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

    private int createElement(List<String> strings, Element parentElement, int index, int level) {
        index++;
        level++;
        List<String> required = new ArrayList<>();

        while (index < strings.size() && level == getStringLevel(strings.get(index))) {
            if (isField(strings.get(index))) {
                Element element = new Element();
                element.setDescription(getDescription(strings.get(index)));

                element.setType(getType(strings.get(index)));
                checkAndFixNotSupportedType(element);

                addToPropertiesForParentElement(strings, parentElement, index, element);
                addToRequiredForParentElement(strings, parentElement, index, required);

                index++;
                if (index == strings.size() || getStringLevel(strings.get(index)) == level - 1) {
                    break;
                }
            }

            if (isObject(strings.get(index))) {
                Element element = new Element();
                element.setDescription(getDescription(strings.get(index)));
                element.setType("object");
                element.setAdditionalProperties(false);

                addToPropertiesForParentElement(strings, parentElement, index, element);
                addToRequiredForParentElement(strings, parentElement, index, required);

                index = createElement(strings, element, index, level);
                if (index == strings.size()) {
                    break;
                }
            }

            if (isArray(strings.get(index))) {
                Element element = new Element();
                element.setDescription(getDescription(strings.get(index)));
                element.setType("array");

                addToPropertiesForParentElement(strings, parentElement, index, element);
                addToRequiredForParentElement(strings, parentElement, index, required);

                Element childElement = new Element();
                childElement.setType("object");
                childElement.setAdditionalProperties(false);

                element.setItems(childElement);

                index = createElement(strings, childElement, index, level);
            }
        }
        return index;
    }

    private void addToRequiredForParentElement(List<String> strings, Element parentElement, int index, List<String> required) {
        if (isRequired(strings.get(index))) {
            required.add(getName(strings.get(index)));
        }
        parentElement.setRequired(required);
    }

    private void addToPropertiesForParentElement(List<String> strings, Element parentElement, int index, Element element) {
        if (parentElement.getProperties() == null) {
            Map<String, Element> properties = new HashMap<>();
            parentElement.setProperties(properties);
        }
        parentElement.getProperties().put(getName(strings.get(index)), element);
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

    private boolean isArray(String line) {
        String[] parts = line.split("\t");
        return parts[3].isEmpty()
                && (parts[4].equals("0..неограниченно")
                || parts[4].equals("1..неограниченно")
                || parts[4].equals("0..unbounded")
                || parts[4].equals("1..unbounded")
        );
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

    private String getTitleName(String line) {
        String nameCell = line.split("\t")[1];
        String[] parts = nameCell.split("_");
        String title;
        if (parts.length == 3 && parts[0].equals("mt") && (parts[2].equals("RQ") || parts[2].equals("RS"))) {
            title = parts[1] + parts[2];
        } else {
            title = nameCell;
        }
        return title;
    }
}
