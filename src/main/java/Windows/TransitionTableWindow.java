package Windows;

import Infrastructure.ObjectProvider;
import Infrastructure.StateMachine;
import Infrastructure.TransitionChar;
import TransitionTableStuff.MachineToTransitionTableConverter;
import javax.swing.*;
import java.awt.*;

public class TransitionTableWindow extends JDialog {
    private final ObjectProvider<StateMachine> machineProvider;
    private final MachineToTransitionTableConverter toTableConverter = new MachineToTransitionTableConverter();
    private TransitionCellsPanel cellsPanel;
    private final JTextField startStateField = new JTextField();
    private final JTextField finalStatesField = new JTextField();


    public TransitionTableWindow(ObjectProvider<StateMachine> machineProvider, JFrame parent){
        super(parent);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(Math.round(dim.width * 0.6f), Math.round(dim.height * 0.6f));
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

        this.machineProvider = machineProvider;

        var table = toTableConverter.convert(machineProvider.getObject());

        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        cellsPanel = new TransitionCellsPanel(table);
        add(cellsPanel);

        var startStatePanel = new JPanel(new GridLayout(1, 2));
        startStatePanel.add(new JLabel("Входное состояние"));
        startStatePanel.add(startStateField);
        add(startStatePanel);

        var finalStatesPanel = new JPanel(new GridLayout(1, 2));
        finalStatesPanel.add(new JLabel("Конечные состояния (через запятую)"));
        finalStatesPanel.add(finalStatesField);
        add(finalStatesPanel);

        var acceptButton = new JButton("Принять изменения");
        acceptButton.addActionListener(e -> acceptNewStateMachine());
        add(acceptButton);

    }

    private void acceptNewStateMachine(){
        try {
            var machine = cellsPanel.getStateMachine();
            var startState = startStateField.getText();
            if (!startState.isEmpty())
                machine.setStartNode(startState);

            var finalStates = finalStatesField.getText().split(",");
            for (var state: finalStates) {
                if (!state.isEmpty())
                    machine.addFinalNode(state);
            }

            machineProvider.setObject(machine);
            this.dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    public static void main(String[] args){
        var machine = new StateMachine();
        machine.addNode("a");
        machine.addNode("b");
        machine.addNode("c");
        machine.addTransition("a", "a", new TransitionChar('1'));
        machine.addTransition("b", "b", new TransitionChar('1'));
        machine.addTransition("c", "c", new TransitionChar('1'));
        machine.addTransition("a", "b", new TransitionChar('2'));
        machine.addTransition("b", "c", new TransitionChar('2'));
        machine.addTransition("c", "a", new TransitionChar('2'));
        machine.addTransition("a", "c", new TransitionChar('3'));
        machine.addTransition("b", "a", new TransitionChar('3'));
        machine.addTransition("c", "b", new TransitionChar('3'));
        var machineProvider = new ObjectProvider<>(machine);

        var window = new TransitionTableWindow(machineProvider, null);
        window.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        window.setVisible(true);
    }
}
