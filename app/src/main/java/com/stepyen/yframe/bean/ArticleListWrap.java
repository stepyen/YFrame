package com.stepyen.yframe.bean;

import java.util.List;

/**
 * date：2022/11/4
 * author：stepyen
 * description：
 */
public class ArticleListWrap {

    public Integer curPage;
    public List<DatasBean> datas;
    public Integer offset;
    public Boolean over;
    public Integer pageCount;
    public Integer size;
    public Integer total;       //文章总数

    public static class DatasBean {
        public Boolean adminAdd;
        public String apkLink;
        public Integer audit;
        public String author;
        public Boolean canEdit;
        public Integer chapterId;
        public String chapterName;
        public Boolean collect;
        public Integer courseId;
        public String desc;
        public String descMd;
        public String envelopePic;
        public Boolean fresh;
        public String host;
        public Integer id;
        public Boolean isAdminAdd;
        public String link;
        public String niceDate;
        public String niceShareDate;
        public String origin;
        public String prefix;
        public String projectLink;
        public Long publishTime;
        public Integer realSuperChapterId;
        public Integer selfVisible;
        public Long shareDate;
        public String shareUser;
        public Integer superChapterId;
        public String superChapterName;
        public List<?> tags;
        public String title;
        public Integer type;
        public Integer userId;
        public Integer visible;
        public Integer zan;
    }
}
