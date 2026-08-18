package model.dao.impl;

import db.DB;
import model.dao.DepartamentDao;
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

public class DepartamentDaoJDBC implements DepartamentDao {

    Connection conn;

    public DepartamentDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Departament obj) {
        PreparedStatement st = null;

        try {
            st =  conn.prepareStatement("INSERT INTO department " +
                    "(NAME)"+
                    "VALUES (?)");

            st.setString(1, obj.getName());

            int rowsAffected = st.executeUpdate();

            System.out.println("Done! Rows affected insert " + rowsAffected);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Departament obj) {
        PreparedStatement st = null;

        try {
            st =  conn.prepareStatement("UPDATE  department SET " +
                    "NAME = ?"+
                    "WHERE ID = ?");

            st.setString(1, obj.getName());
            st.setInt(2, obj.getId());

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
            st =  conn.prepareStatement("DELETE FROM department WHERE ID = ?");

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
    public Departament findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    "select d.id, d.Name" +
                            " from   department d  " +
                            " where id = ?");

            st.setInt(1, id);

            rs = st.executeQuery();

            if (rs.next()){

                Departament obj = instantiateDepartament(rs);

                return obj;
            }else{
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Departament> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                    "select d.id, d.Name" +
                            " from   department d  " +
                            " order by Name");

            rs = st.executeQuery();

            List<Departament> list = new ArrayList<>();

            while (rs.next()){

                Departament obj = instantiateDepartament(rs);
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

    private Departament instantiateDepartament(ResultSet rs) throws SQLException {
        Departament dep = new Departament();
        dep.setId(rs.getInt("Id"));
        dep.setName(rs.getString("Name"));

        return dep;
    }
}
