package ru.vlasov.dto;

import ru.vlasov.entity.Element;
import ru.vlasov.entity.ElementType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import ru.vlasov.entity.Type;

import java.util.List;

public class Scheme {
    private int level;

    public Scheme() {
        this.level = 1;
    }

    public String buildJson(List<Element> elements) {
        JsonObject schema = new JsonObject();
        JsonObject properties = new JsonObject();



        elements.forEach(element -> {
            if (element.getElementType().equals(ElementType.HEADER)) {
                schema.addProperty("$schema", "http://json-schema.org/draft-04/schema#");
                schema.addProperty("title", element.getName());
                schema.addProperty("type", "object");
                nextLevel();
            } else if (element.getElementType().equals(ElementType.FIELD) && level == element.getLevel()) {
                properties.add(element.getName(), createSimpleObject(element));
            } else if (element.getElementType().equals(ElementType.OBJECT)) {

            }
        });
        schema.add("properties", properties);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(schema);
    }

    private JsonObject createSimpleObject(Element element) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("description", element.getDescription());

        if (element.getType().equals(Type.STRING) || element.getType().equals(Type.INTEGER)) {
            jsonObject.addProperty("type", element.getType().getValue());
        } else if (element.getType().equals(Type.LONG)) {
            jsonObject.addProperty("type", Type.INTEGER.getValue());
            jsonObject.addProperty("format", "int64");
        } else {
            throw new RuntimeException("Unknowing type when convert from Entity to Gson");
        }

        return jsonObject;
    }

    private void nextLevel() {
        level++;
    }

    private void previousLevel() {
        level--;
    }
}
