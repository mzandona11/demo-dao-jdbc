package application;

import model.dao.DaoDactory;
import model.dao.DepartamentDao;
import model.dao.SellerDao;
import model.dao.impl.DepartamentDaoJDBC;
import model.entities.Departament;
import model.entities.Seller;

import java.util.Date;
import java.util.List;

public class Programa2 {

    static void main(String[] args) {

        DepartamentDao DepartamentDao = DaoDactory.createDepartamentDao();

        System.out.println("===== TESTE 1: departament findById ========");
        Departament dep = DepartamentDao.findById(3);

        System.out.println(dep);

        System.out.println("\n===== TESTE 2: departament findAll ========");
        List<Departament> list2 = DepartamentDao.findAll();

        for (Departament obj: list2){
            System.out.println(obj);
        }

        System.out.println("\n===== TESTE 3: departament insert ========");
        Departament dep1 = new Departament(null, "Eletronics insert ");
        DepartamentDao.insert(dep1);


        System.out.println("\n===== TESTE 4: departament update ========");
        Departament dep2 = new Departament(4, "Generics update test");
        DepartamentDao.update(dep2);

        System.out.println("\n===== TESTE 5: departament DELETE ========");
        DepartamentDao.deleteById(5);

    }
}
