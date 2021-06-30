/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.time.Duration;
import java.time.Instant;

/**
 *
 * @author Michail Sitmalidis
 */
public class NewThread extends Thread {

    long sleepTime = 3L;
    int count=0;

    public NewThread(String sleepTime, String count) {
        this.sleepTime = Long.valueOf(sleepTime);
        this.count=Integer.valueOf(count);
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
            System.out.println(index);
            index++;
        }
        Instant end = Instant.now();
        System.out.println("New Thread: Thread Ended. Running time :" + Duration.between(start, end));

    }
}
