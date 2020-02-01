package toc.hec.helloworld2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class Main5Activity extends AppCompatActivity {

    ArrayList<Solution> solutions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);



        //Recyvlerview im Layout finden
        RecyclerView rvSolutions = (RecyclerView) findViewById(R.id.rvSolutions);

        // Solutions initialisieren
        ArrayList<Solution> solutions = new ArrayList<Solution>();
        for (int i = 1; i <= 3; i++) {
            solutions.add(new Solution("a+b+c+2xd " ));
        }
        solutions.add( new Solution("a+b"));
        solutions.add( new Solution("1+(2+3+4)*(5+6)"));


        //Adapter generieren und Daten übergeben
        SolutionsAdapter adapter = new SolutionsAdapter(solutions);
        //Adapter übergeben
        rvSolutions.setAdapter(adapter);
        rvSolutions.setLayoutManager(new LinearLayoutManager(this));



    }
}
