package Windows.OperationWindows;

import StateMachineInfrastructure.StateMachine;
import StateMachineInfrastructure.TransitionChar;

import javax.swing.*;
import java.awt.*;

public class FinalNodeRemovingWindow extends JDialog {
    StateMachine machine;
    JTextField nameTextField;

    public FinalNodeRemovingWindow(StateMachine machine, JFrame parent){
        super(parent);
        this.machine = machine;
        this.setTitle("Удалить конечное состояние");
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(Math.round(dim.width * 0.5f), Math.round(dim.height * 0.5f));
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

        var cp = getContentPane();
        var layout = new BoxLayout(cp, BoxLayout.Y_AXIS);

        JLabel nameLabel = new JLabel("Имя вершины: ");
        nameTextField = new JTextField(15);
        var fields = new JPanel(new GridLayout(1, 2));
        fields.add(nameLabel);
        fields.add(nameTextField);

        JButton applyButton = new JButton("Удалить");
        applyButton.addActionListener(e -> onApplyButton());

        add(fields, BorderLayout.CENTER);
        add(applyButton, BorderLayout.PAGE_END);
    }

    private void onApplyButton(){
        try {
            var name = nameTextField.getText();
            if (name.isEmpty())
                throw new Exception("Имя вершины не должно быть пустым");
            machine.removeFinalNode(name);
            this.dispose();
        }catch (Exception e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    public static void main(String[] args){
        var machine = new StateMachine();
        machine.addNode("first");
        machine.addNode("second");
        machine.addTransition("first", "second", new TransitionChar( 'a'));
        var window = new FinalNodeRemovingWindow(machine, null);
        window.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        window.setVisible(true);
    }
}
