package com.mycompany.app;

import java.sql.*;
import java.util.Scanner;

public class VeritabaniIslemleri {

    final static String JDBC_CONNECTION_STR = "jdbc:mysql://localhost:3306/Marvel1?useUnicode=true&useLegacyDatetimeCode=false&serverTimezone=Turkey";
    final static String USERNAME = "root";
    final static String PASSWORD = "123456";

    public void saveHero(Hero hero) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Hero Adı Giriniz");
        String adi = scan.nextLine();
        hero.setAd(adi);
        System.out.println("Hero Soyadı Giriniz");
        String soyadi = scan.nextLine();
        hero.setSoyad(soyadi);

        String sql = "insert into hero (name, surname)" +
                "values (?, ?) ";
        try (Connection conn = DriverManager.getConnection(JDBC_CONNECTION_STR, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setString(1, hero.getAd());
            preparedStatement.setString(2, hero.getSoyad());

            preparedStatement.executeUpdate();
            System.out.println(" Hero eklendi.");
            System.out.println("----------Hero List----------");
            /**
             *Eklenen Heroyu listede gösteren metot.
             */
            getHeroList();
            System.out.println("----------Hero List----------");

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void saveMovie(Movies movie) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Film Adı Giriniz");
        String filmAdi = scan.nextLine();
        movie.setMovieName(filmAdi);
        System.out.println("Film Bütçesi Giriniz");
        int filmBudget = scan.nextInt();
        movie.setBudget(filmBudget);

        System.out.println("Filme eklemek istediğiniz Hero'nun id'sini giriniz.");
        System.out.println("----------------------------------------------------");
        /**
         * tüm hero listesi ekrana getirilir kullanıcının film eklemek istediği hero, 'id' parametresiyle seçilir.
         */
        getHeroList();
        int idHero = scan.nextInt();

        String sql = "insert into movies (movie, budget, Hero_id)" +
                "values (?, ?, ?) ";
        try (Connection conn = DriverManager.getConnection(JDBC_CONNECTION_STR, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            preparedStatement.setString(1, movie.getMovieName());
            preparedStatement.setInt(2, movie.getBudget());
            preparedStatement.setInt(3, idHero);

            int affectedRows = preparedStatement.executeUpdate();
            System.out.println(idHero + " id'li hero filme eklendi");
            System.out.println("----------Movie List----------");

            /**
             * Hero eklendikten sonra movie'nn son halininde bulunduğu film listesi getirilir.
             */
            getMovies();
            System.out.println("----------Movie List----------");
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getMovies() {

        String sql = "Select * from movies";

        try (Connection conn = DriverManager.getConnection(JDBC_CONNECTION_STR, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("Hero_id");
                String movie = resultSet.getString("movie");
                String budget = resultSet.getString("budget");

                System.out.printf("%d - %s - %s \n", id, movie, budget);
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void getHeroList() {

        String sql = "Select * from hero";

        try (Connection conn = DriverManager.getConnection(JDBC_CONNECTION_STR, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("Name");
                String surname = resultSet.getString("Surname");

                System.out.printf("%d - %s - %s \n", id, name, surname);
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void getHeroBudgetChart() {

        String sql = "select concat(hero.name,' ',hero.surname) as heroName,ifnull(Sum(movies.budget),0) as totalBudget\n" +
                " from hero left join movies on hero.id = movies.hero_id  \n" +
                " group by concat(hero.name,' ',hero.surname)\n" +
                " order by Sum(movies.budget) desc";

        try (Connection conn = DriverManager.getConnection(JDBC_CONNECTION_STR, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String heroName = resultSet.getString("heroName");
                String totalBudget = resultSet.getString("totalBudget");

                System.out.printf("Hero: %s - Toplam Bütçe: %s \n", heroName, totalBudget);
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void getHeroMoviChart() {
        String sql = "select concat(hero.name,' ',hero.surname) as heroName,  Count(distinct movies.movie) as movieCount\n" +
                " from hero inner join movies on hero.id = movies.hero_id group by concat(hero.name,' ',hero.surname)\n" +
                " order by movieCount desc";

        try (Connection conn = DriverManager.getConnection(JDBC_CONNECTION_STR, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String heroName = resultSet.getString("heroName");
                String movieCount = resultSet.getString("movieCount");

                System.out.printf("Hero: %s - Film Sayısı : %s \n", heroName, movieCount);
            }
        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}



