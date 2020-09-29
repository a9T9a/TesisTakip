package pomex.garnet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class KomurAG extends AppCompatActivity {
    private Button kamyon,numune;
    private ArrayList<String> tarih,firma,id;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_komur_ag);
        getSupportActionBar().hide();

        kamyon=findViewById(R.id.button_KAG_kamyon);
        numune=findViewById(R.id.button_KAG_Numune);
        listView=findViewById(R.id.lv_kag);
        tarih=new ArrayList<>();
        firma=new ArrayList<>();
        id=new ArrayList<>();

        kamyon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kamyon.setBackgroundColor(Color.rgb(130,220,200));
                numune.setBackgroundColor(Color.rgb(205,205,205));

                id.clear();
                tarih.clear();
                firma.clear();

                ParseQuery<ParseObject>query=ParseQuery.getQuery("KomurAnaliz");
                query.whereEqualTo("Numune",false);
                query.orderByDescending("Tarih");
                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if (e!=null){
                            e.printStackTrace();
                        }else{
                            for (ParseObject object:objects){
                                Date date =object.getDate("Tarih");
                                tarih.add(date.getDate()+"."+(date.getMonth()+1)+"."+(date.getYear()+1900));
                                firma.add(object.getString("Firma"));
                                id.add(object.getObjectId());
                            }
                        }
                        C_kag c_kag=new C_kag();
                        listView.setAdapter(c_kag);
                    }
                });

            }
        });
        numune.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numune.setBackgroundColor(Color.rgb(130,220,200));
                kamyon.setBackgroundColor(Color.rgb(205,205,205));

                id.clear();
                tarih.clear();
                firma.clear();

                ParseQuery<ParseObject>query=ParseQuery.getQuery("KomurAnaliz");
                query.whereEqualTo("Numune",true);
                query.orderByDescending("Tarih");
                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> objects, ParseException e) {
                        if (e!=null){
                            e.printStackTrace();
                        }else{
                            for (ParseObject object:objects){
                                Date date =object.getDate("Tarih");
                                tarih.add(date.getDate()+"."+(date.getMonth()+1)+"."+(date.getYear()+1900));
                                firma.add(object.getString("Firma"));
                                id.add(object.getObjectId());
                            }
                        }
                        C_kag c_kag=new C_kag();
                        listView.setAdapter(c_kag);
                    }
                });
            }
        });
    }

    class C_kag extends BaseAdapter{

        @Override
        public int getCount() {
            return tarih.size();
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
            view=getLayoutInflater().inflate(R.layout.custom_kag,null);
            TextView tv_tarih=view.findViewById(R.id.tv_ckag_tarih);
            TextView tv_firma=view.findViewById(R.id.tv_ckag_firma);

            tv_firma.setText(firma.get(i));
            tv_tarih.setText(tarih.get(i));

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent=new Intent(KomurAG.this,KomurGoster.class);
                    intent.putExtra("id_No",id.get(i));
                    startActivity(intent);
                }
            });

            return view;
        }
    }
}
