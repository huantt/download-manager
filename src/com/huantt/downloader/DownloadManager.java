package com.huantt.downloader;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Huan on 7/6/2016.
 */
public class DownloadManager implements Runnable {
    private static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(Downloader.class);

    int numOfThread;
    private String link;
    private String path;
    private String fileName;
    private static ExecutorService threadPool = Executors.newCachedThreadPool();

    public DownloadManager(int numOfThread, String link, String path, String fileName) {
        this.numOfThread = numOfThread;
        this.link = link;
        this.path = path;
        this.fileName = fileName + getFileExtension();
    }

    public DownloadManager(int numOfThread, String link, String path) {
        this.numOfThread = numOfThread;
        this.link = link;
        this.path = path;
        this.fileName = getFileName();
    }

    private String getFileName() {
        return new String(link.substring(link.lastIndexOf('/') + 1));
    }

    private String getFileExtension() {
        String format = "";
        if (link.lastIndexOf(".") != -1) {
            format = link.substring(link.lastIndexOf("."));
        }
        return format;
    }

    @Override
    public void run() {
        URL url = null;
        try {
            url = new URL(link);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            File file = new File(path + "/" + fileName);

            int sizeFile = connection.getContentLength();
            int partSize = sizeFile / numOfThread;
            int start = 0;
            int end = partSize;
            for (int i = 0; i < numOfThread - 1; i++) {
                log.info("Data of Thread Download " + (i + 1) + ": " + start + "==========>" + end);
                threadPool.submit(new Downloader(link, path, file, start, end,i+1));
                start = end;
                end += partSize;
            }
            end = sizeFile;
            log.info("Data of Thread Download " + numOfThread + ": " + start + "==========>" + end);
            threadPool.submit(new Downloader(link, path, file, start, end,numOfThread));
            threadPool.shutdown();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
