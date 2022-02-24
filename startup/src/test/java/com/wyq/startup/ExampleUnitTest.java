package com.wyq.startup;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        int[] nums = {1,2,3,4,5,6,7,8};
        System.out.print("value==="+getvalue(nums, 5) + "\n");
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
}