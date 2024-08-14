import Interface.MainWindow;

import javax.swing.*;

public class Main {
    public static void main(String[] args) throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        startup();
    }

    public static void startup() throws UnsupportedLookAndFeelException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        MainWindow mainWindow = new MainWindow();
    }
}
