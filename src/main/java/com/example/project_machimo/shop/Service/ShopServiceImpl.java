package com.example.project_machimo.shop.Service;

import com.example.project_machimo.shop.Dao.ShopDao;
import com.example.project_machimo.shop.Dto.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service("ShopService")
public class ShopServiceImpl implements ShopService{
    @Autowired
    private SqlSession sqlSession;
    @Override
    public ArrayList<ProductDto> allItemView() {
        log.info("@# allItemView start");
        ShopDao dao = sqlSession.getMapper(ShopDao.class);
        ArrayList<ProductDto> list = dao.allItemView();
        log.info("@# allItemView end");
        return list;
    }

    @Override
    public ArrayList<UsersDto> findNickName(int user_id) {
        log.info("@# findNickName()");
        ShopDao dao = sqlSession.getMapper(ShopDao.class);
        ArrayList<UsersDto> nick = dao.findNickName(user_id);
        return nick;
    }

    @Override
    public ArrayList<ImgDto> viewImage(int product_id) {
        log.info("@# viewImage");
        ShopDao dao = sqlSession.getMapper(ShopDao.class);
        ArrayList<ImgDto> img = dao.viewImage(product_id);
        return img;
    }

    @Override
    public ArrayList<WishlistDto> wishLike(int product_id) {
        log.info("@# viewImage");
        ShopDao dao = sqlSession.getMapper(ShopDao.class);
        ArrayList<WishlistDto> wish = dao.wishLike(product_id);
        return wish;
    }

    @Override
    public ArrayList<CategoryDto> getCategories() {
        log.info("@# getCategories");
        ShopDao dao = sqlSession.getMapper(ShopDao.class);
        ArrayList<CategoryDto> categories = dao.getCategories();
        return categories;
    }

    @Override
    public ArrayList<CategoryDto> getSubCategories(Integer c_id2) {
        log.info("@# getSubCategories");
        ShopDao dao = sqlSession.getMapper(ShopDao.class);
        ArrayList<CategoryDto> subCategories = dao.getSubCategories(c_id2);
        return subCategories;
    }

    @Override
    public List<ProductDto> getProductsBySubcategoryId(int c_id) {
        ShopDao dao = sqlSession.getMapper(ShopDao.class);
        List<ProductDto> getProductsBySubcategoryId = dao.getProductsBySubcategoryId(c_id);
        return getProductsBySubcategoryId;
    }

    @Override
    public List<ProductDto> getProductsBycategoryId(int c_id2) {
        ShopDao dao = sqlSession.getMapper(ShopDao.class);
        List<ProductDto> getProductsBycategoryId = dao.getProductsBycategoryId(c_id2);
        return getProductsBycategoryId;
    }

    @Override
    public ArrayList<CategoryDto> getCategoryById(Integer c_id) {
        ShopDao dao = sqlSession.getMapper(ShopDao.class);
        ArrayList<CategoryDto> getCategoryById = dao.getCategoryById(c_id);
        return getCategoryById;
    }
}
