package com.shiliangxu.network.domain;


/*******************************************************************************************************
 * Copyright © 2018 ShiliangXu. HIT .College of Computer Science and Technology. All rights reserved.
 * @Package: com.xushiliang.network.domain
 * @author: ShiliangXu
 * @date: 2018/10/29 13:57
 * @Description: 节点之间的连接本可以用矩阵来表示，用矩阵的优点是查找方便，缺点是空间浪费太严重，所以还是用实体数组比较节省空间，但是查找又很不方便
 *******************************************************************************************************/
public class Link {

//    前节点和后节点
    int pnode;
    int nnode;

    public int getPnode() {
        return pnode;
    }

    public void setPnode(int pnode) {
        this.pnode = pnode;
    }

    public int getNnode() {
        return nnode;
    }

    public void setNnode(int nnode) {
        this.nnode = nnode;
    }

    public Link(int pnode, int nnode) {
        this.pnode = pnode;
        this.nnode = nnode;
    }


}

