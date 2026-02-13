package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'insert'");
    }

    @Override
    public void update(Seller obj) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
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
    public List<Seller> findAll(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
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
