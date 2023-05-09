package org.Thread;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.Thread.threadNullPointerCheck.getActualDataFromFundPageAsAlIst;

public class ThreadCodeImprovement {
    public static void main(String[] args) {
        ArrayList<String> urls = new ArrayList<>();

        urls.add("https://www.mfs.com/en-us/individual-investor/product-strategies/mutual-funds/MUSEX-mfs-blended-research-core-equity-fund.html");
        urls.add("https://www.mfs.com/en-us/individual-investor/product-strategies/mutual-funds/BMSLX-mfs-blended-research-mid-cap-equity-fund.html");
        urls.add("https://www.mfs.com/en-us/individual-investor/product-strategies/mutual-funds/BRSJX-mfs-blended-research-small-cap-equity-fund.html");
        urls.add("https://www.mfs.com/en-us/individual-investor/product-strategies/mutual-funds/BRUHX-mfs-blended-research-value-equity-fund.html");
        urls.add("https://www.mfs.com/en-us/individual-investor/product-strategies/mutual-funds/MRGRX-mfs-core-equity-fund.html");
        urls.add("https://www.mfs.com/en-us/individual-investor/product-strategies/mutual-funds/HYPPX.html");

        ExecutorService executor = Executors.newFixedThreadPool(urls.size());
        Instant start = Instant.now();
        for (String url : urls) {
            executor.execute(() -> {
                System.out.println(" Executing Task for " + url);
                try {
                    getActualDataFromFundPageAsAlIst(url);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        System.out.println("All task executed");
        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start, end);

        System.out.println("------------------------------------------------------------------------------------------");
        System.out.println("Total time in scrapping with thread concept:: : "    + timeElapsed.getSeconds() + " seconds" );
        System.out.println("------------------------------------------------------------------------------------------");
    }
}
