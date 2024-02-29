package ru.vlasov.dto;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.vlasov.entity.Element;

public class Scheme {

    public String generate(Element element) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(element);
    }
}
