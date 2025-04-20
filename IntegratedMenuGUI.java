import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

public class IntegratedMenuGUI extends JFrame {
    private static final int SIZE = 512;
    private JTextArea textArea;
    private int[] storedData;

    public IntegratedMenuGUI() {
        setTitle("Integrated Menu Application");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        textArea = new JTextArea();
        textArea.setEditable(false);
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();

        JMenu dataPreparationMenu = new JMenu("Data Preparation");
        JMenuItem generateDataItem = new JMenuItem("Generate Random Data");
        JMenuItem loadDataItem = new JMenuItem("Load Data from File");
        dataPreparationMenu.add(generateDataItem);
        dataPreparationMenu.add(loadDataItem);

        JMenu createUnsortedListMenu = new JMenu("Create Unsorted List");
        JMenuItem arrayItem = new JMenuItem("Store Data in Array");
        JMenuItem linkedListItem = new JMenuItem("Store Data in Linked List");
        createUnsortedListMenu.add(arrayItem);
        createUnsortedListMenu.add(linkedListItem);

        JMenu createSortedListMenu = new JMenu("Create Sorted List");
        JMenuItem sortArrayItem = new JMenuItem("Sort Array using Merge Sort");
        createSortedListMenu.add(sortArrayItem);

        JMenu binarySearchTreeMenu = new JMenu("Binary Search Tree");
        JMenuItem createBSTItem = new JMenuItem("Create and Display BST");
        binarySearchTreeMenu.add(createBSTItem);

        menuBar.add(dataPreparationMenu);
        menuBar.add(createUnsortedListMenu);
        menuBar.add(createSortedListMenu);
        menuBar.add(binarySearchTreeMenu);

        setJMenuBar(menuBar);

        generateDataItem.addActionListener(e -> {
            storedData = generateRandomData();
            displayData(storedData);
        });
        loadDataItem.addActionListener(e -> {
            storedData = loadDataFromFile("src/main/java/data.txt");
            displayData(storedData);
        });
        arrayItem.addActionListener(e -> displayArray(storedData));
        linkedListItem.addActionListener(e -> displayLinkedList(storedData));
        sortArrayItem.addActionListener(e -> {
            int[] data = Arrays.copyOf(storedData, storedData.length);
            mergeSort(data, 0, data.length - 1);
            displayArray(data);
        });
        createBSTItem.addActionListener(e -> {
            BST bst = new BST();
            for (int num : storedData) {
                bst.insert(num);
            }
            displayBST(bst);
        });
    }

    private int[] generateRandomData() {
        Random rand = new Random();
        int[] data = new int[SIZE];
        for (int i = 0; i < SIZE; i++) {
            data[i] = rand.nextInt(1000); // Random numbers between 0 and 999
        }
        return data;
    }

    private int[] loadDataFromFile(String filename) {
        int[] data = new int[SIZE];
        try {
            Scanner fileScanner = new Scanner(new File(filename));
            for (int i = 0; i < SIZE; i++) {
                if (fileScanner.hasNextInt()) {
                    data[i] = fileScanner.nextInt();
                }
            }
        } catch (FileNotFoundException e) {
            textArea.setText("File not found");
        }
        return data;
    }

    private void displayData(int[] data) {
        textArea.setText("");
        for (int i = 0; i < data.length; i++) {
            textArea.append(data[i] + " ");
            if ((i + 1) % 20 == 0) {
                textArea.append("\n");
            }
        }
    }

    private void displayArray(int[] data) {
        textArea.setText("");
        for (int i = 0; i < data.length; i++) {
            textArea.append(data[i] + " ");
            if ((i + 1) % 20 == 0) {
                textArea.append("\n");
            }
        }
    }

    private void displayLinkedList(int[] data) {
        LinkedList<Integer> list = new LinkedList<>();
        for (int num : data) {
            list.add(num);
        }
        textArea.setText("");
        int count = 0;
        for (int num : list) {
            textArea.append(num + " ");
            count++;
            if (count % 20 == 0) {
                textArea.append("\n");
            }
        }
    }

    private void mergeSort(int[] array, int left, int right) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(array, left, mid);
            mergeSort(array, mid + 1, right);
            merge(array, left, mid, right);
        }
    }

    private void merge(int[] array, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        int[] leftArray = new int[n1];
        int[] rightArray = new int[n2];

        for (int i = 0; i < n1; i++) {
            leftArray[i] = array[left + i];
        }
        for (int i = 0; i < n2; i++) {
            rightArray[i] = array[mid + 1 + i];
        }

        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (leftArray[i] <= rightArray[j]) {
                array[k] = leftArray[i];
                i++;
            } else {
                array[k] = rightArray[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            array[k] = leftArray[i];
            i++;
            k++;
        }

        while (j < n2) {
            array[k] = rightArray[j];
            j++;
            k++;
        }
    }

    private void displayBST(BST bst) {
        textArea.setText("");
        displayBSTRec(bst.root, "", true);
    }

    private void displayBSTRec(BST.Node node, String prefix, boolean isTail) {
        if (node != null) {
            textArea.append(prefix + (isTail ? "└── " : "├── ") + node.data + "\n");
            displayBSTRec(node.left, prefix + (isTail ? "    " : "│   "), false);
            displayBSTRec(node.right, prefix + (isTail ? "    " : "│   "), true);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            IntegratedMenuGUI frame = new IntegratedMenuGUI();
            frame.setVisible(true);
        });
    }
}

class BST {
    class Node {
        int data;
        Node left, right;

        Node(int data) {
            this.data = data;
            left = right = null;
        }
    }

    Node root;

    BST() {
        root = null;
    }

    void insert(int data) {
        root = insertRec(root, data);
    }

    Node insertRec(Node root, int data) {
        if (root == null) {
            root = new Node(data);
            return root;
        }
        if (data < root.data) {
            root.left = insertRec(root.left, data);
        } else if (data > root.data) {
            root.right = insertRec(root.right, data);
        }
       
