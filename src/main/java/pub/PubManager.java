package pub;

import util.Constants;

import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class PubManager {

    private ThreadPoolExecutor threadPool;
    private int uniqueID;
    private boolean flag;

    public PubManager(){
        uniqueID =0;
        flag = false;
        threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
        begin();
    }

    public void begin(){
        Random r = new Random();
        Scanner reader = new Scanner(System.in);

        int count = 0;

        while (!flag){
            int seed = r.nextInt(Constants.publishers.length);
            uniqueID++;

            try {
                Publisher pub = (Publisher) Constants.publishers[seed].newInstance();
                pub.configure(Constants.pubNames[seed], Constants.url, Constants.topics[seed], uniqueID);
                threadPool.submit(pub);
            } catch (Exception e) {
                e.printStackTrace();
            }

            count++;

            if(count==Constants.numPubMessages) {
                System.out.println("Submit another publisher? (Y|N)");
                String resp = reader.next();
                if(resp.equals("N")||resp.equals("n")) flag = true;
                else count = 0;
            }

        }

        threadPool.shutdown();
    }

    public void stop(){
        flag = true;
    }

    public static void main(String[] args) {
        new PubManager();
    }

}
