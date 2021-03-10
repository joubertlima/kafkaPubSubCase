package sub;

import util.Constants;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class SubManager {

    private ThreadPoolExecutor threadPool;

    public SubManager(){
        threadPool = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        begin();
    }

    public void begin(){
        Subscriber cofins = new SubscriberCOFINS();
        Subscriber iptu = new SubscriberIPTU();
        Subscriber icms = new SubscriberICMS();
        Subscriber companies = new SubscriberCompanies();
        Subscriber tributes = new SubscriberTributes();

        Collection<String> cofinsTopic = new ArrayList<>();
        cofinsTopic.add(Constants.cofinsTopic);

        Collection<String> iptuTopic = new ArrayList<>();
        iptuTopic.add(Constants.iptuTopic);

        Collection<String> icmsTopic = new ArrayList<>();
        icmsTopic.add(Constants.icmsTopic);

        Collection<String> allTopics = new ArrayList<>();
        allTopics.add(Constants.cofinsTopic);
        allTopics.add(Constants.icmsTopic);
        allTopics.add(Constants.iptuTopic);

        cofins.configure("COFINS_sub",Constants.url, cofinsTopic);
        icms.configure("ICMS_sub",Constants.url, icmsTopic);
        iptu.configure("IPTU_sub",Constants.url, iptuTopic);

        companies.configure("COMPANIES_sub", Constants.url, allTopics);
        tributes.configure("TRIBUTES_sub", Constants.url, allTopics);

        threadPool.submit(cofins);
        threadPool.submit(iptu);
        threadPool.submit(icms);
        threadPool.submit(companies);
        threadPool.submit(tributes);

        Scanner reader = new Scanner(System.in);
        System.out.println("Press any character to finish all subscribers....");

        cofins.stop();
        icms.stop();
        iptu.stop();
        companies.stop();
        tributes.stop();
        threadPool.shutdown();

    }

    public static void main(String[] args) {
        new SubManager();
    }
}
