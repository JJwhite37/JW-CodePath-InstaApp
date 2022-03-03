package com.example.instantsnapapp.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.instantsnapapp.R;
import com.example.instantsnapapp.models.Post;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;

public class PostActivity extends AppCompatActivity {
    public static final String TAG = "PostActivity";
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 87;
    public String photoFileName = "photo.jpg";
    private Button btnSnap;
    private Button btnPost;
    private ImageView ivPicturePost;
    private EditText etCaption;
    private Boolean snapBool = false;
    private File photoFile;
    private ProgressBar pbPost;
    private ImageView ivProfile;
    private ImageView ivHome;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        btnSnap = findViewById(R.id.btnSnap);
        btnPost = findViewById(R.id.btnPost);
        ivPicturePost = findViewById(R.id.ivPicturePost);
        etCaption = findViewById(R.id.etCaption);
        pbPost = findViewById(R.id.pbPost);
        ivProfile = findViewById(R.id.ivProfile);
        ivHome = findViewById(R.id.ivHome);

        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PostActivity.this, ProfileActivity.class);
                startActivity(i);
            }
        });
        ivHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PostActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pbPost.setVisibility(ProgressBar.VISIBLE);
                String description = etCaption.getText().toString();
                if (description.isEmpty()){
                    Toast.makeText(PostActivity.this, "Invalid post,No description.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (photoFile == null){
                    Toast.makeText(PostActivity.this, "Invalid post,No Picture.", Toast.LENGTH_SHORT).show();
                    return;
                }
                ParseUser curentUser = ParseUser.getCurrentUser();
                savePost(description, curentUser, photoFile);
            }
        });

        btnSnap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snapBool = true;
                launchCamera();
            }
        });
    }
    private void launchCamera(){
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create a File reference for future access
        photoFile = getPhotoFileUri(photoFileName);

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        Uri fileProvider = FileProvider.getUriForFile(PostActivity.this, "com.codepath.fileprovider", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    public File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);

        return file;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // by this point we have the camera photo on disk
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                // RESIZE BITMAP, see section below
                // Load the taken image into a preview
                ivPicturePost.setImageBitmap(takenImage);
            } else { // Result was a failure
                Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void savePost(String description, ParseUser currentUser, File photoFile){
        Post post = new Post();
        post.setDescription(description);
        post.setUser(currentUser);
        post.setImage(new ParseFile(photoFile));
        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error during Post", e);
                    return;
                }
                Log.i(TAG, "Post was a success");
                Toast.makeText(PostActivity.this, "Post was successful", Toast.LENGTH_SHORT).show();
                pbPost.setVisibility(ProgressBar.INVISIBLE);
                ivPicturePost.setImageResource(0);
                etCaption.setText("");
            }
        });
    }
}
