package com.shiliangxu.network.service;


import com.shiliangxu.network.domain.Link;
import com.shiliangxu.network.domain.Node;
import com.shiliangxu.network.util.LoggerUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/*******************************************************************************************************
 * Copyright © 2018 ShiliangXu. HIT .College of Computer Science and Technology. All rights reserved.
 * @Package: com.xushiliang.network.service
 * @author: ShiliangXu
 * @date: 2018/10/29 14:23
 * @Description:
 *******************************************************************************************************/
@Service
public class LinkServiceImpl implements LinkService {


    @Override
    //主函数,用于生成给定参数的小世界模型
    public byte[][] mainService(int totalNumber,int k, double p, double t) {
        //初始化二维数组
        byte[][] adjacencyMatrix;
        adjacencyMatrix = new byte[totalNumber][totalNumber];
        //根据k值来确定在初始状态下哪些顶点是互相连接的
        for(int i=0 ; i<totalNumber; i++){
            for(int j=1; j<=k; j++){
                if(i>=j){
                    adjacencyMatrix[i][(i-j)] = 1;
                }else { //负数求余，比如一共有100个数 i=2 j=4,意味着2要和1，0，99，98
                    adjacencyMatrix[i][totalNumber+(i-j)] = 1;
                }
                //加法肯定不会出现负数求余的情况。
                adjacencyMatrix[i][(i+j)%totalNumber] = 1;
            }
        }
        //-----------------随机重连方法二，采用遍历的方法，理论上这种方法比上一种方法产生更多新边:On的平方---------------//
        for(int i=0;i<(totalNumber-1);i++){
            for(int j=i+1;j<totalNumber;j++){
                double px = getConnectionProbability(i,j,totalNumber,p,t);
                if(addLink(px)){
                    adjacencyMatrix[i][j]=1;
                    adjacencyMatrix[j][i]=1;
                }
            }
        }
        //输出到状态栏
        for(int i=0;i<7;i++){
            LoggerUtil.Systemy(getCC(i,adjacencyMatrix,totalNumber)+"是第"+i+"个节点的CC");
        }

        return adjacencyMatrix;
    }


    @Override
    //获取两个顶点的实际距离
    public int getDistance(int node1, int node2, int totalNumber) {
        int distance1 = Math.abs(node1-node2);
        int distance2 = totalNumber-distance1;
        LoggerUtil.Systemx("两点间的实际距离为"+((distance1 < distance2) ? distance1 : distance2));
        return (distance1 < distance2) ? distance1 : distance2;
    }

    @Override
    //计算两个节点之间加边的概率
    public double getConnectionProbability(int node1, int node2, int totalNumber, double p, double t) {
        int distance = getDistance( node1,  node2,  totalNumber);//首先获取两点之间距离
        double px = (1-t)*p + (t*(1-distance/(0.5*totalNumber)));//计算出新的加边概率
        LoggerUtil.Systemx("计算得到两节点之间的加边概率为"+px);
        return px;
    }

    @Override
    //是否给两个节点加一条边
    public boolean addLink(double px) {
        int num= (int)(Math.random()*100000);
        if(num>100000*px){
            LoggerUtil.Systemx("依照概率"+px+"结果是：不加边");
            return false;
        }else{
            LoggerUtil.Systemx("依概率"+px+"结果是：加边");
            return true;
        }
    }

    //将二维数组做形参，转成links实体，为了方便后续显示步骤
    @Override
    public List<Link> turnToLinks(byte[][] adjacencyMatrix, int totalNumber) {
        int a=0;
        List<Link> links = new ArrayList();
        for(int i = 0; i< totalNumber;i++ ){
            for(int j = 0; j< totalNumber;j++){
                if(adjacencyMatrix[i][j] == 1) {
                    links.add(new Link(i,j));
                    a++;
                }
            }
        }
        return links;
    }

    @Override
    public double getCC(int aNode,byte[][] adjacencyMatrix,int totalNumber) {
        int[] AdjacencyNodes = new int[2000];
        int number = 0;
        int Numerator = 0;
        LoggerUtil.Systemy(aNode+"的所有邻接节点为:");
        for(int i=0;i<totalNumber;i++){
            if(adjacencyMatrix[aNode][i]==1){
                AdjacencyNodes[number]=i;
                LoggerUtil.Systemx(i+"--");
                number++;
            }
        }
        for(int i=0; i<number-1;i++){
            for(int j=i+1;j<number;j++){
                if(adjacencyMatrix[AdjacencyNodes[i]][AdjacencyNodes[j]]==1){
                    Numerator++;
                    LoggerUtil.Systemy("相连的节点为"+AdjacencyNodes[i]+" "+AdjacencyNodes[j]);
                }
            }
        }
        double cc = (2*(double)Numerator)/((double)number*((double)number-1));
        LoggerUtil.Systemy("节点"+aNode+"的cc:"+cc+";该节点一共有"+number+"个邻接点，理论上所有邻接点可以构成"+number*(number-1)/2+"条边，但是生成的边仅有："+Numerator+"条");
        return cc;
    }



    @Override
    public List<Node> getALLDD(byte[][] adjacencyMatrix, int totalNumber) {
        List<Node> lnodes = new ArrayList();
        int[] degreeNumber = new int[1000];//
        int k=0;
        for(int i=0;i<totalNumber;i++){
            Node node = new Node(i,0,0,0);
            for(int j=0;j<totalNumber;j++){
                if(adjacencyMatrix[i][j]==1){
                    k++;
                }
            }
            degreeNumber[k]++;
            node.setValue(k);
            LoggerUtil.Systemx("node"+i+"Degree:"+k);
            lnodes.add(node);
            k=0;

        }
        for (int j=6;j<100;j++){
            LoggerUtil.Systemy(j+":"+degreeNumber[j]);
        }
        return  lnodes;
    }


}
