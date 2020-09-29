package pomex.garnet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.Date;
import java.util.FormatFlagsConversionMismatchException;
import java.util.List;

public class KomurGoster extends AppCompatActivity {
    private Integer no;
    private String id_no;
    private EditText firma,boyut,tarih,nem,manyetik,e500,fiyat,aciklama;
    private TextView f_goster,r_goster;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_komur_goster);
        getSupportActionBar().hide();

        firma=findViewById(R.id.et_kg_firma);
        boyut=findViewById(R.id.et_kg_boyut);
        tarih=findViewById(R.id.et_kg_tarih);
        nem=findViewById(R.id.et_kg_nem);
        manyetik=findViewById(R.id.et_kg_manyetik);
        e500=findViewById(R.id.et_kg_ince);
        fiyat=findViewById(R.id.et_kg_fiyat);
        aciklama=findViewById(R.id.et_kg_aciklama);
        f_goster=findViewById(R.id.tv_kg_fiyat2);
        r_goster=findViewById(R.id.tv_kg_resimgoster);
        imageView=findViewById(R.id.imageV_kg_resim);

        fiyat.setVisibility(fiyat.INVISIBLE);

        no= getIntent().getIntExtra("No",0);
        if(no==0){
            id_no=getIntent().getStringExtra("id_No");

            ParseQuery<ParseObject> qq1=ParseQuery.getQuery("KomurAnaliz");
            qq1.getInBackground(id_no, new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {
                    if (e!=null){
                        e.printStackTrace();
                    }else{
                        Date date =object.getDate("Tarih");
                        tarih.setText(date.getDate()+"."+(date.getMonth()+1)+"."+(date.getYear()+1900));
                        firma.setText(object.getString("Firma"));
                        nem.setText(String.valueOf(object.getDouble("Nem")));
                        manyetik.setText(String.valueOf(object.getDouble("Manyetik")));
                        e500.setText(String.valueOf(object.getDouble("Ince")));
                        aciklama.setText(object.getString("Aciklama"));
                        boyut.setText(object.getString("Boyut"));
                        fiyat.setText(String.valueOf(object.getInt("No")));
                    }
                }
            });
        }else{
            ParseQuery<ParseObject> query1=ParseQuery.getQuery("Komur");
            query1.whereEqualTo("No",no);
            query1.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e!=null){
                        e.printStackTrace();
                    }else{
                        for (ParseObject object:objects){
                            boyut.setText(object.getString("Olcu"));
                            Double a=object.getDouble("Tutar");
                            Double b=object.getDouble("Miktar");
                            fiyat.setText(String.format("%.0f",(a/b)*1000));
                        }
                    }
                }
            });

            ParseQuery<ParseObject> query2=ParseQuery.getQuery("KomurAnaliz");
            query2.whereEqualTo("No",no);
            query2.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if(e!=null){
                        e.printStackTrace();
                    }else{
                        for (ParseObject object:objects){
                            Date date =object.getDate("Tarih");
                            tarih.setText(date.getDate()+"."+(date.getMonth()+1)+"."+(date.getYear()+1900));
                            firma.setText(object.getString("Firma"));
                            nem.setText(String.valueOf(object.getDouble("Nem")));
                            manyetik.setText(String.valueOf(object.getDouble("Manyetik")));
                            e500.setText(String.valueOf(object.getDouble("Ince")));
                            aciklama.setText(object.getString("Aciklama"));
                        }
                    }
                }
            });
        }

        f_goster.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (no==0){
                    ParseQuery<ParseObject>q=ParseQuery.getQuery("KomurAnaliz");
                    ParseQuery<ParseObject> q1=ParseQuery.getQuery("KomurAnaliz");
                    q1.getInBackground(id_no, new GetCallback<ParseObject>() {
                        @Override
                        public void done(ParseObject object, ParseException e) {
                            if (e!=null){
                                e.printStackTrace();
                            }else{
                                if (object.getInt("No")==0){
                                    if (object.getInt("Fiyat")!=0){
                                        fiyat.setText(String.valueOf(object.getInt("Fiyat")));
                                        fiyat.setVisibility(fiyat.VISIBLE);
                                    }
                                }else{
                                    ParseQuery<ParseObject> q2=ParseQuery.getQuery("Komur");
                                    q2.whereEqualTo("No",Integer.valueOf(fiyat.getText().toString()));
                                    q2.getFirstInBackground(new GetCallback<ParseObject>() {
                                        @Override
                                        public void done(ParseObject object, ParseException e) {
                                            if (e!=null){
                                                e.printStackTrace();
                                            }else{
                                                Double a=object.getDouble("Tutar");
                                                Double b=object.getDouble("Miktar");
                                                fiyat.setText(String.format("%.0f",(a/b)*1000));
                                                fiyat.setVisibility(fiyat.VISIBLE);
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    });
                }else{
                    ParseQuery<ParseObject> q2=ParseQuery.getQuery("Komur");
                    q2.whereEqualTo("No",Integer.valueOf(fiyat.getText().toString()));
                    q2.getFirstInBackground(new GetCallback<ParseObject>() {
                        @Override
                        public void done(ParseObject object, ParseException e) {
                            if (e!=null){
                                e.printStackTrace();
                            }else{
                                Double a=object.getDouble("Tutar");
                                Double b=object.getDouble("Miktar");
                                fiyat.setText(String.format("%.0f",(a/b)*1000));
                                fiyat.setVisibility(fiyat.VISIBLE);
                            }
                        }
                    });
                }
                return false;
            }
        });

        r_goster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (no==0){
                    ParseQuery<ParseObject>q=ParseQuery.getQuery("KomurAnaliz");
                    q.getInBackground(id_no, new GetCallback<ParseObject>() {
                        @Override
                        public void done(ParseObject object, ParseException e) {
                            if (e!=null){
                                e.printStackTrace();
                            }else{
                                ParseFile parseFile=object.getParseFile("Resim");
                                parseFile.getDataInBackground(new GetDataCallback() {
                                    @Override
                                    public void done(byte[] data, ParseException e) {
                                        if(e==null&&data!=null){
                                            Bitmap bitmap= BitmapFactory.decodeByteArray(data,0,data.length);
                                            imageView.setImageBitmap(bitmap);
                                        }else if (e==null && data==null){
                                            Toast.makeText(getApplicationContext(),"Resim Yok",Toast.LENGTH_SHORT).show();
                                        }else{
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }
                        }
                    });
                }else{
                    ParseQuery<ParseObject>qz=ParseQuery.getQuery("KomurAnaliz");
                    qz.whereEqualTo("No",no);
                    qz.getFirstInBackground(new GetCallback<ParseObject>() {
                        @Override
                        public void done(ParseObject object, ParseException e) {
                            if (e!=null){
                                e.printStackTrace();
                            }else{
                                ParseFile parseFile=object.getParseFile("Resim");
                                parseFile.getDataInBackground(new GetDataCallback() {
                                    @Override
                                    public void done(byte[] data, ParseException e) {
                                        if(e==null&&data!=null){
                                            Bitmap bitmap= BitmapFactory.decodeByteArray(data,0,data.length);
                                            imageView.setImageBitmap(bitmap);
                                        }else if (e==null && data==null){
                                            Toast.makeText(getApplicationContext(),"Resim Yok",Toast.LENGTH_SHORT).show();
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
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(KomurGoster.this,ImageShow.class);
                intent.putExtra("id",id_no);
                startActivity(intent);
            }
        });

    }
}
