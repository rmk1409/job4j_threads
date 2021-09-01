package thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget(url, speed));
        wget.start();
        wget.join();
    }

    @Override
    public void run() {
        var ONE_KB = 1024;
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("tmp_" + url.substring(url.lastIndexOf('/') + 1))) {
            int bytesRead;
            var dataBuffer = new byte[ONE_KB];
            var start = System.currentTimeMillis();
            while ((bytesRead = in.read(dataBuffer, 0, ONE_KB)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                var time = System.currentTimeMillis() - start;
                if (time < speed) {
                    Thread.sleep(speed - time);
                }
                start = System.currentTimeMillis();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }
}