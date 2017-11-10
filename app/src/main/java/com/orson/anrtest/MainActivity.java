package com.orson.anrtest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String PHRASES = "{\n" +
            "  \"phrasesroot\": {\n" +
            "    \"phrasesgroup\": {\n" +
            "      \"name\": \"常用\",\n" +
            "      \"id\": \"basephrases\",\n" +
            "      \"phrase\": [\n" +
            "        \"常用的信息可以在这里保存，便于发送。\",\n" +
            "        \"我的电话是__，常联系。\",\n" +
            "        \"__省__市__街道__小区__单元__室\",\n" +
            "        \"我现在有点忙，稍后联系你。\"\n" +
            "      ]\n" +
            "    }\n" +
            "  }\n" +
            "}";
    private static final String p = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<phrasesroot>\n" +
            "<phrasesgroup name=\"常用\" id=\"basephrases\" >\n" +
            "    <phrase><![CDATA[常用的信息可以在这里保存，便于发送。]]></phrase>\n" +
            "    <phrase><![CDATA[我的电话是__，常联系。]]></phrase>\n" +
            "    <phrase><![CDATA[__省__市__街道__小区__单元__室]]></phrase>\n" +
            "    <phrase><![CDATA[我现在有点忙，稍后联系你。]]></phrase>\n" +
            "</phrasesgroup>\n" +
            "</phrasesroot>";
    private static final String BUNDLE = "Bundle[{Age=25, Name=orson, Location=Beijing}]";
    private Toast mToast;
    private TextView mTxtScreenHeight = null;
    private TextView mTxtScreenWeight = null;
    private Button mBtnShowScreenSizeA = null;
    private Button mBtnShowScreenSizeB = null;
    private Button mBtnShowScreenSizeC = null;
    private ImageView mImageView = null;
    private WebView mWebView = null;
    private Button mBtnIntent = null;
    private Bundle mBundle;
    private JSONObject mJObject;
    private int mScreenHeight;
    private int mScreenWeight;
    private ShortcutPhrasesGroupInfo mInfo;
    private ArrayList<ShortcutPhrasesGroupInfo> mList;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                showTips(msg.obj.toString());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initWebView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("orson_debug", "onResume");
    }

    private void initView() {
        mTxtScreenHeight = (TextView) findViewById(R.id.txt_screen_height);
        mTxtScreenWeight = (TextView) findViewById(R.id.txt_screen_width);
        mBtnShowScreenSizeA = (Button) findViewById(R.id.btn_show_screen_size_a);
        mBtnShowScreenSizeA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getScreenSizeA();
                mTxtScreenHeight.setText(getResources().getString(R.string.screen_height, mScreenHeight));
                mTxtScreenWeight.setText(getResources().getString(R.string.screen_width, mScreenWeight));
            }
        });
        mBtnShowScreenSizeB = (Button) findViewById(R.id.btn_show_screen_size_b);
        mBtnShowScreenSizeB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getScreenSizeB();
                mTxtScreenHeight.setText(getResources().getString(R.string.screen_height, mScreenHeight));
                mTxtScreenWeight.setText(getResources().getString(R.string.screen_width, mScreenWeight));
            }
        });
        mBtnShowScreenSizeC = (Button) findViewById(R.id.btn_show_screen_size_c);
        mBtnShowScreenSizeC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getScreenSizeC();
                mTxtScreenHeight.setText(getResources().getString(R.string.screen_height, mScreenHeight));
                mTxtScreenWeight.setText(getResources().getString(R.string.screen_width, mScreenWeight));
            }
        });

        mImageView = (ImageView) findViewById(R.id.img);
        mImageView.setImageDrawable(getResources().getDrawable(R.mipmap.haha4));

        mWebView = (WebView) findViewById(R.id.webView);
        mWebView.setWebViewClient(new MyWebViewClient());

        mBtnIntent = (Button) findViewById(R.id.btn_intent);
        mBtnIntent.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mBtnIntent.setText(Intent.FLAG_ACTIVITY_NEW_TASK + "");
        mBtnIntent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mWebView.loadUrl("https://item.m.jd.com/product/11452170620.html?ad_od=1");
//                try {
////                    String url = URLEncoder.encode("https://h5.m.taobao.com/awp/core/detail.htm?id=537708308852", "utf-8");
//                    String url = "";
//                    String taobaoData = "openapp.jdmobile://virtual?params={\\\"category\\\":\\\"jump\\\",\\\"des\\\":\\\"productDetail\\\",\\\"skuId\\\":\\\"11452170620\\\",\\\"sourceType\\\":\\\"list\\\",\\\"sourceValue\\\":\\\"672\\\",\\\"M_sourceFrom\\\":\\\"Btop\\\",\\\"m_param\\\":{\\\"m_source\\\":\\\"0\\\",\\\"event_series\\\":{},\\\"jda\\\":\\\"225424388.1875742824.1482217430117.1482299780524.1482302512639.10\\\",\\\"usc\\\":\\\"direct\\\",\\\"ucp\\\":\\\"-\\\",\\\"umd\\\":\\\"none\\\",\\\"utr\\\":\\\"-\\\",\\\"jdv\\\":\\\"225424388%257Cdirect%257C-%257Cnone%257C-%257C1482217430119\\\",\\\"ref\\\":\\\"https%3A%2F%2Fitem.m.jd.com%2Fproduct%2F2078181.html%3Fsid%3Dbbbf7fe3ceacb053d6d6c27b88efcfc1\\\",\\\"psn\\\":\\\"1875742824|10\\\",\\\"psq\\\":5,\\\"unpl\\\":\\\"\\\",\\\"mba_muid\\\":\\\"1875742824\\\",\\\"mba_sid\\\":\\\"14823024982703865311971089160\\\",\\\"mt_xid\\\":\\\"\\\",\\\"mt_subsite\\\":\\\"\\\"},\\\"SE\\\":{\\\"mt_subsite\\\":\\\"\\\",\\\"__jdv\\\":\\\"225424388%257Cdirect%257C-%257Cnone%257C-%257C14822174301\\n19\\\",\\\"unpl\\\":\\\"\\\",\\\"__jda\\\":\\\"225424388.1875742824.1482217430117.1482299780524.1482302512639.10\\\"}}";
//                    Uri uri = Uri.parse(taobaoData);
//                    Intent intent = new Intent();
////                    intent.setPackage("com.taobao.taobao");
//                    intent.setData(uri);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    showTips(intent.getPackage());
//                    startActivity(intent);
//                } catch (Exception e) {
//                    showTips(e.toString());
//                }
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                Log.e("orson_debug", "startActivityForResult");
                startActivityForResult(intent, 100);
            }
        });
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return checkToStartOtherApp(url);
        }

        @SuppressLint("NewApi")
        @Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
            // 非超链接(如Ajax)请求无法直接添加请求头，现拼接到url末尾,这里拼接一个imei作为示例

            String ajaxUrl = url;
            // 如标识:req=ajax
            if (url.contains("req=ajax")) {
                ajaxUrl += "&imei=" + "XXXXXXXX";
            }

            return super.shouldInterceptRequest(view, ajaxUrl);

        }
    }

    private boolean checkToStartOtherApp(String url) {
        if (url.startsWith("http") || url.startsWith("https://"))
            return false;
        else {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
            return true;
        }
    }

    private void initWebView() {
        try {
            String path = "http://www.baidu.com/s/orson m";
            URL url = null;
            URI uri = null;
            try {
                url = new URL(path);
                System.out.println("url.getProtocol() : " + url.getProtocol());
                System.out.println("url.getHost() : " + url.getHost());
                System.out.println("url.getPort() : " + url.getPort());
                System.out.println("url.getQuery() : " + url.getQuery());
                uri = new URI(url.getProtocol(), url.getHost() + ":" + url.getPort(), url.getPath(), url.getQuery(), null);
            } catch (MalformedURLException e) {
                uri = new URI(urlTransfer(path));
            }
            mWebView.loadUrl(urlTransfer(path));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public ShortcutPhrasesGroupInfo parseShortcutPhrasesFromJson(String fileString) {
        if (fileString == null) return null;
        ShortcutPhrasesGroupInfo info = new ShortcutPhrasesGroupInfo();
        try {
            JSONObject rootJsonObject = new JSONObject(fileString);
            String groupObjectStr = rootJsonObject.optString("phrasesroot");
            mList = new ArrayList<>();
            JSONObject groupObject = new JSONObject(groupObjectStr);
            String infoObjectString = groupObject.optString("phrasesgroup");
            JSONObject infoObject = new JSONObject(infoObjectString);
            setCurrentGroupInfo(infoObject);
            return info;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void setCurrentGroupInfo(JSONObject infoObject) throws JSONException {
        mInfo = new ShortcutPhrasesGroupInfo();
        mInfo.groupName = infoObject.optString("name");
        mInfo.groupId = infoObject.optString("id");
        mInfo.describe = infoObject.optString("describe");
        mInfo.category = 1;
        mInfo.phrases = new ArrayList<>();
        JSONArray phraseArray = infoObject.optJSONArray("phrase");
        if (phraseArray != null && phraseArray.length() > 0) {
            for (int i = 0; i < phraseArray.length(); i++) {
                Log.e("orson", phraseArray.get(i).toString());
            }
        }
    }

    private String urlTransfer(String url) {
        return url.replace("+", "%2B")
                .replace(" ", "%20")
                .replace("/", "%2F")
                .replace("?", "%3F")
                .replace("%", "%25")
                .replace("#", "%23")
                .replace("&", "%26")
                .replace("=", "%3D");
    }

    private void getScreenSizeA() {
        DisplayMetrics display = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(display);
        mScreenHeight = display.heightPixels;
        mScreenWeight = display.widthPixels;
    }

    private void getScreenSizeB() {
        DisplayMetrics display = getResources().getDisplayMetrics();
        mScreenHeight = display.heightPixels;
        mScreenWeight = display.widthPixels;
    }

    private void getScreenSizeC() {
        Display display = getWindowManager().getDefaultDisplay();
        mScreenHeight = display.getHeight();
        mScreenWeight = display.getWidth();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTxtScreenHeight = null;
        mTxtScreenHeight = null;
        mBtnShowScreenSizeA = null;
        mBtnShowScreenSizeB = null;
        mBtnShowScreenSizeC = null;
    }


    private void showTips(CharSequence text) {
        if (mToast != null) {
            mToast.setDuration(Toast.LENGTH_SHORT);
            mToast.setText(text);
            mToast.show();
        } else {
            mToast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
            mToast.show();
        }
    }
}
