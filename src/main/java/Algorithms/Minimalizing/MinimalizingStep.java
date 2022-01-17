package Algorithms.Minimalizing;

import Algorithms.AlgorithmStep;

import java.util.List;

public class MinimalizingStep implements AlgorithmStep {
    public List<MinimalizingGroup> groups;

    public MinimalizingStep(List<MinimalizingGroup> groups){
        this.groups = groups;
    }
}
