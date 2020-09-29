package pomex.garnet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SatislarListele extends AppCompatActivity {
    private TextView ocak19,subat19,mart19,nisan19,mayis19,haziran19,temmuz19,agustos19,eylul19,ekim19,kasim19,aralik19,tv2019,top2019;
    private TextView ocak20,subat20,mart20,nisan20,mayis20,haziran20,temmuz20,agustos20,eylul20,ekim20,kasim20,aralik20,tv2020,top2020;
    private ArrayList<Integer> yil_top,ay_top;
    private ArrayList<TextView>tv1,tv2;
    private Integer no;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_satislar_listele);

        tv2019=findViewById(R.id.tv_s_2019);
        ocak19=findViewById(R.id.tv_s_ocak19);
        ocak19.setTag("2019.1");
        ocak19.setOnClickListener(onClickListener);
        subat19=findViewById(R.id.tv_s_subat19);
        subat19.setTag("2019.2");
        subat19.setOnClickListener(onClickListener);
        mart19=findViewById(R.id.tv_s_mart19);
        mart19.setTag("2019.3");
        mart19.setOnClickListener(onClickListener);
        nisan19=findViewById(R.id.tv_s_nisan19);
        nisan19.setTag("2019.4");
        nisan19.setOnClickListener(onClickListener);
        mayis19=findViewById(R.id.tv_s_mayis19);
        mayis19.setTag("2019.5");
        mayis19.setOnClickListener(onClickListener);
        haziran19=findViewById(R.id.tv_s_haziran19);
        haziran19.setTag("2019.6");
        haziran19.setOnClickListener(onClickListener);
        temmuz19=findViewById(R.id.tv_s_temmuz19);
        temmuz19.setTag("2019.7");
        temmuz19.setOnClickListener(onClickListener);
        agustos19=findViewById(R.id.tv_s_agustos19);
        agustos19.setTag("2019.8");
        agustos19.setOnClickListener(onClickListener);
        eylul19=findViewById(R.id.tv_s_eylul19);
        eylul19.setTag("2019.9");
        eylul19.setOnClickListener(onClickListener);
        ekim19=findViewById(R.id.tv_s_ekim19);
        ekim19.setTag("2019.10");
        ekim19.setOnClickListener(onClickListener);
        kasim19=findViewById(R.id.tv_s_kasim19);
        kasim19.setTag("2019.11");
        kasim19.setOnClickListener(onClickListener);
        aralik19=findViewById(R.id.tv_s_aralik19);
        aralik19.setTag("2019.12");
        aralik19.setOnClickListener(onClickListener);
        top2019=findViewById(R.id.tv_s_top19);
        top2019.setTag("2019.0");
        top2019.setOnClickListener(onClickListener);

        tv2020=findViewById(R.id.tv_s_2020);
        ocak20=findViewById(R.id.tv_s_ocak20);
        ocak20.setTag("2020.1");
        ocak20.setOnClickListener(onClickListener);
        subat20=findViewById(R.id.tv_s_subat20);
        subat20.setTag("2020.2");
        subat20.setOnClickListener(onClickListener);
        mart20=findViewById(R.id.tv_s_mart20);
        mart20.setTag("2020.3");
        mart20.setOnClickListener(onClickListener);
        nisan20=findViewById(R.id.tv_s_nisan20);
        nisan20.setTag("2020.4");
        nisan20.setOnClickListener(onClickListener);
        mayis20=findViewById(R.id.tv_s_mayis20);
        mayis20.setTag("2020.5");
        mayis20.setOnClickListener(onClickListener);
        haziran20=findViewById(R.id.tv_s_haziran20);
        haziran20.setTag("2020.6");
        haziran20.setOnClickListener(onClickListener);
        temmuz20=findViewById(R.id.tv_s_temmuz20);
        temmuz20.setTag("2020.7");
        temmuz20.setOnClickListener(onClickListener);
        agustos20=findViewById(R.id.tv_s_agustos20);
        agustos20.setTag("2020.8");
        agustos20.setOnClickListener(onClickListener);
        eylul20=findViewById(R.id.tv_s_eylul20);
        eylul20.setTag("2020.9");
        eylul20.setOnClickListener(onClickListener);
        ekim20=findViewById(R.id.tv_s_ekim20);
        ekim20.setTag("2020.10");
        ekim20.setOnClickListener(onClickListener);
        kasim20=findViewById(R.id.tv_s_kasim20);
        kasim20.setTag("2020.11");
        kasim20.setOnClickListener(onClickListener);
        aralik20=findViewById(R.id.tv_s_aralik20);
        aralik20.setTag("2020.12");
        aralik20.setOnClickListener(onClickListener);
        top2020=findViewById(R.id.tv_s_top20);
        top2020.setTag("2020.0");
        top2020.setOnClickListener(onClickListener);

        yil_top=new ArrayList<>();
        ay_top=new ArrayList<>();
        tv1=new ArrayList<>();
        tv1.addAll(Arrays.asList(ocak19, subat19, mart19, nisan19, mayis19, haziran19, temmuz19, agustos19, eylul19, ekim19, kasim19, aralik19));
        tv2=new ArrayList<>();
        tv2.addAll(Arrays.asList(ocak20, subat20, mart20, nisan20, mayis20, haziran20, temmuz20, agustos20, eylul20, ekim20, kasim20, aralik20));

        dialog=new Dialog(this);

        no=getIntent().getIntExtra("mal",0);

        if (no==1){
            getSupportActionBar().setTitle("Garnet Satış");
            tv2019.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    list(2019);
                    toplamsatis(2019);
                }
            });
            list(2020);
            toplamsatis(2020);
        }

        else if(no==2){
            getSupportActionBar().setTitle("Silis Satış");
            tv2019.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    list(2019);
                    toplamsatis(2019);
                }
            });
            list(2020);
            toplamsatis(2020);
        }

        else if(no==3){
            getSupportActionBar().setTitle("Hematit Satış");
            tv2019.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    list(2019);
                    toplamsatis(2019);
                }
            });
            list(2020);
            toplamsatis(2020);
        }

        else if(no==4){
            getSupportActionBar().setTitle("Rutil Satış");
            tv2019.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    list(2019);
                    toplamsatis(2019);
                }
            });
            list(2020);
            toplamsatis(2020);
        }
    }

    public Integer toplam(ArrayList<Integer> list){
        int toplam=0;
        for (int i:list){
            toplam+=i;
        }
        return toplam;
    }


    public void list(final int yil){
        ParseQuery<ParseObject>query= ParseQuery.getQuery("Satislar");
        for (int j=1;j<=12;j++)
        {
            final int jj=j;
            query.whereEqualTo("Yil",yil);
            query.whereEqualTo("Malzeme",no);
            query.whereEqualTo("Ay",j);
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e!=null){
                        e.printStackTrace();
                    }else{
                        for (ParseObject obj:objects){
                            ay_top.add(obj.getInt("Miktar"));
                        }
                    }
                    if (yil==2019){
                        if (toplam(ay_top)!=0){
                            tv1.get(jj-1).setText(String.valueOf(toplam(ay_top)));
                        }
                    }else if(yil==2020){
                        if (toplam(ay_top)!=0){
                            tv2.get(jj-1).setText(String.valueOf(toplam(ay_top)));
                        }
                    }
                    ay_top.clear();
                }
            });
        }
    }

    public void toplamsatis(final int yil){
        ParseQuery<ParseObject>queryy=ParseQuery.getQuery("Satislar");
        queryy.whereEqualTo("Yil",yil);
        queryy.whereEqualTo("Malzeme",no);
        queryy.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e!=null){
                    e.printStackTrace();
                }else{
                    for (ParseObject obj:objects){
                        yil_top.add(obj.getInt("Miktar"));
                    }
                }
                if (yil==2019){
                    top2019.setText(String.valueOf(toplam(yil_top)));
                }else if(yil==2020){
                    top2020.setText(String.valueOf(toplam(yil_top)));
                }
                yil_top.clear();
            }
        });
    }

    private final View.OnClickListener onClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String t=view.getTag().toString();
            secilen(t);
        }
    };

    public void secilen(String tag_number){
        String s=tag_number;
        String[] spilit=s.split("\\.");
        int yil_sayi=Integer.parseInt(spilit[0]);
        int ay_sayi=Integer.parseInt(spilit[1]);
        show_dialog(yil_sayi,ay_sayi);
    }

    public void show_dialog(final int yil, int ay){
        final TextView t3060,t80,t180;
        dialog.setContentView(R.layout.garnet_s_popup);
        t3060=dialog.findViewById(R.id.tv_s_g_p_3060);
        t80=dialog.findViewById(R.id.tv_s_g_p_80);
        t180=dialog.findViewById(R.id.tv_s_g_p_180);

        if(ay==0){
            ParseQuery<ParseObject>q1=ParseQuery.getQuery("Satislar");
            q1.whereEqualTo("Yil",yil);
            q1.whereEqualTo("Malzeme",no);
            q1.whereEqualTo("Boyut","3060");
            q1.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e!=null){
                        e.printStackTrace();
                    }else{
                        for (ParseObject obj:objects){
                            yil_top.add(obj.getInt("Miktar"));
                        }
                    }
                    t3060.setText(String.valueOf(toplam(yil_top)));
                    yil_top.clear();
                }
            });
            ParseQuery<ParseObject>q2=ParseQuery.getQuery("Satislar");
            q2.whereEqualTo("Yil",yil);
            q2.whereEqualTo("Malzeme",no);
            q2.whereEqualTo("Boyut","80");
            q2.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e!=null){
                        e.printStackTrace();
                    }else{
                        for (ParseObject obj:objects){
                            yil_top.add(obj.getInt("Miktar"));
                        }
                    }
                    t80.setText(String.valueOf(toplam(yil_top)));
                    yil_top.clear();
                }
            });
            ParseQuery<ParseObject>q3=ParseQuery.getQuery("Satislar");
            q3.whereEqualTo("Yil",yil);
            q3.whereEqualTo("Malzeme",no);
            q3.whereEqualTo("Boyut","180");
            q3.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e!=null){
                        e.printStackTrace();
                    }else{
                        for (ParseObject obj:objects){
                            yil_top.add(obj.getInt("Miktar"));
                        }
                    }
                    t180.setText(String.valueOf(toplam(yil_top)));
                    yil_top.clear();
                }
            });
        }else{
            ParseQuery<ParseObject>query1=ParseQuery.getQuery("Satislar");
            query1.whereEqualTo("Yil",yil);
            query1.whereEqualTo("Ay",ay);
            query1.whereEqualTo("Malzeme",no);
            query1.whereEqualTo("Boyut","3060");
            query1.getFirstInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {
                    if (e!=null){
                        e.printStackTrace();
                    }else{
                        if (object.getInt("Miktar")!=0){
                            t3060.setText(String.valueOf(object.getInt("Miktar")));
                        }
                    }
                }
            });
            ParseQuery<ParseObject>query2=ParseQuery.getQuery("Satislar");
            query2.whereEqualTo("Yil",yil);
            query2.whereEqualTo("Ay",ay);
            query2.whereEqualTo("Malzeme",no);
            query2.whereEqualTo("Boyut","80");
            query2.getFirstInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {
                    if (e!=null){
                        e.printStackTrace();
                    }else{
                        if (object.getInt("Miktar")!=0){
                            t80.setText(String.valueOf(object.getInt("Miktar")));
                        }
                    }
                }
            });
            ParseQuery<ParseObject>query3=ParseQuery.getQuery("Satislar");
            query3.whereEqualTo("Yil",yil);
            query3.whereEqualTo("Ay",ay);
            query3.whereEqualTo("Malzeme",no);
            query3.whereEqualTo("Boyut","180");
            query3.getFirstInBackground(new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {
                    if (e!=null){
                        e.printStackTrace();
                    }else{
                        if (object.getInt("Miktar")!=0){
                            t180.setText(String.valueOf(object.getInt("Miktar")));
                        }
                    }
                }
            });
        }
        dialog.getWindow().setTitle(String.valueOf(ay)+"."+String.valueOf(yil));
        dialog.show();
    }

    private String aybul(int sayi){
        String ay="";
        if (sayi==1){
            ay="Ocak";
        }else if (sayi==2){
            ay="Şubat";
        }else if( sayi==3){
            ay="Mart";
        }else if (sayi==4){
            ay="Nisan";
        }else if (sayi==5){
            ay="Mayıs";
        }else if (sayi==6){
            ay="Haziran";
        }else if (sayi==7){
            ay="Temmuz";
        }else if (sayi==8){
            ay="Ağustos";
        }else if (sayi==9){
            ay="Eylül";
        }else if (sayi==10){
            ay="Ekim";
        }else if (sayi==11){
            ay="Kasım";
        }else if (sayi==12){
            ay="Aralık";
        }
        return ay;
    }
}
