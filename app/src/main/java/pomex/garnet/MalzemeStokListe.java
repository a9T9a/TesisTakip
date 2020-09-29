package pomex.garnet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.parse.CountCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class MalzemeStokListe extends AppCompatActivity {
    private TextView bstok1,bstok2,bstok3,bstok4,bstok5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_malzeme_stok_liste);
        getSupportActionBar().hide();

        bstok1=findViewById(R.id.tv_mstok_b1);
        bstok2=findViewById(R.id.tv_mstok_b2);
        bstok3=findViewById(R.id.tv_mstok_b3);
        bstok4=findViewById(R.id.tv_mstok_k);
        bstok5=findViewById(R.id.tv_mstok_p);

        AsyncTask_Stok_Bigbag1 asyncTask_stok_bigbag1=new AsyncTask_Stok_Bigbag1();
        asyncTask_stok_bigbag1.execute("90x90x110");

        AsyncTask_Stok_Bigbag asyncTask_stok_bigbag2=new AsyncTask_Stok_Bigbag();
        asyncTask_stok_bigbag2.execute("90x90x70p");

        AsyncTask_Stok_Bigbag asyncTask_stok_bigbag3=new AsyncTask_Stok_Bigbag();
        asyncTask_stok_bigbag3.execute("90x90x60");

        AsyncTask_Stok_Kraft asyncTask_stok_kraft=new AsyncTask_Stok_Kraft();
        asyncTask_stok_kraft.execute("Garnet");

        AsyncTask_Stok_Palet asyncTask_stok_palet=new AsyncTask_Stok_Palet();
        asyncTask_stok_palet.execute("100x100");
    }

    public class AsyncTask_Stok_Bigbag1 extends AsyncTask<String, Integer, Integer> {
        private ArrayList<Integer> gelen=new ArrayList<>();
        private ArrayList<Integer> giden=new ArrayList<>();
        public Integer GelenToplam=0;
        public Integer GidenToplam=0;
        public Integer Kalan=0;
        public String bigbag_isim;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected Integer doInBackground(String... voids) {
            bigbag_isim=voids[0];

            ParseQuery<ParseObject> query1=ParseQuery.getQuery("Bigbag");
            query1.whereEqualTo("Olcu",voids[0]);
            query1.whereEqualTo("Hareket","Geldi");
            query1.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e!=null){
                        e.printStackTrace();
                    }else{
                        for (ParseObject object:objects){
                            gelen.add(object.getInt("Miktar"));
                        }
                    }

                    for (int i=0;i<gelen.size();i++){
                        GelenToplam+=gelen.get(i);
                    }
                    Log.e("Gelen ",String.valueOf(GelenToplam));
                }
            });
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ParseQuery<ParseObject> query5=ParseQuery.getQuery("Bigbag");
            query5.whereEqualTo("Olcu",voids[0]);
            query5.whereEqualTo("Hareket","Gitti");
            query5.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e!=null){
                        e.printStackTrace();
                    }else{
                        for (ParseObject object:objects){
                            giden.add(object.getInt("Miktar"));
                        }
                    }
                    for (int i=0;i<giden.size();i++){
                        GidenToplam+=giden.get(i);
                    }
                    Log.e("Giden ",String.valueOf(GidenToplam));
                    Kalan=GelenToplam-GidenToplam;
                    Log.e("Kalan",String.valueOf(Kalan));
                    publishProgress(Kalan);
                }
            });
            return Kalan;
        }
        @Override
        protected void onPostExecute(Integer aVoid) {
            bstok1.setText(String.valueOf(aVoid));
            super.onPostExecute(aVoid);
        }
        @Override
        protected void onProgressUpdate(Integer... values) {
            bstok1.setText(String.valueOf(values[0]));
            super.onProgressUpdate(values);
        }
        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }

    public class AsyncTask_Stok_Bigbag extends AsyncTask<String, Integer, Integer> {

        private ArrayList<Integer> gelen=new ArrayList<>();
        private ArrayList<Integer> giden=new ArrayList<>();
        public Integer GelenToplam=0;
        public Integer GidenToplam=0;
        public Integer Kalan=0;
        public Integer Stok80;
        public Integer Stok180;
        public Integer Stok3060;
        public String bigbag_isim;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(String... voids) {
            bigbag_isim=voids[0];

            ParseQuery<ParseObject> query1=ParseQuery.getQuery("Bigbag");
            query1.whereEqualTo("Olcu",voids[0]);
            query1.whereEqualTo("Hareket","Geldi");
            query1.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e!=null){
                        e.printStackTrace();
                    }else{
                        for (ParseObject object:objects){
                            gelen.add(object.getInt("Miktar"));
                        }
                    }

                    for (int i=0;i<gelen.size();i++){
                        GelenToplam+=gelen.get(i);
                    }
                    Log.e("Gelen ",String.valueOf(GelenToplam));
                }
            });

            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            ParseQuery<ParseObject>query2=ParseQuery.getQuery("G80MeshBigbag");
            if (voids[0]=="90x90x60"){
                query2.whereEqualTo("Kraft",true);
                query2.whereEqualTo("Bigbag",true);
            }else if (voids[0]=="90x90x70p"){
                query2.whereEqualTo("Kraft",null);
                query2.whereEqualTo("Bigbag",true);
            }
            query2.whereEqualTo("Gitti",null);
            query2.countInBackground(new CountCallback() {
                @Override
                public void done(int count, ParseException e) {
                    if (e!=null){
                        e.printStackTrace();
                    }else{
                        Stok80=count;
                        Log.e("Stok_80",String.valueOf(count));
                    }
                }
            });

            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            ParseQuery<ParseObject>query3=ParseQuery.getQuery("G180MeshBigbag");
            if (voids[0]=="90x90x60"){
                query3.whereEqualTo("Kraft",true);
                query3.whereEqualTo("Bigbag",true);
            }else if (voids[0]=="90x90x70p"){
                query3.whereEqualTo("Kraft",null);
                query3.whereEqualTo("Bigbag",true);
            }
            query3.whereEqualTo("Gitti",null);
            query3.countInBackground(new CountCallback() {
                @Override
                public void done(int count, ParseException e) {
                    if (e!=null){
                        e.printStackTrace();
                    }else{
                        Stok180=count;
                        Log.e("Stok_180",String.valueOf(count));
                    }
                }
            });

            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            ParseQuery<ParseObject>query4=ParseQuery.getQuery("G3060MeshBigbag");
            if (voids[0]=="90x90x60"){
                query4.whereEqualTo("Kraft",true);
                query4.whereEqualTo("Bigbag",true);
            }else if (voids[0]=="90x90x70p"){
                query4.whereEqualTo("Kraft",null);
                query4.whereEqualTo("Bigbag",true);
            }
            query4.whereEqualTo("Gitti",null);
            query4.countInBackground(new CountCallback() {
                @Override
                public void done(int count, ParseException e) {
                    if (e!=null){
                        e.printStackTrace();
                    }else{
                        Stok3060=count;
                        Log.e("Stok_3060",String.valueOf(count));
                    }
                }
            });

            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            ParseQuery<ParseObject> query5=ParseQuery.getQuery("Bigbag");
            query5.whereEqualTo("Olcu",voids[0]);
            query5.whereEqualTo("Hareket","Gitti");
            query5.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e!=null){
                        e.printStackTrace();
                    }else{
                        for (ParseObject object:objects){
                            giden.add(object.getInt("Miktar"));
                        }
                    }
                    for (int i=0;i<giden.size();i++){
                        GidenToplam+=giden.get(i);
                    }
                    Log.e("Giden ",String.valueOf(GidenToplam));
                    Kalan=GelenToplam-GidenToplam-Stok80-Stok180-Stok3060;
                    Log.e("Kalan",String.valueOf(Kalan));
                    publishProgress(Kalan);
                }
            });

            return Kalan;
        }

        @Override
        protected void onPostExecute(Integer aVoid) {

            if (bigbag_isim=="90x90x70p"){
                bstok2.setText(String.valueOf(aVoid));
            }
            else if (bigbag_isim=="90x90x60"){
                bstok3.setText(String.valueOf(aVoid));
            }

            super.onPostExecute(aVoid);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

            if (bigbag_isim=="90x90x70p"){
                bstok2.setText(String.valueOf(values[0]));
            }
            else if (bigbag_isim=="90x90x60"){
                bstok3.setText(String.valueOf(values[0]));
            }
            super.onProgressUpdate(values);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

    }

    public class AsyncTask_Stok_Kraft extends AsyncTask<String, Integer, Integer> {

        private ArrayList<Integer> gelen=new ArrayList<>();
        private ArrayList<Integer> giden=new ArrayList<>();
        public Integer GelenToplam=0;
        public Integer GidenToplam=0;
        public Integer Kalan=0;
        public Integer Stok80;
        public Integer Stok180;
        public Integer Stok3060;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(String... voids) {

            ParseQuery<ParseObject> query1=ParseQuery.getQuery("Kraft");
            query1.whereEqualTo("Olcu",voids[0]);
            query1.whereEqualTo("Hareket","Geldi");
            query1.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e!=null){
                        e.printStackTrace();
                    }else{
                        for (ParseObject object:objects){
                            gelen.add(object.getInt("Miktar"));
                        }
                    }

                    for (int i=0;i<gelen.size();i++){
                        GelenToplam+=gelen.get(i);
                    }
                    Log.e("Gelen ",String.valueOf(GelenToplam));
                }
            });

            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            ParseQuery<ParseObject>query2=ParseQuery.getQuery("G80MeshBigbag");
            query2.whereEqualTo("Kraft",true);
            query2.whereEqualTo("Gitti",null);
            query2.countInBackground(new CountCallback() {
                @Override
                public void done(int count, ParseException e) {
                    if (e!=null){
                        e.printStackTrace();
                    }else{
                        Stok80=count*40;
                        Log.e("Stok_80",String.valueOf(count));
                    }
                }
            });

            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            ParseQuery<ParseObject>query3=ParseQuery.getQuery("G180MeshBigbag");
            query3.whereEqualTo("Kraft",true);
            query3.whereEqualTo("Gitti",null);
            query3.countInBackground(new CountCallback() {
                @Override
                public void done(int count, ParseException e) {
                    if (e!=null){
                        e.printStackTrace();
                    }else{
                        Stok180=count*40;
                        Log.e("Stok_180",String.valueOf(count));
                    }
                }
            });

            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            ParseQuery<ParseObject>query4=ParseQuery.getQuery("G3060MeshBigbag");
            query4.whereEqualTo("Kraft",true);
            query4.whereEqualTo("Gitti",null);
            query4.countInBackground(new CountCallback() {
                @Override
                public void done(int count, ParseException e) {
                    if (e!=null){
                        e.printStackTrace();
                    }else{
                        Stok3060=count*40;
                        Log.e("Stok_3060",String.valueOf(count));
                    }
                }
            });

            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            ParseQuery<ParseObject> query5=ParseQuery.getQuery("Kraft");
            query5.whereEqualTo("Olcu",voids[0]);
            query5.whereEqualTo("Hareket","Gitti");
            query5.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e!=null){
                        e.printStackTrace();
                    }else{
                        for (ParseObject object:objects){
                            giden.add(object.getInt("Miktar"));
                        }
                    }
                    for (int i=0;i<giden.size();i++){
                        GidenToplam+=giden.get(i);
                    }
                    Log.e("Giden ",String.valueOf(GidenToplam));
                    Kalan=GelenToplam-GidenToplam-Stok80-Stok180-Stok3060;
                    Log.e("Kalan",String.valueOf(Kalan));
                    publishProgress(Kalan);
                }
            });

            return Kalan;
        }

        @Override
        protected void onPostExecute(Integer aVoid) {

            bstok4.setText(String.valueOf(aVoid));

            super.onPostExecute(aVoid);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

            bstok4.setText(String.valueOf(values[0]));
            super.onProgressUpdate(values);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

    }

    public class AsyncTask_Stok_Palet extends AsyncTask<String, Integer, Integer> {

        private ArrayList<Integer> gelen=new ArrayList<>();
        private ArrayList<Integer> giden=new ArrayList<>();
        public Integer GelenToplam=0;
        public Integer GidenToplam=0;
        public Integer Kalan=0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(String... voids) {

            ParseQuery<ParseObject> query1=ParseQuery.getQuery("Palet");
            query1.whereEqualTo("Olcu",voids[0]);
            query1.whereEqualTo("Hareket","Geldi");
            query1.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e!=null){
                        e.printStackTrace();
                    }else{
                        for (ParseObject object:objects){
                            gelen.add(object.getInt("Miktar"));
                        }
                    }

                    for (int i=0;i<gelen.size();i++){
                        GelenToplam+=gelen.get(i);
                    }
                    Log.e("Gelen ",String.valueOf(GelenToplam));
                }
            });

            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            ParseQuery<ParseObject> query5=ParseQuery.getQuery("Palet");
            query5.whereEqualTo("Olcu",voids[0]);
            query5.whereEqualTo("Hareket","Gitti");
            query5.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (e!=null){
                        e.printStackTrace();
                    }else{
                        for (ParseObject object:objects){
                            giden.add(object.getInt("Miktar"));
                        }
                    }
                    for (int i=0;i<giden.size();i++){
                        GidenToplam+=giden.get(i);
                    }
                    Log.e("Giden ",String.valueOf(GidenToplam));
                    Kalan=GelenToplam-GidenToplam;
                    Log.e("Kalan",String.valueOf(Kalan));
                    publishProgress(Kalan);
                }
            });

            return Kalan;
        }

        @Override
        protected void onPostExecute(Integer aVoid) {

            bstok5.setText(String.valueOf(aVoid));

            super.onPostExecute(aVoid);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

            bstok5.setText(String.valueOf(values[0]));
            super.onProgressUpdate(values);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

    }
}
