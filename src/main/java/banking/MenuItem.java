package banking;

class MenuItem {
    private String ID;
    private String description;
    private Runnable function;

    MenuItem(String ID, String name, Runnable function) {
        this.ID = ID;
        this.description = name;
        this.function = function;
    }

    String getID() {
        return ID;
    }

    String getDescription() {
        return description;
    }

    Runnable getFunction() {
        return function;
    }

    @Override
    public String toString() {
        return ID + ". " + description;
    }
}
