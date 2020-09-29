package pomex.garnet;

import androidx.annotation.IntegerRes;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
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

import com.parse.CountCallback;
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

public class GarnetSevkiyat extends AppCompatActivity {
    private Spinner boyut,firma;
    private EditText plaka;
    private AutoCompleteTextView yukleyen;
    private ListView listView,listView2;
    private DatePickerDialog.OnDateSetListener datepicker;
    private TimePickerDialog.OnTimeSetListener timepicker;
    private Calendar cal;
    private TextView tarih,saat;
    private ArrayList<String> boyutlar,firmalar,idler,elemanlar,list_firma,list_tarih,list_saat,list_miktar,list_boyut;
    private ArrayAdapter<String> boyutadap,firmaadp;
    private String secilen_boyut,secilen_firma,s_tablo;
    private ArrayAdapter<Long> bigbagadp;
    private ArrayAdapter<String> elemanadp;
    private ArrayList<Long> bigbagno,sevk;
    private ArrayList<Integer> siparis;
    private Integer no,s;
    private Button button;
    private String id;
    private Date date;
    private DateFormat df;
    private Integer a,z;
    private Boolean sorun;
    private CheckBox cbk,cbb,cbp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garnet_sevkiyat);
        getSupportActionBar().hide();

        boyut=findViewById(R.id.sevk_spin_boyut);
        firma=findViewById(R.id.sevk_spin_firma);
        plaka=findViewById(R.id.etsevk_plaka);
        yukleyen=findViewById(R.id.atv_sevk_yukleyen);
        tarih=findViewById(R.id.tvsevk_tarih);
        saat=findViewById(R.id.tvsevk_saat);
        listView=findViewById(R.id.lvsevk);
        listView2=findViewById(R.id.lvsevk2);
        button=findViewById(R.id.button_sevk_yuklendi);
        cbb=findViewById(R.id.cb_sevk_bigbag);
        cbk=findViewById(R.id.cb_sevk_kaft);
        cbp=findViewById(R.id.cb_sevk_palet);

        ParseQuery<ParseObject> query=ParseQuery.getQuery("GarnetSevkiyat");
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

        siparis=new ArrayList<Integer>();

        firmalar=new ArrayList<>();
        firmalar.add("Firma Seç");
        firmaadp=new ArrayAdapter<String>(GarnetSevkiyat.this,R.layout.spinnerlayout,firmalar);
        firma.setAdapter(firmaadp);

        boyutlar=new ArrayList<>();
        boyutlar.addAll(Arrays.asList("Boyut Seç","30/60 Mesh","80 Mesh","180 Mesh"));
        boyutadap=new ArrayAdapter<String>(GarnetSevkiyat.this,R.layout.spinnerlayout,boyutlar);
        boyut.setAdapter(boyutadap);

        elemanlar=new ArrayList<>();
        elemanlar.addAll(Arrays.asList("Beytullah Burulday","Ramazan Biçer","Ergül Gülyüz","Abdil Doğru","Sami Ceyhan","Üzeyir Akcan","Uğur Yılmaz","Tufan Önder","Hakan Ünlü","Mesut Akman","İsmail Efe","Mehmet Atsan"));
        Collections.sort(elemanlar);
        elemanadp = new ArrayAdapter<String>(GarnetSevkiyat.this, R.layout.spinnerlayout, elemanlar);
        yukleyen.setAdapter(elemanadp);

        bigbagno=new ArrayList<Long>();
        bigbagadp=new ArrayAdapter<Long>(GarnetSevkiyat.this,android.R.layout.simple_list_item_1,bigbagno);

        sevk=new ArrayList<Long>();
        idler=new ArrayList<String>();
        list_tarih=new ArrayList<String>();
        list_saat=new ArrayList<String>();
        list_firma=new ArrayList<String>();
        list_boyut=new ArrayList<String>();
        list_miktar=new ArrayList<String>();

        ParseQuery<ParseObject> querylist=ParseQuery.getQuery("GarnetSevkiyat");
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
                        list_miktar.add(String.valueOf(object.getInt("Adet"))+" ton");
                    }
                }
                customsevkiyatlistadp listadp=new customsevkiyatlistadp();
                listView2.setAdapter(listadp);
                listView2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,list_firma.size()*125));
            }
        });

        boyut.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                secilen_boyut=(String) boyut.getItemAtPosition(i);

                if (secilen_boyut.equalsIgnoreCase("80 Mesh")){
                    firmalar.clear();
                    firmalar.add("Firma Seç");
                    ParseQuery<ParseObject> query=ParseQuery.getQuery("G80MeshBigbag");
                    query.whereEqualTo("Gitti",false);
                    query.whereEqualTo("Gitti",null);
                    query.whereNotEqualTo("Firma","Tekrar Beslendi");
                    query.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            if (e!=null){
                                e.printStackTrace();
                            }else{
                                for (ParseObject obje:objects){
                                    if (obje.getString("Firma")!=null){
                                        firmalar.add(obje.getString("Firma"));
                                    }
                                }
                            }
                            HashSet<String> hs=new HashSet<String>();
                            hs.addAll(firmalar);
                            firmalar.clear();
                            firmalar.addAll(hs);
                            Collections.sort(firmalar);
                            for (int j=0;j<firmalar.size();j++){
                                if (firmalar.get(j).equalsIgnoreCase("Firma Seç")){
                                    firmalar.remove(j);
                                }
                            }
                            firmalar.add(0,"Firma Seç");
                        }
                    });

                }else if (secilen_boyut.equalsIgnoreCase("180 Mesh")){
                    firmalar.clear();
                    firmalar.add("Firma Seç");
                    ParseQuery<ParseObject> query=ParseQuery.getQuery("G180MeshBigbag");
                    query.whereEqualTo("Gitti",false);
                    query.whereEqualTo("Gitti",null);
                    query.whereNotEqualTo("Firma","Tekrar Beslendi");
                    query.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            if (e!=null){
                                e.printStackTrace();
                            }else{
                                for (ParseObject obje:objects){
                                    if (obje.getString("Firma")!=null){
                                        firmalar.add(obje.getString("Firma"));
                                    }
                                }
                            }
                            HashSet<String> hs=new HashSet<String>();
                            hs.addAll(firmalar);
                            firmalar.clear();
                            firmalar.addAll(hs);
                            hs.clear();
                            Collections.sort(firmalar);
                            for (int j=0;j<firmalar.size();j++){
                                if (firmalar.get(j).equalsIgnoreCase("Firma Seç")){
                                    firmalar.remove(j);
                                }
                            }
                            firmalar.add(0,"Firma Seç");
                        }
                    });

                }else if (secilen_boyut.equalsIgnoreCase("30/60 Mesh")){
                    firmalar.clear();
                    firmalar.add("Firma Seç");
                    ParseQuery<ParseObject> query=ParseQuery.getQuery("G3060MeshBigbag");
                    query.whereEqualTo("Gitti",false);
                    query.whereEqualTo("Gitti",null);
                    query.whereNotEqualTo("Firma","Tekrar Beslendi");
                    query.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            if (e!=null){
                                e.printStackTrace();
                            }else{
                                for (ParseObject obje:objects){
                                    if (obje.getString("Firma")!=null){
                                        firmalar.add(obje.getString("Firma"));
                                    }
                                }
                            }
                            HashSet<String> hs=new HashSet<String>();
                            hs.addAll(firmalar);
                            firmalar.clear();
                            firmalar.addAll(hs);
                            hs.clear();
                            Collections.sort(firmalar);
                            for (int j=0;j<firmalar.size();j++){
                                if (firmalar.get(j).equalsIgnoreCase("Firma Seç")){
                                    firmalar.remove(j);
                                }
                            }
                            firmalar.add(0,"Firma Seç");
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        firma.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sevk.clear();
                bigbagno.clear();
                cbb.setChecked(false);
                cbk.setChecked(false);
                cbp.setChecked(false);

                secilen_firma=(String) firma.getItemAtPosition(i);

                if (boyut.getSelectedItemPosition()!=0 && firma.getSelectedItemPosition()!=0 && secilen_boyut.equalsIgnoreCase("80 Mesh")){
                    ParseQuery<ParseObject> query=ParseQuery.getQuery("G80MeshBigbag");
                    query.whereEqualTo("Gitti",false);
                    query.whereEqualTo("Gitti",null);
                    query.whereEqualTo("Firma",secilen_firma);

                    query.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            if (e!=null){
                                e.printStackTrace();
                            }else{
                                for (ParseObject obje:objects){
                                    bigbagno.add(obje.getLong("BigbagNo"));
                                }
                            }
                            customsevkiyatadp adp=new customsevkiyatadp();
                            listView.setAdapter(adp);
                            listView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,bigbagno.size()*180));
                        }
                    });
                }else if (boyut.getSelectedItemPosition()!=0 && firma.getSelectedItemPosition()!=0 && secilen_boyut.equalsIgnoreCase("180 Mesh")){
                    ParseQuery<ParseObject> query=ParseQuery.getQuery("G180MeshBigbag");
                    query.whereEqualTo("Gitti",false);
                    query.whereEqualTo("Gitti",null);
                    query.whereEqualTo("Firma",secilen_firma);

                    query.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            if (e!=null){
                                e.printStackTrace();
                            }else{
                                for (ParseObject obje:objects){
                                    bigbagno.add(obje.getLong("BigbagNo"));
                                }
                            }
                            customsevkiyatadp adp=new customsevkiyatadp();
                            listView.setAdapter(adp);
                            listView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,bigbagno.size()*170));
                        }
                    });
                }else if (boyut.getSelectedItemPosition()!=0 && firma.getSelectedItemPosition()!=0 && secilen_boyut.equalsIgnoreCase("30/60 Mesh")){
                    ParseQuery<ParseObject> query=ParseQuery.getQuery("G3060MeshBigbag");
                    query.whereEqualTo("Gitti",false);
                    query.whereEqualTo("Gitti",null);
                    query.whereEqualTo("Firma",secilen_firma);

                    query.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            if (e!=null){
                                e.printStackTrace();
                            }else{
                                for (ParseObject obje:objects){
                                    bigbagno.add(obje.getLong("BigbagNo"));
                                }
                            }
                            customsevkiyatadp adp=new customsevkiyatadp();
                            listView.setAdapter(adp);
                            listView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,bigbagno.size()*170));
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        tarih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cal=Calendar.getInstance();
                int year=cal.get(Calendar.YEAR);
                int month=cal.get(Calendar.MONTH);
                int day=cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog ddialog=new DatePickerDialog(GarnetSevkiyat.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,datepicker,year,month,day);
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

                TimePickerDialog tdialog= new TimePickerDialog(GarnetSevkiyat.this,android.R.style.Theme_Holo_Light_Dialog,timepicker,saaat,dakika,true);
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

    public void aracYukle (View view){
        sorun=false;
        if (secilen_boyut.equalsIgnoreCase("80 Mesh")&&sevk.size()!=0){

            ParseObject object=new ParseObject("GarnetSevkiyat");
            object.put("No",no);
            object.put("Boyut","80 Mesh");
            object.put("Adet",sevk.size());
            object.put("Firma",secilen_firma);
            if (plaka.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(),"Plaka Gir",Toast.LENGTH_SHORT).show();
                sorun=true;
            }else{object.put("Plaka",plaka.getText().toString());}
            if (yukleyen.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(),"Yükleyenin İsmini Gir",Toast.LENGTH_SHORT).show();
                sorun=true;
            }else{object.put("Yukleyen",yukleyen.getText().toString());}
            if (date==null){
                Toast.makeText(getApplicationContext(),"Tarih Gir",Toast.LENGTH_SHORT).show();
                sorun=true;
            }else{object.put("Tarih",date);}
            if (saat.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(),"Saat Gir",Toast.LENGTH_SHORT).show();
                sorun=true;
            }else{object.put("Saat",saat.getText().toString());}
            if (cbb.isChecked()){object.put("Bigbag",true);}
            if (cbk.isChecked()){object.put("Kraft",true);}
            if (cbp.isChecked()){object.put("Palet",true);}
            object.put("User", ParseUser.getCurrentUser().getUsername());

            if (sorun==false){
                object.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e!=null){
                            Toast.makeText(getApplicationContext(),e.getLocalizedMessage()+" G-Sevk",Toast.LENGTH_LONG).show();
                            sorun=true;
                        }
                    }
                });
            }

            if (cbb.isChecked() && !cbk.isChecked()){
                ParseQuery<ParseObject> query=ParseQuery.getQuery("Bigbag");
                query.getInBackground("Rk0EM79pBC", new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if (e==null && sorun==false){
                            object.increment("Miktar",sevk.size());
                            object.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if(e!=null){
                                        Toast.makeText(getApplicationContext(),e.getLocalizedMessage()+" cbb !cbk",Toast.LENGTH_LONG).show();
                                        sorun=true;
                                    }
                                }
                            });
                        }else{
                            e.printStackTrace();
                        }
                    }
                });
            }

            if (cbb.isChecked() && cbk.isChecked()){
                ParseQuery<ParseObject> query=ParseQuery.getQuery("Bigbag");
                query.getInBackground("2zvlGVHUbH", new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if (e==null && sorun==false){
                            object.increment("Miktar",sevk.size());
                            object.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if(e!=null){
                                        Toast.makeText(getApplicationContext(),e.getLocalizedMessage()+" cbb cbk",Toast.LENGTH_LONG).show();
                                        sorun=true;
                                    }
                                }
                            });
                        }else{
                            e.printStackTrace();
                        }
                    }
                });
            }
            if (cbk.isChecked()){
                ParseQuery<ParseObject> query=ParseQuery.getQuery("Kraft");
                query.getInBackground("1OTOdPEffb", new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if (e==null && sorun==false){
                            object.increment("Miktar",sevk.size()*40);
                            object.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if(e!=null){
                                        Toast.makeText(getApplicationContext(),e.getLocalizedMessage()+" cbk",Toast.LENGTH_LONG).show();
                                        sorun=true;
                                    }
                                }
                            });
                        }else{
                            e.printStackTrace();
                        }
                    }
                });
            }
            if (cbp.isChecked()){
                ParseObject objectt=new ParseObject("Palet");
                objectt.put("Olcu","110x110");
                objectt.put("Hareket","Gitti");
                objectt.put("Miktar",sevk.size());
                objectt.put("Tarih",date);
                object.put("User", ParseUser.getCurrentUser().getUsername());
                if (sorun==false){
                    objectt.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e!=null){
                                Toast.makeText(getApplicationContext(),e.getLocalizedMessage()+" cbp",Toast.LENGTH_LONG).show();
                                sorun=true;
                            }
                        }
                    });
                }
            }

            for (z=0;z<sevk.size();z++){

                ParseQuery<ParseObject> query=ParseQuery.getQuery("G80MeshBigbag");
                query.whereEqualTo("BigbagNo",sevk.get(z));
                query.getFirstInBackground(new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if (e!=null){
                            e.printStackTrace();
                        }else{
                            id=object.getObjectId();
                            ParseQuery<ParseObject> qq = ParseQuery.getQuery("G80MeshBigbag");
                            qq.getInBackground(id, new GetCallback<ParseObject>() {
                                @Override
                                public void done(ParseObject object, ParseException e) {
                                    if (e != null) {
                                        e.printStackTrace();
                                    } else {

                                        object.put("Gitti", true);
                                        if (date==null){
                                            Toast.makeText(getApplicationContext(),"Tarih Gir",Toast.LENGTH_SHORT).show();
                                            sorun=true;
                                        }else{object.put("Tarih", date);}
                                        object.put("SevkNo",no);
                                        if (sorun==false){
                                            object.saveInBackground(new SaveCallback() {
                                                @Override
                                                public void done(ParseException e) {
                                                    if (e!=null){
                                                        Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                                                        sorun=true;
                                                    }
                                                }
                                            });
                                        }
                                    }
                                }
                            });
                        }
                    }
                });
            }
            if (sorun==false){
                Toast.makeText(getApplicationContext(),"Sisteme Kayıt Edildi",Toast.LENGTH_LONG).show();
                this.finish();
            }
        }else if (secilen_boyut.equalsIgnoreCase("180 Mesh")&&sevk.size()!=0){
            sorun=false;

            ParseObject object=new ParseObject("GarnetSevkiyat");
            object.put("No",no);
            object.put("Boyut","180 Mesh");
            object.put("Adet",sevk.size());
            object.put("Firma",secilen_firma);
            if (plaka.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(),"Plaka Gir",Toast.LENGTH_SHORT).show();
                sorun=true;
            }else{object.put("Plaka",plaka.getText().toString());}
            if (yukleyen.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(),"Yükleyenin İsmini Gir",Toast.LENGTH_SHORT).show();
                sorun=true;
            }else{object.put("Yukleyen",yukleyen.getText().toString());}
            if (date==null){
                Toast.makeText(getApplicationContext(),"Tarih Gir",Toast.LENGTH_SHORT).show();
                sorun=true;
            }else{object.put("Tarih",date);}
            if (saat.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(),"Saat Gir",Toast.LENGTH_SHORT).show();
                sorun=true;
            }else{object.put("Saat",saat.getText().toString());}
            if (cbb.isChecked()){object.put("Bigbag",true);}
            if (cbk.isChecked()){object.put("Kraft",true);}
            if (cbp.isChecked()){object.put("Palet",true);}

            object.put("User", ParseUser.getCurrentUser().getUsername());

            if (sorun==false){
                object.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e!=null){
                            Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                            sorun=true;
                        }
                    }
                });
            }

            if (cbb.isChecked() && !cbk.isChecked()){
                ParseQuery<ParseObject> query=ParseQuery.getQuery("Bigbag");
                query.getInBackground("Rk0EM79pBC", new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if (e==null && sorun==false){
                            object.increment("Miktar",sevk.size());
                            object.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if(e!=null){
                                        Toast.makeText(getApplicationContext(),e.getLocalizedMessage()+" cbb !cbk",Toast.LENGTH_LONG).show();
                                        sorun=true;
                                    }
                                }
                            });
                        }else{
                            e.printStackTrace();
                        }
                    }
                });
            }
            if (cbb.isChecked() && cbk.isChecked()){
                ParseQuery<ParseObject> query=ParseQuery.getQuery("Bigbag");
                query.getInBackground("2zvlGVHUbH", new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if (e==null && sorun==false){
                            object.increment("Miktar",sevk.size());
                            object.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if(e!=null){
                                        Toast.makeText(getApplicationContext(),e.getLocalizedMessage()+" cbb cbk",Toast.LENGTH_LONG).show();
                                        sorun=true;
                                    }
                                }
                            });
                        }else{
                            e.printStackTrace();
                        }
                    }
                });
            }
            if (cbk.isChecked()){
                ParseQuery<ParseObject> query=ParseQuery.getQuery("Kraft");
                query.getInBackground("1OTOdPEffb", new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if (e==null && sorun==false){
                            object.increment("Miktar",sevk.size()*40);
                            object.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if(e!=null){
                                        Toast.makeText(getApplicationContext(),e.getLocalizedMessage()+" cbk",Toast.LENGTH_LONG).show();
                                        sorun=true;
                                    }
                                }
                            });
                        }else{
                            e.printStackTrace();
                        }
                    }
                });
            }
            if (cbp.isChecked()){
                ParseObject objectt=new ParseObject("Palet");
                objectt.put("Olcu","110x110");
                objectt.put("Hareket","Gitti");
                objectt.put("Miktar",sevk.size());
                objectt.put("Tarih",date);
                object.put("User", ParseUser.getCurrentUser().getUsername());
                if (sorun==false){
                    objectt.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e!=null){
                                Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                                sorun=true;
                            }
                        }
                    });
                }
            }

            for (z=0;z<sevk.size();z++){

                ParseQuery<ParseObject> query=ParseQuery.getQuery("G180MeshBigbag");
                query.whereEqualTo("BigbagNo",sevk.get(z));
                query.getFirstInBackground(new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if (e!=null){
                            e.printStackTrace();
                        }else{
                            id=object.getObjectId();
                            ParseQuery<ParseObject> qq = ParseQuery.getQuery("G180MeshBigbag");
                            qq.getInBackground(id, new GetCallback<ParseObject>() {
                                @Override
                                public void done(ParseObject object, ParseException e) {
                                    if (e != null) {
                                        e.printStackTrace();
                                    } else {
                                        object.put("Gitti", true);
                                        if (date==null){
                                            Toast.makeText(getApplicationContext(),"Tarih Gir",Toast.LENGTH_SHORT).show();
                                            sorun=true;
                                        }else{object.put("Tarih", date);}
                                        object.put("SevkNo",no);
                                        if (sorun==false){
                                            object.saveInBackground(new SaveCallback() {
                                                @Override
                                                public void done(ParseException e) {
                                                    if (e!=null){
                                                        Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                                                        sorun=true;
                                                    }
                                                }
                                            });
                                        }
                                    }
                                }
                            });
                        }
                    }
                });
            }
            if (sorun==false){
                Toast.makeText(getApplicationContext(),"Sisteme Kayıt Edildi",Toast.LENGTH_LONG).show();
                this.finish();
            }
        }else if (secilen_boyut.equalsIgnoreCase("30/60 Mesh")&&sevk.size()!=0){
            sorun=false;

            ParseObject object=new ParseObject("GarnetSevkiyat");
            object.put("No",no);
            object.put("Boyut","30/60 Mesh");
            object.put("Adet",sevk.size());
            object.put("Firma",secilen_firma);
            if (plaka.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(),"Plaka Gir",Toast.LENGTH_SHORT).show();
                sorun=true;
            }else{object.put("Plaka",plaka.getText().toString());}
            if (yukleyen.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(),"Yükleyenin İsmini Gir",Toast.LENGTH_SHORT).show();
                sorun=true;
            }else{object.put("Yukleyen",yukleyen.getText().toString());}
            if (date==null){
                Toast.makeText(getApplicationContext(),"Tarih Gir",Toast.LENGTH_SHORT).show();
                sorun=true;
            }else{object.put("Tarih",date);}
            if (saat.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(),"Saat Gir",Toast.LENGTH_SHORT).show();
                sorun=true;
            }else{object.put("Saat",saat.getText().toString());}
            if (cbb.isChecked()){object.put("Bigbag",true);}
            if (cbk.isChecked()){object.put("Kraft",true);}
            if (cbp.isChecked()){object.put("Palet",true);}

            object.put("User", ParseUser.getCurrentUser().getUsername());

            if (sorun==false){
                object.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e!=null){
                            Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                            sorun=true;
                        }
                    }
                });
            }

            if (cbb.isChecked() && !cbk.isChecked()){
                ParseQuery<ParseObject> query=ParseQuery.getQuery("Bigbag");
                query.getInBackground("Rk0EM79pBC", new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if (e==null && sorun==false){
                            object.increment("Miktar",sevk.size());
                            object.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if(e!=null){
                                        Toast.makeText(getApplicationContext(),e.getLocalizedMessage()+" cbb !cbk",Toast.LENGTH_LONG).show();
                                        sorun=true;
                                    }
                                }
                            });
                        }else{
                            e.printStackTrace();
                        }
                    }
                });
            }
            if (cbb.isChecked() && cbk.isChecked()){
                ParseQuery<ParseObject> query=ParseQuery.getQuery("Bigbag");
                query.getInBackground("2zvlGVHUbH", new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if (e==null && sorun==false){
                            object.increment("Miktar",sevk.size());
                            object.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if(e!=null){
                                        Toast.makeText(getApplicationContext(),e.getLocalizedMessage()+" cbb cbk",Toast.LENGTH_LONG).show();
                                        sorun=true;
                                    }
                                }
                            });
                        }else{
                            e.printStackTrace();
                        }
                    }
                });
            }
            if (cbk.isChecked()){
                ParseQuery<ParseObject> query=ParseQuery.getQuery("Kraft");
                query.getInBackground("1OTOdPEffb", new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if (e==null && sorun==false){
                            object.increment("Miktar",sevk.size()*40);
                            object.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if(e!=null){
                                        Toast.makeText(getApplicationContext(),e.getLocalizedMessage()+" cbk",Toast.LENGTH_LONG).show();
                                        sorun=true;
                                    }
                                }
                            });
                        }else{
                            e.printStackTrace();
                        }
                    }
                });
            }
            if (cbp.isChecked()){
                ParseObject objectt=new ParseObject("Palet");
                objectt.put("Olcu","110x110");
                objectt.put("Hareket","Gitti");
                objectt.put("Miktar",sevk.size());
                objectt.put("Tarih",date);
                object.put("User", ParseUser.getCurrentUser().getUsername());
                if (sorun==false){
                    objectt.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e!=null){
                                Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                                sorun=true;
                            }
                        }
                    });
                }
            }

            for (z=0;z<sevk.size();z++){

                ParseQuery<ParseObject> query=ParseQuery.getQuery("G3060MeshBigbag");
                query.whereEqualTo("BigbagNo",sevk.get(z));
                query.getFirstInBackground(new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if (e!=null){
                            e.printStackTrace();
                        }else{
                            id=object.getObjectId();
                            ParseQuery<ParseObject> qq = ParseQuery.getQuery("G3060MeshBigbag");
                            qq.getInBackground(id, new GetCallback<ParseObject>() {
                                @Override
                                public void done(ParseObject object, ParseException e) {
                                    if (e != null) {
                                        e.printStackTrace();
                                    } else {
                                        object.put("Gitti", true);
                                        if (date==null){
                                            Toast.makeText(getApplicationContext(),"Tarih Gir",Toast.LENGTH_SHORT).show();
                                            sorun=true;
                                        }else{object.put("Tarih", date);}
                                        object.put("SevkNo",no);
                                        if (sorun==false){
                                            object.saveInBackground(new SaveCallback() {
                                                @Override
                                                public void done(ParseException e) {
                                                    if (e!=null){
                                                        Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                                                        sorun=true;
                                                    }
                                                }
                                            });
                                        }
                                    }
                                }
                            });
                        }
                    }
                });
            }

            if (sorun==false){
                Toast.makeText(getApplicationContext(),"Sisteme Kayıt Edildi",Toast.LENGTH_LONG).show();
                this.finish();
            }
        }
    }
    class customsevkiyatadp extends BaseAdapter{

        @Override
        public int getCount() {
            return bigbagno.size();
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
            view=getLayoutInflater().inflate(R.layout.sevkiyat_bigbag,null);

            TextView c_bigbag=view.findViewById(R.id.tv_sb_bigbagno);
            final TextView c_firma=view.findViewById(R.id.tv_sb_firma);
            final CheckBox cbsevk=view.findViewById(R.id.cb_sb_sec);

            c_bigbag.setText(String.valueOf(bigbagno.get(i)));
            c_firma.setText(secilen_firma);

            cbsevk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (cbsevk.isChecked()){
                        sevk.add(bigbagno.get(i));

                    }else if(!cbsevk.isChecked()){

                        for (int j=0;j<sevk.size();j++){
                            if (bigbagno.get(i).equals(sevk.get(j))){
                                sevk.remove(j);
                            }
                        }

                        for (int k=0;k<sevk.size();k++){
                            if (sevk.size()!=0){
                                Log.e("Listede Kalan",String.valueOf(sevk.get(k)));
                            }
                        }
                    }
                }
            });
            return view;
        }
    }
    class customsevkiyatlistadp extends BaseAdapter{

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
            view=getLayoutInflater().inflate(R.layout.sevkiyat_list,null);

            TextView s_tarih=view.findViewById(R.id.tv_s_list_tarih);
            TextView s_saat=view.findViewById(R.id.tv_s_list_saat);
            TextView s_firma=view.findViewById(R.id.tv_s_list_firma);
            TextView s_boyut=view.findViewById(R.id.tv_s_list_boyut);
            TextView s_miktar=view.findViewById(R.id.tv_s_list_miktar);

            s_tarih.setText(list_tarih.get(i));
            s_saat.setText(list_saat.get(i));
            s_firma.setText(list_firma.get(i));
            s_boyut.setText(list_boyut.get(i));
            s_miktar.setText(list_miktar.get(i));

            return view;
        }
    }
}
