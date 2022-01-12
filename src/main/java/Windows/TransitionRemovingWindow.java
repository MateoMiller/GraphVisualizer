package Windows;

import Infrastructure.StateMachine;
import Infrastructure.TransitionChar;

import javax.swing.*;
import java.awt.*;

public class TransitionRemovingWindow extends JDialog {
    StateMachine machine;
    JTextField firstNodeTextField;
    JTextField secondNodeTextField;
    JTextField transitionCharTextField;

    public TransitionRemovingWindow(StateMachine machine, JFrame parent){
        super(parent);
        this.machine = machine;
        this.setTitle("Удалить переход");
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(Math.round(dim.width * 0.5f), Math.round(dim.height * 0.5f));
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

        var cp = getContentPane();
        var layout = new BoxLayout(cp, BoxLayout.Y_AXIS);

        JLabel firstNodeLabel = new JLabel("Имя первой вершины: ");
        firstNodeTextField = new JTextField();
        JLabel secondNodeLabel = new JLabel("Имя второй вершины: ");
        secondNodeTextField = new JTextField();
        JLabel transitionCharLabel = new JLabel("Символ перехода: ");
        transitionCharTextField = new JTextField();

        var lambdaButton = new JButton("λ");
        lambdaButton.addActionListener(e -> transitionCharTextField.setText("λ"));

        var fields = new JPanel(new GridLayout(3, 3));
        fields.add(firstNodeLabel);
        fields.add(firstNodeTextField);
        fields.add(new JLabel());
        fields.add(secondNodeLabel);
        fields.add(secondNodeTextField);
        fields.add(new JLabel());
        fields.add(transitionCharLabel);
        fields.add(transitionCharTextField);
        fields.add(lambdaButton);

        JButton applyButton = new JButton("Удалить переход");
        applyButton.addActionListener(e -> onApplyButton());

        add(fields, BorderLayout.CENTER);
        add(applyButton, BorderLayout.PAGE_END);
    }

    private void onApplyButton(){
        try {
            var firstNodeName = firstNodeTextField.getText();
            var secondNodeName = secondNodeTextField.getText();
            var transition = transitionCharTextField.getText();
            if (firstNodeName.isEmpty() || secondNodeName.isEmpty())
                throw new Exception("Имя вершины не должно быть пустым");
            if (transition.length() != 1)
                throw new Exception("Символ перехода должен быть длины 1");

            var transitionChar = new TransitionChar(transition.charAt(0));

            machine.removeTransition(firstNodeName, secondNodeName, transitionChar);
            this.dispose();
        }catch (Exception e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    public static void main(String[] args){
        var machine = new StateMachine();
        machine.addNode("first");
        machine.addNode("second");
        machine.addTransition("first", "second", new TransitionChar('a'));
        var window = new TransitionRemovingWindow(machine, null);
        window.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        window.setVisible(true);
    }
}
