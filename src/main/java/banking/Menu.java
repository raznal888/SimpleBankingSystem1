package banking;

import java.util.ArrayList;

class Menu {
    private ArrayList<MenuItem> items;

    Menu(UserInterface ui) {
        items = new ArrayList<>();
        items.add(new MenuItem("0", "Exit", ui::exit));
    }

    void executeCommand(String command) {
        for (MenuItem item: items) {
            if (item.getID().equals(command)) {
                item.getFunction().run();
            }
        }
    }

    void addItem(MenuItem item) {
        items.add(item);
    }

    @Override
    public String toString() {
        StringBuilder menu = new StringBuilder();

        for (int i = 1, n = items.size(); i < n; i++) {
            menu.append(items.get(i).toString());
            menu.append("\n");
        }

        menu.append(items.get(0).toString());

        return menu.toString();
    }
}
