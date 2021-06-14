package com.sqlchan.wenda.controller;

import com.sqlchan.wenda.model.*;
import com.sqlchan.wenda.service.CommentService;
import com.sqlchan.wenda.service.LikeService;
import com.sqlchan.wenda.service.QuestionService;
import com.sqlchan.wenda.service.UserService;
import com.sqlchan.wenda.util.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/6/29.
 */
@Controller
public class QuestionController {
    private static final Logger logger= LoggerFactory.getLogger(IndexController.class);

    @Autowired
    QuestionService questionService;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    UserService userService;
    @Autowired
    CommentService commentService;
    @Autowired
    LikeService likeService;

    /**
     * 问题发布
     *
     * @param title
     * @param content
     * @return
     */
    @RequestMapping(value = "/question/add",method = RequestMethod.POST)
    //@ResponseBody
    public String addquestion(@RequestParam("title")String title,
                              @RequestParam("content")String content){
        try {
            Question question=new Question();
            question.setContent(content);
            question.setTitle(title);
            question.setCreatedDate(new Date());
            question.setCommentCount(0);
            if(hostHolder.getUser()==null){
                question.setUserId(WendaUtil.ANONYMOUS_USERID);
            }else {
                question.setUserId(hostHolder.getUser().getId());
            }

            if(questionService.addquestion(question)>0){
                return "redirect:/";
                //return WendaUtil.getJSONString(0);
            }

        }catch (Exception e){
            logger.info("增加题目失败");
        }
        return "redirect:/";
        //return WendaUtil.getJSONString(1,"shibai");
    }

    /**
     * 发布的问题详情（问题+评论等信息）
     *
     * @param qid
     * @param model
     * @return
     */
    @RequestMapping("/question/{qid}")
    public String questiondetail(@PathVariable("qid")int qid,
                                 Model model){
        Question question=questionService.getById(qid);
        model.addAttribute("question",question);//问题相关信息
        model.addAttribute("owner",userService.getUser(question.getUserId()));

        //该条问题下所有的评论
        List<Comment> commentList=commentService.getCommentsByEntity(qid, EntityType.ENTITY_QUESTION);
        //初始化一个集合，用来放组合封装的每条评论的信息，最后返回的评论信息为该集合
        List<ViewObject> comments=new ArrayList<>();
        for(Comment comment:commentList){
            //每条评论封装上一些用户信息
            ViewObject vo=new ViewObject();
            vo.set("comment",comment);
            vo.set("user",userService.getUser(comment.getUserId()));
//            if (hostHolder.getUser() == null) {
//                vo.set("liked", 0);
//            } else {
//                vo.set("liked", likeService.getLikeStatus(hostHolder.getUser().getId(), EntityType.ENTITY_COMMENT, comment.getId()));
//            }
//
//            vo.set("likeCount", likeService.getLikeCount(EntityType.ENTITY_COMMENT, comment.getId()));
            comments.add(vo);
        }
        model.addAttribute("comments",comments);
        //////////////
        long likeCount=likeService.getLikeCount(EntityType.ENTITY_QUESTION,qid);
        model.addAttribute("likeCount",likeCount);
        int liked=0;
        //原先的程序中hostHolder!=null 该链接跳转出错NullPointerException，
        //更改后hostHolder.getUser()!=null正常
        if(hostHolder.getUser()!=null){
//            logger.info("hostholder"+hostHolder.toString()); //fei kong
//            logger.info("hostholder fei kong");
//            if(hostHolder.getUser()==null){
//                logger.error("hostholder user kong");  //kong
//            }


            liked=likeService.getLikeStatus(hostHolder.getUser().getId(),EntityType.ENTITY_QUESTION,qid);
            logger.error("worng");
        }
        model.addAttribute("liked",liked);
        ///////////////
//        if (hostHolder.getUser() == null) {
//            return "loginpage";
//        }

        return "detail";
    }

    @RequestMapping("/addquestionpage")
    public String addquestionpage(){
        return "addquestionpage";
    }
}
