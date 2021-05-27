package concurrent_primes;

/**
 * The running class
 */
public abstract class Main {

    /**
     * The running method
     * @param args The arguments for this program (ignore)
     * @throws InterruptedException In case a thread is interrupted while waiting
     */
    public static void main(String[] args) throws InterruptedException {
        ConcurrentPrimes app = new ConcurrentPrimes(1000000000, 10);
        app.run();
    }
}