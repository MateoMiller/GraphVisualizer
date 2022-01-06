package Windows;

import Algorithms.Algorithm;
import Algorithms.AlgorithmStep;
import Infrastructure.StateMachine;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class AlgorithmWindow extends JFrame {
    private final StateMachine stateMachine;
    private final Algorithm algorithm;

    public AlgorithmWindow(StateMachine stateMachine, Algorithm algorithm){
        this.stateMachine = stateMachine;
        this.algorithm = algorithm;

        JButton nextButton = new JButton(
                new AbstractAction("СЛЕД") {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        invalidate();
                    }
                }
        );
        JButton prevButton = new JButton(
                new AbstractAction("ПРЕД") {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        invalidate();
                    }
                });
    }

    @Override
    public void invalidate() {
        System.out.println("HELLO");
        super.invalidate();
    }

    public static void main(String[] args){
        var graph = new StateMachine();
        graph.addNode("1");
        graph.addNode("2");

    }
}
