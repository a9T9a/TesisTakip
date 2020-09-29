package pomex.garnet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class SilisSevkiyat extends AppCompatActivity {
    private Spinner boyut,firma;
    private EditText plaka,miktar;
    private AutoCompleteTextView yukleyen;
    private ListView listView;
    private DatePickerDialog.OnDateSetListener datepicker;
    private TimePickerDialog.OnTimeSetListener timepicker;
    private ArrayList<String> firmalar,boyutlar,elemanlar,list_firma,list_tarih,list_saat,list_paket,list_boyut;
    private ArrayList<Integer> list_miktar;
    private ArrayAdapter<String> firmaadp,boyutadap,elemanadp;
    private Calendar cal;
    private Date date;
    private DateFormat df;
    private TextView tarih,saat;
    private CheckBox cbs,cbb,cbt;
    private Button button;
    private Integer no;
    private String secilen_boyut,secilen_firma;
    private Boolean sorun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_silis_sevkiyat);
        getSupportActionBar().hide();

        listView=findViewById(R.id.lv_silissevk);
        boyut=findViewById(R.id.silis_sevk_spin_boyut);
        firma=findViewById(R.id.silis_sevk_spin_firma);
        plaka=findViewById(R.id.et_silissevk_plaka);
        yukleyen=findViewById(R.id.atv_silissevk_yukleyen);
        tarih=findViewById(R.id.tv_silissevk_tarih);
        saat=findViewById(R.id.tv_silissevk_saat);
        miktar=findViewById(R.id.et_silissevk_miktar);
        cbs=findViewById(R.id.cb_silissevk_silobas);
        cbb=findViewById(R.id.cb_silissevk_bigbag);
        cbt=findViewById(R.id.cb_silissevk_torba);
        button=findViewById(R.id.button_silissevk_yuklendi);

        ParseQuery<ParseObject> query=ParseQuery.getQuery("SilisSevkiyat");
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

        list_tarih=new ArrayList<String>();
        list_saat=new ArrayList<String>();
        list_firma=new ArrayList<String>();
        list_boyut=new ArrayList<String>();
        list_miktar=new ArrayList<Integer>();
        list_paket=new ArrayList<String>();

        firmalar=new ArrayList<>();
        firmalar.addAll(Arrays.asList("Firma Seç","Eile","Hidrogen","Kumlamacı","Diğer"));
        firmaadp=new ArrayAdapter<String>(SilisSevkiyat.this,R.layout.spinnerlayout,firmalar);
        firma.setAdapter(firmaadp);

        boyutlar=new ArrayList<>();
        boyutlar.addAll(Arrays.asList("Boyut Seç","-770 Mikron","500-2500 Mikron","500-1200 Mikron"));
        boyutadap=new ArrayAdapter<String>(SilisSevkiyat.this,R.layout.spinnerlayout,boyutlar);
        boyut.setAdapter(boyutadap);

        elemanlar=new ArrayList<>();
        elemanlar.addAll(Arrays.asList("Beytullah Burulday","Ramazan Biçer","Ergül Gülyüz","Abdil Doğru","Sami Ceyhan","Üzeyir Akcan","Uğur Yılmaz","Tufan Önder","Hakan Ünlü","Mesut Akman","İsmail Efe","Mehmet Atsan"));
        Collections.sort(elemanlar);
        elemanadp = new ArrayAdapter<String>(SilisSevkiyat.this, R.layout.spinnerlayout, elemanlar);
        yukleyen.setAdapter(elemanadp);

        ParseQuery<ParseObject> querylist=ParseQuery.getQuery("SilisSevkiyat");
        querylist.addDescendingOrder("No");
        querylist.setLimit(10);
        querylist.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e!=null){
                    e.printStackTrace();
                }else{
                    for (ParseObject object:objects){
                        Date date=object.getDate("Tarih");
                        String d=String.valueOf(date.getDate());
                        String m=String.valueOf(date.getMonth()+1);
                        String y=String.valueOf(date.getYear()+1900);
                        list_tarih.add(d+"/"+m+"/"+y);
                        list_saat.add(object.getString("Saat"));
                        list_firma.add(object.getString("Firma"));
                        list_boyut.add(object.getString("Boyut"));
                        list_miktar.add(object.getInt("Miktar"));
                        list_paket.add(object.getString("Paket"));
                    }
                }
                Customsevkiyatlistadp listadp=new Customsevkiyatlistadp();
                listView.setAdapter(listadp);
                listView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,list_firma.size()*200));
            }
        });

        tarih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cal=Calendar.getInstance();
                int year=cal.get(Calendar.YEAR);
                int month=cal.get(Calendar.MONTH);
                int day=cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog ddialog=new DatePickerDialog(SilisSevkiyat.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,datepicker,year,month,day);
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

                TimePickerDialog tdialog= new TimePickerDialog(SilisSevkiyat.this,android.R.style.Theme_Holo_Light_Dialog,timepicker,saaat,dakika,true);
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

        firma.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                secilen_firma=(String) firma.getItemAtPosition(i);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        boyut.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                secilen_boyut=(String) boyut.getItemAtPosition(i);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }

    public void aracyukle(View view){
        sorun=false;
        ParseObject object=new ParseObject("SilisSevkiyat");
        object.put("No",no);
        if (secilen_firma.equalsIgnoreCase("Firma Seç")){
            Toast.makeText(getApplicationContext(),"Firma Seç",Toast.LENGTH_SHORT).show();
            sorun=true;
        }else{object.put("Firma",secilen_firma);}

        if (secilen_boyut.equalsIgnoreCase("Boyut Seç")){
            Toast.makeText(getApplicationContext(),"Boyut Seç",Toast.LENGTH_SHORT).show();
            sorun=true;
        }else{object.put("Boyut",secilen_boyut);}

        if (plaka.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"Plaka Gir",Toast.LENGTH_SHORT).show();
            sorun=true;
        }else{object.put("Plaka",plaka.getText().toString());}

        if (yukleyen.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"Yükleyen Gir",Toast.LENGTH_SHORT).show();
            sorun=true;
        }else{object.put("Yukleyen",yukleyen.getText().toString());}

        if (miktar.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"Miktar Gir",Toast.LENGTH_SHORT).show();
            sorun=true;
        }else{object.put("Miktar",Integer.parseInt(miktar.getText().toString()));}

        if (date==null){
            Toast.makeText(getApplicationContext(),"Tarih Gir",Toast.LENGTH_SHORT).show();
            sorun=true;
        }else{object.put("Tarih",date);}

        if (saat.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(),"Saat Gir",Toast.LENGTH_SHORT).show();
            sorun=true;
        }else{object.put("Saat",saat.getText().toString());}

        object.put("User", ParseUser.getCurrentUser().getUsername());

        if (cbs.isChecked()){
            object.put("Paket","Silobas");
        }

        if (cbb.isChecked()){
            object.put("Paket","Bigbag");
            ParseQuery<ParseObject> query=ParseQuery.getQuery("Bigbag");
            query.getInBackground("rcTCWWH7Iz", new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {
                    if (e==null && sorun==false){
                        object.increment("Miktar",Integer.parseInt(miktar.getText().toString()));
                        object.saveInBackground();
                    }else{
                        e.printStackTrace();
                    }
                }
            });
        }

        if (cbt.isChecked()){
            object.put("Paket","Torba");
            ParseQuery<ParseObject> query=ParseQuery.getQuery("Poset");
            query.getInBackground("DNVEbplqsD", new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {
                    if (e==null && sorun==false){
                        object.increment("Miktar",Integer.parseInt(miktar.getText().toString()));
                        object.saveInBackground();
                    }else{
                        e.printStackTrace();
                    }
                }
            });
        }

        if (!cbs.isChecked() && !cbb.isChecked() && !cbt.isChecked()){
            Toast.makeText(getApplicationContext(),"Paket Seç",Toast.LENGTH_SHORT).show();
            sorun=true;
        }

        if (sorun==false){
            object.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e!=null){
                        Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(getApplicationContext(),"Kayıt Başarılı",Toast.LENGTH_LONG).show();
                        finish();
                    }
                }
            });
        }
    }

    class Customsevkiyatlistadp extends BaseAdapter {

        @Override
        public int getCount() {
            return list_firma.size();
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
        public View getView(final int i, View view, ViewGroup viewGroup) {
            view=getLayoutInflater().inflate(R.layout.silissevkiyat_list,null);

            TextView s_tarih=view.findViewById(R.id.tv_siliss_list_tarih);
            TextView s_saat=view.findViewById(R.id.tv_siliss_list_saat);
            TextView s_firma=view.findViewById(R.id.tv_siliss_list_firma);
            TextView s_boyut=view.findViewById(R.id.tv_siliss_list_boyut);
            TextView s_miktar=view.findViewById(R.id.tv_siliss_list_miktar);
            TextView s_paket=view.findViewById(R.id.tv_siliss_list_paket);

            s_tarih.setText(list_tarih.get(i));
            s_saat.setText(list_saat.get(i));
            s_firma.setText(list_firma.get(i));
            s_boyut.setText(list_boyut.get(i));
            s_miktar.setText(String.valueOf(list_miktar.get(i)));
            s_paket.setText(list_paket.get(i));

            return view;
        }
    }
}
