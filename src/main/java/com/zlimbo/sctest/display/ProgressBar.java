package com.zlimbo.sctest.display;


public class ProgressBar {
    
    private char leftBoundChar = '[';
    private char rightBoundChar = ']';
    private char unfinisedChar = '=';
    private char finishedChar = '█';
    private int barLength = 42;

    private String msg = "";
    private int leftBound = 0;
    private int rightBound = 0;
    private int current = 0;
    private boolean finised = false;

    long startTime;

    public ProgressBar(int leftBound, int rightBound, String msg) {
        this.msg = msg;
        this.leftBound = leftBound;
        this.rightBound = rightBound;
        startTime = System.nanoTime();
        int num = (rightBound - leftBound) + 1;
    }

    public ProgressBar(int rightBound, String msg) {
        this(0, rightBound, msg);
    }

    synchronized public void reset() {
        current = 0;
        finised = false;
    }

    synchronized public void teminate() {
        finised = true;
        draw();
    }

    synchronized public void step(int current) {
        if (finised || current < this.current || current > rightBound) {
            return;
        }
        if (current == rightBound) {
            finised = true;
        }
        this.current = current;
        draw();
    }

    public void draw() {
        double percentage = 0.0;
        if (current - leftBound > 0) {
            percentage = (double)(current - leftBound) / (rightBound - leftBound);
        }
        int mid = (int)Math.round(percentage * barLength);
        StringBuilder sb = new StringBuilder();
        sb.append('\r');
        sb.append(leftBoundChar);
        for (int i = 0; i < mid; ++i) {
            sb.append(finishedChar);
        }
        for (int i = mid; i < barLength; ++i) {
            sb.append(unfinisedChar);
        }
        sb.append(rightBoundChar);
        double take = (System.nanoTime() - startTime) / 1e9;
        double tps = current / take;
        sb.append(String.format(" (%d/%d) (%.2f%%) (take:%.2fs) (tps:%.2f) (%s)",
                current, rightBound, percentage * 100, take, tps, msg));
        if (finised) {
            sb.append("\n");
        } else {
            sb.append("\t\t\t");
        }
        System.out.print(sb);
        System.out.flush();
    }

    /**
     * 测试进度条
     * @param args
     * @throws InterruptedException
     */
    static public void main(String[] args) throws InterruptedException {

        int n = 1000;
        ProgressBar progressBar = new ProgressBar(0, n, "insert");
        for (int i = 0; i <= 1000; ++i) {
            progressBar.step(i);
            Thread.sleep(5000 / n);
        }
    }
}



