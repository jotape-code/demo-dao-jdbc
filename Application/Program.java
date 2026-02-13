package Application;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

import db.DB;
import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;


public class Program {
    public static void main(String[] args){
        Seller sel = new Seller(null, "Lula", "Lulaladrao@gmail.com", LocalDate.parse("2006-06-04"), 2000.0, new Department(2, "Eletronics"));
        Connection conn = DB.getConnection();

        DaoFactory d = new DaoFactory();
        SellerDao sd = d.createSellerDao(conn);
        sd.insert(sel);
        System.out.println("Inserted! New id = " + sel.getId());


        
    }
}
