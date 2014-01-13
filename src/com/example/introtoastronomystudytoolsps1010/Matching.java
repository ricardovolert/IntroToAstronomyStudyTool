package com.example.introtoastronomystudytoolsps1010;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
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
import android.widget.Spinner;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import com.cvballa3g0.introtoastronomystudytoolsps1010.R;

public class Matching extends Activity {

    // Declare
    private LinearLayout slidingPanel;
    private boolean isExpanded;
    private DisplayMetrics metrics;
    private ListView categories;
    private RelativeLayout headerPanel;
    private RelativeLayout menuPanel;
    private int panelWidth;
    private ImageView menuViewButton;
    public static Spinner QUESTION[];
    public static String ANSWER[];
    public TextView matchingScore;
    public Button resetButton;
    public Button submitButton;
    public TextView peopleText[] = new TextView[14];

    FrameLayout.LayoutParams menuPanelParameters;
    FrameLayout.LayoutParams slidingPanelParameters;
    LinearLayout.LayoutParams headerPanelParameters;
    LinearLayout.LayoutParams listViewParameters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.matching);
        initialize();

        resetButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(getApplicationContext(),
                        Matching.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

        submitButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateScore();
            }
        });

        categories.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                    int position, long id) {
                switch (position) {
                case (0):
                    newScreen(MainActivity.class);
                    break;
                case (1):
                    slidePanel();
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

    public void calculateScore() {
        int right = 0;
        for (int i = 0; i < 14; i++) {
            String question = QUESTION[i].getSelectedItem().toString();
            if (i == 4 || i == 11) {
                if (question.equals(ANSWER[4]) || question.equals(ANSWER[11])) {
                    peopleText[i].setTextColor(Color.parseColor("#008000"));
                    right++;
                } else {
                    peopleText[i].setTextColor(Color.parseColor("#FF0000"));

                }
            } else {
                if (question.equals(ANSWER[i])) {
                    peopleText[i].setTextColor(Color.parseColor("#008000"));
                    right++;
                } else {
                    peopleText[i].setTextColor(Color.parseColor("#FF0000"));

                }
            }
        }
        matchingScore.setText(right + "/14");
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

        headerPanel = (RelativeLayout) findViewById(R.id.matchingHeader);
        headerPanelParameters = (LinearLayout.LayoutParams) headerPanel
                .getLayoutParams();
        headerPanelParameters.width = metrics.widthPixels;
        headerPanel.setLayoutParams(headerPanelParameters);

        menuPanel = (RelativeLayout) findViewById(R.id.matchingMenuPanel);
        menuPanelParameters = (FrameLayout.LayoutParams) menuPanel
                .getLayoutParams();
        menuPanelParameters.width = panelWidth;
        menuPanel.setLayoutParams(menuPanelParameters);

        slidingPanel = (LinearLayout) findViewById(R.id.matchingSlidingPanel);
        slidingPanelParameters = (FrameLayout.LayoutParams) slidingPanel
                .getLayoutParams();
        slidingPanelParameters.width = metrics.widthPixels;
        slidingPanel.setLayoutParams(slidingPanelParameters);

        categories = (ListView) findViewById(R.id.matchingList);

        categories.setAdapter(MainActivity.adapter);
        menuViewButton = (ImageView) findViewById(R.id.matchingMenuViewButton);

        matchingScore = (TextView) findViewById(R.id.matchingScore);
        QUESTION = new Spinner[14];
        QUESTION[0] = (Spinner) findViewById(R.id.spinner0);
        QUESTION[1] = (Spinner) findViewById(R.id.spinner1);
        QUESTION[2] = (Spinner) findViewById(R.id.spinner2);
        QUESTION[3] = (Spinner) findViewById(R.id.spinner3);
        QUESTION[4] = (Spinner) findViewById(R.id.spinner4);
        QUESTION[5] = (Spinner) findViewById(R.id.spinner5);
        QUESTION[6] = (Spinner) findViewById(R.id.spinner6);
        QUESTION[7] = (Spinner) findViewById(R.id.spinner7);
        QUESTION[8] = (Spinner) findViewById(R.id.spinner8);
        QUESTION[9] = (Spinner) findViewById(R.id.spinner9);
        QUESTION[10] = (Spinner) findViewById(R.id.spinner10);
        QUESTION[11] = (Spinner) findViewById(R.id.spinner11);
        QUESTION[12] = (Spinner) findViewById(R.id.spinner12);
        QUESTION[13] = (Spinner) findViewById(R.id.spinner13);
        ANSWER = new String[] { "H", "G", "F", "C", "E", "B", "A", "J", "N",
                "M", "I", "O", "K", "L" };

        ArrayAdapter<CharSequence> letterAdapter = ArrayAdapter
                .createFromResource(this, R.array.letters,
                        android.R.layout.simple_list_item_1);
        for (int i = 0; i < QUESTION.length; i++) {
            QUESTION[i].setAdapter(letterAdapter);
        }

        resetButton = (Button) findViewById(R.id.matchingReset);
        submitButton = (Button) findViewById(R.id.matchingSubmit);

        peopleText[0] = (TextView) findViewById(R.id.person0);
        peopleText[1] = (TextView) findViewById(R.id.person1);
        peopleText[2] = (TextView) findViewById(R.id.person2);
        peopleText[3] = (TextView) findViewById(R.id.person3);
        peopleText[4] = (TextView) findViewById(R.id.person4);
        peopleText[5] = (TextView) findViewById(R.id.person5);
        peopleText[6] = (TextView) findViewById(R.id.person6);
        peopleText[7] = (TextView) findViewById(R.id.person7);
        peopleText[8] = (TextView) findViewById(R.id.person8);
        peopleText[9] = (TextView) findViewById(R.id.person9);
        peopleText[10] = (TextView) findViewById(R.id.person10);
        peopleText[11] = (TextView) findViewById(R.id.person11);
        peopleText[12] = (TextView) findViewById(R.id.person12);
        peopleText[13] = (TextView) findViewById(R.id.person13);

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
}
