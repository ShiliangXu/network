package com.shiliangxu.network.domain;

/*******************************************************************************************************   
 * Copyright © 2018 ShiliangXu. HIT .College of Computer Science and Technology. All rights reserved.
 * @Package: com.xushiliang.network.domain
 * @author: ShiliangXu
 * @date: 2018/10/29 13:50
 * @Description: 节点类
 *******************************************************************************************************/

public class Node {
    private int id;//唯一标识

    private int category;//节点的种类,暂时没用

    private int value;//节点的度

    private int cc;//聚类系数

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getCc() {
        return cc;
    }

    public void setCc(int cc) {
        this.cc = cc;
    }


    public Node(int id, int category, int value, int cc) {
        this.id = id;
        this.category = category;
        this.value = value;
        this.cc = cc;
    }

    public Node(){

    }

    @Override
    public String toString() {
        return "Node{" +
                "id=" + id +
                ", category=" + category +
                ", value=" + value +
                ", cc=" + cc +
                '}';
    }
}
