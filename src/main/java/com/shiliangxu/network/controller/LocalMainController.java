package com.shiliangxu.network.controller;

import com.alibaba.fastjson.JSON;
import com.shiliangxu.network.domain.Link;
import com.shiliangxu.network.domain.Node;
import com.shiliangxu.network.service.LinkService;
import com.shiliangxu.network.service.SocialNetworkServiceImpl;
import com.shiliangxu.network.util.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/*******************************************************************************************************
 * Copyright © 2019 ShiliangXu. HIT .College of Computer Science and Technology. All rights reserved.
 * @Package: com.xushiliang.xushiliang01.complexNetwork.controller
 * @author: ShiliangXu
 * @date: 2019/2/26 16:13 
 * @Description: 控制器，用于控制 复杂网络建模 页面之间的跳转
 *******************************************************************************************************/
@Controller
public class LocalMainController {
    //  参数
    @Value("${limitedValue.Nbiggest}")
    private int Nbiggest;
    @Value("${limitedValue.Nsmallest}")
    private int Nsmallest;
    @Value("${limitedValue.Pbiggest}")
    private double Pbiggest;
    @Value("${limitedValue.Psmallest}")
    private double Psmallest;
    @Value("${limitedValue.Tbiggest}")
    private double Tbiggest;
    @Value("${limitedValue.Tsmallest}")
    private double Tsmallest;

    @Autowired
    private LinkService linkService;
    @Autowired
    private SocialNetworkServiceImpl socialNetworkService;


    @GetMapping("/")
    public String root() {
        return "redirect:complexNetwork/index";
    }
    @GetMapping("/index")
    public String index() {
        return "complexNetwork/index";
    }
    //  复杂网络主界面
    @GetMapping("complexNetwork/index")
    public String index1() {
        return "complexNetwork/index";
    }
    //  表单提交
    @PostMapping("complexNetwork/index")
    public ModelAndView generate(int totalNumber, double initialProbability, double influenceCoefficient, String EDGES, String NODES, String COLOR, Model model){

        // 输入合法性判断1：总节点数
        int totalnumber= totalNumber > Nbiggest? Nbiggest: totalNumber;
        totalnumber = totalnumber < Nsmallest? Nsmallest: totalnumber;
        // 输入合法性判断2： 概率P
        double initialprobability =initialProbability > Pbiggest ? Pbiggest: initialProbability;
        initialprobability =initialprobability < Psmallest ? Psmallest: initialprobability;
        if(initialprobability-0<0.0000001){
            initialprobability = -0.0000001;
        }
        // 输入合法性判断3：自定义概率T
        double influencecoefficient = influenceCoefficient > Tbiggest ? Tbiggest: influenceCoefficient;
        influencecoefficient = influencecoefficient < Tsmallest ? Tsmallest: influencecoefficient;
        if(influencecoefficient-0<0.0000001){
            influencecoefficient = -0.0000001; }

        //邻接矩阵
        byte[][] adjacencyMatrix;
        adjacencyMatrix = linkService.mainService(totalnumber,3,initialprobability,influencecoefficient);
        //由于并不能向js中传递二维数组，这里需要把二维数组转成link实体,然后还要转成json格式

//        //显示各个节点度数的一维数组
//        List<Node> nodes = linkService.getALLDD(adjacencyMatrix,totalnumber);
//        Object nodesJ= JSON.toJSON(nodes);

        //显示各个节点度数的一维数组
        List<Node> nodes = socialNetworkService.getNodes("D:\\MyClouds\\PROJECT\\network\\src\\main\\java\\com\\shiliangxu\\other\\friends_drop_duplicate.csv");
        Object nodesJ= JSON.toJSON(nodes);

        List<Link> links = socialNetworkService.getLinks(nodes);
        Object linksJ= JSON.toJSON(links);

        //边显示方式
        if(EDGES.equals("BY FORCE")){
            model.addAttribute("layout","force");
        }else {
            model.addAttribute("layout","circular");
        }
        //节点显示方式
        if(NODES.equals("NOT SHOW")){
            model.addAttribute("symbolSize",0);
        }else if(NODES.equals("ALL EQUAL ONE")) {
            model.addAttribute("symbolSize",2);
        }else {//显示各节点的度数
            model.addAttribute("symbolSize",-2);
        }
        //边的颜色
        if(COLOR.equals("rgb(129, 129, 129)") ||COLOR.equals("rgb(60, 60, 60)") ||COLOR.equals("rgb(255,0,0)") ){
        }else {
            COLOR = "rgb(129, 129, 129)";
        }

        model.addAttribute("color", COLOR);
        model.addAttribute("links", linksJ);
        model.addAttribute("degrees", nodesJ);
        model.addAttribute("totalnumber", 1000);
        model.addAttribute("initialprobability", initialprobability);
        model.addAttribute("influencecoefficient", influencecoefficient);
        model.addAttribute("Nbiggest", Nbiggest);
        LoggerUtil.logx(this.getClass().getName(),"生成了拥有"+totalnumber+"个节点的图:NPT:"+totalnumber+"---"+ initialprobability+"---"+influencecoefficient);
        return  new ModelAndView("complexNetwork/index1", "model", model);
    }

//    //  社交网络分析专用
//    @PostMapping("complexNetwork/network")
//    public ModelAndView network(int totalNumber, double initialProbability, double influenceCoefficient, String EDGES, String NODES, String COLOR, Model model){
//
//        //由于并不能向js中传递二维数组，这里需要把二维数组转成link实体,然后还要转成json格式
//        List<Link> links = null;
//        Object linksJ= JSON.toJSON(links);
//        //显示各个节点度数的一维数组
//        List<Node> nodes = socialNetworkService.getNodes("D:\\MyClouds\\PROJECT\\network\\src\\main\\java\\com\\shiliangxu\\other\\friends_drop_duplicate.csv");
//
//        Object nodesJ= JSON.toJSON(nodes);
//         EDGES = "BY FORCE";
//         NODES = "symbolSize";
//         COLOR = "rgb(129, 129, 129)";
//
//        //边显示方式
//        if(EDGES.equals("BY FORCE")){
//            model.addAttribute("layout","force");
//        }else {
//            model.addAttribute("layout","circular");
//        }
//        //节点显示方式
//        if(NODES.equals("NOT SHOW")){
//            model.addAttribute("symbolSize",0);
//        }else if(NODES.equals("ALL EQUAL ONE")) {
//            model.addAttribute("symbolSize",2);
//        }else {//显示各节点的度数
//            model.addAttribute("symbolSize",-2);
//        }
//        //边的颜色
//        if(COLOR.equals("rgb(129, 129, 129)") ||COLOR.equals("rgb(60, 60, 60)") ||COLOR.equals("rgb(255,0,0)") ){
//        }else {
//            COLOR = "rgb(129, 129, 129)";
//        }
//
//        model.addAttribute("color", COLOR);
//        model.addAttribute("links", linksJ);
//        model.addAttribute("nodes", nodesJ);
//        model.addAttribute("totalnumber", nodes.size());
//        model.addAttribute("initialprobability", 0);
//        model.addAttribute("influencecoefficient", 0);
//        model.addAttribute("Nbiggest", Nbiggest);
//        return  new ModelAndView("complexNetwork/index1", "model", model);
//    }
//
//
}
