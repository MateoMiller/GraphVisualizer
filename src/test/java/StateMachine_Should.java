import StateMachineInfrastructure.StateMachine;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class StateMachine_Should {
    @Test
    public void getNodes_ReturnsEmpty_WhenNoNodesWasAdded() {
        StateMachine machine = new StateMachine();

        assertThat(machine.getNodes())
                .isEmpty();
    }

    @Test
    public void getNodes_ReturnsEmpty_WhenWasCleared() {
        StateMachine machine = new StateMachine();

        machine.addNode("1");
        machine.clear();

        assertThat(machine.getNodes())
                .isEmpty();
    }

    @Test
    public void addNode_ThrowWhenNodeWithSameNameAlreadyExists() {
        StateMachine machine = new StateMachine();

        machine.addNode("1");

        assertThrows(Exception.class, () -> machine.addNode("1"));
    }

    @Test
    public void removeNode_NotContainRemovedNode() {
        StateMachine actual = new StateMachine();
        StateMachine expected = new StateMachine();
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
        StateMachine actual = new StateMachine();
        StateMachine expected = new StateMachine();
        expected.addNode("1");

        actual.addNode("1");
        actual.removeNode("does not exists");

        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }
}
