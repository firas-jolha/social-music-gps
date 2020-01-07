package android.example.gpsapp.database;


import java.util.HashMap;

public class MyFirebaseDatabaseTest {
    MyFirebaseDatabase database = new MyFirebaseDatabase();

    public void writeToDB() {
        HashMap<String, String> data = new HashMap<>();
        data.put("message", "Hello Firebase Database");
        data.put("username", "Firas");
        data.put("password", "a;ldmqwmkdqwd1cke192ce1");
        database.writeToDB(data);
    }
}