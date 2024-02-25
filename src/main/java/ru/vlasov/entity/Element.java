package ru.vlasov.entity;

public class Element {
    private int level;
    private ElementType elementType;
    private String name;
    private Type type;
    private Repetition repetition;
    private String description;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Repetition getRepetition() {
        return repetition;
    }

    public void setRepetition(Repetition repetition) {
        this.repetition = repetition;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ElementType getElementType() {
        return elementType;
    }

    public void setElementType(ElementType elementType) {
        this.elementType = elementType;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Element{" +
                "level=" + level +
                ", elementType=" + elementType +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", repetition=" + repetition +
                ", description='" + description + '\'' +
                '}';
    }
}
