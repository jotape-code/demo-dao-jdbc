package Application;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import db.DB;
import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;


public class Program {
    public static void main(String[] args){
    int opc = 0;
    Scanner sc = new Scanner(System.in);
    Connection conn = DB.getConnection();
    
    while(opc != 3){
        System.out.println("1-Manipulate sellers table");
        System.out.println("2-Manipulate department table");
        System.out.println("3-Quit");
        opc = sc.nextInt();

        switch (opc) {
            case 1:{
                int opc2 = 0;
                DaoFactory ft = new DaoFactory();
                SellerDao sellerDao= ft.createSellerDao(conn);

                while(opc2 != 7){
                    System.out.println("1-Insert seller");
                    System.out.println("2-Update seller data");
                    System.out.println("3-Delete seller by ID");
                    System.out.println("4-Find seller by ID");
                    System.out.println("5-Show all sellers");
                    System.out.println("6-Find seller by department");
                    System.out.println("7-Back");
                    opc2 = sc.nextInt();

                    switch(opc2){
                        case 1:{
                            DepartmentDao departmentDao = ft.createDepartmentDao(conn);

                            sc.nextLine();
                            System.out.println("Enter the employee's name: ");
                            String name = sc.nextLine();
                            System.out.println("Enter the employee's email: ");
                            String email = sc.nextLine();
                            System.out.println("Enter the employee's Birth Date: ");
                            LocalDate date = LocalDate.parse(sc.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                            System.out.println("Enter the employee's salary: ");
                            Double salary = sc.nextDouble();
                            System.out.println("Enter the employee's Department Id: " );
                            Integer depId = sc.nextInt();
                            Department dep = departmentDao.findById(depId);
                            Seller sel = new Seller(null, name, email, date, salary, dep);
                            sellerDao.insert(sel);
                            break;
                        }
                        case 2:{
                            DepartmentDao departmentDao = ft.createDepartmentDao(conn);

                            sc.nextLine();
                            System.out.println("Enter the employee's id: ");
                            int id = sc.nextInt();
                            System.out.println("Enter the employee's name: ");
                            sc.nextLine();
                            String name = sc.nextLine();
                            System.out.println("Enter the employee's email: ");
                            String email = sc.nextLine();
                            System.out.println("Enter the employee's Birth Date: ");
                            LocalDate date = LocalDate.parse(sc.nextLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                            System.out.println("Enter the employee's salary: ");
                            Double salary = sc.nextDouble();
                            System.out.println("Enter the employee's Department Id: " );
                            Integer depId = sc.nextInt();
                            Department dep = departmentDao.findById(depId);
                            Seller sel = new Seller(id, name, email, date, salary, dep);
                            sellerDao.update(sel);
                            break;
                        }
                        case 3:{
                            System.out.println("Enter the employee's id: ");
                            int id = sc.nextInt();
                            sellerDao.deleteById(id);
                            break;
                        }
                        case 4:{
                            System.out.println("Enter the employee's id: ");
                            int id = sc.nextInt();
                            System.out.println(sellerDao.findById(id));
                            break;
                        }

                        case 5:{
                            List<Seller> list = sellerDao.findAll();
                            for(Seller sel : list){
                                System.out.println(sel);
                            }
                            break;
                        }
                        case 6:{
                            DepartmentDao depDao = ft.createDepartmentDao(conn);

                            System.out.println("Enter the department Id: ");
                            int id = sc.nextInt();
                            Department dep = depDao.findById(id);
                            List<Seller> list = sellerDao.findByDepartment(dep);
                            for(Seller sel : list){
                                System.out.println(sel);
                            }
                            break;
                        }
                    }
                


                }
                break;
            }
            case 2:{
                DaoFactory ft = new DaoFactory();
                DepartmentDao depDao = ft.createDepartmentDao(conn);
                int opc2 = 0;

                while(opc2 != 6){
                    System.out.println("1-Insert department");
                    System.out.println("2-Update department data");
                    System.out.println("3-Delete department by ID");
                    System.out.println("4-Find department by ID");
                    System.out.println("5-Show all Departments");
                    System.out.println("6-Back");
                    opc2 = sc.nextInt();
                
                switch (opc2) {
                    case 1:{
                        sc.nextLine();

                        System.out.println("Enter department name: ");
                        String name = sc.nextLine();
                        Department dep = new Department(null, name);
                        depDao.insert(dep);
                        break;
                    }
                
                    case 2:{
                        System.out.println("Enter department id: ");
                        int id = sc.nextInt();
                        System.out.print("Enter new department name: ");
                        sc.nextLine();
                        String name = sc.nextLine();
                        Department dep = new Department(id, name);
                        depDao.update(dep);
                        break;
                    }
                    case 3:{
                        System.out.println("Enter Department id: ");
                        int id = sc.nextInt();
                        depDao.deleteById(id);
                        break;
                    }
                    case 4:{
                        System.out.println("Enter department id: ");
                        int id = sc.nextInt();
                        System.out.println(depDao.findById(id));
                        break;
                    }
                    case 5:{
                        List<Department> deps = depDao.findAll();
                        for(Department dep : deps){
                            System.out.println(dep);
                        }
                        break;
                    }
                }

                }

                break;
            }
            
        }
    }
        

        DB.closeConnection();
        sc.close();
    }
}
