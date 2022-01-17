package Windows.GraphicalInfrastucture;

import StateMachineInfrastructure.Edge;
import StateMachineInfrastructure.Node;
import StateMachineInfrastructure.StateMachine;
import StateMachineInfrastructure.TransitionChar;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.awt.*;

public class MachineVisualizer {
    private final int stateRadius = 50;
    private final int arrowWidth = 12;
    private final int curvedArrowHeight = 50;
    private final Font font = new Font("arial", Font.BOLD, 20);
    private final NodesLayouter layouter = new NodesLayouter();
    private final Palette palette;

    public MachineVisualizer(Palette palette) {
        this.palette = palette;
    }

    public void Visualize(StateMachine machine, Graphics2D g, int width, int height) {
        layouter.init(machine, width, height);
        g.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );
        g.setFont(font);

        g.setColor(Color.white);
        g.fillRect(0, 0, width, height);

        var edges = machine.getEdges();
        var nodes = machine.getNodes();
        var startNode = machine.getStartNode();
        var finalNodes = machine.getFinalNodes();

        visualizeEdges(edges, g);
        if (startNode != null)
            visualizeStartNodeEntryEdge(machine.getStartNode(), g);
        visualizeFinalNodeExitEdges(finalNodes, g);
        visualizeNodes(nodes, g);
    }

    private void visualizeStartNodeEntryEdge(Node startNode, Graphics2D g) {
        var nodePosition = layouter.getPosition(startNode);
        var positionOnLeft = new Point(nodePosition.x - 100, nodePosition.y);
        var arrow = new VisualArrow(positionOnLeft.x, positionOnLeft.y, 100, arrowWidth, 0, null);
        var color = palette.getStartNodeEntryColor();
        var polygon = arrow.getPolygon();

        g.setColor(color);
        g.fill(polygon);

        g.setColor(Color.BLACK);
        g.draw(polygon);
    }

    private void visualizeFinalNodeExitEdges(List<Node> finalNodes, Graphics2D g) {
        for (var finalNode : finalNodes) {
            var nodePosition = layouter.getPosition(finalNode);
            var arrow = new VisualArrow(nodePosition.x, nodePosition.y, 100, arrowWidth, 45, null);
            var color = palette.getFinalNodeExitColor();
            var polygon = arrow.getPolygon();

            g.setColor(color);
            g.fill(polygon);

            g.setColor(Color.BLACK);
            g.draw(polygon);
        }
    }

    private void visualizeNodes(List<Node> nodes, Graphics2D g) {
        for (Node node : nodes) {
            visualizeOneNode(node, g);
        }
    }

    private void visualizeOneNode(Node node, Graphics2D g) {
        var position = layouter.getPosition(node);
        var color = palette.getNodeColor(node);
        g.setColor(color);
        g.fillOval(position.x - stateRadius / 2, position.y - stateRadius / 2, stateRadius, stateRadius);
        drawNodeName(node, position, g);
    }

    private void visualizeEdges(List<Edge> edges, Graphics2D g) {
        var pairedEdges = new ArrayList<Pair<Edge, Edge>>();
        var nonPairedEdges = new ArrayList<Edge>();
        var loopEdges = new ArrayList<Edge>();
        var pairedIds = new HashSet<Integer>();
        for (int i = 0; i < edges.size(); i++) {
            var edgeI = edges.get(i);
            if (edgeI.to.equals(edgeI.from)) {
                loopEdges.add(edgeI);
            }

            for (int j = i + 1; j < edges.size(); j++) {
                var edgeJ = edges.get(j);
                if (edgeI.from == edgeJ.to && edgeI.to == edgeJ.from) {
                    var pair = new ImmutablePair<>(edgeI, edgeJ);
                    pairedEdges.add(pair);
                    pairedIds.add(i);
                    pairedIds.add(j);
                }
            }
            if (!pairedIds.contains(i))
                nonPairedEdges.add(edgeI);
        }

        var arrows = new ArrayList<Arrow>();
        arrows.addAll(getUnpairedArrows(nonPairedEdges));
        arrows.addAll(getPairedArrows(pairedEdges));
        arrows.addAll(getLoopArrows(loopEdges));

        for (var arrow : arrows)
            drawArrow(arrow, g);

        for (var arrow : arrows)
            drawTransitionChars(arrow, g);
    }

    private void drawArrow(Arrow arrow, Graphics2D g) {
        var polygon = arrow.getPolygon();
        var edge = arrow.getParentEdge();

        g.setColor(palette.getEdgeColor(edge));
        g.fill(polygon);

        g.setColor(palette.getEdgeStrokeColor(edge));
        g.draw(polygon);

        drawTransitionChars(arrow, g);
    }

    private void drawTransitionChars(Arrow arrow, Graphics2D g) {
        var edge = arrow.getParentEdge();

        var text = arrow.getTransitionChars();
        var position = arrow.getCenterPosition();

        drawText(text, arrow.getTransitionCharsAngle(), palette.getTransitionCharColor(edge), position, g);
    }

    private List<Arrow> getUnpairedArrows(List<Edge> edges) {
        var result = new ArrayList<Arrow>();
        for (var edge : edges) {
            var fromPosition = layouter.getPosition(edge.from);
            var toPosition = layouter.getPosition(edge.to);
            var dx = fromPosition.x - toPosition.x;
            var dy = fromPosition.y - toPosition.y;
            var length = Math.sqrt(dx * dx + dy * dy);
            var angle = Math.atan2(-dy, -dx);
            var arrow = new VisualArrow(fromPosition.x, fromPosition.y, (int) length, arrowWidth, angle, edge);
            result.add(arrow);
        }
        return result;
    }

    private List<Arrow> getLoopArrows(List<Edge> edges) {
        var result = new ArrayList<Arrow>();
        for (var edge : edges) {
            var position = layouter.getPosition(edge.from);
            var radius = 100;
            var arrow = new LoopArrow(position.x, position.y, radius, arrowWidth, edge);
            result.add(arrow);
        }
        return result;
    }

    private List<Arrow> getPairedArrows(List<Pair<Edge, Edge>> pairs) {
        var result = new ArrayList<Arrow>();
        for (var pair : pairs) {
            var firstEdge = pair.getLeft();
            var firstNode = firstEdge.from;
            var secondEdge = pair.getRight();
            var secondNode = secondEdge.from;
            var firstPosition = layouter.getPosition(firstNode);
            var secondPosition = layouter.getPosition(secondNode);
            var dx = firstPosition.x - secondPosition.x;
            var dy = firstPosition.y - secondPosition.y;
            var angle = Math.atan2(dy, dx);
            var arrowLength = (int) Math.sqrt(dx * dx + dy * dy);
            var firstArrow = new CurvedArrow(
                    firstPosition.x, firstPosition.y, arrowLength, arrowWidth, curvedArrowHeight, Math.PI + angle, firstEdge);
            var secondArrow = new CurvedArrow(
                    secondPosition.x, secondPosition.y, arrowLength, arrowWidth, curvedArrowHeight, angle, secondEdge);

            result.add(firstArrow);
            result.add(secondArrow);
        }
        return result;
    }


    private void drawText(String text, double angle, Color textColor, Point position, Graphics2D g) {
        if (Math.cos(angle) <= 0)
            angle += Math.PI;

        //Доделать поворот на бок

        AffineTransform transform = g.getTransform();
        var width = g.getFontMetrics().stringWidth(text);
        transform.translate(position.x - width / 2, position.y);
        g.transform(transform);
        g.setColor(Color.black);
        FontRenderContext frc = g.getFontRenderContext();
        TextLayout tl = new TextLayout(text, font, frc);
        Shape shape = tl.getOutline(null);
        g.setStroke(new BasicStroke(3f));
        g.draw(shape);
        g.setColor(textColor);
        g.fill(shape);
        transform.setToRotation(0);
        g.translate(-(position.x - width / 2), -position.y);
    }


    private void drawNodeName(Node node, Point position, Graphics2D g) {
        drawText(node.name, 0, palette.getNodeNameColor(node), position, g);
        drawText(node.name, 0, palette.getNodeNameColor(node), position, g);
    }

    public static void main(String[] args) {
        var width = 500;
        var height = 500;
        var machine = new StateMachine();
        machine.addNode("first");
        machine.addNode("second");
        machine.addNode("third");
        machine.addNode("fourth");
        machine.addNode("fifth");
        machine.setStartNode("first");
        machine.addFinalNode("second");
        machine.addFinalNode("third");

        machine.addTransition("first", "first", new TransitionChar('a'));

        machine.addTransition("first", "second", new TransitionChar());
        machine.addTransition("first", "second", new TransitionChar('a'));
        machine.addTransition("first", "second", new TransitionChar('b'));
        machine.addTransition("first", "second", new TransitionChar('c'));
        machine.addTransition("second", "third", new TransitionChar('b'));
        machine.addTransition("third", "first", new TransitionChar('c'));
        machine.addTransition("first", "third", new TransitionChar('c'));
        machine.addTransition("first", "fourth", new TransitionChar('d'));
        machine.addTransition("fourth", "first", new TransitionChar('e'));
        var visualizer = new MachineVisualizer(new ConstantPalette());
        var image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = (Graphics2D) image.getGraphics();
        visualizer.Visualize(machine, g, width, height);
        var frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(width + 100, height + 100);
        ImageIcon c = new ImageIcon();
        c.setImage(image);
        frame.add(new JLabel(c));
        frame.addMouseListener(
                new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        System.out.println(e.getPoint());
                    }
                }
        );
        frame.setVisible(true);
    }
}
