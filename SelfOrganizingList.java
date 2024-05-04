package gad.list;

import java.util.Optional;
import java.util.function.Predicate;

public class SelfOrganizingList<T> {
    private Node<T> head;

    public Node<T> getHead() {
        return head;
    }
    private Node<T> lastAdded;


    public static class Node<T> {
        private T data;
        private Node<T> next;


        public Node(T d) {
            data = d;
            next = null;
        }

        public T getData() {
            return data;
        }

        public Node<T> getNext() {
            return next;
        }
    }

    public Node<T> getLastAdded(){
        return lastAdded;
    }
    public void setLastAdded(Node<T> node){
        lastAdded = node;
    }

    private void printElements() {
        Node<T> next = head;
        while (next != null) {
            System.out.print(next.data.toString());
            if (next.next != null) {
                System.out.print(" -> ");
            }
            next = next.next;
        }
        System.out.println();
    }

    public void add(T data) {
        if (getLastAdded() == null){
            head = new Node<>(data);
            setLastAdded(head);
            return;
        }else if(getLastAdded() == head){
            head.next = new Node<>(data);
            setLastAdded(head.next);
            return;
        }
        getLastAdded().next = new Node<>(data);
        setLastAdded(getLastAdded().next);
    }

    public Optional<T> findFirst(Predicate<T> p){
        // TODO
        Node<T> curr = head;
        while(curr.next != null && !p.test(curr.next.data)){
            curr = curr.next;
        }
        if (curr.next == null) {
            return Optional.empty();
        }

        Node<T> firstNode = new Node<>(curr.next.data);
        T result = curr.next.data;
        firstNode.next = head;
        head = firstNode;
        curr.next = curr.next.next;
        return Optional.of(result);
    }

    public void removeDuplicates(){
        // TODO
        if (this.head.next == null){
            return;
        }
        Node<T> curr = head;
        Node<T> temp = head;
        Node<T> checker = curr.next;
        while(curr != null){
            T duplicatedValue = curr.data;
            while(checker != null){
                if(checker.data == duplicatedValue){
                    if (checker.next != null) {
                        checker = checker.next;
                        temp.next = checker;
                    }else{
                        temp.next = null;
                        checker = null;
                        break;
                    }
                }else{
                    temp = temp.next;
                    checker = checker.next;
                }
            }
            curr = curr.next;
            temp = curr;
            if (curr != null){
                checker = curr.next;
            }
        }

    }

    public static void main(String[] args) {
        SelfOrganizingList<Integer> myList = new SelfOrganizingList<>();
        System.out.println("add testen");
        for (int i = 0; i < 20; i++) {
            myList.add(i);
        }
        System.out.println("Enthaltene Elemente (sollte von 0 bis 19 aufsteigend sein): ");
        myList.printElements();

        System.out.println();
        System.out.println("findFirst testen");
        System.out.println("Sollte 11 zurückgeben: " + myList.findFirst(n -> n > 10).toString());
        System.out.println("Enthaltene Elemente (11 sollte am Index 0 stehen): ");
        myList.printElements();
        System.out.println("Sollte 12 zurückgeben: " + myList.findFirst(n -> n == 12).toString());
        System.out.println("Enthaltene Elemente (Liste sollte mit 12, 11, 0, 1, ... beginnen): ");
        myList.printElements();
        System.out.println("Sollte leeres Optional zurückgeben" + myList.findFirst(n -> n > 30).toString());
        System.out.println("Enthaltene Elemente (sollten gleich bleiben): ");
        myList.printElements();

        System.out.println();
        System.out.println("removeDuplicates testen");
        for (int i = 0; i < 3; i++) {
            myList.add(3 * i % 4);
            myList.add(4 * i % 3);
        }
        myList.add(20);
        System.out.print("Enthaltene Elemente: ");
        myList.printElements();
        myList.removeDuplicates();
        System.out.print("Enthaltene Elemente: ");
        myList.printElements();
    }
}