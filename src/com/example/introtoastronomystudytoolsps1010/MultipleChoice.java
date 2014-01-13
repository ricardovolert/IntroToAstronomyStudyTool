package com.example.introtoastronomystudytoolsps1010;

import java.util.Arrays;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ScrollView;
import android.widget.TextView;
import com.cvballa3g0.introtoastronomystudytoolsps1010.R;

public class MultipleChoice extends Activity {

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
    public static Button submitButton;
    public static FrameLayout[] questions = new FrameLayout[20];
    public static int number = 0;
    public static TextView[] questionText = new TextView[12];
    public static RadioGroup[] radioGroups = new RadioGroup[12];
    public static RadioButton[][] radiobuttons = new RadioButton[12][5];
    public static Boolean[] isRIGHTORWRONG = new Boolean[12];
    public RelativeLayout scrollView;
    public int SCROLL = 0;
    private static int[] ANSWERS;
    public TextView resultsScore;
    public TextView[] questionStatus = new TextView[12];
    public Button tryAgain;

    FrameLayout.LayoutParams menuPanelParameters;
    FrameLayout.LayoutParams slidingPanelParameters;
    LinearLayout.LayoutParams headerPanelParameters;
    LinearLayout.LayoutParams listViewParameters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multiple_choice_ch1to401);
        initialize();
        nextButton[number].setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SCROLL == questionStatus.length - 1) {

                } else if (SCROLL == questionStatus.length - 2) {
                    submitButton.setVisibility(0);
                    SCROLL++;
                    scrollView.scrollTo(0, questionText[SCROLL].getTop());
                } else {
                    SCROLL++;
                    scrollView.scrollTo(0, questionText[SCROLL].getTop());
                }
            }
        });
        prevButton[number].setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SCROLL == 0) {
                } else {

                    SCROLL--;
                    scrollView.scrollTo(0, questionText[SCROLL].getTop());
                }
            }
        });

        submitButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate();
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
                    slidePanel();
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

    protected void calculate() {
        int right = 0;
        for (int i = 0; i < 12; i++) {
            int id = radioGroups[i].getCheckedRadioButtonId();
            if (id == (radiobuttons[i][0].getId())) {
                right += checkAnswer(right, 0, i);
            } else if (id == radiobuttons[i][1].getId()) {
                right += checkAnswer(right, 1, i);
            } else if (id == radiobuttons[i][2].getId()) {
                right += checkAnswer(right, 2, i);
            } else if (id == radiobuttons[i][3].getId()) {
                right += checkAnswer(right, 3, i);
            } else if (id == radiobuttons[i][4].getId()) {
                right += checkAnswer(right, 4, i);
            }
        }
        setupResults(right);
    }

    private void setupResults(int right) {
        setContentView(R.layout.multiple_choice_results);
        initializeResults();
        resultsScore = (TextView) findViewById(R.id.resultsScore);
        tryAgain = (Button) findViewById(R.id.resultsTryAgain);
        questionStatus[0] = (TextView) findViewById(R.id.question1);
        questionStatus[1] = (TextView) findViewById(R.id.question2);
        questionStatus[2] = (TextView) findViewById(R.id.question3);
        questionStatus[3] = (TextView) findViewById(R.id.question4);
        questionStatus[4] = (TextView) findViewById(R.id.question5);
        questionStatus[5] = (TextView) findViewById(R.id.question6);
        questionStatus[6] = (TextView) findViewById(R.id.question7);
        questionStatus[7] = (TextView) findViewById(R.id.question8);
        questionStatus[8] = (TextView) findViewById(R.id.question9);
        questionStatus[9] = (TextView) findViewById(R.id.question10);
        questionStatus[10] = (TextView) findViewById(R.id.question11);
        questionStatus[11] = (TextView) findViewById(R.id.question12);

        for (int i = 0; i < 12; i++) {
            if (isRIGHTORWRONG[i]) {
                questionStatus[i].setText("Question #" + (i + 1) + ": Right!");
                questionStatus[i].setTextColor(Color.parseColor("#008000"));
            } else {
                questionStatus[i].setText("Question #" + (i + 1) + ": Wrong.");
                questionStatus[i].setTextColor(Color.parseColor("#FF0000"));
            }
        }
        resultsScore.setText("Score: " + right + "/12");

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
                    slidePanel();
                    break;
                case (3):
                    break;
                case (4):
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

        tryAgain.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                newScreen(MultipleChoice.class);
            }
        });
    }

    private void initializeResults() {
        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        panelWidth = (int) ((metrics.widthPixels) * 0.75);

        headerPanel = (RelativeLayout) findViewById(R.id.resultsHeader);
        headerPanelParameters = (LinearLayout.LayoutParams) headerPanel
                .getLayoutParams();
        headerPanelParameters.width = metrics.widthPixels;
        headerPanel.setLayoutParams(headerPanelParameters);

        menuPanel = (RelativeLayout) findViewById(R.id.resultsMenuPanel);
        menuPanelParameters = (FrameLayout.LayoutParams) menuPanel
                .getLayoutParams();
        menuPanelParameters.width = panelWidth;
        menuPanel.setLayoutParams(menuPanelParameters);

        slidingPanel = (LinearLayout) findViewById(R.id.resultsSlidingPanel);
        slidingPanelParameters = (FrameLayout.LayoutParams) slidingPanel
                .getLayoutParams();
        slidingPanelParameters.width = metrics.widthPixels;
        slidingPanel.setLayoutParams(slidingPanelParameters);

        categories = (ListView) findViewById(R.id.resultsList);

        categories.setAdapter(MainActivity.adapter);
        menuViewButton = (ImageView) findViewById(R.id.resultsMenuViewButton);

    }

    private int checkAnswer(int right, int position, int i) {
        if (position == ANSWERS[i]) {
            right = 1;
            isRIGHTORWRONG[i] = true;
        } else {
            right = 0;
            isRIGHTORWRONG[i] = false;
        }
        return right;
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
        SCROLL = 0;
        Arrays.fill(isRIGHTORWRONG, false);
        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        panelWidth = (int) ((metrics.widthPixels) * 0.75);

        headerPanel = (RelativeLayout) findViewById(R.id.oneto401Header);
        headerPanelParameters = (LinearLayout.LayoutParams) headerPanel
                .getLayoutParams();
        headerPanelParameters.width = metrics.widthPixels;
        headerPanel.setLayoutParams(headerPanelParameters);

        menuPanel = (RelativeLayout) findViewById(R.id.oneto401MenuPanel);
        menuPanelParameters = (FrameLayout.LayoutParams) menuPanel
                .getLayoutParams();
        menuPanelParameters.width = panelWidth;
        menuPanel.setLayoutParams(menuPanelParameters);

        slidingPanel = (LinearLayout) findViewById(R.id.oneto401SlidingPanel);
        slidingPanelParameters = (FrameLayout.LayoutParams) slidingPanel
                .getLayoutParams();
        slidingPanelParameters.width = metrics.widthPixels;
        slidingPanel.setLayoutParams(slidingPanelParameters);

        categories = (ListView) findViewById(R.id.oneto401List);

        categories.setAdapter(MainActivity.adapter);
        menuViewButton = (ImageView) findViewById(R.id.oneto401matchingMenuViewButton);

        nextButton[0] = (Button) findViewById(R.id.nextButton0);
        prevButton[0] = (Button) findViewById(R.id.prevButton0);

        questionText[0] = (TextView) findViewById(R.id.question1Text);
        questionText[1] = (TextView) findViewById(R.id.question2Text);
        questionText[2] = (TextView) findViewById(R.id.question3Text);
        questionText[3] = (TextView) findViewById(R.id.question4Text);
        questionText[4] = (TextView) findViewById(R.id.question5Text);
        questionText[5] = (TextView) findViewById(R.id.question6Text);
        questionText[6] = (TextView) findViewById(R.id.question7Text);
        questionText[7] = (TextView) findViewById(R.id.question8Text);
        questionText[8] = (TextView) findViewById(R.id.question9Text);
        questionText[9] = (TextView) findViewById(R.id.question10Text);
        questionText[10] = (TextView) findViewById(R.id.question11Text);
        questionText[11] = (TextView) findViewById(R.id.question12Text);

        submitButton = (Button) findViewById(R.id.multipleChoiceSubmit);

        radioGroups[0] = (RadioGroup) findViewById(R.id.question1Group);
        radioGroups[1] = (RadioGroup) findViewById(R.id.question2Group);
        radioGroups[2] = (RadioGroup) findViewById(R.id.question3Group);
        radioGroups[3] = (RadioGroup) findViewById(R.id.question4Group);
        radioGroups[4] = (RadioGroup) findViewById(R.id.question5Group);
        radioGroups[5] = (RadioGroup) findViewById(R.id.question6Group);
        radioGroups[6] = (RadioGroup) findViewById(R.id.question7Group);
        radioGroups[7] = (RadioGroup) findViewById(R.id.question8Group);
        radioGroups[8] = (RadioGroup) findViewById(R.id.question9Group);
        radioGroups[9] = (RadioGroup) findViewById(R.id.question10Group);
        radioGroups[10] = (RadioGroup) findViewById(R.id.question11Group);
        radioGroups[11] = (RadioGroup) findViewById(R.id.question12Group);

        radiobuttons[0][0] = (RadioButton) findViewById(R.id.question1Radio1);
        radiobuttons[0][1] = (RadioButton) findViewById(R.id.question1Radio2);
        radiobuttons[0][2] = (RadioButton) findViewById(R.id.question1Radio3);
        radiobuttons[0][3] = (RadioButton) findViewById(R.id.question1Radio4);
        radiobuttons[0][4] = (RadioButton) findViewById(R.id.question1Radio5);

        radiobuttons[1][0] = (RadioButton) findViewById(R.id.question2Radio1);
        radiobuttons[1][1] = (RadioButton) findViewById(R.id.question2Radio2);
        radiobuttons[1][2] = (RadioButton) findViewById(R.id.question2Radio3);
        radiobuttons[1][3] = (RadioButton) findViewById(R.id.question2Radio4);
        radiobuttons[1][4] = (RadioButton) findViewById(R.id.question2Radio5);

        radiobuttons[2][0] = (RadioButton) findViewById(R.id.question3Radio1);
        radiobuttons[2][1] = (RadioButton) findViewById(R.id.question3Radio2);
        radiobuttons[2][2] = (RadioButton) findViewById(R.id.question3Radio3);
        radiobuttons[2][3] = (RadioButton) findViewById(R.id.question3Radio4);
        radiobuttons[2][4] = (RadioButton) findViewById(R.id.question3Radio5);

        radiobuttons[3][0] = (RadioButton) findViewById(R.id.question4Radio1);
        radiobuttons[3][1] = (RadioButton) findViewById(R.id.question4Radio2);
        radiobuttons[3][2] = (RadioButton) findViewById(R.id.question4Radio3);
        radiobuttons[3][3] = (RadioButton) findViewById(R.id.question4Radio4);
        radiobuttons[3][4] = (RadioButton) findViewById(R.id.question4Radio5);

        radiobuttons[4][0] = (RadioButton) findViewById(R.id.question5Radio1);
        radiobuttons[4][1] = (RadioButton) findViewById(R.id.question5Radio2);
        radiobuttons[4][2] = (RadioButton) findViewById(R.id.question5Radio3);
        radiobuttons[4][3] = (RadioButton) findViewById(R.id.question5Radio4);
        radiobuttons[4][4] = (RadioButton) findViewById(R.id.question5Radio5);

        radiobuttons[5][0] = (RadioButton) findViewById(R.id.question6Radio1);
        radiobuttons[5][1] = (RadioButton) findViewById(R.id.question6Radio2);
        radiobuttons[5][2] = (RadioButton) findViewById(R.id.question6Radio3);
        radiobuttons[5][3] = (RadioButton) findViewById(R.id.question6Radio4);
        radiobuttons[5][4] = (RadioButton) findViewById(R.id.question6Radio5);

        radiobuttons[6][0] = (RadioButton) findViewById(R.id.question7Radio1);
        radiobuttons[6][1] = (RadioButton) findViewById(R.id.question7Radio2);
        radiobuttons[6][2] = (RadioButton) findViewById(R.id.question7Radio3);
        radiobuttons[6][3] = (RadioButton) findViewById(R.id.question7Radio4);
        radiobuttons[6][4] = (RadioButton) findViewById(R.id.question7Radio5);

        radiobuttons[7][0] = (RadioButton) findViewById(R.id.question8Radio1);
        radiobuttons[7][1] = (RadioButton) findViewById(R.id.question8Radio2);
        radiobuttons[7][2] = (RadioButton) findViewById(R.id.question8Radio3);
        radiobuttons[7][3] = (RadioButton) findViewById(R.id.question8Radio4);
        radiobuttons[7][4] = (RadioButton) findViewById(R.id.question8Radio5);

        radiobuttons[8][0] = (RadioButton) findViewById(R.id.question9Radio1);
        radiobuttons[8][1] = (RadioButton) findViewById(R.id.question9Radio2);
        radiobuttons[8][2] = (RadioButton) findViewById(R.id.question9Radio3);
        radiobuttons[8][3] = (RadioButton) findViewById(R.id.question9Radio4);
        radiobuttons[8][4] = (RadioButton) findViewById(R.id.question9Radio5);

        radiobuttons[9][0] = (RadioButton) findViewById(R.id.question10Radio1);
        radiobuttons[9][1] = (RadioButton) findViewById(R.id.question10Radio2);
        radiobuttons[9][2] = (RadioButton) findViewById(R.id.question10Radio3);
        radiobuttons[9][3] = (RadioButton) findViewById(R.id.question10Radio4);
        radiobuttons[9][4] = (RadioButton) findViewById(R.id.question10Radio5);

        radiobuttons[10][0] = (RadioButton) findViewById(R.id.question11Radio1);
        radiobuttons[10][1] = (RadioButton) findViewById(R.id.question11Radio2);
        radiobuttons[10][2] = (RadioButton) findViewById(R.id.question11Radio3);
        radiobuttons[10][3] = (RadioButton) findViewById(R.id.question11Radio4);
        radiobuttons[10][4] = (RadioButton) findViewById(R.id.question11Radio5);

        radiobuttons[11][0] = (RadioButton) findViewById(R.id.question12Radio1);
        radiobuttons[11][1] = (RadioButton) findViewById(R.id.question12Radio2);
        radiobuttons[11][2] = (RadioButton) findViewById(R.id.question12Radio3);
        radiobuttons[11][3] = (RadioButton) findViewById(R.id.question12Radio4);
        radiobuttons[11][4] = (RadioButton) findViewById(R.id.question12Radio5);

        ANSWERS = new int[] { 3, 2, 3, 1, 4, 4, 1, 0, 2, 1, 2, 0 };
        scrollView = (RelativeLayout) findViewById(R.id.oneto401Relative);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }
}
