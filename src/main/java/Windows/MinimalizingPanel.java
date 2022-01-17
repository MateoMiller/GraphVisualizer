package Windows;

import Algorithms.AlgorithmStep;
import Algorithms.Minimalizing.MinimalizingGroup;
import Algorithms.Minimalizing.MinimalizingStep;
import TransitionTableStuff.TransitionTable;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Random;

public class MinimalizingPanel extends AlgorithmPanel {
    private final TransitionTable table;

    public MinimalizingPanel(TransitionTable table) {
        this.table = table;
    }

    @Override
    public void visualizeStep(AlgorithmStep step) {
        removeAll();

        MinimalizingStep minimalizingStep = (MinimalizingStep) step;
        var groups = minimalizingStep.groups;

        var alphabet = table.getAlphabet();

        setLayout(new GridLayout(
                alphabet.size() + 2,
                calculateTotalNodesCount(minimalizingStep) + 1));


        add(new JLabel());
        for (var group : groups) {
            for (var state: group.states) {
                add(new JLabel(state));
            }
        }

        add(new JLabel());
        for (var group : groups) {
            for (var state: group.states) {
                add(new JLabel(group.groupName));
            }
        }

        for (var symbol : alphabet){
            add(new JLabel(Character.toString(symbol)));
            for(var group: groups){
                for (var state: group.states) {
                    var nextPosition = table.getNextCondition(state, symbol);
                    var stateGroup = getGroup(groups, nextPosition);
                    add(new JLabel(stateGroup.groupName));
                }
            }
        }
    }

    private MinimalizingGroup getGroup(List<MinimalizingGroup> groups, String state){
        for (var group: groups){
            if (group.states.contains(state))
                return group;
        }

        throw new RuntimeException("Данное состояние не содержится ни в одной группе");
    }

    private int calculateTotalNodesCount(MinimalizingStep step){
        var size = 0;
        for (var group: step.groups)
            size += group.states.size();
        return size;
    }
}
