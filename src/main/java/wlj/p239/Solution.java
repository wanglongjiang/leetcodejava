package wlj.p239;

import java.util.TreeMap;

/**
 * 滑动窗口最大值 <br/>
 * 思路: 红黑树。<br/>
 * 使用treemap保存滑动窗口内的值，因为元素有可能重复，所以tree中保存元素的计数，只有计数小于1才会删除<br/>
 * 滑动窗口从左向右滑动,每移动一步,在treemap中添加、删除元素。将最大元素添加到结果中。<br/>
 * 时间复杂度：O(nlogk)， 空间复杂度:O(logk)
 */
public class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        int[] ans = new int[nums.length - k + 1];
        TreeMap<Integer, Integer> tree = new TreeMap<>(); // treemap中存放的key为nums[i]，value为该值的计数
        // 将第1个窗口内的元素添加到tree中
        for (int i = 0; i < k; i++) {
            if (tree.containsKey(nums[i])) {
                tree.put(nums[i], tree.get(nums[i]) + 1);
            } else {
                tree.put(nums[i], 1);
            }
        }
        ans[0] = tree.lastKey();
        // 移动滑动窗口，在移动过程中中添加、删除元素，提取最大值放入结果数组
        for (int i = k; i < nums.length; i++) {
            // 添加新元素
            if (tree.containsKey(nums[i])) {
                tree.put(nums[i], tree.get(nums[i]) + 1);
            } else {
                tree.put(nums[i], 1);
            }
            // 删除旧元素
            int oldCount = tree.get(nums[i - k]);
            if (oldCount == 1) {
                tree.remove(nums[i - k]);
            } else {
                tree.put(nums[i - k], oldCount - 1);
            }
            ans[i - k + 1] = tree.lastKey();
        }
        return ans;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        int[] arr = { 1, 3, -1, -3, 5, 3, 6, 7 };
        int[] res = s.maxSlidingWindow(arr, 3);
        for (int i : res) {
            System.out.print(i);
            System.out.print(',');
        }
    }
}
