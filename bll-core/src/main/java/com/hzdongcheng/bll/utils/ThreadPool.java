package com.hzdongcheng.bll.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {
    private static ExecutorService executorService = Executors.newSingleThreadExecutor();

    public static void QueueUserWorkItem(Runnable work) {
        executorService.submit(work);
    }
}
