package com.huantt.downloader

def input = new Scanner(System.in)
println "Link download: "
def urlDownload = input.next()
println "File save location: "
def folderPath = input.next()
println "Number of concurrent threads: "
def numOfThreads = input.nextInt()
DownloadManager downloadManager = new DownloadManager(numOfThreads, urlDownload, folderPath)
Thread thread = new Thread(downloadManager)
thread.start()