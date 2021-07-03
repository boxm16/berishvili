/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.BasicRoute;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Michail Sitmalidis
 */
public class MemoryUsage {

    public void showMemoryUsage() {
        Runtime runtime = Runtime.getRuntime();
        long memoryMax = runtime.maxMemory();
        long memoryUsed = runtime.totalMemory() - runtime.freeMemory();
        double memoryUsedPercent = (memoryUsed * 100.0) / memoryMax;
        System.out.println();
        System.out.println("***** Heap utilization statistics [MB] *****");
        System.out.println("Used Memory:" + memoryUsed / (1024 * 1024) + "MB");
        System.out.println("Free Memory:" + runtime.freeMemory() / (1024 * 1024) + "MB");
        System.out.println("Total Memory:" + runtime.totalMemory() / (1024 * 1024) + "MB");
        System.out.println("Max Memory:" + memoryMax / (1024 * 1024) + "MB");
        System.out.println("MemoryUsedPercent: " + memoryUsedPercent + "%");
    }

    public void runTest(long routesQuantity) {
        System.out.println("Routes to be created:" + routesQuantity);
        Map<Integer, BasicRoute> map = new HashMap();
        Runtime rt = Runtime.getRuntime();
        long prevTotal = 0;
        long prevFree = rt.freeMemory();

        for (int i = 0; i < routesQuantity; i++) {
            long total = rt.totalMemory();
            long free = rt.freeMemory();
            if (total != prevTotal || free != prevFree) {
                long used = total - free;
                long prevUsed = (prevTotal - prevFree);
                System.out.println(
                        "#" + i
                        + ", Total: " + total / (1024 * 1024) + "MB"
                        + ", Used: " + used / (1024 * 1024) + "MB"
                        + ", Delta_Used: " + (used - prevUsed) / (1024 * 1024) + "MB"
                        + ", Free: " + free / (1024 * 1024) + "MB"
                        + ", Delta_Free: " + (free - prevFree) / (1024 * 1024) + "MB");
                prevTotal = total;
                prevFree = free;
            }
            map.put(i, new BasicRoute());
        }
    }

    void printMemoryUsage() {
        Runtime runtime = Runtime.getRuntime();
        long memoryMax = runtime.maxMemory();
        long memoryUsed = runtime.totalMemory() - runtime.freeMemory();
        double memoryUsedPercent = (memoryUsed * 100.0) / memoryMax;
        System.out.print("Used Memory:" + memoryUsed / (1024 * 1024) + "MB  #");
        System.out.print("Free Memory:" + runtime.freeMemory() / (1024 * 1024) + "MB  #");
        System.out.print("Total Memory:" + runtime.totalMemory() / (1024 * 1024) + "MB  #");
        System.out.print("Max Memory:" + memoryMax / (1024 * 1024) + "MB  #");
        System.out.print("Used %: " + Math.round(memoryUsedPercent) + "%");
        System.out.println("");
    }

}
