package application;

import model.entities.Departament;
import model.entities.Seller;

import java.util.Date;

public class Program {

    static void main(String[] args) {
        Departament obj = new Departament(1, "Books");

        Seller seller = new Seller(21, "Matheus", "matheuszandona11@gmail.com", new Date(), obj);

        System.out.println(seller);
    }
}
