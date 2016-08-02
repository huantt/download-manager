package com.huantt.downloader;

import org.apache.log4j.xml.DOMConfigurator;

/**
 * Created by Huan on 7/6/2016.
 */
public class Main {
    public static void main(String[] args) {
        DOMConfigurator.configureAndWatch(".\\config\\log4j.xml");
        DownloadManager downloadManager = new DownloadManager(1,""
                ,"D:/");
        Thread thread = new Thread(downloadManager);
        thread.start();
    }
}
