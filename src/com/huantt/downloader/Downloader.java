package com.huantt.downloader;

import org.apache.log4j.Logger;
import org.dangnh.xmlconfig.ConfigFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Huan on 7/5/2016.
 */
public class Downloader implements Runnable {
    private static Logger log = Logger.getLogger(Downloader.class);

    private String link;
    private String path;
    private File file;
    private int startPosition;
    private int endPosition;
    private int numOfThread;

    public Downloader(String link, String path, File file, int startPosition, int endPosition, int numOfThrad) {
        this.link = link;
        this.path = path;
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.file = file;
        this.numOfThread = numOfThrad;
    }


    @Override
    public void run() {
        URL url;
        try {
            url = new URL(link);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            String downloadPartSize = startPosition + "-" + endPosition;
            connection.setRequestProperty("Range", "bytes=" + downloadPartSize);
            connection.connect();

            BufferedInputStream dataReader = new BufferedInputStream(connection.getInputStream());
            RandomAccessFile dataWriter = new RandomAccessFile(file, "rw");

            dataWriter.seek(startPosition);
            byte[] bytes = new byte[1024];
            int downloadedSize = 0;
            int totalSize = endPosition - startPosition;
            try {
                while (downloadedSize <= totalSize) {
                    int length = dataReader.read(bytes);
                    if (length != -1) {
                        dataWriter.write(bytes, 0, length);
                    } else break;
                    downloadedSize += length;
                    log.info("Thread: " + Thread.currentThread().getName() + "Start position: " + startPosition + ". End position: " + endPosition + "Downloaded: " + downloadedSize);
                }
                dataWriter.close();
                log.info("Download Thread " + Thread.currentThread().getName() + " Finished !");
                log.info("Downloaded : " + downloadedSize);
            } catch (MalformedURLException e) {
                log.error("URL Form do not correct", e);
            } catch (IOException e) {
                log.error(e.toString());
            }
        } catch (IOException e) {
            log.error(e.toString());
        }
    }


}