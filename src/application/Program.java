package application;

import model.dao.DaoDactory;
import model.dao.SellerDao;
import model.dao.impl.SellerDaoJDBC;
import model.entities.Departament;
import model.entities.Seller;

import java.util.Date;
import java.util.List;

public class Program {

    static void main(String[] args) {

        SellerDao sellerDao = DaoDactory.createSellerDao();

        System.out.println("===== TESTE 1: seller findById ========");
        Seller seller = sellerDao.findById(3);

        System.out.println(seller);

        System.out.println("\n===== TESTE 2: seller findByDepartament ========");
        Departament dep = new Departament(1,"Computers");

        List<Seller> list = sellerDao.findByDepartament(dep);

        for (Seller obj: list){
            System.out.println(obj);
        }

    }
}
