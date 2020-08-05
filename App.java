package app;

import java.util.Scanner;

public class App {

    
    Node head, rear;
    private static final int MAX_LEVEL = 10;
    private static final double P = 0.25;
    int modos = 0;

    // Searched Item Found Or Not?
    boolean isFound = false;

    // Size
    int length = 0;

    // Current Height
    int height = 1;

    // Constructor
    public App() {
        head = new Node(Node.NEG_INF);
        rear = new Node(Node.POS_INF);
        modos = 1;

        head.next = rear;
        rear.prev = head;
    }

    // Start - Search In Skip List By Data
    private Node search(int data) {
        Node p = head;

        while (true) {
            while (p.next.data != Node.POS_INF && p.next.data <= data) {
                p = p.next;
            }

            if (p.down != null) {
                p = p.down;
            } else {
                break;
            }
        }
        modos = 2;

        // Not Used !!!
        isFound = p.data == data ? true : false;

        return p;
    }
    // End - Search in Skip List By Data

    // Start - Return Search Result
    public void get(int data) {
        Node p = search(data);
        System.out.println(p.data == data ? "true" : "false");
    }
    // End - Return Search Result

    // Start - Insert Into Skip List
    public void insert(int data) {
        Node p, q;

        p = search(data);

        // if (p.data == data) {
        // int old = p.data;
        // p.data = data;
        // } else {

        // Add First Node In Column
        q = new Node(data);

        q.prev = p;
        q.next = p.next;
        p.next.prev = q;
        p.next = q;

        length++;

        modos = 1;

        // Current Level
        int i = 1;

        while (Math.random() < P && height < MAX_LEVEL) {
            // Add New Layer
            if (i >= height) {
                Node p1, p2;
                p1 = new Node(Node.NEG_INF);
                p2 = new Node(Node.POS_INF);

                p1.next = p2;
                p1.down = head;
                p2.prev = p1;
                p2.down = rear;

                head.top = p1;
                rear.top = p2;

                head = p1;
                rear = p2;

                height++;
            }

            while (p.top == null) {
                p = p.prev;
            }

            p = p.top;

            // Add Another Node To Column
            Node e = new Node(data);

            e.prev = p;
            e.next = p.next;
            e.down = q;

            p.next.prev = e;
            p.next = e;
            q.top = e;

            q = e;

            i++;

        }
    }
    // }
    // End - Insert Into Skip List

    // Start - Delete From Skip List
    public void delete(int data) {
        // Node p = search(data);

        Node p = search(data);
        if (p.data != data) {
            System.out.println("error");
        }

        while (true) {
            p = search(data);
            if (p.data == data) {
                length--;
                while (p != null) {
                    p.prev.next = p.next;
                    p.next.prev = p.prev;
                    p = p.top;
                }
            } else {
                modos = 0;
                break;
            }
        }
    }
    // End - Delete From Skip List

    // Start - Print Skip List
    public void print() {
        Node p = head;
        // System.out.println("size of list is : " + length);
        if (length == 0) {
            System.out.println("empty");
        } else {

            while (p.down != null) {
                p = p.down;
            }

            while (p.next != null) {
                p = p.next;
                if (p.data != Node.NEG_INF && p.data != Node.POS_INF) {
                    System.out.print(p.data);
                    System.out.print(' ');
                }
            }

            modos = 0;

        }

    }
    // End - Print Skip List

    public static void main(String[] args) throws Exception {
        App app = new App();
        Scanner input = new Scanner(System.in);
        String[] temp;
        String command;
        int data;

        while (input.hasNext()) {
            temp = input.nextLine().split(" ");
            command = temp[0];
            switch (command) {
                case "Print":
                    app.print();
                    break;
                case "Insert":
                    if (temp.length > 1 ){data = Integer.parseInt(temp[1]);
                    app.insert(data);}
                    int modos = 0;
                    break;

                case "Delete":
                if (temp.length > 1 ){data = Integer.parseInt(temp[1]);
                    app.delete(data);}
                    int modoss = 0;
                    break;
                case "Search":
                if (temp.length > 1 ){data = Integer.parseInt(temp[1]);
                    app.get(data);}
                    break;

                default:
                    input.close();
                    break;
            }
        }

        /*
         * app.insert(10); app.insert(10); app.insert(20); app.delete(30); app.print();
         */
    }

    static class Node {
        int data;
        Node top, down, prev, next;
        public static final int POS_INF = (int) Math.pow(10, 9);
        public static final int NEG_INF = (int) Math.pow(-10, 9);
        int modos = 0;
    
        // Constructor
        public Node(int data) {
            this.data = data;
        }
    }
    
    
    }




