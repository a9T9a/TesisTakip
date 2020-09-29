package pomex.garnet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AylikStokListe extends AppCompatActivity {

    private ArrayList<String> tarih,id;
    private ArrayAdapter<String> adp;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aylik_stok_liste);
        getSupportActionBar().hide();

        listView=findViewById(R.id.asl_listview);
        tarih=new ArrayList<>();
        id=new ArrayList<>();
        adp=new ArrayAdapter<String>(AylikStokListe.this,android.R.layout.simple_list_item_1,tarih);

        ParseQuery<ParseObject>query=ParseQuery.getQuery("AylikStok");
        query.orderByDescending("Tarih");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e!=null){
                    e.printStackTrace();
                }else{
                    for (ParseObject object:objects){
                        Date date=object.getDate("Tarih");
                        tarih.add(date.getDate()+"."+(date.getMonth()+1)+"."+(date.getYear()+1900));
                        id.add(object.getObjectId());
                    }
                }
                listView.setAdapter(adp);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(AylikStokListe.this,StokAylik.class);
                intent.putExtra("id",id.get(i));
                startActivity(intent);
            }
        });
    }
}
