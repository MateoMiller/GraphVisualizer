import Windows.MainWindow;


import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class EntryPoint {

    public static void main(String[] args){
        var frame = new MainWindow();
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
