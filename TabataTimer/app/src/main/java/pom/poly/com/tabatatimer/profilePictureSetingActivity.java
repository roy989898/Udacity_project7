package pom.poly.com.tabatatimer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.soundcloud.android.crop.Crop;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class profilePictureSetingActivity extends AppCompatActivity {

    @BindView(R.id.imProfile)
    CircleImageView imProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_picture_seting);
        ButterKnife.bind(this);

        //Load the profile picture
        String profilePURL = getSharedPreferences(getString(R.string.name_sharepreference), MODE_PRIVATE).getString(getString(R.string.SharePreferenceDownloadLinkKey), "");
        if (!profilePURL.equals("")) {
            Picasso picasso = Picasso.with(this);
            picasso.load(profilePURL).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    imProfile.setImageBitmap(bitmap);
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            });
        }

    }

    @OnClick(R.id.imProfile)
    public void onClick() {
        //choose a profile picture
        Crop.pickImage(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent result) {
        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
            beginCrop(result.getData());
        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, result);
        }
    }

    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().start(this);
    }

    private void handleCrop(int resultCode, Intent result) {
        Target target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
                imProfile.setImageBitmap(bitmap);

            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {    //當圖片加載失敗時調用
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {     //當任務被提交時調用
            }
        };
        if (resultCode == RESULT_OK) {
//            resultView.setImageURI(Crop.getOutput(result));
//            Picasso.with(this).load(Crop.getOutput(result)).into(target);
            imProfile.setImageURI(null);
            Uri url = Crop.getOutput(result);
            imProfile.setImageURI(url);
            Log.d("image Url", Crop.getOutput(result).toString());

            //upload the profile picture
            upLoadImage(url);
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void upLoadImage(Uri file) {
        //create a reference
        String url = getString(R.string.preference_uploadPicture_url);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl(url);

        //get the user ID
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Toast.makeText(this, getString(R.string.unSuccessUploadMessageNoSigin), Toast.LENGTH_SHORT);
            return;

        }
        String name = user.getDisplayName();
        String email = user.getEmail();
        Uri photoUrl = user.getPhotoUrl();

        // The user's ID, unique to the Firebase project. Do NOT use this value to
        // authenticate with your backend server, if you have one. Use
        // FirebaseUser.getToken() instead.
        String uid = user.getUid();
        // Create a reference to 'images/mountains.jpg'
        StorageReference profileIMG = storageRef.child("images/TabataTimer/" + uid + "/profile.jpg");

//        Uri file = Uri.fromFile(new File("path/to/images/rivers.jpg"));
//        StorageReference riversRef = storageRef.child("images/" + file.getLastPathSegment());
        UploadTask uploadTask = profileIMG.putFile(file);

        // Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                Toast.makeText(profilePictureSetingActivity.this, getString(R.string.unSuccessUploadMessage), Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                Log.d("downloadUrl", downloadUrl.toString());
                saveTheLinkinSP(downloadUrl.toString());
            }
        });

    }

    private void saveTheLinkinSP(String downloadUrl) {
        SharedPreferences preference = getSharedPreferences(getString(R.string.name_sharepreference), MODE_PRIVATE);
        SharedPreferences.Editor editor = preference.edit();
        editor.putString(getString(R.string.SharePreferenceDownloadLinkKey), downloadUrl);
        editor.commit();
    }


}
