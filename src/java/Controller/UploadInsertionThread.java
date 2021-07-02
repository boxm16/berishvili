package Controller;

import java.time.Instant;

public class UploadInsertionThread extends Thread {

    Instant instant = Instant.now();

    public UploadInsertionThread() {
        System.out.println("Starting New (Upload) Thread");
    }

    @Override
    public void run() {
        String instantName = instant.toString();
        int index = 0;
        while (index < 1000) {
            try {
                System.out.println("++++++Upload Thread: Instant" + instantName + ": Going for sleep for 60 seconds");
                Thread.sleep(
                        60L * 1000L);//first number is seconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("------Upload Thread: Instant" + instantName + ": woke Up. Count:" + index);
            index++;
        }

    }
}
