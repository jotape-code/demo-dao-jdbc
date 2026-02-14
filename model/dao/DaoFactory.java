package model.dao;

import java.sql.Connection;

import model.dao.impl.DepartmentDaoJdbc;
import model.dao.impl.SellerDaoJdbc;

public class DaoFactory {
    
    public SellerDao createSellerDao(Connection conn){
        return new SellerDaoJdbc(conn);
    }
    public DepartmentDao createDepartmentDao(Connection conn){
        return new DepartmentDaoJdbc(conn);
    }
}
