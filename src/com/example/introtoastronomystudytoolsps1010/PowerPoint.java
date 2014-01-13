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
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.AdapterView.OnItemClickListener;
import com.cvballa3g0.introtoastronomystudytoolsps1010.R;

public class PowerPoint extends Activity {

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

    FrameLayout.LayoutParams menuPanelParameters;
    FrameLayout.LayoutParams slidingPanelParameters;
    LinearLayout.LayoutParams headerPanelParameters;
    LinearLayout.LayoutParams listViewParameters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.powerpoint);
        View MAINSCREEN = findViewById(R.id.ppScrolling);
        Drawable background = MAINSCREEN.getBackground();
        background.setAlpha(50);
        initialize();
        Button ch1Button = (Button) findViewById(R.id.ch1Slides);
        Button ch2to3Button = (Button) findViewById(R.id.ch2to3Slides);
        Button ch4Button = (Button) findViewById(R.id.ch4Slides);
        Button ch5to6Button = (Button) findViewById(R.id.ch5to6Slides);
        Button ch16Button = (Button) findViewById(R.id.ch16Slides);
        Button ch17Button = (Button) findViewById(R.id.ch17Slides);

        ch1Button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                openPDF("ch1.pdf");
            }
        });
        ch2to3Button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                openPDF("ch2-3.pdf");
            }
        });

        ch4Button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                openPDF("ch4.pdf");
            }
        });
        ch5to6Button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                openPDF("ch5-6.pdf");
            }
        });
        ch16Button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                openPDF("ch16.pdf");
            }
        });
        ch17Button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                openPDF("ch17.pdf");
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
                    slidePanel();
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

    public void newScreen(Class yolo) {
        slidePanel();
        Intent intent = new Intent(getApplicationContext(), yolo);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

    protected void openPDF(final String fileName) {
        dialog = ProgressDialog.show(this, "Loading PDF", "Please wait...");
        Thread thread = new Thread(new Runnable() {
            public void run() {
                File pdfFile = new File(Environment
                        .getExternalStorageDirectory().getPath()
                        + "/Download/"
                        + fileName);
                if (!pdfFile.exists())
                    CopyAssetsbrochure(fileName);

                Uri path = Uri.fromFile(pdfFile);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(path, "application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                try {
                    dialog.dismiss();
                    startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    showAlertDialog();
                }
            }
        });
        thread.start();

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

        headerPanel = (RelativeLayout) findViewById(R.id.ppHeader);
        headerPanelParameters = (LinearLayout.LayoutParams) headerPanel
                .getLayoutParams();
        headerPanelParameters.width = metrics.widthPixels;
        headerPanel.setLayoutParams(headerPanelParameters);

        menuPanel = (RelativeLayout) findViewById(R.id.ppMenuPanel);
        menuPanelParameters = (FrameLayout.LayoutParams) menuPanel
                .getLayoutParams();
        menuPanelParameters.width = panelWidth;
        menuPanel.setLayoutParams(menuPanelParameters);

        slidingPanel = (LinearLayout) findViewById(R.id.ppSlidingPanel);
        slidingPanelParameters = (FrameLayout.LayoutParams) slidingPanel
                .getLayoutParams();
        slidingPanelParameters.width = metrics.widthPixels;
        slidingPanel.setLayoutParams(slidingPanelParameters);

        categories = (ListView) findViewById(R.id.ppList);
        // listViewParameters = (LinearLayout.LayoutParams) Categories
        // .getLayoutParams();
        // listViewParameters.width = metrics.widthPixels;
        // Categories.setLayoutParams(listViewParameters);

        categories.setAdapter(MainActivity.adapter);
        menuViewButton = (ImageView) findViewById(R.id.ppMatchingMenuViewButton);

    }

    protected void showAlertDialog() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                this);
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

    private void CopyAssetsbrochure(String fileName) {

        AssetManager assetManager = getAssets();
        String[] files = null;
        try {
            files = assetManager.list("");
        } catch (IOException e) {
            Log.e("tag", e.getMessage());
        }
        for (int i = 0; i < files.length; i++) {
            String fStr = files[i];
            if (fStr.equalsIgnoreCase(fileName)) {
                InputStream in = null;
                OutputStream out = null;
                try {
                    in = assetManager.open(files[i]);
                    out = new FileOutputStream(Environment
                            .getExternalStorageDirectory().getPath()
                            + "/Download/" + files[i]);
                    byte[] buffer = new byte[1024];
                    int read;
                    while ((read = in.read(buffer)) != -1) {
                        out.write(buffer, 0, read);
                    }
                    in.close();
                    in = null;
                    out.flush();
                    out.close();
                    out = null;
                    break;
                } catch (Exception e) {
                    Log.e("tag", e.getMessage());
                }
            }
        }
    }
}
