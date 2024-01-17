package concurrency;

import java.util.concurrent.Exchanger;

public class SimpleExchanger {
    public static void main(String[] args) {
        Exchanger<String> exchanger = new Exchanger<>();
        new Thread(new UserText(exchanger)).start();
        new Thread(new MakeText(exchanger)).start();
    }
}

class MakeText implements Runnable {
    private Exchanger<String> exchanger;
    private String text = new String();

    MakeText(Exchanger<String> exchanger) { this.exchanger = exchanger; }

    public void run() {
        char ch = 'A';
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 5; j++) {
                text += ch++;
                try {
                    text = exchanger.exchange(text); // Exchange a full buffer for an empty one.
                } catch (InterruptedException e) {
                    System.out.println(e);
                }
            }
        }
    }
}
class UserText implements Runnable {
    Exchanger<String> exchanger;
    String text = new String();
    UserText(Exchanger<String> exchanger) { this.exchanger = exchanger; }
    public void run() {
        for(int i=0; i < 3; i++) {
            try {
                // Exchange an empty buffer for a full one.
                text = exchanger.exchange(new String());
                System.out.println("Got: " + text);
            } catch(InterruptedException exc) {
                System.out.println(exc);
            }
        }
    }
}
