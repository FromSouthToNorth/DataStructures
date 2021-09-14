package vip.hyzt.DynamicArray;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class CopyTest {

    @Test
    void arraysCopyTest() {
        int[] nums1 = new int[] { 1, 2, 3, 4 };

        // 指定新的长度拷贝 origin array
        int[] num3 = Arrays.copyOf(nums1, 2);
        // [1, 2]
        System.out.println("指定新的长度拷贝 origin array = " + Arrays.toString(num3));
    }

    @Test
    void systemArraycopy() {
        int[] nums1 = new int[] { 1, 2, 3, 4, 9 };
        int[] nums2 = new int[] { 5, 6, 7, 8, 9 };
        // 从源数组中第某个元素下标，拷贝到 target 数组中某个元素下标开始
        System.arraycopy(nums2, 2, nums1, 1, 3);
        System.out.println("Arrays.toString(nums1) = " + Arrays.toString(nums1));
    }

}
