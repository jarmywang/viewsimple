package wang.viewsimple;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        ListView listIntent = findViewById(R.id.list_intent);

        final List<Class<?>> listClass = new ArrayList<>();
        listClass.add(MainActivity.class);
        listClass.add(DragHorListActivity.class);
        listClass.add(WaterMarkActivity.class);
        //=========== 添加一项记得到manifest也添加============

        List<String> listSimpleName = new ArrayList<>();
        for (Class<?> clazz : listClass) {
            listSimpleName.add(clazz.getSimpleName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_list_text, R.id.tv, listSimpleName);
        listIntent.setAdapter(adapter);
        listIntent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                launchFor(listClass.get(position));
            }
        });
    }

    private void launchFor(Class<?> clazz) {
        startActivity(new Intent(this, clazz));
    }
}
