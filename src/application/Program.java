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
        Departament dep = new Departament(2,null);

        List<Seller> list = sellerDao.findByDepartament(dep);

        for (Seller obj: list){
            System.out.println(obj);
        }

        System.out.println("\n===== TESTE 3: seller findAll ========");
        List<Seller> list2 = sellerDao.findAll();

        for (Seller obj: list2){
            System.out.println(obj);
        }

        System.out.println("\n===== TESTE 4: seller insert ========");
        Seller seller1 = new Seller(null, "Matheus Texto 2", "matheus@gmail.com", new Date(), 5000.00,dep);
        sellerDao.insert(seller1);

    }
}
