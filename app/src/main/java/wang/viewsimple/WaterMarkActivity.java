package wang.viewsimple;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class WaterMarkActivity extends AppCompatActivity {

    AppCompatActivity activity;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<Integer> datas;

    View viewWatermark;
    int moveY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_mark);
        activity = this;
        recyclerView = findViewById(R.id.rcv);
        viewWatermark = findViewById(R.id.vwm);

        datas = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            datas.add(i);
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MAdapter();
        recyclerView.setAdapter(adapter);
//        recyclerView.setBackgroundResource(R.drawable.bitmap_watermark);
//        recyclerView.setBackground(getWatermarkBitmapDrawable(activity, "公司水印"));

        Watermark watermark = new Watermark.Builder(activity, "公司水印")
                .setWidthDp(100)
                .setHeightDp(50)
                .build();
        viewWatermark.setBackground(watermark.getWatermarkBitmapDrawable());

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                moveY += dy;
                FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) viewWatermark.getLayoutParams();
                lp.topMargin = -moveY;
//                Log.i("WaterMarkActivity", "moveY=" + moveY);
                viewWatermark.setLayoutParams(lp);
            }
        });
    }

    class MAdapter extends RecyclerView.Adapter<MViewHolder> {

        @Override
        public MViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(activity).inflate(R.layout.item_watermark, parent, false);
            return new MViewHolder(view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(MViewHolder holder, int position) {
            holder.textView.setText("水印测试" + datas.get(position));
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }
    }

    class MViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        MViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv);
        }
    }

    BitmapDrawable getWatermarkBitmapDrawable(Context context, String text) {
        int size = 320;
        int degrees = -30;

        Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        paint.setColor(Color.GRAY);
        paint.setAlpha(80);
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setTextSize(40);

//        Path path = new Path();
//        path.moveTo(30, 150);
//        path.lineTo(300, 0);
//        canvas.drawTextOnPath(text, path, 0, 30, paint);

        canvas.rotate(degrees, 0, size);
        canvas.drawText(text, -degrees * 2, size, paint);

        BitmapDrawable bitmapDrawable = new BitmapDrawable(context.getResources(), bitmap);
        bitmapDrawable.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        bitmapDrawable.setDither(true);

        return bitmapDrawable;
    }

}
