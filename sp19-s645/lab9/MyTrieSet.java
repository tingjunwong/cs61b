import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class MyTrieSet implements TrieSet61B {

    private class Node {
        boolean isKey;                                      //node中并不需要存储当前node所表示的字符
        HashMap<Character, Node> next;
         // 对二叉树来说   在 next指向下一个节点的指针  有一个left  有一个right
        //  向trie 来说    指针有多个  所以用映射来 存储
        //  默认 了每一个节点 存储的每一个字母都是charactr这个类的对象

        Node(boolean isKey) {
            this.isKey = isKey;
            next = new HashMap<>();                                     // next 这个映射中

        }
        //假设     只要能分割成一个一个单元

        Node() {
            this(false);
        }
    }

    private Node root;
    private int size;

    public MyTrieSet() {
        root = new Node();

    }

    //获得了trie 中存储的单词数

    public void clear() {
        root = new Node();
    }

    /** Returns true if the Trie contains KEY, false otherwise */
    public boolean contains(String key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        Node cur = root;
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            if (cur.next.get(c) == null) {
                //找到了一个地方 发现没有这个字符了，说明没哟这个单词了 直接return false
                return false;
            }
            cur = cur.next.get(c);
        }
        //trie 中有panda 这个词  查 pan这个单词     panda   里的n 这个字符所在的节点boolean 为false
        //如果我们出了这个循环  此时 cur 来到了word这个单词最后一个字符所在的节点
        return cur.isKey;
    }

    /** Inserts string KEY into Trie */
    public void add(String key) {
        if (key == null || key.length() < 1) {
            return;
        }
        Node cur = root;                                          //初始化时在root的位置
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);                          //每次从单词中取出一个字符
                                                          //将c字符做成一个节点
            if (cur.next.get(c) == null) {                 //需要检查对于当前curr这个节点  即这个节点为空
                // 他的next这个映射中 ，是否已经有了指向字符c的这个相应节点
                cur.next.put(c, new Node());
            }
            cur = cur.next.get(c);
        }
        if (!cur.isKey) {
            //cur 就来到了整个字符串 所处的最后那个节点  注意这个节点不一定是叶子节点
            // 比如 有panda这个词   添加pan这个词   上述过程查找到了pan的n 所在的位置
            //之前并不表示任何一个单词的结尾
            cur.isKey = true;
            size++;

        }
    }

    /** Returns a list of all words that start with PREFIX */
    public List<String> keysWithPrefix(String prefix) {
        ArrayList<String> lst = new ArrayList<>();
        Node cur = root;
        for (int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);
            if (cur.next.get(c) == null) {
                //找到了一个地方 发现没有这个字符了
                return lst;
            }
            cur = cur.next.get(c);
        }
         //一直到最后 如果能够跳出这个循环  说明我们遍历完了这个prefix字符串所有的字母

        collect(prefix, lst, cur);
        return lst;
    }
    /* @source reference from  CS 61B josh hug's slides
     *and lab TA's solution  only refer to the collect method and the keyswithprefix*/

    private void collect(String prefix, ArrayList lst, Node cur) {
        if (cur.isKey) {
            //一直到最后 如果能够跳出这个循环  说明我们遍历完了这个prefix字符串所有的字母
            lst.add(prefix);
        }
        for (Character e: cur.next.keySet()) {
            collect(prefix + e, lst, cur.next.get(e));
        }
    }


    /** Returns the longest prefix of KEY that exists in the Trie
     * Not required for Lab 9. If you don't implement this, throw an
     * UnsupportedOperationException.
     */
    public String longestPrefixOf(String key) {
        throw new UnsupportedOperationException();
    }


}
