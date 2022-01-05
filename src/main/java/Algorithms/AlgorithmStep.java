package Algorithms;

public abstract class AlgorithmStep {
    private AlgorithmStep next, prev;

    public AlgorithmStep GetNext(){
        return next;
    }

    public void SetNext(AlgorithmStep step){
        next = step;
    }

    public AlgorithmStep GetPrev(){
        return prev;
    }

    public void SetPrev(AlgorithmStep step){
        prev = step;
    }
}
