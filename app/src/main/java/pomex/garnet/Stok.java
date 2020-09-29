package pomex.garnet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.CountCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Stok extends AppCompatActivity {
    private ImageButton malzemegiris,gfiltre,malzemestok,aylikstok;
    private Integer izin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stok);
        getSupportActionBar().hide();

        gfiltre=findViewById(R.id.imgb_stok_garntfiltre);
        malzemegiris=findViewById(R.id.imgb_stok_malzeme);
        malzemestok=findViewById(R.id.img_stok_malstok);
        aylikstok=findViewById(R.id.imgb_as);

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser.get("izin")==null){
            izin=0;
        }else{
            izin = (Integer) currentUser.get("izin");
        }

        gfiltre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Stok.this,GarnetBigBagFiltrele.class);
                if (izin==1){
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"İzin Yok",Toast.LENGTH_SHORT).show();
                }
            }
        });
        malzemegiris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Stok.this,MalGiris.class);
                if (izin==1){
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"İzin Yok",Toast.LENGTH_SHORT).show();
                }
            }
        });
        malzemestok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Stok.this,MalzemeStokListe.class);
                if (izin==1){
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),"İzin Yok",Toast.LENGTH_SHORT).show();
                }
            }
        });
        aylikstok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu=new PopupMenu(Stok.this,aylikstok);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId()==R.id.pop_yeni){
                            Intent intent=new Intent(Stok.this,StokAylik.class);
                            if (izin==1){
                                startActivity(intent);
                            }else{
                                Toast.makeText(getApplicationContext(),"İzin Yok",Toast.LENGTH_SHORT).show();
                            }
                        }else if (menuItem.getItemId()==R.id.pop_liste){
                            Intent intent=new Intent(Stok.this,AylikStokListe.class);
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
