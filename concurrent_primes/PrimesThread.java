package concurrent_primes;

/**
 * A thread for finding primes
 */
public class PrimesThread implements Runnable{
    private final ConcurrentPrimes buff;
    private final int serialNum;

    /**
     * Create a new thread for finding primes
     * @param buff The mutual resource to update with the primes found
     * @param serialNum The serial number of this thread (used for printing the log)
     */
    public PrimesThread(ConcurrentPrimes buff, int serialNum){
        this.buff = buff;
        this.serialNum = serialNum;
    }

    /**
     * Run this thread
     */
    @Override
    public void run() {
        int curIndex;
        while ((curIndex = buff.getCurIndex()) != ConcurrentPrimes.DONE){
                if (isPrime(curIndex + 2)){
                    buff.setPrime(curIndex);

                    //################################################################################
                    System.out.printf("Set %d as prime by thread number %d\n", curIndex+2, serialNum);
                    //################################################################################
            }
        }
    }

    /**
     * Check if the given number is a prime or not
     * @param num The num to check
     * @return True iff it is prime
     */
    private boolean isPrime(Integer num){
        int maxToCheck = (int) Math.floor(Math.sqrt(num));
        for (int i=2; i<=maxToCheck; i++){
            if ( num % i == 0) return false;
        } return true;
    }
}