package pl.wysockif.optimizer.algorithms;

import pl.wysockif.optimizer.structures.graph.Graph;

import java.util.List;

public interface MaxFlowAlgorithm {
    public List<Deal> loadResults(Graph finalGraph);
    public Graph createGraph();
}
