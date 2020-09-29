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

public class AMUDagilimList extends AppCompatActivity {
    private ListView listView;
    private ArrayList<String> tarihler;
    private ArrayList<String> no;
    private ArrayAdapter<String> adp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amudagilim_list);
        getSupportActionBar().hide();

        listView=findViewById(R.id.lv_amudlist);
        tarihler=new ArrayList<>();
        no=new ArrayList<>();
        adp=new ArrayAdapter<String>(AMUDagilimList.this,android.R.layout.simple_list_item_1,tarihler);

        ParseQuery<ParseObject> query=ParseQuery.getQuery("AMUDagilim");
        query.orderByDescending("Tarih");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e!=null){
                    e.printStackTrace();
                }else {
                    for (ParseObject object:objects){
                        Date date=object.getDate("Tarih");
                        tarihler.add(date.getDate()+"."+(date.getMonth()+1)+"."+(date.getYear()+1900));
                        no.add(object.getObjectId());
                    }
                }
                listView.setAdapter(adp);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(AMUDagilimList.this,AMUDGoster.class);
                intent.putExtra("No",no.get(i));
                startActivity(intent);
            }
        });
    }
}
