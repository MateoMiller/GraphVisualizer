package Windows;

import Algorithms.Algorithm;
import Algorithms.AlgorithmStep;
import Infrastructure.Edge;
import Infrastructure.Graph;
import Infrastructure.Node;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class AlgorithmWindow extends JFrame {
    private final Graph graph;
    private AlgorithmStep solution;
    private final Algorithm algorithm;

    public AlgorithmWindow(Graph graph, Algorithm algorithm){
        this.graph = graph;
        this.algorithm = algorithm;

        JButton nextButton = new JButton(
                new AbstractAction("СЛЕД") {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        solution = solution.GetNext();
                        invalidate();
                    }
                }
        );
        JButton prevButton = new JButton(
                new AbstractAction("ПРЕД") {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        solution = solution.GetPrev();
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
        var graph = new Graph();
        graph.addNode("1");
        graph.addNode("2");

    }
}
