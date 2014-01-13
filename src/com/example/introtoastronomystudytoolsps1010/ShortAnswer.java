package com.example.introtoastronomystudytoolsps1010;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
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
import android.widget.TextView;
import com.cvballa3g0.introtoastronomystudytoolsps1010.R;

public class ShortAnswer extends Activity {

    public static class Question {
        TextView questionText;
        TextView answerText;
        Button questionButton;

        public Question(TextView questionText, TextView answerText,
                Button questionButton) {
            this.answerText = answerText;
            this.questionButton = questionButton;
            this.questionText = questionText;
        }
    }

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
    public static Button nextButton;
    public static Button prevButton;
    public static int POSITION = 0;
    public static AlertDialog dialog;
    public RelativeLayout relativeLayout;
    private ArrayList<Question> Questions = new ArrayList<Question>();
    private ImageView shortAnswerImage2;
    private ImageView shortAnswerImage18;

    FrameLayout.LayoutParams menuPanelParameters;
    FrameLayout.LayoutParams slidingPanelParameters;
    LinearLayout.LayoutParams headerPanelParameters;
    LinearLayout.LayoutParams listViewParameters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.short_answer);

        initialize();

        nextButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (POSITION == Questions.size() - 1) {
                } else {
                    POSITION++;
                    relativeLayout.scrollTo(0,
                            Questions.get(POSITION).questionText.getTop());
                }
            }
        });
        prevButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (POSITION == 0) {
                } else {
                    POSITION--;
                    relativeLayout.scrollTo(0,
                            Questions.get(POSITION).questionText.getTop());
                }
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
                    slidePanel();
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

    public void newScreen(Class yolo) {
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
        POSITION = 0;
        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        panelWidth = (int) ((metrics.widthPixels) * 0.75);

        headerPanel = (RelativeLayout) findViewById(R.id.shortAnswerHeader);
        headerPanelParameters = (LinearLayout.LayoutParams) headerPanel
                .getLayoutParams();
        headerPanelParameters.width = metrics.widthPixels;
        headerPanel.setLayoutParams(headerPanelParameters);

        menuPanel = (RelativeLayout) findViewById(R.id.shortAnswerMenuPanel);
        menuPanelParameters = (FrameLayout.LayoutParams) menuPanel
                .getLayoutParams();
        menuPanelParameters.width = panelWidth;
        menuPanel.setLayoutParams(menuPanelParameters);

        slidingPanel = (LinearLayout) findViewById(R.id.shortAnswerSlidingPanel);
        slidingPanelParameters = (FrameLayout.LayoutParams) slidingPanel
                .getLayoutParams();
        slidingPanelParameters.width = metrics.widthPixels;
        slidingPanel.setLayoutParams(slidingPanelParameters);

        categories = (ListView) findViewById(R.id.shortAnswerList);

        categories.setAdapter(MainActivity.adapter);
        menuViewButton = (ImageView) findViewById(R.id.shortAnswerMenuViewButton);

        nextButton = (Button) findViewById(R.id.shortAnswerNextButton);
        prevButton = (Button) findViewById(R.id.shortAnswerPrevButton);

        relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayoutScreens);

        Questions.add(new Question(
                (TextView) findViewById(R.id.shortAnswerText1),
                (TextView) findViewById(R.id.shortAnswerAnswer1),
                (Button) findViewById(R.id.shortAnswerButton1)));
        Questions.add(new Question(
                (TextView) findViewById(R.id.shortAnswerText2),
                (TextView) findViewById(R.id.shortAnswerAnswer2),
                (Button) findViewById(R.id.shortAnswerButton2)));
        Questions.add(new Question(
                (TextView) findViewById(R.id.shortAnswerText3),
                (TextView) findViewById(R.id.shortAnswerAnswer3),
                (Button) findViewById(R.id.shortAnswerButton3)));
        Questions.add(new Question(
                (TextView) findViewById(R.id.shortAnswerText4),
                (TextView) findViewById(R.id.shortAnswerAnswer4),
                (Button) findViewById(R.id.shortAnswerButton4)));
        Questions.add(new Question(
                (TextView) findViewById(R.id.shortAnswerText5),
                (TextView) findViewById(R.id.shortAnswerAnswer5),
                (Button) findViewById(R.id.shortAnswerButton5)));
        Questions.add(new Question(
                (TextView) findViewById(R.id.shortAnswerText6),
                (TextView) findViewById(R.id.shortAnswerAnswer6),
                (Button) findViewById(R.id.shortAnswerButton6)));
        Questions.add(new Question(
                (TextView) findViewById(R.id.shortAnswerText7),
                (TextView) findViewById(R.id.shortAnswerAnswer7),
                (Button) findViewById(R.id.shortAnswerButton7)));
        Questions.add(new Question(
                (TextView) findViewById(R.id.shortAnswerText8),
                (TextView) findViewById(R.id.shortAnswerAnswer8),
                (Button) findViewById(R.id.shortAnswerButton8)));
        Questions.add(new Question(
                (TextView) findViewById(R.id.shortAnswerText9),
                (TextView) findViewById(R.id.shortAnswerAnswer9),
                (Button) findViewById(R.id.shortAnswerButton9)));
        Questions.add(new Question(
                (TextView) findViewById(R.id.shortAnswerText10),
                (TextView) findViewById(R.id.shortAnswerAnswer10),
                (Button) findViewById(R.id.shortAnswerButton10)));
        Questions.add(new Question(
                (TextView) findViewById(R.id.shortAnswerText11),
                (TextView) findViewById(R.id.shortAnswerAnswer11),
                (Button) findViewById(R.id.shortAnswerButton11)));
        Questions.add(new Question(
                (TextView) findViewById(R.id.shortAnswerText12),
                (TextView) findViewById(R.id.shortAnswerAnswer12),
                (Button) findViewById(R.id.shortAnswerButton12)));
        Questions.add(new Question(
                (TextView) findViewById(R.id.shortAnswerText13),
                (TextView) findViewById(R.id.shortAnswerAnswer13),
                (Button) findViewById(R.id.shortAnswerButton13)));
        Questions.add(new Question(
                (TextView) findViewById(R.id.shortAnswerText14),
                (TextView) findViewById(R.id.shortAnswerAnswer14),
                (Button) findViewById(R.id.shortAnswerButton14)));
        Questions.add(new Question(
                (TextView) findViewById(R.id.shortAnswerText15),
                (TextView) findViewById(R.id.shortAnswerAnswer15),
                (Button) findViewById(R.id.shortAnswerButton15)));
        Questions.add(new Question(
                (TextView) findViewById(R.id.shortAnswerText16),
                (TextView) findViewById(R.id.shortAnswerAnswer16),
                (Button) findViewById(R.id.shortAnswerButton16)));
        Questions.add(new Question(
                (TextView) findViewById(R.id.shortAnswerText17),
                (TextView) findViewById(R.id.shortAnswerAnswer17),
                (Button) findViewById(R.id.shortAnswerButton17)));
        Questions.add(new Question(
                (TextView) findViewById(R.id.shortAnswerText18),
                (TextView) findViewById(R.id.shortAnswerAnswer18),
                (Button) findViewById(R.id.shortAnswerButton18)));

        shortAnswerImage2 = (ImageView) findViewById(R.id.shortAnswerImage2);
        shortAnswerImage18 = (ImageView) findViewById(R.id.shortAnswerImage18);

    }

    public void showResult(View view) {
        int visibility = Questions.get(POSITION).answerText.getVisibility();
        if (visibility == 0) {
            Questions.get(POSITION).answerText.setVisibility(4);
            Questions.get(POSITION).questionButton.setText("Show Answer");
            if (POSITION == 1) {
                shortAnswerImage2.setVisibility(4);
            } else if (POSITION == 17) {
                shortAnswerImage18.setVisibility(4);
            }

        } else {
            Questions.get(POSITION).answerText.setVisibility(0);
            Questions.get(POSITION).answerText.setTextColor(Color
                    .parseColor("#008000"));
            Questions.get(POSITION).questionButton.setText("Hide Answer");
            if (POSITION == 1) {
                shortAnswerImage2.setVisibility(0);
            } else if (POSITION == 17) {
                shortAnswerImage18.setVisibility(0);
            }
        }
    }

    public void resetShortAnswer(View view) {
        for (int i = 0; i < Questions.size(); i++) {
            Questions.get(i).answerText.setVisibility(4);
            Questions.get(i).questionButton.setText("Show Answer");
        }
        POSITION = 0;
        shortAnswerImage2.setVisibility(4);
        shortAnswerImage18.setVisibility(4);
        relativeLayout.scrollTo(0,
                Questions.get(POSITION).questionText.getTop());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }
}
