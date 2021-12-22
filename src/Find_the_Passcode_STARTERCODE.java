import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Find_the_Passcode_STARTERCODE {

    public static void main(String[] args) throws IOException {

            String fn = "length_2_N_44.txt";
    //        String fn = "length_3_N_021.txt";
    //        String fn = "length_3_N_429.txt";
    //        String fn = "length_4_N_0930.txt";
    //        String fn = "length_4_N_8589.txt";
    //        String fn = "length_5_N_27100.txt";

            // Call helper function to read the input file
            Integer[] data = read_array(fn);

        //
        // We need to know the length of the "strings" in this data file (for output
        // purposes later), but we read and saved them as integers, so the best way to
        // determine this now is to loop over the list, find the largest item, and
        // assume that all of the items are as long as that one.
        //
        Integer max = 0;
        for (int i = 0; i < data.length; i++) {
            if (data[i] > max) {
                max = data[i];
            }
        }
        int length_of_passcodes = String.valueOf(max).length();


        //
        //---------------------------------------------------------------
        // MAIN PROGRAM LOGIC HERE
        mergeSort(data);
        System.out.println("data: " + Arrays.toString(data));
        int smallest_missing_number = smallest_missing_number(data);
        System.out.println("\nSmallest missing number:" + smallest_missing_number);
        new_passcode(data, smallest_missing_number, length_of_passcodes);
        //---------------------------------------------------------------
        //

    }


    static void new_passcode(Integer[] data, int smallest_missing_number, int length_of_passcodes) {
        Integer[] copy = new Integer[data.length + 1];
        for (int i = 0; i < smallest_missing_number; i++) {
            copy[i] = data[i];
        }
        copy[smallest_missing_number] = smallest_missing_number;
        for (int i = smallest_missing_number + 1; i < copy.length; i++) {
            copy[i] = data[i - 1];
        }

        //Printing the new passcode
        System.out.println("\nThe new passcode");
        System.out.print("[");
        for (int i = 0; i < copy.length; i++) {
            System.out.print(String.format("%0" + length_of_passcodes + "d", copy[i]) + " , ");
        }
        System.out.print("]");
    }

    static int smallest_missing_number(Integer[] data) {
        ArrayList<Integer> missing = new ArrayList<Integer>();
        help(data, 0, (data.length - 1), missing);
        return missing.get(0);
    }

    static void help(Integer[] data, int s, int e, ArrayList<Integer> missing) {
        int m = (s + e) / 2;
        if (s == e) {
            return;
        }
        help(data, s, m, missing);

        if ((data[m]) != m) {
            missing.add(m);
            return;
        }
        help(data, (m + 1), e, missing);
    }

    public static Integer[] read_array(String filename) throws IOException {
        //
        // Reads the input file given by "filename", assumed to contain a list of
        // integer numbers. Stores the numbers into an array and returns the
        // array.
        //
        File file = new File(filename);
        Scanner sc = new Scanner(file);

        //
        // Read the items initially into an ArrayList (for dynamic growth)
        //
        ArrayList<Integer> input_list = new ArrayList<Integer>();
        while (sc.hasNext()) {
            int n = sc.nextInt();
            input_list.add(n);
        }

        //
        // Copy the ArrayList to an Integer[] array of the proper size
        //
        Integer[] arr = new Integer[input_list.size()];
        arr = input_list.toArray(arr);
        return arr;
    }

    static void mergeSort(Integer[] data) {
        if (data.length > 1) {
            Integer[] left = Arrays.copyOfRange(data, 0, ((data.length / 2)));
            Integer[] right = Arrays.copyOfRange(data, (data.length / 2), ((data.length)));
            mergeSort(left);
            mergeSort(right);
            merge(left, right, data);
        }
    }

    static void merge(Integer[] left, Integer[] right, Integer[] data) {
        int i = 0, j = 0, k = 0, p = left.length, q = right.length;
        while (i < p && j < q) {
            if (left[i] <= right[j]) {
                data[k] = left[i];
                i++;
            } else {
                data[k] = right[j];
                j++;
            }
            k++;
        }
        if (i == p) {
            /* Copy remaining elements of right[] if any */
            while (j < q) {
                data[k] = right[j];
                j++;
                k++;
            }
        } else {
            /* Copy remaining elements of left[] if any */
            while (i < p) {
                data[k] = left[i];
                i++;
                k++;
            }
        }
    }
}
