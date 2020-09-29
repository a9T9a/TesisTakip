package pomex.garnet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.ArrayList;

public class g80Duzenle extends AppCompatActivity {

    private EditText BigbagNo,E400,E212,E160,Not;
    private String id;
    private CheckBox cbk,cbb;
    private AutoCompleteTextView Firma;
    private Button Duzelt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_g80_duzenle);
        getSupportActionBar().hide();

        BigbagNo=findViewById(R.id.et80giris_bigbagno_d);
        E400=findViewById(R.id.et80giris_400_d);
        E212=findViewById(R.id.et80giris_212_d);
        E160=findViewById(R.id.et80giris_160_d);
        Firma=findViewById(R.id.atv80giris_firma_d);
        Not=findViewById(R.id.et80giris_not_d);
        cbk=findViewById(R.id.cb80giris_kraft_d);
        cbb=findViewById(R.id.cb80giris_bigbag_d);
        Duzelt=findViewById(R.id.butg80giris3_d);

        id=getIntent().getStringExtra("Id");

        ParseQuery<ParseObject> query=ParseQuery.getQuery("G80MeshBigbag");
        query.getInBackground(id, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e!=null){
                    e.printStackTrace();
                }else{
                    BigbagNo.setText(String.valueOf(object.getLong("BigbagNo")));
                    E400.setText(String.valueOf(object.getInt("ust400")));
                    E212.setText(String.valueOf(object.getInt("alt212")));
                    E160.setText(String.valueOf(object.getInt("alt160")));
                    Firma.setText(object.getString("Firma"));
                    Not.setText(object.getString("Not"));
                    if (object.getBoolean("Kraft")==true){
                        cbk.setChecked(true);
                    }
                    if (object.getBoolean("Bigbag")==true){
                        cbb.setChecked(true);
                    }

                }
            }
        });

        Duzelt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseQuery<ParseObject> query1=ParseQuery.getQuery("G80MeshBgbag");
                query1.getInBackground(id, new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        if (e!=null){
                            e.printStackTrace();
                        }else{
                            object.put("BigbagNo",Long.parseLong(BigbagNo.getText().toString()));
                            object.put("ust400",Double.parseDouble(E400.getText().toString()));
                            object.put("alt212",Double.parseDouble(E212.getText().toString()));
                            object.put("alt160",Double.parseDouble(E160.getText().toString()));
                            if (Firma.getText().toString()!=null){
                                object.put("Firma",Firma.getText().toString());
                            }
                            if (Not.getText().toString()!=null){
                                object.put("Not",Not.getText().toString());
                            }
                            if (cbb.isChecked()){
                                object.put("Bigbag",true);
                            }
                            if (cbk.isChecked()){
                                object.put("Kraft",true);
                            }
                            object.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e!=null){
                                        Toast.makeText(g80Duzenle.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                                    }else{
                                        Toast.makeText(g80Duzenle.this,"Düzeltildi",Toast.LENGTH_SHORT).show();
                                        Intent intent=new Intent(g80Duzenle.this,g80BigbagGiris.class);
                                        startActivity(intent);
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });
    }
}
