package com.movtalent.app.model.dto;

import java.util.List;

/**
 * @author huangyong
 * createTime 2019-09-21
 */
public class CommentGDto {


    /**
     * Code : 200
     * Data : {"item":[{"comment":{"comment_id":"13","comment_pid":"4","comment_rid":"19552","user_id":"12","comment_name":"ceshiuuu","comment_time":"1569059743","comment_content":"责ssss二级留言","comment_up":"0","comment_down":"0","comment_report":"0"},"reply":{"comment_id":"4","comment_pid":"0","comment_rid":"19552","user_id":"15","comment_name":"ceshiuuu","comment_time":"1569046206","comment_content":"测试下评论规则","comment_up":"0","comment_down":"0","comment_report":"0"}},{"comment":{"comment_id":"12","comment_pid":"7","comment_rid":"19552","user_id":"15","comment_name":"ceshiuuu","comment_time":"1569058864","comment_content":"责怪谁哈哈哈这个是二级留言","comment_up":"0","comment_down":"0","comment_report":"0"},"reply":{"comment_id":"7","comment_pid":"0","comment_rid":"19552","user_id":"19","comment_name":"haha1234","comment_time":"1569046526","comment_content":"我来改哥楼","comment_up":"0","comment_down":"0","comment_report":"0"}},{"comment":{"comment_id":"9","comment_pid":"4","comment_rid":"19552","user_id":"19","comment_name":"haha1234","comment_time":"1569046855","comment_content":"哈哈哈哈哈哈哈测试规则","comment_up":"0","comment_down":"0","comment_report":"0"},"reply":{"comment_id":"4","comment_pid":"0","comment_rid":"19552","user_id":"15","comment_name":"ceshiuuu","comment_time":"1569046206","comment_content":"测试下评论规则","comment_up":"0","comment_down":"0","comment_report":"0"}},{"comment":{"comment_id":"8","comment_pid":"7","comment_rid":"19552","user_id":"19","comment_name":"haha1234","comment_time":"1569046791","comment_content":"我来回复试试","comment_up":"0","comment_down":"0","comment_report":"0"},"reply":{"comment_id":"7","comment_pid":"0","comment_rid":"19552","user_id":"19","comment_name":"haha1234","comment_time":"1569046526","comment_content":"我来改哥楼","comment_up":"0","comment_down":"0","comment_report":"0"}},{"comment":{"comment_id":"7","comment_pid":"0","comment_rid":"19552","user_id":"19","comment_name":"haha1234","comment_time":"1569046526","comment_content":"我来改哥楼","comment_up":"0","comment_down":"0","comment_report":"0"},"reply":[]},{"comment":{"comment_id":"6","comment_pid":"1","comment_rid":"19552","user_id":"19","comment_name":"haha1234","comment_time":"1569046453","comment_content":"别听他们瞎说","comment_up":"1","comment_down":"0","comment_report":"0"},"reply":{"comment_id":"1","comment_pid":"0","comment_rid":"19552","user_id":"1","comment_name":"testlogin","comment_time":"1569046039","comment_content":"真的好看么","comment_up":"0","comment_down":"0","comment_report":"0"}},{"comment":{"comment_id":"5","comment_pid":"1","comment_rid":"19552","user_id":"15","comment_name":"ceshiuuu","comment_time":"1569046260","comment_content":"知道哥皮","comment_up":"0","comment_down":"1","comment_report":"0"},"reply":{"comment_id":"1","comment_pid":"0","comment_rid":"19552","user_id":"1","comment_name":"testlogin","comment_time":"1569046039","comment_content":"真的好看么","comment_up":"0","comment_down":"0","comment_report":"0"}},{"comment":{"comment_id":"4","comment_pid":"0","comment_rid":"19552","user_id":"15","comment_name":"ceshiuuu","comment_time":"1569046206","comment_content":"测试下评论规则","comment_up":"0","comment_down":"0","comment_report":"0"},"reply":[]},{"comment":{"comment_id":"3","comment_pid":"1","comment_rid":"19552","user_id":"15","comment_name":"ceshiuuu","comment_time":"1569046159","comment_content":"看了就知道了","comment_up":"0","comment_down":"0","comment_report":"1"},"reply":{"comment_id":"1","comment_pid":"0","comment_rid":"19552","user_id":"1","comment_name":"testlogin","comment_time":"1569046039","comment_content":"真的好看么","comment_up":"0","comment_down":"0","comment_report":"0"}},{"comment":{"comment_id":"1","comment_pid":"0","comment_rid":"19552","user_id":"1","comment_name":"testlogin","comment_time":"1569046039","comment_content":"真的好看么","comment_up":"0","comment_down":"0","comment_report":"0"},"reply":[]}]}
     * Msg :
     */

    private int Code;
    private DataBean Data;
    private String Msg;

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean Data) {
        this.Data = Data;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String Msg) {
        this.Msg = Msg;
    }

    public static class DataBean {
        private List<ItemBean> item;

        public List<ItemBean> getItem() {
            return item;
        }

        public void setItem(List<ItemBean> item) {
            this.item = item;
        }

        public static class ItemBean {
            /**
             * comment : {"comment_id":"13","comment_pid":"4","comment_rid":"19552","user_id":"12","comment_name":"ceshiuuu","comment_time":"1569059743","comment_content":"责ssss二级留言","comment_up":"0","comment_down":"0","comment_report":"0"}
             * reply : {"comment_id":"4","comment_pid":"0","comment_rid":"19552","user_id":"15","comment_name":"ceshiuuu","comment_time":"1569046206","comment_content":"测试下评论规则","comment_up":"0","comment_down":"0","comment_report":"0"}
             */

            private CommentBean comment;
            private ReplyBean reply;
            private String user_icon;
            private String rep_icon;

            public String getUser_icon() {
                return user_icon;
            }

            public void setUser_icon(String user_icon) {
                this.user_icon = user_icon;
            }

            public String getRep_icon() {
                return rep_icon;
            }

            public void setRep_icon(String rep_icon) {
                this.rep_icon = rep_icon;
            }

            public CommentBean getComment() {
                return comment;
            }

            public void setComment(CommentBean comment) {
                this.comment = comment;
            }

            public ReplyBean getReply() {
                return reply;
            }

            public void setReply(ReplyBean reply) {
                this.reply = reply;
            }

            public static class CommentBean {
                /**
                 * comment_id : 13
                 * comment_pid : 4
                 * comment_rid : 19552
                 * user_id : 12
                 * comment_name : ceshiuuu
                 * comment_time : 1569059743
                 * comment_content : 责ssss二级留言
                 * comment_up : 0
                 * comment_down : 0
                 * comment_report : 0
                 */

                private String comment_id;
                private String comment_pid;
                private String comment_rid;
                private String user_id;
                private String comment_name;
                private String comment_time;
                private String comment_content;
                private String comment_up;
                private String comment_down;
                private String comment_report;

                public String getComment_id() {
                    return comment_id;
                }

                public void setComment_id(String comment_id) {
                    this.comment_id = comment_id;
                }

                public String getComment_pid() {
                    return comment_pid;
                }

                public void setComment_pid(String comment_pid) {
                    this.comment_pid = comment_pid;
                }

                public String getComment_rid() {
                    return comment_rid;
                }

                public void setComment_rid(String comment_rid) {
                    this.comment_rid = comment_rid;
                }

                public String getUser_id() {
                    return user_id;
                }

                public void setUser_id(String user_id) {
                    this.user_id = user_id;
                }

                public String getComment_name() {
                    return comment_name;
                }

                public void setComment_name(String comment_name) {
                    this.comment_name = comment_name;
                }

                public String getComment_time() {
                    return comment_time;
                }

                public void setComment_time(String comment_time) {
                    this.comment_time = comment_time;
                }

                public String getComment_content() {
                    return comment_content;
                }

                public void setComment_content(String comment_content) {
                    this.comment_content = comment_content;
                }

                public String getComment_up() {
                    return comment_up;
                }

                public void setComment_up(String comment_up) {
                    this.comment_up = comment_up;
                }

                public String getComment_down() {
                    return comment_down;
                }

                public void setComment_down(String comment_down) {
                    this.comment_down = comment_down;
                }

                public String getComment_report() {
                    return comment_report;
                }

                public void setComment_report(String comment_report) {
                    this.comment_report = comment_report;
                }
            }

            public static class ReplyBean {
                /**
                 * comment_id : 4
                 * comment_pid : 0
                 * comment_rid : 19552
                 * user_id : 15
                 * comment_name : ceshiuuu
                 * comment_time : 1569046206
                 * comment_content : 测试下评论规则
                 * comment_up : 0
                 * comment_down : 0
                 * comment_report : 0
                 */

                private String comment_id;
                private String comment_pid;
                private String comment_rid;
                private String user_id;
                private String comment_name;
                private String comment_time;
                private String comment_content;
                private String comment_up;
                private String comment_down;
                private String comment_report;

                public String getComment_id() {
                    return comment_id;
                }

                public void setComment_id(String comment_id) {
                    this.comment_id = comment_id;
                }

                public String getComment_pid() {
                    return comment_pid;
                }

                public void setComment_pid(String comment_pid) {
                    this.comment_pid = comment_pid;
                }

                public String getComment_rid() {
                    return comment_rid;
                }

                public void setComment_rid(String comment_rid) {
                    this.comment_rid = comment_rid;
                }

                public String getUser_id() {
                    return user_id;
                }

                public void setUser_id(String user_id) {
                    this.user_id = user_id;
                }

                public String getComment_name() {
                    return comment_name;
                }

                public void setComment_name(String comment_name) {
                    this.comment_name = comment_name;
                }

                public String getComment_time() {
                    return comment_time;
                }

                public void setComment_time(String comment_time) {
                    this.comment_time = comment_time;
                }

                public String getComment_content() {
                    return comment_content;
                }

                public void setComment_content(String comment_content) {
                    this.comment_content = comment_content;
                }

                public String getComment_up() {
                    return comment_up;
                }

                public void setComment_up(String comment_up) {
                    this.comment_up = comment_up;
                }

                public String getComment_down() {
                    return comment_down;
                }

                public void setComment_down(String comment_down) {
                    this.comment_down = comment_down;
                }

                public String getComment_report() {
                    return comment_report;
                }

                public void setComment_report(String comment_report) {
                    this.comment_report = comment_report;
                }
            }
        }
    }
}
