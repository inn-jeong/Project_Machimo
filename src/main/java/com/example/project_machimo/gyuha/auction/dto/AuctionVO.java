package com.example.project_machimo.gyuha.auction.dto;

import java.sql.Timestamp;

public record AuctionVO(
        Integer auctionId
        , Integer userId
        ,Integer productsId
        , Timestamp startTime
        , Timestamp endTime
        , Integer highestBid
        ) {
}
