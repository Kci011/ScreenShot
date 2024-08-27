package com.example.screengo;
//defines where package where the class resides

import android.Manifest;
import android.content.ContentResolver;
import android.database.Cursor;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;

import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import android.content.pm.PackageManager;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
// provides functionalities for UI components and window insets handling

public class MainActivity extends AppCompatActivity {
    //gives access to features of an android activity
    private static final int PERMISSION_REQUEST_READ_EXTERNAL_STORAGE = 100;
    private ImageView galleryImageView;


    @SuppressLint("MissingInflatedId")
    @Override //overrides a method from its superclass
    protected void onCreate(Bundle savedInstanceState) {// initial state of the activity
        super.onCreate(savedInstanceState);// calls the onCreate method of the superclass (AppCompatActivity) to perform the default setup
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);// Sets the activity's content to the layout defined
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;//Sets an OnApplyWindowInsetsListener to the main view. This listener adjusts the padding of the view to account for system bars (like the status bar and navigation bar).
        });
        setTitle("ScreenGo");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        galleryImageView = findViewById(R.id.galleryImageView);
    }
    //Outside OnCreate


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add_folder) {
            Toast.makeText(this, "Add Folder", Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.settings) {
            // Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(this, SettingsActivity.class);
            startActivity(i);
        }
        if (id == R.id.rate_us) {
            Toast.makeText(this, "Rate this app", Toast.LENGTH_SHORT).show();
        }
        return true;
        // Request the READ_EXTERNAL_STORAGE permission if it's not already granted

        // Request permission if not already granted
        // Permission already granted
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                loadImagesFromGallery();
            }
        }
    }

    private void loadImagesFromGallery() {
        // Get the ContentResolver
        ContentResolver contentResolver = getContentResolver();

        // Define the URI for the media store
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        // Define the columns we want to retrieve
        String[] projection = {MediaStore.Images.Media._ID};

        // Create a cursor to query the media store
        Cursor cursor = contentResolver.query(uri, projection, null, null, null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                // Get the ID of the first image in the gallery
                long imageId = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID));

                // Create the URI for the image using the ID
                Uri imageUri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, String.valueOf(imageId));

                // Load the image as a Bitmap and display it in the ImageView
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri);
                    galleryImageView.setImageBitmap(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            cursor.close();
        }
    }
}


