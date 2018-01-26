package wang.viewsimple;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    AppCompatActivity context;
    AppBarLayout appBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;
        appBar = findViewById(R.id.appBar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

//                View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_top, null);
//                PopupWindow popupWindow = new PopupWindow(dialogView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                popupWindow.setOutsideTouchable(true);
//                popupWindow.setFocusable(true);
//                popupWindow.setAnimationStyle(R.style.dialogTopAnimation);
//                ColorDrawable colorDrawable = new ColorDrawable(getResources().getColor(R.color.colorPrimaryDark));
//                popupWindow.setBackgroundDrawable(colorDrawable);
//                popupWindow.showAsDropDown(appBar);

                final ListPopupWindow listPopupWindow = new ListPopupWindow(context);
                listPopupWindow.setWidth(ListPopupWindow.MATCH_PARENT);
                listPopupWindow.setHeight(ListPopupWindow.WRAP_CONTENT);
                ColorDrawable listColorDrawable = new ColorDrawable(getResources().getColor(R.color.ehr_bg));
                listPopupWindow.setBackgroundDrawable(listColorDrawable);
                listPopupWindow.setAdapter(new PopupWindowAdapter());
                listPopupWindow.setAnchorView(appBar);
                listPopupWindow.setModal(true);//设置为true响应物理键
                listPopupWindow.setSelection(3);
                // setOnItemClickListener要写到show前才生效
//                listPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        view.setBackgroundResource(R.color.colorAccent);
//                        Toast.makeText(context, position + " click", Toast.LENGTH_SHORT).show();
//                        listPopupWindow.dismiss();
//                    }
//                });
                listPopupWindow.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Log.d("Select", "position=" + position);
                        listPopupWindow.dismiss();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        Log.d("Select", "c=" + parent.getCount());
                    }
                });
                listPopupWindow.show();
            }
        });

        EditText editText = findViewById(R.id.editText);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "" + R.id.editText, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class PopupWindowAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(context).inflate(R.layout.dialog_top, parent, false);
            return convertView;
        }
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

        //noinspection SimplifiableIfStatemen
        if (id == R.id.action_settings) {
//            DatePicker calendarView = new DatePicker(context);
//            ((ViewGroup)getWindow().getDecorView()).addView(calendarView);

            DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    Toast.makeText(context, String.format(Locale.CHINA, "%02d年%02d月%02d日", year, month + 1, dayOfMonth), Toast.LENGTH_SHORT).show();
                }
            }, 1990, 0, 1);
            datePickerDialog.show();
            return true;
        }

        if (id == R.id.action_datepicker_year) {
            Calendar calendar = Calendar.getInstance();
            int nowYear = calendar.get(Calendar.YEAR);
            Log.d("Select", "nowYear=" + nowYear);
            final String[] ss = new String[nowYear - 1900 + 1];
            for (int i = 0; i <= (nowYear - 1900); i++) {
                ss[i] = 1900 + i + "";
            }
            new AlertDialog.Builder(context)
                    .setSingleChoiceItems(ss, 90, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Toast.makeText(context, ss[which], Toast.LENGTH_SHORT).show();
                        }
                    })
                    .show();
            return true;
        }

        if (id == R.id.action_datepicker_year2) { // 这个不行
            NumberPicker numberPicker = new NumberPicker(context);
            numberPicker.setMinValue(1900);
            numberPicker.setMaxValue(Calendar.getInstance().get(Calendar.YEAR));
            numberPicker.setValue(1990);
            numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    Toast.makeText(context, newVal + "", Toast.LENGTH_SHORT).show();
                }
            });
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            numberPicker.setLayoutParams(layoutParams);
            ((ViewGroup) getWindow().getDecorView()).addView(numberPicker);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    BaseAdapter adapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    };

}
