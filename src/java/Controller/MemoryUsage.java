/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

/**
 *
 * @author Michail Sitmalidis
 */
public class MemoryUsage {

    public void showMemoryUsage() {
        Runtime runtime = Runtime.getRuntime();

        System.out.println("Used Memory:"
                + (runtime.totalMemory() - runtime.freeMemory()) / 1000000);

        System.out.println("Free Memory:"
                + runtime.freeMemory() / 1000000);

        System.out.println("Total Memory:" + runtime.totalMemory() / 1000000);

        System.out.println("Max Memory:" + runtime.maxMemory() / 1000000);
    }
}
