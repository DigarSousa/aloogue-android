package alugueis.alugueis;

import alugueis.alugueis.adapter.ViewPageAdapter;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PlaceViewerActivity extends AppCompatActivity {
    @BindView(R.id.place_viewer_toolbar)
    Toolbar toolbar;

    @BindView(R.id.place_viewer_tab_layout)
    TabLayout tabLayout;

    @BindView(R.id.place_viewer_view_pager)
    ViewPager viewPager;
    private ViewPageAdapter viewPageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_viewer_activity);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        initViewPager();
        initViewPagerFragments();
    }

    private void initViewPager() {
        viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPageAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void initViewPagerFragments() {
        viewPageAdapter.addFragment(new ProductListFragment(),"Products");
        viewPageAdapter.addFragment(new ProductListFragment(),"Outros");
        viewPageAdapter.notifyDataSetChanged();
    }
}