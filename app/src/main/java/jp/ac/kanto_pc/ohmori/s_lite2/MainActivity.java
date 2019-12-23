package jp.ac.kanto_pc.ohmori.s_lite2;

import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase db;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DatabaseHelper dHelper = new DatabaseHelper(this);
        db = dHelper.getReadableDatabase();

        setContentView(R.layout.activity_main);
        Query();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        db.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        /* アクションバーへの登録 （Android3.0以降） */
        MenuItem item1 = menu.add(1, 11, 0, "表示");
        item1.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);	// ALWAYS/IF_ROOM/NEVER
        MenuItem item2 = menu.add(1, 12, 0, "追加");
        item2.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        MenuItem item3 = menu.add(1, 13, 0, "グラフ");
        item3.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case 11:
                setContentView(R.layout.activity_main);
                Query();
                break;
            case 12:
                setContentView(R.layout.activity_sub);
                Insert();
                break;
            case 13:
                setContentView(R.layout.activity_sub2);
                Insert();
                break;
        }

        return true;
    }


    void Query() {
        try {
            TableLayout tl = (TableLayout) findViewById(R.id.table);
            String[] columns = {"num1", "num2"};
            Cursor myCursor = db.query("test_m", columns, null, null, null, null, null);
            while (myCursor.moveToNext()) {
                TextView tv1 = new TextView(this);
                tv1.setText(myCursor.getString(0));
                TextView tv2 = new TextView(this);
                tv2.setText(myCursor.getString(1));

                TableRow tr = new TableRow(this);
                tr.addView(tv1);
                tr.addView(tv2);

                tl.addView(tr, new TableLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void Insert() {
        Button bt = (Button) findViewById(R.id.enter);
        final EditText et_num1 = (EditText) findViewById(R.id.num1);
        final EditText et_num2 = (EditText) findViewById(R.id.num2);


        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String num1 = et_num1.getText().toString();
                String num2 = et_num2.getText().toString();


                if (num1.length() > 0) {
                    db.execSQL("insert into test_m (num1, num2) values ('" + num1 + "','" + num2 + "')");
                    et_num1.setText("");
                    et_num2.setText("");
                }
            }
        });
    }
}

