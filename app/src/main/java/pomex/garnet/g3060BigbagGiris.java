package pomex.garnet;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class g3060BigbagGiris extends AppCompatActivity {

    private EditText BigbagNo,E600,E400,E212,Not,s;
    private Integer no;
    private CheckBox cbk,cbb;
    private AutoCompleteTextView Firma;
    private ArrayList<Long> bigbagno;
    private ArrayList<Double> anlz1,anlz2,anlz3;
    private ArrayList<String> firmalar,firma2,not,tarih,id,id2;
    private ArrayList<Integer> noo;
    private ArrayList<Boolean> gitti;
    private ArrayAdapter<String> firmaadp;
    private ListView listView;
    private Button yukle,goster;
    private Boolean sorun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_g3060_bigbag_giris);
        getSupportActionBar().hide();

        BigbagNo=findViewById(R.id.et3060giris_bigbagno);
        E600=findViewById(R.id.et3060giris_600);
        E400=findViewById(R.id.et3060giris_400);
        E212=findViewById(R.id.et3060giris_212);
        Firma=findViewById(R.id.atv3060giris_firma);
        Not=findViewById(R.id.et3060giris_not);
        cbk=findViewById(R.id.cb3060giris_kraft);
        cbb=findViewById(R.id.cb3060giris_bigbag);
        listView=findViewById(R.id.lv_3060giris);
        yukle=findViewById(R.id.butg3060giris);
        s=findViewById(R.id.et_g3060g_ss);
        goster=findViewById(R.id.button_g3060g_goster);

        BigbagNo.setText("3060");
        BigbagNo.setSelection(BigbagNo.getText().length());

        anlz1=new ArrayList<>();
        anlz2=new ArrayList<>();
        anlz3=new ArrayList<>();
        firma2=new ArrayList<>();
        not=new ArrayList<>();
        gitti=new ArrayList<>();
        tarih=new ArrayList<>();
        bigbagno=new ArrayList<>();
        firmalar=new ArrayList<>();
        id=new ArrayList<>();
        id2=new ArrayList<>();
        noo=new ArrayList<>();
        sorun=false;

        ParseQuery<ParseObject> qfirma = ParseQuery.getQuery("G3060MeshBigbag");
        qfirma.setLimit(Integer.valueOf(s.getText().toString()));
        qfirma.orderByDescending("No");
        qfirma.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e != null) {
                    e.printStackTrace();
                } else {
                    for (ParseObject object : objects) {
                        if (object.getString("Firma")!=null){
                            firmalar.add(object.getString("Firma"));
                        }
                        noo.add(object.getInt("No"));
                        id.add(object.getObjectId());
                        bigbagno.add(object.getLong("BigbagNo"));
                        anlz1.add(object.getDouble("ust600"));
                        anlz2.add(object.getDouble("ust400"));
                        anlz3.add(object.getDouble("alt212"));
                        firma2.add(object.getString("Firma"));
                        not.add(object.getString("Not"));
                        if (object.getBoolean("Gitti")==true){
                            gitti.add(true);
                        }else{
                            gitti.add(false);
                        }
                        if (object.getDate("Tarih")!=null){
                            tarih.add(String.valueOf(object.getDate("Tarih").getDate())+"/"+String.valueOf(object.getDate("Tarih").getMonth()+1)+"/"+String.valueOf(object.getDate("Tarih").getYear()+1900));
                        }else{
                            tarih.add("-");
                        }
                    }
                }
                customlist3060 adapter=new customlist3060();
                listView.setAdapter(adapter);
                listView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,bigbagno.size()*150));

                HashSet<String> hs2 = new HashSet<String>();
                hs2.addAll(firmalar);
                firmalar.clear();
                firmalar.addAll(hs2);
                Collections.sort(firmalar);

                firmaadp = new ArrayAdapter<String>(g3060BigbagGiris.this, R.layout.spinnerlayout, firmalar);
                Firma.setAdapter(firmaadp);
            }
        });

        ParseQuery<ParseObject> query=ParseQuery.getQuery("G3060MeshBigbag");
        query.orderByDescending("No");
        query.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                if (e!=null){e.printStackTrace();}
                else{
                    no=object.getInt("No");
                    no=no+1;
                }
            }
        });

        cbk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cbk.isChecked()){
                    cbb.setChecked(true);
                }
            }
        });

        goster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!s.getText().toString().isEmpty()&&bigbagno.size()<Integer.valueOf(s.getText().toString())){
                    ParseQuery<ParseObject>query=ParseQuery.getQuery("G3060MeshBigbag");
                    query.setSkip(bigbagno.size());
                    query.setLimit(Integer.valueOf(s.getText().toString())-bigbagno.size());
                    query.orderByDescending("No");
                    query.findInBackground(new FindCallback<ParseObject>() {
                        @Override
                        public void done(List<ParseObject> objects, ParseException e) {
                            if (e!=null){
                                e.printStackTrace();
                            }else{
                                for (ParseObject object:objects){
                                    if (object.getString("Firma")!=null){
                                        firmalar.add(object.getString("Firma"));
                                    }
                                    noo.add(object.getInt("No"));
                                    id.add(object.getObjectId());
                                    bigbagno.add(object.getLong("BigbagNo"));
                                    anlz1.add(object.getDouble("ust600"));
                                    anlz2.add(object.getDouble("ust400"));
                                    anlz3.add(object.getDouble("alt212"));
                                    firma2.add(object.getString("Firma"));
                                    not.add(object.getString("Not"));
                                    if (object.getBoolean("Gitti")==true){
                                        gitti.add(true);
                                    }else{
                                        gitti.add(false);
                                    }
                                    if (object.getDate("Tarih")!=null){
                                        tarih.add(String.valueOf(object.getDate("Tarih").getDate())+"/"+String.valueOf(object.getDate("Tarih").getMonth()+1)+"/"+String.valueOf(object.getDate("Tarih").getYear()+1900));
                                    }else{
                                        tarih.add("-");
                                    }
                                }
                            }
                            customlist3060 adapter=new customlist3060();
                            listView.setAdapter(adapter);
                            listView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,bigbagno.size()*150));
                        }
                    });
                }
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Integer z=i;
                PopupMenu popupMenu=new PopupMenu(g3060BigbagGiris.this,listView);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu2,popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId()==R.id.pop_duz){
                            Intent intent=new Intent(g3060BigbagGiris.this,g3060Duzelt.class);
                            intent.putExtra("Id",id.get(z));
                            startActivity(intent);
                        }else if(menuItem.getItemId()==R.id.pop_sil){
                            Log.e("---noo",String.valueOf(noo.get(z)));
                            AlertDialog.Builder adb=new AlertDialog.Builder(g3060BigbagGiris.this);
                            adb.setTitle("Dikkat");
                            adb.setMessage("Analizi silmek istediğinize emin misiniz?");
                            adb.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    ParseQuery<ParseObject>quer=ParseQuery.getQuery("G3060MeshBigbag");
                                    Log.e("---id.get(z)",String.valueOf(id.get(z)));
                                    quer.getInBackground(id.get(z), new GetCallback<ParseObject>() {
                                        @Override
                                        public void done(ParseObject object, ParseException e) {
                                            if (e!=null){
                                                e.printStackTrace();
                                                sorun=true;
                                            }else{
                                                object.deleteInBackground(new DeleteCallback() {
                                                    @Override
                                                    public void done(ParseException e) {
                                                        if (e!= null){
                                                            Toast.makeText(g3060BigbagGiris.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                                                            sorun=true;
                                                        }else{
                                                            ParseQuery<ParseObject>query1=ParseQuery.getQuery("G3060MeshBigbag");
                                                            Log.e("---noo.get(z)",String.valueOf(noo.get(z)));
                                                            query1.whereGreaterThan("No",noo.get(z));
                                                            query1.findInBackground(new FindCallback<ParseObject>() {
                                                                @Override
                                                                public void done(List<ParseObject> objects, ParseException e) {
                                                                    if (e!=null){
                                                                        e.printStackTrace();
                                                                    }else{
                                                                        for (ParseObject object:objects){
                                                                            id2.add(object.getObjectId());
                                                                            Log.e("---id2",String.valueOf(object.getObjectId()));
                                                                        }
                                                                        for (int j=0;j<id2.size();j++){
                                                                            ParseQuery<ParseObject> qqq=ParseQuery.getQuery("G3060MeshBigbag");
                                                                            Log.e("---id2.get(j)",String.valueOf(id.get(j)));
                                                                            qqq.getInBackground(id2.get(j), new GetCallback<ParseObject>() {
                                                                                @Override
                                                                                public void done(ParseObject object, ParseException e) {
                                                                                    if (e!=null){
                                                                                        e.printStackTrace();
                                                                                        sorun=true;
                                                                                    }else{
                                                                                        object.increment("No",-1);
                                                                                        Log.e("yeni No",String.valueOf(object.getInt("No")));
                                                                                        object.saveInBackground(new SaveCallback() {
                                                                                            @Override
                                                                                            public void done(ParseException e) {
                                                                                                if (e!=null){
                                                                                                    Toast.makeText(g3060BigbagGiris.this,"Sorun Çıktı, Sıralama Yapılamadı, Sistemi Kontrol Edin",Toast.LENGTH_LONG).show();
                                                                                                    sorun=true;
                                                                                                }
                                                                                            }
                                                                                        });
                                                                                    }
                                                                                }
                                                                            });
                                                                        }
                                                                        //olmadı
                                                                    }
                                                                    //olmadı
                                                                }
                                                            });
                                                        }
                                                        //
                                                    }
                                                });
                                            }
                                        }
                                    });

                                    if (sorun==false){

                                        if (sorun==false){
                                            Toast.makeText(g3060BigbagGiris.this,"Silindi",Toast.LENGTH_LONG).show();
                                            g3060BigbagGiris.this.finish();
                                        }
                                    }else{
                                        Toast.makeText(g3060BigbagGiris.this,"Sorun Çıktı, Silme İşlemi Yapılamadı, Sistemi Kontrol Edin",Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                            adb.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });

                            AlertDialog dialog=adb.create();
                            dialog.show();

                        }
                        return false;
                    }
                });
                return false;
            }
        });
    }

    public void yukle3060(View view) {
        yukle.setEnabled(false);
        ParseObject object = new ParseObject("G3060MeshBigbag");
        object.put("No",no);
        object.put("BigbagNo", Long.parseLong(BigbagNo.getText().toString()));
        object.put("ust600", Double.parseDouble(E600.getText().toString()));
        object.put("ust400", Double.parseDouble(E400.getText().toString()));
        object.put("alt212", Double.parseDouble(E212.getText().toString()));
        if (!Firma.getText().toString().isEmpty()) {
            object.put("Firma", Firma.getText().toString());
        }
        if (!Not.getText().toString().isEmpty()) {
            object.put("Not", Not.getText().toString());
        }
        if (cbb.isChecked()){
            object.put("Bigbag",true);
        }
        if (cbk.isChecked()){
            object.put("Kraft",true);
        }
        object.put("User", ParseUser.getCurrentUser().getUsername());

        object.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e!=null){
                    Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Yükleme Başarılı",Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        });
    }

    class customlist3060 extends BaseAdapter {

        @Override
        public int getCount() {
            return bigbagno.size();
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
            view=getLayoutInflater().inflate(R.layout.customlist,null);

            TextView tv_bigbag=view.findViewById(R.id.tv_bigbagno);
            TextView tv_anlz1=view.findViewById(R.id.tv_anlz1);
            TextView tv_anlz2=view.findViewById(R.id.tv_anlz2);
            TextView tv_anlz3=view.findViewById(R.id.tv_anlz3);
            TextView tv_not=view.findViewById(R.id.tv_not);
            TextView tv_tarih=view.findViewById(R.id.tv_tarih);
            TextView tv_firma=view.findViewById(R.id.tv_firma);
            CheckBox cb_gitti=view.findViewById(R.id.cb_gitti);

            tv_bigbag.setText(String.valueOf(bigbagno.get(i)));
            tv_anlz1.setText(String.valueOf(anlz1.get(i)));
            tv_anlz2.setText(String.valueOf(anlz2.get(i)));
            tv_anlz3.setText(String.valueOf(anlz3.get(i)));
            tv_not.setText(not.get(i));
            tv_tarih.setText(tarih.get(i));
            tv_firma.setText(firma2.get(i));
            if (gitti.get(i)==true){
                cb_gitti.setChecked(true);
                tv_bigbag.setTextColor(Color.rgb(140,20,20));
                tv_anlz1.setTextColor(Color.rgb(140,20,20));
                tv_anlz2.setTextColor(Color.rgb(140,20,20));
                tv_anlz3.setTextColor(Color.rgb(140,20,20));
                tv_firma.setTextColor(Color.rgb(140,20,20));
                tv_not.setTextColor(Color.rgb(140,20,20));
                tv_tarih.setTextColor(Color.rgb(140,20,20));
            }
            return view;
        }
    }
}
