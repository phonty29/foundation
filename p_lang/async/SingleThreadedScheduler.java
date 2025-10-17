package p_lang.async;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.PriorityQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class SingleThreadedScheduler {
    private final PriorityQueue<ScheduledTask> taskQueue = new PriorityQueue<>();
    private final AtomicBoolean running = new AtomicBoolean(false);
    
    // NO SYNCHRONIZATION NEEDED - single thread only!
    public void schedule(Runnable task, long delayMs) {
        long executeTime = System.currentTimeMillis() + delayMs;
        taskQueue.offer(new ScheduledTask(task, executeTime));
    }
    
    public void start() {
        if (!running.compareAndSet(false, true)) {
            throw new IllegalStateException("Scheduler already running");
        }
        
        System.out.println("Starting pure single-threaded scheduler");
        printThreadInfo();
        
        // MAIN EVENT LOOP - ONLY THREAD
        while (running.get()) {
            long currentTime = System.currentTimeMillis();
            
            // Check if any tasks are ready to execute
            // NO SYNCHRONIZATION - single thread owns the queue
            ScheduledTask nextTask = taskQueue.peek();
            
            if (nextTask == null) {
                // No tasks - sleep briefly to avoid busy-wait
                sleep(100);
                continue;
            }
            
            long delay = nextTask.executeTime - currentTime;
            if (delay > 0) {
                // Task not ready yet - sleep until it's ready or new task arrives
                sleep(Math.min(delay, 100));
                continue;
            }
            
            // Execute the task
            taskQueue.poll(); // Remove from queue
            try {
                nextTask.task.run();
            } catch (Exception e) {
                System.err.println("Task execution failed: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        System.out.println("Scheduler stopped");
    }
    
    private void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            running.set(false);
        }
    }
    
    public void stop() {
        running.set(false);
    }
    
    public int getPendingTaskCount() {
        return taskQueue.size(); // No sync needed - single thread
    }
    
    private void printThreadInfo() {
        ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
        System.out.println("Total JVM threads: " + threadBean.getThreadCount());
        System.out.println("Current thread: " + Thread.currentThread().getName());
    }
    
    private static class ScheduledTask implements Comparable<ScheduledTask> {
        final Runnable task;
        final long executeTime;
        
        ScheduledTask(Runnable task, long executeTime) {
            this.task = task;
            this.executeTime = executeTime;
        }
        
        @Override
        public int compareTo(ScheduledTask other) {
            return Long.compare(this.executeTime, other.executeTime);
        }
    }
    
    public static void main(String[] args) {
        SingleThreadedScheduler scheduler = new SingleThreadedScheduler();
        
        // Schedule tasks from main thread BEFORE starting scheduler
        scheduler.schedule(() -> System.out.println("Task 1 at " + System.currentTimeMillis()), 1000);
        scheduler.schedule(() -> System.out.println("Task 2 at " + System.currentTimeMillis()), 2000);
        scheduler.schedule(() -> System.out.println("Task 3 at " + System.currentTimeMillis()), 1500);
        
        // Schedule stop
        scheduler.schedule(() -> {
            System.out.println("Stopping scheduler. Pending tasks: " + scheduler.getPendingTaskCount());
            scheduler.stop();
        }, 5000);
        
        // Start the scheduler (this will block)
        scheduler.start();
    }
}