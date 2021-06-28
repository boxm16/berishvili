package Controller;

import DAO.UploadDao;
import Model.Route;
import java.time.Duration;
import java.time.Instant;
import java.util.TreeMap;

public class UploadInsertionThread extends Thread {

    TreeMap<Float, Route> routesNumbersAndDatesFromUploadedExcelFile;

    public UploadInsertionThread(TreeMap<Float, Route> routesNumbersAndDatesFromUploadedExcelFile) {
        this.routesNumbersAndDatesFromUploadedExcelFile = routesNumbersAndDatesFromUploadedExcelFile;
    }

    @Override
    public void run() {
        Instant start = Instant.now();
        UploadDao uploadDao = new UploadDao();
        String deletionStatus = uploadDao.deleteLastUpload();
        System.out.println("Last Upload Deletion Status:" + deletionStatus);
        /*
        try {
            Thread.sleep(3L * 1000L);//first number is seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
         */
        String insertionStatus = uploadDao.insertNewUpload(this.routesNumbersAndDatesFromUploadedExcelFile);
        System.out.println("New Upload Insertion Status:" + insertionStatus);
        Instant end = Instant.now();
        System.out.println("Upload Insertion Thread running time :" + Duration.between(start, end));

    }

}
