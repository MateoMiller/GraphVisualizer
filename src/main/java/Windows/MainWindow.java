package Windows;

import Algorithms.Minimalizing.MinimalizingAlgorithm;
import StateMachineInfrastructure.ObjectProvider;
import StateMachineInfrastructure.StateMachine;
import StateMachineInfrastructure.TransitionChar;
import TransitionTableInfrasturcture.MachineToTransitionTableConverter;
import Windows.GraphicalInfrastucture.ConstantPalette;
import Windows.GraphicalInfrastucture.MachineVisualizer;
import Windows.OperationWindows.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MainWindow extends JFrame {
    private final MachineVisualizer visualizer;
    private final ObjectProvider<StateMachine> machineProvider;
    private final MachineToTransitionTableConverter toTransitionTableConverter;
    private final ImageIcon imageIcon;

    public MainWindow() {
        visualizer = new MachineVisualizer(new ConstantPalette());
        toTransitionTableConverter = new MachineToTransitionTableConverter();
        var machine = new StateMachine();
        machineProvider = new ObjectProvider<>(machine);

        machine.addNode("1");
        machine.addNode("2");
        machine.addNode("3");
        machine.addNode("4");
        machine.addNode("5");
        machine.addNode("6");
        machine.addTransition("1", "6", new TransitionChar('a'));
        machine.addTransition("1", "6", new TransitionChar('b'));
        machine.addTransition("2", "2", new TransitionChar('a'));
        machine.addTransition("2", "4", new TransitionChar('b'));
        machine.addTransition("3", "5", new TransitionChar('a'));
        machine.addTransition("3", "6", new TransitionChar('b'));
        machine.addTransition("4", "1", new TransitionChar('a'));
        machine.addTransition("4", "3", new TransitionChar('b'));
        machine.addTransition("5", "5", new TransitionChar('a'));
        machine.addTransition("5", "5", new TransitionChar('b'));
        machine.addTransition("6", "5", new TransitionChar('a'));
        machine.addTransition("6", "5", new TransitionChar('b'));

        machine.setStartNode("2");

        machine.addFinalNode("1");
        machine.addFinalNode("3");

        imageIcon = new ImageIcon();
        add(new JLabel(imageIcon));

        JMenuBar menu = new JMenuBar();
        InitMachineEditFunctions(menu);
        InitOperations(menu);
        InitAlgorithms(menu);
        this.setJMenuBar(menu);

        //?????????? ???????????????????? ???????????????? 75% ???????????? ?? ???????? ?? ???????????????? ????????????
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(Math.round(dim.width * 0.75f), Math.round(dim.height * 0.75f));
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

        repaint();
    }

    private void InitMachineEditFunctions(JMenuBar menu) {
        JMenu editMenu = new JMenu("??????????????????????????");

        JMenuItem setGraphFromTransitionTableItem = new JMenuItem("?????????????????????????? ?????????????? ??????????????????");
        setGraphFromTransitionTableItem.addActionListener(e -> editTransitionTable());

        editMenu.add(setGraphFromTransitionTableItem);
        menu.add(editMenu);
    }

    private void editTransitionTable() {
        var window = new TransitionTableWindow(machineProvider, this);
        openChildWindow(window);
    }

    private void InitOperations(JMenuBar menu) {
        JMenu operationsMenu = new JMenu("????????????????");

        JMenuItem addNodeItem = new JMenuItem("???????????????? ??????????????");
        addNodeItem.addActionListener(e -> addNode());

        JMenuItem removeNodeItem = new JMenuItem("?????????????? ??????????????");
        removeNodeItem.addActionListener(e -> removeNode());

        JMenuItem addTransitionItem = new JMenuItem("???????????????? ??????????????");
        addTransitionItem.addActionListener(e -> addTransition());

        JMenuItem removeEdgeItem = new JMenuItem("?????????????? ??????????????") ;
        removeEdgeItem.addActionListener(e -> removeTransition());

        JMenuItem setStartNodeItem = new JMenuItem("???????????????????? ?????????????????? ??????????????");
        setStartNodeItem.addActionListener(e -> setStartNode());

        JMenuItem addFinalNodeItem = new JMenuItem("???????????????? ???????????????? ??????????????????");
        addFinalNodeItem.addActionListener(e -> addFinalNode());

        JMenuItem removeFinalNodeItem = new JMenuItem("?????????????? ???????????????? ??????????????????");
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
        JMenu algorithmMenu = new JMenu("????????????????");

        JMenuItem determiningItem = new JMenuItem("?????????????????????????? ?? ??????");
        determiningItem.addActionListener(e -> determine());

        JMenuItem minimalMachineItem = new JMenuItem("?????????????????????????? ?? ????????????????????????");
        minimalMachineItem.addActionListener(e -> minimalize());


        //algorithmMenu.add(determiningItem);
        algorithmMenu.add(minimalMachineItem);

        menu.add(algorithmMenu);
    }

    private void minimalize() {
        try {
            var machine = machineProvider.getObject();
            var transitionTable = toTransitionTableConverter.convert(machine);
            var algorithm = new MinimalizingAlgorithm(machine);
            var algorithmPanel = new MinimalizingPanel(transitionTable);
            var window = new AlgorithmWindow(this, machine, algorithm, algorithmPanel);
            openChildWindow(window);
        }catch (Exception e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void determine() {
        System.out.println("????????????????????????");
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
