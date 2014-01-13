package com.example.introtoastronomystudytoolsps1010;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import com.cvballa3g0.introtoastronomystudytoolsps1010.R;

public class MainActivity extends Activity {
    protected static final String VERSION = "1.05";
    // Declare
    private LinearLayout slidingPanel;
    private boolean isExpanded;
    private DisplayMetrics metrics;
    private ListView categories;
    private RelativeLayout headerPanel;
    private RelativeLayout menuPanel;
    private int panelWidth;
    private ImageView menuViewButton;
    public static ArrayAdapter<String> adapter;

    FrameLayout.LayoutParams menuPanelParameters;
    FrameLayout.LayoutParams slidingPanelParameters;
    LinearLayout.LayoutParams headerPanelParameters;
    LinearLayout.LayoutParams listViewParameters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        checkUpdate();
        RelativeLayout MAINSCREEN = (RelativeLayout) findViewById(R.id.MAINSCREEN);
        Drawable background = MAINSCREEN.getBackground();
        background.setAlpha(100);
        initialize();

        categories.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                    int position, long id) {
                switch (position) {
                case (0):
                    slidePanel();
                    break;
                case (1):
                    newScreen(Matching.class);
                    break;
                case (2):
                    newScreen(MultipleChoice.class);
                    break;
                case (3):
                    newScreen(ShortAnswer.class);
                    break;
                case (4):
                    newScreen(Homework.class);
                    break;
                case (5):
                    newScreen(PowerPoint.class);
                    break;
                case (6):
                    newScreen(About.class);
                    break;
                }
            }
        });

        menuViewButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                slidePanel();
            }
        });

    }

    protected void newScreen(Class yolo) {
        slidePanel();
        Intent intent = new Intent(getApplicationContext(), yolo);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

    protected void slidePanel() {
        if (!isExpanded) {
            categories.setVisibility(0);
            isExpanded = true;

            // Expand
            new ExpandAnimation(slidingPanel, panelWidth,
                    Animation.RELATIVE_TO_SELF, 0.0f,
                    Animation.RELATIVE_TO_SELF, 0.75f, 0, 0.0f, 0, 0.0f);
        } else {
            isExpanded = false;

            // Collapse
            new CollapseAnimation(slidingPanel, panelWidth,
                    TranslateAnimation.RELATIVE_TO_SELF, 0.75f,
                    TranslateAnimation.RELATIVE_TO_SELF, 0.0f, 0, 0.0f, 0, 0.0f);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    categories.setVisibility(4);
                }
            }, 400);
        }
    }

    public void initialize() {
        String[] CATEGORIES = new String[] { "Home", "Scientists/Matching",
                "Multiple Choice", "Daily Quizes/Short Answer", "Homework",
                "PowerPoint Slides", "About" };
        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        panelWidth = (int) ((metrics.widthPixels) * 0.75);

        headerPanel = (RelativeLayout) findViewById(R.id.header);
        headerPanelParameters = (LinearLayout.LayoutParams) headerPanel
                .getLayoutParams();
        headerPanelParameters.width = metrics.widthPixels;
        headerPanel.setLayoutParams(headerPanelParameters);

        menuPanel = (RelativeLayout) findViewById(R.id.menuPanel);
        menuPanelParameters = (FrameLayout.LayoutParams) menuPanel
                .getLayoutParams();
        menuPanelParameters.width = panelWidth;
        menuPanel.setLayoutParams(menuPanelParameters);

        slidingPanel = (LinearLayout) findViewById(R.id.slidingPanel);
        slidingPanelParameters = (FrameLayout.LayoutParams) slidingPanel
                .getLayoutParams();
        slidingPanelParameters.width = metrics.widthPixels;
        slidingPanel.setLayoutParams(slidingPanelParameters);

        categories = (ListView) findViewById(R.id.list);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, CATEGORIES);
        categories.setAdapter(adapter);
        menuViewButton = (ImageView) findViewById(R.id.menuViewButton);

    }

    protected void showAlertDialog() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getApplicationContext());
        alertDialogBuilder.setTitle("No PDF Reader Found");
        alertDialogBuilder
                .setMessage(
                        "Android did not find a PDF Reader application on your phone.")
                .setCancelable(false)
                .setNegativeButton("Close",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog,
                                    final int id) {
                            }
                        })
                .setPositiveButton("Download Adobe Reader",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(final DialogInterface dialog,
                                    final int id) {
                                Intent browserIntent = new Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse("https://play.google.com/store/apps/details?id=com.adobe.reader&hl=en"));
                                startActivity(browserIntent);
                            }
                        });
        final AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    private void checkUpdate() {
        int sdf = 0;
//        Thread Timer = new Thread() {
//            public void run() {
//                try {
//                    sleep(1);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } finally {
//                    String nextLine;
//                    URL url = null;
//                    URLConnection urlConn = null;
//                    InputStreamReader inStream = null;
//                    BufferedReader buff = null;
//                    try {
//                        url = new URL(
//                                "https://play.google.com/store/apps/details?id=com.cvballa3g0.introtoastronomystudytoolsps1010#?t=W251bGwsMSwxLDIxMiwiY29tLmN2YmFsbGEzZzAuaW50cm90b2FzdHJvbm9teXN0dWR5dG9vbHNwczEwMTAiXQ..");
//                        urlConn = url.openConnection();
//                        inStream = new InputStreamReader(
//                                urlConn.getInputStream());
//                        buff = new BufferedReader(inStream);
//
//                        nextLine = buff.readLine();
//                        while (nextLine
//                                .indexOf("<dd itemprop=\"softwareVersion\">") == -1) {
//                            nextLine = buff.readLine();
//                        }
//                        String subupdate = nextLine
//                                .substring(
//                                        (nextLine
//                                                .indexOf("=\"softwareVersion\">") + 19),
//                                        (nextLine.indexOf("</dd><dt itemprop=")));
//                        if (subupdate.equals(VERSION)) {
//
//                        } else {
//                            handler.sendEmptyMessage(0);
//
//                        }
//                    } catch (MalformedURLException e) {
//                    } catch (IOException e1) {
//                    }
//                }
//            }
//        };
//
//        Timer.start();

    }

    public void Dialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this)
                .setTitle("Update!")
                .setMessage(
                        "A new version of Intro to Astronomy Study Tool is available.\n\n"
                                + "It is strongly recommended to update, because new content might have been added.")
                .setPositiveButton("Update Now",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                    int which) {
                                Intent browserIntent = new Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse("https://play.google.com/store/apps/details?id=com.cvballa3g0.introtoastronomystudytoolsps1010#?t=W251bGwsMSwxLDIxMiwiY29tLmN2YmFsbGEzZzAuaW50cm90b2FzdHJvbm9teXN0dWR5dG9vbHNwczEwMTAiXQ.."));
                                startActivity(browserIntent);
                            }
                        })
                .setNegativeButton("Close",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                    int which) {
                            }
                        });
        alertDialog.setCancelable(false);
        alertDialog.show();

    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            Dialog();
        }
    };
}
