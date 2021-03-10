import pub.PubManager;
import sub.SubManager;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        new Main();
    }

    public Main(){

        PubManager pubM = new PubManager();
        //SubManager subM = new SubManager();

        ThreadWrapperPub tPub = new ThreadWrapperPub(pubM);
        tPub.start();
        System.out.println("Publish Manager started...");

        /*ThreadWrapperSub tSub = new ThreadWrapperSub(subM);
        tSub.start();
        System.out.println("Subscribe Manager started...");*/

        Scanner reader = new Scanner(System.in);

        System.out.println("Press any character to finish the application...");
        reader.next();

        pubM.stop();
        //subM.stop();

        try{
            //tSub.join();
            tPub.join();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    class ThreadWrapperPub extends Thread{
        PubManager pubM;
        public ThreadWrapperPub(PubManager pubM){
            this.pubM = pubM;
        }
        public void run(){
            pubM.begin();
        }
    }

    /*class ThreadWrapperSub extends Thread{
        SubManager subM;
        public ThreadWrapperSub(SubManager subM){
            this.subM = subM;
        }
        public void run(){
            subM.begin();
        }
    }*/

}
