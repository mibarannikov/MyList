package com.example;

import java.util.Arrays;
import java.util.Comparator;

public class MyArrayList<E> implements MyList<E> {
    private static final int DEF_CAPACITY = 10;

    private Object[] elements;

    /**
     * The size of the ArrayList (the number of elements it contains).
     *
     * @serial
     */
    private int size;

    /**
     * Создает пустой массив с начальной емкостью по умолчанию (5).
     */
    public MyArrayList() {
        elements = new Object[DEF_CAPACITY];
    }

    /**
     * Создает пустой массив с указанной начальной емкостью.
     *
     * @param capacity начальная емкость массива
     */
    public MyArrayList(int capacity) {
        elements = new Object[capacity];
    }

    private static final int Start_CAPACITY = 5;

    /**
     * Добавляет элемент в конец массива.
     *
     * @param o элемент для добавления
     * @return {@code true}, если элемент успешно добавлен
     */
    @Override
    public boolean add(E o) {
        if (elements.length == size()) {
            elements = grow();
        }
        elements[size] = o;
        size++;
        return true;
    }

    /**
     * Вставляет элемент по указанному индексу в массив.
     *
     * @param index   индекс для вставки элемента
     * @param element элемент для вставки
     */
    @Override
    public void add(int index, E element) {
        while (index >= elements.length) {
            elements = grow();
        }
        Object[] newArr = new Object[elements.length + 1];
        System.arraycopy(elements, 0, newArr, 0, elements.length);
        newArr[index] = element;
        System.arraycopy(elements, index, newArr, index + 1, elements.length - index);
        elements = newArr;
        if (size - 1 > index) {
            size++;
        } else {
            size = index + 1;
        }
    }

    /**
     * Возвращает элемент по указанному индексу.
     *
     * @param index индекс элемента
     * @return элемент по указанному индексу
     * @throws IndexOutOfBoundsException если индекс выходит за пределы допустимого диапазона
     */
    @Override
    public E get(int index) {
        if (size <= index) {
            throw new IndexOutOfBoundsException();
        }
        return (E) elements[index];
    }

    /**
     * Очищает массив, устанавливая его размер в ноль.
     */
    @Override
    public void clear() {
        elements = new Object[DEF_CAPACITY];
        size = 0;

    }

    /**
     * Сортирует массив с использованием заданного компаратора.
     *
     * @param c компаратор для сравнения элементов массива
     */
    @Override
    public void sort(Comparator c) {
        Arrays.sort((E[]) elements, 0, size, c);

    }

    /**
     * Возвращает текущий размер массива.
     *
     * @return текущий размер массива
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Увеличивает емкость массива, создавая новый массив с увеличенной емкостью и
     * копируя в него элементы из старого массива.
     *
     * @return новый массив с увеличенной емкостью
     */
    private Object[] grow() {
        Object[] newArr = new Object[elements.length + elements.length / 2];
        System.arraycopy(elements, 0, newArr, 0, elements.length);
        return newArr;
    }

}
