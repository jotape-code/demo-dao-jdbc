package Application;

import java.util.List;

import db.DB;
import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;


public class Program {
    public static void main(String[] args){
        DaoFactory fc = new DaoFactory();
        SellerDao seller = fc.createSellerDao(DB.getConnection());
        Department dep = new Department(2,"Computers");
        List<Seller> s = seller.findByDepartment(dep);
        System.out.println(s);

        
    }
}
