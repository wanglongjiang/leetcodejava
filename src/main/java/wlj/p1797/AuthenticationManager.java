package wlj.p1797;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeSet;

/**
 * 思路：哈希和红黑树 generate和renew可以通过哈希表来更新
 * 对于countUnexpiredTokens,要统计>currentTime的时间，因为每次调用的currentTime严格单调递增，所以过期时间均是唯一的
 * 可以通过一个TreeSet来保存所有的过期时间，当调用countUnexpiredTokens时，查询大于currentTime的数据集合大小
 * 时间复杂度：3个操作都是O(logn) 空间复杂度：O(n)
 */
public class AuthenticationManager {
    private int timeToLive;
    private Map<String, Integer> tokenMap = new HashMap<>();
    private TreeSet<Integer> expiredTimes = new TreeSet<>();

    public AuthenticationManager(int timeToLive) {
        this.timeToLive = timeToLive;
    }

    public void generate(String tokenId, int currentTime) {
        int expTime = currentTime + this.timeToLive;
        Integer oldExpTime = tokenMap.put(tokenId, expTime);
        if (oldExpTime != null) {// 如果有旧的过期时间，删除旧过期时间
            expiredTimes.remove(oldExpTime);
        }
        expiredTimes.add(expTime);
    }

    public void renew(String tokenId, int currentTime) {
        if (tokenMap.containsKey(tokenId)) {
            int oldExpTime = tokenMap.get(tokenId);
            if (oldExpTime > currentTime) {// 只有旧过期时间大于当前时间，才进行更新
                int expTime = currentTime + this.timeToLive;
                expiredTimes.remove(oldExpTime);
                expiredTimes.add(expTime);
                tokenMap.put(tokenId, expTime);
            }
        }
    }

    public int countUnexpiredTokens(int currentTime) {
        return expiredTimes.tailSet(currentTime, false).size();
    }

    public static void main(String[] args) {
        AuthenticationManager authenticationManager = new AuthenticationManager(5); // 构造 AuthenticationManager ，设置
                                                                                    // timeToLive = 5 秒。
        authenticationManager.renew("aaa", 1); // 时刻 1 时，没有验证码的 tokenId 为 "aaa" ，没有验证码被更新。
        authenticationManager.generate("aaa", 2); // 时刻 2 时，生成一个 tokenId 为 "aaa" 的新验证码。
        authenticationManager.countUnexpiredTokens(6); // 时刻 6 时，只有 tokenId 为 "aaa" 的验证码未过期，所以返回 1 。
        authenticationManager.generate("bbb", 7); // 时刻 7 时，生成一个 tokenId 为 "bbb" 的新验证码。
        authenticationManager.renew("aaa", 8); // tokenId 为 "aaa" 的验证码在时刻 7 过期，且 8 >= 7 ，所以时刻 8 的renew 操作被忽略，没有验证码被更新。
        authenticationManager.renew("bbb", 10); // tokenId 为 "bbb" 的验证码在时刻 10 没有过期，所以 renew 操作会执行，该 token 将在时刻 15 过期。
        authenticationManager.countUnexpiredTokens(15); // tokenId 为 "bbb" 的验证码在时刻 15 过期，tokenId 为 "aaa" 的验证码在时刻 7
                                                        // 过期，所有验证码均已过期，所以返回 0 。
    }
}
