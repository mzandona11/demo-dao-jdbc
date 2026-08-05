package application;

import model.dao.DaoDactory;
import model.dao.SellerDao;
import model.entities.Departament;
import model.entities.Seller;

import java.util.Date;

public class Program {

    static void main(String[] args) {

        SellerDao sellerDao = DaoDactory.createSellerDao();

        System.out.println("===== TESTE 1: seller findById ========");
        Seller seller = sellerDao.findById(3);

        System.out.println(seller);

    }
}
