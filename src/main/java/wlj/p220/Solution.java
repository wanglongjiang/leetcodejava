package wlj.p220;

/**
 * 220. 存在重复元素 III 给你一个整数数组 nums 和两个整数 k 和 t 。请你判断是否存在两个下标 i 和 j，使得 abs(nums[i]
 * - nums[j]) <= t ， 同时又满足 abs(i - j) <= k 。 如果存在则返回 true，不存在返回 false。
 */
import java.util.TreeMap;

/**
 * 思路2，treemap 在滑动窗口内查找与nums[i]的差<=t的值<br/>
 * 时间复杂度：O(nlogk) treemap大小为k，每次操作时间复杂度都是O(logk)<br/>
 * 空间复杂度：O(n)
 */
public class Solution {
    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        if (nums.length <= 1) {
            return false;
        }
        TreeMap<Long, Integer> tree = new TreeMap<>();
        tree.put(Long.valueOf(nums[0]), 1);
        int size = 1;
        long diff = t;
        for (int i = 1; i < nums.length; i++) {
            Long floor = tree.floorKey(Long.valueOf(nums[i] + diff));
            if (floor != null && floor >= nums[i] - diff) {
                return true;
            }
            if (size >= k) {// 如果窗口大小超过限制，需要将最旧的元素删除
                int oldCount = tree.get(Long.valueOf(nums[i - k]));
                // 可能有重复的值，如果重复值多于1个，减掉1个，如果只剩下1个，需要删除
                if (oldCount > 1) {
                    tree.put(Long.valueOf(nums[i - k]), oldCount - 1);
                } else {
                    tree.remove(Long.valueOf(nums[i - k]));
                }
            } else {
                size++;
            }
            // 将当前值加入tree，如果有重复的值，将计数+1，否则新增1个
            Integer oldCount = tree.get(Long.valueOf(nums[i]));
            if (oldCount == null) {
                tree.put(Long.valueOf(nums[i]), 1);
            } else {
                tree.put(Long.valueOf(nums[i]), oldCount + 1);
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        int[] arr4 = { 2147483640, 2147483641 };
        System.out.println(s.containsNearbyAlmostDuplicate(arr4, 1, 100));
        int[] arr = { 1, 2, 3, 1 };
        System.out.println(s.containsNearbyAlmostDuplicate(arr, 3, 0));
        int[] arr2 = { 1, 0, 1, 1 };
        System.out.println(s.containsNearbyAlmostDuplicate(arr2, 1, 2));
        int[] arr3 = { 1, 5, 9, 1, 5, 9 };
        System.out.println(s.containsNearbyAlmostDuplicate(arr3, 2, 3));
    }
}
