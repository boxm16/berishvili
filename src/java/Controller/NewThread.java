/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.lang.management.ManagementFactory;
import java.time.Duration;
import java.time.Instant;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Michail Sitmalidis
 */
public class NewThread extends Thread {

    MemoryUsage memoryUsage;
    long sleepTime = 3L;
    int count = 0;

    public NewThread(String sleepTime, String count) {
        memoryUsage = new MemoryUsage();
        this.sleepTime = Long.valueOf(sleepTime);
        this.count = Integer.valueOf(count);
    }

    public void run() {
        Instant start = Instant.now();
        System.out.println("New Thread: New Thread Started");

        try {
            Thread.sleep(this.sleepTime * 1000L);//first number is seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int index = 0;
        while (index < this.count) {
            if (index % 1000 == 0) {
                System.out.println(index);
                memoryUsage.printMemoryUsage();
                double averLoad = ManagementFactory.getPlatformMXBean(
                        com.sun.management.OperatingSystemMXBean.class).getSystemLoadAverage();
                System.out.println("Average Load:" + averLoad);
                if (averLoad > 1.0) {
                    System.out.println("TOO MUCH, GING TO SLEEP");
                    try {
                        Thread.sleep(10 * 1000L);//first number is seconds
                    } catch (InterruptedException ex) {
                        Logger.getLogger(NewThread.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                double cpuLoad = ManagementFactory.getPlatformMXBean(
                        com.sun.management.OperatingSystemMXBean.class).getSystemCpuLoad();
                System.out.println("CPU Load:" + cpuLoad);
            }
            index++;
        }
        Instant end = Instant.now();
        System.out.println("New Thread: Thread Ended. Running time :" + Duration.between(start, end));

    }
}
