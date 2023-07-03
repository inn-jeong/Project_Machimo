package com.example.project_machimo.review.service;

import com.example.project_machimo.review.dto.Criteria;
import com.example.project_machimo.review.dto.ReviewDto;

import java.util.ArrayList;
import java.util.HashMap;


public interface ReviewService {
    ArrayList<ReviewDto> list();
    //페이징 처리 목록(오버로딩)
    ArrayList<ReviewDto> list(Criteria cri);
    void write(HashMap<String,String> param);
    ReviewDto contentView(HashMap<String,String> param);
    void modify(HashMap<String,String> param);
    ReviewDto modify_view(String reviewId);

    void delete(String reviewId);

    int getTotalCount();

//    int updateCount();


    void updateCount(int reviewId);


}
