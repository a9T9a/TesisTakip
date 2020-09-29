package pomex.garnet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class AgirMineralUrunDagilim extends AppCompatActivity {
    private TextView tarih,saat,y_manyetit,y_1020,y_2040,y_3060,y_80,y_180,y_iri_rutil,y_orta_rutil,y_ince_rutil,y_iri2000,y_ince2000,y_toz;
    private EditText s_besleme,s_manyetit,s_1020,s_2040,s_3060,s_80,s_180,s_iri_rutil,s_orta_rutil,s_ince_rutil,s_iri2000,s_ince2000,s_toz;
    private EditText m_besleme,m_manyetit,m_1020,m_2040,m_3060,m_80,m_180,m_iri_rutil,m_orta_rutil,m_ince_rutil,m_iri2000,m_ince2000,m_toz;
    private Spinner spin_bes,spin_man,spin_1020,spin_2040,spin_3060,spin_80,spin_180,spin_irir,spin_ortar,spin_incer,spin_iri2,spin_ince2,spin_toz;
    private Spinner spin_bes2,spin_man2,spin_10202,spin_20402,spin_30602,spin_802,spin_1802,spin_irir2,spin_ortar2,spin_incer2,spin_iri22,spin_ince22,spin_toz2;
    private Double besleme,manyetit,g1020,g2040,g3060,g80,g180,iri_rutil,orta_rutil,ince_rutil,iri2000,ince2000,toz;
    private ArrayList<String> sure=new ArrayList<>();
    private ArrayList<String> agirlik=new ArrayList<>();
    private ArrayAdapter<String> sureadp,agirlikadp;
    private DatePickerDialog.OnDateSetListener datepicker;
    private TimePickerDialog.OnTimeSetListener timepicker;
    private Calendar cal;
    private Date date;
    private DateFormat df;
    private Button k_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agir_mineral_urun_dagilim);
        getSupportActionBar().hide();

        tarih=findViewById(R.id.tv_ud_tarih);
        saat=findViewById(R.id.tv_ud_saat);

        k_btn=findViewById(R.id.button_k_amud);

        y_manyetit=findViewById(R.id.tv_ud_y_man);
        y_1020=findViewById(R.id.tv_ud_y_1020);
        y_2040=findViewById(R.id.tv_ud_y_2040);
        y_3060=findViewById(R.id.tv_ud_y_3060);
        y_80=findViewById(R.id.tv_ud_y_80);
        y_180=findViewById(R.id.tv_ud_y_180);
        y_iri_rutil=findViewById(R.id.tv_ud_y_irir);
        y_orta_rutil=findViewById(R.id.tv_ud_y_ortar);
        y_ince_rutil=findViewById(R.id.tv_ud_y_incer);
        y_iri2000=findViewById(R.id.tv_ud_y_iri2);
        y_ince2000=findViewById(R.id.tv_ud_y_ince2);
        y_toz=findViewById(R.id.tv_ud_y_toz);

        m_besleme=findViewById(R.id.et_ud_besleme);
        m_manyetit=findViewById(R.id.et_ud_manyetit);
        m_1020=findViewById(R.id.et_ud_1020);
        m_2040=findViewById(R.id.et_ud_2040);
        m_3060=findViewById(R.id.et_ud_3060);
        m_80=findViewById(R.id.et_ud_80);
        m_180=findViewById(R.id.et_ud_180);
        m_iri_rutil=findViewById(R.id.et_ud_irir);
        m_orta_rutil=findViewById(R.id.et_ud_ortar);
        m_ince_rutil=findViewById(R.id.et_ud_incer);
        m_iri2000=findViewById(R.id.et_ud_iri2000);
        m_ince2000=findViewById(R.id.et_ud_ince2000);
        m_toz=findViewById(R.id.et_ud_toz);

        s_besleme=findViewById(R.id.et_ud_besleme_sure);
        s_manyetit=findViewById(R.id.et_ud_man_sure);
        s_1020=findViewById(R.id.et_ud_1020_sure);
        s_2040=findViewById(R.id.et_ud_2040_sure);
        s_3060=findViewById(R.id.et_ud_3060_sure);
        s_80=findViewById(R.id.et_ud_80_sure);
        s_180=findViewById(R.id.et_ud_180_sure);
        s_iri_rutil=findViewById(R.id.et_ud_irir_sure);
        s_orta_rutil=findViewById(R.id.et_ud_ortar_sure);
        s_ince_rutil=findViewById(R.id.et_ud_incer_sure);
        s_iri2000=findViewById(R.id.et_ud_iri2_sure);
        s_ince2000=findViewById(R.id.et_ud_ince2_sure);
        s_toz=findViewById(R.id.et_ud_toz_sure);

        spin_bes=findViewById(R.id.spinner_ud_sure13);
        spin_man=findViewById(R.id.spinner_ud_sure12);
        spin_1020=findViewById(R.id.spinner_ud_sure);
        spin_2040=findViewById(R.id.spinner_ud_sure2);
        spin_3060=findViewById(R.id.spinner_ud_sure3);
        spin_80=findViewById(R.id.spinner_ud_sure4);
        spin_180=findViewById(R.id.spinner_ud_sure5);
        spin_irir=findViewById(R.id.spinner_ud_sure6);
        spin_ortar=findViewById(R.id.spinner_ud_sure7);
        spin_incer=findViewById(R.id.spinner_ud_sure8);
        spin_iri2=findViewById(R.id.spinner_ud_sure9);
        spin_ince2=findViewById(R.id.spinner_ud_sure10);
        spin_toz=findViewById(R.id.spinner_ud_sure11);

        spin_bes2=findViewById(R.id.spinner_ud_sure14);
        spin_man2=findViewById(R.id.spinner_ud_sure15);
        spin_10202=findViewById(R.id.spinner_ud_sure16);
        spin_20402=findViewById(R.id.spinner_ud_sure17);
        spin_30602=findViewById(R.id.spinner_ud_sure18);
        spin_802=findViewById(R.id.spinner_ud_sure19);
        spin_1802=findViewById(R.id.spinner_ud_sure20);
        spin_irir2=findViewById(R.id.spinner_ud_sure21);
        spin_ortar2=findViewById(R.id.spinner_ud_sure22);
        spin_incer2=findViewById(R.id.spinner_ud_sure23);
        spin_iri22=findViewById(R.id.spinner_ud_sure24);
        spin_ince22=findViewById(R.id.spinner_ud_sure25);
        spin_toz2=findViewById(R.id.spinner_ud_sure26);


        sure.addAll(Arrays.asList("Saniye","Dakika","Saat"));
        sureadp=new ArrayAdapter<String>(AgirMineralUrunDagilim.this,R.layout.spinnerlayout,sure);
        spin_bes.setAdapter(sureadp);
        spin_man.setAdapter(sureadp);
        spin_1020.setAdapter(sureadp);
        spin_2040.setAdapter(sureadp);
        spin_3060.setAdapter(sureadp);
        spin_80.setAdapter(sureadp);
        spin_180.setAdapter(sureadp);
        spin_irir.setAdapter(sureadp);
        spin_ortar.setAdapter(sureadp);
        spin_incer.setAdapter(sureadp);
        spin_iri2.setAdapter(sureadp);
        spin_ince2.setAdapter(sureadp);
        spin_toz.setAdapter(sureadp);

        agirlik.addAll(Arrays.asList("gr","kg"));
        agirlikadp=new ArrayAdapter<String>(AgirMineralUrunDagilim.this,R.layout.spinnerlayout,agirlik);
        spin_bes2.setAdapter(agirlikadp);
        spin_man2.setAdapter(agirlikadp);
        spin_10202.setAdapter(agirlikadp);
        spin_20402.setAdapter(agirlikadp);
        spin_30602.setAdapter(agirlikadp);
        spin_802.setAdapter(agirlikadp);
        spin_1802.setAdapter(agirlikadp);
        spin_irir2.setAdapter(agirlikadp);
        spin_ortar2.setAdapter(agirlikadp);
        spin_incer2.setAdapter(agirlikadp);
        spin_iri22.setAdapter(agirlikadp);
        spin_ince22.setAdapter(agirlikadp);
        spin_toz2.setAdapter(agirlikadp);

        tarih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cal=Calendar.getInstance();
                int year=cal.get(Calendar.YEAR);
                int month=cal.get(Calendar.MONTH);
                int day=cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog ddialog=new DatePickerDialog(AgirMineralUrunDagilim.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,datepicker,year,month,day);
                ddialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                ddialog.show();
            }
        });

        saat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cal=Calendar.getInstance();
                int saaat=cal.get(Calendar.HOUR_OF_DAY);
                int dakika=cal.get(Calendar.MINUTE);

                TimePickerDialog tdialog= new TimePickerDialog(AgirMineralUrunDagilim.this,android.R.style.Theme_Holo_Light_Dialog,timepicker,saaat,dakika,true);
                tdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.GRAY));
                tdialog.show();
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
    }

    public void hesap(View view){
        if (!m_besleme.getText().toString().isEmpty()&&!s_besleme.getText().toString().isEmpty()){
            Double a=0.0;
            Double s=0.0;
            if (spin_bes.getSelectedItem().toString().equalsIgnoreCase("Saniye")){
                s= 3600/Double.valueOf(s_besleme.getText().toString());
            }
            else if (spin_bes.getSelectedItem().toString().equalsIgnoreCase("Dakika")){
                s= 60*(Double.valueOf(s_besleme.getText().toString()));
            }
            else if (spin_bes.getSelectedItem().toString().equalsIgnoreCase("Saat")){
                s= 1/(Double.valueOf(s_besleme.getText().toString()));
            }

            if (spin_bes2.getSelectedItem().toString().equalsIgnoreCase("gr")){
                a=(Double.valueOf(m_besleme.getText().toString()))/1000;
            }
            else if (spin_bes2.getSelectedItem().toString().equalsIgnoreCase("kg")){
                a=(Double.valueOf(m_besleme.getText().toString()));
            }

            besleme=a*s;
            Log.e("bes",String.valueOf(besleme));
        }else{
            Toast.makeText(getApplicationContext(),"Besleme Değerlerini Gir",Toast.LENGTH_SHORT).show();
        }

        if (!m_manyetit.getText().toString().isEmpty()&&!s_manyetit.getText().toString().isEmpty()){
            Double a=0.0;
            Double s=0.0;
            if (spin_man.getSelectedItem().toString().equalsIgnoreCase("Saniye")){
                s= 3600/Double.valueOf(s_manyetit.getText().toString());
            }
            else if (spin_man.getSelectedItem().toString().equalsIgnoreCase("Dakika")){
                s= 60*(Double.valueOf(s_manyetit.getText().toString()));
            }
            else if (spin_bes.getSelectedItem().toString().equalsIgnoreCase("Saat")){
                s= 1/(Double.valueOf(s_besleme.getText().toString()));
            }

            if (spin_man2.getSelectedItem().toString().equalsIgnoreCase("gr")){
                a=(Double.valueOf(m_manyetit.getText().toString()))/1000;
            }
            else if (spin_man2.getSelectedItem().toString().equalsIgnoreCase("kg")){
                a=(Double.valueOf(m_manyetit.getText().toString()));
            }

            manyetit=a*s;
        }else{
            Toast.makeText(getApplicationContext(),"Manyetit Değerlerini Gir",Toast.LENGTH_SHORT).show();
        }

        if (!m_1020.getText().toString().isEmpty()&&!s_1020.getText().toString().isEmpty()){
            Double a=0.0;
            Double s=0.0;
            if (spin_1020.getSelectedItem().toString().equalsIgnoreCase("Saniye")){
                s= 3600/Double.valueOf(s_1020.getText().toString());
            }
            else if (spin_1020.getSelectedItem().toString().equalsIgnoreCase("Dakika")){
                s= 60*(Double.valueOf(s_1020.getText().toString()));
            }
            else if (spin_bes.getSelectedItem().toString().equalsIgnoreCase("Saat")){
                s= 1/(Double.valueOf(s_besleme.getText().toString()));
            }

            if (spin_10202.getSelectedItem().toString().equalsIgnoreCase("gr")){
                a=(Double.valueOf(m_1020.getText().toString()))/1000;
            }
            else if (spin_10202.getSelectedItem().toString().equalsIgnoreCase("kg")){
                a=(Double.valueOf(m_1020.getText().toString()));
            }

            g1020=a*s;
        }else{
            Toast.makeText(getApplicationContext(),"10/20 Değerlerini Gir",Toast.LENGTH_SHORT).show();
        }

        if (!m_2040.getText().toString().isEmpty()&&!s_2040.getText().toString().isEmpty()){
            Double a=0.0;
            Double s=0.0;
            if (spin_2040.getSelectedItem().toString().equalsIgnoreCase("Saniye")){
                s= 3600/Double.valueOf(s_2040.getText().toString());
            }
            else if (spin_2040.getSelectedItem().toString().equalsIgnoreCase("Dakika")){
                s= 60*(Double.valueOf(s_2040.getText().toString()));
            }
            else if (spin_bes.getSelectedItem().toString().equalsIgnoreCase("Saat")){
                s= 1/(Double.valueOf(s_besleme.getText().toString()));
            }

            if (spin_20402.getSelectedItem().toString().equalsIgnoreCase("gr")){
                a=(Double.valueOf(m_2040.getText().toString()))/1000;
            }
            else if (spin_20402.getSelectedItem().toString().equalsIgnoreCase("kg")){
                a=(Double.valueOf(m_2040.getText().toString()));
            }

            g2040=a*s;
        }else{
            Toast.makeText(getApplicationContext(),"20/40 Değerlerini Gir",Toast.LENGTH_SHORT).show();
        }

        if (!m_3060.getText().toString().isEmpty()&&!s_3060.getText().toString().isEmpty()){
            Double a=0.0;
            Double s=0.0;
            if (spin_3060.getSelectedItem().toString().equalsIgnoreCase("Saniye")){
                s= 3600/Double.valueOf(s_3060.getText().toString());
            }
            else if (spin_3060.getSelectedItem().toString().equalsIgnoreCase("Dakika")){
                s= 60*(Double.valueOf(s_3060.getText().toString()));
            }
            else if (spin_bes.getSelectedItem().toString().equalsIgnoreCase("Saat")){
                s= 1/(Double.valueOf(s_besleme.getText().toString()));
            }

            if (spin_30602.getSelectedItem().toString().equalsIgnoreCase("gr")){
                a=(Double.valueOf(m_3060.getText().toString()))/1000;
            }
            else if (spin_30602.getSelectedItem().toString().equalsIgnoreCase("kg")){
                a=(Double.valueOf(m_3060.getText().toString()));
            }

            g3060=a*s;
        }else{
            Toast.makeText(getApplicationContext(),"30/60 Değerlerini Gir",Toast.LENGTH_SHORT).show();
        }

        if (!m_80.getText().toString().isEmpty()&&!s_80.getText().toString().isEmpty()){
            Double a=0.0;
            Double s=0.0;
            if (spin_80.getSelectedItem().toString().equalsIgnoreCase("Saniye")){
                s= 3600/Double.valueOf(s_80.getText().toString());
            }
            else if (spin_80.getSelectedItem().toString().equalsIgnoreCase("Dakika")){
                s= 60*(Double.valueOf(s_80.getText().toString()));
            }
            else if (spin_bes.getSelectedItem().toString().equalsIgnoreCase("Saat")){
                s= 1/(Double.valueOf(s_besleme.getText().toString()));
            }

            if (spin_802.getSelectedItem().toString().equalsIgnoreCase("gr")){
                a=(Double.valueOf(m_80.getText().toString()))/1000;
            }
            else if (spin_802.getSelectedItem().toString().equalsIgnoreCase("kg")){
                a=(Double.valueOf(m_80.getText().toString()));
            }

            g80=a*s;
        }else{
            Toast.makeText(getApplicationContext(),"80 Değerlerini Gir",Toast.LENGTH_SHORT).show();
        }

        if (!m_180.getText().toString().isEmpty()&&!s_180.getText().toString().isEmpty()){
            Double a=0.0;
            Double s=0.0;
            if (spin_180.getSelectedItem().toString().equalsIgnoreCase("Saniye")){
                s= 3600/Double.valueOf(s_180.getText().toString());
            }
            else if (spin_180.getSelectedItem().toString().equalsIgnoreCase("Dakika")){
                s= 60*(Double.valueOf(s_180.getText().toString()));
            }
            else if (spin_bes.getSelectedItem().toString().equalsIgnoreCase("Saat")){
                s= 1/(Double.valueOf(s_besleme.getText().toString()));
            }

            if (spin_1802.getSelectedItem().toString().equalsIgnoreCase("gr")){
                a=(Double.valueOf(m_180.getText().toString()))/1000;
            }
            else if (spin_1802.getSelectedItem().toString().equalsIgnoreCase("kg")){
                a=(Double.valueOf(m_180.getText().toString()));
            }

            g180=a*s;
        }else{
            Toast.makeText(getApplicationContext(),"180 Değerlerini Gir",Toast.LENGTH_SHORT).show();
        }

        if (!m_iri_rutil.getText().toString().isEmpty()&&!s_iri_rutil.getText().toString().isEmpty()){
            Double a=0.0;
            Double s=0.0;
            if (spin_irir.getSelectedItem().toString().equalsIgnoreCase("Saniye")){
                s= 3600/Double.valueOf(s_iri_rutil.getText().toString());
            }
            else if (spin_irir.getSelectedItem().toString().equalsIgnoreCase("Dakika")){
                s= 60*(Double.valueOf(s_iri_rutil.getText().toString()));
            }
            else if (spin_bes.getSelectedItem().toString().equalsIgnoreCase("Saat")){
                s= 1/(Double.valueOf(s_besleme.getText().toString()));
            }

            if (spin_irir2.getSelectedItem().toString().equalsIgnoreCase("gr")){
                a=(Double.valueOf(m_iri_rutil.getText().toString()))/1000;
            }
            else if (spin_irir2.getSelectedItem().toString().equalsIgnoreCase("kg")){
                a=(Double.valueOf(m_iri_rutil.getText().toString()));
            }

            iri_rutil=a*s;
        }else{
            Toast.makeText(getApplicationContext(),"İri Ruil Değerlerini Gir",Toast.LENGTH_SHORT).show();
        }

        if (!m_orta_rutil.getText().toString().isEmpty()&&!s_orta_rutil.getText().toString().isEmpty()){
            Double a=0.0;
            Double s=0.0;
            if (spin_ortar.getSelectedItem().toString().equalsIgnoreCase("Saniye")){
                s= 3600/Double.valueOf(s_orta_rutil.getText().toString());
            }
            else if (spin_ortar.getSelectedItem().toString().equalsIgnoreCase("Dakika")){
                s= 60*(Double.valueOf(s_orta_rutil.getText().toString()));
            }
            else if (spin_bes.getSelectedItem().toString().equalsIgnoreCase("Saat")){
                s= 1/(Double.valueOf(s_besleme.getText().toString()));
            }

            if (spin_ortar2.getSelectedItem().toString().equalsIgnoreCase("gr")){
                a=(Double.valueOf(m_orta_rutil.getText().toString()))/1000;
            }
            else if (spin_ortar2.getSelectedItem().toString().equalsIgnoreCase("kg")){
                a=(Double.valueOf(m_orta_rutil.getText().toString()));
            }

            orta_rutil=a*s;
        }else{
            Toast.makeText(getApplicationContext(),"Orta Rutil Değerlerini Gir",Toast.LENGTH_SHORT).show();
        }

        if (!m_ince_rutil.getText().toString().isEmpty()&&!s_ince_rutil.getText().toString().isEmpty()){
            Double a=0.0;
            Double s=0.0;
            if (spin_incer.getSelectedItem().toString().equalsIgnoreCase("Saniye")){
                s= 3600/Double.valueOf(s_ince_rutil.getText().toString());
            }
            else if (spin_incer.getSelectedItem().toString().equalsIgnoreCase("Dakika")){
                s= 60*(Double.valueOf(s_ince_rutil.getText().toString()));
            }
            else if (spin_bes.getSelectedItem().toString().equalsIgnoreCase("Saat")){
                s= 1/(Double.valueOf(s_besleme.getText().toString()));
            }

            if (spin_incer2.getSelectedItem().toString().equalsIgnoreCase("gr")){
                a=(Double.valueOf(m_ince_rutil.getText().toString()))/1000;
            }
            else if (spin_incer2.getSelectedItem().toString().equalsIgnoreCase("kg")){
                a=(Double.valueOf(m_ince_rutil.getText().toString()));
            }

            ince_rutil=a*s;
        }else{
            Toast.makeText(getApplicationContext(),"İnce Rutil Değerlerini Gir",Toast.LENGTH_SHORT).show();
        }

        if (!m_iri2000.getText().toString().isEmpty()&&!s_iri2000.getText().toString().isEmpty()){
            Double a=0.0;
            Double s=0.0;
            if (spin_iri2.getSelectedItem().toString().equalsIgnoreCase("Saniye")){
                s= 3600/Double.valueOf(s_iri2000.getText().toString());
            }
            else if (spin_iri2.getSelectedItem().toString().equalsIgnoreCase("Dakika")){
                s= 60*(Double.valueOf(s_iri2000.getText().toString()));
            }
            else if (spin_bes.getSelectedItem().toString().equalsIgnoreCase("Saat")){
                s= 1/(Double.valueOf(s_besleme.getText().toString()));
            }

            if (spin_iri22.getSelectedItem().toString().equalsIgnoreCase("gr")){
                a=(Double.valueOf(m_iri2000.getText().toString()))/1000;
            }
            else if (spin_iri22.getSelectedItem().toString().equalsIgnoreCase("kg")){
                a=(Double.valueOf(m_iri2000.getText().toString()));
            }

            iri2000=a*s;
        }else{
            Toast.makeText(getApplicationContext(),"İri 2000 Değerlerini Gir",Toast.LENGTH_SHORT).show();
        }

        if (!m_ince2000.getText().toString().isEmpty()&&!s_ince2000.getText().toString().isEmpty()){
            Double a=0.0;
            Double s=0.0;
            if (spin_ince2.getSelectedItem().toString().equalsIgnoreCase("Saniye")){
                s= 3600/Double.valueOf(s_ince2000.getText().toString());
            }
            else if (spin_ince2.getSelectedItem().toString().equalsIgnoreCase("Dakika")){
                s= 60*(Double.valueOf(s_ince2000.getText().toString()));
            }
            else if (spin_bes.getSelectedItem().toString().equalsIgnoreCase("Saat")){
                s= 1/(Double.valueOf(s_besleme.getText().toString()));
            }

            if (spin_ince22.getSelectedItem().toString().equalsIgnoreCase("gr")){
                a=(Double.valueOf(m_ince2000.getText().toString()))/1000;
            }
            else if (spin_ince22.getSelectedItem().toString().equalsIgnoreCase("kg")){
                a=(Double.valueOf(m_ince2000.getText().toString()));
            }

            ince2000=a*s;
        }else{
            Toast.makeText(getApplicationContext(),"İnce 2000 Değerlerini Gir",Toast.LENGTH_SHORT).show();
        }

        if (!m_toz.getText().toString().isEmpty()&&!s_toz.getText().toString().isEmpty()){
            Double a=0.0;
            Double s=0.0;
            if (spin_toz.getSelectedItem().toString().equalsIgnoreCase("Saniye")){
                s= 3600/Double.valueOf(s_toz.getText().toString());
            }
            else if (spin_toz.getSelectedItem().toString().equalsIgnoreCase("Dakika")){
                s= 60*(Double.valueOf(s_toz.getText().toString()));
            }
            else if (spin_bes.getSelectedItem().toString().equalsIgnoreCase("Saat")){
                s= 1/(Double.valueOf(s_besleme.getText().toString()));
            }

            if (spin_toz2.getSelectedItem().toString().equalsIgnoreCase("gr")){
                a=(Double.valueOf(m_toz.getText().toString()))/1000;
            }
            else if (spin_toz2.getSelectedItem().toString().equalsIgnoreCase("kg")){
                a=(Double.valueOf(m_toz.getText().toString()));
            }

            toz=a*s;
        }else{
            Toast.makeText(getApplicationContext(),"Toz Değerlerini Gir",Toast.LENGTH_SHORT).show();
        }

        y_manyetit.setText(String.format("%.1f",manyetit/besleme*100));

        Double t=1-(manyetit/besleme);
        Log.e("t",String.valueOf(t));
        Double top=g1020+g2040+g3060+g80+g180+toz+iri_rutil+orta_rutil+ince_rutil+iri2000+ince2000;
        Log.e("top",String.valueOf(top));

        y_1020.setText(String.format("%.1f",g1020/top*t*100));
        y_2040.setText(String.format("%.1f",g2040/top*t*100));
        y_3060.setText(String.format("%.1f",g3060/top*t*100));
        y_80.setText(String.format("%.1f",g80/top*t*100));
        y_180.setText(String.format("%.1f",g180/top*t*100));
        y_toz.setText(String.format("%.1f",toz/top*t*100));
        y_iri_rutil.setText(String.format("%.1f",iri_rutil/top*t*100));
        y_orta_rutil.setText(String.format("%.1f",orta_rutil/top*t*100));
        y_ince_rutil.setText(String.format("%.1f",ince_rutil/top*t*100));
        y_iri2000.setText(String.format("%.1f",iri2000/top*t*100));
        y_ince2000.setText(String.format("%.1f",ince2000/top*t*100));
    }

    public void kaydet(View view){
        k_btn.setEnabled(false);
        ParseObject object = new ParseObject("AMUDagilim");
        object.put("g1020",Double.valueOf(y_1020.getText().toString().replace(",",".")));
        object.put("g2040",Double.valueOf(y_2040.getText().toString().replace(",",".")));
        object.put("g3060",Double.valueOf(y_3060.getText().toString().replace(",",".")));
        object.put("g80",Double.valueOf(y_80.getText().toString().replace(",",".")));
        object.put("g180",Double.valueOf(y_180.getText().toString().replace(",",".")));
        object.put("toz",Double.valueOf(y_toz.getText().toString().replace(",",".")));
        object.put("iri_rutil",Double.valueOf(y_iri_rutil.getText().toString().replace(",",".")));
        object.put("orta_rutil",Double.valueOf(y_orta_rutil.getText().toString().replace(",",".")));
        object.put("ince_rutil",Double.valueOf(y_ince_rutil.getText().toString().replace(",",".")));
        object.put("iri2000",Double.valueOf(y_iri2000.getText().toString().replace(",",".")));
        object.put("ince2000",Double.valueOf(y_ince2000.getText().toString().replace(",",".")));
        object.put("manyetit",Double.valueOf(y_manyetit.getText().toString().replace(",",".")));
        object.put("user", ParseUser.getCurrentUser().getUsername());
        object.put("Tarih",date);
        object.put("Saat",saat.getText().toString());
        object.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e!=null){
                    Toast.makeText(getApplicationContext(),"Bir Sorun Oldu Tekrar Dene",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Kayıt Edildi",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

    }
}
