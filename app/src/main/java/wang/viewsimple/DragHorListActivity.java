package wang.viewsimple;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DragHorListActivity extends AppCompatActivity {

    AppCompatActivity activity;
    RecyclerView recyclerView;
    ItemTouchHelper itemTouchHelper;
    RecyclerView.Adapter adapter;
    List<Integer> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_hor_list);
        activity = this;
        recyclerView = findViewById(R.id.rcv);
        datas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            datas.add(i);
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false);
//        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MAdapter();
        recyclerView.setAdapter(adapter);

        itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    class MAdapter extends RecyclerView.Adapter<MViewHolder> {

        @Override
        public MViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(activity).inflate(R.layout.item_image, parent, false);
            return new MViewHolder(view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(final MViewHolder holder, int position) {
            holder.textView.setText("" + datas.get(position));
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }
    }

    class MViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;

        MViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv);
            textView = itemView.findViewById(R.id.tv);
        }
    }

    ItemTouchHelper.Callback callback = new ItemTouchHelper.Callback() {

        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (linearLayoutManager.getOrientation() == LinearLayoutManager.HORIZONTAL) {
                    Log.i("ItemTouchHelper", "设置了Flag");
                    int dragFlags = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                    int swipeFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                    return makeMovementFlags(dragFlags, swipeFlags);
                }
            }
            return 0;
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            if (viewHolder.getItemViewType() != target.getItemViewType()) return false;
            Collections.swap(datas, viewHolder.getAdapterPosition(), target.getAdapterPosition());
            adapter.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            datas.remove(viewHolder.getAdapterPosition());
            adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
        }

        @Override
        public boolean isLongPressDragEnabled() {
            return true;
        }

        @Override
        public boolean isItemViewSwipeEnabled() {
            return false;
        }

        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder
                , float dX, float dY, int actionState, boolean isCurrentlyActive) {
            // 要先判断为横向LinearLayoutManager
            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                if (Math.abs(dY) > viewHolder.itemView.getHeight()) {
                    dY = viewHolder.itemView.getHeight();
                }
                Log.i("ItemTouchHelper", "ACTION_STATE_SWIPE.onChildDraw.dy=" + dY);
                float alpha = ((float) viewHolder.itemView.getHeight() - Math.abs(dY)) / (float) viewHolder.itemView.getHeight();
                viewHolder.itemView.setAlpha(alpha);
                viewHolder.itemView.setTranslationY(dY);
            } else {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }

        @Override
        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
            if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                viewHolder.itemView.setBackgroundColor(Color.LTGRAY);
                viewHolder.itemView.setScaleX(1.05f);
                viewHolder.itemView.setScaleY(1.05f);
                viewHolder.itemView.setTranslationZ(20);
            } else {
                super.onSelectedChanged(viewHolder, actionState);
            }
        }

        @Override
        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            super.clearView(recyclerView, viewHolder);
            viewHolder.itemView.setBackgroundColor(activity.getWindow().getDecorView().getDrawingCacheBackgroundColor());
            viewHolder.itemView.setScaleX(1.0f);
            viewHolder.itemView.setScaleY(1.0f);
            viewHolder.itemView.setTranslationZ(0);
        }
    };
}
