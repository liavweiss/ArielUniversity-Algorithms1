package MinMaxAlgorithms;

import java.util.ArrayList;
import java.util.Stack;

/**
 * This class represents the solution to the problem of finding two maximum numbers using a stack.
 */

class Node {
    public int number;
    public Stack<Integer> st;

    public Node(int a) {
        this.number = a;
        this.st = new Stack<Integer>();
    }
}

public class MinMax_stack {
    /**
     * Recursive.
     * Comparisons: n/2+n/4+n/8.....+1 = n-1 + log(n).
     * Method that receives an array and sends each member to Node.
     */
    public static void max1Max2Recursive(int[] arr) {
        int n = arr.length;
        Node[] node = new Node[n];
        for (int i = 0; i < n; i++) {
            node[i] = new Node(arr[i]);
        }
        int index = max1Max2(node, 0, n - 1);
        int max1 = node[index].number;
        int max2 = node[index].st.pop();
        while (!node[index].st.isEmpty()) {
            int temp = node[index].st.pop();
            if (temp > max2) max2 = temp;
        }
        System.out.println("Max1: " + max1 + ", max2: " + max2);
    }


    private static int max1Max2(Node[] a, int low, int high) {
        if (low < high) {
            int index = 0;
            int mid = (low + high) / 2;
            int i = max1Max2(a, low, mid);
            int j = max1Max2(a, mid + 1, high);

            if (a[i].number > a[j].number) {
                a[i].st.push(a[j].number);
                index = i;
            } else {
                a[j].st.push(a[i].number);
                index = j;
            }
            return index;
        } else {
            return low;
        }
    }

    /**
     * Find the 2 maximum elements in array - Inductive
     * Complexity: O(n) - (n + log n) comparisons
     */
    public static int max1Max2Inductive(int[] arr) {
        int comparisons = 0;
        ArrayList<Node> list = new ArrayList<Node>();
        for (int i = 0; i < arr.length; i++) {
            list.add(new Node(arr[i]));
        }
        int i = 0;
        while (list.size() > 1) {
            Node x = list.get(i);
            Node y = list.get(i + 1);
            comparisons++;
            if (x.number > y.number) {
                x.st.add(y.number);
                list.remove(i + 1);
            } else {
                y.st.add(x.number);
                list.remove(i);
            }
            i++;
            if (i == list.size() || i == list.size() - 1) i = 0;
        }
        int max1 = list.get(0).number;
        Stack<Integer> st = list.get(0).st;
        int max2 = st.pop();
        while (!st.isEmpty()) {
            int x = st.pop();
            comparisons++;
            if (x > max2) {
                max2 = x;
            }
        }
        System.out.println("Max1 = " + max1 + " , Max2 = " + max2 + " , Comparisons = " + comparisons);
        return comparisons;
    }


    public static void main(String[] args) {
        int[] arr = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 55, 4, 5, 255};
        max1Max2Recursive(arr);
        max1Max2Inductive(arr);

    }
}


/**
 * הוכחת נכונות:
 * לכל איבר במערך נצמיד מחסנית ריקה
 * ואז נבדוק את המערך בזוגות כשאר כל זוג נבדוק מי גדול יותר ומי שקטן יותר ייכנס למחסנית של הגדול יותר כמועמד למקסימום 2
 * נעשה את זה שוב עם המערך המצומצם עד שנגיע למועמד להיות מקס 1
 * בסופו של דבר נבחר מתוך המחסנית של מקס 1 את מקס 2
 * מספר ההשוואות יהיה : n-1+log n
 */
