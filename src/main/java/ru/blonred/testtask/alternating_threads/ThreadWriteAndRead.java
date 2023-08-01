package ru.blonred.testtask.alternating_threads;

class ThreadWriteAndRead implements Runnable {
    protected Thread thread;
    private final WriteAndRead writeAndRead;
    private final int targetNumber;

    public ThreadWriteAndRead(String name, int targetNumber, WriteAndRead writeAndRead) {
        thread = new Thread(this, name);
        this.targetNumber = targetNumber;
        this.writeAndRead = writeAndRead;
        thread.start();
    }

    public void run() {
        int currentValue;
        int oldValue;
        do {
            synchronized (writeAndRead) {
                int[] oldAndNewValue = writeAndRead.processForThread(targetNumber);
                oldValue = oldAndNewValue[0];
                currentValue = oldAndNewValue[1];
                if (checkConditionForEnd(currentValue, targetNumber)) break;
                printInfo(oldValue, currentValue);
                if (!notifyAndWait()) break;
            }
        } while (currentValue < targetNumber);
    }

    private boolean notifyAndWait(){
        writeAndRead.notifyAll();
        try {
            writeAndRead.wait();
        } catch (InterruptedException exc) {
            System.out.println(exc.getMessage());
            return false;
        }
        return true;
    }
    private boolean checkConditionForEnd(int currentValue, int targetNumber) {
        if (currentValue < 0 || currentValue > targetNumber) {
            writeAndRead.notifyAll();
            return true;
        }
        return false;
    }
    private void printInfo(int oldValue, int currentValue){
        System.out.printf("Old value: %d\nNew value: %d\n%s\n\n",
                oldValue, currentValue, Thread.currentThread().getName());
    }
}