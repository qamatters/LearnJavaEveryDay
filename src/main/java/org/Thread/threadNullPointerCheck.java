package org.Thread;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class threadNullPointerCheck {
    public static void main(String[] args) throws InterruptedException {
        ArrayList<String> urls = new ArrayList<>();

        urls.add("https://www.mfs.com/en-us/individual-investor/product-strategies/mutual-funds/MUSEX-mfs-blended-research-core-equity-fund.html");
        urls.add("https://www.mfs.com/en-us/individual-investor/product-strategies/mutual-funds/BMSLX-mfs-blended-research-mid-cap-equity-fund.html");
        urls.add("https://www.mfs.com/en-us/individual-investor/product-strategies/mutual-funds/BRSJX-mfs-blended-research-small-cap-equity-fund.html");
        urls.add("https://www.mfs.com/en-us/individual-investor/product-strategies/mutual-funds/BRUHX-mfs-blended-research-value-equity-fund.html");
        urls.add("https://www.mfs.com/en-us/individual-investor/product-strategies/mutual-funds/MRGRX-mfs-core-equity-fund.html");
        urls.add("https://www.mfs.com/en-us/individual-investor/product-strategies/mutual-funds/HYPPX.html");

        ArrayList<Runnable> tasks = new ArrayList<>();
        CountDownLatch countDownLatch = new CountDownLatch(urls.size());

        for (String url : urls) {
            tasks.add(() -> {
                System.out.println(" Executing Task for " + url);
                try {
                    getActualDataFromFundPageAsAlIst(url);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                countDownLatch.countDown();
            });
        }

        ExecutorService executor = Executors.newFixedThreadPool(tasks.size());

        Instant start = Instant.now();
        for (Runnable task : tasks) {
            executor.execute(task);
        }
        countDownLatch.await();
        executor.shutdown();

        System.out.println("All task executed");
        Instant end = Instant.now();
        Duration timeElapsed = Duration.between(start, end);

        System.out.println("------------------------------------------------------------------------------------------");
        System.out.println("Total time in scrapping with thread concept: " + timeElapsed.getSeconds()+ " seconds");
        System.out.println("------------------------------------------------------------------------------------------");
    }

    static List<String> getActualDataFromFundPageAsAlIst(String url) throws IOException {
        String performanceTabURL = url + "#tab-performance/";
        List<String> values = new ArrayList<>();

        System.out.println("----------------------------------------------------");
        System.out.println("Fund link is: " + url);
        System.out.println("----------------------------------------------------");
        Connection.Response response = null;
        try {
            response = Jsoup.connect(performanceTabURL).maxBodySize(0).execute();
        } catch (IOException e) {
            System.out.println("Exception occurred during fetching data  " + e.getMessage());
        }

        if (response.statusCode() == 200) {
            values = getDataFromResponse(response);
        } else {
            System.out.println("status code for url "  + url + "is :" + response.statusCode());
        }
        return values;
    }

    private static List<String> getDataFromResponse(Connection.Response response) throws IOException {
        List<String> al = null;
        Document doc = response.parse();
        Elements productDetailPerformanceTab = doc.getElementsByClass("product-detail__performance-tab js-performance-tab js-xx-collapse-handle");
        if(productDetailPerformanceTab.size() ==0) {
            System.out.println("The response UI Data is not having class product-detail__performance-tab js-performance-tab js-xx-collapse-handle which holds chart data" +
                    "check that the data is present in UI or not");
        } else {
            String alldata = String.valueOf(productDetailPerformanceTab.get(0));
            String value = StringUtils.substringBetween(alldata, "data-graphdata", "\">");
            String[] allArrays = StringUtils.substringsBetween(value, "data&quot;:", "}");
            al = Arrays.asList(allArrays);
            System.out.println("----------------------------------------------------");
            if (al.size() >= 3) {
                System.out.println(al.get(2));
            }
            if (al.size() >= 4) {
                System.out.println(al.get(3));
            }
            if (al.size() == 2) {
                System.out.println(al.get(1));
            }
        }
        return al;
    }
}
