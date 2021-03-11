package sub;

import util.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class SubManager {

    private ThreadPoolExecutor threadPool;

    public SubManager(){
        threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
        begin();
    }

    public void begin(){
        Subscriber cofins = new SubscriberCOFINS();
        Subscriber iptu = new SubscriberIPTU();
        Subscriber icms = new SubscriberICMS();
        Subscriber companies = new SubscriberCompanies();
        Subscriber tributes = new SubscriberTributes();


        cofins.configure("COFINS_sub",Constants.url, Arrays.asList(Constants.cofinsTopic));
        icms.configure("ICMS_sub",Constants.url, Arrays.asList(Constants.icmsTopic));
        iptu.configure("IPTU_sub",Constants.url, Arrays.asList(Constants.iptuTopic));

        companies.configure("COMPANIES_sub", Constants.url, Arrays.asList(Constants.cofinsTopic, Constants.icmsTopic, Constants.iptuTopic));
        tributes.configure("TRIBUTES_sub", Constants.url, Arrays.asList(Constants.cofinsTopic, Constants.icmsTopic, Constants.iptuTopic));

        threadPool.submit(cofins);
        threadPool.submit(iptu);
        threadPool.submit(icms);
        threadPool.submit(companies);
        threadPool.submit(tributes);

        Scanner reader = new Scanner(System.in);
        String resp = reader.next();
        if(resp.equals("exit")) {
            cofins.stop();
            icms.stop();
            iptu.stop();
            companies.stop();
            tributes.stop();
            threadPool.shutdown();
        }

    }

    public static void main(String[] args) {
        new SubManager();
    }
}
