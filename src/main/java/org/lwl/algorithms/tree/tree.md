## 遍历框架

```java
import org.lwl.datastructure.tree.TreeNode;

void traverse(TreeNode root){
    if (root == null){
        return;
    }
    //前序
    traverse(root.left);
    //中序
    traverse(root.right);
    //后序
}
```


