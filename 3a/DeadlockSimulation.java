import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

public class DeadlockSimulation {

    private static final Object resource1 = new Object();
    private static final Object resource2 = new Object();

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            synchronized (resource1) {
                System.out.println("Thread 1 acquired resource 1");
                try {
                    Thread.sleep(1000); // Simulate some work
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (resource2) {
                    System.out.println("Thread 1 acquired resource 2");
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            synchronized (resource2) {
                System.out.println("Thread 2 acquired resource 2");
                try {
                    Thread.sleep(1000); // Simulate some work
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (resource1) {
                    System.out.println("Thread 2 acquired resource 1");
                }
            }
        });

        thread1.start();
        thread2.start();

        // Monitor for deadlock
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        while (true) {
            long[] deadlockedThreadIds = threadMXBean.findDeadlockedThreads();
            if (deadlockedThreadIds != null && deadlockedThreadIds.length > 0) {
                System.out.println("Deadlock detected!");
                ThreadInfo[] threadInfos = threadMXBean.getThreadInfo(deadlockedThreadIds, true, true);
                for (ThreadInfo threadInfo : threadInfos) {
                    System.out.println(threadInfo.getThreadName() + " is deadlocked");
                }
                System.exit(1); // Terminate the program
            }
            try {
                Thread.sleep(1000); // Check for deadlock every second
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}