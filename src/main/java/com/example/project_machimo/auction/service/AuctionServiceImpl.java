package com.example.project_machimo.auction.service;

import com.example.project_machimo.auction.dao.AuctionDAO;
import com.example.project_machimo.auction.dao.ProductsDAO;
import com.example.project_machimo.auction.dao.UserDAO;
import com.example.project_machimo.auction.dto.AuctionVO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuctionServiceImpl implements AuctionService{

    private final AuctionDAO auctionDAO;
    private final UserDAO userDAO;

    @Autowired
    public AuctionServiceImpl(AuctionDAO auctionDAO, UserDAO userDAO) {
        this.auctionDAO = auctionDAO;
        this.userDAO = userDAO;
    }



    @Override
    public AuctionVO aList(int id) {
        AuctionVO auctionVOS = auctionDAO.pList(id);
        return auctionVOS;
    }

    @Override
    public void highestBidUpdate(Long amount, int id,Integer userId) {

        auctionDAO.highestBidUpdate(amount,id);
    }

    @Override
    public int getUserId(Integer integer) {
        return userDAO.getUserId(integer);
    }
}
