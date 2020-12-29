package banking;

class MenuItem {
    private final String ID;
    private final String description;
    private final Runnable function;

    MenuItem(String ID, String name, Runnable function) {
        this.ID = ID;
        this.description = name;
        this.function = function;
    }

    String getID() {
        return ID;
    }

    Runnable getFunction() {
        return function;
    }

    @Override
    public String toString() {
        return ID + ". " + description;
    }


}
