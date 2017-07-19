package core;

import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Created by Cube27 on 19.07.2017.
 */
public class MyList<T> implements List<T> {

    private T[] m;

    private int size;

    public MyList(){
        m = (T[]) new Object[1];
        size = 0;
    }
    public MyList(List<T> ts) {
        m = (T[]) ts.toArray();
        size = m.length;
    }


    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public boolean contains(final Object o) {
        for (int i = 0; i < size; i++) {
            if (m[i].equals(o)) return true;
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new ElementsIterator();
    }

    @Override
    public Object[] toArray() {
        final T[] newM = (T[]) new Object[this.size()];
        System.arraycopy(m, 0, newM, 0, this.size());
        return newM;
    }

    @Override
    public <T1> T1[] toArray(final T1[] a) {
        if (a.length < size) return (T1[]) Arrays.copyOf(m, size, a.getClass());

        System.arraycopy(m, 0, a, 0, size);

        if (a.length > size) a[size] = null;

        return a;
    }

    @Override
    public boolean add(final T t) {
        if (m.length == size) {
            final T[] oldM = m;
            m = (T[]) new Object[this.size() * 2];
            System.arraycopy(oldM, 0, m, 0, oldM.length);
        }
        m[size++] = t;
        return true;
    }
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for(int i=0;i<m.length;i++){
            sb.append(m[i]);
            if(i<m.length-1){
                sb.append(", ");
            }

        }
        sb.append("]");
        return sb.toString();
    }
    @Override
    public boolean remove(final Object o) {
        for (int i = 0; i < size(); i++) {
            if (m[i].equals(o)) {
                this.remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(final Collection<?> c) {
        for (final Object item : c) {
            if (!this.contains(item)) return false;
        }
        return true;
    }

    @Override
    public boolean addAll(final Collection<? extends T> c) {
        for (final T item : c) {
            add(item);
        }
        return true;
    }

    @Override
    public boolean removeAll(final Collection<?> c) {
        for (final Object item : c) {
            remove(item);
        }
        return true;
    }

    @Override
    public boolean retainAll(final Collection<?> c) {
        for (final Object item : this) {
            if (!c.contains(item)) this.remove(item);
        }
        return true;
    }

    @Override
    public void clear() {
        m = (T[]) new Object[1];
        size = 0;
    }

    @Override
    public T remove(final int index) {
        final T element = m[index];
        if (index != this.size() - 1) {
            final T[] newM = (T[]) new Object[size()-1];

            System.arraycopy(m, index + 1, m, index, this.size() - index - 1);
            System.arraycopy(m, 0, newM, 0, this.size()- 1);
            m=newM;
        }else{
            final T[] newM = (T[]) new Object[size()-1];

            System.arraycopy(m, 0, newM, 0, this.size()- 1);
            m=newM;
        }
        size--;

        return element;
    }


    @Override
    public ListIterator listIterator() {
        return new ElementsIterator(0);
    }

    @Override
    public ListIterator listIterator(final int index) {
        return new ElementsIterator(index);
    }

    @NotNull
    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        if(toIndex>this.size()||fromIndex<0){
            throw new IndexOutOfBoundsException();
        }
        int newSize = toIndex-fromIndex;
        final T[] newM = (T[]) new Object[newSize];
        System.arraycopy(m, fromIndex, newM, 0, newSize);
        return  new MyList<T>(Arrays.asList(newM));

    }

    @Override
    public int lastIndexOf(final Object target) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(final Object target) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(final int index, final T element) {
        if (index > size || index < 0) throw new IndexOutOfBoundsException();

        if (size == 0 || index == size) {
            add(element);

        } else if (m.length == size) {

            final T[] tempM = m;
            m = (T[]) new Object[this.size() * 2];

            System.arraycopy(tempM, 0, m, 0, index);
            System.arraycopy(tempM, index, m, index + 1, size() - index);

            set(index, element);
            size++;

        } else {

            final T[] tempM = m;
            System.arraycopy(tempM, 0, m, 0, index + 1);
            System.arraycopy(tempM, index, m, index + 1, size() - index);
            set(index, element);
            size++;

        }
    }

    @Override
    public boolean addAll(final int index, final Collection elements) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T set(final int index, final T element) {
        m[index] = element;
        return element;
    }

    @Override
    public T get(final int index) {
        return m[index];
    }

    private class ElementsIterator implements ListIterator<T> {

        private static final int LAST_IS_NOT_SET = -1;
        private int index;
        private int lastIndex = LAST_IS_NOT_SET;
        private int offset = 0;

        public ElementsIterator() {
            this(0);
        }

        public ElementsIterator(final int index) {
            //
            this.index = index;

            //
        }

        @Override
        public boolean hasNext() {
            return MyList.this.size() > index;
        }

        @Override
        public T next() {
            if (!hasNext())
                throw new NoSuchElementException();
            lastIndex = index;
            return MyList.this.m[index++];
        }

        @Override

        public void add(final T element) {
            //
            MyList.this.add(index, element);
            index++;
            //
        }

        @Override
        public void set(final T element) {
            //

            if (lastIndex == -1)
                throw new IllegalStateException();
            try {
                MyList.this.remove(lastIndex);
                MyList.this.add(lastIndex, element);
            } catch (IndexOutOfBoundsException e) {
                throw new UnsupportedOperationException();
            }
            //
        }

        @Override
        public int previousIndex() {
            //
            //Returns the index of the element that would be returned by a subsequent call to previous().
            // (Returns -1 if the list iterator is at the beginning of the list.)
            int prevIndex = index - 1;
            return prevIndex;
            //
        }

        @Override
        public int nextIndex() {
            //
            int nexIndex = index;
            return nexIndex;
            //
        }

        @Override
        public boolean hasPrevious() {
            //
            return previousIndex() >= 0;

            //
        }

        @Override
        public T previous() {
            //
            if (hasPrevious()) {
                offset = -1;
                return MyList.this.m[--index];

            } else
                throw new NoSuchElementException();
            //
        }

        @Override
        public void remove() {
            if (lastIndex == LAST_IS_NOT_SET) throw new IllegalStateException();
            MyList.this.remove(lastIndex);
            index--;
            lastIndex = LAST_IS_NOT_SET;
        }

    }
}