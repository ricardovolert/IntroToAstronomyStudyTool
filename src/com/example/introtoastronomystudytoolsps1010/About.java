package com.example.introtoastronomystudytoolsps1010;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.AdapterView.OnItemClickListener;
import com.cvballa3g0.introtoastronomystudytoolsps1010.R;

public class About extends Activity {

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
    public static Button[] nextButton = new Button[20];
    public static Button[] prevButton = new Button[20];
    public static FrameLayout[] questions = new FrameLayout[20];
    public static int number = 0;
    public static AlertDialog dialog;
    public Button facebookButton;

    FrameLayout.LayoutParams menuPanelParameters;
    FrameLayout.LayoutParams slidingPanelParameters;
    LinearLayout.LayoutParams headerPanelParameters;
    LinearLayout.LayoutParams listViewParameters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        View MAINSCREEN = findViewById(R.id.aboutRelative);
        Drawable background = MAINSCREEN.getBackground();
        background.setAlpha(100);
        initialize();

        Button rateButton = (Button) findViewById(R.id.playStoreButton);
        Button emailButton = (Button) findViewById(R.id.emailButton);

        facebookButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                dialog = ProgressDialog.show(About.this, "Launching Facebook",
                        "Please wait...");
                Thread thread = new Thread(new Runnable() {
                    public void run() {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri
                                .parse("fb://profile/832902177"));
                        try {
                            startActivity(intent);
                        } catch (ActivityNotFoundException e) {
                            openWebsite("https://www.facebook.com/Cvballa3g0");
                        }
                        dialog.dismiss();
                    }
                });
                thread.start();
            }
        });

        rateButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebsite("https://play.google.com/store/apps/details?id=com.cvballa3g0.introtoastronomystudytoolsps1010#?t=W251bGwsMSwxLDIxMiwiY29tLmN2YmFsbGEzZzAuaW50cm90b2FzdHJvbm9teXN0dWR5dG9vbHNwczEwMTAiXQ..");
            }
        });
        emailButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });

        categories.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                    int position, long id) {
                Intent intent;
                switch (position) {
                case (0):
                    newScreen(MainActivity.class);
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
                    slidePanel();
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

    protected void openWebsite(String website) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(website));
        startActivity(browserIntent);

    }

    public void newScreen(Class yolo) {
        slidePanel();
        Intent intent = new Intent(getApplicationContext(), yolo);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

    private void sendEmail() {
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_EMAIL,
                new String[] { "Cvballa3g0@gmail.com" });
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
                "Intro to Astronomy App");
        emailIntent.setType("plain/text");
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT,
                "\n\n\n- Sent from Intro to Astronomy App");
        startActivity(emailIntent);
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
        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        panelWidth = (int) ((metrics.widthPixels) * 0.75);

        headerPanel = (RelativeLayout) findViewById(R.id.aboutHeader);
        headerPanelParameters = (LinearLayout.LayoutParams) headerPanel
                .getLayoutParams();
        headerPanelParameters.width = metrics.widthPixels;
        headerPanel.setLayoutParams(headerPanelParameters);

        menuPanel = (RelativeLayout) findViewById(R.id.aboutMenuPanel);
        menuPanelParameters = (FrameLayout.LayoutParams) menuPanel
                .getLayoutParams();
        menuPanelParameters.width = panelWidth;
        menuPanel.setLayoutParams(menuPanelParameters);

        slidingPanel = (LinearLayout) findViewById(R.id.aboutSlidingPanel);
        slidingPanelParameters = (FrameLayout.LayoutParams) slidingPanel
                .getLayoutParams();
        slidingPanelParameters.width = metrics.widthPixels;
        slidingPanel.setLayoutParams(slidingPanelParameters);

        categories = (ListView) findViewById(R.id.aboutList);
        categories.setAdapter(MainActivity.adapter);
        facebookButton = (Button) findViewById(R.id.facebookButton);
        menuViewButton = (ImageView) findViewById(R.id.aboutMenuViewButton);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }
}
