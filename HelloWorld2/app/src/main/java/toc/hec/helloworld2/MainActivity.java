package toc.hec.helloworld2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    android.widget.Button myButton;
    android.widget.TextView myText;
    android.widget.EditText Input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        myText = findViewById(R.id.textView);
        Input = findViewById(R.id.editText3);

        myButton = findViewById(R.id.button_rechne);
        myButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            //Toast.makeText(MainActivity.this, "dfldg",Toast.LENGTH_LONG).show();
                                            //myText.setText("geklickt");
                                            Klick();
                                        }
                                    }
        );




    }


    public void StartMainActivity2(View view) {
        // Do something in response to button
        Toast.makeText(MainActivity.this, "Activity 2 Knopf gedrueckt",Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);
    }




    public void StartMainActivity3(View view) {
        // Do something in response to button
        Toast.makeText(MainActivity.this, "Activity 3 Knopf gedrueckt",Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, Main3Activity.class);
        startActivity(intent);
    }


    public void StartMainActivity4(View view) {
        // Do something in response to button
        Toast.makeText(MainActivity.this, "Activity 4 Knopf gedrueckt",Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, Main4Activity.class);
        startActivity(intent);
    }

    public void StartMainActivity5(View view) {
        // Do something in response to button
        Toast.makeText(MainActivity.this, "Activity 5 Knopf gedrueckt",Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, Main5Activity.class);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void Klick() {
        int result = 0;
        addition myaddition = new addition();

        try {
            String text = Input.getText().toString();
            Integer i = Integer.parseInt(text);
            result = myaddition.add(i, 12);
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, e.getMessage(),Toast.LENGTH_SHORT).show();
        }


        //String mystring = getResources().getString(R.string.los);
        //mystring = mystring + "\n" + text;

        String erg = "Ergebnis: " + Integer.toString(result) + "\n";

        myText.append(erg);


    }

}
