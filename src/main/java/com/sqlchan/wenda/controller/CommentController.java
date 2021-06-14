package com.sqlchan.wenda.controller;

import com.sqlchan.wenda.model.Comment;
import com.sqlchan.wenda.model.EntityType;
import com.sqlchan.wenda.model.HostHolder;
import com.sqlchan.wenda.service.CommentService;
import com.sqlchan.wenda.service.QuestionService;
import com.sqlchan.wenda.util.WendaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

/**
 * Created by Administrator on 2017/6/30.
 */
@Controller
public class CommentController {
    private static final Logger logger= LoggerFactory.getLogger(IndexController.class);
    @Autowired
    HostHolder hostHolder;
    @Autowired
    CommentService commentService;
    @Autowired
    QuestionService questionService;

    /**
     * 给问题增加一条评论
     *
     * @param questionId  问题id
     * @param content  评论内容
     * @param model
     * @return
     */
    @RequestMapping(value = "/addComment",method = RequestMethod.POST)
    public String addComment(@RequestParam("questionId")int questionId,
                             @RequestParam("content")String content,
                             Model model){
        try {
            Comment comment=new Comment();
            comment.setContent(content);
            if(hostHolder.getUser()!=null){
                comment.setUserId(hostHolder.getUser().getId());
            }else {
                //未登录，给一个匿名用户
                comment.setUserId(WendaUtil.ANONYMOUS_USERID);
            }
            comment.setCreateTime(new Date());
            //实体：对帖子的评论
            comment.setEntityType(EntityType.ENTITY_QUESTION);
            comment.setEntityId(questionId);
            comment.setStatus(0);
            commentService.addComment(comment);

            //更新该问题表中的评论数（要加事务）
            int count=commentService.getCommentCount(comment.getEntityId(),comment.getEntityType());
            questionService.updateCommentCount(comment.getEntityId(),count);


        }catch (Exception e){
            logger.error("comment error");
        }
        return "redirect:/question/"+questionId;

    }


}
