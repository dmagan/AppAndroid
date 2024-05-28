package com.example.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.navigation.NavigationView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements ClickListener {

    private RecyclerView recyclerView;
    private ContactAdapter contactAdapter;
    private List<Contact> contactList;
    private DrawerLayout drawerLayout;
    private Map<String, int[]> colorMap; // نقشه برای ذخیره رنگ‌های کاربران

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // تغییر رنگ نوار وضعیت
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));

        // مقداردهی به Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Connecting");

        // تنظیم کلید همبرگر
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_view); // استفاده از آیکون پیش‌فرض Android
        toolbar.setNavigationOnClickListener(v -> drawerLayout.openDrawer(navigationView));

        // مقداردهی به RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // ایجاد لیست داده‌ها با گرادینت‌های تصادفی
        contactList = new ArrayList<>();
        colorMap = new HashMap<>();

        addContact("Mohammad", "Online");
        addContact("Neda", "Offline");
        addContact("Shahram", "Last seen recently");
        // می‌توانید داده‌های نمونه بیشتری اضافه کنید

        // تنظیم Adapter برای RecyclerView
        contactAdapter = new ContactAdapter(contactList, this);
        recyclerView.setAdapter(contactAdapter);
    }

    private void addContact(String name, String status) {
        int startColor = generateRandomColor();
        int endColor = generateRandomColor();
        colorMap.put(name, new int[]{startColor, endColor});
        contactList.add(new Contact(name, status, startColor, endColor));
    }

    private int generateRandomColor() {
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
    }

    @Override
    public void onItemClick(int position) {
        // دریافت اطلاعات کاربر
        Contact contact = contactList.get(position);

        // ایجاد Intent برای شروع ChatActivity
        Intent intent = new Intent(MainActivity.this, ChatActivity.class);
        intent.putExtra("userName", contact.getName());
        int[] colors = colorMap.get(contact.getName());
        if (colors != null) {
            intent.putExtra("startColor", colors[0]);
            intent.putExtra("endColor", colors[1]);
        }
        startActivity(intent);
    }
}
