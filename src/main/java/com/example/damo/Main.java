package com.example.damo;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {
    static final String DB_URL = "jdbc:postgresql://localhost:5432/test";
    static final String USER = "postgres";
    static final String PASS = "shashpa";
    static final String QUERY = "select u.login, u.email, ua.account_number, ua.currency " +
            "from users u join user_account ua on u._id = ua.id_users";

    public static <UserWithAccount> void main(String[] args) {
        ArrayList<UserWithAccount> list = new ArrayList<>();
        HashMap<String, BigDecimal> hashMap = new HashMap<>();
        try(Connection com = DriverManager.getConnection(DB_URL, USER, PASS);
            Statement stmt = com.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY)) {
            while (rs.next()) {
                UserWithAccount user = new UserWithAccount();
                user.setLogin(rs.getString("login"));
                user.setEmail(rs.getString("email"));
                user.setAccount_number(rs.getString("account_number"));
                user.setCurrency(rs.getBigDecimal("currency"));

                System.out.println(user.toString());
                list.add(user);
            }

            for (UserWithAccount user : list){
                hashMap.put(user.getLogin(), user.getCurrency());

            }
            for (Map.Entry<String, BigDecimal> sum: hashMap.entrySet()) {
                System.out.println("login = " + sum.getKey() + "\ncurrency = " + sum.getValue());
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
