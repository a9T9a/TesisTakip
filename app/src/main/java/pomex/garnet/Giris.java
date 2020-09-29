package pomex.garnet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.io.File;

public class Giris extends AppCompatActivity {
    private Button buttonGiris, buttonKayit;
    private EditText editTextName, editTextPass, editTextKayitKodu;
    private String a="pmz";
    private EditText b;
    private CheckBox cbox;
    private SharedPreferences mPrefs;
    private static final String PREFS_NAME="PrefsFile";
    private File ff;
    private Integer izn_kont1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);
        getSupportActionBar().hide();

        mPrefs=getSharedPreferences(PREFS_NAME,MODE_PRIVATE);

        editTextName=findViewById(R.id.editTextName);
        editTextPass=findViewById(R.id.editTextPass);
        editTextKayitKodu=findViewById(R.id.editTextKayitKodu);
        buttonGiris=findViewById(R.id.buttonGiris);
        buttonKayit=findViewById(R.id.buttonKayit);
        cbox=findViewById(R.id.checkBoxHatirla);

        b=findViewById(R.id.editTextKayitKodu);

        ff=new File(Environment.getExternalStorageDirectory().getAbsolutePath());

        buttonKayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if((b.getText().toString()).equalsIgnoreCase(a)){
                    ParseUser user=new ParseUser();
                    user.setUsername(editTextName.getText().toString());
                    user.setPassword(editTextPass.getText().toString());
                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e!=null){
                                Toast.makeText(Giris.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(Giris.this,"Kullanıcı Oluşturuldu",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(Giris.this,"Yanlış Kayıt Kodu",Toast.LENGTH_LONG).show();
                }
            }
        });

        buttonGiris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(cbox.isChecked()){
                    Boolean bchecked=cbox.isChecked();
                    SharedPreferences.Editor spe=mPrefs.edit();
                    spe.putString("pref_name",editTextName.getText().toString());
                    spe.putString("pref_pass",editTextPass.getText().toString());
                    spe.putBoolean("pref_check",bchecked);
                    spe.apply();

                }else{
                    mPrefs.edit().clear().apply();
                }

                ParseUser.logInInBackground(editTextName.getText().toString(), editTextPass.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {

                        if(e!=null){
                            Toast.makeText(getApplicationContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getApplicationContext(),"Giriş Başarılı",Toast.LENGTH_SHORT).show();
                            if (editTextName.getText().toString().equalsIgnoreCase("Kantar")){
                                Intent yintent = new Intent(Giris.this,Kantar.class);
                                startActivity(yintent);
                            }else{
                                Intent yintent = new Intent(Giris.this,AnaMenu.class);
                                yintent.putExtra("user",editTextName.getText().toString());
                                startActivity(yintent);
                            }
                        }
                    }
                });
            }
        });

        getPreferences();
    }

    private void getPreferences() {
        SharedPreferences sp= getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        if(sp.contains("pref_name")){
            String u=sp.getString("pref_name","nothing");
            editTextName.setText(u.toString());
        }
        if (sp.contains("pref_pass")){
            String u=sp.getString("pref_pass","nothing");
            editTextPass.setText(u.toString());
        }
        if (sp.contains("pref_check")){
            Boolean b=sp.getBoolean("pref_check",false);
            cbox.setChecked(b);
        }
    }
}
