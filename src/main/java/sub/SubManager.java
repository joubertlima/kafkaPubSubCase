package sub;

import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import util.Constants;

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


        cofins.configure("COFINS_sub", Arrays.asList(Constants.cofinsTopic));
        icms.configure("ICMS_sub", Arrays.asList(Constants.icmsTopic));
        iptu.configure("IPTU_sub", Arrays.asList(Constants.iptuTopic));

        companies.configure("COMPANIES_sub", Arrays.asList(Constants.cofinsTopic, Constants.icmsTopic, Constants.iptuTopic));
        tributes.configure("TRIBUTES_sub", Arrays.asList(Constants.cofinsTopic, Constants.icmsTopic, Constants.iptuTopic));

        threadPool.submit(cofins);
        threadPool.submit(iptu);
        threadPool.submit(icms);
        threadPool.submit(companies);
        threadPool.submit(tributes);

        @SuppressWarnings("resource")
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
