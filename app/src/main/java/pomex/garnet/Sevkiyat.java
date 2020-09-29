package pomex.garnet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class Sevkiyat extends AppCompatActivity {
    private ImageButton garnet,silis;
    private TextView t_garnet,t_silis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sevkiyat);
        getSupportActionBar().hide();

        garnet=findViewById(R.id.imageButtongarnet);
        silis=findViewById(R.id.imageButtonsilis);

        garnet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(Sevkiyat.this,GarnetSevkiyat.class);
                startActivity(intent);
            }
        });

        silis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(Sevkiyat.this,SilisSevkiyat.class);
                startActivity(intent);
            }
        });

    }
}
