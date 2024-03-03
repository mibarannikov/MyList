package com.example;

import java.util.Comparator;

public class MyLinkedList<E> implements MyList<E> {

    private int size;

    /**
     * Указатель на первый элемент.
     */
    transient Node<E> first;

    /**
     * Указатель на последний элемент.
     */
    transient Node<E> last;

    /**
     * Добавляет указанный элемент в конец этого списка.
     * Элемент @param e, который будет добавлен в этот список
     *
     * @return {@code true}
     **/
    @Override
    public boolean add(E e) {
        linkLast(e);
        return true;
    }

    /**
     * Вставляет указанный элемент в указанную позицию в этом списке.
     * Смещает элемент, находящийся в данный момент в этой позиции (если есть) и любой
     * последующие элементы справа (добавляет единицу к их индексам).
     *
     * @param index   индекс, в который должен быть вставлен указанный элемент
     * @param element элемент, который нужно вставить
     * @throws IndexOutOfBoundsException
     */
    @Override
    public void add(int index, E element) {
        if (index >= 0 && index <= size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == size)
            linkLast(element);
        else
            linkBefore(element, node(index));
    }

    /**
     * Возвращает элемент в указанной позиции в этом списке.
     *
     * @param index индекс возвращаемого элемента
     * @return элемент в указанной позиции в этом списке
     * @throws IndexOutOfBoundsException
     */

    @Override
    public E get(int index) {
        if (index >= 0 && index <= size) {
            throw new IndexOutOfBoundsException();
        }
        return node(index).item;
    }

    /**
     * Удаляет все элементы из этого списка.
     * Список будет пуст после возврата этого вызова.
     */
    @Override
    public void clear() {
        for (Node<E> x = first; x != null; ) {
            Node<E> next = x.next;
            x.item = null;
            x.next = null;
            x.prev = null;
            x = next;
        }
        first = last = null;
        size = 0;
    }

    /**
     * Сортирует список
     */
    @Override
    public void sort(Comparator<? super E> c) {
       first =  sortList(first, c);
       Node<E> current =first;
       while(current.next !=null){
           current = current.next;
       }
       last = current;
    }

    /**
     * Размер списка
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Вложенный класс, представляющий узел в двусвязном списке.
     *
     * @param <E> тип элементов, хранимых в узле
     */
    private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;

        Node() {};

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    /**
     * Добавляет элемент в конец связанного списка.
     *
     * @param e элемент для добавления в конец списка
     */
    void linkLast(E e) {
        final Node<E> l = last;
        final Node<E> newNode = new Node<>(l, e, null);
        last = newNode;
        if (l == null)
            first = newNode;
        else
            l.next = newNode;
        size++;

    }

    /**
     * Вставляет новый узел с элементом {@code e} перед существующим узлом {@code succ} в связанном списке.
     *
     * @param e    элемент для вставки в список
     * @param succ узел, перед которым нужно вставить новый узел
     * @throws NullPointerException если {@code succ} равен {@code null}
     */
    void linkBefore(E e, Node<E> succ) {
        // assert succ != null;
        final Node<E> pred = succ.prev;
        final Node<E> newNode = new Node<>(pred, e, succ);
        succ.prev = newNode;
        if (pred == null)
            first = newNode;
        else
            pred.next = newNode;
        size++;

    }

    /**
     * Возвращает узел по заданному индексу в связанном списке.
     *
     * @param index индекс узла, который нужно вернуть
     * @return узел по заданному индексу
     * @throws IndexOutOfBoundsException если индекс выходит за пределы допустимого диапазона
     */
    Node<E> node(int index) {
        // assert isElementIndex(index);

        if (index < (size >> 1)) {
            Node<E> x = first;
            for (int i = 0; i < index; i++)
                x = x.next;
            return x;
        } else {
            Node<E> x = last;
            for (int i = size - 1; i > index; i--)
                x = x.prev;
            return x;
        }
    }

    /**
     * Сортирует связанный список с использованием заданного компаратора.
     *
     * @param head начальный узел списка
     * @param c    компаратор для сравнения элементов списка
     * @return узел, представляющий начало отсортированного списка
     */
    public Node<E> sortList(Node<E> head, Comparator<? super E> c) {
        if (head == null || head.next == null) {
            return head;
        }
        Node<E> middle = findMiddle(head);
        Node<E> secondHalf = middle.next;
        middle.next = null;
        secondHalf.prev = null;
        Node<E> left = sortList(head, c);
        Node<E> right = sortList(secondHalf, c);
        return merge(left, right, c);
    }

    /**
     * Находит середину связанного списка.
     *
     * @param head начальный узел списка
     * @return узел, представляющий середину списка
     */
    private Node<E> findMiddle(Node<E> head) {
        Node<E> slow = head;
        Node<E> fast = head.next;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }

    /**
     * Объединяет два отсортированных списка в один отсортированный список с использованием заданного компаратора.
     *
     * @param left  начальный узел левого списка
     * @param right начальный узел правого списка
     * @param c     компаратор для сравнения элементов списка
     * @return узел, представляющий начало отсортированного списка
     */
    private Node<E> merge(Node<E> left, Node<E> right, Comparator<? super E> c) {
        Node<E> dummy = new Node();
        Node<E> current = dummy;

        while (left != null && right != null) {
            if (c.compare(left.item, right.item) > 0) {
                current.next = left;
                left.prev = current;
                left = left.next;
            } else {
                current.next = right;
                right.prev = current;
                right = right.next;
            }
            current = current.next;
        }

        if (left != null) {
            current.next = left;
            left.prev = current;
        }

        if (right != null) {
            current.next = right;
            right.prev = current;
        }

        return dummy.next;
    }

}
