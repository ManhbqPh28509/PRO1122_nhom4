package com.example.pro1122_nhm4.DbHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "mydb";
    private static final int DATABASE_VERSION = 1;
    public DbHelper(@Nullable Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_TV = "CREATE TABLE User (user_id INTEGER primary key autoincrement, hoten text not null, ngaysinh date not null, sdt text not null, diachi text not null, matkhau text not null, email text not null, user_role text not null)";
        db.execSQL(CREATE_TABLE_TV);
        db.execSQL("INSERT INTO User VALUES (1,'Bùi Quang Mạnh','2003-02-09','0984938203','Số 14, Ngách 6 Ngõ 113 Đường Đan Khê Xã Di Trạch, Huyện Hoài Đức, Hà Nội','manh123','manhbqph28509@fpt.edu.vn','admin')," +
                "(2,'Nguyễn Thế Việt','2003-09-08','0984943646','Số 14, Ngách 6 Ngõ 113 Đường Đan Khê Xã Di Trạch, Huyện Hoài Đức, Hà Nội','viet123','theviet89@gmail.com','user')");
        String CREATE_TABLE_CATEGORY = "CREATE TABLE Category (category_id integer primary key autoincrement, name text not null, img text not null)";
        db.execSQL(CREATE_TABLE_CATEGORY);
        db.execSQL("INSERT INTO Category VALUES (1,'Món khai vị','https://cdn-icons-png.flaticon.com/512/8502/8502775.png')," +
                "(2,'Món chính','https://cdn-icons-png.flaticon.com/512/1037/1037855.png'),"+
                "(3,'Món phụ','https://static.vecteezy.com/system/resources/previews/051/897/617/non_2x/a-bowl-of-food-with-tomatoes-and-lettuce-vector.jpg'),"+
                "(4,'Tráng miệng','https://cdn-icons-png.flaticon.com/512/3173/3173443.png'),"+
                "(5,'Đồ uống','https://cdn-icons-png.flaticon.com/512/4682/4682506.png'),"+
                "(6,'Súp','https://cdn-icons-png.flaticon.com/512/2387/2387954.png'),"+
                "(7,'Mì/Nui','https://cdn-icons-png.flaticon.com/512/2276/2276941.png'),"+
                "(8,'Cơm','https://cdn-icons-png.flaticon.com/512/2515/2515271.png'),"+
                "(9,'Hải sản','https://cdn-icons-png.flaticon.com/512/7780/7780149.png'),"+
                "(10,'Món chay','https://cdn-icons-png.flaticon.com/512/2515/2515263.png'),"+
                "(11,'Pizza','https://cdn-icons-png.flaticon.com/512/9633/9633377.png'),"+
                "(12,'Burgers','https://cdn-icons-png.flaticon.com/512/5787/5787014.png'),"+
                "(13,'Salads','https://cdn-icons-png.flaticon.com/512/8512/8512332.png'),"+
                "(14,'Món nướng','https://cdn-icons-png.flaticon.com/512/4829/4829131.png')");
        String CREATE_TABLE_DISH = "CREATE TABLE Dish (dish_id integer primary key autoincrement, name text not null,price integer not null, description text not null,availability text not null,category_id integer REFERENCES Category(category_id) , img text not null)";
        db.execSQL(CREATE_TABLE_DISH);
        db.execSQL("INSERT INTO Dish VALUES (1,'Súp hải sản',55000,'Súp hải sản là một món ăn phổ biến ở nhiều nền ẩm thực, từ phương Đông đến phương Tây. Món này thường có vị ngọt tự nhiên của hải sản và sự hòa quyện của nhiều loại nguyên liệu.','Còn hàng',1,'https://shaitien.com/upload/product/1519737225_dd57f910b50b01d70aa945e140064049.jpeg'),"+
                "(2,'Gỏi cuốn', 75000,'Gỏi cuốn là món ăn truyền thống của Việt Nam, nổi bật với sự tươi ngon và cách trình bày bắt mắt. Đây là một món ăn lành mạnh, dễ làm và rất phổ biến trong các bữa tiệc hoặc món ăn nhẹ hàng ngày.','Còn hàng',1,'https://khaihoanphuquoc.com.vn/wp-content/uploads/2023/11/nu%CC%9Bo%CC%9B%CC%81c-ma%CC%86%CC%81m-cha%CC%82%CC%81m-go%CC%89i-cuo%CC%82%CC%81n.png'),"+
                "(3,'Bánh mì nướng bơ tỏi',40000,'Bánh mì nướng bơ tỏi là món ăn đơn giản nhưng thơm ngon, thường xuất hiện trong thực đơn khai vị hoặc ăn nhẹ.','Còn hàng',1,'https://daylambanh.edu.vn/wp-content/uploads/2017/03/banh-mi-bo-toi.jpg'),"+
                "(4,'Bò bít tết',70000,'Bò bít tết là món ăn nổi tiếng từ phương Tây, được ưa chuộng trên toàn thế giới. Món này chủ yếu tập trung vào chất lượng thịt và cách chế biến để giữ nguyên hương vị tự nhiên.','Còn hàng',2,'https://file.hstatic.net/1000389344/file/kieu_phap__2__400f82dde3ed4d98be7db1d7f2eae111_grande.jpg'),"+
                "(5,'Cơm chiên Dương Châu',35000,'Cơm chiên Dương Châu là một món ăn nổi tiếng của Trung Hoa, đã trở nên phổ biến ở nhiều quốc gia. Đây là món cơm chiên đa dạng nguyên liệu và giàu dinh dưỡng.','Còn hàng',2,'https://image.baophapluat.vn/w840/Uploaded/2024/ycgvptcc/2020_05_28/cach-lam-com-chien-duong-chau-600x481_DLZM.jpg'),"+
                "(6,'Gà nướng mật ong',130000,'Gà nướng mật ong là một món ăn thơm ngon, hấp dẫn với lớp da vàng óng và vị ngọt thanh, béo ngậy.','Còn hàng',2,'https://bepnhamo.vn/wp-content/uploads/2023/01/ga-nuong-mat-ong.jpg'),"+
                "(7,'Khoai tây chiên',35000,'Khoai tây chiên là món ăn nhanh phổ biến trên toàn thế giới, đặc biệt được yêu thích bởi độ giòn rụm và vị mặn nhẹ hấp dẫn. Đây là món ăn kèm lý tưởng trong các bữa ăn hoặc đồ uống.','Còn hàng',3,'https://cdn.tgdd.vn/Files/2015/03/01/615221/bi-quyet-lam-moi-khoai-tay-chien-cu-5-760x367.jpg'),"+
                "(8,'Rau xào',50000,'Rau xào là món ăn phổ biến trong bữa cơm gia đình Việt Nam, mang lại sự cân bằng dinh dưỡng với nhiều vitamin và chất xơ. Món ăn có thể linh hoạt thay đổi nguyên liệu rau củ theo mùa.','Còn hàng',3,'https://icdn.one/upload/2021/01/21/20210121142600-9a143e53.jpg'),"+
                "(9,' Salad trộn',50000,'Salad trộn là món ăn tươi mát, bổ dưỡng, phù hợp với những người ăn kiêng hoặc muốn bổ sung vitamin. Nguyên liệu đa dạng và linh hoạt, dễ chế biến.','Còn hàng',3,'https://inoxhungcuong.com/upload/data/images/SEO/cach-lam-salad-1.jpg'),"+
                "(10,'Bánh flan',25000,'Bánh flan là món tráng miệng mềm mịn, béo ngậy, phổ biến ở nhiều quốc gia. Món này có nguồn gốc từ Pháp và được yêu thích vì hương vị nhẹ nhàng, dễ ăn.','Còn hàng',4,'https://cdn.buffetposeidon.com/app/media/Kham-pha-am-thuc/11.2023/251123-cach-lam-banh-flan-buffet-poseidon-02-jpg.jpg'),"+
                "(11,'Pudding sô-cô-la',40000,'Pudding sô-cô-la là món tráng miệng đậm đà, thơm ngon, được làm từ sữa và sô-cô-la. Món này có kết cấu mềm, mịn, và là lựa chọn lý tưởng cho những người yêu thích vị ngọt ngào, đắng nhẹ của sô-cô-la.','Còn hàng',4,'https://file.hstatic.net/1000396324/article/cach-lam-pudding-socola-chuan-vi-phuong-tay_69e503af883e425eae7239844081cb09_1024x1024.jpg'),"+
                "(12,'Kem trái cây',30000,'Kem trái cây là món tráng miệng giải nhiệt, với sự kết hợp giữa kem mát lạnh và vị ngọt tự nhiên của trái cây. Đây là lựa chọn hoàn hảo trong những ngày hè nóng bức.','Còn hàng',4,'https://anh.24h.com.vn/upload/1-2017/images/2017-03-11/1489200543-148820101673811-ice-cream.jpg'),"+
                "(13,'Trà sữa',30000,'Trà sữa là thức uống phổ biến khắp thế giới, đặc biệt ở châu Á. Sự kết hợp giữa vị chát nhẹ của trà, vị béo ngọt của sữa, và topping đa dạng khiến món này được ưa chuộng.','Còn hàng',5,'https://simexcodl.com.vn/wp-content/uploads/2024/05/tra-sua-ca-phe-2.jpg'),"+
                "(14,' Cà phê đen',30000,'Cà phê đen là thức uống quen thuộc, đơn giản nhưng đậm đà và giàu hương vị. Món này thường được thưởng thức để khởi đầu ngày mới hoặc trong những giờ nghỉ ngơi.','Còn hàng',5,'https://file.hstatic.net/1000274203/article/tac_dung_cua_ca_phe_den_2a1a0e12486f430cb893203b10dea6c7.jpg'),"+
                "(15,'Nước ép trái cây',30000,'Nước ép trái cây là thức uống giải nhiệt, giàu vitamin, giúp tăng cường sức khỏe. Món này được chế biến từ các loại trái cây tươi, tùy khẩu vị và mùa vụ.','Hết hàng',5,'https://vov.vn/sites/default/files/styles/large/public/2023-03/1_5_1.jpg')");
        String CREATE_TABLE_CART = "CREATE TABLE Cart (cart_id integer primary key autoincrement, user_id integer REFERENCES User(user_id), dish_id integer REFERENCES Dish(dish_id), quantity integer not null, sum integer not null)";
        db.execSQL(CREATE_TABLE_CART);
        String CREATE_TABLE_ORDER = "CREATE TABLE `Order` (" +
                "order_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "user_id INTEGER REFERENCES User(user_id)," +
                "order_date DATETIME DEFAULT CURRENT_TIMESTAMP," +
                "total_amount INTEGER NOT NULL," +
                "status TEXT NOT NULL" +
                ")";

        String CREATE_TABLE_ORDER_ITEM = "CREATE TABLE OrderItem (" +
                "order_item_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "order_id INTEGER REFERENCES `Order`(order_id)," +
                "dish_id INTEGER REFERENCES Dish(dish_id)," +
                "quantity INTEGER NOT NULL," +
                "price INTEGER NOT NULL" +
                ")";
        String CRATE_TABLE_RATE_ORDER = "CREATE TABLE RateOrder (rate_order_id integer primary key autoincrement, user_id integer REFERENCES User(user_id), order_id integer REFERENCES `Orde`(order_id), rating integer not null, comment text not null, date_rate date not null)";
        String CREATE_TABLE_RATE_Dish = "CREATE TABLE RateDish (rate_dish_id integer primary key autoincrement, user_id integer REFERENCES User(user_id), dish_id integer REFERENCES Dish(dish_id), rating integer not null, comment text not null, date_rate date not null)";
        db.execSQL(CRATE_TABLE_RATE_ORDER);
        db.execSQL(CREATE_TABLE_RATE_Dish);
        db.execSQL(CREATE_TABLE_ORDER);
        db.execSQL(CREATE_TABLE_ORDER_ITEM);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS User");
        db.execSQL("DROP TABLE IF EXISTS Category");
        db.execSQL("DROP TABLE IF EXISTS Dish");
        db.execSQL("DROP TABLE IF EXISTS Cart");
        onCreate(db);
    }
}
