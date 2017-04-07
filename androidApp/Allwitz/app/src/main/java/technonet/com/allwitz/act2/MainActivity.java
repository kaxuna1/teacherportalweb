package technonet.com.allwitz.act2;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import technonet.com.allwitz.R;

public class MainActivity extends AppCompatActivity {


    FrameLayout frame;
    LinearLayout searchView;
    LinearLayout accoutView;
    LayoutInflater inflater;



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            frame.removeAllViews();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    frame.addView(searchView);
                    return true;
                case R.id.navigation_dashboard:
                    return true;
                case R.id.navigation_account:
                    frame.addView(accoutView);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        frame=(FrameLayout)findViewById(R.id.content);
        inflater = (LayoutInflater) this.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        searchView = (LinearLayout) inflater.inflate(R.layout.search_page_layout, null);
        accoutView = (LinearLayout) inflater.inflate(R.layout.account_page_layout, null);
        frame.addView(searchView);


    }

}
