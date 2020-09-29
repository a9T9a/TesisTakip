package pomex.garnet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Space;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class MalGiris extends AppCompatActivity {
    private TextView tarih, olcubirim, miktarbirim;
    private Spinner malzeme;
    private EditText miktar, acklama, tutar, s;
    private Button yukle, goster;
    private AutoCompleteTextView olcu,firma;
    private ArrayList<String> malzemeler, olculer,firmalar,cfirmalar,cboyutlar,ctarih,cbirim,caciklama;
    private ArrayList<Integer> cmiktar,ctutar,no;
    private ArrayAdapter<String> malzemeadp, olcuadp,firmaadp,cfirmaadp;
    private ArrayAdapter<Integer> cmiktaradp,ctutaradp;
    private DatePickerDialog.OnDateSetListener datepicker;
    private Calendar cal;
    private Date date;
    private DateFormat df;
    private Boolean sorun,a;
    private LinearLayout linearLayout;
    private Space space1;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mal_giris);
        getSupportActionBar().hide();

        sorun = false;

        listView=findViewById(R.id.lv_mg_list);
        linearLayout=findViewById(R.id.linear_mg_olcu);
        space1=findViewById(R.id.space_mg_1);
        tarih = findViewById(R.id.tv_mg_tarih);
        olcubirim = findViewById(R.id.tv_mg_olcu_birim);
        miktarbirim = findViewById(R.id.tv_mg_miktar_birim);
        malzeme = findViewById(R.id.spinner_mg_malzeme);
        miktar = findViewById(R.id.et_mg_miktar);
        tutar=findViewById(R.id.et_mg_tutar);
        yukle = findViewById(R.id.button_mg_yukle);
        olcu = findViewById(R.id.atv_mg_olcu);
        firma=findViewById(R.id.atv_mg_firma);
        acklama = findViewById(R.id.et_mg_aciklama);
        s = findViewById(R.id.et_mg_ss);
        goster= findViewById(R.id.button_mg_goster);

        no=new ArrayList<>();
        caciklama=new ArrayList<>();
        cfirmalar=new ArrayList<>();
        cmiktar=new ArrayList<>();
        ctarih=new ArrayList<>();
        cboyutlar=new ArrayList<>();
        ctutar=new ArrayList<>();
        cbirim=new ArrayList<>();
        firmalar=new ArrayList<>();
        olculer = new ArrayList<>();
        malzemeler = new ArrayList<>();
        malzemeler.addAll(Arrays.asList("Malzeme Seç", "Bigbag", "Kömür", "Kraft", "LNG", "Palet", "Poşet"));
        malzemeadp = new ArrayAdapter<String>(MalGiris.this, R.layout.spinnerlayout, malzemeler);
        malzeme.setAdapter(malzemeadp);

        malzeme.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                listele();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        tarih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog ddialog = new DatePickerDialog(MalGiris.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, datepicker, year, month, day);
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

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (malzeme.getSelectedItem().toString().equalsIgnoreCase("Kömür")){
                    Intent intent =new Intent(MalGiris.this,KomurGoster.class);
                    intent.putExtra("No",no.get(i));
                    startActivity(intent);
                }
                return true;
            }
        });

        goster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (malzeme.getSelectedItem().toString().equalsIgnoreCase("Bigbag")) {
                    linearLayout.setVisibility(linearLayout.VISIBLE);
                    space1.setVisibility(space1.VISIBLE);
                    olcubirim.setText("cm");
                    miktarbirim.setText("Adet");

                    ParseQuery<ParseObject>qqq=ParseQuery.getQuery("Bigbag");
                    qqq.whereEqualTo("Hareket","Geldi");
                    qqq.orderByDescending("Tarih");
                    qqq.setSkip(cfirmalar.size());
                    qqq.setLimit(Integer.valueOf(s.getText().toString())-cfirmalar.size());
                    qqq.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            if (e!=null){
                                e.printStackTrace();
                            }else{
                                for (ParseObject object:objects){
                                    cfirmalar.add(object.getString("Firma"));
                                    cboyutlar.add(object.getString("Olcu"));
                                    cmiktar.add(object.getInt("Miktar"));
                                    ctutar.add(object.getInt("Tutar"));
                                    ctarih.add(String.valueOf(object.getDate("Tarih").getDate())+"/"+String.valueOf(object.getDate("Tarih").getMonth()+1)+"/"+String.valueOf(object.getDate("Tarih").getYear()+1900));
                                    Double a=Double.valueOf(object.getInt("Tutar"));
                                    Double b=Double.valueOf(object.getInt("Miktar"));
                                    cbirim.add(String.format("%.2f",a/b)+" TL/adet");
                                    caciklama.add(object.getString("Aciklama"));
                                }
                            }
                            malzemelist adp=new malzemelist();
                            listView.setAdapter(adp);
                            listView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,cfirmalar.size()*215));
                        }
                    });

                    ParseQuery<ParseObject> query = ParseQuery.getQuery("Bigbag");
                    query.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            if (e != null) {
                                e.printStackTrace();
                            } else {
                                for (ParseObject object : objects) {
                                    olculer.add(object.getString("Olcu"));
                                    if (object.getString("Firma")!=null){
                                        firmalar.add(object.getString("Firma"));
                                    }
                                }
                            }
                            HashSet<String> hs = new HashSet<String>();
                            hs.addAll(olculer);
                            olculer.clear();
                            olculer.addAll(hs);
                            Collections.sort(olculer);

                            olcuadp = new ArrayAdapter<String>(MalGiris.this, R.layout.spinnerlayout, olculer);
                            olcu.setAdapter(olcuadp);

                            HashSet<String> hs2 = new HashSet<String>();
                            hs2.addAll(firmalar);
                            firmalar.clear();
                            firmalar.addAll(hs2);
                            Collections.sort(firmalar);

                            firmaadp = new ArrayAdapter<String>(MalGiris.this, R.layout.spinnerlayout, firmalar);
                            firma.setAdapter(firmaadp);
                        }
                    });
                } else if (malzeme.getSelectedItem().toString().equalsIgnoreCase("Kömür")) {
                    linearLayout.setVisibility(linearLayout.VISIBLE);
                    space1.setVisibility(space1.VISIBLE);
                    olcubirim.setText("mm");
                    miktarbirim.setText("kg");

                    ParseQuery<ParseObject>qqq=ParseQuery.getQuery("Komur");
                    qqq.whereEqualTo("Hareket","Geldi");
                    qqq.orderByDescending("Tarih");
                    qqq.setSkip(cfirmalar.size());
                    qqq.setLimit(Integer.valueOf(s.getText().toString())-cfirmalar.size());
                    qqq.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            if (e!=null){
                                e.printStackTrace();
                            }else{
                                for (ParseObject object:objects){
                                    no.add(object.getInt("No"));
                                    cfirmalar.add(object.getString("Firma"));
                                    cboyutlar.add(object.getString("Olcu"));
                                    cmiktar.add(object.getInt("Miktar"));
                                    ctutar.add(object.getInt("Tutar"));
                                    ctarih.add(String.valueOf(object.getDate("Tarih").getDate())+"/"+String.valueOf(object.getDate("Tarih").getMonth()+1)+"/"+String.valueOf(object.getDate("Tarih").getYear()+1900));
                                    Double t=Double.valueOf(object.getInt("Tutar"));
                                    Double m=Double.valueOf(object.getInt("Miktar"));
                                    cbirim.add(String.format("%.0f",(t/m)*1000)+" TL/ton");
                                    caciklama.add(object.getString("Aciklama"));
                                }
                            }
                            malzemelist adp=new malzemelist();
                            listView.setAdapter(adp);
                            listView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,cfirmalar.size()*215));
                        }
                    });

                    ParseQuery<ParseObject> query = ParseQuery.getQuery("Komur");
                    query.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            if (e != null) {
                                e.printStackTrace();
                            } else {
                                for (ParseObject object : objects) {
                                    olculer.add(object.getString("Olcu"));
                                    if (object.getString("Firma")!=null){
                                        firmalar.add(object.getString("Firma"));
                                    }
                                }
                            }
                            HashSet<String> hs = new HashSet<String>();
                            hs.addAll(olculer);
                            olculer.clear();
                            olculer.addAll(hs);
                            Collections.sort(olculer);

                            olcuadp = new ArrayAdapter<String>(MalGiris.this, R.layout.spinnerlayout, olculer);
                            olcu.setAdapter(olcuadp);

                            HashSet<String> hs2 = new HashSet<String>();
                            hs2.addAll(firmalar);
                            firmalar.clear();
                            firmalar.addAll(hs2);
                            Collections.sort(firmalar);

                            firmaadp = new ArrayAdapter<String>(MalGiris.this, R.layout.spinnerlayout, firmalar);
                            firma.setAdapter(firmaadp);
                        }
                    });
                } else if (malzeme.getSelectedItem().toString().equalsIgnoreCase("Kraft")) {
                    linearLayout.setVisibility(linearLayout.VISIBLE);
                    space1.setVisibility(space1.VISIBLE);
                    olcubirim.setText("cm");
                    miktarbirim.setText("Adet");

                    ParseQuery<ParseObject>qqq=ParseQuery.getQuery("Kraft");
                    qqq.whereEqualTo("Hareket","Geldi");
                    qqq.orderByDescending("Tarih");
                    qqq.setSkip(cfirmalar.size());
                    qqq.setLimit(Integer.valueOf(s.getText().toString())-cfirmalar.size());
                    qqq.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            if (e!=null){
                                e.printStackTrace();
                            }else{
                                for (ParseObject object:objects){
                                    cfirmalar.add(object.getString("Firma"));
                                    cboyutlar.add(object.getString("Olcu"));
                                    cmiktar.add(object.getInt("Miktar"));
                                    ctutar.add(object.getInt("Tutar"));
                                    ctarih.add(String.valueOf(object.getDate("Tarih").getDate())+"/"+String.valueOf(object.getDate("Tarih").getMonth()+1)+"/"+String.valueOf(object.getDate("Tarih").getYear()+1900));
                                    Double a=Double.valueOf(object.getInt("Tutar"));
                                    Double b=Double.valueOf(object.getInt("Miktar"));
                                    cbirim.add(String.format("%.2f",a/b)+" TL/adet");
                                    caciklama.add(object.getString("Aciklama"));
                                }
                            }
                            malzemelist adp=new malzemelist();
                            listView.setAdapter(adp);
                            listView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,cfirmalar.size()*215));
                        }
                    });

                    ParseQuery<ParseObject> query = ParseQuery.getQuery("Kraft");
                    query.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            if (e != null) {
                                e.printStackTrace();
                            } else {
                                for (ParseObject object : objects) {
                                    olculer.add(object.getString("Olcu"));
                                    if (object.getString("Firma")!=null){
                                        firmalar.add(object.getString("Firma"));
                                    }
                                }
                            }
                            HashSet<String> hs = new HashSet<String>();
                            hs.addAll(olculer);
                            olculer.clear();
                            olculer.addAll(hs);
                            Collections.sort(olculer);

                            olcuadp = new ArrayAdapter<String>(MalGiris.this, R.layout.spinnerlayout, olculer);
                            olcu.setAdapter(olcuadp);

                            HashSet<String> hs2 = new HashSet<String>();
                            hs2.addAll(firmalar);
                            firmalar.clear();
                            firmalar.addAll(hs2);
                            Collections.sort(firmalar);

                            firmaadp = new ArrayAdapter<String>(MalGiris.this, R.layout.spinnerlayout, firmalar);
                            firma.setAdapter(firmaadp);
                        }
                    });
                } else if (malzeme.getSelectedItem().toString().equalsIgnoreCase("Palet")) {
                    linearLayout.setVisibility(linearLayout.VISIBLE);
                    space1.setVisibility(space1.VISIBLE);
                    olcubirim.setText("cm");
                    miktarbirim.setText("Adet");

                    ParseQuery<ParseObject>qqq=ParseQuery.getQuery("Palet");
                    qqq.whereEqualTo("Hareket","Geldi");
                    qqq.orderByDescending("Tarih");
                    qqq.setSkip(cfirmalar.size());
                    qqq.setLimit(Integer.valueOf(s.getText().toString())-cfirmalar.size());
                    qqq.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            if (e!=null){
                                e.printStackTrace();
                            }else{
                                for (ParseObject object:objects){
                                    cfirmalar.add(object.getString("Firma"));
                                    cboyutlar.add(object.getString("Olcu"));
                                    cmiktar.add(object.getInt("Miktar"));
                                    ctutar.add(object.getInt("Tutar"));
                                    ctarih.add(String.valueOf(object.getDate("Tarih").getDate())+"/"+String.valueOf(object.getDate("Tarih").getMonth()+1)+"/"+String.valueOf(object.getDate("Tarih").getYear()+1900));
                                    Double a=Double.valueOf(object.getInt("Tutar"));
                                    Double b=Double.valueOf(object.getInt("Miktar"));
                                    cbirim.add(String.format("%.2f",a/b)+" TL/adet");
                                    caciklama.add(object.getString("Aciklama"));
                                }
                            }
                            malzemelist adp=new malzemelist();
                            listView.setAdapter(adp);
                            listView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,cfirmalar.size()*215));
                        }
                    });

                    ParseQuery<ParseObject> query = ParseQuery.getQuery("Palet");
                    query.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            if (e != null) {
                                e.printStackTrace();
                            } else {
                                for (ParseObject object : objects) {
                                    olculer.add(object.getString("Olcu"));
                                    if (object.getString("Firma")!=null){
                                        firmalar.add(object.getString("Firma"));
                                    }
                                }
                            }
                            HashSet<String> hs = new HashSet<String>();
                            hs.addAll(olculer);
                            olculer.clear();
                            olculer.addAll(hs);
                            Collections.sort(olculer);

                            olcuadp = new ArrayAdapter<String>(MalGiris.this, R.layout.spinnerlayout, olculer);
                            olcu.setAdapter(olcuadp);

                            HashSet<String> hs2 = new HashSet<String>();
                            hs2.addAll(firmalar);
                            firmalar.clear();
                            firmalar.addAll(hs2);
                            Collections.sort(firmalar);

                            firmaadp = new ArrayAdapter<String>(MalGiris.this, R.layout.spinnerlayout, firmalar);
                            firma.setAdapter(firmaadp);
                        }
                    });
                } else if (malzeme.getSelectedItem().toString().equalsIgnoreCase("Poşet")) {
                    linearLayout.setVisibility(linearLayout.VISIBLE);
                    space1.setVisibility(space1.VISIBLE);
                    olcubirim.setText("cm");
                    miktarbirim.setText("Adet");

                    ParseQuery<ParseObject>qqq=ParseQuery.getQuery("Poset");
                    qqq.whereEqualTo("Hareket","Geldi");
                    qqq.orderByDescending("Tarih");
                    qqq.setSkip(cfirmalar.size());
                    qqq.setLimit(Integer.valueOf(s.getText().toString())-cfirmalar.size());
                    qqq.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            if (e!=null){
                                e.printStackTrace();
                            }else{
                                for (ParseObject object:objects){
                                    cfirmalar.add(object.getString("Firma"));
                                    cboyutlar.add(object.getString("Olcu"));
                                    cmiktar.add(object.getInt("Miktar"));
                                    ctutar.add(object.getInt("Tutar"));
                                    ctarih.add(String.valueOf(object.getDate("Tarih").getDate())+"/"+String.valueOf(object.getDate("Tarih").getMonth()+1)+"/"+String.valueOf(object.getDate("Tarih").getYear()+1900));
                                    Double a=Double.valueOf(object.getInt("Tutar"));
                                    Double b=Double.valueOf(object.getInt("Miktar"));
                                    cbirim.add(String.format("%.2f",a/b)+" TL/adet");
                                    caciklama.add(object.getString("Aciklama"));
                                }
                            }
                            malzemelist adp=new malzemelist();
                            listView.setAdapter(adp);
                            listView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,cfirmalar.size()*215));
                        }
                    });

                    ParseQuery<ParseObject> query = ParseQuery.getQuery("Poset");
                    query.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            if (e != null) {
                                e.printStackTrace();
                            } else {
                                for (ParseObject object : objects) {
                                    olculer.add(object.getString("Olcu"));
                                    if (object.getString("Firma")!=null){
                                        firmalar.add(object.getString("Firma"));
                                    }
                                }
                            }
                            HashSet<String> hs = new HashSet<String>();
                            hs.addAll(olculer);
                            olculer.clear();
                            olculer.addAll(hs);
                            Collections.sort(olculer);

                            olcuadp = new ArrayAdapter<String>(MalGiris.this, R.layout.spinnerlayout, olculer);
                            olcu.setAdapter(olcuadp);

                            HashSet<String> hs2 = new HashSet<String>();
                            hs2.addAll(firmalar);
                            firmalar.clear();
                            firmalar.addAll(hs2);
                            Collections.sort(firmalar);

                            firmaadp = new ArrayAdapter<String>(MalGiris.this, R.layout.spinnerlayout, firmalar);
                            firma.setAdapter(firmaadp);
                        }
                    });
                } else if (malzeme.getSelectedItem().toString().equalsIgnoreCase("LNG")) {
                    linearLayout.setVisibility(linearLayout.INVISIBLE);
                    space1.setVisibility(space1.INVISIBLE);
                    miktarbirim.setText("kg");

                    ParseQuery<ParseObject>qqq=ParseQuery.getQuery("LNG");
                    qqq.whereEqualTo("Hareket","Geldi");
                    qqq.orderByDescending("Tarih");
                    qqq.setSkip(cfirmalar.size());
                    qqq.setLimit(Integer.valueOf(s.getText().toString())-cfirmalar.size());
                    qqq.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            if (e!=null){
                                e.printStackTrace();
                            }else{
                                for (ParseObject object:objects){
                                    cfirmalar.add(object.getString("Firma"));
                                    cboyutlar.add(object.getString("Olcu"));
                                    cmiktar.add(object.getInt("Miktar"));
                                    ctutar.add(object.getInt("Tutar"));
                                    ctarih.add(String.valueOf(object.getDate("Tarih").getDate())+"/"+String.valueOf(object.getDate("Tarih").getMonth()+1)+"/"+String.valueOf(object.getDate("Tarih").getYear()+1900));
                                    Double a=Double.valueOf(object.getInt("Tutar"));
                                    Double b=Double.valueOf(object.getInt("Miktar"));
                                    cbirim.add(String.format("%.2f",a/b)+" TL/kg");
                                    caciklama.add(object.getString("Aciklama"));
                                }
                            }
                            malzemelist adp=new malzemelist();
                            listView.setAdapter(adp);
                            listView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,cfirmalar.size()*215));
                        }
                    });

                    ParseQuery<ParseObject> query = ParseQuery.getQuery("LNG");
                    query.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            if (e != null) {
                                e.printStackTrace();
                            } else {
                                for (ParseObject object : objects) {
                                    if (object.getString("Firma")!=null){
                                        firmalar.add(object.getString("Firma"));
                                    }
                                }
                            }

                            HashSet<String> hs2 = new HashSet<String>();
                            hs2.addAll(firmalar);
                            firmalar.clear();
                            firmalar.addAll(hs2);
                            Collections.sort(firmalar);

                            firmaadp = new ArrayAdapter<String>(MalGiris.this, R.layout.spinnerlayout, firmalar);
                            firma.setAdapter(firmaadp);
                        }
                    });
                }
            }
        });

    }

    public void listele(){
        cfirmalar.clear();
        cmiktar.clear();
        ctarih.clear();
        cboyutlar.clear();
        ctutar.clear();
        cbirim.clear();
        olculer.clear();
        firmalar.clear();
        caciklama.clear();

        if (malzeme.getSelectedItem().toString().equalsIgnoreCase("Bigbag")) {
            linearLayout.setVisibility(linearLayout.VISIBLE);
            space1.setVisibility(space1.VISIBLE);
            olcubirim.setText("cm");
            miktarbirim.setText("Adet");

            ParseQuery<ParseObject>qqq=ParseQuery.getQuery("Bigbag");
            qqq.whereEqualTo("Hareket","Geldi");
            qqq.orderByDescending("Tarih");
            qqq.setLimit(Integer.valueOf(s.getText().toString()));
            qqq.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e!=null){
                        e.printStackTrace();
                    }else{
                        for (ParseObject object:objects){
                            cfirmalar.add(object.getString("Firma"));
                            cboyutlar.add(object.getString("Olcu"));
                            cmiktar.add(object.getInt("Miktar"));
                            ctutar.add(object.getInt("Tutar"));
                            ctarih.add(String.valueOf(object.getDate("Tarih").getDate())+"/"+String.valueOf(object.getDate("Tarih").getMonth()+1)+"/"+String.valueOf(object.getDate("Tarih").getYear()+1900));
                            Double a=Double.valueOf(object.getInt("Tutar"));
                            Double b=Double.valueOf(object.getInt("Miktar"));
                            cbirim.add(String.format("%.2f",a/b)+" TL/adet");
                            caciklama.add(object.getString("Aciklama"));
                        }
                    }
                    malzemelist adp=new malzemelist();
                    listView.setAdapter(adp);
                    listView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,cfirmalar.size()*215));
                }
            });

            ParseQuery<ParseObject> query = ParseQuery.getQuery("Bigbag");
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e != null) {
                        e.printStackTrace();
                    } else {
                        for (ParseObject object : objects) {
                            olculer.add(object.getString("Olcu"));
                            if (object.getString("Firma")!=null){
                                firmalar.add(object.getString("Firma"));
                            }
                        }
                    }
                    HashSet<String> hs = new HashSet<String>();
                    hs.addAll(olculer);
                    olculer.clear();
                    olculer.addAll(hs);
                    Collections.sort(olculer);

                    olcuadp = new ArrayAdapter<String>(MalGiris.this, R.layout.spinnerlayout, olculer);
                    olcu.setAdapter(olcuadp);

                    HashSet<String> hs2 = new HashSet<String>();
                    hs2.addAll(firmalar);
                    firmalar.clear();
                    firmalar.addAll(hs2);
                    Collections.sort(firmalar);

                    firmaadp = new ArrayAdapter<String>(MalGiris.this, R.layout.spinnerlayout, firmalar);
                    firma.setAdapter(firmaadp);
                }
            });
        } else if (malzeme.getSelectedItem().toString().equalsIgnoreCase("Kömür")) {
            linearLayout.setVisibility(linearLayout.VISIBLE);
            space1.setVisibility(space1.VISIBLE);
            olcubirim.setText("mm");
            miktarbirim.setText("kg");

            ParseQuery<ParseObject>qqq=ParseQuery.getQuery("Komur");
            qqq.whereEqualTo("Hareket","Geldi");
            qqq.orderByDescending("Tarih");
            qqq.setLimit(Integer.valueOf(s.getText().toString()));
            qqq.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e!=null){
                        e.printStackTrace();
                    }else{
                        for (ParseObject object:objects){
                            no.add(object.getInt("No"));
                            cfirmalar.add(object.getString("Firma"));
                            cboyutlar.add(object.getString("Olcu"));
                            cmiktar.add(object.getInt("Miktar"));
                            ctutar.add(object.getInt("Tutar"));
                            ctarih.add(String.valueOf(object.getDate("Tarih").getDate())+"/"+String.valueOf(object.getDate("Tarih").getMonth()+1)+"/"+String.valueOf(object.getDate("Tarih").getYear()+1900));
                            Double t=Double.valueOf(object.getInt("Tutar"));
                            Double m=Double.valueOf(object.getInt("Miktar"));
                            cbirim.add(String.format("%.0f",(t/m)*1000)+" TL/ton");
                            caciklama.add(object.getString("Aciklama"));
                        }
                    }
                    malzemelist adp=new malzemelist();
                    listView.setAdapter(adp);
                    listView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,cfirmalar.size()*215));
                }
            });

            ParseQuery<ParseObject> query = ParseQuery.getQuery("Komur");
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e != null) {
                        e.printStackTrace();
                    } else {
                        for (ParseObject object : objects) {
                            olculer.add(object.getString("Olcu"));
                            if (object.getString("Firma")!=null){
                                firmalar.add(object.getString("Firma"));
                            }
                        }
                    }
                    HashSet<String> hs = new HashSet<String>();
                    hs.addAll(olculer);
                    olculer.clear();
                    olculer.addAll(hs);
                    Collections.sort(olculer);

                    olcuadp = new ArrayAdapter<String>(MalGiris.this, R.layout.spinnerlayout, olculer);
                    olcu.setAdapter(olcuadp);

                    HashSet<String> hs2 = new HashSet<String>();
                    hs2.addAll(firmalar);
                    firmalar.clear();
                    firmalar.addAll(hs2);
                    Collections.sort(firmalar);

                    firmaadp = new ArrayAdapter<String>(MalGiris.this, R.layout.spinnerlayout, firmalar);
                    firma.setAdapter(firmaadp);
                }
            });
        } else if (malzeme.getSelectedItem().toString().equalsIgnoreCase("Kraft")) {
            linearLayout.setVisibility(linearLayout.VISIBLE);
            space1.setVisibility(space1.VISIBLE);
            olcubirim.setText("cm");
            miktarbirim.setText("Adet");

            ParseQuery<ParseObject>qqq=ParseQuery.getQuery("Kraft");
            qqq.whereEqualTo("Hareket","Geldi");
            qqq.orderByDescending("Tarih");
            qqq.setLimit(Integer.valueOf(s.getText().toString()));
            qqq.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e!=null){
                        e.printStackTrace();
                    }else{
                        for (ParseObject object:objects){
                            cfirmalar.add(object.getString("Firma"));
                            cboyutlar.add(object.getString("Olcu"));
                            cmiktar.add(object.getInt("Miktar"));
                            ctutar.add(object.getInt("Tutar"));
                            ctarih.add(String.valueOf(object.getDate("Tarih").getDate())+"/"+String.valueOf(object.getDate("Tarih").getMonth()+1)+"/"+String.valueOf(object.getDate("Tarih").getYear()+1900));
                            Double a=Double.valueOf(object.getInt("Tutar"));
                            Double b=Double.valueOf(object.getInt("Miktar"));
                            cbirim.add(String.format("%.2f",a/b)+" TL/adet");
                            caciklama.add(object.getString("Aciklama"));
                        }
                    }
                    malzemelist adp=new malzemelist();
                    listView.setAdapter(adp);
                    listView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,cfirmalar.size()*215));
                }
            });

            ParseQuery<ParseObject> query = ParseQuery.getQuery("Kraft");
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e != null) {
                        e.printStackTrace();
                    } else {
                        for (ParseObject object : objects) {
                            olculer.add(object.getString("Olcu"));
                            if (object.getString("Firma")!=null){
                                firmalar.add(object.getString("Firma"));
                            }
                        }
                    }
                    HashSet<String> hs = new HashSet<String>();
                    hs.addAll(olculer);
                    olculer.clear();
                    olculer.addAll(hs);
                    Collections.sort(olculer);

                    olcuadp = new ArrayAdapter<String>(MalGiris.this, R.layout.spinnerlayout, olculer);
                    olcu.setAdapter(olcuadp);

                    HashSet<String> hs2 = new HashSet<String>();
                    hs2.addAll(firmalar);
                    firmalar.clear();
                    firmalar.addAll(hs2);
                    Collections.sort(firmalar);

                    firmaadp = new ArrayAdapter<String>(MalGiris.this, R.layout.spinnerlayout, firmalar);
                    firma.setAdapter(firmaadp);
                }
            });
        } else if (malzeme.getSelectedItem().toString().equalsIgnoreCase("Palet")) {
            linearLayout.setVisibility(linearLayout.VISIBLE);
            space1.setVisibility(space1.VISIBLE);
            olcubirim.setText("cm");
            miktarbirim.setText("Adet");

            ParseQuery<ParseObject>qqq=ParseQuery.getQuery("Palet");
            qqq.whereEqualTo("Hareket","Geldi");
            qqq.orderByDescending("Tarih");
            qqq.setLimit(Integer.valueOf(s.getText().toString()));
            qqq.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e!=null){
                        e.printStackTrace();
                    }else{
                        for (ParseObject object:objects){
                            cfirmalar.add(object.getString("Firma"));
                            cboyutlar.add(object.getString("Olcu"));
                            cmiktar.add(object.getInt("Miktar"));
                            ctutar.add(object.getInt("Tutar"));
                            ctarih.add(String.valueOf(object.getDate("Tarih").getDate())+"/"+String.valueOf(object.getDate("Tarih").getMonth()+1)+"/"+String.valueOf(object.getDate("Tarih").getYear()+1900));
                            Double a=Double.valueOf(object.getInt("Tutar"));
                            Double b=Double.valueOf(object.getInt("Miktar"));
                            cbirim.add(String.format("%.2f",a/b)+" TL/adet");
                            caciklama.add(object.getString("Aciklama"));
                        }
                    }
                    malzemelist adp=new malzemelist();
                    listView.setAdapter(adp);
                    listView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,cfirmalar.size()*215));
                }
            });

            ParseQuery<ParseObject> query = ParseQuery.getQuery("Palet");
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e != null) {
                        e.printStackTrace();
                    } else {
                        for (ParseObject object : objects) {
                            olculer.add(object.getString("Olcu"));
                            if (object.getString("Firma")!=null){
                                firmalar.add(object.getString("Firma"));
                            }
                        }
                    }
                    HashSet<String> hs = new HashSet<String>();
                    hs.addAll(olculer);
                    olculer.clear();
                    olculer.addAll(hs);
                    Collections.sort(olculer);

                    olcuadp = new ArrayAdapter<String>(MalGiris.this, R.layout.spinnerlayout, olculer);
                    olcu.setAdapter(olcuadp);

                    HashSet<String> hs2 = new HashSet<String>();
                    hs2.addAll(firmalar);
                    firmalar.clear();
                    firmalar.addAll(hs2);
                    Collections.sort(firmalar);

                    firmaadp = new ArrayAdapter<String>(MalGiris.this, R.layout.spinnerlayout, firmalar);
                    firma.setAdapter(firmaadp);
                }
            });
        } else if (malzeme.getSelectedItem().toString().equalsIgnoreCase("Poşet")) {
            linearLayout.setVisibility(linearLayout.VISIBLE);
            space1.setVisibility(space1.VISIBLE);
            olcubirim.setText("cm");
            miktarbirim.setText("Adet");

            ParseQuery<ParseObject>qqq=ParseQuery.getQuery("Poset");
            qqq.whereEqualTo("Hareket","Geldi");
            qqq.orderByDescending("Tarih");
            qqq.setLimit(Integer.valueOf(s.getText().toString()));
            qqq.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e!=null){
                        e.printStackTrace();
                    }else{
                        for (ParseObject object:objects){
                            cfirmalar.add(object.getString("Firma"));
                            cboyutlar.add(object.getString("Olcu"));
                            cmiktar.add(object.getInt("Miktar"));
                            ctutar.add(object.getInt("Tutar"));
                            ctarih.add(String.valueOf(object.getDate("Tarih").getDate())+"/"+String.valueOf(object.getDate("Tarih").getMonth()+1)+"/"+String.valueOf(object.getDate("Tarih").getYear()+1900));
                            Double a=Double.valueOf(object.getInt("Tutar"));
                            Double b=Double.valueOf(object.getInt("Miktar"));
                            cbirim.add(String.format("%.2f",a/b)+" TL/adet");
                            caciklama.add(object.getString("Aciklama"));
                        }
                    }
                    malzemelist adp=new malzemelist();
                    listView.setAdapter(adp);
                    listView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,cfirmalar.size()*215));
                }
            });

            ParseQuery<ParseObject> query = ParseQuery.getQuery("Poset");
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e != null) {
                        e.printStackTrace();
                    } else {
                        for (ParseObject object : objects) {
                            olculer.add(object.getString("Olcu"));
                            if (object.getString("Firma")!=null){
                                firmalar.add(object.getString("Firma"));
                            }
                        }
                    }
                    HashSet<String> hs = new HashSet<String>();
                    hs.addAll(olculer);
                    olculer.clear();
                    olculer.addAll(hs);
                    Collections.sort(olculer);

                    olcuadp = new ArrayAdapter<String>(MalGiris.this, R.layout.spinnerlayout, olculer);
                    olcu.setAdapter(olcuadp);

                    HashSet<String> hs2 = new HashSet<String>();
                    hs2.addAll(firmalar);
                    firmalar.clear();
                    firmalar.addAll(hs2);
                    Collections.sort(firmalar);

                    firmaadp = new ArrayAdapter<String>(MalGiris.this, R.layout.spinnerlayout, firmalar);
                    firma.setAdapter(firmaadp);
                }
            });
        } else if (malzeme.getSelectedItem().toString().equalsIgnoreCase("LNG")) {
            linearLayout.setVisibility(linearLayout.INVISIBLE);
            space1.setVisibility(space1.INVISIBLE);
            miktarbirim.setText("kg");

            ParseQuery<ParseObject>qqq=ParseQuery.getQuery("LNG");
            qqq.whereEqualTo("Hareket","Geldi");
            qqq.orderByDescending("Tarih");
            qqq.setLimit(Integer.valueOf(s.getText().toString()));
            qqq.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e!=null){
                        e.printStackTrace();
                    }else{
                        for (ParseObject object:objects){
                            cfirmalar.add(object.getString("Firma"));
                            cboyutlar.add(object.getString("Olcu"));
                            cmiktar.add(object.getInt("Miktar"));
                            ctutar.add(object.getInt("Tutar"));
                            ctarih.add(String.valueOf(object.getDate("Tarih").getDate())+"/"+String.valueOf(object.getDate("Tarih").getMonth()+1)+"/"+String.valueOf(object.getDate("Tarih").getYear()+1900));
                            Double a=Double.valueOf(object.getInt("Tutar"));
                            Double b=Double.valueOf(object.getInt("Miktar"));
                            cbirim.add(String.format("%.2f",a/b)+" TL/kg");
                            caciklama.add(object.getString("Aciklama"));
                        }
                    }
                    malzemelist adp=new malzemelist();
                    listView.setAdapter(adp);
                    listView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,cfirmalar.size()*215));
                }
            });

            ParseQuery<ParseObject> query = ParseQuery.getQuery("LNG");
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e != null) {
                        e.printStackTrace();
                    } else {
                        for (ParseObject object : objects) {
                            if (object.getString("Firma")!=null){
                                firmalar.add(object.getString("Firma"));
                            }
                        }
                    }

                    HashSet<String> hs2 = new HashSet<String>();
                    hs2.addAll(firmalar);
                    firmalar.clear();
                    firmalar.addAll(hs2);
                    Collections.sort(firmalar);

                    firmaadp = new ArrayAdapter<String>(MalGiris.this, R.layout.spinnerlayout, firmalar);
                    firma.setAdapter(firmaadp);
                }
            });
        }
    }

    public void mg_yukle(View view) {
        sorun=false;
        a=false;
        if (malzeme.getSelectedItem().toString().equalsIgnoreCase("Bigbag")) {
            ParseObject object = new ParseObject("Bigbag");

            if (olcu.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Ölçü Gir", Toast.LENGTH_SHORT).show();
                sorun = true;
            } else {
                object.put("Olcu", olcu.getText().toString());
            }

            if (firma.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(), "Firma Gir", Toast.LENGTH_SHORT).show();
                sorun = true;
            } else {
                object.put("Firma", firma.getText().toString());
            }

            if (miktar.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Miktar Gir", Toast.LENGTH_SHORT).show();
                sorun = true;
            } else {
                object.put("Miktar", Integer.parseInt(miktar.getText().toString()));
            }

            if (tutar.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(),"Tutar Gir",Toast.LENGTH_SHORT).show();
                sorun=true;
            }else{object.put("Tutar",Integer.parseInt(tutar.getText().toString()));}

            if (date == null) {
                Toast.makeText(getApplicationContext(), "Tarih Gir", Toast.LENGTH_SHORT).show();
                sorun = true;
            } else {
                object.put("Tarih", date);
            }

            if (!acklama.getText().toString().isEmpty()) {
                object.put("Aciklama", acklama.getText().toString());
            }

            object.put("Hareket", "Geldi");

            object.put("User", ParseUser.getCurrentUser().getUsername());

            if (sorun == false) {
                object.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e != null) {
                            e.printStackTrace();
                        } else {
                            Toast.makeText(getApplicationContext(), "Yükleme Başarılı", Toast.LENGTH_SHORT).show();
                            a=true;
                        }
                    }
                });
            }
        } else if (malzeme.getSelectedItem().toString().equalsIgnoreCase("Kömür")) {
            ParseObject object = new ParseObject("Komur");

            object.put("No",no.get(0)+1);

            if (olcu.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Ölçü Gir", Toast.LENGTH_SHORT).show();
                sorun = true;
            } else {
                object.put("Olcu", olcu.getText().toString());
            }

            if (firma.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(), "Firma Gir", Toast.LENGTH_SHORT).show();
                sorun = true;
            } else {
                object.put("Firma", firma.getText().toString());
            }

            if (miktar.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Miktar Gir", Toast.LENGTH_SHORT).show();
                sorun = true;
            } else {
                object.put("Miktar", Integer.parseInt(miktar.getText().toString()));
            }

            if (tutar.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(),"Tutar Gir",Toast.LENGTH_SHORT).show();
                sorun=true;
            }else{object.put("Tutar",Integer.parseInt(tutar.getText().toString()));}

            if (date == null) {
                Toast.makeText(getApplicationContext(), "Tarih Gir", Toast.LENGTH_SHORT).show();
                sorun = true;
            } else {
                object.put("Tarih", date);
            }

            if (!acklama.getText().toString().isEmpty()) {
                object.put("Aciklama", acklama.getText().toString());
            }

            object.put("Hareket", "Geldi");

            object.put("User", ParseUser.getCurrentUser().getUsername());

            if (sorun == false) {
                object.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e != null) {
                            e.printStackTrace();
                        } else {
                            Toast.makeText(getApplicationContext(), "Yükleme Başarılı", Toast.LENGTH_SHORT).show();
                            a=true;
                        }
                    }
                });
            }
        } else if (malzeme.getSelectedItem().toString().equalsIgnoreCase("Kraft")) {
            ParseObject object = new ParseObject("Kraft");

            if (olcu.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Ölçü Gir", Toast.LENGTH_SHORT).show();
                sorun = true;
            } else {
                object.put("Olcu", olcu.getText().toString());
            }

            if (firma.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(), "Firma Gir", Toast.LENGTH_SHORT).show();
                sorun = true;
            } else {
                object.put("Firma", firma.getText().toString());
            }

            if (miktar.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Miktar Gir", Toast.LENGTH_SHORT).show();
                sorun = true;
            } else {
                object.put("Miktar", Integer.parseInt(miktar.getText().toString()));
            }

            if (tutar.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(),"Tutar Gir",Toast.LENGTH_SHORT).show();
                sorun=true;
            }else{object.put("Tutar",Integer.parseInt(tutar.getText().toString()));}

            if (date == null) {
                Toast.makeText(getApplicationContext(), "Tarih Gir", Toast.LENGTH_SHORT).show();
                sorun = true;
            } else {
                object.put("Tarih", date);
            }

            if (!acklama.getText().toString().isEmpty()) {
                object.put("Aciklama", acklama.getText().toString());
            }

            object.put("Hareket", "Geldi");

            object.put("User", ParseUser.getCurrentUser().getUsername());

            if (sorun == false) {
                object.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e != null) {
                            e.printStackTrace();
                        } else {
                            Toast.makeText(getApplicationContext(), "Yükleme Başarılı", Toast.LENGTH_SHORT).show();
                            a=true;
                        }
                    }
                });
            }
        } else if (malzeme.getSelectedItem().toString().equalsIgnoreCase("LNG")) {
            ParseObject object = new ParseObject("LNG");

            if (firma.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(), "Firma Gir", Toast.LENGTH_SHORT).show();
                sorun = true;
            } else {
                object.put("Firma", firma.getText().toString());
            }

            if (miktar.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Miktar Gir", Toast.LENGTH_SHORT).show();
                sorun = true;
            } else {
                object.put("Miktar", Integer.parseInt(miktar.getText().toString()));
            }

            if (tutar.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(),"Tutar Gir",Toast.LENGTH_SHORT).show();
                sorun=true;
            }else{object.put("Tutar",Integer.parseInt(tutar.getText().toString()));}

            if (date == null) {
                Toast.makeText(getApplicationContext(), "Tarih Gir", Toast.LENGTH_SHORT).show();
                sorun = true;
            } else {
                object.put("Tarih", date);
            }

            if (!acklama.getText().toString().isEmpty()) {
                object.put("Aciklama", acklama.getText().toString());
            }

            object.put("Hareket", "Geldi");

            object.put("User", ParseUser.getCurrentUser().getUsername());

            if (sorun == false) {
                object.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e != null) {
                            e.printStackTrace();
                        } else {
                            Toast.makeText(getApplicationContext(), "Yükleme Başarılı", Toast.LENGTH_SHORT).show();
                            a=true;
                        }
                    }
                });
            }
        } else if (malzeme.getSelectedItem().toString().equalsIgnoreCase("Palet")) {
            ParseObject object = new ParseObject("Palet");

            if (olcu.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Ölçü Gir", Toast.LENGTH_SHORT).show();
                sorun = true;
            } else {
                object.put("Olcu", olcu.getText().toString());
            }

            if (firma.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(), "Firma Gir", Toast.LENGTH_SHORT).show();
                sorun = true;
            } else {
                object.put("Firma", firma.getText().toString());
            }

            if (miktar.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Miktar Gir", Toast.LENGTH_SHORT).show();
                sorun = true;
            } else {
                object.put("Miktar", Integer.parseInt(miktar.getText().toString()));
            }

            if (tutar.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(),"Tutar Gir",Toast.LENGTH_SHORT).show();
                sorun=true;
            }else{object.put("Tutar",Integer.parseInt(tutar.getText().toString()));}

            if (date == null) {
                Toast.makeText(getApplicationContext(), "Tarih Gir", Toast.LENGTH_SHORT).show();
                sorun = true;
            } else {
                object.put("Tarih",date);
            }

            if (!acklama.getText().toString().isEmpty()) {
                object.put("Aciklama", acklama.getText().toString());
            }

            object.put("Hareket", "Geldi");

            object.put("User", ParseUser.getCurrentUser().getUsername());

            if (sorun == false) {
                object.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e != null) {
                            e.printStackTrace();
                        } else {
                            Toast.makeText(getApplicationContext(), "Yükleme Başarılı", Toast.LENGTH_SHORT).show();
                            a=true;
                        }
                    }
                });
            }
        } else if (malzeme.getSelectedItem().toString().equalsIgnoreCase("Poşet")) {
            ParseObject object = new ParseObject("Poset");

            if (olcu.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Ölçü Gir", Toast.LENGTH_SHORT).show();
                sorun = true;
            } else {
                object.put("Olcu", olcu.getText().toString());
            }

            if (firma.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(), "Firma Gir", Toast.LENGTH_SHORT).show();
                sorun = true;
            } else {
                object.put("Firma", firma.getText().toString());
            }

            if (miktar.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Miktar Gir", Toast.LENGTH_SHORT).show();
                sorun = true;
            } else {
                object.put("Miktar", Integer.parseInt(miktar.getText().toString()));
            }

            if (tutar.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(),"Tutar Gir",Toast.LENGTH_SHORT).show();
                sorun=true;
            }else{object.put("Tutar",Integer.parseInt(tutar.getText().toString()));}

            if (date == null) {
                Toast.makeText(getApplicationContext(), "Tarih Gir", Toast.LENGTH_SHORT).show();
                sorun = true;
            } else {
                object.put("Tarih", date);
            }

            if (!acklama.getText().toString().isEmpty()) {
                object.put("Aciklama", acklama.getText().toString());
            }

            object.put("Hareket", "Geldi");

            object.put("User", ParseUser.getCurrentUser().getUsername());

            if (sorun == false) {
                object.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e != null) {
                            Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Yükleme Başarılı", Toast.LENGTH_SHORT).show();
                            a=true;
                        }
                    }
                });
            }
        }
        if (a==true){
            this.finish();
        }

    }

    class malzemelist extends BaseAdapter{

        @Override
        public int getCount() {
            return cfirmalar.size();
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
            view=getLayoutInflater().inflate(R.layout.customgmalzeme,null);

            TextView tv_firma=view.findViewById(R.id.tv_cmg_firma);
            TextView tv_miktar=view.findViewById(R.id.tv_cmg_miktar);
            TextView tv_tarih=view.findViewById(R.id.tv_cmg_tarih);
            TextView tv_boyut=view.findViewById(R.id.tv_cmg_boyut);
            TextView tv_tutar=view.findViewById(R.id.tv_cmg_tutar);
            TextView tv_birim=view.findViewById(R.id.tv_cmg_birim);
            TextView tv_aciklama=view.findViewById(R.id.tv_cmg_aciklama);

            tv_firma.setText(cfirmalar.get(i));
            if (malzeme.getSelectedItem()=="Kömür"||malzeme.getSelectedItem()=="LNG"){
                tv_miktar.setText(String.valueOf(cmiktar.get(i))+" kg");
            }else{
                tv_miktar.setText(String.valueOf(cmiktar.get(i))+" adet");
            }
            tv_tarih.setText(ctarih.get(i));
            tv_boyut.setText(cboyutlar.get(i));
            tv_tutar.setText(String.valueOf(ctutar.get(i))+" TL+kdv");
            tv_birim.setText(String.valueOf(cbirim.get(i))+" ");
            tv_aciklama.setText(caciklama.get(i));

            notifyDataSetChanged();

            return view;
        }

        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
        }
    }
}
