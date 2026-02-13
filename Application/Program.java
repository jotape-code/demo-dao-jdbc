package Application;

import java.sql.Connection;
import java.time.LocalDate;


import db.DB;
import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;


public class Program {
    public static void main(String[] args){
        Seller sel = new Seller(10, "Lula", "bababoi@gmail.com", LocalDate.parse("2006-06-04"), 2000.0, new Department(2, "Eletronics"));
        Connection conn = DB.getConnection();

        sel.setEmail("peitanao@gmail.com");
        DaoFactory factory = new DaoFactory();
        SellerDao sellerDao = factory.createSellerDao(conn);
        sellerDao.update(sel);

        
    }
}
