package vip.hyzt.DynamicArray;

import java.io.Serializable;
import java.util.*;

/**
 * 动态数组
 * @author Josh Bloch, Neal Gafter
 */
public class DynamicArray<E> extends AbstractList<E> implements List<E>, RandomAccess, Cloneable, Serializable {

    private static final long serialVersionUID = 8683452581122892189L;

    /**
     * 默认初始容量
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * 用于空实例的共享空数组实例
     */
    private static final Object[] EMPTY_ELEMENTDATA = {};

    /**
     * 用于默认大小的空实例的共享空数组实例。我们将其与 EMPTY_ELEMENTDATA 区分开来，以了解添加第一个元素时要膨胀多少。
     */
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};

    /**
     * DynamicArray 的元素存储在其中的数组缓冲区。
     * DynamicArray 的容量就是这个数组缓冲区的长度。
     * 添加第一个元素时，
     * 任何带有 elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA 的空 ArrayList 都将扩展为 DEFAULT_CAPACITY。
     */
    transient Object[] elementData; // 非私有以简化嵌套类访问

    /**
     * DynamicArray 的大小（它包含的元素数）。
     */
    private int size;

    /**
     * 构造一个具有指定初始容量的空列表。
     * @param initialCapacity – 列表的初始容量
     * @throws IllegalArgumentException - 如果指定的初始容量为负
     */
    public DynamicArray(int initialCapacity) {
        // 当指定容量为正创建一个数组大小为指定容量，赋值给 elementData
        if (initialCapacity > 0) {
            this.elementData = new Object[initialCapacity];
        }
        // 当指定容量为 0，将共享空数组实例赋值给 elementData
        else if (initialCapacity == 0) {
            this.elementData = EMPTY_ELEMENTDATA;
        }
        // 如果指定的初始容量为负
        else {
            throw new IllegalArgumentException("Illegal Capacity: " +
                    initialCapacity);
        }
    }

    /**
     * 构造一个初始容量为 10 的空列表。
     */
    public DynamicArray() {
        this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }

    /**
     * 构造一个包含指定元素的列表
     * 集合，按照集合返回的顺序
     * 迭代器。
     * @param c 其元素将被放入此列表的集合
     */
    public DynamicArray(Collection<? extends E> c) {
        Object[] a = c.toArray();
        if ((size = a.length) != 0) {
            if (c.getClass() == DynamicArray.class) {
                elementData = a;
            }
            else {
                elementData = Arrays.copyOf(a, size, Object[].class);
            }
        }
        else {
            // 替换为空数组.
            elementData = EMPTY_ELEMENTDATA;
        }
    }

    /**
     * 将此 DynamicArray 实例的容量修剪为列表的当前大小。
     * 应用程序可以使用此操作来最小化 DynamicArray 实例的存储空间。
     */
    public void trimToSize() {
        modCount++;
        if (size < elementData.length) {
            elementData = (size == 0)
                    ? EMPTY_ELEMENTDATA
                    : Arrays.copyOf(elementData, size);
        }
    }

    /**
     * 增加此 <tt>DynamicArray</tt> 实例的容量，如果
     * 必要的，以确保它至少可以容纳元素的数量
     * 由最小容量参数指定。
     * @param minCapacity 所需的最小容量
     */
    public void ensureCapacity(int minCapacity) {
        int minExpand = (elementData != DEFAULTCAPACITY_EMPTY_ELEMENTDATA)
                // 任何大小，如果不是默认元素表
                ? 0
                // 大于默认空表的默认值。它已经
                // 应该是默认大小。
                : DEFAULT_CAPACITY;
        if (minCapacity > minExpand) {
            ensureExplicitCapacity(minCapacity);
        }
    }

    private static int calculateCapacity(Object[] elementData, int minCapacity) {
        if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
            return Math.max(DEFAULT_CAPACITY, minCapacity);
        }
        return minCapacity;
    }

    private void ensureCapacityInternal(int minCapacity) {
        ensureExplicitCapacity(calculateCapacity(elementData, minCapacity));
    }

    private void ensureExplicitCapacity(int minCapacity) {
        modCount++;

        // 溢出意识代码
        if (minCapacity - elementData.length > 0) {
            grow(minCapacity);
        }
    }

    /**
     * 要分配的数组的最大大小。
     * 一些 VM 在数组中保留一些头字。
     * 尝试分配更大的数组可能会导致
     * OutOfMemoryError：请求的数组大小超过 VM 限制
     */
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    /**
     * 增加容量以确保它至少可以容纳
     * 由最小容量参数指定的元素数。
     * @param minCapacity 所需的最小容量
     */
    private void grow(int minCapacity) {
        // 溢出意识代码
        int oldCapacity = elementData.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (newCapacity - minCapacity < 0) {
            newCapacity = minCapacity;
        }
        if (newCapacity - MAX_ARRAY_SIZE > 0) {
            newCapacity = hugeCapacity(minCapacity);
        }
        // minCapacity 通常接近 size，所以这是一个胜利：
        elementData = Arrays.copyOf(elementData, newCapacity);
    }

    private static int hugeCapacity(int minCapacity) {
        // 溢出
        if (minCapacity < 0) {
            throw new OutOfMemoryError();
        }
        return (minCapacity > MAX_ARRAY_SIZE) ?
                Integer.MAX_VALUE :
                MAX_ARRAY_SIZE;
    }


    /**
     * 返回此列表中的元素数。
     * @return 此列表中的元素数。
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * 如果此列表不包含任何元素，则返回 <tt>true</tt>。
     * @return <tt>true</tt> 如果此列表不包含任何元素
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 如果此列表包含指定的元素，则返回 <tt>true</tt>。
     * 更正式地，当且仅当此列表包含时返回 <tt>true</tt>
     * 至少一个元素 <tt>e</tt> 使得
     * <tt>(o==null ? e==null : o.equals(e))</tt>。
     * @param o 要测试其在此列表中是否存在的元素
     * @return <tt>true</tt> 如果此列表包含指定的元素
     */
    @Override
    public boolean contains(Object o) {
        return indexOf(0) >= 0;
    }

    /**
     * 返回指定元素第一次出现的索引
     * 在此列表中，如果此列表不包含该元素，则为 -1。
     * 更正式地，返回最低索引 <tt>i</tt> 使得
     * <tt>(o==null ? get(i)==null : o.equals(get(i)))</tt>,
     * 或者 -1 如果没有这样的索引。
     */
    @Override
    public int indexOf(Object o) {
        if (o == null) {
            for (int i = 0; i < size; i++) {
                if (elementData[i] == null) {
                    return i;
                }
            }
        }
        else {
            for (int i = 0; i < size; i++) {
                if (o.equals(elementData[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * 返回指定元素最后一次出现的索引
     * 在此列表中，如果此列表不包含该元素，则为 -1。
     * 更正式地，返回最高索引 <tt>i</tt> 使得
     */
    @Override
    public int lastIndexOf(Object o) {
        if (o == null) {
            for (int i = size - 1; i >= 0; i--) {
                if (elementData[i] == null) {
                    return i;
                }
            }
        }
        else {
            for (int i = size - 1; i >= 0; i--) {
                if (o.equals(elementData[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * 返回此 <tt>ArrayList</tt> 实例的浅拷贝。 （这
     * 元素本身不会被复制。）
     * @return 这个 <tt>ArrayList</tt> 实例的克隆
     */
    @Override
    public Object clone() {
        try {
            DynamicArray<?> v = (DynamicArray<?>) super.clone();
            v.elementData = Arrays.copyOf(elementData, size);
            v.modCount = 0;
            return v;
        }
        catch (CloneNotSupportedException ex) {
            // 这不应该发生，因为我们是可克隆的
            throw new InternalError(ex);
        }
    }

    /**
     * 返回一个包含此列表中所有元素的数组
     *  以适当的顺序（从第一个元素到最后一个元素）。
     *  <p>返回的数组将是“安全的”，因为没有对它的引用
     *  由这个列表维护。 （换句话说，这个方法必须分配
     *  一个新数组）。因此调用者可以自由地修改返回的数组。
     *  <p>这个方法充当了基于数组和基于集合的桥梁
     *  蜜蜂。
     *  @return 包含此列表中所有元素的数组
     *          正确的顺序
     */
    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elementData, size);
    }

    /**
     * 以适当的方式返回包含此列表中所有元素的数组
     * 序列（从第一个元素到最后一个元素）；返回的运行时类型
     * 数组是指定数组的数组。如果列表符合
     * 指定的数组，它在其中返回。否则，一个新数组是
     * 使用指定数组的运行时类型和大小分配
     * 这份清单。
     * <p>如果列表适合指定的数组并有剩余空间
     * （即，数组的元素比列表多），中的元素
     * 紧跟在集合末尾之后的数组被设置为
     * <tt>空</tt>。 （这在确定长度时很有用
     * list <i>only</i> 如果调用者知道列表不包含
     * 任何空元素。）
     * @param a 列表元素所在的数组
     *          存储，如果它足够大；否则，一个新的数组
     *          为此分配了相同的运行时类型。
     * @return 包含列表元素的数组
     * @throws ArrayStoreException 如果指定数组的运行时类型
     *         不是每个元素的运行时类型的超类型
     *         这份清单
     * 如果指定的数组为空，则@throws NullPointerException
     */
    @Override
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
        if (a.length < size) {
            // 创建一个运行时类型的新数组，但我的内容：
            return (T[]) Arrays.copyOf(elementData, size, a.getClass());
        }
        System.arraycopy(elementData, 0, a, 0, size);
        if (a.length > size) {
            a[size] = null;
        }
        return a;
    }

    // 位置访问操作

    @SuppressWarnings("unchecked")
    E elementData(int index) {
        return (E) elementData[index];
    }

    /**
     * 返回此列表中指定位置的元素。
     * @param index 要返回的元素的索引
     * @return 列表中指定位置的元素
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public E get(int index) {
        rangeCheck(index);

        return elementData(index);
    }

    /**
     * 将此列表中指定位置的元素替换为
     * 指定的元素。
     * @param index 要替换的元素的索引
     * @param element 要存储在指定位置的元素
     * @return 之前指定位置的元素
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public E set(int index, E element) {
        rangeCheck(index);

        E oldValue = elementData(index);
        elementData[index] = element;
        return oldValue;
    }

    /**
     * 将指定的元素附加到此列表的末尾。
     * @param e 要附加到此列表的元素
     * @return <tt>true</tt>（由 {@link Collection#add} 指定）
     */
    @Override
    public boolean add(E e) {
        // 增加 modCount ！！
        ensureCapacityInternal(size + 1);
        elementData[size++] = e;
        return true;
    }

    /**
     * 在此指定位置插入指定元素
     * 列表。移动当前在该位置的元素（如果有）和
     * 右边的任何后续元素（在它们的索引上加一）。
     * @param index 要插入指定元素的索引
     * @param element 要插入的元素
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    @Override
    public void add(int index, E element) {
        rangeCheck(index);

        // 增加 modCount ！！
        ensureCapacityInternal(size + 1);
        System.arraycopy(elementData, index, elementData, index + 1,
                size - index);
        elementData[index] = element;
        size++;
    }

    @Override
    public E remove(int index) {
        rangeCheck(index);

        modCount++;
        E oldValue = elementData(index);

        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(elementData, index + 1, elementData, index, numMoved);
        }
        // 明确让GC做它的工作
        elementData[--size] = null;

        return oldValue;
    }

    /**
     * 从此列表中删除第一次出现的指定元素，
     * 如果它存在。如果列表不包含该元素，则为
     * 不变。更正式地，删除具有最低索引的元素
     * <tt>i</tt> 使得
     * <tt>(o==null ? get(i)==null : o.equals(get(i)))</tt>
     * （如果存在这样的元素）。如果此列表，则返回 <tt>true</tt>
     * 包含指定的元素（或等效地，如果此列表
     * 由于调用而更改）。
     * @param o 要从此列表中删除的元素（如果存在）
     * @return <tt>true</tt> 如果此列表包含指定的元素
     */
    @Override
    public boolean remove(Object o) {
        if (o == null) {
            for (int index = 0; index < size; index++) {
                if (elementData[index] == null) {
                    fastRemove(index);
                    return true;
                }
            }
        }
        else {
            for (int index = 0; index < size; index++) {
                if (o.equals(elementData[index])) {
                    fastRemove(index);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 跳过边界检查且不执行的私有删除方法
     * 返回移除的值。
     */
    private void fastRemove(int index) {
        modCount++;
        int numMoved = size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(elementData, index+1, elementData, index, numMoved);
        }
        // 明确让GC做它的工作
        elementData[--size] = null;
    }

    /**
     * 从此列表中删除所有元素。该名单将
     * 此调用返回后为空。
     */
    @Override
    public void clear() {
        modCount++;

        // 明确让GC做它的工作
        for (int i = 0; i < size; i++) {
            elementData[i] = null;
        }
        size = 0;
    }

    /**
     * 将指定集合中的所有元素追加到末尾
     * 这个列表，按照它们返回的顺序
     * 指定集合的​​迭代器。此操作的行为是
     * undefined 如果在操作时修改了指定的集合
     * 正在处理。 （这意味着这个调用的行为是
     * undefined 如果指定的集合是这个列表，并且这个
     * 列表非空。）
     * @param c 包含要添加到此列表的元素的集合
     * @return <tt>true</tt> 如果此列表因调用而更改
     * @throws NullPointerException 如果指定的集合为空
     */
    @Override
    public boolean addAll(Collection<? extends E> c) {
        Object[] a = c.toArray();
        int numNew = a.length;
        // 增加 modCount
        ensureCapacityInternal(size + numNew);
        System.arraycopy(a, 0, elementData, size, numNew);
        size += numNew;
        return numNew != 0;
    }

    /**
     * 将指定集合中的所有元素插入到此
     * 列表，从指定位置开始。移动元素
     * 当前在该位置（如果有）和任何后续元素
     * 右边（增加他们的指数）。新元素会出现
     * 在列表中按照它们返回的顺序
     * 指定集合的迭代器。
     * @param index 插入第一个元素的索引
     *              指定集合
     * @param c 包含要添加到此列表的元素的集合
     * @return <tt>true</tt> 如果此列表因调用而更改
     * @throws IndexOutOfBoundsException {@inheritDoc}
     * @throws NullPointerException 如果指定的集合为空
     */
    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        rangeCheckForAdd(index);

        Object[] a = c.toArray();
        int numNew = a.length;
        ensureCapacityInternal(size + numNew);

        int numMoved = size - index;
        if (numMoved > 0) {
            System.arraycopy(elementData, index, elementData, index + numNew, numMoved);
        }

        System.arraycopy(a, 0, elementData, index, numNew);
        size += numMoved;
        return numNew != 0;
    }

    /**
     * 检查给定的索引是否在范围内。如果不是，则抛出一个适当的
     * 运行时异常。此方法*不*检查索引是否为
     * 否定：它总是在数组访问之前立即使用，
     * 如果 index 为负，则抛出 ArrayIndexOutOfBoundsException 。
     */
    private void rangeCheck(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }

    /**
     * add 和 addAll 使用的 rangeCheck 版本。
     */
    private void rangeCheckForAdd(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }

    /**
     * 构造一个 IndexOutOfBoundsException 详细消息。
     * 在错误处理代码的许多可能重构中，
     * 这种“大纲”在服务器和客户端虚拟机上表现最佳。
     */
    private String outOfBoundsMsg(int index) {
        return "Index: " + index + ", Size: " + size;
    }

}
