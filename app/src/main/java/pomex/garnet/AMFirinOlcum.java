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

public class AMFirinOlcum extends AppCompatActivity {
    private TextView tarih,saat,t_malzeme,t_komur,not;
    private EditText malzeme,komur,s_malzeme,s_komur;
    private Spinner sm_sure,sk_sure,sm_agr,sk_agr;
    private DatePickerDialog.OnDateSetListener datepicker;
    private TimePickerDialog.OnTimeSetListener timepicker;
    private Calendar cal;
    private Date date;
    private DateFormat df;
    private Button hesap,kaydet;
    private ArrayList<String> sure=new ArrayList<>();
    private ArrayList<String> agirlik=new ArrayList<>();
    private ArrayAdapter<String> sureadp,agirlikadp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amfirin_olcum);
        getSupportActionBar().hide();

        tarih=findViewById(R.id.tv_amfo_tarih);
        saat=findViewById(R.id.tv_amfo_saat);
        t_malzeme=findViewById(R.id.tv_amfo_mal2);
        t_komur=findViewById(R.id.tv_amfo_kom2);
        malzeme=findViewById(R.id.et_amfo_mal);
        komur=findViewById(R.id.et_amfo_kom);
        s_malzeme=findViewById(R.id.et_amfo_sure1);
        s_komur=findViewById(R.id.et_amfo_sure2);
        sm_agr=findViewById(R.id.spinner_amfo_ag1);
        sk_agr=findViewById(R.id.spinner_amfo_ag2);
        sm_sure=findViewById(R.id.spinner_amfo_sure1);
        sk_sure=findViewById(R.id.spinner_amfo_sure2);
        hesap=findViewById(R.id.button_amfo_hesap);
        kaydet=findViewById(R.id.button_amfo_kaydet);
        not=findViewById(R.id.tv_amfo_not);

        agirlik.addAll(Arrays.asList("gr","kg"));
        agirlikadp=new ArrayAdapter<String>(AMFirinOlcum.this,R.layout.spinnerlayout,agirlik);
        sm_agr.setAdapter(agirlikadp);
        sk_agr.setAdapter(agirlikadp);

        sure.addAll(Arrays.asList("Saniye","Dakika","Saat"));
        sureadp=new ArrayAdapter<String>(AMFirinOlcum.this,R.layout.spinnerlayout,sure);
        sm_sure.setAdapter(sureadp);
        sk_sure.setAdapter(sureadp);

        tarih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cal= Calendar.getInstance();
                int year=cal.get(Calendar.YEAR);
                int month=cal.get(Calendar.MONTH);
                int day=cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog ddialog=new DatePickerDialog(AMFirinOlcum.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,datepicker,year,month,day);
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

                TimePickerDialog tdialog= new TimePickerDialog(AMFirinOlcum.this,android.R.style.Theme_Holo_Light_Dialog,timepicker,saaat,dakika,true);
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

        hesap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                t_komur.requestFocus();
                if (!malzeme.getText().toString().isEmpty()&&!s_malzeme.getText().toString().isEmpty()){
                    Double a=0.0;
                    Double s=0.0;
                    if (sm_sure.getSelectedItem().toString().equalsIgnoreCase("Saniye")){
                        s= 3600/Double.valueOf(s_malzeme.getText().toString());
                    }
                    else if (sm_sure.getSelectedItem().toString().equalsIgnoreCase("Dakika")){
                        s= 60*(Double.valueOf(s_malzeme.getText().toString()));
                    }
                    else if (sm_sure.getSelectedItem().toString().equalsIgnoreCase("Saat")){
                        s= 1/(Double.valueOf(s_malzeme.getText().toString()));
                    }

                    if (sm_agr.getSelectedItem().toString().equalsIgnoreCase("gr")){
                        a=(Double.valueOf(malzeme.getText().toString()))/1000;
                    }
                    else if (sm_agr.getSelectedItem().toString().equalsIgnoreCase("kg")){
                        a=(Double.valueOf(malzeme.getText().toString()));
                    }

                    t_malzeme.setText(String.format("%.1f",a*s));
                }else{
                    Toast.makeText(getApplicationContext(),"Malzeme Değerlerini Gir",Toast.LENGTH_SHORT).show();
                }

                if (!komur.getText().toString().isEmpty()&&!s_komur.getText().toString().isEmpty()){
                    Double a=0.0;
                    Double s=0.0;
                    if (sk_sure.getSelectedItem().toString().equalsIgnoreCase("Saniye")){
                        s= 3600/Double.valueOf(s_komur.getText().toString());
                    }
                    else if (sk_sure.getSelectedItem().toString().equalsIgnoreCase("Dakika")){
                        s= 60*(Double.valueOf(s_komur.getText().toString()));
                    }
                    else if (sk_sure.getSelectedItem().toString().equalsIgnoreCase("Saat")){
                        s= 1/(Double.valueOf(s_komur.getText().toString()));
                    }

                    if (sk_agr.getSelectedItem().toString().equalsIgnoreCase("gr")){
                        a=(Double.valueOf(komur.getText().toString()))/1000;
                    }
                    else if (sk_agr.getSelectedItem().toString().equalsIgnoreCase("kg")){
                        a=(Double.valueOf(komur.getText().toString()));
                    }

                    t_komur.setText(String.format("%.1f",a*s));
                }else{
                    Toast.makeText(getApplicationContext(),"Kömür Değerlerini Gir",Toast.LENGTH_SHORT).show();
                }

                Double k=Double.valueOf(t_malzeme.getText().toString().replace(",","."))*0.13;
                if (k+2<(Double.valueOf(t_komur.getText().toString().replace(",",".")))){
                    not.setText("Kömürü azalt,"+" "+String.format("%.0f",k)+" "+"olması gerekiyor");
                }else if (k-2>(Double.valueOf(t_komur.getText().toString().replace(",",".")))){
                    not.setText("Kömürü arttır,"+" "+String.format("%.0f",k)+" "+"olması gerekiyor");
                }else{
                    not.setText("Kömür oranı uygun");
                }
            }
        });

        kaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kaydet.setEnabled(false);
                ParseObject object=new ParseObject("AMFirinOlcum");
                object.put("Tarih",date);
                object.put("Saat",saat.getText().toString());
                object.put("Malzeme",Double.valueOf(t_malzeme.getText().toString().replace(",",".")));
                object.put("Komur",Double.valueOf(t_komur.getText().toString().replace(",",".")));
                object.put("user", ParseUser.getCurrentUser().getUsername());
                object.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e!=null){
                            e.printStackTrace();
                        }else{
                            Toast.makeText(getApplicationContext(),"Kayıt Edildi",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });
            }
        });

    }
}
