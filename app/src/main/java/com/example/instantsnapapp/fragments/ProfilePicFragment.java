package com.example.instantsnapapp.fragments;
import static android.app.Activity.RESULT_OK;

import com.bumptech.glide.Glide;
import com.example.instantsnapapp.R;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.io.File;
import java.io.IOException;

public class ProfilePicFragment extends Fragment {
    public static final String TAG = "ProfilePicFragment";
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 87;
    public static final int PICK_IMAGE_REQUEST_CODE = 71;
    public String photoFileName = "photo.jpg";
    private Button btnSnap;
    private Button btnPost;
    private Button btnUpload;
    private ImageView ivPicturePost;
    private File photoFile;
    private ProgressBar pbPost;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private ParseUser currentUser;

    public ProfilePicFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_pic, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        currentUser = ParseUser.getCurrentUser();

        btnSnap = view.findViewById(R.id.btnSnap);
        btnPost = view.findViewById(R.id.btnPost);
        ivPicturePost = view.findViewById(R.id.ivPicturePost);
        pbPost = view.findViewById(R.id.pbPost);
        btnUpload = view.findViewById(R.id.btnUpload);

        Glide.with(getContext())
                .load(currentUser.getParseFile("profilePic").getUrl())
                .into(ivPicturePost);

        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pbPost.setVisibility(ProgressBar.VISIBLE);
                if (photoFile == null){
                    Toast.makeText(getContext(), "Invalid post,No Picture.", Toast.LENGTH_SHORT).show();
                    return;
                }
                savePic(currentUser, photoFile);
            }
        });

        btnSnap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchCamera();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyStoragePermissions(getActivity());
                launchFileUpload(view);
            }
        });
    }
    private void launchCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        photoFile = getPhotoFileUri(photoFileName);
        Uri fileProvider = FileProvider.getUriForFile(getContext(), "com.codepath.fileprovider", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);
        if (intent.resolveActivity(getContext().getPackageManager()) != null) {
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    public File getPhotoFileUri(String fileName) {
        File mediaStorageDir = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);

        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(TAG, "failed to create directory");
        }

        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);

        return file;
    }

    public void launchFileUpload(View view) {

        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE);
        }
    }

    public Bitmap loadFromUri(Uri photoUri) {
        Bitmap image = null;
        try {
            if(Build.VERSION.SDK_INT > 27){
                ImageDecoder.Source source = ImageDecoder.createSource(getActivity().getContentResolver(), photoUri);
                image = ImageDecoder.decodeBitmap(source);
            } else {
                image = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), photoUri);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                ivPicturePost.setImageResource(0);
                ivPicturePost.setImageBitmap(takenImage);
            } else {
                Toast.makeText(getContext(), "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
        if(requestCode == PICK_IMAGE_REQUEST_CODE) {
            if(resultCode == RESULT_OK) {
                Uri photoUri = data.getData();
                Cursor returnCursor = getActivity().getContentResolver().query(photoUri, null, null, null, null);
                int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                returnCursor.moveToFirst();
                photoFileName = returnCursor.getString(nameIndex);
                photoFile = new File("/sdcard/Download/" + photoFileName);
                Bitmap selectedImage = loadFromUri(photoUri);
                ivPicturePost.setImageResource(0);
                ivPicturePost.setImageBitmap(selectedImage);
            }
        }
    }

    private void savePic(ParseUser currentUser, File photoFile){
        ParseFile photo = new ParseFile(photoFile);
        photo.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Error during Post", e);
                    return;
                }
                currentUser.put("profilePic",photo);
                currentUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e != null) {
                            Log.e(TAG, "Error during Post", e);
                            return;
                        }
                        Log.i(TAG, "Post was a success");
                        Toast.makeText(getContext(), "Picture updated", Toast.LENGTH_SHORT).show();
                        pbPost.setVisibility(ProgressBar.INVISIBLE);
                        Fragment someFragment = new ProfileFragment();
                        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                        transaction.replace(R.id.flContainer, someFragment );
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                });
            }
        });
    }
    public static void verifyStoragePermissions(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }
}
