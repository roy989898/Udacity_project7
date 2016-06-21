package pom.poly.com.tabatatimer;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

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
            imProfile.setImageURI(Crop.getOutput(result));
            Log.d("image Url",Crop.getOutput(result).toString());
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


}
