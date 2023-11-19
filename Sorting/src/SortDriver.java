import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class SortDriver {
    public static void main(String[] args) throws Exception {
        
        Scanner keyboard = new Scanner(System.in);

        int list[] = new int[0];

        if (args.length != 0) {

            File input = new File(args[0]);
            list = new int[10000];
            try {
                    Scanner fileReader = new Scanner(input);
                    for (int count = 0; count < list.length; count++) {
                        list[count] = fileReader.nextInt();
                    }
                fileReader.close();
            } catch (FileNotFoundException fnfe) {
                System.out.println("File not found.");
            }
        } else {
            System.out.print("Enter array length:");
            int num = keyboard.nextInt();
            list = new int[num];
            for (int count = 0; count < list.length; count++) {
                list[count] = (int) (Math.random() * list.length);
            }
        }
        
        Counter count = new Counter();
        System.out.println("selection-sort (s) merge-sort (m) heap-sort (h) quick-sort-fp (q) quick-sort-rp (r)");
        System.out.print("Enter the algorithm");
        String input = keyboard.next();
        String sortType = "";
        switch (input) {
            case "s":
                SelectionSort(list, count);
                sortType = "Selection-sort";
                break;

            case "m":
                MergeSort(list, 0, list.length - 1, count);
                sortType = "Merge-sort";
                break;

            case "h":
                HeapSort(list, list.length, count);
                sortType = "Heap-sort";
                break;

            case "q":
                QuickSortFP(list, 0, list.length - 1, count);
                sortType = "Quick-sort-fp";
                break;

            case "r":
                QuickSortRP(list, 0, list.length - 1, count);
                sortType = "Quick-sort-rp";
                break;

            default:
                System.out.print("Incorrect input");
                System.exit(0);
                break;
        }

        for(int num : list) {
            System.out.print(num + " ");
        }
        System.out.println();

        System.out.println("#" + sortType + " comparisons: " + count.getCount());

        keyboard.close();
    }

    public static void SelectionSort (int values[ ], Counter count)
    // Post: Sorts array values[0 . . numValues-1 ]
    // into ascending order by key
    {
        int endIndex = values.length - 1;
        for (int current=0; current < endIndex; current++) {
            Swap(values, current, MinIndex(values, current, endIndex, count));
        }
    }

    private static int MinIndex(int values [ ], int start, int end, Counter count)
    // Post: Function value = index of the smallest value
    // in values [start] . . values [end].
    {
    int indexOfMin = start ;
    for(int index = start + 1 ; index <= end ; index++) {
        count.count();
        if (values[index] < values [indexOfMin])
            indexOfMin = index ;
    }
    return indexOfMin;
    }

    private static void Swap(int values[], int loc1, int loc2) {
        int temp = values[loc1];
        values[loc1] = values[loc2];
        values[loc2] = temp;
    }

    public static void MergeSort (int values[ ], int first, int last, Counter count)
    // Pre: first <= last
    // Post: Array values[first..last] sorted into
    // ascending order.
    {
        count.count();
        if ( first < last ) // general case
        {
            int middle = ( first + last ) / 2;
            MergeSort ( values, first, middle, count);
            MergeSort ( values, middle + 1, last, count);
            // now merge two subarrays
            // values [ first . . . middle ] with
            // values [ middle + 1, . . . last ].
            Merge(values, first, middle, middle + 1, last, count);
        }
    }

    private static void Merge (int values[], int leftFirst, int leftLast, int rightFirst, int rightLast, Counter count) {
        int[] tempArray = new int[values.length];
        int index = leftFirst;
        int saveFirst = leftFirst;

        while ((leftFirst <= leftLast) && (rightFirst <= rightLast)) {
            count.count();
            if (values[leftFirst] < values[rightFirst]) {
                tempArray[index] = values[leftFirst];
                leftFirst++;
            } else {
                tempArray[index] = values[rightFirst];
                rightFirst++;
            }
            index++;
        }
        
        while (leftFirst <= leftLast) {
            tempArray[index] = values[leftFirst];
            leftFirst++;
            index++;
        }

        while (rightFirst <= rightLast) {
            tempArray[index] = values [rightFirst];
            rightFirst++;
            index++;
        }

        for (index = saveFirst; index <= rightLast; index++) {
            values[index] = tempArray[index];
        }
    }

    public static void HeapSort (int values[], int numValues, Counter count)
    // Post: Sorts array values[ 0 . . numValues-1 ] into
    // ascending order by key
    {
        int index ;
        // Convert array values[0..numValues-1] into a heap
        for (index = numValues/2 - 1; index >= 0; index--)
            ReheapDown ( values , index , numValues - 1, count) ;
        // Sort the array.
        for (index = numValues - 1; index >= 1; index--)
        {
            Swap (values, 0 , index);
            ReheapDown (values , 0 , index - 1, count);
        }
    }

    private static void ReheapDown (int values[], int root, int bottom, Counter count) {
        int maxChild ;
        int rightChild ;
        int leftChild ;

        leftChild = root * 2 + 1 ;
        rightChild = root * 2 + 2 ;
        
        if (leftChild <= bottom) // ReheapDown continued
        {
            if (leftChild == bottom)
                maxChild = leftChild;
            else
            {
                count.count();
                if (values[leftChild] <= values [rightChild])
                    maxChild = rightChild ;
                 else
                    maxChild = leftChild ;
            }
            count.count();
            if (values[ root ] < values[maxChild])
            {
                Swap (values, root, maxChild);
                ReheapDown (values, maxChild, bottom, count);
            }
        }
    }

    public static void QuickSortFP(int[] values, int low, int high, Counter count) {
        if (low < high) {
            int splitPoint = SplitFP(values, low, high, count);
            QuickSortFP(values, low, splitPoint - 1, count);
            QuickSortFP(values, splitPoint + 1, high, count);
        }
    }

    public static int SplitFP(int[] values, int low, int high, Counter count) {
        int splitVal = values[low];
        int left = low + 1;
        int right = high;

        while (left <= right) {
            while (left <= right && values[left] <= splitVal) {
                count.count();
                left++;
            }
            while (left <= right && values[right] > splitVal) {
                count.count();
                right--;
            }
            if (left < right) {
                int temp = values[left];
                values[left] = values[right];
                values[right] = temp;
            }
        }

        int temp = values[right];
        values[right] = values[low];
        values[low] = temp;

        return right;
    }

    public static void QuickSortRP(int[] values, int low, int high, Counter count) {
        if (low < high) {
            int splitPoint = SplitRP(values, low, high, count);
            QuickSortRP(values, low, splitPoint - 1, count);
            QuickSortRP(values, splitPoint + 1, high, count);
        }
    }

    public static int SplitRP(int[] values, int low, int high, Counter count) {
        int splitPoint = (int) (Math.random() * (high - low)) + low;
        int splitVal = values[splitPoint];
        Swap(values, low, splitPoint);
        int left = low + 1;
        int right = high;

        while (left <= right) {
            while (left <= right && values[left] <= splitVal) {
                count.count();
                left++;
            }
            while (left <= right && values[right] > splitVal) {
                count.count();
                right--;
            }
            if (left < right) {
                int temp = values[left];
                values[left] = values[right];
                values[right] = temp;
            }
        }

        int temp = values[right];
        values[right] = values[low];
        values[low] = temp;

        return right;
    }
}