package com.sqlchan.wenda.controller;

import java.io.Serializable;
import java.util.Date;

import com.sqlchan.wenda.model.Comment;

/**
 * @author zhanzhan
 * @date 2021/6/13 14:19
 */

public class CommentRequest implements Serializable {
    private int discussPostId;

    //private Comment comment;
    //评论id
    private int id;
    //评论用户
    private int userId;
    //评论实体id
    private int entityId;
    //评论实体
    private int entityType;
    //评论内容
    private String content;
    //评论时间
    private Date createTime;
    //状态
    private int status;
    //记录回复指向的人,即回复目标 (只会发生在回复中 判断target_id==0，目标为0，说明未回复)
    private int targetId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public int getEntityType() {
        return entityType;
    }

    public void setEntityType(int entityType) {
        this.entityType = entityType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTargetId() {
        return targetId;
    }

    public void setTargetId(int targetId) {
        this.targetId = targetId;
    }

    public int getDiscussPostId() {
        return discussPostId;
    }

    public void setDiscussPostId(int discussPostId) {
        this.discussPostId = discussPostId;
    }

    // public Comment getComment() {
    //     return comment;
    // }
    //
    // public void setComment(Comment comment) {
    //     this.comment = comment;
    // }
}
