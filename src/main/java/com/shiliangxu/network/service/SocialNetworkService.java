package com.shiliangxu.network.service;

import com.shiliangxu.network.domain.Link;
import com.shiliangxu.network.domain.Node;

import java.util.List;

/*******************************************************************************************************
 * Copyright © 2019 ShiliangXu. HIT .College of Computer Science and Technology. All rights reserved.
 * @Package: com.shiliangxu.network.service
 * @author: ShiliangXu
 * @date: 2019/5/2 13:36 
 * @Description: 用于社交网络分析的service
 *******************************************************************************************************/
public interface SocialNetworkService {

    //主函数,从文件中生成对应的二维数组
    List<Node> getNodes(String FileLocation);

    List<Link> getLinks(List<Node> nodes);
}
