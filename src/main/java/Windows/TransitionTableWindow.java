package Windows;

import Infrastructure.StateMachine;

import javax.swing.*;
import java.util.List;

public class TransitionTableWindow extends JDialog {
    private final ObjectProvider<StateMachine> machineProvider;

    public TransitionTableWindow(ObjectProvider<StateMachine> machineProvider, JFrame parent){
        super(parent);
        this.machineProvider = machineProvider;
    }
}
