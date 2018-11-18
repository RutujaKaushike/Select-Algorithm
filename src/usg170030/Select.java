/**
 * @author
 *      1. Utkarsh Gandhi (usg170030)
 *      2. Rutuja Kaushike (rnk170000)
 */

package usg170030;

import java.util.*;

public class Select {

    static Random random;
    static int threshold = 16;

    /**
     *select method
     * @param arr
     * @param k
     */
    public static void select(int[] arr, int k) {
        select(arr, 0, arr.length, k);
    }

    /**
     * helper method of select - finds kth largest element index using randomized partitioning
     * @param arr
     * @param p
     * @param n
     * @param k
     * @return kth largest element index
     */

    private static int select(int[] arr, int p, int n, int k) {
        if (n < threshold) {
            insertionSort(arr, p, p + n - 1);
            return n - k;
        }

        int q = randomizedPartition(arr, p, p + n - 1);
        int left = q - p;
        int right = n - left -1;
        if (right >= k) {
            return select(arr, q + 1, right, k);
        }
        else if (right + 1 == k) {
                return q;
        }
        else {
            return  select(arr, p, left, k - right - 1);
        }
    }

    /**
     * randomizedPartition - performs random partition using a random pivot
     * @param arr
     * @param p
     * @param r
     * @return index from which partition to be done
     */
    private static int randomizedPartition(int[] arr, int p, int r) {

        int pivot = p + random.nextInt(r - p + 1);
        swap(arr, pivot, r);
        return partition(arr, p, r);
    }

    /**
     * partition method - performs the partition on the array
     * @param arr
     * @param p
     * @param r
     * @return index
     */
    private static int partition(int[] arr, int p, int r) {
        int x = arr[r];
        int i = p - 1;
        for (int j = p; j <= r-1; j++) {
            if (arr[j] <= x){
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i+1, r);
        return i+1;
    }

    /**
     * swap method - swap two elements in the array
     * @param arr
     * @param p
     * @param r
     */
    private static void swap(int[] arr, int p, int r) {
        int temp = arr[p];
        arr[p] = arr[r];
        arr[r] = temp;
    }

    /**
     * insertionSort - performs insertion sort on the array if the size of the array is smaller than threshold
     * @param arr
     * @param p
     * @param r
     */
    public static void insertionSort(int[] arr, int p, int r) {
        for (int i = p + 1; i <= r; ++i) {
            int key = arr[i];
            int j = i-1;

            while (j >= p && arr[j] > key) {
                arr[j+1] = arr[j];
                j--;
            }
            arr[j+1] = key;
        }
    }

    /**
     *compareResult - compares the two lists - used for comparing the results from prioirty queue and select algorithm
     * @param res1
     * @param res2
     * @return true if same otherwise false
     */
    private static boolean compareResult(List<Integer> res1, List<Integer> res2) {
        if(res1.size() != res2.size()) return false;
        for (int i=0;i<res1.size();i++) {
            if(!res1.get(i).equals(res2.get(i))) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Timer timer = new Timer();
        random = new Random();
        int[] arr = new int[640000];
        int k = arr.length/2;
        for (int i = 0; i < arr.length; i++) {
            int newNumber = random.nextInt();
            arr[i] = newNumber;
        }

        System.out.println("Using Priority Queue");
        timer.start();
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for (int i =0; i < arr.length; i++ ) {
            if (pq.size() < k) {
                pq.offer(arr[i]);
            }
            else {
                if(arr[i] > pq.peek()) {
                    pq.offer(arr[i]);
                    pq.poll();
                }
            }
        }
        timer.end();
        System.out.println(timer);
        System.out.println();

        List <Integer> priorityResult = new ArrayList<>(); // stores the k largest elements using priority queue
        while (!pq.isEmpty()) {
            priorityResult.add(pq.poll());
        }

        System.out.println("Using Select Algorithm");
        timer.start();
        select(arr, k);
        timer.end();
        System.out.println(timer);


        List<Integer> selectResult = new ArrayList<>(); // stores the k largest elements using select algorithm
        for (int i=arr.length-k;i<arr.length;i++)
            selectResult.add(arr[i]);
        Collections.sort(selectResult);

        compareResult(selectResult, priorityResult);
    }
}
