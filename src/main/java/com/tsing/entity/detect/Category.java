/**
  * Copyright 2023 json.cn 
  */
package com.tsing.entity.detect;
import java.util.List;

/**
 * Auto-generated: 2023-07-25 13:31:45
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
public class Category {

    private int Id;
    private String CategoryName;
    private List<Items> Items;
    public void setId(int Id) {
         this.Id = Id;
     }
     public int getId() {
         return Id;
     }

    public void setCategoryName(String CategoryName) {
         this.CategoryName = CategoryName;
     }
     public String getCategoryName() {
         return CategoryName;
     }

    public void setItems(List<Items> Items) {
         this.Items = Items;
     }
     public List<Items> getItems() {
         return Items;
     }

}