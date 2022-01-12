package Windows;

import Infrastructure.StateMachine;
import Infrastructure.TransitionChar;
import Windows.GraphicalStuff.ConstantPalette;
import Windows.GraphicalStuff.MachineVisualizer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MainWindow extends JFrame {
    private final MachineVisualizer visualizer;
    private final ObjectProvider<StateMachine> machineProvider;
    private final ImageIcon imageIcon;

    public MainWindow() {
        visualizer = new MachineVisualizer(new ConstantPalette());
        var machine = new StateMachine();
        machineProvider = new ObjectProvider<>(machine);

        machine.addNode("a");
        machine.addNode("b");
        machine.addNode("c");
        machine.addTransition("a", "a", new TransitionChar());
        machine.addTransition("a", "b", new TransitionChar());
        machine.addTransition("a", "b", new TransitionChar('1'));
        machine.addTransition("a", "c", new TransitionChar());
        machine.addTransition("b", "a", new TransitionChar());
        machine.addTransition("b", "b", new TransitionChar());
        machine.addTransition("b", "c", new TransitionChar());
        machine.addTransition("b", "c", new TransitionChar('1'));
        machine.addTransition("b", "c", new TransitionChar('2'));
        machine.addTransition("c", "a", new TransitionChar());
        machine.addTransition("c", "b", new TransitionChar());
        machine.addTransition("c", "b", new TransitionChar('1'));
        machine.addTransition("c", "b", new TransitionChar('2'));
        machine.addTransition("c", "c", new TransitionChar());
        imageIcon = new ImageIcon();
        add(new JLabel(imageIcon));

        JMenuBar menu = new JMenuBar();
        InitGraphSettingFunctions(menu);
        InitOperations(menu);
        InitAlgorithms(menu);
        this.setJMenuBar(menu);

        //Чтобы приложение занимало 75% экрана и было в середине экрана
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(Math.round(dim.width * 0.75f), Math.round(dim.height * 0.75f));
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

        repaint();
    }

    private void InitGraphSettingFunctions(JMenuBar menu) {
        JMenu settingsMenu = new JMenu("Таблицы переходов");

        JMenuItem setGraphFromTransitionTableItem = new JMenuItem("Задать автомат таблицей переходов");
        setGraphFromTransitionTableItem.addActionListener(e -> setGraphFromTransitionTable());

        JMenuItem generateTransitionTableItem = new JMenuItem("Преобразовать текущий автомат к таблице переходов");
        generateTransitionTableItem.addActionListener(e -> generateTransitionTable());

        settingsMenu.add(setGraphFromTransitionTableItem);
        settingsMenu.add(generateTransitionTableItem);

        menu.add(settingsMenu);
    }

    private void generateTransitionTable() {
        System.out.println("Создаю таблицу переходов");
    }

    private void setGraphFromTransitionTable() {
        System.out.println("Создаю автомат по таблице переходов");
    }

    private void InitOperations(JMenuBar menu) {
        JMenu operationsMenu = new JMenu("Операция");

        JMenuItem addNodeItem = new JMenuItem("Добавить вершину");
        addNodeItem.addActionListener(e -> addNode());

        JMenuItem removeNodeItem = new JMenuItem("Удалить вершину");
        removeNodeItem.addActionListener(e -> removeNode());

        JMenuItem addTransitionItem = new JMenuItem("Добавить переход");
        addTransitionItem.addActionListener(e -> addTransition());

        JMenuItem removeEdgeItem = new JMenuItem("Удалить переход") ;
        removeEdgeItem.addActionListener(e -> removeTransition());

        JMenuItem setStartNodeItem = new JMenuItem("Установить стартовую вершину");
        setStartNodeItem.addActionListener(e -> setStartNode());

        JMenuItem addFinalNodeItem = new JMenuItem("Добавить конечное состояние");
        addFinalNodeItem.addActionListener(e -> addFinalNode());

        JMenuItem removeFinalNodeItem = new JMenuItem("Удалить конечное состояние");
        removeFinalNodeItem.addActionListener(e -> removeFinalNode());

        operationsMenu.add(addNodeItem);
        operationsMenu.add(removeNodeItem);
        operationsMenu.add(addTransitionItem);
        operationsMenu.add(removeEdgeItem);
        operationsMenu.add(setStartNodeItem);
        operationsMenu.add(addFinalNodeItem);
        operationsMenu.add(removeFinalNodeItem);

        menu.add(operationsMenu);
    }

    private void InitAlgorithms(JMenuBar menu) {
        JMenu algorithmMenu = new JMenu("Алгоритм");

        JMenuItem determiningItem = new JMenuItem("Преобразовать в ДКА");
        determiningItem.addActionListener(e -> determine());

        JMenuItem minimalMachineItem = new JMenuItem("Преобразовать к приведенному");
        minimalMachineItem.addActionListener(e -> minimalize());


        algorithmMenu.add(determiningItem);
        algorithmMenu.add(minimalMachineItem);

        menu.add(algorithmMenu);
    }

    private void minimalize() {
        System.out.println("Минимализирую");
    }

    private void determine() {
        System.out.println("Детерминирую");
    }

    private void addNode(){
        var window = new NodeAddingWindow(machineProvider.getObject(), this);
        openChildWindow(window);
    }

    private void addTransition(){
        var window = new TransitionAddingWindow(machineProvider.getObject(), this);
        openChildWindow(window);
    }

    private void setStartNode(){
        var window = new SelectingStartNodeWindow(machineProvider.getObject(), this);
        openChildWindow(window);
    }

    private void addFinalNode(){
        var window = new FinalNodeAddingWindow(machineProvider.getObject(), this);
        openChildWindow(window);
    }

    private void removeFinalNode(){
        var window = new FinalNodeRemovingWindow(machineProvider.getObject(), this);
        openChildWindow(window);
    }

    private void removeNode(){
        var window = new NodeRemovingWindow(machineProvider.getObject(), this);
        openChildWindow(window);
    }

    private void removeTransition(){
        var window = new TransitionRemovingWindow(machineProvider.getObject(), this);
        openChildWindow(window);
    }

    private void openChildWindow(JDialog window){
        window.setModal(true);
        window.setVisible(true);
        repaint();
    }

    @Override
    public void repaint() {
        super.repaint();
        var image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        var imageGraphics = (Graphics2D) image.getGraphics();
        visualizer.Visualize(machineProvider.getObject(), imageGraphics, getWidth(), getHeight());
        imageIcon.setImage(image);
    }

    public static void main(String[] args){
        var frame = new MainWindow();
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
