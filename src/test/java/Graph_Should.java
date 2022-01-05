import Infrastructure.Graph;
import Infrastructure.Node;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class Graph_Should {
    @Test
    public void getNodes_ReturnsEmpty_WhenNoNodesWasAdded() {
        Graph g = new Graph();

        assertThat(g.getNodes())
                .isEmpty();
    }

    @Test
    public void getNodes_ReturnsEmpty_WhenWasCleared() {
        Graph g = new Graph();

        g.addNode("1");
        g.clear();

        assertThat(g.getNodes())
                .isEmpty();
    }

    @Test
    public void addNode_ThrowWhenNodeWithSameNameAlreadyExists() {
        Graph g = new Graph();

        g.addNode("1");

        assertThrows(Exception.class, () -> g.addNode("1"));
    }

    @Test
    public void removeNode_NotContainRemovedNode() {
        Graph actual = new Graph();
        Graph expected = new Graph();
        expected.addNode("1");
        expected.addNode("3");

        actual.addNode("1");
        actual.addNode("2");
        actual.addNode("3");
        actual.removeNode("2");

        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    public void removeNode_NotChangeGraphWhenNodeWithGivenNameDoesNotExists() {
        Graph actual = new Graph();
        Graph expected = new Graph();
        expected.addNode("1");

        actual.addNode("1");
        actual.removeNode("does not exists");

        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }
}
