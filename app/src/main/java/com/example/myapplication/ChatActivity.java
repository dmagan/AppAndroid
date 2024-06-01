package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Window;
import android.view.WindowManager;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.ULocale;

public class ChatActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private RecyclerView recyclerViewMessages;
    private MessageAdapter messageAdapter;
    private List<Message> messageList;
    private EditText editTextMessage;
    private Button buttonSend;
    private ImageButton buttonCamera;
    private Uri photoURI;
    private File photoFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        // تغییر رنگ نوار وضعیت
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));

        // تنظیم Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // نمایش دکمه بازگشت
        toolbar.setNavigationOnClickListener(v -> onBackPressed()); // عملکرد دکمه بازگشت

        // تنظیم محتوای سفارشی نوار ابزار
        LayoutInflater inflater = LayoutInflater.from(this);
        View customView = inflater.inflate(R.layout.toolbar_custom, toolbar, false);
        toolbar.addView(customView);

        ImageView userImageView = customView.findViewById(R.id.userImageView);
        TextView userNameTextView = customView.findViewById(R.id.userNameTextView);

        // دریافت اطلاعات کاربر از Intent
        String userName = getIntent().getStringExtra("userName");
        int startColor = getIntent().getIntExtra("startColor", ContextCompat.getColor(this, R.color.colorAccent));
        int endColor = getIntent().getIntExtra("endColor", ContextCompat.getColor(this, R.color.colorAccent));

        if (userName != null) {
            userNameTextView.setText(userName);
        }

        // تنظیم گرادیان دایره‌ای
        GradientDrawable gradient = new GradientDrawable(
                GradientDrawable.Orientation.TL_BR,
                new int[]{startColor, endColor});
        gradient.setShape(GradientDrawable.OVAL);
        userImageView.setBackground(gradient);

        // مقداردهی به ویوها
        recyclerViewMessages = findViewById(R.id.recyclerViewMessages);
        editTextMessage = findViewById(R.id.editTextMessage);
        buttonSend = findViewById(R.id.buttonSend);
        buttonCamera = findViewById(R.id.buttonCamera);

        // تنظیم RecyclerView
        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(messageList);
        recyclerViewMessages.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMessages.setAdapter(messageAdapter);

        // تنظیم کلیک دکمه ارسال
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = editTextMessage.getText().toString().trim();
                if (!messageText.isEmpty()) {
                    // استفاده از ICU4J برای دریافت تاریخ و زمان شمسی
                    Calendar calendar = Calendar.getInstance(new ULocale("fa_IR@calendar=persian"));
                    String dateStr = String.format("%d/%02d/%02d %02d:%02d:%02d",
                            calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH) + 1,
                            calendar.get(Calendar.DAY_OF_MONTH),
                            calendar.get(Calendar.HOUR_OF_DAY),
                            calendar.get(Calendar.MINUTE),
                            calendar.get(Calendar.SECOND));

                    // افزودن تاریخ و زمان شمسی به پیام
                    String fullMessage = messageText + "\n(" + dateStr + ")";

                    Message message = new Message(fullMessage, true); // پیام ارسال شده توسط کاربر
                    messageList.add(message);
                    messageAdapter.notifyItemInserted(messageList.size() - 1);
                    recyclerViewMessages.scrollToPosition(messageList.size() - 1);
                    editTextMessage.setText("");
                }
            }
        });

        // تنظیم کلیک دکمه دوربین
        buttonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
    }

    private File createImageFile() throws IOException {
        // ایجاد یک نام فایل منحصر به فرد
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // مسیر فایل را ذخیره کنید تا بعدا از آن استفاده کنید
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // فایل جایی که عکس ذخیره می‌شود را ایجاد کنید
            try {
                photoFile = createImageFile();
                if (photoFile != null) {
                    photoURI = FileProvider.getUriForFile(this,
                            "com.example.myapplication.fileprovider",
                            photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            } catch (IOException ex) {
                // در صورت بروز خطا
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            new LoadImageTask().execute(photoFile.getAbsolutePath());
        }
    }

    private class LoadImageTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... paths) {
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            return BitmapFactory.decodeFile(paths[0], bmOptions);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap != null) {
                // اضافه کردن عکس به لیست پیام‌ها
                Message message = new Message(null, true);
                message.setImage(bitmap);
                message.setImagePath(photoFile.getAbsolutePath()); // ذخیره مسیر تصویر
                messageList.add(message);
                messageAdapter.notifyItemInserted(messageList.size() - 1);
                recyclerViewMessages.scrollToPosition(messageList.size() - 1);
            }
        }
    }
}
