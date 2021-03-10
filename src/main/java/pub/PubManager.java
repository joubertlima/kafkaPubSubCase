package pub;

import util.Constants;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class PubManager {

    private ThreadPoolExecutor threadPool;
    private int uniqueID;
    private boolean flag;

    public PubManager(){
        uniqueID =0;
        flag = false;
        threadPool = (ThreadPoolExecutor) Executors.newCachedThreadPool();
    }

    public void begin(){
        Random r = new Random();

        while (!flag){
            int seed = r.nextInt(Constants.publishers.length);
            int seedTopic = r.nextInt(Constants.topics.length);
            uniqueID++;

            try {
                Publisher pub = (Publisher) Constants.publishers[seed].newInstance();
                pub.configure(Constants.pubNames[seed], Constants.url, Constants.topics[seedTopic], uniqueID);
                threadPool.submit(pub);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        threadPool.shutdown();
    }

    public void stop(){
        flag = true;
    }

}
