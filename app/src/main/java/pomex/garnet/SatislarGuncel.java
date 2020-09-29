package pomex.garnet;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Contacts;
import android.util.Log;
import android.widget.TextView;

import com.parse.CountCallback;
import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SatislarGuncel extends AppCompatActivity {
    private TextView g3060,g80,g180,gtop,s77,s525,stop,r75;
    private Calendar calendar1,calendar2;
    private Date start,finish;
    private int silobas1,silobas2,silobas3;
    private double bigbag1,bigbag2,bigbag3;
    private ArrayList<Integer> list,s_list,b_list,t_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_satislar_guncel);
        getSupportActionBar().setTitle("Güncel Satışlar");

        g3060=findViewById(R.id.tv_sg_g_3060);
        g80=findViewById(R.id.tv_sg_g_80);
        g180=findViewById(R.id.tv_sg_g_180);
        gtop=findViewById(R.id.tv_sg_g_top);
        s77=findViewById(R.id.tv_sg_s_770);
        s525=findViewById(R.id.tv_sg_s_525);
        stop=findViewById(R.id.tv_sg_s_top);
        r75=findViewById(R.id.tv_sg_r_75);
        list=new ArrayList<>();
        s_list=new ArrayList<>();
        b_list=new ArrayList<>();


        calendar1=Calendar.getInstance();
        calendar1.set(Calendar.DAY_OF_MONTH,1);
        calendar1.set(Calendar.HOUR,0);
        calendar1.set(Calendar.MINUTE,01);
        start=calendar1.getTime();

        calendar2=Calendar.getInstance();
        calendar2.set(Calendar.HOUR,23);
        calendar2.set(Calendar.MINUTE,59);
        finish=calendar2.getTime();
        Log.e("date2",String.valueOf(finish));


        ParseQuery<ParseObject> query1=ParseQuery.getQuery("GarnetSevkiyat");
        query1.whereEqualTo("Boyut","30/60 Mesh");
        query1.whereGreaterThanOrEqualTo("Tarih",start);
        query1.whereLessThanOrEqualTo("Tarih",finish);
        query1.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e!=null){
                    e.printStackTrace();
                }else{
                    for (ParseObject obj:objects){
                        list.add(obj.getInt("Adet"));
                    }
                }
                g3060.setText(String.valueOf(topla(list)));
                list.clear();
            }
        });

        ParseQuery<ParseObject> query2=ParseQuery.getQuery("GarnetSevkiyat");
        query2.whereEqualTo("Boyut","80 Mesh");
        query2.whereGreaterThanOrEqualTo("Tarih",start);
        query2.whereLessThanOrEqualTo("Tarih",finish);
        query2.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e!=null){
                    e.printStackTrace();
                }else{
                    for (ParseObject obj:objects){
                        list.add(obj.getInt("Adet"));
                    }
                }
                g80.setText(String.valueOf(topla(list)));
                list.clear();
            }
        });

        ParseQuery<ParseObject> query3=ParseQuery.getQuery("GarnetSevkiyat");
        query3.whereEqualTo("Boyut","180 Mesh");
        query3.whereGreaterThanOrEqualTo("Tarih",start);
        query3.whereLessThanOrEqualTo("Tarih",finish);
        query3.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e!=null){
                    e.printStackTrace();
                }else{
                    for (ParseObject obj:objects){
                        list.add(obj.getInt("Adet"));
                    }
                }
                g180.setText(String.valueOf(topla(list)));
                list.clear();
            }
        });

        ParseQuery<ParseObject> query4=ParseQuery.getQuery("GarnetSevkiyat");
        query4.whereGreaterThanOrEqualTo("Tarih",start);
        query4.whereLessThanOrEqualTo("Tarih",finish);
        query4.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e!=null){
                    e.printStackTrace();
                }else{
                    for (ParseObject obj:objects){
                        list.add(obj.getInt("Adet"));
                    }
                }
                gtop.setText(String.valueOf(topla(list)));
                list.clear();
            }
        });

        ParseQuery<ParseObject> query5=ParseQuery.getQuery("SilisSevkiyat");
        query5.whereEqualTo("Boyut","-770 Mikron");
        query5.whereEqualTo("Paket","Silobas");
        query5.whereGreaterThanOrEqualTo("Tarih",start);
        query5.whereLessThanOrEqualTo("Tarih",finish);
        query5.countInBackground(new CountCallback() {
            @Override
            public void done(int count, ParseException e) {
                if (e!=null){
                    e.printStackTrace();
                }else{
                    silobas1=count*27;
                }
            }
        });

        ParseQuery<ParseObject> query6=ParseQuery.getQuery("SilisSevkiyat");
        query6.whereEqualTo("Boyut","-770 Mikron");
        query6.whereEqualTo("Paket","Bigbag");
        query6.whereGreaterThanOrEqualTo("Tarih",start);
        query6.whereLessThanOrEqualTo("Tarih",finish);
        query6.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e!=null){
                    e.printStackTrace();
                }else{
                    for (ParseObject obj:objects){
                        b_list.add(obj.getInt("Miktar"));
                    }
                }
                bigbag1=(int) topla(b_list)*1.6;
            }
        });

        ParseQuery<ParseObject> query7=ParseQuery.getQuery("SilisSevkiyat");
        query7.whereEqualTo("Boyut","500-2500 Mikron");
        query7.whereEqualTo("Paket","Silobas");
        query7.whereGreaterThanOrEqualTo("Tarih",start);
        query7.whereLessThanOrEqualTo("Tarih",finish);
        query7.countInBackground(new CountCallback() {
            @Override
            public void done(int count, ParseException e) {
                if (e!=null){
                    e.printStackTrace();
                }else{
                    silobas2=count*27;
                }
            }
        });

        ParseQuery<ParseObject> query8=ParseQuery.getQuery("SilisSevkiyat");
        query8.whereEqualTo("Boyut","500-2500 Mikron");
        query8.whereEqualTo("Paket","Bigbag");
        query8.whereGreaterThanOrEqualTo("Tarih",start);
        query8.whereLessThanOrEqualTo("Tarih",finish);
        query8.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e!=null){
                    e.printStackTrace();
                }else{
                    for (ParseObject obj:objects){
                        s_list.add(obj.getInt("Miktar"));
                    }
                }
                bigbag2=(int) topla(s_list)*1.6;
            }
        });

        A_task a_task=new A_task();
        a_task.execute();
        B_task b_task=new B_task();
        b_task.execute();
        C_task c_task=new C_task();
        c_task.execute();
    }

    public int topla(ArrayList<Integer> list){
        int sonuc=0;
        for (int i=0;i<list.size();i++){
            sonuc=sonuc+list.get(i);
        }
        return sonuc;
    }

    public class A_task extends AsyncTask<Void,Double,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            double str=silobas1+bigbag1;
            publishProgress(str);
            return null;
        }

        @Override
        protected void onProgressUpdate(Double... values) {
            s77.setText(String.format("%.1f",values[0]).replace(",","."));
            super.onProgressUpdate(values);
        }
    }

    public class B_task extends AsyncTask<Void,Double,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            double str=silobas2+bigbag2;
            publishProgress(str);
            return null;
        }

        @Override
        protected void onProgressUpdate(Double... values) {
            s525.setText(String.format("%.1f",values[0]).replace(",","."));
            super.onProgressUpdate(values);
        }
    }

    public class C_task extends AsyncTask<Void,Double,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            double str=Double.valueOf(s77.getText().toString())+Double.valueOf(s525.getText().toString());
            publishProgress(str);
            return null;
        }

        @Override
        protected void onProgressUpdate(Double... values) {
            stop.setText(String.valueOf(values[0]));
            super.onProgressUpdate(values);
        }
    }

}
