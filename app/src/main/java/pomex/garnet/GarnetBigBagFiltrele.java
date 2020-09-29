package pomex.garnet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class GarnetBigBagFiltrele extends AppCompatActivity {
    private ArrayList<String> boyutlar,firmalar,firma2,not,tarih,idler;
    private ArrayList<Long> bigbagno,bigbaglist;
    private ArrayList<Double> anlz1,anlz2,anlz3;
    private ArrayList<Boolean> gitti,kraft,bigbag;
    private ArrayAdapter<String> boyutadap,firmaadp,atv_adp;
    private Spinner boyut,firma;
    private CheckBox cbstok,cbbos;
    private TextView miktar,listbigbag,listanlz1,listanlz2,listanlz3,listfirma,listgitti;
    private ListView listView;
    private String secilen_boyut,secilen_firma,yazılan_firma;
    private Button but_firm_yaz;
    private AutoCompleteTextView firma_yazılan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garnet_big_bag_filtrele);
        getSupportActionBar().hide();

        but_firm_yaz=findViewById(R.id.buttonfilt_firmayaz);
        boyut=findViewById(R.id.spinfiltboyut);
        firma=findViewById(R.id.spinfiltfirma);
        cbstok=findViewById(R.id.cbfilt_stok);
        cbbos=findViewById(R.id.cbfilt_bos);
        miktar=findViewById(R.id.tvfilt_miktar);
        listView=findViewById(R.id.lv_garnetfiltre);
        listbigbag=findViewById(R.id.tvlist_bigbagno);
        listanlz1=findViewById(R.id.tvlist_anlz1);
        listanlz2=findViewById(R.id.tvlist_anlz2);
        listanlz3=findViewById(R.id.tvlist_anlz3);
        listfirma=findViewById(R.id.tvlist_firma);
        listgitti=findViewById(R.id.tvlist_gitti);
        firma_yazılan=findViewById(R.id.autoCompleteTextViewfilt);

        listbigbag.setText("Bigbag No");
        listfirma.setText("Firma");
        listgitti.setText("Seç");

        boyutlar=new ArrayList<>();
        boyutlar.addAll(Arrays.asList("Boyut Seç","30/60 Mesh","80 Mesh","180 Mesh"));
        boyutadap=new ArrayAdapter<String>(GarnetBigBagFiltrele.this,R.layout.spinnerlayout,boyutlar);
        boyut.setAdapter(boyutadap);

        firmalar=new ArrayList<>();
        firmalar.add("Firma Seç");
        firmaadp=new ArrayAdapter<String>(GarnetBigBagFiltrele.this,R.layout.spinnerlayout,firmalar);
        firma.setAdapter(firmaadp);

        bigbagno=new ArrayList<Long>();
        bigbaglist=new ArrayList<Long>();
        anlz1=new ArrayList<>();
        anlz2=new ArrayList<>();
        anlz3=new ArrayList<>();
        firma2=new ArrayList<>();
        not=new ArrayList<>();
        tarih=new ArrayList<>();
        gitti=new ArrayList<>();
        idler=new ArrayList<>();
        kraft=new ArrayList<>();
        bigbag=new ArrayList<>();

        boyut.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                secilen_boyut=(String) boyut.getItemAtPosition(i);

                if (secilen_boyut.equalsIgnoreCase("80 Mesh")){
                    listanlz1.setText("+400");
                    listanlz2.setText("-212");
                    listanlz3.setText("-160");

                    firmalar.clear();
                    firmalar.add("Firma Seç");
                    firmalar.add("Hepsi");
                    ParseQuery<ParseObject> query=ParseQuery.getQuery("G80MeshBigbag");
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

                            atv_adp=new ArrayAdapter<String>(GarnetBigBagFiltrele.this,R.layout.spinnerlayout,firmalar);
                            firma_yazılan.setAdapter(atv_adp);

                            for (int j=0;j<firmalar.size();j++){
                                if (firmalar.get(j).equalsIgnoreCase("Hepsi")){
                                    firmalar.remove(j);
                                }
                            }
                            firmalar.add(0,"Hepsi");

                            for (int j=0;j<firmalar.size();j++){
                                if (firmalar.get(j).equalsIgnoreCase("Firma Seç")){
                                    firmalar.remove(j);
                                }
                            }
                            firmalar.add(0,"Firma Seç");
                        }
                    });

                }else if (secilen_boyut.equalsIgnoreCase("180 Mesh")){
                    listanlz1.setText("+212");
                    listanlz2.setText("+160");
                    listanlz3.setText("-90");

                    firmalar.clear();
                    firmalar.add("Firma Seç");
                    firmalar.add("Hepsi");
                    ParseQuery<ParseObject> query=ParseQuery.getQuery("G180MeshBigbag");
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

                            atv_adp=new ArrayAdapter<String>(GarnetBigBagFiltrele.this,R.layout.spinnerlayout,firmalar);
                            firma_yazılan.setAdapter(atv_adp);

                            for (int j=0;j<firmalar.size();j++){
                                if (firmalar.get(j).equalsIgnoreCase("Hepsi")){
                                    firmalar.remove(j);
                                }
                            }
                            firmalar.add(0,"Hepsi");

                            for (int j=0;j<firmalar.size();j++){
                                if (firmalar.get(j).equalsIgnoreCase("Firma Seç")){
                                    firmalar.remove(j);
                                }
                            }
                            firmalar.add(0,"Firma Seç");
                        }
                    });

                }else if (secilen_boyut.equalsIgnoreCase("30/60 Mesh")){
                    listanlz1.setText("+600");
                    listanlz2.setText("+400");
                    listanlz3.setText("-212");

                    firmalar.clear();
                    firmalar.add("Firma Seç");
                    firmalar.add("Hepsi");
                    ParseQuery<ParseObject> query=ParseQuery.getQuery("G3060MeshBigbag");
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

                            atv_adp=new ArrayAdapter<String>(GarnetBigBagFiltrele.this,R.layout.spinnerlayout,firmalar);
                            firma_yazılan.setAdapter(atv_adp);

                            for (int j=0;j<firmalar.size();j++){
                                if (firmalar.get(j).equalsIgnoreCase("Hepsi")){
                                    firmalar.remove(j);
                                }
                            }
                            firmalar.add(0,"Hepsi");

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
                bigbagno.clear();
                anlz1.clear();
                anlz2.clear();
                anlz3.clear();
                firma2.clear();
                not.clear();
                gitti.clear();
                tarih.clear();
                kraft.clear();
                bigbag.clear();

                secilen_firma=(String) firma.getItemAtPosition(i);

                if (secilen_boyut.equalsIgnoreCase("80 Mesh")){
                    ParseQuery<ParseObject> query=ParseQuery.getQuery("G80MeshBigbag");
                    if (!secilen_firma.equalsIgnoreCase("Hepsi")){
                        query.whereEqualTo("Firma",secilen_firma);
                    }
                    query.orderByDescending("No");
                    query.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            if (e!=null){
                                e.printStackTrace();
                            }else{
                                for (ParseObject object:objects){
                                    bigbagno.add(object.getLong("BigbagNo"));
                                    anlz1.add(object.getDouble("ust400"));
                                    anlz2.add(object.getDouble("alt212"));
                                    anlz3.add(object.getDouble("alt160"));
                                    firma2.add(object.getString("Firma"));
                                    not.add(object.getString("Not"));

                                    if (object.getBoolean("Kraft")==true){
                                        kraft.add(true);
                                    }else{kraft.add(false);}
                                    if (object.getBoolean("Bigbag")==true){
                                        bigbag.add(true);
                                    }else{bigbag.add(false);}

                                    if (object.getBoolean("Gitti")==true){
                                        gitti.add(true);
                                    }else{
                                        gitti.add(false);
                                    }
                                    if (object.getDate("Tarih")!=null){
                                        tarih.add(String.valueOf(object.getDate("Tarih").getDate())+"/"+String.valueOf(object.getDate("Tarih").getMonth()+1)+"/"+String.valueOf(object.getDate("Tarih").getYear()+1900));
                                    }else{
                                        tarih.add("-");
                                    }

                                }
                            }
                            miktar.setText(String.valueOf(bigbagno.size()));
                            customlist adp=new customlist();
                            listView.setAdapter(adp);
                            listView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,bigbagno.size()*150));
                        }
                    });
                }else if (secilen_boyut.equalsIgnoreCase("180 Mesh")){
                    ParseQuery<ParseObject> query=ParseQuery.getQuery("G180MeshBigbag");
                    if (!secilen_firma.equalsIgnoreCase("Hepsi")){
                        query.whereEqualTo("Firma",secilen_firma);
                    }
                    query.orderByDescending("No");
                    query.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            if (e!=null){
                                e.printStackTrace();
                            }else{
                                for (ParseObject object:objects){
                                    bigbagno.add(object.getLong("BigbagNo"));
                                    anlz1.add(object.getDouble("ust212"));
                                    anlz2.add(object.getDouble("ust160"));
                                    anlz3.add(object.getDouble("alt90"));
                                    firma2.add(object.getString("Firma"));
                                    not.add(object.getString("Not"));

                                    if (object.getBoolean("Kraft")==true){
                                        kraft.add(true);
                                    }else{kraft.add(false);}
                                    if (object.getBoolean("Bigbag")==true){
                                        bigbag.add(true);
                                    }else{bigbag.add(false);}

                                    if (object.getBoolean("Gitti")==true){
                                        gitti.add(true);
                                    }else{
                                        gitti.add(false);
                                    }
                                    if (object.getDate("Tarih")!=null){
                                        tarih.add(String.valueOf(object.getDate("Tarih").getDate())+"/"+String.valueOf(object.getDate("Tarih").getMonth()+1)+"/"+String.valueOf(object.getDate("Tarih").getYear()+1900));
                                    }else{
                                        tarih.add("-");
                                    }

                                }
                            }
                            miktar.setText(String.valueOf(bigbagno.size()));
                            customlist adp=new customlist();
                            listView.setAdapter(adp);
                            listView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,bigbagno.size()*150));
                        }
                    });
                }else if (secilen_boyut.equalsIgnoreCase("30/60 Mesh")){
                    ParseQuery<ParseObject> query=ParseQuery.getQuery("G3060MeshBigbag");
                    if (!secilen_firma.equalsIgnoreCase("Hepsi")){
                        query.whereEqualTo("Firma",secilen_firma);
                    }
                    query.orderByDescending("No");
                    query.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            if (e!=null){
                                e.printStackTrace();
                            }else{
                                for (ParseObject object:objects){
                                    bigbagno.add(object.getLong("BigbagNo"));
                                    anlz1.add(object.getDouble("ust600"));
                                    anlz2.add(object.getDouble("ust400"));
                                    anlz3.add(object.getDouble("alt212"));
                                    firma2.add(object.getString("Firma"));
                                    not.add(object.getString("Not"));

                                    if (object.getBoolean("Kraft")==true){
                                        kraft.add(true);
                                    }else{kraft.add(false);}
                                    if (object.getBoolean("Bigbag")==true){
                                        bigbag.add(true);
                                    }else{bigbag.add(false);}

                                    if (object.getBoolean("Gitti")==true){
                                        gitti.add(true);
                                    }else{
                                        gitti.add(false);
                                    }
                                    if (object.getDate("Tarih")!=null){
                                        tarih.add(String.valueOf(object.getDate("Tarih").getDate())+"/"+String.valueOf(object.getDate("Tarih").getMonth()+1)+"/"+String.valueOf(object.getDate("Tarih").getYear()+1900));
                                    }else{
                                        tarih.add("-");
                                    }

                                }
                            }
                            miktar.setText(String.valueOf(bigbagno.size()));
                            customlist adp=new customlist();
                            listView.setAdapter(adp);
                            listView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,bigbagno.size()*150));
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        cbstok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bigbagno.clear();
                anlz1.clear();
                anlz2.clear();
                anlz3.clear();
                firma2.clear();
                not.clear();
                gitti.clear();
                tarih.clear();
                kraft.clear();
                bigbag.clear();

                if (cbstok.isChecked()){
                    if (secilen_boyut.equalsIgnoreCase("80 Mesh")){
                        ParseQuery<ParseObject> query=ParseQuery.getQuery("G80MeshBigbag");
                        if (!secilen_firma.equalsIgnoreCase("Hepsi")){
                            query.whereEqualTo("Firma",secilen_firma);
                        }
                        query.orderByDescending("No");
                        query.whereEqualTo("Gitti",false);
                        query.whereEqualTo("Gitti",null);
                        query.findInBackground(new FindCallback<ParseObject>() {
                            @Override
                            public void done(List<ParseObject> objects, ParseException e) {
                                if (e!=null){
                                    e.printStackTrace();
                                }else{
                                    for (ParseObject object:objects){
                                        bigbagno.add(object.getLong("BigbagNo"));
                                        anlz1.add(object.getDouble("ust400"));
                                        anlz2.add(object.getDouble("alt212"));
                                        anlz3.add(object.getDouble("alt160"));
                                        firma2.add(object.getString("Firma"));
                                        not.add(object.getString("Not"));

                                        if (object.getBoolean("Kraft")==true){
                                            kraft.add(true);
                                        }else{kraft.add(false);}
                                        if (object.getBoolean("Bigbag")==true){
                                            bigbag.add(true);
                                        }else{bigbag.add(false);}

                                        if (object.getBoolean("Gitti")==true){
                                            gitti.add(object.getBoolean("Gitti"));
                                        }else{
                                            gitti.add(false);
                                        }
                                        if (object.getDate("Tarih")!=null){
                                            tarih.add(String.valueOf(object.getDate("Tarih").getDate())+"/"+String.valueOf(object.getDate("Tarih").getMonth()+1)+"/"+String.valueOf(object.getDate("Tarih").getYear()+1900));
                                        }else{
                                            tarih.add("-");
                                        }

                                    }
                                }
                                miktar.setText(String.valueOf(bigbagno.size()));
                                customlist adp=new customlist();
                                listView.setAdapter(adp);
                                listView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,bigbagno.size()*150));
                            }
                        });
                    }else if (secilen_boyut.equalsIgnoreCase("180 Mesh")){
                        ParseQuery<ParseObject> query=ParseQuery.getQuery("G180MeshBigbag");
                        if (!secilen_firma.equalsIgnoreCase("Hepsi")){
                            query.whereEqualTo("Firma",secilen_firma);
                        }
                        query.orderByDescending("No");
                        query.whereEqualTo("Gitti",false);
                        query.whereEqualTo("Gitti",null);
                        query.findInBackground(new FindCallback<ParseObject>() {
                            @Override
                            public void done(List<ParseObject> objects, ParseException e) {
                                if (e!=null){
                                    e.printStackTrace();
                                }else{
                                    for (ParseObject object:objects){
                                        bigbagno.add(object.getLong("BigbagNo"));
                                        anlz1.add(object.getDouble("ust212"));
                                        anlz2.add(object.getDouble("ust160"));
                                        anlz3.add(object.getDouble("alt90"));
                                        firma2.add(object.getString("Firma"));
                                        not.add(object.getString("Not"));

                                        if (object.getBoolean("Kraft")==true){
                                            kraft.add(true);
                                        }else{kraft.add(false);}
                                        if (object.getBoolean("Bigbag")==true){
                                            bigbag.add(true);
                                        }else{bigbag.add(false);}

                                        if (object.getBoolean("Gitti")==true){
                                            gitti.add(object.getBoolean("Gitti"));
                                        }else{
                                            gitti.add(false);
                                        }
                                        if (object.getDate("Tarih")!=null){
                                            tarih.add(String.valueOf(object.getDate("Tarih").getDate())+"/"+String.valueOf(object.getDate("Tarih").getMonth()+1)+"/"+String.valueOf(object.getDate("Tarih").getYear()+1900));
                                        }else{
                                            tarih.add("-");
                                        }

                                    }
                                }
                                miktar.setText(String.valueOf(bigbagno.size()));
                                customlist adp=new customlist();
                                listView.setAdapter(adp);
                                listView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,bigbagno.size()*150));
                            }
                        });
                    }else if (secilen_boyut.equalsIgnoreCase("30/60 Mesh")){
                        ParseQuery<ParseObject> query=ParseQuery.getQuery("G3060MeshBigbag");
                        if (!secilen_firma.equalsIgnoreCase("Hepsi")){
                            query.whereEqualTo("Firma",secilen_firma);
                        }
                        query.orderByDescending("No");
                        query.whereEqualTo("Gitti",false);
                        query.whereEqualTo("Gitti",null);
                        query.findInBackground(new FindCallback<ParseObject>() {
                            @Override
                            public void done(List<ParseObject> objects, ParseException e) {
                                if (e!=null){
                                    e.printStackTrace();
                                }else{
                                    for (ParseObject object:objects){
                                        bigbagno.add(object.getLong("BigbagNo"));
                                        anlz1.add(object.getDouble("ust600"));
                                        anlz2.add(object.getDouble("ust400"));
                                        anlz3.add(object.getDouble("alt212"));
                                        firma2.add(object.getString("Firma"));
                                        not.add(object.getString("Not"));

                                        if (object.getBoolean("Kraft")==true){
                                            kraft.add(true);
                                        }else{kraft.add(false);}
                                        if (object.getBoolean("Bigbag")==true){
                                            bigbag.add(true);
                                        }else{bigbag.add(false);}

                                        if (object.getBoolean("Gitti")==true){
                                            gitti.add(object.getBoolean("Gitti"));
                                        }else{
                                            gitti.add(false);
                                        }
                                        if (object.getDate("Tarih")!=null){
                                            tarih.add(String.valueOf(object.getDate("Tarih").getDate())+"/"+String.valueOf(object.getDate("Tarih").getMonth()+1)+"/"+String.valueOf(object.getDate("Tarih").getYear()+1900));
                                        }else{
                                            tarih.add("-");
                                        }

                                    }
                                }
                                miktar.setText(String.valueOf(bigbagno.size()));
                                customlist adp=new customlist();
                                listView.setAdapter(adp);
                                listView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,bigbagno.size()*150));
                            }
                        });
                    }
                }else if (!cbstok.isChecked()){
                    if (secilen_boyut.equalsIgnoreCase("80 Mesh")){
                        ParseQuery<ParseObject> query=ParseQuery.getQuery("G80MeshBigbag");
                        if (!secilen_firma.equalsIgnoreCase("Hepsi")){
                            query.whereEqualTo("Firma",secilen_firma);
                        }
                        query.orderByDescending("No");
                        query.findInBackground(new FindCallback<ParseObject>() {
                            @Override
                            public void done(List<ParseObject> objects, ParseException e) {
                                if (e!=null){
                                    e.printStackTrace();
                                }else{
                                    for (ParseObject object:objects){
                                        bigbagno.add(object.getLong("BigbagNo"));
                                        anlz1.add(object.getDouble("ust400"));
                                        anlz2.add(object.getDouble("alt212"));
                                        anlz3.add(object.getDouble("alt160"));
                                        firma2.add(object.getString("Firma"));
                                        not.add(object.getString("Not"));

                                        if (object.getBoolean("Kraft")==true){
                                            kraft.add(true);
                                        }else{kraft.add(false);}
                                        if (object.getBoolean("Bigbag")==true){
                                            bigbag.add(true);
                                        }else{bigbag.add(false);}

                                        if (object.getBoolean("Gitti")==true){
                                            gitti.add(object.getBoolean("Gitti"));
                                        }else{
                                            gitti.add(false);
                                        }
                                        if (object.getDate("Tarih")!=null){
                                            tarih.add(String.valueOf(object.getDate("Tarih").getDate())+"/"+String.valueOf(object.getDate("Tarih").getMonth()+1)+"/"+String.valueOf(object.getDate("Tarih").getYear()+1900));
                                        }else{
                                            tarih.add("-");
                                        }

                                    }
                                }
                                miktar.setText(String.valueOf(bigbagno.size()));
                                customlist adp=new customlist();
                                listView.setAdapter(adp);
                                listView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,bigbagno.size()*150));
                            }
                        });
                    }else if (secilen_boyut.equalsIgnoreCase("180 Mesh")){
                        ParseQuery<ParseObject> query=ParseQuery.getQuery("G180MeshBigbag");
                        if (!secilen_firma.equalsIgnoreCase("Hepsi")){
                            query.whereEqualTo("Firma",secilen_firma);
                        }
                        query.orderByDescending("No");
                        query.findInBackground(new FindCallback<ParseObject>() {
                            @Override
                            public void done(List<ParseObject> objects, ParseException e) {
                                if (e!=null){
                                    e.printStackTrace();
                                }else{
                                    for (ParseObject object:objects){
                                        bigbagno.add(object.getLong("BigbagNo"));
                                        anlz1.add(object.getDouble("ust212"));
                                        anlz2.add(object.getDouble("ust160"));
                                        anlz3.add(object.getDouble("alt90"));
                                        firma2.add(object.getString("Firma"));
                                        not.add(object.getString("Not"));

                                        if (object.getBoolean("Kraft")==true){
                                            kraft.add(true);
                                        }else{kraft.add(false);}
                                        if (object.getBoolean("Bigbag")==true){
                                            bigbag.add(true);
                                        }else{bigbag.add(false);}

                                        if (object.getBoolean("Gitti")==true){
                                            gitti.add(object.getBoolean("Gitti"));
                                        }else{
                                            gitti.add(false);
                                        }
                                        if (object.getDate("Tarih")!=null){
                                            tarih.add(String.valueOf(object.getDate("Tarih").getDate())+"/"+String.valueOf(object.getDate("Tarih").getMonth()+1)+"/"+String.valueOf(object.getDate("Tarih").getYear()+1900));
                                        }else{
                                            tarih.add("-");
                                        }

                                    }
                                }
                                miktar.setText(String.valueOf(bigbagno.size()));
                                customlist adp=new customlist();
                                listView.setAdapter(adp);
                                listView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,bigbagno.size()*150));
                            }
                        });
                    }else if (secilen_boyut.equalsIgnoreCase("30/60 Mesh")){
                        ParseQuery<ParseObject> query=ParseQuery.getQuery("G3060MeshBigbag");
                        if (!secilen_firma.equalsIgnoreCase("Hepsi")){
                            query.whereEqualTo("Firma",secilen_firma);
                        }
                        query.orderByDescending("No");
                        query.findInBackground(new FindCallback<ParseObject>() {
                            @Override
                            public void done(List<ParseObject> objects, ParseException e) {
                                if (e!=null){
                                    e.printStackTrace();
                                }else{
                                    for (ParseObject object:objects){
                                        bigbagno.add(object.getLong("BigbagNo"));
                                        anlz1.add(object.getDouble("ust600"));
                                        anlz2.add(object.getDouble("ust400"));
                                        anlz3.add(object.getDouble("alt212"));
                                        firma2.add(object.getString("Firma"));
                                        not.add(object.getString("Not"));

                                        if (object.getBoolean("Kraft")==true){
                                            kraft.add(true);
                                        }else{kraft.add(false);}
                                        if (object.getBoolean("Bigbag")==true){
                                            bigbag.add(true);
                                        }else{bigbag.add(false);}

                                        if (object.getBoolean("Gitti")==true){
                                            gitti.add(object.getBoolean("Gitti"));
                                        }else{
                                            gitti.add(false);
                                        }
                                        if (object.getDate("Tarih")!=null){
                                            tarih.add(String.valueOf(object.getDate("Tarih").getDate())+"/"+String.valueOf(object.getDate("Tarih").getMonth()+1)+"/"+String.valueOf(object.getDate("Tarih").getYear()+1900));
                                        }else{
                                            tarih.add("-");
                                        }

                                    }
                                }
                                miktar.setText(String.valueOf(bigbagno.size()));
                                customlist adp=new customlist();
                                listView.setAdapter(adp);
                                listView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,bigbagno.size()*150));
                            }
                        });
                    }
                }
            }
        });

        cbbos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bigbagno.clear();
                anlz1.clear();
                anlz2.clear();
                anlz3.clear();
                firma2.clear();
                not.clear();
                gitti.clear();
                tarih.clear();
                kraft.clear();
                bigbag.clear();

                if (cbbos.isChecked()){
                    if (secilen_boyut.equalsIgnoreCase("80 Mesh")){
                        ParseQuery<ParseObject> query=ParseQuery.getQuery("G80MeshBigbag");
                        query.orderByDescending("No");
                        query.whereEqualTo("Gitti",false);
                        query.whereEqualTo("Gitti",null);
                        query.whereEqualTo("Firma",null);
                        query.findInBackground(new FindCallback<ParseObject>() {
                            @Override
                            public void done(List<ParseObject> objects, ParseException e) {
                                if (e!=null){
                                    e.printStackTrace();
                                }else{
                                    for (ParseObject object:objects){
                                        bigbagno.add(object.getLong("BigbagNo"));
                                        anlz1.add(object.getDouble("ust400"));
                                        anlz2.add(object.getDouble("alt212"));
                                        anlz3.add(object.getDouble("alt160"));
                                        firma2.add(object.getString("Firma"));
                                        not.add(object.getString("Not"));

                                        if (object.getBoolean("Kraft")==true){
                                            kraft.add(true);
                                        }else{kraft.add(false);}
                                        if (object.getBoolean("Bigbag")==true){
                                            bigbag.add(true);
                                        }else{bigbag.add(false);}

                                        if (object.getBoolean("Gitti")==true){
                                            gitti.add(object.getBoolean("Gitti"));
                                        }else{
                                            gitti.add(false);
                                        }
                                        if (object.getDate("Tarih")!=null){
                                            tarih.add(String.valueOf(object.getDate("Tarih").getDate())+"/"+String.valueOf(object.getDate("Tarih").getMonth()+1)+"/"+String.valueOf(object.getDate("Tarih").getYear()+1900));
                                        }else{
                                            tarih.add("-");
                                        }

                                    }
                                }
                                miktar.setText(String.valueOf(bigbagno.size()));
                                customlist adp=new customlist();
                                listView.setAdapter(adp);
                                listView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,bigbagno.size()*150));
                            }
                        });
                    }else if (secilen_boyut.equalsIgnoreCase("180 Mesh")){
                        ParseQuery<ParseObject> query=ParseQuery.getQuery("G180MeshBigbag");
                        query.orderByDescending("No");
                        query.whereEqualTo("Gitti",false);
                        query.whereEqualTo("Gitti",null);
                        query.whereEqualTo("Firma",null);
                        query.findInBackground(new FindCallback<ParseObject>() {
                            @Override
                            public void done(List<ParseObject> objects, ParseException e) {
                                if (e!=null){
                                    e.printStackTrace();
                                }else{
                                    for (ParseObject object:objects){
                                        bigbagno.add(object.getLong("BigbagNo"));
                                        anlz1.add(object.getDouble("ust212"));
                                        anlz2.add(object.getDouble("ust160"));
                                        anlz3.add(object.getDouble("alt90"));
                                        firma2.add(object.getString("Firma"));
                                        not.add(object.getString("Not"));

                                        if (object.getBoolean("Kraft")==true){
                                            kraft.add(true);
                                        }else{kraft.add(false);}
                                        if (object.getBoolean("Bigbag")==true){
                                            bigbag.add(true);
                                        }else{bigbag.add(false);}

                                        if (object.getBoolean("Gitti")==true){
                                            gitti.add(object.getBoolean("Gitti"));
                                        }else{
                                            gitti.add(false);
                                        }
                                        if (object.getDate("Tarih")!=null){
                                            tarih.add(String.valueOf(object.getDate("Tarih").getDate())+"/"+String.valueOf(object.getDate("Tarih").getMonth()+1)+"/"+String.valueOf(object.getDate("Tarih").getYear()+1900));
                                        }else{
                                            tarih.add("-");
                                        }

                                    }
                                }
                                miktar.setText(String.valueOf(bigbagno.size()));
                                customlist adp=new customlist();
                                listView.setAdapter(adp);
                                listView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,bigbagno.size()*150));
                            }
                        });
                    }else if (secilen_boyut.equalsIgnoreCase("30/60 Mesh")){
                        ParseQuery<ParseObject> query=ParseQuery.getQuery("G3060MeshBigbag");
                        query.orderByDescending("No");
                        query.whereEqualTo("Gitti",false);
                        query.whereEqualTo("Gitti",null);
                        query.whereEqualTo("Firma",null);
                        query.findInBackground(new FindCallback<ParseObject>() {
                            @Override
                            public void done(List<ParseObject> objects, ParseException e) {
                                if (e!=null){
                                    e.printStackTrace();
                                }else{
                                    for (ParseObject object:objects){
                                        bigbagno.add(object.getLong("BigbagNo"));
                                        anlz1.add(object.getDouble("ust600"));
                                        anlz2.add(object.getDouble("ust400"));
                                        anlz3.add(object.getDouble("alt212"));
                                        firma2.add(object.getString("Firma"));
                                        not.add(object.getString("Not"));

                                        if (object.getBoolean("Kraft")==true){
                                            kraft.add(true);
                                        }else{kraft.add(false);}
                                        if (object.getBoolean("Bigbag")==true){
                                            bigbag.add(true);
                                        }else{bigbag.add(false);}

                                        if (object.getBoolean("Gitti")==true){
                                            gitti.add(object.getBoolean("Gitti"));
                                        }else{
                                            gitti.add(false);
                                        }
                                        if (object.getDate("Tarih")!=null){
                                            tarih.add(String.valueOf(object.getDate("Tarih").getDate())+"/"+String.valueOf(object.getDate("Tarih").getMonth()+1)+"/"+String.valueOf(object.getDate("Tarih").getYear()+1900));
                                        }else{
                                            tarih.add("-");
                                        }

                                    }
                                }
                                miktar.setText(String.valueOf(bigbagno.size()));
                                customlist adp=new customlist();
                                listView.setAdapter(adp);
                                listView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,bigbagno.size()*150));
                            }
                        });
                    }
                }else if (!cbstok.isChecked()){
                    if (secilen_boyut.equalsIgnoreCase("80 Mesh")){
                        ParseQuery<ParseObject> query=ParseQuery.getQuery("G80MeshBigbag");
                        if (!secilen_firma.equalsIgnoreCase("Hepsi")){
                            query.whereEqualTo("Firma",secilen_firma);
                        }
                        query.orderByDescending("No");
                        query.findInBackground(new FindCallback<ParseObject>() {
                            @Override
                            public void done(List<ParseObject> objects, ParseException e) {
                                if (e!=null){
                                    e.printStackTrace();
                                }else{
                                    for (ParseObject object:objects){
                                        bigbagno.add(object.getLong("BigbagNo"));
                                        anlz1.add(object.getDouble("ust400"));
                                        anlz2.add(object.getDouble("alt212"));
                                        anlz3.add(object.getDouble("alt160"));
                                        firma2.add(object.getString("Firma"));
                                        not.add(object.getString("Not"));

                                        if (object.getBoolean("Kraft")==true){
                                            kraft.add(true);
                                        }else{kraft.add(false);}
                                        if (object.getBoolean("Bigbag")==true){
                                            bigbag.add(true);
                                        }else{bigbag.add(false);}

                                        if (object.getBoolean("Gitti")==true){
                                            gitti.add(object.getBoolean("Gitti"));
                                        }else{
                                            gitti.add(false);
                                        }
                                        if (object.getDate("Tarih")!=null){
                                            tarih.add(String.valueOf(object.getDate("Tarih").getDate())+"/"+String.valueOf(object.getDate("Tarih").getMonth()+1)+"/"+String.valueOf(object.getDate("Tarih").getYear()+1900));
                                        }else{
                                            tarih.add("-");
                                        }

                                    }
                                }
                                miktar.setText(String.valueOf(bigbagno.size()));
                                customlist adp=new customlist();
                                listView.setAdapter(adp);
                                listView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,bigbagno.size()*150));
                            }
                        });
                    }else if (secilen_boyut.equalsIgnoreCase("180 Mesh")){
                        ParseQuery<ParseObject> query=ParseQuery.getQuery("G180MeshBigbag");
                        if (!secilen_firma.equalsIgnoreCase("Hepsi")){
                            query.whereEqualTo("Firma",secilen_firma);
                        }
                        query.orderByDescending("No");
                        query.findInBackground(new FindCallback<ParseObject>() {
                            @Override
                            public void done(List<ParseObject> objects, ParseException e) {
                                if (e!=null){
                                    e.printStackTrace();
                                }else{
                                    for (ParseObject object:objects){
                                        bigbagno.add(object.getLong("BigbagNo"));
                                        anlz1.add(object.getDouble("ust212"));
                                        anlz2.add(object.getDouble("ust160"));
                                        anlz3.add(object.getDouble("alt90"));
                                        firma2.add(object.getString("Firma"));
                                        not.add(object.getString("Not"));

                                        if (object.getBoolean("Kraft")==true){
                                            kraft.add(true);
                                        }else{kraft.add(false);}
                                        if (object.getBoolean("Bigbag")==true){
                                            bigbag.add(true);
                                        }else{bigbag.add(false);}

                                        if (object.getBoolean("Gitti")==true){
                                            gitti.add(object.getBoolean("Gitti"));
                                        }else{
                                            gitti.add(false);
                                        }
                                        if (object.getDate("Tarih")!=null){
                                            tarih.add(String.valueOf(object.getDate("Tarih").getDate())+"/"+String.valueOf(object.getDate("Tarih").getMonth()+1)+"/"+String.valueOf(object.getDate("Tarih").getYear()+1900));
                                        }else{
                                            tarih.add("-");
                                        }

                                    }
                                }
                                miktar.setText(String.valueOf(bigbagno.size()));
                                customlist adp=new customlist();
                                listView.setAdapter(adp);
                                listView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,bigbagno.size()*150));
                            }
                        });
                    }else if (secilen_boyut.equalsIgnoreCase("30/60 Mesh")){
                        ParseQuery<ParseObject> query=ParseQuery.getQuery("G3060MeshBigbag");
                        if (!secilen_firma.equalsIgnoreCase("Hepsi")){
                            query.whereEqualTo("Firma",secilen_firma);
                        }
                        query.orderByDescending("No");
                        query.findInBackground(new FindCallback<ParseObject>() {
                            @Override
                            public void done(List<ParseObject> objects, ParseException e) {
                                if (e!=null){
                                    e.printStackTrace();
                                }else{
                                    for (ParseObject object:objects){
                                        bigbagno.add(object.getLong("BigbagNo"));
                                        anlz1.add(object.getDouble("ust600"));
                                        anlz2.add(object.getDouble("ust400"));
                                        anlz3.add(object.getDouble("alt212"));
                                        firma2.add(object.getString("Firma"));
                                        not.add(object.getString("Not"));

                                        if (object.getBoolean("Kraft")==true){
                                            kraft.add(true);
                                        }else{kraft.add(false);}
                                        if (object.getBoolean("Bigbag")==true){
                                            bigbag.add(true);
                                        }else{bigbag.add(false);}

                                        if (object.getBoolean("Gitti")==true){
                                            gitti.add(object.getBoolean("Gitti"));
                                        }else{
                                            gitti.add(false);
                                        }
                                        if (object.getDate("Tarih")!=null){
                                            tarih.add(String.valueOf(object.getDate("Tarih").getDate())+"/"+String.valueOf(object.getDate("Tarih").getMonth()+1)+"/"+String.valueOf(object.getDate("Tarih").getYear()+1900));
                                        }else{
                                            tarih.add("-");
                                        }

                                    }
                                }
                                miktar.setText(String.valueOf(bigbagno.size()));
                                customlist adp=new customlist();
                                listView.setAdapter(adp);
                                listView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,bigbagno.size()*150));
                            }
                        });
                    }
                }
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(GarnetBigBagFiltrele.this,GarnetBigbagDuzenle.class);
                intent.putExtra("BigbagNo",bigbagno.get(i));
                startActivity(intent);
                return true;
            }
        });

        but_firm_yaz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yazılan_firma=firma_yazılan.getText().toString();

                if (secilen_boyut.equalsIgnoreCase("80 Mesh")){
                    ParseQuery<ParseObject> query=ParseQuery.getQuery("G80MeshBigbag");
                    for (int i=0;i<bigbaglist.size();i++){
                        query.whereEqualTo("BigbagNo",bigbaglist.get(i));
                        query.getFirstInBackground(new GetCallback<ParseObject>() {
                            @Override
                            public void done(ParseObject object, ParseException e) {
                                if (e!=null){
                                    e.printStackTrace();
                                }else{
                                    idler.add(object.getObjectId());
                                }
                            }
                        });
                    }

                    ParseQuery<ParseObject> query1=ParseQuery.getQuery("G80MeshBigbag");
                    for (int i=0;i<idler.size();i++){
                        query1.getInBackground(idler.get(i), new GetCallback<ParseObject>() {
                            @Override
                            public void done(ParseObject object, ParseException e) {
                                if (yazılan_firma==null){
                                    object.remove("Firma");
                                }else{
                                    object.put("Firma",yazılan_firma);
                                }
                                object.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        if (e!=null){
                                            e.printStackTrace();
                                        }else{
                                            Toast.makeText(getApplicationContext(),idler.size()+" Kayıt",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }
                        });
                    }
                    idler.clear();

                }
                else if (secilen_boyut.equalsIgnoreCase("180 Mesh")){
                    ParseQuery<ParseObject> query=ParseQuery.getQuery("G180MeshBigbag");
                    for (int i=0;i<bigbaglist.size();i++){
                        query.whereEqualTo("BigbagNo",bigbaglist.get(i));
                        query.getFirstInBackground(new GetCallback<ParseObject>() {
                            @Override
                            public void done(ParseObject object, ParseException e) {
                                if (e!=null){
                                    e.printStackTrace();
                                }else{
                                    idler.add(object.getObjectId());
                                }
                            }
                        });
                    }

                    ParseQuery<ParseObject> query1=ParseQuery.getQuery("G180MeshBigbag");
                    for (int i=0;i<idler.size();i++){
                        query1.getInBackground(idler.get(i), new GetCallback<ParseObject>() {
                            @Override
                            public void done(ParseObject object, ParseException e) {
                                if (yazılan_firma==null){
                                    object.remove("Firma");
                                }else{
                                    object.put("Firma",yazılan_firma);
                                }
                                object.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        if (e!=null){
                                            e.printStackTrace();
                                        }else{
                                            Toast.makeText(getApplicationContext(),idler.size()+" Kayıt",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }
                        });
                    }
                    idler.clear();
                }
                else if (secilen_boyut.equalsIgnoreCase("30/60 Mesh")){
                    ParseQuery<ParseObject> query=ParseQuery.getQuery("G3060MeshBigbag");
                    for (int i=0;i<bigbaglist.size();i++){
                        query.whereEqualTo("BigbagNo",bigbaglist.get(i));
                        query.getFirstInBackground(new GetCallback<ParseObject>() {
                            @Override
                            public void done(ParseObject object, ParseException e) {
                                if (e!=null){
                                    e.printStackTrace();
                                }else{
                                    idler.add(object.getObjectId());
                                }
                            }
                        });
                    }

                    ParseQuery<ParseObject> query1=ParseQuery.getQuery("G3060MeshBigbag");
                    for (int i=0;i<idler.size();i++){
                        query1.getInBackground(idler.get(i), new GetCallback<ParseObject>() {
                            @Override
                            public void done(ParseObject object, ParseException e) {
                                if (yazılan_firma==null){
                                    object.remove("Firma");
                                }else{
                                    object.put("Firma",yazılan_firma);
                                }
                                object.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        if (e!=null){
                                            e.printStackTrace();
                                        }else{
                                            Toast.makeText(getApplicationContext(),idler.size()+" Kayıt",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }
                        });
                    }
                    idler.clear();
                }
            }
        });


    }

    class customlist extends BaseAdapter{

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
            view=getLayoutInflater().inflate(R.layout.customlist,null);

            TextView tv_bigbag=view.findViewById(R.id.tv_bigbagno);
            TextView tv_anlz1=view.findViewById(R.id.tv_anlz1);
            TextView tv_anlz2=view.findViewById(R.id.tv_anlz2);
            TextView tv_anlz3=view.findViewById(R.id.tv_anlz3);
            TextView tv_not=view.findViewById(R.id.tv_not);
            TextView tv_tarih=view.findViewById(R.id.tv_tarih);
            TextView tv_firma=view.findViewById(R.id.tv_firma);
            TextView tv_paket=view.findViewById(R.id.tv_paket);
            final CheckBox cb_gitti=view.findViewById(R.id.cb_gitti);

            tv_bigbag.setText(String.valueOf(bigbagno.get(i)));
            tv_anlz1.setText(String.valueOf(anlz1.get(i)));
            tv_anlz2.setText(String.valueOf(anlz2.get(i)));
            tv_anlz3.setText(String.valueOf(anlz3.get(i)));
            tv_not.setText(not.get(i));
            tv_tarih.setText(tarih.get(i));
            tv_firma.setText(firma2.get(i));
            if (gitti.get(i)==true){
                tv_bigbag.setTextColor(Color.rgb(140,20,20));
                tv_anlz1.setTextColor(Color.rgb(140,20,20));
                tv_anlz2.setTextColor(Color.rgb(140,20,20));
                tv_anlz3.setTextColor(Color.rgb(140,20,20));
                tv_firma.setTextColor(Color.rgb(140,20,20));
                tv_not.setTextColor(Color.rgb(140,20,20));
                tv_tarih.setTextColor(Color.rgb(140,20,20));
                tv_paket.setTextColor(Color.rgb(140,20,20));
            }
            if (kraft.get(i)==true&&bigbag.get(i)==true){
                tv_paket.setText("Kraft");
            }else if (kraft.get(i)==false&&bigbag.get(i)==true){
                tv_paket.setText("Bigbag");
            }

            cb_gitti.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (cb_gitti.isChecked()){
                        bigbaglist.add(bigbagno.get(i));
                    }
                    else if (!cb_gitti.isChecked()){
                        for (int j=0;j<bigbaglist.size();j++){
                            if (bigbagno.get(i).equals(bigbaglist.get(j))){
                                bigbaglist.remove(j);
                            }
                        }
                    }
                }
            });
            return view;
        }
    }
}
