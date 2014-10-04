
package edu.wetisai.orc;

import com.googlecode.tesseract.android.TessBaseAPI;

import edu.wetisai.ocr.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class CameraORC extends Activity {
    private final int cameraResultCode = 1003;
    private TextView tessResults;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orc_camera);
        findView();

    }

    private void findView() {
        tessResults = findViewId(R.id.results);
        image = findViewId(R.id.image);

    }

    private <T extends View> T findViewId(int resId) {
        return (T) findViewById(resId);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }

        if (requestCode == cameraResultCode) {
            Bundle extras = data.getExtras();
            Bitmap bmp = (Bitmap) extras.get("data");
            image.setImageBitmap(bmp);
            
            TessBaseAPI baseApi = new TessBaseAPI();
            baseApi.init("/mnt/sdcard/tesseract/", "chi_tra");
            baseApi.setPageSegMode(TessBaseAPI.PageSegMode.PSM_SINGLE_LINE);
            baseApi.setImage(bmp);
            String outputText = baseApi.getUTF8Text();
            tessResults.setText(outputText);
        }


        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onCamera(View v) {
        Intent mIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(mIntent, cameraResultCode);
    }

}
