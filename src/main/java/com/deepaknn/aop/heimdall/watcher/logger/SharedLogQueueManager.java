package com.deepaknn.aop.heimdall.watcher.logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class SharedLogQueueManager {

    @Value("${log.queue.size:1000}")
    private long size;

    private final ConcurrentLinkedQueue<LogDetails> queue = new ConcurrentLinkedQueue<>();

    public void enqueue(LogDetails logDetails){
        queue.offer(logDetails);
        while(queue.size() > this.size) {
            queue.poll();
        }
    }

    public void dequeue() {
        queue.poll();
    }

    public LogDetails peek(){
        return queue.peek();
    }

    public boolean isEmpty(){
        return queue.isEmpty();
    }
}
