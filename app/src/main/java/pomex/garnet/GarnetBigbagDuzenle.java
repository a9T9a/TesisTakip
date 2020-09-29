package pomex.garnet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class GarnetBigbagDuzenle extends AppCompatActivity {
    private EditText BigbagNo,A1,A2,A3,Firma,Not;
    private TextView aa1,aa2,aa3,Tarih;
    private CheckBox cbk,cbb,cbg;
    private Long bbno;
    private String srvr,id;
    private Calendar cal;
    private DatePickerDialog.OnDateSetListener datepicker;
    private DateFormat df;
    private Date date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garnet_bigbag_duzenle);
        getSupportActionBar().hide();

        BigbagNo=findViewById(R.id.etdzn_bigbagno);
        A1=findViewById(R.id.etdzn_a1);
        aa1=findViewById(R.id.tvdzn_2);
        A2=findViewById(R.id.etdzn_a2);
        aa2=findViewById(R.id.tvdzn_3);
        A3=findViewById(R.id.etdzn_a3);
        aa3=findViewById(R.id.tvdzn_4);
        Firma=findViewById(R.id.etdzn_firma);
        Not=findViewById(R.id.etdzn_not);
        Tarih=findViewById(R.id.tvdzn_tarih);
        cbk=findViewById(R.id.cbdzn_kraft);
        cbb=findViewById(R.id.cbdzn_bigbag);
        cbg=findViewById(R.id.cbdzn_gitti);

        bbno=getIntent().getLongExtra("BigbagNo",0);
        BigbagNo.setText(String.valueOf(bbno));

        srvr=String.valueOf(bbno).substring(0,2);

        if (srvr.equalsIgnoreCase("80")){
            aa1.setText("+400");
            aa2.setText("-212");
            aa3.setText("-160");

            ParseQuery<ParseObject> query=ParseQuery.getQuery("G80MeshBigbag");
            query.whereEqualTo("BigbagNo",bbno);
            query.getFirstInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {
                    A1.setText(String.valueOf(object.getDouble("ust400")));
                    A2.setText(String.valueOf(object.getDouble("alt212")));
                    A3.setText(String.valueOf(object.getDouble("alt160")));
                    Firma.setText(object.getString("Firma"));
                    Not.setText(object.getString("Not"));
                    if (object.getBoolean("Gitti")==true){
                        cbg.setChecked(true);
                    }else{cbg.setChecked(false);}
                    if (object.getDate("Tarih")!=null){
                        Tarih.setText(String.valueOf(object.getDate("Tarih").getDate())+"/"+String.valueOf(object.getDate("Tarih").getMonth()+1)+"/"+String.valueOf(object.getDate("Tarih").getYear()+1900));
                        date=object.getDate("Tarih");
                    }
                    if (object.getBoolean("Kraft")==true){
                        cbk.setChecked(true);
                    }else {cbk.setChecked(false);}
                    if (object.getBoolean("Bigbag")==true){
                        cbb.setChecked(true);
                    }else{cbb.setChecked(false);}
                }
            });
        }else if (srvr.equalsIgnoreCase("18")){
            aa1.setText("+212");
            aa2.setText("+160");
            aa3.setText("-90");

            ParseQuery<ParseObject> query=ParseQuery.getQuery("G180MeshBigbag");
            query.whereEqualTo("BigbagNo",bbno);
            query.getFirstInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {
                    A1.setText(String.valueOf(object.getDouble("ust212")));
                    A2.setText(String.valueOf(object.getDouble("ust160")));
                    A3.setText(String.valueOf(object.getDouble("alt90")));
                    Firma.setText(object.getString("Firma"));
                    Not.setText(object.getString("Not"));
                    if (object.getBoolean("Gitti")==true){
                        cbg.setChecked(true);
                    }else{cbg.setChecked(false);}
                    if (object.getDate("Tarih")!=null){
                        Tarih.setText(String.valueOf(object.getDate("Tarih").getDate())+"/"+String.valueOf(object.getDate("Tarih").getMonth()+1)+"/"+String.valueOf(object.getDate("Tarih").getYear()+1900));
                        date=object.getDate("Tarih");
                    }
                    if (object.getBoolean("Kraft")==true){
                        cbk.setChecked(true);
                    }else {cbk.setChecked(false);}
                    if (object.getBoolean("Bigbag")==true){
                        cbb.setChecked(true);
                    }else{cbb.setChecked(false);}
                }
            });
        }else if (srvr.equalsIgnoreCase("30")){
            aa1.setText("+600");
            aa2.setText("+400");
            aa3.setText("-212");

            ParseQuery<ParseObject> query=ParseQuery.getQuery("G3060MeshBigbag");
            query.whereEqualTo("BigbagNo",bbno);
            query.getFirstInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {
                    A1.setText(String.valueOf(object.getDouble("ust600")));
                    A2.setText(String.valueOf(object.getDouble("ust400")));
                    A3.setText(String.valueOf(object.getDouble("alt212")));
                    Firma.setText(object.getString("Firma"));
                    Not.setText(object.getString("Not"));
                    if (object.getBoolean("Gitti")==true){
                        cbg.setChecked(true);
                    }else{cbg.setChecked(false);}
                    if (object.getDate("Tarih")!=null){
                        Tarih.setText(String.valueOf(object.getDate("Tarih").getDate())+"/"+String.valueOf(object.getDate("Tarih").getMonth()+1)+"/"+String.valueOf(object.getDate("Tarih").getYear()+1900));
                        date=object.getDate("Tarih");
                    }
                    if (object.getBoolean("Kraft")==true){
                        cbk.setChecked(true);
                    }else {cbk.setChecked(false);}
                    if (object.getBoolean("Bigbag")==true){
                        cbb.setChecked(true);
                    }else{cbb.setChecked(false);}
                }
            });
        }

        Tarih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cal= Calendar.getInstance();
                int year=cal.get(Calendar.YEAR);
                int month=cal.get(Calendar.MONTH);
                int day=cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog ddialog=new DatePickerDialog(GarnetBigbagDuzenle.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,datepicker,year,month,day);
                ddialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                ddialog.show();
            }
        });
        datepicker =new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month=month+1;
                Tarih.setText(day+"/"+month+"/"+year);
                df=new SimpleDateFormat("dd/MM/yyyy");
                try {
                    date=df.parse(Tarih.getText().toString());
                    cbg.setChecked(true);
                }catch(java.text.ParseException e)
                {e.printStackTrace();}
            }
        };
    }

    public void Guncelle(View view){
        if (srvr.equalsIgnoreCase("80")){
            ParseQuery<ParseObject> query=ParseQuery.getQuery("G80MeshBigbag");
            query.whereEqualTo("BigbagNo",bbno);
            query.getFirstInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {
                    if (e!=null){
                        e.printStackTrace();
                    }else{
                        id=object.getObjectId();
                        ParseQuery<ParseObject>qq=ParseQuery.getQuery("G80MeshBigbag");
                        qq.getInBackground(id, new GetCallback<ParseObject>() {
                            @Override
                            public void done(ParseObject object, ParseException e) {
                                if (e!=null){
                                    e.printStackTrace();
                                }else{
                                    object.put("BigbagNo",Long.parseLong(BigbagNo.getText().toString()));
                                    object.put("ust400",Double.parseDouble(A1.getText().toString()));
                                    object.put("alt212", Double.parseDouble(A2.getText().toString()));
                                    object.put("alt160", Double.parseDouble(A3.getText().toString()));
                                    if (!Firma.getText().toString().isEmpty()) {
                                        object.put("Firma", Firma.getText().toString());
                                    }
                                    if (!Not.getText().toString().isEmpty()) {
                                        object.put("Not", Not.getText().toString());
                                    }
                                    if (!Tarih.getText().toString().isEmpty()){
                                        object.put("Tarih",date);
                                    }
                                    if (cbb.isChecked()){
                                        object.put("Bigbag",true);
                                    }
                                    if (cbk.isChecked()){
                                        object.put("Kraft",true);
                                    }
                                    if (cbg.isChecked()){
                                        object.put("Gitti",true);
                                    }
                                    object.saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(ParseException e) {
                                            if (e!=null){
                                                Toast.makeText(getApplicationContext(),e.getLocalizedMessage()+" Bir Sorun Oldu",Toast.LENGTH_LONG).show();
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
        }
        else if (srvr.equalsIgnoreCase("18")){
            ParseQuery<ParseObject> query=ParseQuery.getQuery("G180MeshBigbag");
            query.whereEqualTo("BigbagNo",bbno);
            query.getFirstInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {
                    if (e!=null){
                        e.printStackTrace();
                    }else{
                        id=object.getObjectId();
                        ParseQuery<ParseObject>qq=ParseQuery.getQuery("G180MeshBigbag");
                        qq.getInBackground(id, new GetCallback<ParseObject>() {
                            @Override
                            public void done(ParseObject object, ParseException e) {
                                if (e!=null){
                                    e.printStackTrace();
                                }else{
                                    object.put("BigbagNo",Long.parseLong(BigbagNo.getText().toString()));
                                    object.put("ust212",Double.parseDouble(A1.getText().toString()));
                                    object.put("ust160", Double.parseDouble(A2.getText().toString()));
                                    object.put("alt90", Double.parseDouble(A3.getText().toString()));
                                    if (!Firma.getText().toString().isEmpty()) {
                                        object.put("Firma", Firma.getText().toString());
                                    }
                                    if (!Not.getText().toString().isEmpty()) {
                                        object.put("Not", Not.getText().toString());
                                    }
                                    if (!Tarih.getText().toString().isEmpty()){
                                        object.put("Tarih",date);
                                    }
                                    if (cbb.isChecked()){
                                        object.put("Bigbag",true);
                                    }
                                    if (cbk.isChecked()){
                                        object.put("Kraft",true);
                                    }
                                    if (cbg.isChecked()){
                                        object.put("Gitti",true);
                                    }
                                    object.saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(ParseException e) {
                                            if (e!=null){
                                                Toast.makeText(getApplicationContext(),"Bir Sorun Oldu",Toast.LENGTH_LONG).show();
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
        }
        else if (srvr.equalsIgnoreCase("30")){
            ParseQuery<ParseObject> query=ParseQuery.getQuery("G3060MeshBigbag");
            query.whereEqualTo("BigbagNo",bbno);
            query.getFirstInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {
                    if (e!=null){
                        e.printStackTrace();
                    }else{
                        id=object.getObjectId();
                        ParseQuery<ParseObject>qq=ParseQuery.getQuery("G3060MeshBigbag");
                        qq.getInBackground(id, new GetCallback<ParseObject>() {
                            @Override
                            public void done(ParseObject object, ParseException e) {
                                if (e!=null){
                                    e.printStackTrace();
                                }else{
                                    object.put("BigbagNo",Long.parseLong(BigbagNo.getText().toString()));
                                    object.put("ust600",Double.parseDouble(A1.getText().toString()));
                                    object.put("ust400", Double.parseDouble(A2.getText().toString()));
                                    object.put("alt212", Double.parseDouble(A3.getText().toString()));
                                    if (!Firma.getText().toString().isEmpty()) {
                                        object.put("Firma", Firma.getText().toString());
                                    }
                                    if (!Not.getText().toString().isEmpty()) {
                                        object.put("Not", Not.getText().toString());
                                    }
                                    if (!Tarih.getText().toString().isEmpty()){
                                        object.put("Tarih",date);
                                    }
                                    if (cbb.isChecked()){
                                        object.put("Bigbag",true);
                                    }
                                    if (cbk.isChecked()){
                                        object.put("Kraft",true);
                                    }
                                    if (cbg.isChecked()){
                                        object.put("Gitti",true);
                                    }
                                    object.saveInBackground(new SaveCallback() {
                                        @Override
                                        public void done(ParseException e) {
                                            if (e!=null){
                                                Toast.makeText(getApplicationContext(),"Bir Sorun Oldu",Toast.LENGTH_LONG).show();
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
        }
    }
}
