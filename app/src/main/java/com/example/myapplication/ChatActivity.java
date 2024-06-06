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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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
import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.ULocale;
import com.ibm.icu.text.SimpleDateFormat;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private RecyclerView recyclerViewMessages;
    private MessageAdapter messageAdapter;
    private List<Message> messageList;
    private EditText editTextMessage;
    private ImageButton buttonSendIcon;
    private ImageButton buttonCamera;
    private Uri photoURI;
    private File photoFile;
    private String userId = "665f548517eb80f08f098020"; // آی دی کاربر Mohammad

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
        buttonSendIcon = findViewById(R.id.buttonSendIcon);
        buttonCamera = findViewById(R.id.buttonCamera);

        // تنظیم RecyclerView
        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(messageList);
        recyclerViewMessages.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewMessages.setAdapter(messageAdapter);

        // تنظیم کلیک دکمه ارسال
        buttonSendIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String messageText = editTextMessage.getText().toString().trim();
                if (!messageText.isEmpty()) {
                    String timeStamp = getCurrentTime();
                    Message message = new Message(messageText, true, timeStamp); // پیام ارسال شده توسط کاربر
                    messageList.add(message);
                    messageAdapter.notifyItemInserted(messageList.size() - 1);
                    recyclerViewMessages.scrollToPosition(messageList.size() - 1);
                    editTextMessage.setText("");

                    // حذف دکمه ارسال پس از ارسال پیام
                    buttonSendIcon.setVisibility(View.GONE);
                }
            }
        });

        // اضافه کردن TextWatcher به EditText
        editTextMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // نمایش دکمه ارسال وقتی که متن وارد شده است
                if (s.length() > 0) {
                    buttonSendIcon.setVisibility(View.VISIBLE);
                } else {
                    buttonSendIcon.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // تنظیم کلیک دکمه دوربین
        buttonCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        // دریافت پیام‌ها از سرور
        getMessagesFromServer();
    }

    private void getMessagesFromServer() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<List<Message>> call = apiService.getMessages(userId);
        call.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    messageList.clear(); // ابتدا لیست را پاک می‌کنیم تا مطمئن شویم پیام‌های تکراری اضافه نشوند
                    messageList.addAll(response.body());
                    messageAdapter.notifyDataSetChanged();
                    Log.d("ChatActivity", "Messages received: " + response.body().size());
                } else {
                    Log.e("ChatActivity", "Failed to receive messages. Response code: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                Log.e("ChatActivity", "Failed to receive messages", t);
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
                String timeStamp = getCurrentTime();
                Message message = new Message(null, true, timeStamp);
                message.setImage(bitmap);
                message.setImagePath(photoFile.getAbsolutePath()); // ذخیره مسیر تصویر
                messageList.add(message);
                messageAdapter.notifyItemInserted(messageList.size() - 1);
                recyclerViewMessages.scrollToPosition(messageList.size() - 1);
            }
        }
    }

    private String getCurrentTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm", ULocale.getDefault());
        return sdf.format(calendar.getTime());
    }
}
