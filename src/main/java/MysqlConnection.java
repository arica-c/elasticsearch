import java.sql.*;
import java.util.ArrayList;

class MysqlConnection{
    public static void main(String args[]){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("connected");
            Connection con=DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/Elastic","root","2Brokegirls@");
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select * from category");
            ArrayList<Category> arrayList= new ArrayList<>();
            while(rs.next()) {
                Category category = new Category();
                category.setCategory_Id(rs.getInt("cat_id"));
                category.setCategory_title(rs.getString("cat_title"));
                category.setCategory_pages(rs.getInt("cat_pages"));
                category.setCategory_subcats(rs.getInt("cat_subcats"));
                category.setCategory_files(rs.getInt("cat_files"));
                arrayList.add(category);
                // System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));
            }
            for(Category c: arrayList){
                System.out.println(c.getCategory_Id()+ "  "+ c.getCategory_title()+ "  "+ c.getCategory_pages() + "  "+ c.getCategory_subcats()+"  "+ c.getCategory_files());
            }
            System.out.println(arrayList.size());
            con.close();
        }catch(Exception e){ System.out.println(e);}
    }
}