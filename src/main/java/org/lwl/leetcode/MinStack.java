package org.lwl.leetcode;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.Supplier;

/**
 * LeetCode 155 - 最小栈（提交时将类名改为 MinStack）
 * <p>
 * 题目：设计一个支持 push、pop、top 操作，并能在常数时间内检索到最小元素的栈。
 * - MinStack()            初始化堆栈对象
 * - void push(int value)  将元素 value 推入堆栈
 * - void pop()            删除堆栈顶部的元素
 * - int top()             获取堆栈顶部的元素
 * - int getMin()          获取堆栈中的最小元素
 * <p>
 * 关键理解一：为什么「用一个变量记最小值」不够用。
 * push 时维护 min = Math.min(min, x) 很轻松，问题出在 pop：
 * 若弹出的恰好是当前最小值，min 就必须恢复成「剩余元素中的最小值」，
 * 而那个历史次小值在单变量里已经丢了。
 * 这正是辅助栈存在的全部理由——它把「历史最小值」按栈的时序保存了下来。
 * 换个角度说：栈的 pop 是「时间倒流」操作，普通栈能倒流是因为元素本身按序存着，
 * 而最小值是一个「派生信息」，不会自动倒流，必须显式存档。
 * <p>
 * 关键理解二：辅助栈与数据栈的「同步」与「不同步」。
 * - 同步：每次 push 都往辅助栈压一份「此刻的最小值」，两栈等高，
 *   pop 时一起弹，天然对齐，逻辑最简单，是首选写法。
 * - 不同步：只在新元素「小于等于」当前最小值时才压入辅助栈，
 *   因为最小值没变的那些 push 不产生新的历史信息，压了也是重复。
 *   pop 时只有弹出的值「等于」辅助栈栈顶才弹辅助栈。
 * 此处必须是 <= 而不是 <，否则重复的最小值会出错：见方法二注释中的反例。
 * <p>
 * 关键理解三：getMin 为什么能 O(1)。
 * 「当前最小值」在每次 push 时就已经算好并存在辅助栈顶了，
 * getMin 只是一次 peek，不需要任何遍历——这就是「空间换时间」的典型应用。
 * <p>
 * 时间空间复杂度：三种方案的单次操作均为 O(1)；空间均为 O(n)。
 * 其中差值法只用了一个栈，辅助栈法在最坏情况（严格递减入栈）也是 O(n)，
 * 但在最小值变化不频繁时更省空间。
 * <p>
 * 举一反三：
 * - LeetCode 716「最大栈」把 getMin 换成 getMax，且要求 popMax——辅助栈思路不变，
 *   但 popMax 需要额外结构（如 TreeMap + 双向链表）才能做到 O(log n)。
 * - 本题是「单调结构」的入门：LeetCode 239「滑动窗口最大值」用单调队列，
 *   与其同源的「单调栈」系列（如 LeetCode 739 每日温度、84 柱状图中最大矩形）
 *   都在做同一件事——用额外结构把「历史最值信息」保存下来，避免重复扫描。
 * - 反过来看，凡是需要「在动态增删中 O(1) 查询极值」的场景（如滑动窗口、
 *   实时监控指标），本质都是本题的推广。
 */
public class MinStack implements MinStackOps {

    // ============ 方法一：双栈同步（推荐，最直观最不易错） ============

    /** 数据栈：存真正的元素 */
    private final Deque<Integer> dataStack = new ArrayDeque<>();

    /** 辅助栈：与数据栈等高，第 i 个位置存的是「数据栈前 i 个元素中的最小值」 */
    private final Deque<Integer> minStack = new ArrayDeque<>();

    public MinStack() {
        // 无需初始化，两个 Deque 在字段声明处已创建
    }

    /**
     * des: 压入元素，同时把「压入后的最小值」也压入辅助栈，保持两栈等高
     */
    @Override
    public void push(int value) {
        dataStack.push(value);
        // 空栈时辅助栈没有栈顶可比，此时最小值就是 value 自己
        int currentMin = minStack.isEmpty() ? value : Math.min(minStack.peek(), value);
        minStack.push(currentMin);
    }

    /**
     * des: 两栈必须同步弹出，否则辅助栈会残留过期的最小值
     */
    @Override
    public void pop() {
        requireNotEmpty("pop");
        dataStack.pop();
        // 关键：这里不能省略。若只弹数据栈，辅助栈顶会停留在「已不存在的元素」的最小值上
        minStack.pop();
    }

    /**
     * des: 返回栈顶元素（不移除）
     */
    @Override
    public int top() {
        requireNotEmpty("top");
        return dataStack.peek();
    }

    /**
     * des: 辅助栈顶即当前最小值，一次 peek 完成
     */
    @Override
    public int getMin() {
        requireNotEmpty("getMin");
        return minStack.peek();
    }

    /** des: 空栈时统一抛异常，避免返回 null 拆箱成 NullPointerException 这种难排查的报错 */
    private void requireNotEmpty(String operation) {
        if (dataStack.isEmpty()) {
            throw new IllegalStateException("栈为空，无法执行 " + operation);
        }
    }

    // ============ 方法二：辅助栈按需压入（空间更省） ============

    /**
     * des: 辅助栈不再是每次都压，而是「只在出现新的最小值时」才压
     * <p>
     * 与方法一的差别：方法一的辅助栈里会有大量重复的最小值（如 push 的全是递增序列时），
     * 本方法把它们省掉了，因此辅助栈通常比数据栈矮，空间更省。
     * <p>
     * 反例说明为什么必须用 <= 而不是 <：设操作为 push(0)、push(0)、pop()、getMin()。
     * - 若用 <：第一个 0 压入辅助栈，第二个 0 因「不严格小于」被跳过，辅助栈只有 [0]；
     *   随后 pop 掉第二个 0 时，判断「弹出的值 0 等于辅助栈顶 0」成立，辅助栈被弹空；
     *   但数据栈里还剩第一个 0，此时 getMin 直接报错——明明栈非空却查不到最小值。
     * - 用 <=：两个 0 都会进辅助栈，弹出时一一对应，才不会提前弹空。
     */
    static class MinStackByLazyAux implements MinStackOps {

        private final Deque<Integer> dataStack = new ArrayDeque<>();
        private final Deque<Integer> minStack = new ArrayDeque<>();

        @Override
        public void push(int value) {
            dataStack.push(value);
            // 只有「新元素 <= 当前最小值」才需要记录，因为只有这种情况才产生了新的最小值历史
            if (minStack.isEmpty() || value <= minStack.peek()) {
                minStack.push(value);
            }
        }

        @Override
        public void pop() {
            if (dataStack.isEmpty()) {
                throw new IllegalStateException("栈为空，无法执行 pop");
            }
            int removed = dataStack.pop();
            // 弹出的正是当前最小值时，辅助栈顶才需要同步弹出
            if (!minStack.isEmpty() && removed == minStack.peek()) {
                minStack.pop();
            }
        }

        @Override
        public int top() {
            if (dataStack.isEmpty()) {
                throw new IllegalStateException("栈为空，无法执行 top");
            }
            return dataStack.peek();
        }

        @Override
        public int getMin() {
            if (minStack.isEmpty()) {
                throw new IllegalStateException("栈为空，无法执行 getMin");
            }
            return minStack.peek();
        }
    }

    // ============ 方法三：差值法单栈（进阶技巧，把两个栈压缩成一个） ============

    /**
     * des: 栈里不存元素本身，改存「该元素与压入前最小值的差」，最小值单独用变量记
     * <p>
     * 核心等式：diff = value - minPrev。
     * - diff < 0 说明新元素比原最小值还小，它就是新的最小值，此时「原值 == 当前 min」；
     * - diff >= 0 说明原最小值没变，此时「原值 == 当前 min + diff」。
     * pop 时若 diff < 0，说明弹出的正是当前最小值，需要把 min 还原为压入它之前的最小值：
     * 由 diff = value - minPrev 且 min == value，得 minPrev = min - diff。
     * <p>
     * 为什么栈里要用 long 而不是 int：diff 最大可达 Integer.MAX_VALUE - Integer.MIN_VALUE，
     * 接近 2^32，远超 int 的表示范围（用例 5 专门验证了这一点）。
     * 这是本方案唯一但最致命的坑，写业务代码时同样要警惕「两个 int 相减」的溢出。
     */
    static class MinStackByDiff implements MinStackOps {

        /** 存差值，用 Long 避免 x - min 溢出 */
        private final Deque<Long> diffStack = new ArrayDeque<>();

        /** 当前栈中所有元素的最小值 */
        private long min;

        @Override
        public void push(int value) {
            if (diffStack.isEmpty()) {
                // 空栈：最小值就是自己，差值定义为 0
                diffStack.push(0L);
                min = value;
                return;
            }
            long diff = (long) value - min;
            diffStack.push(diff);
            // 差值为负 -> 新元素更小，刷新最小值
            if (diff < 0) {
                min = value;
            }
        }

        @Override
        public void pop() {
            if (diffStack.isEmpty()) {
                throw new IllegalStateException("栈为空，无法执行 pop");
            }
            long diff = diffStack.pop();
            // 差值为负 -> 弹出的是当前最小值，把 min 还原为「压入它之前的最小值」
            if (diff < 0) {
                min = min - diff;
            }
        }

        @Override
        public int top() {
            if (diffStack.isEmpty()) {
                throw new IllegalStateException("栈为空，无法执行 top");
            }
            long diff = diffStack.peek();
            // 差值为负 -> 栈顶就是当前最小值本身；否则原值 = 当前最小值 + 差值
            return diff < 0 ? (int) min : (int) (min + diff);
        }

        @Override
        public int getMin() {
            if (diffStack.isEmpty()) {
                throw new IllegalStateException("栈为空，无法执行 getMin");
            }
            return (int) min;
        }
    }

    // ============ 测试用例 ============

    public static void main(String[] args) {
        // 同一套用例依次跑三种实现，任何一处行为不一致都会立刻暴露
        checkAll("方法一：双栈同步", MinStack::new);
        checkAll("方法二：辅助栈按需压入", MinStackByLazyAux::new);
        checkAll("方法三：差值法单栈", MinStackByDiff::new);
    }

    /** des: 用同一套用例验证某个实现，工厂负责为每个用例创建全新的栈实例 */
    private static void checkAll(String name, Supplier<MinStackOps> factory) {
        System.out.println("==== " + name + " ====");
        boolean pass = true;

        // ---------- 用例 1：官方示例 ----------
        {
            MinStackOps s = factory.get();
            s.push(-2);
            s.push(0);
            s.push(-3);
            pass &= expect("push(-2,0,-3) 后 getMin", s.getMin(), -3);
            s.pop();
            pass &= expect("pop 后 top", s.top(), 0);
            pass &= expect("pop 后 getMin", s.getMin(), -2);
        }

        // ---------- 用例 2：重复的最小值，专治「用 < 代替 <=」的写法 ----------
        {
            MinStackOps s = factory.get();
            s.push(0);
            s.push(1);
            s.push(0);
            pass &= expect("push(0,1,0) 后 getMin", s.getMin(), 0);
            s.pop();
            pass &= expect("弹出栈顶的 0 后 getMin", s.getMin(), 0);
            s.pop();
            pass &= expect("再弹出 1 后 getMin", s.getMin(), 0);
            s.pop();
            // 弹空后重新入栈，验证内部状态没有残留（差值法尤其容易在这里出问题）
            s.push(7);
            pass &= expect("弹空后 push(7) 的 top", s.top(), 7);
            pass &= expect("弹空后 push(7) 的 getMin", s.getMin(), 7);
        }

        // ---------- 用例 3：严格递减入栈，最小值被逐个弹出（辅助栈最深的场景） ----------
        {
            MinStackOps s = factory.get();
            s.push(3);
            s.push(2);
            s.push(1);
            pass &= expect("push(3,2,1) 后 top", s.top(), 1);
            pass &= expect("push(3,2,1) 后 getMin", s.getMin(), 1);
            s.pop();
            pass &= expect("弹出 1 后 getMin", s.getMin(), 2);
            s.pop();
            pass &= expect("弹出 2 后 getMin", s.getMin(), 3);
            pass &= expect("弹出 2 后 top", s.top(), 3);
        }

        // ---------- 用例 4：最小值先入栈且之后不再变化（辅助栈最浅的场景） ----------
        {
            MinStackOps s = factory.get();
            s.push(-5);
            s.push(0);
            s.push(9);
            pass &= expect("push(-5,0,9) 后 getMin", s.getMin(), -5);
            s.pop();
            s.pop();
            pass &= expect("弹出 9 和 0 后 getMin", s.getMin(), -5);
            pass &= expect("弹出 9 和 0 后 top", s.top(), -5);
        }

        // ---------- 用例 5：Integer 边界值，差值法在此处最容易溢出 ----------
        {
            MinStackOps s = factory.get();
            s.push(Integer.MAX_VALUE);
            s.push(Integer.MIN_VALUE);
            s.push(0);
            pass &= expect("push(MAX,MIN,0) 后 getMin", s.getMin(), Integer.MIN_VALUE);
            pass &= expect("push(MAX,MIN,0) 后 top", s.top(), 0);
            s.pop();
            s.pop();
            pass &= expect("弹出 0 与 MIN 后 top", s.top(), Integer.MAX_VALUE);
            pass &= expect("弹出 0 与 MIN 后 getMin", s.getMin(), Integer.MAX_VALUE);
        }

        // ---------- 用例 6：空栈操作应当显式报错，而不是静默返回脏数据 ----------
        {
            MinStackOps s = factory.get();
            pass &= expectEmptyThrows("空栈 getMin", s::getMin);
            pass &= expectEmptyThrows("空栈 pop", s::pop);
            pass &= expectEmptyThrows("空栈 top", s::top);
        }

        System.out.println(pass ? "  => 全部通过" : "  => 存在失败");
        System.out.println();
    }

    /** des: 比对单个返回值并打印结果 */
    private static boolean expect(String label, int actual, int expected) {
        boolean ok = actual == expected;
        System.out.printf("  %-26s 实际=%-12d 期望=%-12d %s%n", label, actual, expected, ok ? "✅" : "❌");
        return ok;
    }

    /** des: 验证空栈操作会抛出异常，而非返回错误值 */
    private static boolean expectEmptyThrows(String label, Runnable action) {
        try {
            action.run();
            System.out.printf("  %-26s 未抛异常  ❌%n", label);
            return false;
        } catch (IllegalStateException e) {
            System.out.printf("  %-26s 抛出「%s」  ✅%n", label, e.getMessage());
            return true;
        }
    }
}

/**
 * des: 三个实现的公共接口，抽象出来就能「一套用例跑三种实现」
 * <p>
 * 之所以放在顶层而不是嵌套在 MinStack 内部：类的 implements 子句属于「类声明」而非「类体」，
 * 看不到自己类体里声明的成员类型，写成内部接口会直接编译报错。
 */
interface MinStackOps {
    void push(int value);

    void pop();

    int top();

    int getMin();
}

