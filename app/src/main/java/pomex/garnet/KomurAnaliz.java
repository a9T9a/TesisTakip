package pomex.garnet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class KomurAnaliz extends AppCompatActivity {
    private EditText yas,kuru,man_miktar,man_tutlan,ince,aciklama,firma,boyut;
    private TextView saat,tarih,nem,man,inc,r_ekle;
    private Button button;
    private DatePickerDialog.OnDateSetListener datepicker;
    private TimePickerDialog.OnTimeSetListener timepicker;
    private Calendar cal;
    private Date date;
    private DateFormat df;
    private Boolean sorun;
    private Integer no;
    private CheckBox cb_kamyon,cb_numune;
    private Integer izin;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_komur_analiz);
        getSupportActionBar().hide();

        tarih=findViewById(R.id.tv_ka_tarih2);
        saat=findViewById(R.id.tv_ka_saat2);
        yas=findViewById(R.id.et_ka_yas);
        kuru=findViewById(R.id.et_ka_kuru);
        man_miktar=findViewById(R.id.et_ka_man_miktar);
        man_tutlan=findViewById(R.id.et_ka_man_tutulan);
        ince=findViewById(R.id.et_ka_ince);
        nem=findViewById(R.id.tv_ka_nem3);
        man=findViewById(R.id.tv_ka_many3);
        inc=findViewById(R.id.tv_ka_inc3);
        button=findViewById(R.id.button_ka_kaydet);
        aciklama=findViewById(R.id.et_ka_aciklama);
        cb_kamyon=findViewById(R.id.cb_ka_kamyon);
        cb_numune=findViewById(R.id.cb_ka_numune);
        r_ekle=findViewById(R.id.tv_ka_resimekle);
        imageView=findViewById(R.id.imageV_ka);
        firma=findViewById(R.id.et_ka_firma2);
        boyut=findViewById(R.id.et_ka_boyut);

        imageView.setVisibility(imageView.INVISIBLE);

        ParseQuery<ParseObject> query=ParseQuery.getQuery("KomurAnaliz");
        query.orderByDescending("No");
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e!=null){e.printStackTrace();}
                else{
                    no=object.getInt("No");
                    no=no+1;
                }
            }
        });

        kuru.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                Double a,b;
                if (!yas.getText().toString().isEmpty()){
                    if (!kuru.getText().toString().isEmpty()) {
                        a = Double.valueOf(kuru.getText().toString());
                    }else{ a = Double.valueOf(yas.getText().toString());}
                    b =Double.valueOf(yas.getText().toString());
                    nem.setText(String.format("%.1f",100-(a/b*100)));
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        man_tutlan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {

            }
            @Override
            public void afterTextChanged(Editable editable) {
                Double a=0.0;
                Double b=0.0;
                if (!man_miktar.getText().toString().isEmpty()&&!man_tutlan.getText().toString().isEmpty()){
                    a=Double.valueOf(man_tutlan.getText().toString());
                    b=Double.valueOf(man_miktar.getText().toString());
                }
                man.setText(String.format("%.1f",a/b*100));
            }
        });

        man_miktar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Double a=0.0;
                Double b=0.0;
                if (!man_miktar.getText().toString().isEmpty()&&!man_tutlan.getText().toString().isEmpty()){
                    a=Double.valueOf(man_tutlan.getText().toString());
                    b=Double.valueOf(man_miktar.getText().toString());
                }
                man.setText(String.format("%.1f",a/b*100));
            }
        });

        ince.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                Double a,b;
                if (!yas.getText().toString().isEmpty()){
                    if(!ince.getText().toString().isEmpty()) {
                        a = Double.valueOf(ince.getText().toString());
                    }else{a = Double.valueOf(kuru.getText().toString());}
                    b =Double.valueOf(kuru.getText().toString());
                    inc.setText(String.format("%.1f",a/b*100));
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        cb_numune.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cb_kamyon.setChecked(false);
            }
        });
        cb_kamyon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cb_numune.setChecked(false);
            }
        });

        tarih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog ddialog = new DatePickerDialog(KomurAnaliz.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, datepicker, year, month, day);
                ddialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                ddialog.show();
            }
        });
        datepicker =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month=month+1;
                tarih.setText(day+"/"+month+"/"+year);
                String sss=day+"/"+month+"/"+year+" 13:00";
                df=new SimpleDateFormat("dd/MM/yyyy hh:mm");
                try {
                    date=df.parse(sss);
                } catch (java.text.ParseException e) {
                    e.printStackTrace();
                }
            }
        };

        saat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cal=Calendar.getInstance();
                int saaat=cal.get(Calendar.HOUR_OF_DAY);
                int dakika=cal.get(Calendar.MINUTE);

                TimePickerDialog tdialog= new TimePickerDialog(KomurAnaliz.this,android.R.style.Theme_Holo_Light_Dialog,timepicker,saaat,dakika,true);
                tdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.GRAY));
                tdialog.show();
            }
        });
        timepicker=new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int saaat, int dakika) {
                String s,d;
                if(dakika==0){
                    d="00";
                }else if(dakika/10==0){
                    d="0"+String.valueOf(dakika);
                }else{
                    d=String.valueOf(dakika);
                }
                if(saaat==0){
                    s="00";
                }else if (saaat/10==0){
                    s="0"+String.valueOf(saaat);
                }else{
                    s=String.valueOf(saaat);
                }
                saat.setText(s+":"+d);
            }
        };

        r_ekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Resim();
                imageView.setVisibility(imageView.VISIBLE);
            }
        });
    }

    public void Resim(){
        izin= ContextCompat.checkSelfPermission(KomurAnaliz.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (izin!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(KomurAnaliz.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        }else{
            Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent,2);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==1){
            if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,2);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==2 && resultCode==RESULT_OK && data!=null){
            Uri uri=data.getData();
            try{
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                if (bitmap.getWidth()>900){
                    Integer k =bitmap.getWidth()/900;
                    Integer h=bitmap.getHeight()/k;
                    Integer w=bitmap.getWidth()/k;
                    Bitmap new_bitmap=Bitmap.createScaledBitmap(bitmap,w,h,false);
                    imageView.setImageBitmap(new_bitmap);
                }else{
                    imageView.setImageBitmap(bitmap);
                }
            }catch(IOException e){
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void Kaydet(View view){
        sorun=false;
        ParseObject object=new ParseObject("KomurAnaliz");
        if (cb_kamyon.isChecked()){
            object.put("No",no);
            object.put("Numune",false);
        }
        if (cb_numune.isChecked()){
            object.put("Numune",true);
        }
        if (tarih.getText().toString().isEmpty()){
            sorun=true;
            Toast.makeText(getApplicationContext(),"Tarih Gir",Toast.LENGTH_LONG).show();
        }else{
            object.put("Tarih",date);
        }
        if (saat.getText().toString().isEmpty()){
            sorun=true;
            Toast.makeText(getApplicationContext(),"Saat Gir",Toast.LENGTH_LONG).show();
        }else{
            object.put("Saat",saat.getText().toString());
        }
        if (firma.getText().toString().isEmpty()){
            sorun=true;
            Toast.makeText(getApplicationContext(),"Firma Gir",Toast.LENGTH_LONG).show();
        }else{
            object.put("Firma",firma.getText().toString());
        }
        if (boyut.getText().toString().isEmpty()){
            sorun=true;
            Toast.makeText(getApplicationContext(),"Boyut Gir",Toast.LENGTH_LONG).show();
        }else{
            object.put("Boyut",boyut.getText().toString());
        }
        if (nem.getText().toString().isEmpty()){
            sorun=true;
            Toast.makeText(getApplicationContext(),"Kuru Değer Gir",Toast.LENGTH_LONG).show();
        }else{
            object.put("Nem",Double.parseDouble(nem.getText().toString().replace(",",".")));
        }
        if (man.getText().toString().isEmpty()){
            sorun=true;
            Toast.makeText(getApplicationContext(),"Manyetik Değer Gir",Toast.LENGTH_LONG).show();
        }else{
            object.put("Manyetik",Double.parseDouble(man.getText().toString().replace(",",".")));
        }
        if (inc.getText().toString().isEmpty()){
            sorun=true;
            Toast.makeText(getApplicationContext(),"-500 Değer Gir",Toast.LENGTH_LONG).show();
        }else{
            object.put("Ince",Double.parseDouble(inc.getText().toString().replace(",",".")));
        }
        if (!aciklama.getText().toString().isEmpty()){
            object.put("Aciklama",aciklama.getText().toString());
        }
        if (imageView.getDrawable()!=null){
            Bitmap bitmap=((BitmapDrawable)imageView.getDrawable()).getBitmap();
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,40,baos);
            byte[] bytedata=baos.toByteArray();
            ParseFile parseFile=new ParseFile("image.jpeg",bytedata);
            object.put("Resim",parseFile);
        }
        object.put("User", ParseUser.getCurrentUser().getUsername());

        if (sorun!=true){
            object.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e!=null){
                    Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(getApplicationContext(), "Kayıt Edildi", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            });
        }
    }
}
