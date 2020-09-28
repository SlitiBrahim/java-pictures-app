package com.supinfo.suppictures.entity;

import com.supinfo.suppictures.dao.CategoryDAO;
import com.supinfo.suppictures.dao.MemberDAO;

import java.util.Date;
import java.util.List;

public class Post {
    private Long id;
    private String title;
    private String description;
    private String path;
    private Date createdAt;
    private Date updatedAt;

    private String createdAtString;
    private String updatedAtString;

    private String locality;

    private Long categoryId;
    private Long memberId;

    private Category category;
    private Member member;


    public Post(){}

    public Post(String title, String description, String path, String locality, Long categoryId, Long memberId) {
        this.title = title;
        this.description = description;
        this.path = path;
        this.locality = locality;
        this.categoryId = categoryId;
        this.memberId = memberId;
    }

    public Post(Long id, String title, String description, String path, Date createdAt, Date updatedAt, String locality, Long categoryId, Long memberId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.path = path;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.locality = locality;
        this.categoryId = categoryId;
        this.memberId = memberId;
    }

    public Post(Long id, String title, String description, String path, String createdAtString, String  updatedAtString, String locality, Long categoryId, Long memberId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.path = path;
        this.createdAtString = createdAtString;
        this.updatedAtString = updatedAtString;
        this.locality = locality;
        this.categoryId = categoryId;
        this.memberId = memberId;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPath() {
        return path;
    }


    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }


    public String getCreatedAtToString() {
        return getDateFormatString(createdAtString);
    }

    public String getUpdatedAtToString() {
        return getDateFormatString(updatedAtString);
    }


    public String getLocality() {
        return locality;
    }

    public Long getCategoryId() {
        return categoryId;
    }


    public Long getMemberId() {
        return memberId;
    }

    public Category getCategory() {
        CategoryDAO categoryDAO = new CategoryDAO();
        return categoryDAO.find(categoryId);
    }

    public Member getMember() {
        MemberDAO memberDAO = new MemberDAO();
        return memberDAO.find(memberId);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public void setCategory(Category category){
        this.categoryId = category.getId();
        this.category = category;
    }

    public void setMember(Member member){
        this.memberId = member.getId();
        this.member = member;
    }

    public static String toJson(Post post) {
        return String.format(
                "{" +
                        "\"id\": %d," +
                        "\"title\": \"%s\"," +
                        "\"description\": \"%s\"," +
                        "\"path\": %s," +
                        "\"createdAt\": \"%s\"," +
                        "\"updatedAt\": \"%s\"," +
                        "\"locality\": %s," +
                        "\"categoryId\": %d," +
                        "\"authorId\": %d" +

                        "}",
                post.getId(),
                post.getTitle(),
                post.getDescription(),
                post.getPath() != null ? String.format("\"%s\"", post.getPath()) : null,
                post.getCreatedAtToString(),
                post.getUpdatedAtToString(),
                post.getLocality() != null ? String.format("\"%s\"", post.getLocality()) : null,
                post.getCategoryId(),
                post.getMemberId()
        );
    }

    public static String toJson(List<Post> posts) {

        if (posts.size() == 0) return null;

        StringBuilder jsonPosts = new StringBuilder();

        // get all posts as json objects string
        for (int i = 0; i < posts.size(); i++) {
            jsonPosts.append(toJson(posts.get(i)));

            // not last iteration
            if (i != posts.size() -1) {
                jsonPosts.append(",");
            }
        }

        return jsonPosts.toString();
    }

    private String getDateFormatString(String dateStringToFormat) {
        String[] dateTimeParts = dateStringToFormat.split(" ");
        String[] dateParts = dateTimeParts[0].split("-");
        String[] timeParts = dateTimeParts[1].split(":");
        return dateParts[2] + "-" + dateParts[1] + "-" + dateParts[0] + " " + timeParts[0] + ":" + timeParts[1];
    }
}
