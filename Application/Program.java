package Application;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

import db.DB;
import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;


public class Program {
    public static void main(String[] args){
        Connection conn = DB.getConnection();
        DaoFactory factory = new DaoFactory();
        DepartmentDao depDao = factory.createDepartmentDao(conn);

        Department dep = new Department(1, "Computers");
        dep.setName("Cellphone");
        depDao.update(dep);

       

        
    }
}
