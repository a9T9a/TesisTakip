package pomex.garnet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.net.StandardSocketOptions;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class StokAylik extends AppCompatActivity {
    private EditText g36,g80,g180,r_iri,r_orta,r_ince,r_yik,r_ogut,man,hem,s77,s525,p77,p525,p12,lng,komur,b1,b2,b3,kraft,poset,palet;
    private EditText k_g36,k_g80,k_g180,k_r_iri,k_r_orta,k_r_ince,k_r_yik,k_r_ogut,k_man,k_hem,k_s77,k_s525,k_p77,k_p525,k_p12,k_lng,k_komur,k_b1,k_b2,k_b3,k_kraft,k_poset,k_palet;
    private TextView tarih,top_g36,top_g80,top_g180,top_r_iri,top_r_orta,top_r_ince,top_r_yik,top_r_ogut,top_man,top_hem,top_s77,top_s525,top_p77,top_p525,top_p12,top_lng,top_komur,top_b1,top_b2,top_b3,top_kraft,top_poset,top_palet;
    private Spinner s_g36,s_g80,s_g180,s_r_iri,s_r_orta,s_r_ince,s_r_yik,s_r_ogut,s_man,s_hem,s_s77,s_s525,s_p77,s_p525,s_p12,s_lng,s_komur,s_b1,s_b2,s_b3,s_kraft,s_poset,s_palet;
    private ArrayList<String> liste;
    private ArrayAdapter<String> listeadp;
    private Calendar cal;
    private Date date;
    private DateFormat df;
    private DatePickerDialog.OnDateSetListener datepicker;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aylik_stok);
        getSupportActionBar().hide();

        tarih=findViewById(R.id.as_tarih);
        g36=findViewById(R.id.as_et_g3060);
        g80=findViewById(R.id.as_et_g80);
        g180=findViewById(R.id.as_et_g180);
        r_iri=findViewById(R.id.as_et_riri);
        r_orta=findViewById(R.id.as_et_rorta);
        r_ince=findViewById(R.id.as_et_rince);
        r_yik=findViewById(R.id.as_et_ryikanmis);
        r_ogut=findViewById(R.id.as_et_rogut);
        man=findViewById(R.id.as_et_dmanyetit);
        hem=findViewById(R.id.as_et_dhematit);
        s77=findViewById(R.id.as_et_s770);
        s525=findViewById(R.id.as_et_s525);
        p77=findViewById(R.id.as_et_p770);
        p525=findViewById(R.id.as_et_p525);
        p12=findViewById(R.id.as_et_p12);
        lng=findViewById(R.id.as_et_lng);
        komur=findViewById(R.id.as_et_komur);
        b1=findViewById(R.id.as_et_b1);
        b2=findViewById(R.id.as_et_b2);
        b3=findViewById(R.id.as_et_b3);
        kraft=findViewById(R.id.as_et_kraft);
        poset=findViewById(R.id.as_et_poset);
        palet=findViewById(R.id.as_et_palet);
        button=findViewById(R.id.as_button);

        k_g36=findViewById(R.id.as_et_g3060_);
        k_g80=findViewById(R.id.as_et_g80_);
        k_g180=findViewById(R.id.as_et_g180_);
        k_r_iri=findViewById(R.id.as_et_riri_);
        k_r_orta=findViewById(R.id.as_et_rorta_);
        k_r_ince=findViewById(R.id.as_et_rince_);
        k_r_yik=findViewById(R.id.as_et_ryikanmis_);
        k_r_ogut=findViewById(R.id.as_et_rogut_);
        k_man=findViewById(R.id.as_et_dmanyetit_);
        k_hem=findViewById(R.id.as_et_dhematit_);
        k_s77=findViewById(R.id.as_et_s770_);
        k_s525=findViewById(R.id.as_et_s525_);
        k_p77=findViewById(R.id.as_et_p770_);
        k_p525=findViewById(R.id.as_et_p525_);
        k_p12=findViewById(R.id.as_et_p12_);
        k_lng=findViewById(R.id.as_et_lng_);
        k_komur=findViewById(R.id.as_et_komur_);
        k_b1=findViewById(R.id.as_et_b1_);
        k_b2=findViewById(R.id.as_et_b2_);
        k_b3=findViewById(R.id.as_et_b3_);
        k_kraft=findViewById(R.id.as_et_kraft_);
        k_poset=findViewById(R.id.as_et_poset_);
        k_palet=findViewById(R.id.as_et_palet_);

        top_g36=findViewById(R.id.as_top_g3060);
        top_g80=findViewById(R.id.as_top_g80);
        top_g180=findViewById(R.id.as_top_g180);
        top_r_iri=findViewById(R.id.as_top_riri);
        top_r_orta=findViewById(R.id.as_top_rorta);
        top_r_ince=findViewById(R.id.as_top_rince);
        top_r_yik=findViewById(R.id.as_top_ryika);
        top_r_ogut=findViewById(R.id.as_top_rogut);
        top_man=findViewById(R.id.as_top_many);
        top_hem=findViewById(R.id.as_top_hema);
        top_s77=findViewById(R.id.as_top_s770);
        top_s525=findViewById(R.id.as_top_s525);
        top_p77=findViewById(R.id.as_top_p770);
        top_p525=findViewById(R.id.as_top_p525);
        top_p12=findViewById(R.id.as_top_p12);
        top_lng=findViewById(R.id.as_top_lng);
        top_komur=findViewById(R.id.as_top_komur);
        top_b1=findViewById(R.id.as_top_b1);
        top_b2=findViewById(R.id.as_top_b2);
        top_b3=findViewById(R.id.as_top_b3);
        top_kraft=findViewById(R.id.as_top_kraft);
        top_poset=findViewById(R.id.as_top_poset);
        top_palet=findViewById(R.id.as_top_palet);

        s_g36=findViewById(R.id.as_spinner);
        s_g80=findViewById(R.id.as_spinner2);
        s_g180=findViewById(R.id.as_spinner3);
        s_r_iri=findViewById(R.id.as_spinner4);
        s_r_orta=findViewById(R.id.as_spinner5);
        s_r_ince=findViewById(R.id.as_spinner6);
        s_r_yik=findViewById(R.id.as_spinner7);
        s_r_ogut=findViewById(R.id.as_spinner8);
        s_man=findViewById(R.id.as_spinner9);
        s_hem=findViewById(R.id.as_spinner10);
        s_s77=findViewById(R.id.as_spinner11);
        s_s525=findViewById(R.id.as_spinner12);
        s_p77=findViewById(R.id.as_spinner13);
        s_p525=findViewById(R.id.as_spinner23);
        s_p12=findViewById(R.id.as_spinner14);
        s_lng=findViewById(R.id.as_spinner15);
        s_komur=findViewById(R.id.as_spinner16);
        s_b1=findViewById(R.id.as_spinner17);
        s_b2=findViewById(R.id.as_spinner18);
        s_b3=findViewById(R.id.as_spinner19);
        s_kraft=findViewById(R.id.as_spinner20);
        s_poset=findViewById(R.id.as_spinner21);
        s_palet=findViewById(R.id.as_spinner22);

        k_g36.setText("1.8");
        k_g80.setText("1");
        k_g180.setText("1");
        k_r_iri.setText("1.8");
        k_r_orta.setText("1.8");
        k_r_ince.setText("1.8");
        k_r_yik.setText("1");
        k_r_ogut.setText("1");
        k_man.setText("1");
        k_hem.setText("1.8");
        k_s77.setText("1.6");
        k_s525.setText("1.6");
        k_p12.setText("1.3");
        k_p525.setText("1.3");
        k_p77.setText("1.3");
        k_lng.setText("1");
        k_komur.setText("1");
        k_b1.setText("1");
        k_b2.setText("1");
        k_b3.setText("1");
        k_kraft.setText("1");
        k_poset.setText("1");
        k_palet.setText("1");

        liste=new ArrayList<>();
        liste.addAll(Arrays.asList("Bigbag","Yığın","Adet"));
        listeadp=new ArrayAdapter<String>(StokAylik.this,R.layout.spinnerlayout,liste);
        s_g36.setAdapter(listeadp);
        s_g80.setAdapter(listeadp);
        s_g180.setAdapter(listeadp);
        s_r_iri.setAdapter(listeadp);
        s_r_orta.setAdapter(listeadp);
        s_r_ince.setAdapter(listeadp);
        s_r_yik.setAdapter(listeadp);
        s_r_ogut.setAdapter(listeadp);
        s_man.setAdapter(listeadp);
        s_hem.setAdapter(listeadp);
        s_s77.setAdapter(listeadp);
        s_s525.setAdapter(listeadp);
        s_p77.setAdapter(listeadp);
        s_p525.setAdapter(listeadp);
        s_p12.setAdapter(listeadp);
        s_lng.setAdapter(listeadp);
        s_komur.setAdapter(listeadp);
        s_b1.setAdapter(listeadp);
        s_b2.setAdapter(listeadp);
        s_b3.setAdapter(listeadp);
        s_kraft.setAdapter(listeadp);
        s_poset.setAdapter(listeadp);
        s_palet.setAdapter(listeadp);

        if(getIntent().getStringExtra("id")!=null){
            final String id=getIntent().getStringExtra("id");
            ParseQuery<ParseObject>query=ParseQuery.getQuery("AylikStok");
            query.getInBackground(id, new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {
                    if (e!=null){
                        e.printStackTrace();
                    }else{
                        top_g36.setText(String.valueOf(object.getDouble("g3060")));
                        top_g80.setText(String.valueOf(object.getDouble("g80")));
                        top_g180.setText(String.valueOf(object.getDouble("g180")));
                        top_r_iri.setText(String.valueOf(object.getDouble("r_iri")));
                        top_r_orta.setText(String.valueOf(object.getDouble("r_orta")));
                        top_r_ince.setText(String.valueOf(object.getDouble("r_ince")));
                        top_r_yik.setText(String.valueOf(object.getDouble("r_yikanmis")));
                        top_r_ogut.setText(String.valueOf(object.getDouble("r_ogut")));
                        top_man.setText(String.valueOf(object.getDouble("man")));
                        top_hem.setText(String.valueOf(object.getDouble("hem")));
                        top_s77.setText(String.valueOf(object.getDouble("s77")));
                        top_s525.setText(String.valueOf(object.getDouble("s525")));
                        top_p77.setText(String.valueOf(object.getDouble("p77")));
                        top_p525.setText(String.valueOf(object.getDouble("p525")));
                        top_p12.setText(String.valueOf(object.getDouble("p12")));
                        top_lng.setText(String.valueOf(object.getDouble("lng")));
                        top_komur.setText(String.valueOf(object.getDouble("komur")));
                        top_b1.setText(String.valueOf(object.getDouble("b_90x90x60")));
                        top_b2.setText(String.valueOf(object.getDouble("b_90x90x70p")));
                        top_b3.setText(String.valueOf(object.getDouble("b_90x90x110")));
                        top_kraft.setText(String.valueOf(object.getDouble("kraft")));
                        top_poset.setText(String.valueOf(object.getDouble("poset")));
                        top_palet.setText(String.valueOf(object.getDouble("palet")));
                        Date date=object.getDate("Tarih");
                        tarih.setText(date.getDate()+"."+(date.getMonth()+1)+"."+(date.getYear()+1900));
                        button.setText("Düzelt");
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                ParseQuery<ParseObject>query1=ParseQuery.getQuery("AylikStok");
                                query1.getInBackground(id, new GetCallback<ParseObject>() {
                                    @Override
                                    public void done(ParseObject object, ParseException e) {
                                        if(e!=null){
                                            e.printStackTrace();
                                        }else{
                                            if (top_g36.getText().toString()==""||top_g80.getText().toString()==""||top_g180.getText().toString()==""||top_r_iri.getText().toString()==""||
                                                    top_r_orta.getText().toString()==""||top_r_ince.getText().toString()==""||top_r_yik.getText().toString()==""||top_r_ogut.getText().toString()==""||
                                                    top_man.getText().toString()==""||top_hem.getText().toString()==""||top_s77.getText().toString()==""||top_s525.getText().toString()==""||
                                                    top_p77.getText().toString()==""||top_p12.getText().toString()==""||top_lng.getText().toString()==""||top_komur.getText().toString()==""||
                                                    top_b1.getText().toString()==""||top_b2.getText().toString()==""||top_b3.getText().toString()==""||top_kraft.getText().toString()==""||
                                                    top_poset.getText().toString()==""||top_palet.getText().toString()==""||tarih.getText().toString()==""){
                                                Toast.makeText(StokAylik.this,"Boş Alanları Doldrun",Toast.LENGTH_LONG).show();
                                            }else {
                                                object.put("g3060", Double.valueOf(top_g36.getText().toString()));
                                                object.put("g80", Double.valueOf(top_g80.getText().toString()));
                                                object.put("g180", Double.valueOf(top_g180.getText().toString()));
                                                object.put("r_iri", Double.valueOf(top_r_iri.getText().toString()));
                                                object.put("r_orta", Double.valueOf(top_r_orta.getText().toString()));
                                                object.put("r_ince", Double.valueOf(top_r_ince.getText().toString()));
                                                object.put("r_yikanmis", Double.valueOf(top_r_yik.getText().toString()));
                                                object.put("r_ogut", Double.valueOf(top_r_ogut.getText().toString()));
                                                object.put("man", Double.valueOf(top_man.getText().toString()));
                                                object.put("hem", Double.valueOf(top_hem.getText().toString()));
                                                object.put("s77", Double.valueOf(top_s77.getText().toString()));
                                                object.put("s525", Double.valueOf(top_s525.getText().toString()));
                                                object.put("p77", Double.valueOf(top_p77.getText().toString()));
                                                object.put("p525", Double.valueOf(top_p525.getText().toString()));
                                                object.put("p12", Double.valueOf(top_p12.getText().toString()));
                                                object.put("lng", Double.valueOf(top_lng.getText().toString()));
                                                object.put("komur", Double.valueOf(top_komur.getText().toString()));
                                                object.put("b_90x90x60", Double.valueOf(top_b1.getText().toString()));
                                                object.put("b_90x90x70p", Double.valueOf(top_b2.getText().toString()));
                                                object.put("b_90x90x110", Double.valueOf(top_b3.getText().toString()));
                                                object.put("kraft", Double.valueOf(top_kraft.getText().toString()));
                                                object.put("poset", Double.valueOf(top_poset.getText().toString()));
                                                object.put("palet", Double.valueOf(top_palet.getText().toString()));
                                                object.put("user", ParseUser.getCurrentUser().getUsername());
                                                object.saveInBackground(new SaveCallback() {
                                                    @Override
                                                    public void done(ParseException e) {
                                                        if (e == null) {
                                                            Toast.makeText(StokAylik.this, "Kayıt Edildi", Toast.LENGTH_LONG).show();
                                                            StokAylik.this.finish();
                                                        } else {
                                                            Toast.makeText(StokAylik.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                                                        }
                                                    }
                                                });
                                            }
                                        }
                                    }
                                });
                            }
                        });
                    }
                }
            });

        }else{
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ParseObject object=new ParseObject("AylikStok");
                    if (top_g36.getText().toString()==""||top_g80.getText().toString()==""||top_g180.getText().toString()==""||top_r_iri.getText().toString()==""||
                            top_r_orta.getText().toString()==""||top_r_ince.getText().toString()==""||top_r_yik.getText().toString()==""||top_r_ogut.getText().toString()==""||
                            top_man.getText().toString()==""||top_hem.getText().toString()==""||top_s77.getText().toString()==""||top_s525.getText().toString()==""||
                            top_p77.getText().toString()==""||top_p12.getText().toString()==""||top_lng.getText().toString()==""||top_komur.getText().toString()==""||
                            top_b1.getText().toString()==""||top_b2.getText().toString()==""||top_b3.getText().toString()==""||top_kraft.getText().toString()==""||
                            top_poset.getText().toString()==""||top_palet.getText().toString()==""||tarih.getText().toString()==""){
                        Toast.makeText(StokAylik.this,"Boş Alanları Doldrun",Toast.LENGTH_LONG).show();
                    }else{
                        object.put("Tarih",date);
                        object.put("g3060",Double.valueOf(top_g36.getText().toString()));
                        object.put("g80",Double.valueOf(top_g80.getText().toString()));
                        object.put("g180",Double.valueOf(top_g180.getText().toString()));
                        object.put("r_iri",Double.valueOf(top_r_iri.getText().toString()));
                        object.put("r_orta",Double.valueOf(top_r_orta.getText().toString()));
                        object.put("r_ince",Double.valueOf(top_r_ince.getText().toString()));
                        object.put("r_yikanmis",Double.valueOf(top_r_yik.getText().toString()));
                        object.put("r_ogut",Double.valueOf(top_r_ogut.getText().toString()));
                        object.put("man",Double.valueOf(top_man.getText().toString()));
                        object.put("hem",Double.valueOf(top_hem.getText().toString()));
                        object.put("s77",Double.valueOf(top_s77.getText().toString()));
                        object.put("s525",Double.valueOf(top_s525.getText().toString()));
                        object.put("p77",Double.valueOf(top_p77.getText().toString()));
                        object.put("p525",Double.valueOf(top_p525.getText().toString()));
                        object.put("p12",Double.valueOf(top_p12.getText().toString()));
                        object.put("lng",Double.valueOf(top_lng.getText().toString()));
                        object.put("komur",Double.valueOf(top_komur.getText().toString()));
                        object.put("b_90x90x60",Double.valueOf(top_b1.getText().toString()));
                        object.put("b_90x90x70p",Double.valueOf(top_b2.getText().toString()));
                        object.put("b_90x90x110",Double.valueOf(top_b3.getText().toString()));
                        object.put("kraft",Double.valueOf(top_kraft.getText().toString()));
                        object.put("poset",Double.valueOf(top_poset.getText().toString()));
                        object.put("palet",Double.valueOf(top_palet.getText().toString()));
                        object.put("user", ParseUser.getCurrentUser().getUsername());
                        object.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if(e==null){
                                    Toast.makeText(StokAylik.this,"Kayıt Edildi",Toast.LENGTH_LONG).show();
                                    StokAylik.this.finish();
                                }else{
                                    Toast.makeText(StokAylik.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                }
            });
        }

        final As_toplam as_toplam=new As_toplam();

        g36.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                try{
                    top_g36.setText(as_toplam.toplam(g36,k_g36));
                }catch (Exception e){
                    Toast.makeText(StokAylik.this,"Miktar Giriniz",Toast.LENGTH_SHORT).show();
                }
            }
        });
        k_g36.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    top_g36.setText(as_toplam.toplam(g36,k_g36));
                }catch (Exception e){
                    Toast.makeText(StokAylik.this,"Katsayı Giriniz",Toast.LENGTH_SHORT).show();
                }
            }
        });

        g80.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                try{
                    top_g80.setText(as_toplam.toplam(g80,k_g80));
                }catch (Exception e){
                    Toast.makeText(StokAylik.this,"Miktar Giriniz",Toast.LENGTH_SHORT).show();
                }
            }
        });
        k_g80.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                try{
                    top_g80.setText(as_toplam.toplam(g80,k_g80));
                }catch (Exception e){
                    Toast.makeText(StokAylik.this,"Katsayı Giriniz",Toast.LENGTH_SHORT).show();
                }
            }
        });

        g180.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                try{
                    top_g180.setText(as_toplam.toplam(g180,k_g180));
                }catch (Exception e){
                    Toast.makeText(StokAylik.this,"Miktar Giriniz",Toast.LENGTH_SHORT).show();
                }
            }
        });
        k_g180.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                try{
                    top_g180.setText(as_toplam.toplam(g180,k_g180));
                }catch (Exception e){
                    Toast.makeText(StokAylik.this,"Katsayı Giriniz",Toast.LENGTH_SHORT).show();
                }
            }
        });

        r_iri.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                try{
                    top_r_iri.setText(as_toplam.toplam(r_iri,k_r_iri));
                }catch (Exception e){
                    Toast.makeText(StokAylik.this,"Miktar Giriniz",Toast.LENGTH_SHORT).show();
                }
            }
        });
        k_r_iri.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                try{
                    top_r_iri.setText(as_toplam.toplam(r_iri,k_r_iri));
                }catch (Exception e){
                    Toast.makeText(StokAylik.this,"Katsayı Giriniz",Toast.LENGTH_SHORT).show();
                }
            }
        });

        r_orta.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                try{
                    top_r_orta.setText(as_toplam.toplam(r_orta,k_r_orta));
                }catch (Exception e){
                    Toast.makeText(StokAylik.this,"Miktar Giriniz",Toast.LENGTH_SHORT).show();
                }
            }
        });
        k_r_orta.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                try {
                    top_r_orta.setText(as_toplam.toplam(r_orta,k_r_orta));
                }catch (Exception e){
                    Toast.makeText(StokAylik.this,"Katsayı Giriniz",Toast.LENGTH_SHORT).show();
                }
            }
        });

        r_ince.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                try{
                    top_r_ince.setText(as_toplam.toplam(r_ince,k_r_ince));
                }catch (Exception e){
                    Toast.makeText(StokAylik.this,"Miktar Giriniz",Toast.LENGTH_SHORT).show();
                }
            }
        });
        k_r_ince.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                try{
                    top_r_ince.setText(as_toplam.toplam(r_ince,k_r_ince));
                }catch (Exception e){
                    Toast.makeText(StokAylik.this,"Katsayı Giriniz",Toast.LENGTH_SHORT).show();
                }
            }
        });

        r_yik.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                try{
                    top_r_yik.setText(as_toplam.toplam(r_yik,k_r_yik));
                }catch (Exception e){
                    Toast.makeText(StokAylik.this,"Miktar Giriniz",Toast.LENGTH_SHORT).show();
                }
            }
        });
        k_r_yik.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                try{
                    top_r_yik.setText(as_toplam.toplam(r_yik,k_r_yik));
                }catch (Exception e){
                    Toast.makeText(StokAylik.this,"Katsayı Giriniz",Toast.LENGTH_SHORT).show();
                }
            }
        });

        r_ogut.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                try{
                    top_r_ogut.setText(as_toplam.toplam(r_ogut,k_r_ogut));
                }catch (Exception e){
                    Toast.makeText(StokAylik.this,"Miktar Giriniz",Toast.LENGTH_SHORT).show();
                }
            }
        });
        k_r_ogut.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                try{
                    top_r_ogut.setText(as_toplam.toplam(r_ogut,k_r_ogut));
                }catch (Exception e){
                    Toast.makeText(StokAylik.this,"Katsayı Giriniz",Toast.LENGTH_SHORT).show();
                }
            }
        });

        man.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                try{
                    top_man.setText(as_toplam.toplam(man,k_man));
                }catch (Exception e){
                    Toast.makeText(StokAylik.this,"Miktar Giriniz",Toast.LENGTH_SHORT).show();
                }
            }
        });
        k_man.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                try{
                    top_man.setText(as_toplam.toplam(man,k_man));
                }catch (Exception e){
                    Toast.makeText(StokAylik.this,"Katsayı Giriniz",Toast.LENGTH_SHORT).show();
                }
            }
        });

        hem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                try{
                    top_hem.setText(as_toplam.toplam(hem,k_hem));
                }catch (Exception e){
                    Toast.makeText(StokAylik.this,"Miktar Giriniz",Toast.LENGTH_SHORT).show();
                }
            }
        });
        k_hem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                try{
                    top_hem.setText(as_toplam.toplam(hem,k_hem));
                }catch (Exception e){
                    Toast.makeText(StokAylik.this,"Katsayı Giriniz",Toast.LENGTH_SHORT).show();
                }
            }
        });

        s77.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                try{
                    top_s77.setText(as_toplam.toplam(s77,k_s77));
                }catch (Exception e){
                    Toast.makeText(StokAylik.this,"Miktar Giriniz",Toast.LENGTH_SHORT).show();
                }
            }
        });
        k_s77.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                try{
                    top_s77.setText(as_toplam.toplam(s77,k_s77));
                }catch (Exception e){
                    Toast.makeText(StokAylik.this,"Katsayı Giriniz",Toast.LENGTH_SHORT).show();
                }
            }
        });

        s525.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                try{
                top_s525.setText(as_toplam.toplam(s525,k_s525));
                }catch (Exception e){
                    Toast.makeText(StokAylik.this,"Miktar Giriniz",Toast.LENGTH_SHORT).show();
                }
            }
        });
        k_s525.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                try{
                top_s525.setText(as_toplam.toplam(s525,k_s525));
                }catch (Exception e){
                    Toast.makeText(StokAylik.this,"Katsayı Giriniz",Toast.LENGTH_SHORT).show();
                }
            }
        });

        p77.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                try{
                top_p77.setText(as_toplam.toplam(p77,k_p77));
                }catch (Exception e){
                    Toast.makeText(StokAylik.this,"Miktar Giriniz",Toast.LENGTH_SHORT).show();
                }
            }
        });
        k_p77.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                try{
                top_p77.setText(as_toplam.toplam(p77,k_p77));
                }catch (Exception e){
                    Toast.makeText(StokAylik.this,"Katsayı Giriniz",Toast.LENGTH_SHORT).show();
                }
            }
        });

        p525.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                try{
                    top_p525.setText(as_toplam.toplam(p525,k_p525));
                }catch (Exception e){
                    Toast.makeText(StokAylik.this,"Miktar Giriniz",Toast.LENGTH_SHORT).show();
                }
            }
        });
        k_p525.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                try{
                    top_p525.setText(as_toplam.toplam(p525,k_p525));
                }catch (Exception e){
                    Toast.makeText(StokAylik.this,"Katsayı Giriniz",Toast.LENGTH_SHORT).show();
                }
            }
        });

        p12.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                try{
                top_p12.setText(as_toplam.toplam(p12,k_p12));
                }catch (Exception e){
                    Toast.makeText(StokAylik.this,"Miktar Giriniz",Toast.LENGTH_SHORT).show();
                }
            }
        });
        k_p12.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                try{
                    top_p12.setText(as_toplam.toplam(p12,k_p12));
                }catch (Exception e){
                    Toast.makeText(StokAylik.this,"Katsayı Giriniz",Toast.LENGTH_SHORT).show();
                }
            }
        });

        lng.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                try{
                    top_lng.setText(as_toplam.toplam(lng,k_lng));
                }catch (Exception e){
                    Toast.makeText(StokAylik.this,"Miktar Giriniz",Toast.LENGTH_SHORT).show();
                }
            }
        });
        k_lng.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                try{
                top_lng.setText(as_toplam.toplam(lng,k_lng));
                }catch (Exception e){
                    Toast.makeText(StokAylik.this,"Katsayı Giriniz",Toast.LENGTH_SHORT).show();
                }
            }
        });

        komur.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                try{
                top_komur.setText(as_toplam.toplam(komur,k_komur));
                }catch (Exception e){
                    Toast.makeText(StokAylik.this,"Miktar Giriniz",Toast.LENGTH_SHORT).show();
                }
            }
        });
        k_komur.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                try{
                top_komur.setText(as_toplam.toplam(komur,k_komur));
                }catch (Exception e){
                    Toast.makeText(StokAylik.this,"Katsayı Giriniz",Toast.LENGTH_SHORT).show();
                }
            }
        });

        b1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                try{
                top_b1.setText(as_toplam.toplam(b1,k_b1));
                }catch (Exception e){
                    Toast.makeText(StokAylik.this,"Miktar Giriniz",Toast.LENGTH_SHORT).show();
                }
            }
        });
        k_b1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                try{
                top_b1.setText(as_toplam.toplam(b1,k_b1));
                }catch (Exception e){
                    Toast.makeText(StokAylik.this,"Katsayı Giriniz",Toast.LENGTH_SHORT).show();
                }
            }
        });

        b2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                try{
                top_b2.setText(as_toplam.toplam(b2,k_b2));
                }catch (Exception e){
                    Toast.makeText(StokAylik.this,"Miktar Giriniz",Toast.LENGTH_SHORT).show();
                }
            }
        });
        k_b2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                try{
                top_b2.setText(as_toplam.toplam(b2,k_b2));
                }catch (Exception e){
                    Toast.makeText(StokAylik.this,"Katsayı Giriniz",Toast.LENGTH_SHORT).show();
                }
            }
        });

        b3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                try{
                top_b3.setText(as_toplam.toplam(b3,k_b3));
                }catch (Exception e){
                    Toast.makeText(StokAylik.this,"Miktar Giriniz",Toast.LENGTH_SHORT).show();
                }
            }
        });
        k_b3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                try{
                top_b3.setText(as_toplam.toplam(b3,k_b3));
                }catch (Exception e){
                    Toast.makeText(StokAylik.this,"Katsayı Giriniz",Toast.LENGTH_SHORT).show();
                }
            }
        });

        kraft.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                try{
                top_kraft.setText(as_toplam.toplam(kraft,k_kraft));
                }catch (Exception e){
                    Toast.makeText(StokAylik.this,"Miktar Giriniz",Toast.LENGTH_SHORT).show();
                }
            }
        });
        k_kraft.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                try{
                top_kraft.setText(as_toplam.toplam(kraft,k_kraft));
                }catch (Exception e){
                    Toast.makeText(StokAylik.this,"Katsayı Giriniz",Toast.LENGTH_SHORT).show();
                }
            }
        });

        poset.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                try{
                top_poset.setText(as_toplam.toplam(poset,k_poset));
                }catch (Exception e){
                    Toast.makeText(StokAylik.this,"Miktar Giriniz",Toast.LENGTH_SHORT).show();
                }
            }
        });
        k_poset.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                try{
                top_poset.setText(as_toplam.toplam(poset,k_poset));
                }catch (Exception e){
                    Toast.makeText(StokAylik.this,"Katsayı Giriniz",Toast.LENGTH_SHORT).show();
                }
            }
        });

        palet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                try{
                    top_palet.setText(as_toplam.toplam(palet,k_palet));
                }catch (Exception e){
                    Toast.makeText(StokAylik.this,"Miktar Giriniz",Toast.LENGTH_SHORT).show();
                }
            }
        });
        k_palet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                try{
                    top_palet.setText(as_toplam.toplam(palet,k_palet));
                }catch (Exception e){
                    Toast.makeText(StokAylik.this,"Katsayı Giriniz",Toast.LENGTH_SHORT).show();
                }
            }
        });

        s_r_yik.setSelection(1);
        s_man.setSelection(1);
        s_lng.setSelection(1);
        s_komur.setSelection(1);
        s_b1.setSelection(2);
        s_b2.setSelection(2);
        s_b3.setSelection(2);
        s_kraft.setSelection(2);
        s_poset.setSelection(2);
        s_palet.setSelection(2);

        tarih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog ddialog = new DatePickerDialog(StokAylik.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, datepicker, year, month, day);
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
    }


    class As_toplam{
        public String toplam(EditText t1,EditText t2){
            String sonuc;
            Double a=Double.valueOf(t1.getText().toString());
            Double b=Double.valueOf(t2.getText().toString());
            sonuc=String.format("%.1f",a*b);
            sonuc=kes(sonuc);
            return sonuc;
        }
        public String kes(String s){
            String son;
            String [] k=s.split(",");
            String i=k[0];
            String j=k[1];
            if(Integer.parseInt(j)==0){
                son=i.replace(",",".");
            }else{
                son=s.replace(",",".");
            }
            return son;
        }
    }
}
