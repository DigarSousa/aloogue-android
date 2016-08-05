package alugueis.alugueis;

import alugueis.alugueis.adapter.ViewPageAdapter;
import alugueis.alugueis.model.Place;
import alugueis.alugueis.util.StaticUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;

import java.io.IOException;

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
        Bundle args = new Bundle();
        try {

            Place place = (Place) StaticUtil.readObject(this, StaticUtil.PLACE);
            args.putSerializable("place", place);

            PlaceInfoViewerFragment placeInfoFgm = new PlaceInfoViewerFragment();
            placeInfoFgm.setArguments(args);
            ProductListFragment productListFragment = new ProductListFragment();
            productListFragment.setArguments(args);

            viewPageAdapter.addFragment(placeInfoFgm, getString(R.string.placeViewerFormTitle));
            viewPageAdapter.addFragment(productListFragment, getString(R.string.placeViewerProductsTitle));
            viewPageAdapter.notifyDataSetChanged();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}