package org.lwl.leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

/**
 * LeetCode 208 - 实现 Trie（前缀树）（提交时将类名改为 Trie）
 * <p>
 * 题目：实现一个前缀树，支持以下操作——
 * - Trie()                     初始化前缀树对象
 * - void insert(String word)   向前缀树中插入字符串 word
 * - boolean search(String word)          字符串 word 在树中（此前插入过）返回 true，否则 false
 * - boolean startsWith(String prefix)    此前插入过的单词中有一个以 prefix 为前缀，返回 true
 * 全部输入仅由小写英文字母组成，单词与前缀的长度均 >= 1。
 * <p>
 * 这道题到底在解决什么问题：它要解决的是「按前缀查词」的效率问题。
 * 假设用最朴素的做法——把所有单词塞进一个 HashSet——那么 search 是很快的，
 * 但 startsWith 只能退化成「遍历集合里每个单词，逐个判断它是否以 prefix 开头」，
 * 单次代价 O(n·k)（n 为单词数，k 为单词长度）。词表一大，
 * 自动补全、拼写检查这类「每敲一个键就要重新查一次」的场景立刻卡住。
 * 前缀树把「共享前缀」这一事实显式地建成了结构：所有以 "app" 开头的单词共用同一条路径，
 * 于是任何一次查询都只需沿字符向下走 k 步，代价 O(k)，与词表里存了多少个单词完全无关。
 * <p>
 * 关键理解一：节点代表的是「前缀」，而不是「字符」。这是读懂全部代码的钥匙。
 * 根节点代表空前缀，每个非根节点代表「从根走到它这条路径所拼出来的那个字符串」。
 * 字符本身由「父节点指向子节点的那条边」来表达——所以本文件的节点类里根本没有 char 字段，
 * 只有一个指向儿子们的容器和一个布尔标记。想通这一点，
 * insert 里「先查子节点是否存在，不存在才新建」就是显然的了：
 * 同一个前缀只应该有一个节点，否则前缀就不再共享、树也就退化成了一堆链表。
 * <p>
 * 关键理解二：search 与 startsWith 的唯一区别，是末尾那个 isEnd 判断。
 * 「路径存在」只说明这个字符串是某些已插入单词的前缀，不说明它自己是个单词。
 * 插入 "apple" 之后，路径 a→p→p→l→e 全程存在，所以 startsWith("app") 为 true；
 * 但 "app" 从未被作为单词插入过，所以 search("app") 必须为 false。
 * 没有 isEnd 标记，这两件事就无法区分——这正是 isEnd 不可省略的原因。
 * 也正因如此，两个方法共用同一段「沿前缀下行」的逻辑（findNode），只在终点判断上分道扬镳。
 * <p>
 * 关键理解三：children 容器怎么选，决定了空间开销与适用范围。
 * - 固定 26 槽数组：下标直接寻址，最快，是本题的标准写法。但每个节点无论用不用都占 26 个引用，
 *   且只支持小写字母（传大写或中文会直接数组越界）。
 * - HashMap：只为真实存在的边分配空间，支持任意字符集，代价是哈希开销带来的更大常数。
 *   字符集大而稀疏时（含大小写、数字、中文）明显更省；本题这种 26 个小写字母的密集场景，
 *   哈希表自身的开销反而让它比数组更费空间。
 * 空间上真正的上界是「所有插入单词的总字符数」，因为共享前缀只存一份：
 * 先插入 "app" 会产生 3 个节点，再插入 "apple" 只需新增 2 个节点（l、e），而不是 5 个。
 * <p>
 * 核心思路：从根出发，insert 时按字符下行、缺边就补节点、在终点打上词尾标记；
 * search / startsWith 同样按字符下行，前者额外要求终点是词尾。
 * <p>
 * 时间复杂度：insert、search、startsWith 均为 O(k)，k 为参数字符串的长度，与已存单词数无关。
 * 对照 HashSet 存全部单词的做法：search 均摊 O(k)（算哈希本身就要扫一遍字符），
 * 但 startsWith 最坏要 O(n·k)。
 * 空间复杂度：O(m)，m 为所有插入单词的字符总数，即节点数的上界；
 * 数组实现下每个节点固定持有 26 个引用，故在「引用个数」这一级别上是 O(26·m)。
 * <p>
 * 举一反三：
 * - LeetCode 211「添加与搜索单词」把查询词里的 '.' 当作任意字母，下行时遇到 '.' 就要分叉尝试，
 *   于是朴素的循环变成回溯——见本文件最后的 searchWithWildcard。
 * - LeetCode 648「单词替换」求最短前缀、LeetCode 212「单词搜索 II」用 Trie 为网格回溯剪枝，
 *   都是「沿前缀走」这一能力的直接延伸。
 * - 工程里更常见的是它的变体：搜索引擎的联想词、输入法词库、IP 路由表的最长前缀匹配
 *   （把 IP 按二进制位建成 Trie）、以及 AC 自动机（在 Trie 上补失配指针做多模式串匹配，
 *   可看作 Trie 与 KMP 的结合）。
 * - 反过来看 Trie 的短板：它擅长「前缀」，不擅长「后缀」与「模糊匹配」。
 *   要支持后缀查询需要后缀树，要支持容错需要编辑距离自动机，
 *   本质都是把同一棵树换一种方式组织。
 */
public class Trie implements TrieOps {

    // ============ 方法一：数组子节点（推荐，LeetCode 208 标准写法） ============

    /** 根节点，对应「空前缀」，它不表示任何字符 */
    private final TrieNode root = new TrieNode();

    /**
     * des: 前缀树节点
     * <p>
     * 请注意这个类的字段设计所表达的关键视角：节点里「不存字符」。
     * 一个字符是通过「父节点指向它的那条边」来表达的——children[c - 'a'] 非空，
     * 就代表「本前缀后面还可以接字符 c」。因此每个节点真正代表的是
     * 「从根走到它的那条路径拼成的前缀」，而不是某个孤立的字符。
     */
    private static class TrieNode {

        /** 26 个小写字母的槽位，children[i] 非空表示存在「当前前缀 + 第 i 个字母」这个前缀 */
        private final TrieNode[] children = new TrieNode[26];

        /** 从根走到本节点的路径是否恰好是一个「已插入的完整单词」，而不只是某个单词的前缀 */
        private boolean isEnd;
    }

    @Override
    public void insert(String word) {
        TrieNode node = root;
        for (int i = 0; i < word.length(); i++) {
            int index = word.charAt(i) - 'a';
            // 只有这条边不存在时才新建节点；已存在就直接复用——「共享前缀」正是在这里省下空间的
            if (node.children[index] == null) {
                node.children[index] = new TrieNode();
            }
            node = node.children[index];
        }
        // 走完整条路径，给终点打上词尾标记。
        // 注意这里不是「新建一个节点代表该单词」，而是「给已有路径的终点做标记」——这是关键区别
        node.isEnd = true;
    }

    @Override
    public boolean search(String word) {
        TrieNode node = findNode(word);
        // 两个条件都要成立：路径走得通（node != null），且终点被标记为词尾
        return node != null && node.isEnd;
    }

    @Override
    public boolean startsWith(String prefix) {
        // 与 search 唯一的差别：不检查 isEnd。只要路径走得通，就说明这个前缀存在
        return findNode(prefix) != null;
    }

    /**
     * des: 沿前缀逐字符下行，返回终点节点；中途走不通（该前缀不存在）时返回 null
     */
    private TrieNode findNode(String prefix) {
        TrieNode node = root;
        for (int i = 0; i < prefix.length(); i++) {
            node = node.children[prefix.charAt(i) - 'a'];
            if (node == null) {
                return null;
            }
        }
        return node;
    }

    // ============ 扩展：带通配符的搜索（LeetCode 211 添加与搜索单词） ============

    /**
     * des: 支持用 '.' 匹配任意单个字母的搜索
     * <p>
     * 遇到普通字母时，下一步走哪条边是唯一确定的，属于「直线前进」；
     * 遇到 '.' 时分支成了最多 26 种可能，必须逐个尝试，因此这里只能用回溯（DFS），
     * 不能再用上面那种朴素的 while 循环。
     * <p>
     * 这也揭示了 Trie 的另一类用法：它不仅能做「精确定位某个前缀」，
     * 还能做「按前缀枚举候选」——自动补全要给出某前缀下的全部单词，
     * 正是同一能力换个方向使用。
     */
    public boolean searchWithWildcard(String pattern) {
        return matchFrom(root, pattern, 0);
    }

    private boolean matchFrom(TrieNode node, String pattern, int index) {
        if (node == null) {
            return false;
        }
        if (index == pattern.length()) {
            // 模式串已走完，此时要求落点必须是一个完整单词
            return node.isEnd;
        }
        char c = pattern.charAt(index);
        if (c != '.') {
            return matchFrom(node.children[c - 'a'], pattern, index + 1);
        }
        // 通配符：26 条岔路中有一条走得通，整体就成立
        for (TrieNode child : node.children) {
            if (matchFrom(child, pattern, index + 1)) {
                return true;
            }
        }
        return false;
    }

    // ============ 方法二：HashMap 子节点（支持任意字符集） ============

    /**
     * des: 与方法一的差别只在于 children 这一个字段
     * <p>
     * 数组版无论实际用到几个字母，每个节点都固定占 26 个引用槽；
     * 哈希版只保存真实存在的那几条边。所以字符集越大、越稀疏，哈希版越省。
     * 代价是每次下行都要算一次哈希，常数比数组下标寻址大。
     * <p>
     * 选择依据可以简单记成一句话：字符集小且密集（ASCII 小写字母、DNA 碱基、数字）用数组，
     * 字符集大或稀疏（自然语言、含大小写与符号）用哈希。
     */
    static class TrieByMap implements TrieOps {

        private final MapNode root = new MapNode();

        private static class MapNode {
            private final Map<Character, MapNode> children = new HashMap<>();
            private boolean isEnd;
        }

        @Override
        public void insert(String word) {
            MapNode node = root;
            for (int i = 0; i < word.length(); i++) {
                // computeIfAbsent 一步完成「取子节点，没有就建一个」，逻辑上与数组版完全一致
                node = node.children.computeIfAbsent(word.charAt(i), c -> new MapNode());
            }
            node.isEnd = true;
        }

        @Override
        public boolean search(String word) {
            MapNode node = findNode(word);
            return node != null && node.isEnd;
        }

        @Override
        public boolean startsWith(String prefix) {
            return findNode(prefix) != null;
        }

        /**
         * des: 沿前缀下行
         * <p>
         * 这里用 get 判断是否为空即可，因为 children 的值永远不会是 null。
         * 反过来，若某个场景允许把 null 当作 value 存进去，就必须改用 containsKey，
         * 否则「键存在但值为 null」会被误判成「键不存在」——这是 Java 里用 Map 做缓存时
         * 最常见的静默 bug 之一。
         */
        private MapNode findNode(String prefix) {
            MapNode node = root;
            for (int i = 0; i < prefix.length(); i++) {
                node = node.children.get(prefix.charAt(i));
                if (node == null) {
                    return null;
                }
            }
            return node;
        }
    }

    // ============ 方法三：HashSet 存全部前缀（仅供对照，说明 Trie 的价值所在） ============

    /**
     * des: 不用任何节点，直接用一个集合存所有单词、另一个集合存所有前缀
     * <p>
     * 代码短到几乎不用解释，但它恰恰是用来说明「Trie 到底省了什么」的最佳反例。
     * insert 时要把 word 的每一个前缀都完整地存成一份独立的字符串对象，
     * 于是 "app" 这个前缀在 "app"、"apple" 里各存一遍，重复的副本随单词数线性增长，
     * 总存储量是 O(n·k) 的字符，而 Trie 只存边、前缀天然共享，总量是 O(m) 的节点。
     * <p>
     * 更本质的差距在于能力：
     * 1. 每次 startsWith 都要现造一个长度为 k 的字符串并算一次哈希，
     *    Trie 则是原地挪指针，不产生任何新对象；
     * 2. 它无法支持「枚举某前缀下的全部单词」（自动补全的核心需求），
     *    因为集合里没有「谁是谁的儿子」这层关系；
     * 3. 它无法支持删除——删掉 "apple" 时，无法判断前缀 "app" 是否还被别的单词需要，
     *    要做到就得给每个前缀加引用计数，那已经是在手工模拟一棵 Trie 了。
     * <p>
     * 一句话总结：它能通过本题，但没有把「前缀」这一结构真正表达出来，因此丧失了 Trie 的全部优势。
     */
    static class TrieBySet implements TrieOps {

        /** 所有被插入过的完整单词 */
        private final Set<String> words = new HashSet<>();

        /** 所有出现过的前缀 */
        private final Set<String> prefixes = new HashSet<>();

        TrieBySet() {
            // 空串是所有字符串的前缀，这里对应方法一中「根节点始终存在、代表空前缀」，
            // 好让三种实现在 startsWith("") 这个边界上的行为完全一致
            // （题目约束 prefix 长度 >= 1，实际不会触发，仅用于验证实现的一致性）
            prefixes.add("");
        }

        @Override
        public void insert(String word) {
            words.add(word);
            // 把 word 的每一个非空前缀都完整地存成一份字符串副本
            for (int i = 1; i <= word.length(); i++) {
                prefixes.add(word.substring(0, i));
            }
        }

        @Override
        public boolean search(String word) {
            return words.contains(word);
        }

        @Override
        public boolean startsWith(String prefix) {
            return prefixes.contains(prefix);
        }
    }

    // ============ 测试用例 ============

    public static void main(String[] args) {
        // 同一套用例依次跑三种实现，任何一处行为不一致都会立刻暴露
        checkAll("方法一：数组子节点", Trie::new);
        checkAll("方法二：HashMap 子节点", TrieByMap::new);
        checkAll("方法三：HashSet 存全部前缀", TrieBySet::new);

        // 方法二、方法三不受「仅小写字母」的限制，单独验证
        checkBeyondLowerCase();

        // 打印真实的树形结构，看前缀是如何被共享的
        demonstrateStructure();

        // LeetCode 211 通配符搜索扩展
        demonstrateWildcard();
    }

    /** des: 用同一套用例验证某个实现，工厂负责为每个用例创建全新的 Trie 实例 */
    private static void checkAll(String name, Supplier<TrieOps> factory) {
        System.out.println("==== " + name + " ====");
        boolean pass = true;

        // ---------- 用例 1：官方示例 ----------
        {
            TrieOps t = factory.get();
            t.insert("apple");
            pass &= expect("insert(\"apple\") 后 search(\"apple\")", t.search("apple"), true);
            pass &= expect("insert(\"apple\") 后 search(\"app\")", t.search("app"), false);
            pass &= expect("insert(\"apple\") 后 startsWith(\"app\")", t.startsWith("app"), true);
            t.insert("app");
            pass &= expect("补插 \"app\" 之后 search(\"app\")", t.search("app"), true);
        }

        // ---------- 用例 2：isEnd 的意义——路径上每个前缀都存在，但不是每个都是「单词」 ----------
        {
            TrieOps t = factory.get();
            t.insert("apple");
            pass &= expect("insert(\"apple\") 后 search(\"a\")", t.search("a"), false);
            pass &= expect("insert(\"apple\") 后 startsWith(\"a\")", t.startsWith("a"), true);
            pass &= expect("insert(\"apple\") 后 search(\"appl\")", t.search("appl"), false);
            pass &= expect("insert(\"apple\") 后 startsWith(\"appl\")", t.startsWith("appl"), true);
            // 路径走不到底，两个方法必须都为 false
            pass &= expect("insert(\"apple\") 后 search(\"apples\")", t.search("apples"), false);
            pass &= expect("insert(\"apple\") 后 startsWith(\"apples\")", t.startsWith("apples"), false);
        }

        // ---------- 用例 3：重复插入同一个单词应当幂等 ----------
        {
            TrieOps t = factory.get();
            t.insert("ab");
            t.insert("ab");
            t.insert("ab");
            pass &= expect("连续三次 insert(\"ab\") 后 search(\"ab\")", t.search("ab"), true);
            pass &= expect("连续三次 insert(\"ab\") 后 startsWith(\"ab\")", t.startsWith("ab"), true);
        }

        // ---------- 用例 4：短词本身也是长词的前缀，两者都被插入，必须都能查到 ----------
        {
            TrieOps t = factory.get();
            t.insert("a");
            t.insert("ab");
            t.insert("abc");
            pass &= expect("insert(a,ab,abc) 后 search(\"a\")", t.search("a"), true);
            pass &= expect("insert(a,ab,abc) 后 search(\"ab\")", t.search("ab"), true);
            pass &= expect("insert(a,ab,abc) 后 search(\"abc\")", t.search("abc"), true);
            pass &= expect("insert(a,ab,abc) 后 search(\"abcd\")", t.search("abcd"), false);
            pass &= expect("insert(a,ab,abc) 后 startsWith(\"abcd\")", t.startsWith("abcd"), false);
        }

        // ---------- 用例 5：多个单词共享前缀，在分叉处必须能正确区分 ----------
        {
            TrieOps t = factory.get();
            t.insert("bat");
            t.insert("batch");
            t.insert("bath");
            pass &= expect("insert(bat,batch,bath) 后 search(\"bat\")", t.search("bat"), true);
            pass &= expect("insert(bat,batch,bath) 后 search(\"batch\")", t.search("batch"), true);
            pass &= expect("insert(bat,batch,bath) 后 search(\"bath\")", t.search("bath"), true);
            // "batc" 是 "batch" 的前缀，但不是任何已插入的单词
            pass &= expect("insert(bat,batch,bath) 后 search(\"batc\")", t.search("batc"), false);
            pass &= expect("insert(bat,batch,bath) 后 startsWith(\"batc\")", t.startsWith("batc"), true);
            // 分叉后走上另一条支路，两条路都不能混
            pass &= expect("insert(bat,batch,bath) 后 search(\"bad\")", t.search("bad"), false);
        }

        // ---------- 用例 6：空树上的查询 ----------
        {
            TrieOps t = factory.get();
            pass &= expect("空树 search(\"a\")", t.search("a"), false);
            pass &= expect("空树 startsWith(\"a\")", t.startsWith("a"), false);
            pass &= expect("空树 search(\"\")", t.search(""), false);
            // 空串是任何字符串的前缀，对应「根节点始终存在」，三种实现统一返回 true
            pass &= expect("空树 startsWith(\"\")", t.startsWith(""), true);
        }

        // ---------- 用例 7：空串——题目约束长度 >= 1，此处仅用于验证三种实现行为一致 ----------
        {
            TrieOps t = factory.get();
            t.insert("");
            pass &= expect("insert(\"\") 后 search(\"\")", t.search(""), true);
            pass &= expect("insert(\"\") 后 startsWith(\"a\")", t.startsWith("a"), false);
        }

        System.out.println(pass ? "  => 全部通过" : "  => 存在失败");
        System.out.println();
    }

    /** des: 方法二 / 方法三不受「仅小写字母」的限制，单独验证大小写、数字与中文 */
    private static void checkBeyondLowerCase() {
        System.out.println("==== 方法二 / 方法三：小写字母之外的字符集 ====");
        boolean pass = true;
        pass &= checkCharacters("  方法二：HashMap", new TrieByMap());
        pass &= checkCharacters("  方法三：HashSet", new TrieBySet());
        System.out.println(pass ? "  => 全部通过" : "  => 存在失败");
        System.out.println();
    }

    private static boolean checkCharacters(String name, TrieOps t) {
        System.out.println(name);
        t.insert("Hello");
        t.insert("hello");
        t.insert("a1b2");
        t.insert("中文词");
        boolean pass = true;
        pass &= expect("insert(Hello,hello,a1b2,中文词) 后 search(\"Hello\")", t.search("Hello"), true);
        pass &= expect("... 后 search(\"hello\")（与 Hello 是两个不同的词）", t.search("hello"), true);
        pass &= expect("... 后 search(\"HELLO\")（大小写敏感）", t.search("HELLO"), false);
        pass &= expect("... 后 search(\"a1b2\")", t.search("a1b2"), true);
        pass &= expect("... 后 startsWith(\"a1\")", t.startsWith("a1"), true);
        pass &= expect("... 后 search(\"中文\")", t.search("中文"), false);
        pass &= expect("... 后 startsWith(\"中文\")", t.startsWith("中文"), true);
        pass &= expect("... 后 search(\"中文词\")", t.search("中文词"), true);
        return pass;
    }

    /** des: 打印真实的树形结构，直观展示「节点代表前缀」「共享前缀只存一份」「词尾标记」 */
    private static void demonstrateStructure() {
        String[] words = {"app", "apple", "bat", "batch"};
        System.out.println("---------- 结构演示：依次插入 " + Arrays.toString(words) + " ----------");

        Trie trie = new Trie();
        for (String word : words) {
            trie.insert(word);
        }

        System.out.println("树形结构（根节点代表空前缀，← 词尾 表示该节点 isEnd = true）：");
        System.out.println("  （根）");
        printTree(trie.root, "  ");

        int nodeCount = countNodes(trie.root);
        int charCount = Arrays.stream(words).mapToInt(String::length).sum();
        System.out.printf("节点总数（含根）：%d；四个单词的字符总数：%d%n", nodeCount, charCount);
        System.out.println("节点数少于字符总数，差额就来自共享前缀——app/apple 与 bat/batch 的前缀路径只存了一份。");
        System.out.println();

        // 对照：方法三把每个前缀都当作独立字符串完整存下来，冗余一目了然
        TrieBySet bySet = new TrieBySet();
        for (String word : words) {
            bySet.insert(word);
        }
        int storedChars = 0;
        for (String s : bySet.words) {
            storedChars += s.length();
        }
        for (String s : bySet.prefixes) {
            storedChars += s.length();
        }
        System.out.printf("作为对照，方法三存放的字符串个数：words %d 个 + prefixes %d 个%n",
                bySet.words.size(), bySet.prefixes.size());
        System.out.printf("方法三实际存储的字符总数：%d 个（同一段前缀被反复完整保存，且随单词数线性增长）%n",
                storedChars);
        System.out.println("这就是 Trie「用边共享前缀」的价值：存储量与字符总量挂钩，而不是与「前缀副本的份数」挂钩。");
        System.out.println();
    }

    /** des: LeetCode 211 通配符搜索的演示 */
    private static void demonstrateWildcard() {
        System.out.println("---------- 扩展演示：LeetCode 211 通配符搜索 ----------");
        Trie trie = new Trie();
        String[] words = {"bad", "dad", "mad"};
        for (String word : words) {
            trie.insert(word);
        }
        System.out.println("已插入：" + Arrays.toString(words) + "，'.' 可匹配任意单个字母");

        boolean pass = true;
        pass &= expect("searchWithWildcard(\".ad\")", trie.searchWithWildcard(".ad"), true);
        pass &= expect("searchWithWildcard(\"b..\")", trie.searchWithWildcard("b.."), true);
        pass &= expect("searchWithWildcard(\"..d\")", trie.searchWithWildcard("..d"), true);
        pass &= expect("searchWithWildcard(\"b.m\")", trie.searchWithWildcard("b.m"), false);
        pass &= expect("searchWithWildcard(\"bad\")（无通配符时等价于 search）",
                trie.searchWithWildcard("bad"), true);
        pass &= expect("searchWithWildcard(\"ba\")（长度对不上）", trie.searchWithWildcard("ba"), false);
        System.out.println(pass ? "  => 全部通过" : "  => 存在失败");
        System.out.println();
    }

    /** des: 递归打印树形结构，缩进体现层级 */
    private static void printTree(TrieNode node, String indent) {
        for (int i = 0; i < 26; i++) {
            TrieNode child = node.children[i];
            if (child == null) {
                continue;
            }
            System.out.printf("%s└─ %c%s%n", indent, (char) ('a' + i), child.isEnd ? "   ← 词尾" : "");
            printTree(child, indent + "   ");
        }
    }

    /** des: 统计树中的节点总数（含传入节点自身） */
    private static int countNodes(TrieNode node) {
        int count = 1;
        for (TrieNode child : node.children) {
            if (child != null) {
                count += countNodes(child);
            }
        }
        return count;
    }

    /** des: 比对单个返回值并打印结果 */
    private static boolean expect(String label, boolean actual, boolean expected) {
        boolean ok = actual == expected;
        System.out.printf("  %-46s 实际=%-6b 期望=%-6b %s%n", label, actual, expected, ok ? "✅" : "❌");
        return ok;
    }
}

/**
 * des: 三种实现的公共接口，抽象出来就能「一套用例跑三种实现」
 * <p>
 * 之所以放在顶层而不是嵌套在 Trie 内部：类的 implements 子句属于「类声明」而非「类体」，
 * 看不到自己类体里声明的成员类型，写成内部接口会直接编译报错。
 */
interface TrieOps {

    void insert(String word);

    boolean search(String word);

    boolean startsWith(String prefix);
}
