package com.wyq.startup;

import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        int[] nums = {2, 1, 2, 6, 7, 3, 9, 4, 5, 8};
        //threadTest();
        System.out.print("value===" + Arrays.toString(nums) + "\n");
        bubbling(nums);
        System.out.print("value===" + Arrays.toString(nums) + "\n");

        quickSort(nums, 0, nums.length - 1);
    }

    //快排
    private void quickSort(int[] aar, int left, int right) {
        if (left < right) {
            int partitionIndex = partition(aar, left, right);
            quickSort(aar,left,partitionIndex - 1);
            quickSort(aar,partitionIndex + 1,right);
        }
    }

    private int partition(int[] aar, int left, int right) {
        int pivot = left;
        int index = pivot + 1;
        for (int i = index; i <= right; i++) {
            if (aar[i] < aar[pivot]) {
                int temp = aar[i];
                aar[i] = aar[index];
                aar[index] = temp;
                index++;
            }
        }
        int temp = aar[pivot];
        aar[pivot] = aar[index - 1];
        aar[index - 1] = temp;
        return index - 1;
    }


    //冒泡排序
    private void bubbling(int[] nums) {
        int temp;
        int flags = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = 0; j < nums.length - 1 - i; j++) {
                if (nums[j] > nums[j + 1]) {
                    temp = nums[j];
                    nums[j] = nums[j + 1];
                    nums[j + 1] = temp;
                    flags = 1; //不是有序的，flags设置为1；
                }
            }
            if (flags == 0)
                return;
        }
    }

    //快慢指针查找有序数组重复元素
    int removeDuplicates(int[] nums) {
        int n = nums.length;
        if (n == 0) return 0;
        int slow = 0, fast = 1;
        while (fast < n) {
            if (nums[fast] != nums[slow]) {
                slow++;
                // 维护 nums[0..slow] 无重复
                nums[slow] = nums[fast];
            }
            fast++;
        }
        // 长度为索引 + 1
        return slow + 1;
    }

    private int getvalue(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;              //7
        while (left <= right) {
            int mid = left + (right - left) / 2;  //3
            if (nums[mid] == target) {            //4
                return mid;
            } else if (nums[mid] < target) {      //
                left = mid + 1;                   //left = 4  [mid + 1, right]
            } else if (nums[mid] > target) {      //[left, mid - 1]
                right = mid - 1;
            }
        }
        return -1;
    }

    private final Lock lock = new ReentrantLock();


    private void threadTest() {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                test();
            }
        }, "线程A");

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                test();
            }
        }, "线程B");
        thread1.start();
        thread2.start();
    }

    private void test() {
        try {
            lock.lock();
            System.out.print(Thread.currentThread().getName() + " 获取了锁");
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.print(Thread.currentThread().getName() + " 释放了锁");
            lock.unlock();
        }
    }

}