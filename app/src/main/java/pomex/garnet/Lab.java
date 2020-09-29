package pomex.garnet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.parse.ParseUser;

import java.util.Calendar;

public class Lab extends AppCompatActivity {
    private ImageButton g3060,g80,g180,komur,mineral,dagilim;
    private Calendar calendar;
    private Integer izin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab);
        getSupportActionBar().hide();

        g3060=findViewById(R.id.imgb_lab_3060);
        g80=findViewById(R.id.imgb_lab_80);
        g180=findViewById(R.id.imgb_lab_180);
        komur=findViewById(R.id.imgb_lab_komur);
        mineral=findViewById(R.id.imgb_lab_mineral);
        dagilim=findViewById(R.id.imgb_dagilim);

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser.get("izin")==null){
            izin=0;
        }else{
            izin = (Integer) currentUser.get("izin");
        }

        calendar.getInstance();
        Integer ss=Calendar.MONTH;


        g3060.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Lab.this,g3060BigbagGiris.class);
                if (izin==1){
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"İzin Yok",Toast.LENGTH_SHORT).show();
                }
            }
        });
        g80.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Lab.this,g80BigbagGiris.class);
                if (izin==1){
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"İzin Yok",Toast.LENGTH_SHORT).show();
                }
            }
        });
        g180.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Lab.this,g180BigbagGiris.class);
                if (izin==1){
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"İzin Yok",Toast.LENGTH_SHORT).show();
                }
            }
        });
        komur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu=new PopupMenu(Lab.this,komur);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId()==R.id.pop_yeni){
                            Intent intent=new Intent(Lab.this,KomurAnaliz.class);
                            if (izin==1){
                                startActivity(intent);
                            }else{
                                Toast.makeText(getApplicationContext(),"İzin Yok",Toast.LENGTH_SHORT).show();
                            }
                        }else if (menuItem.getItemId()==R.id.pop_liste){
                            Intent intent=new Intent(Lab.this,KomurAG.class);
                            if (izin==1){
                                startActivity(intent);
                            }else{
                                Toast.makeText(getApplicationContext(),"İzin Yok",Toast.LENGTH_SHORT).show();
                            }
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        mineral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Lab.this,FirinMineralAnaliz.class);
                if (izin==1){
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"İzin Yok",Toast.LENGTH_SHORT).show();
                }
            }
        });
        dagilim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu=new PopupMenu(Lab.this,dagilim);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId()==R.id.pop_yeni){
                            Intent intent=new Intent(Lab.this,AgirMineralUrunDagilim.class);
                            if (izin==1){
                                startActivity(intent);
                            }else{
                                Toast.makeText(getApplicationContext(),"İzin Yok",Toast.LENGTH_SHORT).show();
                            }
                        }else if (menuItem.getItemId()==R.id.pop_liste){
                            Intent intent=new Intent(Lab.this,AMUDagilimList.class);
                            if (izin==1){
                                startActivity(intent);
                            }else{
                                Toast.makeText(getApplicationContext(),"İzin Yok",Toast.LENGTH_SHORT).show();
                            }
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }
}
