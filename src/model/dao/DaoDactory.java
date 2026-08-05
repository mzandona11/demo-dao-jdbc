package model.dao;

import model.dao.impl.SellerDaoJDBC;

public class DaoDactory {

    public static SellerDao createSellerDao(){
        return new SellerDaoJDBC();
    }
}
