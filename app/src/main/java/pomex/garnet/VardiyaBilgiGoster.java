package pomex.garnet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Space;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class VardiyaBilgiGoster extends AppCompatActivity {
    private TextView tarih,vardiya,baslama,bitis;
    private EditText iri_rutil,orta_rutil,ince_rutil,iri_2000,ince_2000,manyetit,g2040,g3060,g80,g180,ariza,yapilanis,aciklama;
    private CheckBox cb_a_min,cb_silis;
    private Button button;
    private ScrollView scrollView;
    private Integer no,ilk_toplam,izin;
    private String id;
    private Boolean sorun;
    private Calendar calendar;
    private TimePickerDialog.OnTimeSetListener timepicker1,timepicker2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vardiya_bilgi_goster);
        getSupportActionBar().hide();

        scrollView=findViewById(R.id.scrollView_vardbg);
        tarih=findViewById(R.id.tv_vardbg_tarih);
        vardiya=findViewById(R.id.tv_vardbg_vardiya);
        baslama=findViewById(R.id.tv_vardbg_bas_saat);
        bitis=findViewById(R.id.tv_vardbg_bit_saat);
        iri_rutil=findViewById(R.id.et_vardbg_alnn_irirutil);
        orta_rutil=findViewById(R.id.et_vardbg_alnn_ortarutil);
        ince_rutil=findViewById(R.id.et_vardbg_alnn_incerutil);
        iri_2000=findViewById(R.id.et_vardbg_alnn_iri2000);
        ince_2000=findViewById(R.id.et_vardbg_alnn_ince2000);
        manyetit=findViewById(R.id.et_vardbg_alnn_manyetit);
        g2040=findViewById(R.id.et_vardbg_alnn_2040);
        g3060=findViewById(R.id.et_vardbg_alnn_3060);
        g80=findViewById(R.id.et_vardbg_alnn_80);
        g180=findViewById(R.id.et_vardbg_alnn_180);
        ariza=findViewById(R.id.et_vardbg_ariza);
        yapilanis=findViewById(R.id.et_vardbg_yapilan);
        aciklama=findViewById(R.id.et_vardbg_aciklama);
        cb_a_min=findViewById(R.id.cb_vardbg_amin);
        cb_silis=findViewById(R.id.cb_vardbg_silis);
        button=findViewById(R.id.button_vardbg_kaydet);

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser.get("izin")==null){
            izin=0;
        }else{
            izin = (Integer) currentUser.get("izin");
        }

        if (izin!=1){
            button.setEnabled(false);
        }

        scrollView.scrollTo(0,0);
        baslama.setFocusableInTouchMode(true);
        baslama.isFocused();

        no=getIntent().getIntExtra("No",0);

        baslama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar=Calendar.getInstance();
                int saaat=calendar.get(Calendar.HOUR_OF_DAY);
                int dakika=calendar.get(Calendar.MINUTE);

                TimePickerDialog tdialog= new TimePickerDialog(VardiyaBilgiGoster.this,android.R.style.Theme_Holo_Light_Dialog,timepicker1,saaat,dakika,true);
                tdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.GRAY));
                tdialog.show();
            }
        });
        timepicker1=new TimePickerDialog.OnTimeSetListener() {
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
                baslama.setText(s+":"+d);
            }
        };
        bitis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar=Calendar.getInstance();
                int saaat=calendar.get(Calendar.HOUR_OF_DAY);
                int dakika=calendar.get(Calendar.MINUTE);

                TimePickerDialog tdialog= new TimePickerDialog(VardiyaBilgiGoster.this,android.R.style.Theme_Holo_Light_Dialog,timepicker2,saaat,dakika,true);
                tdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.GRAY));
                tdialog.show();
            }
        });
        timepicker2=new TimePickerDialog.OnTimeSetListener() {
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
                bitis.setText(s+":"+d);
            }
        };

        ParseQuery<ParseObject>query=ParseQuery.getQuery("VardiyaBilgi");
        query.whereEqualTo("No",no);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e!=null){
                    e.printStackTrace();
                }else{
                    tarih.setText(String.valueOf(object.getDate("Tarih").getDate())+"."+String.valueOf(object.getDate("Tarih").getMonth()+1)+"."+String.valueOf(object.getDate("Tarih").getYear()+1900));
                    vardiya.setText(object.getString("Vardiya"));
                    baslama.setText(object.getString("SFirinBas"));
                    bitis.setText(object.getString("SFirinBit"));
                    if (object.getBoolean("AgirMineral")==true){
                        cb_a_min.setChecked(true);
                        Log.e("agir minearl","true");
                    }
                    if (object.getBoolean("Silis")==true){
                        cb_silis.setChecked(true);
                    }
                    if(object.getInt("iri_rutil")!=0){
                        iri_rutil.setText(String.valueOf(object.getInt("iri_rutil")));
                    }
                    if(object.getInt("orta_rutil")!=0){
                        orta_rutil.setText(String.valueOf(object.getInt("orta_rutil")));
                    }
                    if(object.getInt("ince_rutil")!=0){
                        ince_rutil.setText(String.valueOf(object.getInt("ince_rutil")));
                    }
                    if(object.getInt("iri_2000")!=0){
                        iri_2000.setText(String.valueOf(object.getInt("iri_2000")));
                    }
                    if(object.getInt("ince_2000")!=0){
                        ince_2000.setText(String.valueOf(object.getInt("ince_2000")));
                    }
                    if(object.getInt("manyetit")!=0){
                        manyetit.setText(String.valueOf(object.getInt("manyetit")));
                    }
                    if(object.getInt("g2040")!=0){
                        g2040.setText(String.valueOf(object.getInt("g2040")));
                    }
                    if(object.getInt("g3060")!=0){
                        g3060.setText(String.valueOf(object.getInt("g3060")));
                    }
                    if(object.getInt("g80")!=0){
                        g80.setText(String.valueOf(object.getInt("g80")));
                    }
                    if(object.getInt("g180")!=0){
                        g180.setText(String.valueOf(object.getInt("g180")));
                    }
                    if (!object.getString("Ariza").isEmpty()){
                        ariza.setText(object.getString("Ariza"));
                    }
                    if (!object.getString("Yapilanis").isEmpty()){
                        yapilanis.setText(object.getString("Yapilanis"));
                    }
                    if (!object.getString("Aciklama").isEmpty()){
                        aciklama.setText(object.getString("Aciklama"));
                    }
                }
                ilk_toplam=Toplam_vbg_bigbag();
            }
        });
    }
    public Integer Toplam_vbg_bigbag() {
        Integer sonuc = 0;
        if (!iri_rutil.getText().toString().isEmpty()) {
            sonuc = sonuc + Integer.valueOf(iri_rutil.getText().toString());
        }
        if (!orta_rutil.getText().toString().isEmpty()) {
            sonuc = sonuc + Integer.valueOf(orta_rutil.getText().toString());
        }
        if (!ince_rutil.getText().toString().isEmpty()) {
            sonuc = sonuc + Integer.valueOf(ince_rutil.getText().toString());
        }
        if (!iri_2000.getText().toString().isEmpty()) {
            sonuc = sonuc + Integer.valueOf(iri_2000.getText().toString());
        }
        if (!ince_2000.getText().toString().isEmpty()) {
            sonuc = sonuc + Integer.valueOf(ince_2000.getText().toString());
        }
        if (!g2040.getText().toString().isEmpty()) {
            sonuc = sonuc + Integer.valueOf(g2040.getText().toString());
        }
        if (!g3060.getText().toString().isEmpty()) {
            sonuc = sonuc + Integer.valueOf(g3060.getText().toString());
        }
        return sonuc;
    }

    public String Sure(String baslama,String bitis){
        String sonuc;
        Integer sure_saat,sure_dak;
        String[] basla =baslama.split(":",2);
        String[] bitir =bitis.split(":",2);
        Integer bas_saat=Integer.parseInt(basla[0]);
        Integer bas_dak=Integer.parseInt(basla[1]);
        Integer bit_saat=Integer.parseInt(bitir[0]);
        Integer bit_dak=Integer.parseInt(bitir[1]);

        if(bit_dak==0){
            bit_dak=60;
            bit_saat=bit_saat-1;
            if (bit_dak-bas_dak>=0){
                sure_dak=bit_dak-bas_dak;
                Log.e("d",String.valueOf(sure_dak));
                sure_saat=bit_saat-bas_saat;
                Log.e("s",String.valueOf(sure_saat));
            }else{
                bit_dak=bit_dak+60;
                bit_saat=bit_saat-1;
                sure_dak=bit_dak-bas_dak;
                Log.e("d",String.valueOf(sure_dak));
                sure_saat=bit_saat-bas_saat;
                Log.e("s",String.valueOf(sure_saat));
            }
        }else{
            if (bit_dak-bas_dak>=0){
                sure_dak=bit_dak-bas_dak;
                Log.e("d",String.valueOf(sure_dak));
                sure_saat=bit_saat-bas_saat;
                Log.e("s",String.valueOf(sure_saat));
            }else{
                bit_dak=bit_dak+60;
                bit_saat=bit_saat-1;
                sure_dak=bit_dak-bas_dak;
                Log.e("d",String.valueOf(sure_dak));
                sure_saat=bit_saat-bas_saat;
                Log.e("s",String.valueOf(sure_saat));
            }
        }
        sonuc=sure_saat+":"+sure_dak;
        return sonuc;
    }

    public void Guncelle(View view){
        sorun=false;
        final ParseQuery<ParseObject>query=ParseQuery.getQuery("VardiyaBilgi");
        query.whereEqualTo("No",no);
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e!=null){
                    e.printStackTrace();
                }else{
                    id=object.getObjectId();
                    ParseQuery<ParseObject>qq=ParseQuery.getQuery("VardiyaBilgi");
                    query.getInBackground(id, new GetCallback<ParseObject>() {
                        @Override
                        public void done(ParseObject object, ParseException e) {
                            if (e!=null){
                                e.printStackTrace();
                            }else{

                                if (vardiya.getText().toString().equalsIgnoreCase("Gece")){
                                    if (!baslama.getText().toString().isEmpty() && bitis.getText().toString().isEmpty()){
                                        bitis.setText("08:00");
                                    }else if (baslama.getText().toString().isEmpty() && !bitis.getText().toString().isEmpty()){
                                        baslama.setText("00:00");
                                    }
                                }else if (vardiya.getText().toString().equalsIgnoreCase("Akşam")){
                                    if (!baslama.getText().toString().isEmpty() && bitis.getText().toString().isEmpty()){
                                        bitis.setText("00:00");
                                    }else if (baslama.getText().toString().isEmpty() && !bitis.getText().toString().isEmpty()){
                                        baslama.setText("16:00");
                                    }
                                }else if (vardiya.getText().toString().equalsIgnoreCase("Gündüz")){
                                    if (!baslama.getText().toString().isEmpty() && bitis.getText().toString().isEmpty()){
                                        bitis.setText("16:00");
                                    }else if (baslama.getText().toString().isEmpty() && !bitis.getText().toString().isEmpty()){
                                        baslama.setText("08:00");
                                    }
                                }

                                if (!baslama.getText().toString().isEmpty()){
                                    object.put("SFirinBas",baslama.getText().toString());
                                }
                                if (!bitis.getText().toString().isEmpty()){
                                    object.put("SFirinBit",bitis.getText().toString());
                                }
                                if (!baslama.getText().toString().isEmpty() && !bitis.getText().toString().isEmpty()){
                                    object.put("Sure",Sure(baslama.getText().toString(),bitis.getText().toString()));
                                }
                                if (cb_a_min.isChecked()){
                                    object.put("AgirMineral",true);
                                }
                                if (cb_silis.isChecked()){
                                    object.put("Silis",true);
                                }
                                if (!iri_rutil.getText().toString().isEmpty()){
                                    object. put("iri_rutil",Integer.parseInt(iri_rutil.getText().toString()));
                                }else{object.put("iri_rutil",0);}
                                if (!orta_rutil.getText().toString().isEmpty()){
                                    object. put("orta_rutil",Integer.parseInt(orta_rutil.getText().toString()));
                                }else{object.put("orta_rutil",0);}
                                if (!ince_rutil.getText().toString().isEmpty()){
                                    object. put("ince_rutil",Integer.parseInt(ince_rutil.getText().toString()));
                                }else{object.put("ince_rutil",0);}
                                if (!iri_2000.getText().toString().isEmpty()){
                                    object. put("iri_2000",Integer.parseInt(iri_2000.getText().toString()));
                                }else{object.put("iri_2000",0);}
                                if (!ince_2000.getText().toString().isEmpty()){
                                    object. put("ince_2000",Integer.parseInt(ince_2000.getText().toString()));
                                }else{object.put("ince_2000",0);}
                                if (!manyetit.getText().toString().isEmpty()){
                                    object. put("manyetit",Integer.parseInt(manyetit.getText().toString()));
                                }else{object.put("manyetit",0);}
                                if (!g2040.getText().toString().isEmpty()){
                                    object. put("g2040",Integer.parseInt(g2040.getText().toString()));
                                }else{object.put("g2040",0);}
                                if (!g3060.getText().toString().isEmpty()){
                                    object. put("g3060",Integer.parseInt(g3060.getText().toString()));
                                }else{object.put("g3060",0);}
                                if (!g80.getText().toString().isEmpty()){
                                    object. put("g80",Integer.parseInt(g80.getText().toString()));
                                }else{object.put("g80",0);}
                                if (!g180.getText().toString().isEmpty()){
                                    object. put("g180",Integer.parseInt(g180.getText().toString()));
                                }else{object.put("g180",0);}
                                if (!ariza.getText().toString().isEmpty()){
                                    object. put("Ariza",ariza.getText().toString());
                                }
                                if (!yapilanis.getText().toString().isEmpty()){
                                    object. put("Yapilanis",yapilanis.getText().toString());
                                }
                                if (!aciklama.getText().toString().isEmpty()){
                                    object. put("Aciklama",aciklama.getText().toString());
                                }
                                object.put("User", ParseUser.getCurrentUser().getUsername());
                                object.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        if (e!=null){
                                            Toast.makeText(getApplicationContext(),"Bir Sorun Var",Toast.LENGTH_LONG).show();
                                            sorun=true;
                                        }else{
                                            Toast.makeText(getApplicationContext(),"Güncellendi",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });

        if (sorun==false){
            ParseQuery<ParseObject>query1= ParseQuery.getQuery("Bigbag");
            query1.getInBackground("rcTCWWH7Iz", new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {
                    if (e==null){
                        object.increment("Miktar",Toplam_vbg_bigbag()-ilk_toplam);
                        object.saveInBackground();
                    }else{
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
