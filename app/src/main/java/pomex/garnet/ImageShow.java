package pomex.garnet;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import uk.co.senab.photoview.PhotoViewAttacher;

public class ImageShow extends AppCompatActivity {
    PhotoViewAttacher pv;
    private ImageView a_foto;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_show);
        getSupportActionBar().hide();

        a_foto=findViewById(R.id.imageViewanalizshow);
        pv=new PhotoViewAttacher(a_foto);

        id=getIntent().getStringExtra("id");

        ParseQuery<ParseObject> query=ParseQuery.getQuery("KomurAnaliz");
        query.getInBackground(id, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e!=null){
                    e.printStackTrace();
                }else{
                    ParseFile pfA=(ParseFile) object.getParseFile("Resim");
                    pfA.getDataInBackground(new GetDataCallback() {
                        @Override
                        public void done(byte[] data, ParseException e) {
                            if (e==null&&data!=null){
                                Bitmap bmap= BitmapFactory.decodeByteArray(data,0,data.length);
                                a_foto.setImageBitmap(bmap);
                            }else if (e==null&&data==null){
                                Toast.makeText(ImageShow.this,"Resim Yok",Toast.LENGTH_LONG).show();
                            }else{
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
    }
}
