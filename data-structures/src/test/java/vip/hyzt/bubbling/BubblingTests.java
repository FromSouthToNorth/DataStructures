package vip.hyzt.bubbling;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class BubblingTests {

    @Test
    void bubblingTest() {
        int[] nums = new int[]{10, 8, 1, 4, 3, 2, 9, 7, 5, 6};
        int[] bubbling = bubbling(nums);
        System.out.println("bubbling = " + Arrays.toString(bubbling));
    }

    public static int[] bubbling(int[] array) {
        // 获取 array 的 长度
        int size = array.length;

        for (int i = size - 1; i > 0; i--) {
            // 内层
            for (int j = 0; j < i; j++) {
                if (array[j] > array[j + 1]) {
                    int time = array[j + 1];
                    array[j + 1] = array[j];
                    array[j] = time;
                }
            }
        }
        return array;
    }

}
