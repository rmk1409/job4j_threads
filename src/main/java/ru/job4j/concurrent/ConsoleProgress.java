package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(1000); /* симулируем выполнение параллельной задачи в течение 1 секунды. */
        progress.interrupt();
    }

    @Override
    public void run() {
        char[] process = {'\\', '|', '/'};
        int i = 0;
        while (!Thread.currentThread().isInterrupted()) {
            try {
                System.out.print("\r load: " + process[i++]);
                i = i < process.length ? i : 0;
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
