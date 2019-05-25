package com.shiliangxu.network.service;

import com.shiliangxu.network.domain.Link;
import com.shiliangxu.network.domain.Node;
import com.shiliangxu.network.util.LoggerUtil;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/*******************************************************************************************************
 * Copyright © 2019 ShiliangXu. HIT .College of Computer Science and Technology. All rights reserved.
 * @Package: com.shiliangxu.network.service
 * @author: ShiliangXu
 * @date: 2019/5/2 13:35
 * @Description: implements LinkService
 *******************************************************************************************************/
@Service
public class SocialNetworkServiceImpl implements SocialNetworkService  {
    @Override
    public List<Node> getNodes(String fileName) {
        List<Node> nodes = new ArrayList();
        //第一次读取文件，获得节点基本数据
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));//换成你的文件名
            reader.readLine();//第一行信息，为标题信息，不用,如果需要，注释掉
            String line = null;
            int i=0;
            while((line=reader.readLine())!=null){
                String item[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
                Node node = new Node(i,0,item.length-1,0,item[0],"");
                nodes.add(node);
                i++;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        //第二次读取文件，获得链接信息
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));//换成你的文件名
            reader.readLine();//第一行信息，为标题信息，不用,如果需要，注释掉
            String line = null;
            int i=0;
            while((line=reader.readLine())!=null){
                String item[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
                if((nodes.get(i).getName()).equals(item[0])){
                    for(int k=1;k<item.length;k++){
                        item[k]=item[k].replace("{","");
                        item[k]=item[k].replace("\"","");
                        item[k]=item[k].replace("\'","");
                        item[k]=item[k].replace("}","");
                        item[k]=item[k].replace(" ","");
                    }
                    // item存放着朋友的数组信息，将该信息存放在node的type中用S隔开
                    System.out.println("对于第"+i+"个节点："+item[0]+",朋友个数为"+(item.length-1)+",朋友为"+item[1]+"等等");
                    for(int k=1;k<item.length;k++){
                        //System.out.println("开始匹配："+item[k]);
                        for(int j=0;j<nodes.size();j++){
                            //根据当前item的信息来进行赋值
                            if(item[k].equals(nodes.get(j).getName())){
                                String str = nodes.get(i).getType()+"S"+j;
                                nodes.get(i).setType(str);
                                break;
                            }
                        }
                    }
                    //System.out.println(nodes.get(i).toString());
                }
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nodes;
    }

    @Override
    public List<Link> getLinks(List<Node> nodes) {
        List<Link> links = new ArrayList();
        for(int j=0;j<nodes.size();j++){
            String message = nodes.get(j).getType();
            String m[] = message.split("S");
            for(int i=0;i<m.length;i++){
                if(m[i].equals("")){
                    continue;
                }
                int pnode = nodes.get(j).getId();
                int nnode = Integer.parseInt(m[i]);
                links.add(new Link(pnode,nnode));
            }
        }
        return links;
    }

    @Override
    public List<Node> CascadeModel(List<Node> nodes, int startNumber, double P){
        //先筛选出度数最大的几个点
        int numbers[] = new int[startNumber];
        int degrees[] = new int[startNumber];
        for(int i=0;i<nodes.size(); i++){

            for(int j=0;j<startNumber;j++){
                if (nodes.get(i).getValue()>degrees[j]){
                    degrees[j]= nodes.get(i).getValue();
                    numbers[j]= i;
                    break;
                }else {
                    continue;
                }
            }
        }
        //开始以概率p进行广度优先遍历
        Queue<Integer> thequeue = new LinkedList<Integer>();
        int numberofActivate = startNumber;
        //起始结点装入队列
        for(int i=0;i<startNumber; i++){
            thequeue.offer(numbers[i]);
            nodes.get(numbers[i]).setCategory(1);
            System.out.println("首先装入的节点为"+numbers[i]+"度数为"+degrees[i]);
        }

        while(!thequeue.isEmpty()){ //队列非空
            //取出队首节点
            int firstNodeNumberInQueue = thequeue.poll();


            //对于该节点的所有邻居，如果没有被激活，则以概率P激活，并压入队列
            String message = nodes.get(firstNodeNumberInQueue).getType();
            String m[] = message.split("S");
            for(int i=0;i<m.length;i++){

                if(m[i].equals("")){
                    continue;
                }
//                已经被激活
                if(nodes.get(Integer.parseInt(m[i])).getCategory()!= 0 ){
                    continue;
                }else {
                    //如果该节点还没有被激活
                    int num= (int)(Math.random()*100000);
                    if(num>100000*P){
//                        System.out.println("依照概率"+P+"结果是：不将其加入队列");

                    }else{
//                        System.out.println("依概率"+P+"结果是：加入队列");
                        thequeue.offer(Integer.parseInt(m[i]));
                        nodes.get(Integer.parseInt(m[i])).setCategory(1);
                        numberofActivate++;
                    }
                }

            }

        }


        System.out.println("初始节点个数"+startNumber+";激活概率"+P+";被激活节点总数量"+numberofActivate);
        return nodes;
    }



}
