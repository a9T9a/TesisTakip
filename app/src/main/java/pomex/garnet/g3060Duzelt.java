package pomex.garnet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

public class g3060Duzelt extends AppCompatActivity {
    private EditText BigbagNo,E600,E400,E212,Not;
    private String id;
    private CheckBox cbk,cbb;
    private AutoCompleteTextView Firma;
    private Button Duzelt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_g3060_duzelt);
        getSupportActionBar().hide();

        BigbagNo=findViewById(R.id.et3060giris_bigbagno_d);
        E600=findViewById(R.id.et3060giris_600_d);
        E400=findViewById(R.id.et3060giris_400_d);
        E212=findViewById(R.id.et3060giris_212_d);
        Firma=findViewById(R.id.atv3060giris_firma_d);
        Not=findViewById(R.id.et3060giris_not_d);
        cbk=findViewById(R.id.cb3060giris_kraft_d);
        cbb=findViewById(R.id.cb3060giris_bigbag_d);
        Duzelt=findViewById(R.id.button_g3060_duzelt);

        id=getIntent().getStringExtra("Id");

        ParseQuery<ParseObject> query=ParseQuery.getQuery("G3060MeshBigbag");
        query.getInBackground(id, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e!=null){
                    e.printStackTrace();
                }else{
                    BigbagNo.setText(String.valueOf(object.getLong("BigbagNo")));
                    E600.setText(String.valueOf(object.getInt("ust600")));
                    E400.setText(String.valueOf(object.getInt("ust400")));
                    E212.setText(String.valueOf(object.getInt("alt212")));
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

    }

    public void duzelt(View view){
        ParseQuery<ParseObject> query1=ParseQuery.getQuery("G3060MeshBigbag");
        query1.getInBackground(id, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e!=null){
                    e.printStackTrace();
                }else{
                    object.put("BigbagNo",Long.parseLong(BigbagNo.getText().toString()));
                    object.put("ust600",Double.parseDouble(E600.getText().toString()));
                    object.put("ust400",Double.parseDouble(E400.getText().toString()));
                    object.put("alt212",Double.parseDouble(E212.getText().toString()));
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
                                Toast.makeText(g3060Duzelt.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(g3060Duzelt.this,"Düzeltildi",Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(g3060Duzelt.this,g3060BigbagGiris.class);
                                startActivity(intent);
                                startActivity(intent);
                            }
                        }
                    });
                }
            }
        });
    }
}
