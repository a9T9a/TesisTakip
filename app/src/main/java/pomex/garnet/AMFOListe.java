package pomex.garnet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Date;
import java.util.FormatFlagsConversionMismatchException;
import java.util.List;

public class AMFOListe extends AppCompatActivity {
    private ListView listView;
    private ArrayList<String> list,saat;
    private ArrayList<Integer> agirm,komur;
    private Button ss;
    private EditText s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amfoliste);
        getSupportActionBar().hide();

        listView=findViewById(R.id.lv_amfolist);
        ss=findViewById(R.id.button_amfol_goster);
        s=findViewById(R.id.et_amfoliste_ss);

        list=new ArrayList<>();
        saat=new ArrayList<>();
        agirm=new ArrayList<>();
        komur=new ArrayList<>();

        ParseQuery<ParseObject>query=ParseQuery.getQuery("AMFirinOlcum");
        query.setLimit(Integer.valueOf(s.getText().toString()));
        query.orderByDescending("Tarih");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e!=null){
                    e.printStackTrace();
                }else{
                    for (ParseObject object:objects){
                        Date date=object.getDate("Tarih");
                        list.add(date.getDate()+"."+(date.getMonth()+1)+"."+(date.getYear()+1900));
                        saat.add(object.getString("Saat"));
                        agirm.add(object.getInt("Malzeme"));
                        komur.add(object.getInt("Komur"));
                    }
                }
                C_olcum c_olcum=new C_olcum();
                listView.setAdapter(c_olcum);
            }
        });

        ss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!s.getText().toString().isEmpty()&&list.size()<Integer.valueOf(s.getText().toString())){
                    ParseQuery<ParseObject>query=ParseQuery.getQuery("AMFirinOlcum");
                    query.setSkip(list.size());
                    query.setLimit(Integer.valueOf(s.getText().toString())-list.size());
                    query.orderByDescending("Tarih");
                    query.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            if (e!=null){
                                e.printStackTrace();
                            }else{
                                for (ParseObject object:objects){
                                    Date date=object.getDate("Tarih");
                                    list.add(date.getDate()+"."+(date.getMonth()+1)+"."+(date.getYear()+1900));
                                    saat.add(object.getString("Saat"));
                                    agirm.add(object.getInt("Malzeme"));
                                    komur.add(object.getInt("Komur"));
                                }
                            }
                            C_olcum c_olcum=new C_olcum();
                            listView.setAdapter(c_olcum);
                        }
                    });
                }
            }
        });
    }

    class C_olcum extends BaseAdapter{

        @Override
        public int getCount() {
            return list.size();
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
            view=getLayoutInflater().inflate(R.layout.custom_olcum,null);
            TextView tv_tarih=view.findViewById(R.id.tv_colcum_tarih);
            TextView tv_saat=view.findViewById(R.id.tv_colcum_saat);
            TextView tv_malzeme=view.findViewById(R.id.tv_colcum_agirm);
            TextView tv_komur=view.findViewById(R.id.tv_colcum_komur);

            tv_tarih.setText(String.valueOf(list.get(i)));
            tv_saat.setText(String.valueOf(saat.get(i)));
            tv_malzeme.setText(String.valueOf(agirm.get(i).toString()));
            tv_komur.setText(String.valueOf(komur.get(i).toString()));

            return view;
        }
    }
}
