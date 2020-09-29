package pomex.garnet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AMUDGoster extends AppCompatActivity {
    private TextView tarih,saat,g1020,g2040,g3060,g80,g180,toz,manyetit,iri2000,ince2000,irirutil,ortarutil,incerutil;
    private TextView g1020o,g2040o,g3060o,g80o,g180o,tozo,manyetito,iri2000o,ince2000o,irirutilo,ortarutilo,incerutilo;
    private Button ortalama;
    private String  no;
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amudgoster);
        getSupportActionBar().hide();

        ortalama=findViewById(R.id.button_amudg_ort);
        layout=findViewById(R.id.Llayout);

        tarih=findViewById(R.id.tv_amudg_tarih);
        saat=findViewById(R.id.tv_amudg_saat);
        g1020=findViewById(R.id.tv_amudg_y_1020);
        g2040=findViewById(R.id.tv_amudg_y_2040);
        g3060=findViewById(R.id.tv_amudg_y_3060);
        g80=findViewById(R.id.tv_amudg_y_80);
        g180=findViewById(R.id.tv_amudg_y_180);
        toz=findViewById(R.id.tv_amudg_y_toz);
        manyetit=findViewById(R.id.tv_amudg_y_man);
        iri2000=findViewById(R.id.tv_amudg_y_iri2);
        ince2000=findViewById(R.id.tv_amudg_y_ince2);
        irirutil=findViewById(R.id.tv_amudg_y_irir);
        ortarutil=findViewById(R.id.tv_amudg_y_ortar);
        incerutil=findViewById(R.id.tv_amudg_y_incer);

        g1020o=findViewById(R.id.tv_amudg_y2_1020);
        g2040o=findViewById(R.id.tv_amudg_y2_2040);
        g3060o=findViewById(R.id.tv_amudg_y2_3060);
        g80o=findViewById(R.id.tv_amudg_y2_80);
        g180o=findViewById(R.id.tv_amudg_y2_180);
        tozo=findViewById(R.id.tv_amudg_y2_toz);
        manyetito=findViewById(R.id.tv_amudg_y2_man);
        iri2000o=findViewById(R.id.tv_amudg_y2_iri2);
        ince2000o=findViewById(R.id.tv_amudg_y2_ince2);
        irirutilo=findViewById(R.id.tv_amudg_y2_irir);
        ortarutilo=findViewById(R.id.tv_amudg_y2_ortar);
        incerutilo=findViewById(R.id.tv_amudg_y2_incer);

        layout.setVisibility(layout.INVISIBLE);
        no= getIntent().getStringExtra("No");

        ParseQuery<ParseObject> query=ParseQuery.getQuery("AMUDagilim");
        query.getInBackground(no, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e!=null){
                    e.printStackTrace();
                }else{
                    Date date =object.getDate("Tarih");
                    tarih.setText(date.getDate()+"."+(date.getMonth()+1)+"."+(date.getYear()+1900));
                    saat.setText(object.getString("Saat"));
                    g1020.setText(String.valueOf(object.getDouble("g1020")));
                    g2040.setText(String.valueOf(object.getDouble("g2040")));
                    g3060.setText(String.valueOf(object.getDouble("g3060")));
                    g80.setText(String.valueOf(object.getDouble("g80")));
                    g180.setText(String.valueOf(object.getDouble("g180")));
                    toz.setText(String.valueOf(object.getDouble("toz")));
                    irirutil.setText(String.valueOf(object.getDouble("iri_rutil")));
                    ortarutil.setText(String.valueOf(object.getDouble("orta_rutil")));
                    incerutil.setText(String.valueOf(object.getDouble("ince_rutil")));
                    manyetit.setText(String.valueOf(object.getDouble("manyetit")));
                    iri2000.setText(String.valueOf(object.getDouble("iri2000")));
                    ince2000.setText(String.valueOf(object.getDouble("ince2000")));
                }
            }
        });
    }
    public void ort(View view){
        final ArrayList<Double> al_g1020,al_g2040,al_g3060,al_g80,al_g180,al_toz,al_irirutil,al_ortarutil,al_incerutil,al_manyetit,al_iri2000,al_ince2000;
        al_g1020=new ArrayList<>();
        al_g2040=new ArrayList<>();
        al_g3060=new ArrayList<>();
        al_g80=new ArrayList<>();
        al_g180=new ArrayList<>();
        al_toz=new ArrayList<>();
        al_irirutil=new ArrayList<>();
        al_ortarutil=new ArrayList<>();
        al_incerutil=new ArrayList<>();
        al_manyetit=new ArrayList<>();
        al_iri2000=new ArrayList<>();
        al_ince2000=new ArrayList<>();

        layout.setVisibility(layout.VISIBLE);

        ParseQuery<ParseObject>query=ParseQuery.getQuery("AMUDagilim");
        query.whereEqualTo("Dogruluk",true);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e!=null){
                    e.printStackTrace();
                }else{
                    for (ParseObject object:objects){
                        al_g1020.add(object.getDouble("g1020"));
                        al_g2040.add(object.getDouble("g2040"));
                        al_g3060.add(object.getDouble("g3060"));
                        al_g80.add(object.getDouble("g80"));
                        al_g180.add(object.getDouble("g180"));
                        al_toz.add(object.getDouble("toz"));
                        al_irirutil.add(object.getDouble("iri_rutil"));
                        al_ortarutil.add(object.getDouble("orta_rutil"));
                        al_incerutil.add(object.getDouble("ince_rutil"));
                        al_manyetit.add(object.getDouble("manyetit"));
                        al_iri2000.add(object.getDouble("iri2000"));
                        al_ince2000.add(object.getDouble("ince2000"));
                    }
                }
                g1020o.setText(String.format("%.1f",hesap(al_g1020)));
                g2040o.setText(String.format("%.1f",hesap(al_g2040)));
                g3060o.setText(String.format("%.1f",hesap(al_g3060)));
                g80o.setText(String.format("%.1f",hesap(al_g80)));
                g180o.setText(String.format("%.1f",hesap(al_g180)));
                tozo.setText(String.format("%.1f",hesap(al_toz)));
                irirutilo.setText(String.format("%.1f",hesap(al_irirutil)));
                ortarutilo.setText(String.format("%.1f",hesap(al_ortarutil)));
                incerutilo.setText(String.format("%.1f",hesap(al_incerutil)));
                manyetito.setText(String.format("%.1f",hesap(al_manyetit)));
                iri2000o.setText(String.format("%.1f",hesap(al_iri2000)));
                ince2000o.setText(String.format("%.1f",hesap(al_ince2000)));
            }
        });
    }

    public Double hesap(ArrayList<Double> list){
        Double sonuc=0.0;
        for (int i=0;i<list.size();i++){
            sonuc+=list.get(i);
        }
        sonuc=sonuc/list.size();
        return  sonuc;
    }
}
