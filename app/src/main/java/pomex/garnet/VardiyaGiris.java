package pomex.garnet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

public class VardiyaGiris extends AppCompatActivity {
    private ImageButton yapis,gecmis,olcum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vardiya_giris);
        getSupportActionBar().hide();
        yapis=findViewById(R.id.imgb_vardg_yapis);
        gecmis=findViewById(R.id.imgb_vardg_gec);
        olcum=findViewById(R.id.imgb_vardg_olcum);

        yapis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(VardiyaGiris.this,Vardiya.class);
                startActivity(intent);
            }
        });

        gecmis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(VardiyaGiris.this,VardiyaListe.class);
                startActivity(intent);
            }
        });

        olcum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu=new PopupMenu(VardiyaGiris.this,olcum);
                popupMenu.getMenuInflater().inflate(R.menu.popup_menu,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId()==R.id.pop_yeni){
                            Intent intent=new Intent(VardiyaGiris.this,AMFirinOlcum.class);
                            startActivity(intent);
                        }else if (menuItem.getItemId()==R.id.pop_liste){
                            Intent intent=new Intent(VardiyaGiris.this,AMFOListe.class);
                            startActivity(intent);
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }
}
