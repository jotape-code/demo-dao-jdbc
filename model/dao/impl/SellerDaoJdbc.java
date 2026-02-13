package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.sql.Statement;
import java.util.List;
import java.util.Map;



import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJdbc implements SellerDao {

    private Connection conn;
    
    public SellerDaoJdbc(Connection conn){
        this.conn = conn;
    }

    @Override
    public void insert(Seller obj) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement("INSERT INTO seller\r\n" + //
                                "(Name, Email, BirthDate, BaseSalary, DepartmentId)\r\n" + //
                                "VALUES\r\n" + //
                                "(?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            st.setString(1, obj.getName());
            st.setString(2, obj.getEmail());
            st.setObject(3, obj.getBirthDate());
            st.setDouble(4, obj.getBaseSalary());
            st.setInt(5, obj.getDepartment().getId());
            
            int rows = st.executeUpdate();
            if(rows > 0){
                ResultSet rs = st.getGeneratedKeys();
                while(rs.next()){
                    int id = rs.getInt(1);
                    obj.setId(id);
                }
                rs.close();
            }
            else{
                throw new DbException("Unexpected erros! No rows added");
            }
        }
        catch(SQLException e){
            throw new DbException(e.getMessage());
        }
        finally{
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Seller obj) {
        PreparedStatement st = null;
        try{
            st = conn.prepareStatement("UPDATE seller\r\n" + //
                                "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ?\r\n" + //
                                "WHERE Id = ?");
            st.setString(1, obj.getName());
            st.setString(2, obj.getEmail());
            st.setObject(3, obj.getBirthDate());  
            st.setDouble(4, obj.getBaseSalary());  
            st.setInt(5, obj.getDepartment().getId());
            st.setInt(6, obj.getId());            

            st.executeUpdate();
        }   
        catch(SQLException e){
            throw new DbException(e.getMessage());
        }
        finally{
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteById'");
    }

    @Override
    public Seller findById(Integer id) {
       PreparedStatement st = null;
       ResultSet rs = null;
       try{
            st = conn.prepareStatement("SELECT seller.*,department.Name as DepName\r\n" +
                                "FROM seller INNER JOIN department\r\n" + 
                                "ON seller.DepartmentId = department.Id\r\n" +
                                "WHERE seller.Id = ?");
            st.setInt(1, id);
            rs = st.executeQuery();
            if(rs.next()){
                Department dep = instatiateDepartment(rs);
                Seller sel = instatiateSeller(rs, dep);
                return sel;
            }
            return null;
       }
       catch(SQLException e){
            throw new DbException(e.getMessage());
       }
       finally{
        DB.closeResultSet(rs);
        DB.closeStatement(st);
       }
    }

    @Override
    public List<Seller> findAll() {
        Statement st = null;
        ResultSet rs = null;
        try{
            st =  conn.createStatement();
            rs = st.executeQuery("SELECT seller.*,department.Name as DepName\r\n" + //
                                "FROM seller INNER JOIN department\r\n" + //
                                "ON seller.DepartmentId = department.Id\r\n" + //
                                "ORDER BY Name");
            Map<Integer, Department> deps = new  HashMap<>();
            List<Seller> sels = new ArrayList<>();

            while(rs.next()){
                final Integer departmentId = rs.getInt("DepartmentId");

                Seller sel = null;
                Department dep_aux = null;
                if(!deps.containsKey(departmentId)){
                    dep_aux = instatiateDepartment(rs);
                    deps.put(departmentId, dep_aux);
                }
                else{
                    dep_aux = deps.get(departmentId);
                }
                sel = instatiateSeller(rs, dep_aux);
                sels.add(sel);
            }
            return sels;
        }
        catch(SQLException e){
            throw new DbException(e.getMessage());
        }
        finally{
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }
    @Override
    public List<Seller> findByDepartment(Department dep){
        PreparedStatement st = null;
        ResultSet rs = null;
        try{
            st = conn.prepareStatement("SELECT seller.*,department.Name as DepName\r\n" +
                                "FROM seller INNER JOIN department\r\n" + 
                                "ON seller.DepartmentId = department.Id\r\n" + 
                                "WHERE DepartmentId = ?\r\n" + 
                                "ORDER BY Name");
            st.setInt(1, dep.getId());
            rs = st.executeQuery();
            Map<Integer, Department> deps = new  HashMap<>();
            List<Seller> sels = new ArrayList<>();

            while(rs.next()){
                final Integer departmentId = rs.getInt("DepartmentId");

                Seller sel = null;
                Department dep_aux = null;
                if(!deps.containsKey(departmentId)){
                    dep_aux = instatiateDepartment(rs);
                    deps.put(departmentId, dep_aux);
                }
                else{
                    dep_aux = deps.get(departmentId);
                }
                sel = instatiateSeller(rs, dep_aux);
                sels.add(sel);
            }
            return sels;
        }
        catch(SQLException e){
            throw new DbException(e.getMessage());
        }
        finally{
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }


    private Department instatiateDepartment(ResultSet rs) throws SQLException{
        Department dep = new Department();
        dep.setId(rs.getInt("DepartmentId"));
        dep.setName(rs.getString("DepName"));
        return dep;
    }
    
    private Seller instatiateSeller(ResultSet rs, Department dep) throws SQLException{
        Seller sel = new Seller();
        sel.setId(rs.getInt("Id"));
        sel.setName(rs.getString("Name"));
        sel.setEmail(rs.getString("Email"));
        sel.setBaseSalary(rs.getDouble("BaseSalary"));
        sel.setBirthDate(rs.getObject("BirthDate", LocalDate.class));
        sel.setDepartment(dep);
        return sel;
    }
    


}
