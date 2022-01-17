package Windows;

import StateMachineInfrastructure.StateMachine;
import StateMachineInfrastructure.TransitionChar;
import TransitionTableInfrasturcture.TransitionTable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransitionCellsPanel extends JPanel {
    private final Map<Point, JTextField> cells = new HashMap<>();
    private final List<JTextField> stateFields = new ArrayList<>();
    private final List<JTextField> alphabetFields = new ArrayList<>();
    private final JButton addStateButton;
    private final JButton addCharButton;

    public TransitionCellsPanel(TransitionTable table){
        var alphabet = table.getAlphabet().toArray(Character[]::new);
        var states = table.getNodes().toArray(String[]::new);

        addStateButton = new JButton("+");
        addStateButton.addActionListener(e -> addState());

        addCharButton = new JButton("+");
        addCharButton.addActionListener(e -> addChar());

        for (String state : states) {
            stateFields.add(new JTextField(state));
        }

        for (Character character : alphabet) {
            alphabetFields.add(new JTextField(character.toString()));
        }

        for (int x = 0; x < states.length; x++){
            for (int y = 0; y < alphabet.length; y++){
                var textField = new JTextField();
                var nextState = table.getNextCondition(states[x], alphabet[y]);
                var cellPosition = new Point(x, y);
                if (nextState != null)
                    textField.setText(nextState);
                cells.put(cellPosition, textField);
            }
        }

        updateLayout();
    }

    private void updateLayout(){
        var width = stateFields.size();
        var height = alphabetFields.size();

        removeAll();
        setLayout(new GridLayout(height + 3, width + 3));

        add(new JLabel());
        add(new JLabel());
        for (int x = 0; x < width; x++){
            var removeStateButton = new JButton("X");
            int iCopy = x;
            removeStateButton.addActionListener(e -> removeState(iCopy));
            add(removeStateButton);
        }
        add(new JLabel());

        add(new JLabel());
        add(new JLabel("Переход\\Состояние"));
        for (JTextField stateField : stateFields) {
            add(stateField);
        }
        add(addStateButton);

        for (int y = 0; y < height; y++){
            var removeCharButton = new JButton("X");
            int yCopy = y;
            removeCharButton.addActionListener(e -> removeChar(yCopy));

            add(removeCharButton);
            add(alphabetFields.get(y));
            for (int x = 0; x < width; x++){
                add(cells.get(new Point(x, y)));
            }
            add(new JLabel());
        }

        add(new JLabel());
        add(addCharButton);
        for (int x = 0; x < width + 1; x++){
            add(new JLabel());
        }

        validate();
    }

    private void removeState(int stateId) {
        stateFields.remove(stateId);

        for (int x = stateId; x < stateFields.size(); x++) {
            for (int y = 0; y < alphabetFields.size(); y++) {
                cells.replace(new Point(x, y), cells.get(new Point(x + 1, y)));
            }
        }
        for (int y = 0; y <= alphabetFields.size(); y++) {
            cells.remove(new Point(stateFields.size(), y));
        }

        updateLayout();
    }

    private void addState(){
        var newWidth = stateFields.size() + 1;
        var height = alphabetFields.size();
        stateFields.add(new JTextField());
        for (int y = 0; y < height; y++){
            var cellPosition = new Point(newWidth - 1, y);
            cells.put(cellPosition, new JTextField());
        }

        updateLayout();
    }

    private void removeChar(int charId){
        alphabetFields.remove(charId);

        for (int y = charId; y < alphabetFields.size(); y++) {
            for (int x = 0; x < stateFields.size(); x++) {
                cells.replace(new Point(x, y), cells.get(new Point(x, y + 1)));
            }
        }
        for (int x = 0; x <= stateFields.size(); x++) {
            cells.remove(new Point(x, alphabetFields.size()));
        }

        updateLayout();
    }

    private void addChar(){
        var newHeight = alphabetFields.size() + 1;
        var width = stateFields.size();
        alphabetFields.add(new JTextField());
        for (int x = 0; x < width; x++){
            var cellPosition = new Point(x, newHeight - 1);
            cells.put(cellPosition, new JTextField());
        }

        updateLayout();
    }

    public StateMachine getStateMachine(){
        var machine = new StateMachine();
        for (var stateField: stateFields) {
            machine.addNode(stateField.getText());
        }

        for(int y = 0; y < alphabetFields.size(); y++){
            var transitionChar = alphabetFields.get(y).getText();
            if (transitionChar.length() != 1)
                throw new RuntimeException("Не все символы алфавита состоят из 1-го символа");
            for (int x = 0; x < stateFields.size(); x++){
                var state = stateFields.get(x).getText();
                var nextCondition = cells.get(new Point(x, y)).getText();
                if (!nextCondition.isEmpty())
                    machine.addTransition(state, nextCondition, new TransitionChar(transitionChar.charAt(0)));
            }
        }

        return machine;

    }
}
