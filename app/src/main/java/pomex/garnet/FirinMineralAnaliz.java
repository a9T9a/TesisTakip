package pomex.garnet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FirinMineralAnaliz extends AppCompatActivity {
    private TextView tarih,saat,y_u600,y_u400,y_u212,y_u160,y_u90,y_a90;
    private EditText u600,u400,u212,u160,u90,a90,man;
    private Double toplam;
    private Button hesap;
    private Calendar cal;
    private DatePickerDialog.OnDateSetListener datepicker;
    private TimePickerDialog.OnTimeSetListener timepicker;
    private Date date;
    private DateFormat df;
    private ListView listView;
    private ArrayList<String> list_tarih,list_man,list_u600,list_u400,list_u212,list_u160,list_u90,list_a90;
    private Boolean act;
    private ScrollView scrollView;
    private Button kayt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firin_mineral_analiz);
        getSupportActionBar().hide();

        act=false;
        scrollView=findViewById(R.id.scrollView3);
        tarih=findViewById(R.id.tv_fma_tarih2);
        saat=findViewById(R.id.tv_fma_saat2);
        u600=findViewById(R.id.et_fma_u600);
        u400=findViewById(R.id.et_fma_u400);
        u212=findViewById(R.id.et_fma_u212);
        u160=findViewById(R.id.et_fma_u160);
        u90=findViewById(R.id.et_fma_u90);
        a90=findViewById(R.id.et_fma_a90);
        man=findViewById(R.id.et_fma_man);
        y_u600=findViewById(R.id.tv_fma_u600_2);
        y_u400=findViewById(R.id.tv_fma_u400_2);
        y_u212=findViewById(R.id.tv_fma_u212_2);
        y_u160=findViewById(R.id.tv_fma_u160_2);
        y_u90=findViewById(R.id.tv_fma_u90_2);
        y_a90=findViewById(R.id.tv_fma_a90_2);
        hesap=findViewById(R.id.button_fma_hesap);
        listView=findViewById(R.id.lv_fma);
        kayt=findViewById(R.id.button_fma_kaydet);

        list_tarih=new ArrayList<>();
        list_man=new ArrayList<>();
        list_u600=new ArrayList<>();
        list_u400=new ArrayList<>();
        list_u212=new ArrayList<>();
        list_u160=new ArrayList<>();
        list_u90=new ArrayList<>();
        list_a90=new ArrayList<>();

        scrollView.scrollTo(0,0);
        tarih.setFocusableInTouchMode(true);
        u600.setFocusableInTouchMode(true);
        tarih.isFocused();

        ParseQuery<ParseObject> query=ParseQuery.getQuery("FirinMineralAnaliz");
        query.orderByDescending("Tarih");query.setLimit(5);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e!=null){
                    e.printStackTrace();
                }else{
                    for (ParseObject object:objects){
                        list_tarih.add(String.valueOf(object.getDate("Tarih").getDate())+"/"+String.valueOf(object.getDate("Tarih").getMonth()+1)+"/"+String.valueOf(object.getDate("Tarih").getYear()+1900));
                        list_man.add(String.valueOf(object.getDouble("Manyetik")));
                        list_u600.add(String.valueOf(object.getDouble("u600")));
                        list_u400.add(String.valueOf(object.getDouble("u400")));
                        list_u212.add(String.valueOf(object.getDouble("u212")));
                        list_u160.add(String.valueOf(object.getDouble("u160")));
                        list_u90.add(String.valueOf(object.getDouble("u90")));
                        list_a90.add(String.valueOf(object.getDouble("a90")));
                    }
                }
                analizlist adp=new analizlist();
                listView.setAdapter(adp);
                listView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,list_tarih.size()*150));
            }
        });

        u600.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                scrollView.scrollTo(0,500);
                return false;
            }
        });

        hesap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (u600.getText().toString().isEmpty() || u400.getText().toString().isEmpty()
                        || u212.getText().toString().isEmpty() || u160.getText().toString().isEmpty()
                        || u90.getText().toString().isEmpty() || a90.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Elek Değerlerini Doldur",Toast.LENGTH_SHORT).show();
                }else{
                    toplam=Double.valueOf(u600.getText().toString())+Double.valueOf(u400.getText().toString())+
                            Double.valueOf(u212.getText().toString())+Double.valueOf(u160.getText().toString())+
                            Double.valueOf(u90.getText().toString())+Double.valueOf(a90.getText().toString());

                    y_u600.setText(String.format("%.1f",Double.valueOf(Double.valueOf(u600.getText().toString())*100/toplam)));
                    y_u400.setText(String.format("%.1f",Double.valueOf(Double.valueOf(u400.getText().toString())*100/toplam)));
                    y_u212.setText(String.format("%.1f",Double.valueOf(Double.valueOf(u212.getText().toString())*100/toplam)));
                    y_u160.setText(String.format("%.1f",Double.valueOf(Double.valueOf(u160.getText().toString())*100/toplam)));
                    y_u90.setText(String.format("%.1f",Double.valueOf(Double.valueOf(u90.getText().toString())*100/toplam)));
                    y_a90.setText(String.format("%.1f",Double.valueOf(Double.valueOf(a90.getText().toString())*100/toplam)));
                    act=true;
                }
            }
        });

        tarih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cal= Calendar.getInstance();
                int year=cal.get(Calendar.YEAR);
                int month=cal.get(Calendar.MONTH);
                int day=cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog ddialog=new DatePickerDialog(FirinMineralAnaliz.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,datepicker,year,month,day);
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

                TimePickerDialog tdialog= new TimePickerDialog(FirinMineralAnaliz.this,android.R.style.Theme_Holo_Light_Dialog,timepicker,saaat,dakika,true);
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

    public void Kaydet_fma(View view){
        kayt.setEnabled(false);
        Boolean sorun=false;
        if (act==false){
            Toast.makeText(getApplicationContext(), "Yüzdeleri Hesaplat", Toast.LENGTH_SHORT).show();
        }else{
            ParseObject object=new ParseObject("FirinMineralAnaliz");
            if (tarih.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(),"Tarih Gir",Toast.LENGTH_SHORT).show();
                sorun=true;
            }else{object.put("Tarih",date);}
            if (saat.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(),"Saat Gir",Toast.LENGTH_SHORT).show();
                sorun=true;
            }else{object.put("Saat",saat.getText().toString());}
            object.put("u600",Double.parseDouble(y_u600.getText().toString().replace(",",".")));
            object.put("u400",Double.parseDouble(y_u400.getText().toString().replace(",",".")));
            object.put("u212",Double.parseDouble(y_u212.getText().toString().replace(",",".")));
            object.put("u160",Double.parseDouble(y_u160.getText().toString().replace(",",".")));
            object.put("u90",Double.parseDouble(y_u90.getText().toString().replace(",",".")));
            object.put("a90",Double.parseDouble(y_a90.getText().toString().replace(",",".")));
            if (man.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(), "Manyetik Oranını Gir", Toast.LENGTH_SHORT).show();
                sorun=true;
            }else{
                object.put("Manyetik",Double.parseDouble(man.getText().toString()));
            }
            object.put("User", ParseUser.getCurrentUser().getUsername());
            if (sorun==false){
                object.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e==null){
                            Toast.makeText(getApplicationContext(),"Kayıt Edildi",Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    }
    class analizlist extends BaseAdapter {

        @Override
        public int getCount() {
            return list_tarih.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view=getLayoutInflater().inflate(R.layout.customlist_fma,null);

            TextView tv_tarih=view.findViewById(R.id.tv_cfma_tarih);
            TextView tv_man=view.findViewById(R.id.tv_cfma_man);
            TextView tv_u600=view.findViewById(R.id.tv_cfma_u600);
            TextView tv_u400=view.findViewById(R.id.tv_cfma_u400);
            TextView tv_u212=view.findViewById(R.id.tv_cfma_u212);
            TextView tv_u160=view.findViewById(R.id.tv_cfma_u160);
            TextView tv_u90=view.findViewById(R.id.tv_cfma_u90);
            TextView tv_a90=view.findViewById(R.id.tv_cfma_a90);

            tv_tarih.setText("Tarih :"+list_tarih.get(i));
            tv_man.setText("Manyetik : % "+list_man.get(i));
            tv_u600.setText(list_u600.get(i));
            tv_u400.setText(list_u400.get(i));
            tv_u212.setText(list_u212.get(i));
            tv_u160.setText(list_u160.get(i));
            tv_u90.setText(list_u90.get(i));
            tv_a90.setText(list_a90.get(i));

            notifyDataSetChanged();

            return view;
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
        }
    }

    public void scrll(){
        scrollView.scrollTo(0,500);
    }
}
