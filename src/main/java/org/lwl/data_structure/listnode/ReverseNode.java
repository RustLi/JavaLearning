package data_structure.listnode;

/**
 * @date: 2019/12/17
 * @author: lwl
 * @description: 反转链表
 */
public class ReverseNode {

    public static void main(String[] args){
//        strucTest();

        ListNode<Integer> head = new ListNode<>(1);
        head.next= new ListNode<>(2);
        head.next.next = new ListNode<>(3);
        traverse(head);
    }

    //反转链表
    private static void strucTest(){
        ListNode<Integer> head = new ListNode<>(1);
        head.next= new ListNode<>(2);
        head.next.next = new ListNode<>(3);

        ListNode<Integer> h = head;
        while (null != h){
            System.out.println(h.data);
            h = h.next;
        }
//        head = reverseList(head);
        head = reverseList2(head);

        System.out.println("反转以后的：：：：");
        while (null != head){
            System.out.println(head.data);
            head = head.next;
        }
    }

    /**
     * 递归反转法
     *  反转头节点，先反转其下一个节点，一直到尾节点，从尾到头逆向反转
     *
     *  a-b-c-d-e-null
     */
    public static ListNode<Integer> reverseList(ListNode<Integer> head){
        //head为null表示空链表，head.next为null表示尾节点
        if(head == null || head.next == null){
            return head;
        }
        //反转当前节点的后续节点（原尾，现在头部）
        //a -> (b <- c <- d <- e)
        ListNode<Integer> newNode = reverseList(head.next);
        //当前节点（尾部开始的节点）的指针域指向前一个节点
        //b === head.next, b.next -->> head
        head.next.next = head;
        //前一个节点的指针域为null
        // a --->> null
        head.next = null;
        return newNode;
    }

    /**
     *遍历反转法
     * 1.以head的下一个节点作为当前node;
     * 2.保存当前node的下一个node;
     * 3.反转当前node指向前一个node；
     * 4.向后移动node指针，直到当前node为null；
     */
    public static ListNode<Integer> reverseList2(ListNode<Integer> head){
        if(head == null || head.next == null)//head为null表示空链表，head.next为null表示尾节点
            return head;
        ListNode pre = head;
        ListNode cur = head.next;//当前节点为head的下一个节点
        ListNode tmp;
        while (null != cur){
            //保存好当前节点的下一个节点
            tmp = cur.next;
            //反转当前指针指向前一个节点
            cur.next = pre;
            //指针往后移动
            pre = cur;
            cur = tmp;
        }
        head.next = null;
        return pre;
    }

    /**
     * 反转以 a 为头结点的链表 [a,null)
     * 遍历翻转法，重点是要先保存 c.next，翻转后往后平移
     **/
    ListNode reverse(ListNode a) {
        ListNode pre, cur, nxt;
        pre = null; cur = a; nxt = a;
        while (cur != null) {
            nxt = cur.next;
            // 逐个结点反转
            cur.next = pre;
            // 更新指针位置
            pre = cur;
            cur = nxt;
        }
        // 返回反转后的头结点
        return pre;
    }


    /** 反转区间 [a, b) 的元素，注意是左闭右开 */
    ListNode reverse(ListNode a, ListNode b) {
        ListNode pre, cur, nxt;
        pre = null; cur = a; nxt = a;
        // while 终止的条件改一下就行了
        while (cur != b) {
            nxt = cur.next;
            cur.next = pre;
            pre = cur;
            cur = nxt;
        }
        // 返回反转后的头结点
        return pre;
    }

    /**
     * leetcode-25: K 个一组反转链表；
     **/
    ListNode reverseKGroup(ListNode head, int k) {
        if (head == null) return null;
        // 区间 [a, b) 包含 k 个待反转元素
        ListNode a, b;
        a = b = head;
        for (int i = 0; i < k; i++) {
            // 不足 k 个，不需要反转，base case
            if (b == null) return head;
            b = b.next;
        }

        //1-2   3-4-5 2=newHead 1=a
        //2->1 -> 4->3 ->5
        // 反转前 k 个元素
        ListNode newHead = reverse(a, b);
        // 递归反转后续链表并连接起来
        a.next = reverseKGroup(b, k);
        return newHead;
    }


    /**
     * 翻转前N个节点
     *
     *  1-2-3-4-5-6
     *  N=3: 3-2-1-4-5-6
     *
     **/
    ListNode successor = null; // 后驱节点
    // 反转以 head 为起点的 n 个节点，返回新的头结点
    ListNode reverseN(ListNode head, int n) {
        if (n == 1) {
            // 记录第 n + 1 个节点
            successor = head.next;
            return head;
        }
        // 以 head.next 为起点，需要反转前 n - 1 个节点
        ListNode last = reverseN(head.next, n - 1);

        //head.next = 2, 2.next = head
        head.next.next = head;
        // 让反转之后的 head 节点和后面的节点连起来
        //head.next --- 4
        head.next = successor;
        return last;
    }

    /**
     * 翻转[m,n]个节点
     **/
    ListNode reverseBetween(ListNode head, int m, int n) {
        // base case
        //m == 1，就相当于反转链表开头的 n 个元素
        if (m == 1) {
            return reverseN(head, n);
        }
        // 前进到反转的起点触发 base case
        head.next = reverseBetween(head.next, m - 1, n - 1);
        return head;
    }


    /* 递归遍历单链表，倒序打印链表元素 */
    public static void traverse(ListNode head) {
        if (head == null) {
            return;
        }
        traverse(head.next);
        // 后序位置
        System.out.println(head.data);
    }
}
