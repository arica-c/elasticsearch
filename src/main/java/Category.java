public class Category {
     private int category_Id;
     private String category_title;
     private int category_pages;
     private int category_subcats;
     private int category_files;

     public void setCategory_Id(int category_Id){
          this.category_Id= category_Id;
     }

     public void setCategory_title(String category_title){
          this.category_title= category_title;
     }

     public void setCategory_pages(int category_pages){
          this.category_pages= category_pages;
     }

     public void setCategory_subcats(int category_subcats){
          this.category_subcats= category_subcats;
     }

     public void setCategory_files(int category_files){
          this.category_files= category_files;
     }

     public int getCategory_Id(){
          return this.category_Id;
     }

     public String getCategory_title(){
          return this.category_title;
     }

     public int getCategory_pages(){
          return this.category_pages;
     }

     public int getCategory_subcats(){
          return this.category_subcats;
     }

     public int getCategory_files(){
          return this.category_files;
     }

}
