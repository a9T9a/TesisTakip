package pomex.garnet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class VardiyaListe extends AppCompatActivity {
    private ArrayList<String>tarih,vardiya;
    private ArrayAdapter<String> t_adp,v_adp;
    private ArrayList<Integer> no;
    private ArrayAdapter<Integer> n_adp;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vardiya_liste);
        getSupportActionBar().hide();

        listView=findViewById(R.id.lv_vardlist);
        no=new ArrayList<>();
        tarih=new ArrayList<>();
        vardiya=new ArrayList<>();

        ParseQuery<ParseObject> query=ParseQuery.getQuery("VardiyaBilgi");
        query.orderByDescending("No");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e!=null){
                    e.printStackTrace();
                }else{
                    for (ParseObject object:objects){
                        no.add(object.getInt("No"));
                        tarih.add(String.valueOf(object.getDate("Tarih").getDate()+"/"+String.valueOf(object.getDate("Tarih").getMonth()+1)+"/"+String.valueOf(object.getDate("Tarih").getYear()+1900)));
                        vardiya.add(object.getString("Vardiya"));
                    }
                }
                Customvardiyalistadp adp=new Customvardiyalistadp();
                listView.setAdapter(adp);
                listView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,no.size()*100));
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent =new Intent(VardiyaListe.this,VardiyaBilgiGoster.class);
                intent.putExtra("No",no.get(i));
                startActivity(intent);
            }
        });
    }

    class Customvardiyalistadp extends BaseAdapter {

        @Override
        public int getCount() {
            return no.size();
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
            view=getLayoutInflater().inflate(R.layout.custom_vardiya,null);

            TextView c_tarih=view.findViewById(R.id.tv_cvard_tarih);
            TextView c_vardiya=view.findViewById(R.id.tv_cvard_vard);

            c_tarih.setText(tarih.get(i));
            c_vardiya.setText(vardiya.get(i));

            return view;
        }
    }
}
