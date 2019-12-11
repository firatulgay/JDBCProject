package com.mycompany.app;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Hello world!
 */
public class App {
    final static String JDBC_CONNECTION_STR = "jdbc:mysql://localhost:3306/Marvel1?useUnicode=true&useLegacyDatetimeCode=false&serverTimezone=Turkey";
    final static String USERNAME = "root";
    final static String PASSWORD = "123456";

    static Hero hero = new Hero();
    static Movies movies = new Movies();
    static VeritabaniIslemleri veritabaniIslemleri = new VeritabaniIslemleri();
    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("1. Hero ekle. ");
            System.out.println("2. Movie ekle. ");
            System.out.println("3. Her hero'nun oynadığı film bütçe toplamını göster ");
            System.out.println("4. Her hero'nun oynadığı film sayısını göster");

            int secim = scan.nextInt();

            switch (secim) {
                case 1:
                    veritabaniIslemleri.saveHero(hero);
                    break;
                case 2:
                    veritabaniIslemleri.saveMovie(movies);
                    break;
                case 3:
                    veritabaniIslemleri.getHeroBudgetChart();
                    break;
                case 4:
                    veritabaniIslemleri.getHeroMoviChart();
                default:
                    break;
            }
        }
    }


//        veritabaniIslemleri.saveHero(hero);


//        boolean isBaglantiHazir = baglantiyiKontrolEt();
//        if (!isBaglantiHazir) {
//            System.out.println("Bağlantı problemi var. Lütfen kontrol edin.");
//        } else {
//            System.out.println("Bağlantı hazır");
//    }
//}
//    private static boolean baglantiyiKontrolEt() {
//
//        try (Connection conn = DriverManager.getConnection(
//                JDBC_CONNECTION_STR, USERNAME, PASSWORD)) {
//
//            if (conn != null) {
//                return true;
//            } else {
//                System.out.println("Failed to make connection!");
//            }
//
//        } catch (SQLException e) {
//            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
}




