package model.dao.impl;

import db.DB;
import model.dao.SellerDao;
import model.entities.Departament;
import model.entities.Seller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {

    private Connection conn;

    public SellerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Seller obj) {
        PreparedStatement st = null;

        try {
            st =  conn.prepareStatement("INSERT INTO seller " +
                                        "(NAME, Email, BirthDate, BaseSalary, DepartmentId)"+
                                        "VALUES (?, ?, ?, ?, ?)");

            st.setString(1, obj.getName());
            st.setString(2, obj.getEmail());
            st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
            st.setDouble(4, obj.getBaseSalary());
            st.setInt(5, obj.getDepartament().getId());

            int rowsAffected = st.executeUpdate();

            System.out.println("Done! Rows affected insert " + rowsAffected);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Seller obj) {
        PreparedStatement st = null;

        try {
            st =  conn.prepareStatement("UPDATE  seller SET " +
                    "NAME = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "+
                    "WHERE SELLER.ID = ?");

            st.setString(1, obj.getName());
            st.setString(2, obj.getEmail());
            st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
            st.setDouble(4, obj.getBaseSalary());
            st.setInt(5, obj.getDepartament().getId());
            st.setInt(6, obj.getId());

            int rowsAffected = st.executeUpdate();

            System.out.println("Done! Rows affected update " + rowsAffected);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;

        try {
            st =  conn.prepareStatement("DELETE FROM seller WHERE ID = ?");

            st.setInt(1, id);

            int rowsAffected = st.executeUpdate();

            System.out.println("Done! Rows affected DELETE " + rowsAffected);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public Seller findById(Integer id) {

        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    "select s.id, s.Name, s.Email, s.BirthDate, s.BaseSalary, s.DepartmentId, d.Name as DepName" +
                    " from   seller s  " +
                    "   inner join department  d " +
                    "     on d.id = s.DepartmentId " +
                    " where s.id = ?");

            st.setInt(1, id);

            rs = st.executeQuery();

            if (rs.next()){
                Departament dep = instantiateDepartament(rs);

                Seller obj = instantiateSeller(rs, dep);

                return obj;
            }else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);

        }
    }

    private Seller instantiateSeller(ResultSet rs, Departament dep) throws SQLException {
        Seller obj = new Seller();
        obj.setId(rs.getInt("id"));
        obj.setName(rs.getString("name"));
        obj.setEmail(rs.getString("Email"));
        obj.setBaseSalary(rs.getDouble("BaseSalary"));
        obj.setBirthDate(rs.getDate("BirthDate"));
        obj.setDepartament(dep);

        return obj;
    }

    private Departament instantiateDepartament(ResultSet rs) throws SQLException {
        Departament dep = new Departament();
        dep.setId(rs.getInt("DepartmentId"));
        dep.setName(rs.getString("DepName"));

        return dep;
    }

    @Override
    public List<Seller> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    "select s.id, s.Name, s.Email, s.BirthDate, s.BaseSalary, s.DepartmentId, d.Name as DepName" +
                            " from   seller s  " +
                            "   inner join department  d " +
                            "     on d.id = s.DepartmentId " +
                            " order by Name");

            rs = st.executeQuery();

            List<Seller> list = new ArrayList<>();
            Map<Integer, Departament> map = new HashMap<>();

            while (rs.next()){

                Departament dep = map.get(rs.getInt("DepartmentId"));
                if (dep == null){
                    dep = instantiateDepartament(rs);
                    map.put(rs.getInt("DepartmentId"), dep);
                }

                Seller obj = instantiateSeller(rs, dep);
                list.add(obj);
            }

            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Seller> findByDepartament(Departament dep) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    "select s.id, s.Name, s.Email, s.BirthDate, s.BaseSalary, s.DepartmentId, d.Name as DepName" +
                            " from   seller s  " +
                            "   inner join department  d " +
                            "     on d.id = s.DepartmentId " +
                            " where s.DepartmentId = ?" +
                            " order by Name");

            st.setInt(1, dep.getId());

            rs = st.executeQuery();

            List<Seller> list = new ArrayList<>();
            Map<Integer, Departament> map = new HashMap<>();

            while (rs.next()){

                Departament dep1 = map.get(rs.getInt("DepartmentId"));

                if (dep1 == null){
                    dep1 = instantiateDepartament(rs);
                    map.put(rs.getInt("DepartmentId"), dep1);
                }

                Seller obj = instantiateSeller(rs, dep1);
                list.add(obj);
            }

            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

}
