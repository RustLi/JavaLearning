package data_structure.listnode;

/**
 * @date: 2019/12/17
 * @author: lwl
 * @description: 反转链表
 */
public class ReverseNode {

    public static void main(String[] args){
        strucTest();
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
     *反转头节点，先反转其下一个节点，一直到尾节点，从尾到头逆向反转
     */
    public static ListNode<Integer> reverseList(ListNode<Integer> head){
        if(head == null || head.next == null)//head为null表示空链表，head.next为null表示尾节点
            return head;
        ListNode<Integer> newNode = reverseList(head.next);//反转当前节点的后续节点（原尾，现在头部）
        head.next.next = head;//当前节点（尾部开始的节点）的指针域指向前一个节点
        head.next = null;//前一个节点的指针域为null
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
}
