package model.dao;

import java.sql.Connection;

import model.dao.impl.SellerDaoJdbc;

public class DaoFactory {
    
    public SellerDao createSellerDao(Connection conn){
        return new SellerDaoJdbc(conn);
    }
}
