package pl.wysockif.optimizer.algorithms;

import pl.wysockif.optimizer.structures.queue.BlockingQueueFIFO;

public class EdmondsKarpAlgorithm {
    private BlockingQueueFIFO<Integer> queue;

    public EdmondsKarpAlgorithm(){
        queue = new BlockingQueueFIFO<>();
    }
}
