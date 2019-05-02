package com.shiliangxu.network.service;

import com.shiliangxu.network.domain.Link;
import com.shiliangxu.network.domain.Node;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

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
                    System.out.println("对于第"+i+"个节点："+item[0]+",朋友个数为"+(item.length-1)+",朋友为"+item[1]);
                    for(int k=1;k<item.length;k++){
                        //System.out.println("开始匹配："+item[k]);
                        for(int j=0;j<nodes.size();j++){
                            //根据当前item的信息来进行赋值
                            if(item[k].equals(nodes.get(j).getName())){
                                String str = nodes.get(i).getType()+"||"+item[k]+"*"+j+"*";
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
        return null;
    }
}
