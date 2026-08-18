package model.dao;

import db.DB;
import model.dao.impl.DepartamentDaoJDBC;
import model.dao.impl.SellerDaoJDBC;

public class DaoDactory {

    public static SellerDao createSellerDao(){
        return new SellerDaoJDBC(DB.getconnection());
    }

    public static DepartamentDao createDepartamentDao(){
        return new DepartamentDaoJDBC(DB.getconnection());
    }
}
