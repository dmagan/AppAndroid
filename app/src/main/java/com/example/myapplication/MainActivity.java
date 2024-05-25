package com.example.myapplication;

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
import java.util.List;

public class MainActivity extends AppCompatActivity implements ClickListener {

    private RecyclerView recyclerView;
    private ContactAdapter contactAdapter;
    private List<Contact> contactList;
    private DrawerLayout drawerLayout;

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

        // ایجاد لیست داده‌ها
        contactList = new ArrayList<>();
        contactList.add(new Contact("Mohammad", "Online", R.drawable.ic_launcher_foreground));
        contactList.add(new Contact("Neda", "Offline", R.drawable.ic_launcher_foreground));
        contactList.add(new Contact("Shahram", "Last seen recently", R.drawable.ic_launcher_foreground));
        // می‌توانید داده‌های نمونه بیشتری اضافه کنید

        // تنظیم Adapter برای RecyclerView
        contactAdapter = new ContactAdapter(contactList, this);
        recyclerView.setAdapter(contactAdapter);
    }

    @Override
    public void onItemClick(int position) {
        // اینجا کد مربوط به عملکرد کلیک روی آیتم را اضافه کنید
    }
}
