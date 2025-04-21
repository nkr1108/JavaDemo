import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.io.*;

public class IntegratedMenuGUI extends JFrame {
    private static final int SIZE = 512;
    private JTextArea textArea;
    private int[] UnSortedArrayData; // for storing random and file array data
    private int[] sortedArrayData; // sorted array

    private LinkedList<Integer> linkedListLL = new LinkedList<>();
    private LinkedList<Integer> linkedListLLSorted = new LinkedList<>();

    private BinarySearchTree bst; // binary search tree

    int comparisonCount = 0; // for performance management using count comparison

    public IntegratedMenuGUI() {
        setTitle("CS401 Final Project - Mandakini");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        textArea = new JTextArea();
        textArea.setEditable(false);
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();

        // First Menu Item - Data Preparation
        // (1) Generate Random Data
        // (2) Load Data from File
        JMenu dataPreparationMenu = new JMenu("Data Preparation");
        JMenuItem generateDataItem = new JMenuItem("Generate Random Data");
        JMenuItem loadDataItem = new JMenuItem("Load Data from File");
        dataPreparationMenu.add(generateDataItem);
        dataPreparationMenu.add(loadDataItem);

        // Second Menu Item - Create Unsorted List
        // (1) Store Data in Array
        // (2) Store Data in Linked List
        JMenu createUnsortedListMenu = new JMenu("Create Unsorted List");
        JMenuItem arrayItem = new JMenuItem("Store Data in Array");
        JMenuItem linkedListItem = new JMenuItem("Store Data in Linked List");
        createUnsortedListMenu.add(arrayItem);
        createUnsortedListMenu.add(linkedListItem);

        // Third Menu Item - Create Sorted List
        // (1) Sort Array using Merge Sort
        // (2) Sort LL using Merge Sort
        JMenu createSortedListMenu = new JMenu("Create Sorted List");
        JMenuItem sortArrayItem = new JMenuItem("Sort Array using Merge Sort");
        JMenuItem sortLLItem = new JMenuItem("Sort LL using Merge Sort");
        createSortedListMenu.add(sortArrayItem);
        createSortedListMenu.add(sortLLItem);

        // Fourth Menu Item - Binary Search Tree
        // (1) Create and Display BST
        JMenu binarySearchTreeMenu = new JMenu("Binary Search Tree");
        JMenuItem createBSTItem = new JMenuItem("Create and Display BST");
        binarySearchTreeMenu.add(createBSTItem);

        // Fifth Menu Item - Search Implementation
        // (1) Search Unsorted Array
        // (2) Search Unsorted Linked List
        // (3) Search Sorted Array
        // (4) Search Sorted Linked List
        // (5) Search BST
        JMenu searchImplementation = new JMenu("Search Implementation");
        JMenuItem searchUnsortedArray = new JMenuItem("Search Unsorted Array");
        JMenuItem searchUnsortedLinkedList = new JMenuItem("Search Unsorted Linked List");
        JMenuItem searchSortedArray = new JMenuItem("Search Sorted Array");
        JMenuItem searchSortedLinkedList = new JMenuItem("Search Sorted Linked List");
        JMenuItem searchBST = new JMenuItem("Search BST");

        searchImplementation.add(searchUnsortedArray);
        searchImplementation.add(searchUnsortedLinkedList);
        searchImplementation.add(searchSortedArray);
        searchImplementation.add(searchSortedLinkedList);
        searchImplementation.add(searchBST);

        // adding to main menu
        menuBar.add(dataPreparationMenu);
        menuBar.add(createUnsortedListMenu);
        menuBar.add(createSortedListMenu);
        menuBar.add(binarySearchTreeMenu);
        menuBar.add(searchImplementation);

        setJMenuBar(menuBar);

        // all event listeners
        // Generate Random data, store in array and display on screen
        generateDataItem.addActionListener(e -> {
            UnSortedArrayData = generateRandomData();
            displayData(UnSortedArrayData);
        });

        // Load data from file, store in Array, then display
        loadDataItem.addActionListener(e -> {
            UnSortedArrayData = loadDataFromFile("src/main/java/data.txt");
            displayData(UnSortedArrayData);
        });

        // Store data in Array, it was done in previous step, just displaying here again
        arrayItem.addActionListener(e -> displayArray(UnSortedArrayData));

        // Store data in Linked List, then display it on the screen
        linkedListItem.addActionListener(e -> {
            linkedListLL = createLinkedList(UnSortedArrayData);
            linkedListLL.displayLinkedList(textArea);
        });

        // take unsorted array data and sorts them using merge sort then display the same on screen
        sortArrayItem.addActionListener(e -> {
            sortedArrayData = Arrays.copyOf(UnSortedArrayData, UnSortedArrayData.length);
            mergeSort(sortedArrayData, 0, sortedArrayData.length - 1);
            displayArray(sortedArrayData);
        });

        // take unsorted linked list and sort it and then display it
        sortLLItem.addActionListener(e -> {
            linkedListLLSorted = linkedListLL.copy();
            linkedListLLSorted.mergeSort();
            linkedListLLSorted.displayLinkedList(textArea);
        });


        // creates Binary Search Tree from Unsorted Array Data and then display
        createBSTItem.addActionListener(e -> {
            bst = new BinarySearchTree();
            for (int num : UnSortedArrayData) {
                bst.insert(num);
            }
            displayBST(bst);
        });

        // SEARCH IMPLEMENTATION
        // search using unsorted array
        searchUnsortedArray.addActionListener( Act -> {
            int target = getSearchTarget();
            comparisonCount = 0;
            boolean found = linearSearch(UnSortedArrayData, target);
            textArea.setText("Search in Unsorted Array:\nValue " + target + (found ? " found." : " not found."));
            textArea.append("\nUnsorted Array Search Comparison: " + comparisonCount);
        });

        // search using unsorted linked list
        searchUnsortedLinkedList.addActionListener( Act -> {
            int target = getSearchTarget();
            boolean found = linkedListLL.search(target);
            textArea.setText("Search in Unsorted Linked List:\nValue " + target + (found ? " found." : " not found."));
            textArea.append("\nUnsorted Linked List Search Comparison: " + linkedListLL.getComparisionCount());
        });

        // search using sorted array
        searchSortedArray.addActionListener(e -> {
            int target = getSearchTarget();
            comparisonCount = 0;
            boolean found = binarySearch(sortedArrayData, target);
            textArea.setText("Search in Sorted Array:\nValue " + target + (found ? " found." : " not found."));
            textArea.append("\nSorted Array Search Comparison: " + comparisonCount);
        });

        // search using sorted linked list
        searchSortedLinkedList.addActionListener( Act -> {
            int target = getSearchTarget();
            boolean found = linkedListLLSorted.search(target);
            textArea.setText("Search in Sorted Linked List:\nValue " + target + (found ? " found." : " not found."));
            textArea.append("\nSorted Linked List Comparison: " + linkedListLLSorted.getComparisionCount());
        });


        // searching Binary Search Tree
        searchBST.addActionListener(e -> {
            int target = getSearchTarget();
            comparisonCount = 0;
            boolean found = bst.search(target);
            textArea.setText("Search in BST:\nValue " + target + (found ? " found." : " not found."));
            textArea.append("\nBinary Search Tree Comparison: " + bst.getComparisonCount());
        });

    }

    // methods supporting listeners
    private int[] generateRandomData() {
        Random rand = new Random();
        int[] data = new int[SIZE];
        for (int i = 0; i < SIZE; i++) {
            data[i] = rand.nextInt(5000); // Random numbers between 0 and 999
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


    private LinkedList<Integer> createLinkedList(int[] data) {
        LinkedList<Integer> list = new LinkedList<>();
        for (int num : data) {
            list.insert(num);
        }
        return list;
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

    private int getSearchTarget() {
        String input = JOptionPane.showInputDialog(this, "Enter value to search:");
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private boolean linearSearch(int[] data, int target) {
        for (int val : data) {
            if (val == target) {
                return true;
            }
            comparisonCount++; // Counting comparisons
        }
        return false;
    }

    private boolean binarySearch(int[] data, int target) {
        int left = 0, right = data.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            comparisonCount++;
            if (data[mid] == target) return true;
            if (data[mid] < target) left = mid + 1;
            else right = mid - 1;
        }
        return false;
    }

    private void displayBST(BinarySearchTree bst) {
        textArea.setText("");
        displayBSTRec(bst.root, "", true);
    }

    private void displayBSTRec(BinarySearchTree.Node node, String prefix, boolean isTail) {
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