package cn.com.infcn.core.util;

import java.util.ArrayList;
import java.util.List;

import cn.com.infcn.core.pageModel.Tree;

/**
 * 菜单相关的帮助类
 * 
 * @author NOLY DAKE
 *
 */
public class MenuUtil {

    /**
     * 将一个列表转换为Tree对象
     * 
     * @param listTree
     *            列表对象
     * @return 树对象
     */
    public static List<Tree> list2Tree(List<Tree> listTree) {

        List<Tree> result = new ArrayList<Tree>();

        // 首先构造一级树
        for (Tree tree : listTree) {
            if (tree.getPid() == null || tree.getPid().trim().length() == 0) {
                result.add(tree);
            }
        }

        for (Tree tree : result) {
            buildTreeByParent(tree, listTree);
        }

        return result;
    }

    private static void buildTreeByParent(Tree tree, List<Tree> listTree) {

        List<Tree> children = null;

        for (Tree subTree : listTree) {

            // 当前父节点的子节点列表
            children = tree.getChildren();

            if (tree.getId().equals(subTree.getPid())) {

                if (children == null) {
                    children = new ArrayList<Tree>();
                    tree.setChildren(children);
                }

                children.add(subTree);
            }
        }

        if (children == null || children.size() == 0) {
            return;
        }

        for (Tree subTree : children) {
            buildTreeByParent(subTree, listTree);
        }
    }
}
