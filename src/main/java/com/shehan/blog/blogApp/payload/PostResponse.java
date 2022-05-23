package com.shehan.blog.blogApp.payload;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
public class PostResponse {
    private List<PostDTO> content;
    private int pageNo;
    private int pageSize;
    private int totalPages;
    private boolean last;
    private Long totalElemets;

    public List<PostDTO> getContent() {
        return content;
    }

    public void setContent(List<PostDTO> content) {
        this.content = content;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public boolean isLast() {
        return last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public Long getTotalElemets() {
        return totalElemets;
    }

    public void setTotalElemets(Long totalElemets) {
        this.totalElemets = totalElemets;
    }
}
