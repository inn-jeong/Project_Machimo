package com.example.project_machimo.review.dao;

import com.example.project_machimo.review.dto.Criteria;
import com.example.project_machimo.review.dto.ReviewDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.HashMap;
@Mapper
public interface ReviewDao {
    ArrayList<ReviewDto> list();
    ArrayList<ReviewDto> listWithPaging(Criteria cri);
    void write(HashMap<String,String> param);
    ReviewDto contentView(HashMap<String,String> param);
    void modify(HashMap<String,String> param);

    ReviewDto modify_view(String reviewId);

    void delete(String param);

    int getTotalCount();
}
