package com.huantt.downloader;

import org.apache.log4j.xml.DOMConfigurator;

/**
 * Created by Huan on 7/6/2016.
 */
public class Main {
    public static void main(String[] args) {
        DOMConfigurator.configureAndWatch(".\\config\\log4j.xml");
        DownloadManager downloadManager = new DownloadManager(5,"http://widewallpaper.info/wp-content/uploads/2016/01/DOCK-SKY-LAKE-4K-WALLPAPER.jpg"
                ,"D:/");
        Thread thread = new Thread(downloadManager);
        thread.start();
    }
}
