package algorithms.strings;
/**
 * @date: 2020/3/29
 * @author: lwl
 * @description: 字符串是否是回文
 *    12321  回文
 */
public class Palindrome {

    public static void main(String[] args) {
        System.out.println(isPalindrome("1232122"));
    }

    private static boolean isPalindrome(String s)
    {
        int N = s.length();
        for (int i = 0; i < N/2; i++)
            if (s.charAt(i) != s.charAt(N-1-i))
                return false;
        return true;
    }
}
