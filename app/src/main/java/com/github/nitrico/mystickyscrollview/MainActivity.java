package com.github.nitrico.mystickyscrollview;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.github.nitrico.stickyscrollview.StickyScrollView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements StickyScrollView.OnStickyScrollViewListener {

    private String[] items = {
            "Shamika Verner", "Christie Tabon", "Keitha Ours", "Andreas Shorts", "Jestine Cruse",
            "Gail Perdue", "Michell Milano", "Venice Rocchio", "Celinda Leeper", "Norris Baltzell",
            "Barb Gaylord", "Chris Mass", "Rosalie Schow", "Lionel Yetter", "Krysten Lepak",
            "Marnie Duffin", "Jarod Camden", "Charlette Spalla", "Marg Lutes", "Awilda Pullen"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initTabLayout();

        RecyclerView recycler = (RecyclerView) findViewById(R.id.recycler);
        recycler.setNestedScrollingEnabled(false);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        ExampleAdapter adapter = new ExampleAdapter();
        adapter.setItems(items);
        recycler.setAdapter(adapter);

        StickyScrollView sticky = (StickyScrollView) findViewById(R.id.sticky);
        sticky.addOnStickyScrollViewListener(this);
    }

    private void initTabLayout() {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_header);

        TabLayout.Tab tab = tabLayout.newTab();
        tab.setText("First");
        tabLayout.addTab(tab);

        tab = tabLayout.newTab();
        tab.setText("Second");
        tabLayout.addTab(tab);

        tab = tabLayout.newTab();
        tab.setText("Third");
        tabLayout.addTab(tab);
    }

    @Override
    public void onScrollChanged(int x, int y, int oldX, int oldY) {

    }

    private static class ExampleAdapter extends RecyclerView.Adapter<ExampleViewHolder> {

        private static final int VIEW_TYPE_NON_STICKY = R.layout.item_text;
        private static final int VIEW_TYPE_STICKY = R.layout.item_text_sticky;

        private List<ListItem> items = new ArrayList<>();

        @Override
        public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(viewType, parent, false);
            return new ExampleViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ExampleViewHolder holder, int position) {
            holder.bind(items.get(position));
        }

        @Override
        public int getItemViewType(int position) {
            if (items.get(position) instanceof StickyListItem) {
                return VIEW_TYPE_STICKY;
            }
            return VIEW_TYPE_NON_STICKY;
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        void setItems(String[] newItems) {
            items.clear();
            for (String item : newItems) {
                items.add(new ListItem(item));
            }
            // sort items in natural order
            Collections.sort(items, new Comparator<ListItem>() {
                @Override
                public int compare(ListItem o1, ListItem o2) {
                    return o1.text.compareToIgnoreCase(o2.text);
                }
            });
            // categorize items by their first letter
            StickyListItem stickyListItem = null;
            for (int i = 0, size = items.size(); i < size; i++) {
                ListItem listItem = items.get(i);
                String firstLetter = String.valueOf(listItem.text.charAt(0));
                if (stickyListItem == null || !stickyListItem.text.equals(firstLetter)) {
                    stickyListItem = new StickyListItem(firstLetter);
                    items.add(i, stickyListItem);
                    size += 1;
                }
            }
            notifyDataSetChanged();
        }
    }

    private static class ExampleViewHolder extends RecyclerView.ViewHolder {
        private TextView text;
        ExampleViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);
        }
        void bind(ListItem item) {
            text.setText(item.text);
        }
    }

    private static class ListItem {
        protected String text;
        public ListItem(String text) {
            this.text = text;
        }
    }

    private static class StickyListItem extends ListItem {
        public StickyListItem(String text) {
            super(text);
        }
    }

}
