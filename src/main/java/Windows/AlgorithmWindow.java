package Windows;

import Algorithms.Algorithm;
import Algorithms.AlgorithmResult;
import Algorithms.AlgorithmStep;
import StateMachineInfrastructure.StateMachine;

import javax.swing.*;
import java.awt.*;

public class AlgorithmWindow extends JDialog {
    private final StateMachine stateMachine;
    private final Algorithm algorithm;
    private final AlgorithmResult solution;
    private int currentStepId;
    private final AlgorithmPanel algorithmPanel;
    private final JButton prevButton;
    private final JButton nextButton;



    public AlgorithmWindow(JFrame parent, StateMachine stateMachine, Algorithm algorithm, AlgorithmPanel algorithmPanel){
        super(parent);
        this.stateMachine = stateMachine;
        this.algorithm = algorithm;
        this.algorithmPanel = algorithmPanel;
        solution = algorithm.solve();
        currentStepId = 0;

        setLayout(new GridLayout(2, 1));

        nextButton = new JButton("СЛЕД");
        nextButton.addActionListener(e -> moveNext());
        nextButton.setEnabled(solution.steps.size() != 1);

        prevButton = new JButton("ПРЕД");
        prevButton.addActionListener(e -> moveBack());
        prevButton.setEnabled(false);

        var buttonsPanel = new JPanel();
        buttonsPanel.add(prevButton);
        buttonsPanel.add(nextButton);
        algorithmPanel.visualizeStep(getCurrentStep());

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(Math.round(dim.width * 0.75f), Math.round(dim.height * 0.75f));
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

        add(algorithmPanel);
        add(buttonsPanel);
    }

    private void moveNext(){
        currentStepId++;
        prevButton.setEnabled(currentStepId != 0);
        nextButton.setEnabled(currentStepId + 1 != solution.steps.size());

        algorithmPanel.visualizeStep(getCurrentStep());
        revalidate();
    }

    private void moveBack(){
        currentStepId--;
        prevButton.setEnabled(currentStepId != 0);
        nextButton.setEnabled(currentStepId + 1 != solution.steps.size());

        algorithmPanel.visualizeStep(getCurrentStep());
        revalidate();
    }

    private AlgorithmStep getCurrentStep(){
        return solution.steps.get(currentStepId);
    }

    public static void main(String[] args){
        var graph = new StateMachine();
        graph.addNode("1");
        graph.addNode("2");

    }
}
