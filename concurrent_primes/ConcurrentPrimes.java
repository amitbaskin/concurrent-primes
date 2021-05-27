package concurrent_primes;

import java.util.concurrent.*;
import static java.lang.System.exit;

/**
 * A program for finding all primes up to some number using several threads simultaneously
 */
public class ConcurrentPrimes {
    static final int DONE = -1;
    static final String TOO_LONG_MSG = "Calculation took too long...";
    static final String PRIMES_FORMATTED_MSG = "The primes up to %d that were calculated:\n";
    static final String PRIMES_UP_TO_NUM_ERR =
            "The number to get the primes up to must be greater than or equal to 1. Terminating program.";
    static final String THREADS_AMOUNT_ERR =
            "The threads amount must be greater than or equal to 1. Terminating program.";
    private final int primesUptoNum;
    private final int threadsAmount;
    private int curIndex;
    private final boolean[] lst;

    /**
     * Create a new program for finding primes
     * @param primesUpToNum The number to find the primes up to
     * @param threadsAmount The amount of threads to use
     */
    public ConcurrentPrimes(int primesUpToNum, int threadsAmount){
        this.primesUptoNum = primesUpToNum;
        validatePrimesUpToNum(primesUpToNum);
        this.threadsAmount = threadsAmount;
        validateThreadsAmount(threadsAmount);
        this.lst = new boolean[primesUpToNum - 1];
    }

    /**
     * get the current number to check
     * @return The number
     */
    public synchronized int getCurIndex() {
        return curIndex < primesUptoNum - 1 ? curIndex++ : DONE;
    }

    /**
     * Set the number index as a prime
     * @param index The number to set
     */
    public synchronized void setPrime(int index) {
        lst[index] = true;
    }

    /**
     * Run the program
     * @throws InterruptedException In case a thread is being interrupted while waiting
     */
    public void run() throws InterruptedException {
        boolean isEnded;
        ExecutorService executorService = Executors.newCachedThreadPool();
        System.out.println("\n\nThreads Log:\n\n");
        for (int i = 0; i < threadsAmount; i++) {
            System.out.printf("Started thread number %d\n", i);
            executorService.execute(new PrimesThread(this, i));
        } executorService.shutdown();
        isEnded = executorService.awaitTermination(1, TimeUnit.MINUTES);
        if (!isEnded) System.out.println(TOO_LONG_MSG);
        System.out.println("\n\n");
        printPrimes();
    }

    /**
     * Prints all primes found
     */
    private void printPrimes() {
        System.out.printf(PRIMES_FORMATTED_MSG, primesUptoNum);
        for (int i = 0; i < primesUptoNum - 1; i++) {
            if (lst[i]) System.out.println(i + 2);
        }
    }

    /**
     * Check to see if the input for the primes up to num is valid
     * @param primesUptoNum The number to find the primes up to
     */
    private void validatePrimesUpToNum(int primesUptoNum){
        if (primesUptoNum < 1) {
            System.out.println(PRIMES_UP_TO_NUM_ERR);
            exit(0);
        }
    }

    /**
     * Check to see if the input for the amount of threads is valid
     * @param threadsAmount The amount of threads to use
     */
    private void validateThreadsAmount(int threadsAmount){
        if (threadsAmount < 1) {
            System.out.println(THREADS_AMOUNT_ERR);
            exit(0);
        }
    }
}

