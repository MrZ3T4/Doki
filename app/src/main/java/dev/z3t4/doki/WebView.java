package dev.z3t4.doki;

import android.os.Bundle;
import android.webkit.WebSettings;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebView extends AppCompatActivity {


    @BindView(R.id.web_view)
    android.webkit.WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);

        ButterKnife.bind(this);
        ButterKnife.setDebug(true);

        Bundle bundle = getIntent().getExtras();

        String url = null;
        if (bundle != null) {
            url = bundle.getString("url");
        }

        WebSettings webSettings = webView.getSettings();
        webSettings.setLoadsImagesAutomatically(true);
        webView.loadUrl(url);

    }
}