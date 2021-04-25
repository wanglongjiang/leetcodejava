package wlj.p363;

public class Solution {
    public int maxSumSubmatrix(int[][] matrix, int k) {
        int m = matrix.length;
        int n = matrix[0].length;
        int minItems = k / 100 + (k % 100 == 0 ? 0 : 1); // 最少需要的元素数
        // 1、求矩阵的前缀和
        int[][] sums = new int[m + 1][n + 1];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                sums[i + 1][j + 1] = matrix[i][j] + sums[i][j + 1] + sums[i + 1][j] - sums[i][j];
        int diff = Integer.MAX_VALUE;
        // 2、遍历每个坐标，求其与右、下每个坐标形成的矩阵的和与k的差
        for (int i = 1; i < m + 1; i++) {
            if ((m + 1 - i) * (n) < minItems) {
                break;// 矩阵最大大小已不满足最少元素数，退出循环
            }
            for (int j = 1; j < n + 1; j++) {
                if ((m + 1 - i) * (n + 1 - j) < minItems) {
                    break;// 矩阵最大大小已不满足最少元素数，退出循环
                }
                for (int bottom = m; bottom > i - 1; bottom--) {
                    if ((bottom + 1 - i) * (n + 1 - j) < minItems) {
                        break;
                    }
                    for (int right = n; right > j - 1; right--) {
                        if ((bottom + 1 - i) * (right + 1 - j) < minItems) {
                            break;
                        }
                        // 求2个坐标形成的矩阵和
                        int s = sums[i - 1][j - 1] + sums[bottom][right] - sums[i - 1][right] - sums[bottom][j - 1];
                        if (s <= k && k - s < diff) {
                            diff = k - s;
                            if (diff == 0)
                                return k;
                        }
                    }
                }
            }
        }
        return k - diff;
    }
}
