package com.example.elasticsearch.services;

import com.example.elasticsearch.models.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MysqlConnection{

    public List<Category> sqlConnect(String query) {
        ArrayList<Category> arrayList = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("connected");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/Elastic", "root", "2Brokegirls@");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            arrayList = new ArrayList<>();
            while (rs.next()) {
                Category category = new Category();
                category.setCategory_Id(rs.getInt("cat_id"));
                category.setCategory_title(rs.getString("cat_title"));
                category.setCategory_pages(rs.getInt("cat_pages"));
                category.setCategory_subcats(rs.getInt("cat_subcats"));
                category.setCategory_files(rs.getInt("cat_files"));
                category.setTags(rs.getString("tags"));
                arrayList.add(category);
            }

            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        return arrayList;
    }


}