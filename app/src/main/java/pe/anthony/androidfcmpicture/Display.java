package pe.anthony.androidfcmpicture;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import pe.anthony.androidfcmpicture.Config.Config;

public class Display extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        imageView = findViewById(R.id.imageView);
        if(!TextUtils.isEmpty(Config.imageLink)){
            Picasso.with(this).load(Config.imageLink).into(imageView);
        }
    }
}
