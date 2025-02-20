  /**
二叉树数据结构TreeNode可用来表示单向链表（其中left置空，right为下一个链表节点）。实现一个方法，把二叉搜索树转换为单向链表，要求依然符合二叉搜索
树的性质，转换操作应是原址的，也就是在原始的二叉搜索树上直接修改。 

 返回转换后的单向链表的头节点。 

 注意：本题相对原题稍作改动 

 

 示例： 

 输入： [4,2,5,1,3,null,6,0]
输出： [0,null,1,null,2,null,3,null,4,null,5,null,6]
 

 提示： 

 
 节点数量不会超过 100000。 
 

 Related Topics 栈 树 深度优先搜索 二叉搜索树 链表 二叉树 👍 149 👎 0

*/

  package org.lwl.algorithms.leetcode.code.tree;

  import org.lwl.datastructure.tree.TreeNode;

  public class TreeToSingleLinkedList {
      public static void main(String[] args) {
           Solution solution = new TreeToSingleLinkedList().new Solution();
           TreeNode root = TreeNode.createTree(new Integer[]{4,2,5,1,3,null,6,0});
           TreeNode head = solution.convertBiNode(root);
           TreeNode.bfsTraversal(head);
      }
      //leetcode submit region begin(Prohibit modification and deletion)
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    //最小的节点
    TreeNode minNode;

    /**
     * 采用逆向的中序遍历，右-->根-->左
     **/
    public TreeNode convertBiNode(TreeNode root) {
        if (root == null){
            return null;
        }

        //处理右节点
        convertBiNode(root.right);

        //处理根节点
        //记录当前节点的上一个节点
        root.right = minNode;
        //更新最小的节点
        minNode = root;

        //处理左节点
        convertBiNode(root.left);
        root.left = null;
        return minNode;
      }
   }
}