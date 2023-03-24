package ru.blonred.testtask.alternatingThreads;

class ThreadWR implements Runnable {
    Thread thrd;
    WriteAndRead wrob;
    private final int count;

    public ThreadWR(String name, int n, WriteAndRead wr) {
        thrd = new Thread(this, name);
        count = n/2;
        wrob = wr;
        thrd.start();
    }


    public void run() {
        if (thrd.getName().compareTo("Thread #1") == 0) {
            for (int i = 0; i < count; i++) {
                wrob.threadOne(true);
            }
            wrob.threadOne(false);
        } else {
            for (int i = 0; i < count; i++) {
                wrob.threadTwo(true);
            }
            wrob.threadTwo(false);
        }
    }
}