package com.solution.brothergroup.PSA.UI;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.WindowInsetsControllerCompat;

import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.solution.brothergroup.R;
import com.solution.brothergroup.usefull.CustomLoader;

public class UTILogin extends AppCompatActivity {
    WebView panWeb;
    CustomLoader customLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowInsetsControllerCompat controller = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(true);
        controller.setAppearanceLightNavigationBars(true);
        setContentView(R.layout.activity_utilogin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setTitle("UTI Login Portal");
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_icon);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        customLoader = new CustomLoader(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);

        if (customLoader != null) {
            if (customLoader.isShowing())
                customLoader.dismiss();
        }
        panWeb = findViewById(R.id.panWeb);
        panWeb.setWebViewClient(new MyBrowser(customLoader));

        panWeb.getSettings().setLoadsImagesAutomatically(true);
        panWeb.getSettings().setJavaScriptEnabled(true);
        panWeb.setInitialScale(1);
        panWeb.getSettings().setLoadWithOverviewMode(true);
        panWeb.getSettings().setUseWideViewPort(true);
        panWeb.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        panWeb.setScrollbarFadingEnabled(false);

        panWeb.loadUrl("https://www.psaonline.utiitsl.com/psaonline/");


    }


    private class MyBrowser extends WebViewClient {

        private CustomLoader customLoader;


        public MyBrowser(CustomLoader customLoader) {
            this.customLoader = customLoader;
            customLoader.show();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (customLoader != null)
                if (customLoader.isShowing())
                    customLoader.dismiss();
        }
    }
}

