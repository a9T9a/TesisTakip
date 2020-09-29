package pomex.garnet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.CountCallback;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.FormatFlagsConversionMismatchException;
import java.util.List;

public class AnaMenu extends AppCompatActivity {
    private ImageButton lab,sevk,stok,vardiya;
    private ImageView satis;
    private TextView giden80,stok80,bos80,giden180,stok180,bos180,giden36,stok36,bos36,top_g,top_s,top_b,gecen_ay;
    private Calendar calendar1,calendar2;
    private Date start,finish;
    private Integer s1,s2,s3,g1,g2,g3,b1,b2,b3;
    private ArrayList<Integer> miktar;
    private Integer top,izin;
    private String u_name;
    private Button stoklar;
    private Date sd,fd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ana_menu);
        getSupportActionBar().hide();

        lab=findViewById(R.id.imgb_ana_lab);
        sevk=findViewById(R.id.imgb_ana_sevk);
        stok=findViewById(R.id.imgb_ana_stok);
        vardiya=findViewById(R.id.imgb_ana_vardiya);
        satis=findViewById(R.id.imgb_ana_satis);
        giden80=findViewById(R.id.tv_ana_80g);
        stok80=findViewById(R.id.tv_ana_80s);
        bos80=findViewById(R.id.tv_ana_80b);
        giden180=findViewById(R.id.tv_ana_180g);
        stok180=findViewById(R.id.tv_ana_180s);
        bos180=findViewById(R.id.tv_ana_180b);
        giden36=findViewById(R.id.tv_ana_3060g);
        stok36=findViewById(R.id.tv_ana_3060s);
        bos36=findViewById(R.id.tv_ana_3060b);
        top_g=findViewById(R.id.tv_ana_top_g);
        top_s=findViewById(R.id.tv_ana_top_s);
        top_b=findViewById(R.id.tv_ana_top_b);
        gecen_ay=findViewById(R.id.tv_ana_ga_g);
        stoklar=findViewById(R.id.button_ana_stoklar);

        miktar=new ArrayList<>();

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser.get("izin")==null){
            izin=0;
        }else{
            izin = (Integer) currentUser.get("izin");
        }

        calendar1=Calendar.getInstance();
        calendar1.set(Calendar.DAY_OF_MONTH,1);
        calendar1.set(Calendar.HOUR,0);
        calendar1.set(Calendar.MINUTE,01);
        start=calendar1.getTime();

        calendar2=Calendar.getInstance();
        calendar2.set(Calendar.DAY_OF_MONTH,Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar2.set(Calendar.HOUR,23);
        calendar2.set(Calendar.MINUTE,59);
        finish=calendar2.getTime();

        sd=Calendar.getInstance().getTime();
        sd.setDate(1);
        sd.setHours(0);
        sd.setMinutes(1);
        if (sd.getMonth()==0){
            sd.setMonth(11);
            sd.setYear(sd.getYear()-1);
        }else{
            sd.setMonth(sd.getMonth()-1);
        }

        fd=Calendar.getInstance().getTime();
        fd.setDate(1);
        fd.setHours(0);
        fd.setMinutes(0);
        fd.setSeconds(0);

        lab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AnaMenu.this,Lab.class);
                if (izin==1){
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"İzin Yok",Toast.LENGTH_SHORT).show();
                }
            }
        });

        sevk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AnaMenu.this,Sevkiyat.class);
                if (izin==0){
                    Toast.makeText(getApplicationContext(),"İzin Yok",Toast.LENGTH_SHORT).show();
                }else{
                    startActivity(intent);
                }
            }
        });

        stok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AnaMenu.this,Stok.class);
                if (izin==1){
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"İzin Yok",Toast.LENGTH_SHORT).show();
                }
            }
        });

        vardiya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AnaMenu.this,VardiyaGiris.class);
                if (izin==0){
                    Toast.makeText(getApplicationContext(),"İzin Yok",Toast.LENGTH_SHORT).show();
                }else{
                    startActivity(intent);
                }
            }
        });

        satis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final PopupMenu popupMenu=new PopupMenu(AnaMenu.this,satis);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu3,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId()==R.id.pop_guncel){
                            Intent intent=new Intent(AnaMenu.this,SatislarGuncel.class);
                            if (izin==0){
                                Toast.makeText(getApplicationContext(),"İzin Yok",Toast.LENGTH_SHORT).show();
                            }else{
                                startActivity(intent);
                            }
                        }else if(menuItem.getItemId()==R.id.pop_gecmis){
                            Intent intent=new Intent(AnaMenu.this,Satislar.class);
                            if (izin==0){
                                Toast.makeText(getApplicationContext(),"İzin Yok",Toast.LENGTH_SHORT).show();
                            }else{
                                startActivity(intent);
                            }
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });


        stoklar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stoklar.setClickable(false);
                try {
                    ParseQuery<ParseObject> query80g=ParseQuery.getQuery("G80MeshBigbag");
                    query80g.whereGreaterThanOrEqualTo("Tarih",start);
                    query80g.whereLessThanOrEqualTo("Tarih",finish);
                    query80g.whereEqualTo("Gitti",true);
                    query80g.countInBackground(new CountCallback() {
                        @Override
                        public void done(int count, ParseException e) {
                            if (e!=null){
                                e.printStackTrace();
                            }else{
                                giden80.setText(String.valueOf(count));
                                g1=count;
                            }
                        }
                    });

                    ParseQuery<ParseObject> query80s=ParseQuery.getQuery("G80MeshBigbag");
                    query80s.whereNotEqualTo("Firma","Tekrar Beslendi");
                    query80s.whereEqualTo("Gitti",false);
                    query80s.whereEqualTo("Gitti",null);
                    query80s.countInBackground(new CountCallback() {
                        @Override
                        public void done(int count, ParseException e) {
                            if (e!=null){
                                e.printStackTrace();
                            }else{
                                stok80.setText(String.valueOf(count));
                                s1=count;
                            }
                        }
                    });

                    ParseQuery<ParseObject> query80b=ParseQuery.getQuery("G80MeshBigbag");
                    query80b.whereEqualTo("Gitti",false);
                    query80b.whereEqualTo("Gitti",null);
                    query80b.whereEqualTo("Firma",null);
                    query80b.countInBackground(new CountCallback() {
                        @Override
                        public void done(int count, ParseException e) {
                            if (e!=null){
                                e.printStackTrace();
                            }else{
                                bos80.setText(String.valueOf(count));
                                b1=count;
                            }
                        }
                    });

                    ParseQuery<ParseObject> query180g=ParseQuery.getQuery("G180MeshBigbag");
                    query180g.whereGreaterThanOrEqualTo("Tarih",start);
                    query180g.whereLessThanOrEqualTo("Tarih",finish);
                    query180g.whereEqualTo("Gitti",true);
                    query180g.countInBackground(new CountCallback() {
                        @Override
                        public void done(int count, ParseException e) {
                            if (e!=null){
                                e.printStackTrace();
                            }else{
                                giden180.setText(String.valueOf(count));
                                g2=count;
                            }
                        }
                    });

                    ParseQuery<ParseObject> query180s=ParseQuery.getQuery("G180MeshBigbag");
                    query180s.whereNotEqualTo("Firma","Tekrar Beslendi");
                    query180s.whereEqualTo("Gitti",false);
                    query180s.whereEqualTo("Gitti",null);
                    query180s.countInBackground(new CountCallback() {
                        @Override
                        public void done(int count, ParseException e) {
                            if (e!=null){
                                e.printStackTrace();
                            }else{
                                stok180.setText(String.valueOf(count));
                                s2=count;
                            }
                        }
                    });

                    ParseQuery<ParseObject> query180b=ParseQuery.getQuery("G180MeshBigbag");
                    query180b.whereEqualTo("Gitti",false);
                    query180b.whereEqualTo("Gitti",null);
                    query180b.whereEqualTo("Firma",null);
                    query180b.countInBackground(new CountCallback() {
                        @Override
                        public void done(int count, ParseException e) {
                            if (e!=null){
                                e.printStackTrace();
                            }else{
                                bos180.setText(String.valueOf(count));
                                b2=count;
                            }
                        }
                    });

                    ParseQuery<ParseObject> query36g=ParseQuery.getQuery("G3060MeshBigbag");
                    query36g.whereGreaterThanOrEqualTo("Tarih",start);
                    query36g.whereLessThanOrEqualTo("Tarih",finish);
                    query36g.whereEqualTo("Gitti",true);
                    query36g.countInBackground(new CountCallback() {
                        @Override
                        public void done(int count, ParseException e) {
                            if (e!=null){
                                e.printStackTrace();
                            }else{
                                giden36.setText(String.valueOf(count));
                                g3=count;
                            }
                        }
                    });

                    ParseQuery<ParseObject> query36s=ParseQuery.getQuery("G3060MeshBigbag");
                    query36s.whereNotEqualTo("Firma","Tekrar Beslendi");
                    query36s.whereEqualTo("Gitti",false);
                    query36s.whereEqualTo("Gitti",null);
                    query36s.countInBackground(new CountCallback() {
                        @Override
                        public void done(int count, ParseException e) {
                            if (e!=null){
                                e.printStackTrace();
                            }else{
                                stok36.setText(String.valueOf(count));
                                s3=count;
                            }
                        }
                    });

                    ParseQuery<ParseObject> query36b=ParseQuery.getQuery("G3060MeshBigbag");
                    query36b.whereEqualTo("Gitti",false);
                    query36b.whereEqualTo("Gitti",null);
                    query36b.whereEqualTo("Firma",null);
                    query36b.countInBackground(new CountCallback() {
                        @Override
                        public void done(int count, ParseException e) {
                            if (e!=null){
                                e.printStackTrace();
                            }else{
                                bos36.setText(String.valueOf(count));
                                b3=count;
                            }
                        }
                    });

                    ParseQuery<ParseObject> query_ga=ParseQuery.getQuery("GarnetSevkiyat");
                    query_ga.whereGreaterThanOrEqualTo("Tarih",sd);
                    query_ga.whereLessThanOrEqualTo("Tarih",fd);
                    query_ga.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            if(e==null){
                                for (ParseObject object:objects){
                                    miktar.add(object.getInt("Adet"));
                                }
                            }else{e.printStackTrace();}
                            top=0;
                            for (int i=0;i<miktar.size();i++){
                                top=top+miktar.get(i);
                            }
                            gecen_ay.setText(String.valueOf(top));
                        }
                    });

                    A_giden a_giden=new A_giden();
                    a_giden.execute();

                    A_stok a_stok=new A_stok();
                    a_stok.execute();

                    A_bos a_bos=new A_bos();
                    a_bos.execute();
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(),"Bağlantı Problemi Tekrar Deneyin",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public class A_giden extends AsyncTask<Void,Integer,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(1200);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            Integer str=g1+g2+g3;
            publishProgress(str);
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            top_g.setText(String.valueOf(values[0]));
            super.onProgressUpdate(values);
        }
    }

    public class A_stok extends AsyncTask<Void,Integer,Void>{

         @Override
         protected Void doInBackground(Void... voids) {
             try {
                 Thread.sleep(1000);
             }catch (InterruptedException e){
                 e.printStackTrace();
             }
             Integer str=s1+s2+s3;
             publishProgress(str);
             return null;
         }

         @Override
         protected void onProgressUpdate(Integer... values) {
             top_s.setText(String.valueOf(values[0]));
             super.onProgressUpdate(values);
         }
     }

    public class A_bos extends AsyncTask<Void,Integer,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            Integer str=b1+b2+b3;
            publishProgress(str);
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            top_b.setText(String.valueOf(values[0]));
            super.onProgressUpdate(values);
        }
    }
}
