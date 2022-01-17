package Algorithms.Minimalizing;

import Algorithms.Algorithm;
import Algorithms.AlgorithmResult;
import Algorithms.AlgorithmStep;
import Infrastructure.StateMachine;
import Infrastructure.TransitionChar;
import TransitionTableStuff.MachineToTransitionTableConverter;
import TransitionTableStuff.TransitionTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MinimalizingAlgorithm implements Algorithm {
    private final StateMachine machine;
    private final MachineToTransitionTableConverter toTransitionTableConverter = new MachineToTransitionTableConverter();

    public MinimalizingAlgorithm(StateMachine machine) {
        this.machine = machine;
    }

    @Override
    public AlgorithmResult solve() {
        TransitionTable table = toTransitionTableConverter.convert(machine);
        List<AlgorithmStep> steps = new ArrayList<>();
        var currentSeparation = separateOnFinalAndNonFinalNodes();

        while (currentSeparation != null){
            var step = new MinimalizingStep(currentSeparation);
            steps.add(step);
            currentSeparation = findNewSeparation(currentSeparation, table);
        }

        var result = new AlgorithmResult();
        result.steps = steps;

        return result;
    }

    private List<MinimalizingGroup> separateOnFinalAndNonFinalNodes(){
        var finalNodes = machine
                .getFinalNodes()
                .stream()
                .map(x -> x.name)
                .collect(Collectors.toList());

        var nonFinalNodes = machine.getNodes()
                .stream()
                .map(x -> x.name)
                .filter(x -> !finalNodes.contains(x))
                .collect(Collectors.toList());

        var groups = new ArrayList<MinimalizingGroup>();

        addNewGroup(groups, nonFinalNodes);
        addNewGroup(groups, finalNodes);
        return groups;
    }

    private List<MinimalizingGroup> findNewSeparation(List<MinimalizingGroup> groups, TransitionTable table){
        var alphabet = table.getAlphabet();

        for (var group: groups) {
            Map<List<MinimalizingGroup>, List<String>> newPositionsToState = new HashMap<>();
            for (var state: group.states) {
                var newPositions = new ArrayList<MinimalizingGroup>();
                for (var symbol : alphabet) {
                    var nextPosition = table.getNextCondition(state, symbol);
                    newPositions.add(getMinimalizingGroup(groups, nextPosition));
                }
                if (newPositionsToState.containsKey(newPositions))
                    newPositionsToState.get(newPositions).add(state);
                else
                    newPositionsToState.put(newPositions, new ArrayList<>(List.of(state)));
            }

            if (newPositionsToState.size() != 1){
                var newGroups = new ArrayList<MinimalizingGroup>();
                for(var group1: groups){
                    if (group1 != group)
                        addNewGroup(newGroups, group1.states);
                }

                for (var states : newPositionsToState.values()) {
                    addNewGroup(newGroups, states);
                }
                return newGroups;
            }
        }

        return null;
    }

    private void addNewGroup(List<MinimalizingGroup> currentGroups, List<String> states){
        var newGroupName =
                "(" + String.join("," ,states) + ")";

        currentGroups.add(new MinimalizingGroup(states, newGroupName));
    }

    private MinimalizingGroup getMinimalizingGroup(List<MinimalizingGroup> groups, String state){
        for (var group: groups){
            if (group.states.contains(state))
                return group;
        }

        throw new RuntimeException("Что-то пошло не так");
    }

    public static void main(String[] args){
        var machine = new StateMachine();
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

        var algorithm = new MinimalizingAlgorithm(machine);
        var result = algorithm.solve();
    }
}
