package pomex.garnet;

import androidx.annotation.IntegerRes;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.w3c.dom.Text;

import java.lang.annotation.Target;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class VardiyaBilgi extends AppCompatActivity {
    private TextView tarih,vardiya,baslama,bitis;
    private EditText iri_rutil,orta_rutil,ince_rutil,iri_2000,ince_2000,manyetit,g2040,g3060,g80,g180,ariza,yapilanis,aciklama;
    private CheckBox cb_a_min,cb_silis;
    private Button button;
    private Calendar calendar;
    private TimePickerDialog.OnTimeSetListener timepicker1,timepicker2;
    private Date date;
    private DateFormat df;
    private ScrollView scrollView;
    private Integer no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vardiya_bilgi);
        getSupportActionBar().hide();

        scrollView=findViewById(R.id.scrollView_vard);
        tarih=findViewById(R.id.tv_vard_tarih);
        vardiya=findViewById(R.id.tv_vard_vardiya);
        baslama=findViewById(R.id.tv_vard_bas_saat);
        bitis=findViewById(R.id.tv_vard_bit_saat);
        iri_rutil=findViewById(R.id.et_vard_alnn_irirutil);
        orta_rutil=findViewById(R.id.et_vard_alnn_ortarutil);
        ince_rutil=findViewById(R.id.et_vard_alnn_incerutil);
        iri_2000=findViewById(R.id.et_vard_alnn_iri2000);
        ince_2000=findViewById(R.id.et_vard_alnn_ince2000);
        manyetit=findViewById(R.id.et_vard_alnn_manyetit);
        g2040=findViewById(R.id.et_vard_alnn_2040);
        g3060=findViewById(R.id.et_vard_alnn_3060);
        g80=findViewById(R.id.et_vard_alnn_80);
        g180=findViewById(R.id.et_vard_alnn_180);
        ariza=findViewById(R.id.et_vard_ariza);
        yapilanis=findViewById(R.id.et_vard_yapilan);
        aciklama=findViewById(R.id.et_vard_aciklama);
        cb_a_min=findViewById(R.id.cb_vard_amin);
        cb_silis=findViewById(R.id.cb_vard_silis);
        button=findViewById(R.id.button_vard_kaydet);

        scrollView.scrollTo(0,0);
        baslama.setFocusableInTouchMode(true);
        baslama.isFocused();

        vardiya.setText(getIntent().getStringExtra("Vardiya"));

        ParseQuery<ParseObject> query=ParseQuery.getQuery("VardiyaBilgi");
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

        calendar=Calendar.getInstance();

        if (calendar.get(calendar.HOUR_OF_DAY)<1){
            tarih.setText((calendar.get(calendar.DAY_OF_MONTH)-1)+"."+(calendar.get(calendar.MONTH)+1)+"."+calendar.get(calendar.YEAR));
            String sss=calendar.get(calendar.DAY_OF_MONTH)+"/"+(calendar.get(calendar.MONTH)+1)+"/"+calendar.get(calendar.YEAR)+" 13:00";
            df=new SimpleDateFormat("dd/MM/yyyy hh:mm");
            try {
                date=df.parse(sss);
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }
        }else{
            tarih.setText(calendar.get(calendar.DAY_OF_MONTH)+"."+(calendar.get(calendar.MONTH)+1)+"."+calendar.get(calendar.YEAR));
            String sss=calendar.get(calendar.DAY_OF_MONTH)+"/"+(calendar.get(calendar.MONTH)+1)+"/"+calendar.get(calendar.YEAR)+" 13:00";
            df=new SimpleDateFormat("dd/MM/yyyy hh:mm");
            try {
                date=df.parse(sss);
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }
        }

        baslama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar=Calendar.getInstance();
                int saaat=calendar.get(Calendar.HOUR_OF_DAY);
                int dakika=calendar.get(Calendar.MINUTE);

                TimePickerDialog tdialog= new TimePickerDialog(VardiyaBilgi.this,android.R.style.Theme_Holo_Light_Dialog,timepicker1,saaat,dakika,true);
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

                TimePickerDialog tdialog= new TimePickerDialog(VardiyaBilgi.this,android.R.style.Theme_Holo_Light_Dialog,timepicker2,saaat,dakika,true);
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

    public Integer Toplam_bigbag(){
        Integer sonuc=0;
        if (!iri_rutil.getText().toString().isEmpty()){
            sonuc=sonuc+Integer.valueOf(iri_rutil.getText().toString());
        }
        if (!orta_rutil.getText().toString().isEmpty()){
            sonuc=sonuc+Integer.valueOf(orta_rutil.getText().toString());
        }
        if (!ince_rutil.getText().toString().isEmpty()){
            sonuc=sonuc+Integer.valueOf(ince_rutil.getText().toString());
        }
        if (!iri_2000.getText().toString().isEmpty()){
            sonuc=sonuc+Integer.valueOf(iri_2000.getText().toString());
        }
        if (!ince_2000.getText().toString().isEmpty()){
            sonuc=sonuc+Integer.valueOf(ince_2000.getText().toString());
        }
        if (!g2040.getText().toString().isEmpty()){
            sonuc=sonuc+Integer.valueOf(g2040.getText().toString());
        }
        if (!g3060.getText().toString().isEmpty()){
            sonuc=sonuc+Integer.valueOf(g3060.getText().toString());
        }
        return sonuc;
    }

    public void Vardiya_Kaydet(View view){
        ParseQuery<ParseObject>query= ParseQuery.getQuery("Bigbag");
        query.getInBackground("rcTCWWH7Iz", new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e==null){
                    object.increment("Miktar",Toplam_bigbag());
                    object.saveInBackground();
                }else{
                    e.printStackTrace();
                }
            }
        });

        ParseObject object=new ParseObject("VardiyaBilgi");
        object.put("No",no);
        object.put("Tarih",date);
        object.put("Vardiya",vardiya.getText().toString());

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
        if(cb_a_min.isChecked()){
            object.put("AgirMineral",true);
        }
        if(cb_silis.isChecked()){
            object.put("Silis",true);
        }
        if (!iri_rutil.getText().toString().isEmpty()){
            object. put("iri_rutil",Integer.parseInt(iri_rutil.getText().toString()));
        }
        if (!orta_rutil.getText().toString().isEmpty()){
            object. put("orta_rutil",Integer.parseInt(orta_rutil.getText().toString()));
        }
        if (!ince_rutil.getText().toString().isEmpty()){
            object. put("ince_rutil",Integer.parseInt(ince_rutil.getText().toString()));
        }
        if (!iri_2000.getText().toString().isEmpty()){
            object. put("iri_2000",Integer.parseInt(iri_2000.getText().toString()));
        }
        if (!ince_2000.getText().toString().isEmpty()){
            object. put("ince_2000",Integer.parseInt(ince_2000.getText().toString()));
        }
        if (!manyetit.getText().toString().isEmpty()){
            object. put("manyetit",Integer.parseInt(manyetit.getText().toString()));
        }
        if (!g2040.getText().toString().isEmpty()){
            object. put("g2040",Integer.parseInt(g2040.getText().toString()));
        }
        if (!g3060.getText().toString().isEmpty()){
            object. put("g3060",Integer.parseInt(g3060.getText().toString()));
        }
        if (!g80.getText().toString().isEmpty()){
            object. put("g80",Integer.parseInt(g80.getText().toString()));
        }
        if (!g180.getText().toString().isEmpty()){
            object. put("g180",Integer.parseInt(g180.getText().toString()));
        }
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
                }else{
                    Toast.makeText(getApplicationContext(),"Kayıt Edildi",Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }
}
