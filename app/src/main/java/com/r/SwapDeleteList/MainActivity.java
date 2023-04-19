package com.r.SwapDeleteList;

import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.hudomju.swipe.SwipeToDismissTouchListener;
import com.hudomju.swipe.adapter.ListViewAdapter;
import com.r.swaplistview.R;
import com.r.swaplistview.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private CustomAdapter customAdapter;
    private ArrayList<ModelSwap> modelArrayList;
    private final String[] nameList = new String[]{"List1", "List2", "List3", "List4", "List5", "List6", "List7", "List8"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initView();
    }

    private void initView() {
        modelArrayList = populateList();
        customAdapter = new CustomAdapter(this, modelArrayList);
        binding.listview.setAdapter(customAdapter);

        final SwipeToDismissTouchListener<ListViewAdapter> touchListener = new SwipeToDismissTouchListener<>(
                new ListViewAdapter(binding.listview),
                new SwipeToDismissTouchListener.DismissCallbacks<ListViewAdapter>() {
                    @Override
                    public boolean canDismiss(int position) {
                        return true;
                    }

                    @Override
                    public void onDismiss(ListViewAdapter view, int position) {
                        customAdapter.remove(position);
                    }
                });

        binding.listview.setOnTouchListener(touchListener);
        binding.listview.setOnScrollListener((AbsListView.OnScrollListener) touchListener.makeScrollListener());
        binding.listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (touchListener.existPendingDismisses()) {
                    touchListener.undoPendingDismiss();
                } else {
                    Toast.makeText(MainActivity.this, "Position " + position, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private ArrayList<ModelSwap> populateList() {
        ArrayList<ModelSwap> list = new ArrayList<>();
        for (int i = 0; i < nameList.length; i++) {
            ModelSwap model = new ModelSwap();
            model.setName(nameList[i]);
            list.add(model);
        }

        return list;
    }
}

