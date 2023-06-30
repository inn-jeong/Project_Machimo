package com.example.project_machimo.admin.dao;

import com.example.project_machimo.admin.dto.BoardDto;
import com.example.project_machimo.admin.dto.Criteria;
import com.example.project_machimo.admin.dto.ProductDto;
import com.example.project_machimo.admin.dto.UsersDto;

import java.util.ArrayList;
import java.util.HashMap;

public interface AdminDao {
    ArrayList<UsersDto> adminList(Criteria cri);
    public int getTotalCount();
    public void adminDelete(int userId);

    UsersDto userView(HashMap<String, String> param);

    ArrayList<BoardDto> BoardList(Criteria cri);

    void boardWrite(BoardDto dto);
    public Integer updateHits(Integer boardId);

    BoardDto boardView(int boardId);

    void adminModify(int userId);

    void boardWrite(HashMap<String, Object> param);

    void boardModify(BoardDto dto);

    void boardDelete(int boardId);

    ArrayList<ProductDto> pList(Criteria cri);


    void updateStatus(int ProductId, int PSalesStatus);


}
