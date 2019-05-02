package com.shiliangxu.network.service;


import com.shiliangxu.network.domain.Link;
import com.shiliangxu.network.domain.Node;


import java.util.List;

/*******************************************************************************************************
 * Copyright © 2018 ShiliangXu. HIT .College of Computer Science and Technology. All rights reserved.
 * @Package: com.xushiliang.network.service
 * @author: ShiliangXu
 * @date: 2018/10/29 14:02
 * @Description: 连接策略
 *******************************************************************************************************/
public interface LinkService {

    //主函数,用于生成给定参数的小世界模型
    byte[][] mainService(int totalNumber,int k, double p, double t);

    //计算两个节点之间的地理(编号)距离
    int getDistance(int node1, int node2, int totalNumber);

    //计算两个节点之间加边的概率,p代表初始重连概率，t代表位置对加边概率影响的权重
    double getConnectionProbability(int node1, int node2, int totalNumber,double p, double t);

    //是否给两个节点加一条边
    boolean addLink(double px);

    //把二维数组转成LINK实体
    List<Link> turnToLinks(byte[][] adjacencyMatrix, int totalNumber);

    //计算某节点的聚类系数
    double getCC(int aNode,byte[][] adjacencyMatrix,int totalNumber);

    //计算整个网络的度分布,返回每个节点的度的数组
    List<Node> getALLDD(byte[][] adjacencyMatrix, int totalNumber);
}
