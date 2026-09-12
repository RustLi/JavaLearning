package org.lwl.leetcode;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

/**
 * LeetCode 20 - 有效的括号
 * <p>
 * 题目：给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s，判断字符串是否有效。
 * 有效字符串需满足：
 * 1. 左括号必须用相同类型的右括号闭合；
 * 2. 左括号必须以正确的顺序闭合；
 * 3. 每个右括号都有一个对应的相同类型的左括号。
 * <p>
 * 核心思路：栈（后进先出）
 * "正确的顺序闭合"即"后开先闭"，正好是栈的 LIFO 特性：
 * - 遇到左括号：压栈，等待将来被闭合；
 * - 遇到右括号：栈顶就是最内层尚未闭合的左括号，检查二者是否配对；
 * 栈空说明右括号落单，类型不符说明顺序或类型错误，均直接判负。
 * <p>
 * 时间复杂度 O(n)：每个字符最多入栈、出栈各一次；
 * 空间复杂度 O(n)：最坏情况（如 "(((((" ）栈中元素个数与字符串长度同阶。
 */
public class ValidParentheses {

    /**
     * 栈 + 哈希表映射
     */
    public boolean isValid(String s) {
        // 剪枝：括号成对出现，长度为奇数必然无效
        if (s == null || s.length() % 2 != 0) {
            return false;
        }

        // 右括号 -> 左括号 的映射
        Map<Character, Character> pairs = new HashMap<>();
        pairs.put(')', '(');
        pairs.put(']', '[');
        pairs.put('}', '{');

        Deque<Character> stack = new ArrayDeque<>();

        for (char c : s.toCharArray()) {
            if (pairs.containsKey(c)) {
                // 右括号：栈顶必须是对应的左括号
                // 栈空 → 右括号落单；栈顶不符 → 类型或顺序错误
                if (stack.isEmpty() || stack.peek() != pairs.get(c)) {
                    return false;
                }
                stack.pop(); // 配对成功，最内层左括号出栈
            } else {
                // 左括号：压栈，等待被闭合
                stack.push(c);
            }
        }

        // 栈空说明所有左括号都被正确闭合，否则存在多余的左括号
        return stack.isEmpty();
    }

    /**
     * 纯数组模拟栈 —— 省去装箱开销，常数更小（面试手写推荐）
     */
    public boolean isValidByArray(String s) {
        if (s == null || s.length() % 2 != 0) {
            return false;
        }

        char[] stack = new char[s.length()];
        int top = -1;

        for (char c : s.toCharArray()) {
            switch (c) {
                case '(':
                case '[':
                case '{':
                    stack[++top] = c;
                    break;
                case ')':
                    if (top < 0 || stack[top--] != '(') return false;
                    break;
                case ']':
                    if (top < 0 || stack[top--] != '[') return false;
                    break;
                case '}':
                    if (top < 0 || stack[top--] != '{') return false;
                    break;
                default:
                    return false;
            }
        }

        return top == -1;
    }

    public static void main(String[] args) {
        ValidParentheses solution = new ValidParentheses();

        String[] cases = {
                "()",           // true
                "()[]{}",       // true
                "(]",           // false —— 类型不匹配
                "([)]",         // false —— 顺序错误
                "{[]}",         // true  —— 嵌套正确
                "([{}])",       // true  —— 多层嵌套
                "(",            // false —— 左括号多余
                ")",            // false —— 右括号落单
                "",             // true  —— 空串
                "(((((",        // false —— 全部左括号
        };

        for (String s : cases) {
            System.out.printf("s = %-8s -> isValid = %-5s | isValidByArray = %-5s%n",
                    "\"" + s + "\"",
                    solution.isValid(s),
                    solution.isValidByArray(s));
        }
    }
}
