package pomex.garnet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class Kantar extends AppCompatActivity {
    private ListView listView_g,listView_s;
    private Button button_g;
    private ArrayList<String> tarih_g,saat_g,firma_g,boyut_g,paket_g,plaka_g,yukleyen_g;
    private ArrayList<String> tarih_s,saat_s,firma_s,boyut_s,paket_s,plaka_s,yukleyen_s;
    private ArrayList<Integer>miktar_g,miktar_s;
    private Date calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kantar);
        getSupportActionBar().hide();

        button_g=findViewById(R.id.button_kantar_g);
        listView_g=findViewById(R.id.lv_kantar);
        listView_s=findViewById(R.id.lv_kantar_s);

        tarih_g=new ArrayList<>();
        saat_g=new ArrayList<>();
        firma_g=new ArrayList<>();
        boyut_g=new ArrayList<>();
        miktar_g=new ArrayList<>();
        paket_g=new ArrayList<>();
        plaka_g=new ArrayList<>();
        yukleyen_g=new ArrayList<>();
        tarih_s=new ArrayList<>();
        saat_s=new ArrayList<>();
        firma_s=new ArrayList<>();
        boyut_s=new ArrayList<>();
        miktar_s=new ArrayList<>();
        paket_s=new ArrayList<>();
        plaka_s=new ArrayList<>();
        yukleyen_s=new ArrayList<>();

        calendar=Calendar.getInstance().getTime();
        calendar.setHours(0);
        calendar.setMinutes(1);
        calendar.setSeconds(0);
        Log.e("tarih",String.valueOf(calendar));

        listele();

        button_g.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 listele();
             }
         });
    }

    public void listele(){
        tarih_g.clear();
        saat_g.clear();
        firma_g.clear();
        boyut_g.clear();
        miktar_g.clear();
        paket_g.clear();
        plaka_g.clear();
        yukleyen_g.clear();
        tarih_s.clear();
        saat_s.clear();
        firma_s.clear();
        boyut_s.clear();
        miktar_s.clear();
        paket_s.clear();
        plaka_s.clear();
        yukleyen_s.clear();
        final ParseQuery<ParseObject>query=ParseQuery.getQuery("GarnetSevkiyat");
        query.whereGreaterThanOrEqualTo("Tarih",calendar);
        query.orderByDescending("Tarih");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e!=null){
                    e.printStackTrace();
                }else{
                    for (ParseObject object:objects){
                        tarih_g.add(String.valueOf(object.getDate("Tarih").getDate())+"."+(object.getDate("Tarih").getMonth()+1)+"."+(object.getDate("Tarih").getYear()+1900));
                        saat_g.add(object.getString("Saat"));
                        firma_g.add(object.getString("Firma"));
                        boyut_g.add(object.getString("Boyut"));
                        miktar_g.add(object.getInt("Adet"));
                        if (object.getBoolean("Kraft")==true){
                            paket_g.add(" Kraft + Bigbag");
                        }else{paket_g.add(" Bigbag");}
                        plaka_g.add(object.getString("Plaka"));
                        yukleyen_g.add(object.getString("Yukleyen"));
                    }
                }
                garnetsevklistadpg adp=new garnetsevklistadpg();
                listView_g.setAdapter(adp);
            }
        });

        final ParseQuery<ParseObject>query1=ParseQuery.getQuery("SilisSevkiyat");
        query1.whereGreaterThanOrEqualTo("Tarih",calendar);
        query1.orderByDescending("Tarih");
        query1.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e!=null){
                    e.printStackTrace();
                }else{
                    for (ParseObject object:objects){
                        tarih_s.add(String.valueOf(object.getDate("Tarih").getDate())+"."+(object.getDate("Tarih").getMonth()+1)+"."+(object.getDate("Tarih").getYear()+1900));
                        saat_s.add(object.getString("Saat"));
                        firma_s.add(object.getString("Firma"));
                        boyut_s.add(object.getString("Boyut"));
                        miktar_s.add(object.getInt("Miktar"));
                        paket_s.add(" "+object.getString("Paket"));
                        plaka_s.add(object.getString("Plaka"));
                        yukleyen_s.add(object.getString("Yukleyen"));
                    }
                }
                garnetsevklistadps adp=new garnetsevklistadps();
                listView_s.setAdapter(adp);
            }
        });
    }

    class garnetsevklistadpg extends BaseAdapter {

        @Override
        public int getCount() {
            return tarih_g.size();
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
            view=getLayoutInflater().inflate(R.layout.custom_kantar_garnet,null);

            TextView s_tarih=view.findViewById(R.id.ckg_tarih);
            TextView s_saat=view.findViewById(R.id.ckg_saat);
            TextView s_firma=view.findViewById(R.id.ckg_firma);
            TextView s_boyut=view.findViewById(R.id.ckg_boyut);
            TextView s_miktar=view.findViewById(R.id.ckg_miktar);
            TextView s_paket=view.findViewById(R.id.ckg_paket);
            TextView s_plaka=view.findViewById(R.id.ckg_plaka);
            TextView s_yukleyen=view.findViewById(R.id.ckg_yukleyen);

            s_tarih.setText(tarih_g.get(i));
            s_saat.setText(saat_g.get(i));
            s_firma.setText(firma_g.get(i));
            s_boyut.setText(boyut_g.get(i));
            s_miktar.setText(String.valueOf(miktar_g.get(i)));
            s_paket.setText(paket_g.get(i));
            s_plaka.setText(plaka_g.get(i));
            s_yukleyen.setText(yukleyen_g.get(i));

            return view;
        }
    }

    class garnetsevklistadps extends BaseAdapter {

        @Override
        public int getCount() {
            return tarih_s.size();
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
            view=getLayoutInflater().inflate(R.layout.custom_kantar_garnet,null);

            TextView s_tarih=view.findViewById(R.id.ckg_tarih);
            TextView s_saat=view.findViewById(R.id.ckg_saat);
            TextView s_firma=view.findViewById(R.id.ckg_firma);
            TextView s_boyut=view.findViewById(R.id.ckg_boyut);
            TextView s_miktar=view.findViewById(R.id.ckg_miktar);
            TextView s_paket=view.findViewById(R.id.ckg_paket);
            TextView s_plaka=view.findViewById(R.id.ckg_plaka);
            TextView s_yukleyen=view.findViewById(R.id.ckg_yukleyen);

            s_tarih.setText(tarih_s.get(i));
            s_saat.setText(saat_s.get(i));
            s_firma.setText(firma_s.get(i));
            s_boyut.setText(boyut_s.get(i));
            s_miktar.setText(String.valueOf(miktar_s.get(i)));
            s_paket.setText(paket_s.get(i));
            s_plaka.setText(plaka_s.get(i));
            s_yukleyen.setText(yukleyen_s.get(i));

            return view;
        }
    }
}
