package pomex.garnet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Satislar extends AppCompatActivity {
    ImageView granat,silis,rutil,hematit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_satislar);
        getSupportActionBar().hide();

        granat=findViewById(R.id.imgv_s_garnet);
        silis=findViewById(R.id.imgv_s_silis);
        rutil=findViewById(R.id.imgv_s_rutil);
        hematit=findViewById(R.id.imgv_s_hematit);

        granat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Satislar.this,SatislarListele.class);
                intent.putExtra("mal",1);
                startActivity(intent);
            }
        });

        silis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Satislar.this,SatislarListele.class);
                intent.putExtra("mal",2);
                startActivity(intent);
            }
        });

        hematit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Satislar.this,SatislarListele.class);
                intent.putExtra("mal",3);
                startActivity(intent);
            }
        });

        rutil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Satislar.this,SatislarListele.class);
                intent.putExtra("mal",4);
                startActivity(intent);
            }
        });
    }
}
