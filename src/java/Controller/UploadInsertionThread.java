package Controller;

import DAO.UploadDao;
import Model.Route;
import java.time.Duration;
import java.time.Instant;
import java.util.TreeMap;

public class UploadInsertionThread extends Thread {

    TreeMap<Float, Route> routesNumbersAndDatesFromUploadedExcelFile;

    public UploadInsertionThread(TreeMap<Float, Route> routesNumbersAndDatesFromUploadedExcelFile) {
        System.out.println("Starting New (Upload) Thread");
        this.routesNumbersAndDatesFromUploadedExcelFile = routesNumbersAndDatesFromUploadedExcelFile;
    }

    @Override
    public void run() {
        Instant start = Instant.now();
        try {
            System.out.println("Upload Thread: Going for sleep for 1 seconds");
            Thread.sleep(
                    1L * 1000L);//first number is seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
         System.out.println("Upload Thread: Sleep Is Over");
        UploadDao uploadDao = new UploadDao();
        String deletionStatus = uploadDao.deleteLastUpload();
        System.out.println("Upload Thread: Last Upload Deletion Status:" + deletionStatus);

        String insertionStatus = uploadDao.insertNewUpload(this.routesNumbersAndDatesFromUploadedExcelFile);
        System.out.println("Upload Thread: New Upload Insertion Status:" + insertionStatus);
        Instant end = Instant.now();
        System.out.println("Upload Thread: Thread Ended. Running time :" + Duration.between(start, end));

    }

}
