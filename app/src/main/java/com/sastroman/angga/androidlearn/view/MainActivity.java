package com.sastroman.angga.androidlearn.view;

/**
 * Created by Angga N P on 12/13/2017.
 */

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sastroman.angga.androidlearn.R;
import com.sastroman.angga.androidlearn.helper.SQLiteHandler;
import com.transitionseverywhere.Fade;
import com.transitionseverywhere.Recolor;
import com.transitionseverywhere.TransitionSet;
import com.transitionseverywhere.extra.Scale;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener{

    private Window window;
    boolean doubleBackToExitPressedOnce = false;

    private int[] tabIcons = {
            R.drawable.ic_action_product1,
            R.drawable.ic_action_product2,
            R.drawable.ic_action_product3,
            R.drawable.ic_action_product4
    };

    @ViewById
    TextView name, email;
    @ViewById
    ImageView ppic;
    @ViewById
    Toolbar toolbar;
    @ViewById
    LinearLayout nav_header_container, container_toolbar;
    @ViewById
    TabLayout tabs;
    @ViewById
    ViewPager viewpager;
    @ViewById
    AppBarLayout appbar;
    @ViewById
    FrameLayout container_body;
    @ViewById
    Button btnLogout;

    @AfterViews
    void afterView(){
        window = this.getWindow();
        SQLiteHandler db = new SQLiteHandler(this.getApplicationContext());

        HashMap<String, String> user = db.getUserDetails();
        name.setText(user.get("name"));
        email.setText(user.get("email"));

        setSupportActionBar(toolbar);

        //Setup View
        setupViewPager(viewpager);
        tabs.setupWithViewPager(viewpager);
        setupTabIcons();

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                changeView(tabs.getSelectedTabPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        FragmentDrawer drawerFragment = (FragmentDrawer) getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);
        drawerFragment.setDrawerListener(this);

        // display the first navigation drawer view on app launch
        displayView(0);

    }

    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click back again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    //Navigation for Drawer
    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);

        switch (position) {
            case 0:
                displayTabs();
                getSupportActionBar().setTitle(R.string.title_home);
                break;

            case 1:
                hideTabs();
                fragment = new FragmentAccount();
                title = getString(R.string.title_account);
                break;

            case 2:
                hideTabs();
                fragment = new FragmentHistory();
                title = getString(R.string.title_history);
                break;

            case 3:
                hideTabs();
                fragment = new FragmentFeedback();
                title = getString(R.string.title_help_feedback);
                break;

            default:
                displayTabs();
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }

    //Change View of the tabs and navigation bar
    private void changeView(int position) {

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        //get current toolbar & navigation bar color
        Drawable toolbarBackground = toolbar.getBackground();
        Drawable navBackground = nav_header_container.getBackground();
        int tColor = ((ColorDrawable)toolbarBackground).getColor();
        int nColor = ((ColorDrawable)navBackground).getColor();
        int wColor = window.getStatusBarColor();

        //switching between position
        switch (position) {
            case 0:

                getSupportActionBar().setTitle(R.string.title_product1);
                WAC(wColor, getResources().getColor(R.color.colorPrimaryDark));
                TAC(tColor, getResources().getColor(R.color.colorPrimary));
                NAC(nColor, getResources().getColor(R.color.colorAccent));
                //appbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                //toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                //window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
                //nav_header_container.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                break;

            case 1:

                getSupportActionBar().setTitle(R.string.title_product2);
                // finally change the color
                //appbar.setBackgroundColor(getResources().getColor(R.color.greenPrimary));
                //toolbar.setBackgroundColor(getResources().getColor(R.color.greenPrimary));
                WAC(wColor, getResources().getColor(R.color.redPrimaryDark));
                TAC(tColor, getResources().getColor(R.color.redPrimary));
                NAC(nColor, getResources().getColor((R.color.redAccent)));

                //recolor(getResources().getColor(R.color.greenPrimary));

                //window.setStatusBarColor(ContextCompat.getColor(this, R.color.greenPrimaryDark));
                //nav_header_container.setBackgroundColor(getResources().getColor(R.color.greenAccent));
                break;

            case 2:

                getSupportActionBar().setTitle(R.string.title_product3);

                WAC(wColor, getResources().getColor(R.color.greenPrimaryDark));
                TAC(tColor, getResources().getColor(R.color.greenPrimary));
                NAC(nColor, getResources().getColor(R.color.greenAccent));
                //appbar.setBackgroundColor(getResources().getColor(R.color.redPrimary));
                //toolbar.setBackgroundColor(getResources().getColor(R.color.redPrimary));
                //window.setStatusBarColor(ContextCompat.getColor(this, R.color.redPrimaryDark));
                //nav_header_container.setBackgroundColor(getResources().getColor(R.color.redAccent));
                break;

            case 3:

                getSupportActionBar().setTitle(R.string.title_product4);

                WAC(wColor, getResources().getColor(R.color.magentaPrimaryDark));
                TAC(tColor, getResources().getColor(R.color.magentaPrimary));
                NAC(nColor, getResources().getColor((R.color.magentaAccent)));

                // finally change the color
                //appbar.setBackgroundColor(getResources().getColor(R.color.magentaPrimary));
                //toolbar.setBackgroundColor(getResources().getColor(R.color.magentaPrimary));
                //window.setStatusBarColor(ContextCompat.getColor(this, R.color.magentaPrimaryDark));
                //nav_header_container.setBackgroundColor(getResources().getColor(R.color.magentaAccent));
                break;

            default:

                getSupportActionBar().setTitle(R.string.title_product1);

                WAC(wColor, getResources().getColor(R.color.colorPrimaryDark));
                TAC(tColor, getResources().getColor(R.color.colorPrimary));
                NAC(nColor, getResources().getColor(R.color.colorAccent));

                //appbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                //toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                //window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
                //nav_header_container.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                break;
        }
    }


    //Animate Color
    private void WAC(int colorfrom, int colorto) {
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        ValueAnimator colorWindow = ValueAnimator.ofObject(new ArgbEvaluator(), colorfrom, colorto);
        colorWindow.setDuration(250); // milliseconds
        colorWindow.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                window.setStatusBarColor((int) animator.getAnimatedValue());
            }

        });
        colorWindow.start();
    }
    private void TAC(int colorfrom, int colorto) {
        ValueAnimator colorToolbar = ValueAnimator.ofObject(new ArgbEvaluator(), colorfrom, colorto);
        colorToolbar.setDuration(250); // milliseconds
        colorToolbar.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                toolbar.setBackgroundColor((int) animator.getAnimatedValue());
                appbar.setBackgroundColor((int) animator.getAnimatedValue());
                btnLogout.setBackgroundColor((int) animator.getAnimatedValue());
            }

        });
        colorToolbar.start();
    }
    private void NAC (int colorfrom, int colorto){
        ValueAnimator colorNav = ValueAnimator.ofObject(new ArgbEvaluator(),colorfrom, colorto);
        colorNav.setDuration(250); // milliseconds
        colorNav.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                nav_header_container.setBackgroundColor((int) animator.getAnimatedValue());
            }

        });
        colorNav.start();
    }


    //Create Tab from here
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentProduct1(), "ONE");
        adapter.addFragment(new FragmentProduct2(), "TWO");
        adapter.addFragment(new FragmentProduct3(), "THREE");
        adapter.addFragment(new FragmentProduct4(), "FOUR");
        viewPager.setAdapter(adapter);
    }

    //Setting tab icons
    private void setupTabIcons() {
        tabs.getTabAt(0).setIcon(tabIcons[0]);
        tabs.getTabAt(1).setIcon(tabIcons[1]);
        tabs.getTabAt(2).setIcon(tabIcons[2]);
        tabs.getTabAt(3).setIcon(tabIcons[3]);

    }

    //Setting Adapter
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {

            return mFragmentList.get(position);

        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {

            return null;
        }
    }

    //No more question?
    private void displayTabs(){
        tabs.setVisibility(View.VISIBLE);
        viewpager.setVisibility(View.VISIBLE);
        container_body.setVisibility(View.GONE);
    }

    private void hideTabs(){
        tabs.setVisibility(View.GONE);
        viewpager.setVisibility(View.GONE);
        container_body.setVisibility(View.VISIBLE);
    }

}
