package com.example.project_machimo.alert.service;

import com.example.project_machimo.alert.dao.AlertDAO;
import com.example.project_machimo.alert.dto.AlertVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlertServiceImpl implements AlertService {

    private final AlertDAO alertDAO;

    @Autowired
    public AlertServiceImpl(AlertDAO alertDAO) {
        this.alertDAO = alertDAO;
    }

    @Override
    public void executeWinningBid() {

    }

    @Override
    public void failedAuctionNoBids() {

    }

    @Override
    public List<AlertVO> alList(int uId) {
        return alertDAO.alList(uId);
    }

    @Override
    public void checkedFlag(int alId) {
        alertDAO.checkedFlag(alId);
    }

    @Override
    public Integer alertResult(int uId) {
        return alertDAO.alertResult(uId);
    }
}
