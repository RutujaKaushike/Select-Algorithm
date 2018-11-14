package usg170030;

import java.util.Random;

public class Select {

    public static int threshold = 4;

    public static void select(int[] arr, int k) {
        System.out.println(select(arr, 0, arr.length, k));
    }

    private static int select(int[] arr, int p, int n, int k) {
        if (n < threshold) {
            insertionSort(arr, p, p + n - 1);
        }
        else {
            int q = randomizedPartition(arr, p, p + n - 1);
            int left = q - p;
            int right = n - left -1;
            if (right >= k) {
                return select(arr, q + 1, right, k);
            }
            else {
                if (right + 1 == k) {
                    return q;
                }
                else {
                    return  select(arr, p, left, k - right -1);
                }
            }
        }
        return 0;
    }

    private static int randomizedPartition(int[] arr, int p, int r) {
        int n = r - p +1;
        int pivot = (int)(Math.random()) * (n-1);
        swap(arr, p + pivot, r);
        return partition(arr, p, r);
    }

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

    private static void swap(int[] arr, int p, int r) {
        int temp = arr[p];
        arr[p] = arr[r];
        arr[r] = temp;
    }

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

    public static void main(String[] args) {
//        int[] arr = new int[100];
//        for(int i=0; i<arr.length; i++) {
//            arr[i] = i;
//        }
        Random random = new Random();
        int[] integers = new int[10];
        for (int i = 0; i < integers.length; i++) {
            int newNumber = random.nextInt();
            integers[i] = newNumber;
        }
        select(integers, 3);
    }
}
