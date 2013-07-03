package practice;

/**

   Blocking queue is a producer / consumer problem

 */
public class BlockingQueue<T> {

    private Object[] array;

    private int lastIndex;

    public BlockingQueue(int N) {
        array = new Object[N];
        lastIndex = 0;
    }

    public synchronized void enqueue(T item) {
        while (lastIndex == array.length - 1) {  // full
            try {
                wait();
            } catch (InterruptedException ie) {

            }
        }

        array[++lastIndex] = item;
        notifyAll();
    }

    public synchronized T dequeue() {
        while (lastIndex == -1) {  // empty
            try {
                wait();
            } catch (InterruptedException ie) {

            }
        }
        T result = (T) array[lastIndex--];
        notifyAll();
        return result;
    }
}
