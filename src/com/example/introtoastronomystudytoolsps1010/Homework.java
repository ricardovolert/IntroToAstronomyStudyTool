package com.example.introtoastronomystudytoolsps1010;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import com.cvballa3g0.introtoastronomystudytoolsps1010.R;
import com.example.introtoastronomystudytoolsps1010.ShortAnswer.Question;

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
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Homework extends Activity {

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
    private ImageView question18Image;
    private ImageView question47Image;
    private ImageView question49Image;
    private ImageView question50Image;

    FrameLayout.LayoutParams menuPanelParameters;
    FrameLayout.LayoutParams slidingPanelParameters;
    LinearLayout.LayoutParams headerPanelParameters;
    LinearLayout.LayoutParams listViewParameters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homework);

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
                    newScreen(ShortAnswer.class);
                    break;
                case (4):
                    slidePanel();
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

        headerPanel = (RelativeLayout) findViewById(R.id.homeworkHeader);
        headerPanelParameters = (LinearLayout.LayoutParams) headerPanel
                .getLayoutParams();
        headerPanelParameters.width = metrics.widthPixels;
        headerPanel.setLayoutParams(headerPanelParameters);

        menuPanel = (RelativeLayout) findViewById(R.id.homeworkMenuPanel);
        menuPanelParameters = (FrameLayout.LayoutParams) menuPanel
                .getLayoutParams();
        menuPanelParameters.width = panelWidth;
        menuPanel.setLayoutParams(menuPanelParameters);

        slidingPanel = (LinearLayout) findViewById(R.id.homeworkSlidingPanel);
        slidingPanelParameters = (FrameLayout.LayoutParams) slidingPanel
                .getLayoutParams();
        slidingPanelParameters.width = metrics.widthPixels;
        slidingPanel.setLayoutParams(slidingPanelParameters);

        categories = (ListView) findViewById(R.id.homeworkList);

        categories.setAdapter(MainActivity.adapter);
        menuViewButton = (ImageView) findViewById(R.id.homeworkMenuViewButton);

        nextButton = (Button) findViewById(R.id.homeworkNextButton);
        prevButton = (Button) findViewById(R.id.homeworkPrevButton);

        relativeLayout = (RelativeLayout) findViewById(R.id.homeworkRelativeLayoutScreens);

        Questions.add(new Question((TextView) findViewById(R.id.homeworkText1),
                (TextView) findViewById(R.id.homeworkAnswer1),
                (Button) findViewById(R.id.homeworkButton1)));
        Questions.add(new Question((TextView) findViewById(R.id.homeworkText2),
                (TextView) findViewById(R.id.homeworkAnswer2),
                (Button) findViewById(R.id.homeworkButton2)));
        Questions.add(new Question((TextView) findViewById(R.id.homeworkText3),
                (TextView) findViewById(R.id.homeworkAnswer3),
                (Button) findViewById(R.id.homeworkButton3)));
        Questions.add(new Question((TextView) findViewById(R.id.homeworkText4),
                (TextView) findViewById(R.id.homeworkAnswer4),
                (Button) findViewById(R.id.homeworkButton4)));
        Questions.add(new Question((TextView) findViewById(R.id.homeworkText5),
                (TextView) findViewById(R.id.homeworkAnswer5),
                (Button) findViewById(R.id.homeworkButton5)));
        Questions.add(new Question((TextView) findViewById(R.id.homeworkText6),
                (TextView) findViewById(R.id.homeworkAnswer6),
                (Button) findViewById(R.id.homeworkButton6)));
        Questions.add(new Question((TextView) findViewById(R.id.homeworkText7),
                (TextView) findViewById(R.id.homeworkAnswer7),
                (Button) findViewById(R.id.homeworkButton7)));
        Questions.add(new Question((TextView) findViewById(R.id.homeworkText8),
                (TextView) findViewById(R.id.homeworkAnswer8),
                (Button) findViewById(R.id.homeworkButton8)));
        Questions.add(new Question((TextView) findViewById(R.id.homeworkText9),
                (TextView) findViewById(R.id.homeworkAnswer9),
                (Button) findViewById(R.id.homeworkButton9)));
        Questions.add(new Question(
                (TextView) findViewById(R.id.homeworkText10),
                (TextView) findViewById(R.id.homeworkAnswer10),
                (Button) findViewById(R.id.homeworkButton10)));
        Questions.add(new Question(
                (TextView) findViewById(R.id.homeworkText11),
                (TextView) findViewById(R.id.homeworkAnswer11),
                (Button) findViewById(R.id.homeworkButton11)));
        Questions.add(new Question(
                (TextView) findViewById(R.id.homeworkText12),
                (TextView) findViewById(R.id.homeworkAnswer12),
                (Button) findViewById(R.id.homeworkButton12)));
        Questions.add(new Question(
                (TextView) findViewById(R.id.homeworkText13),
                (TextView) findViewById(R.id.homeworkAnswer13),
                (Button) findViewById(R.id.homeworkButton13)));
        Questions.add(new Question(
                (TextView) findViewById(R.id.homeworkText14),
                (TextView) findViewById(R.id.homeworkAnswer14),
                (Button) findViewById(R.id.homeworkButton14)));
        Questions.add(new Question(
                (TextView) findViewById(R.id.homeworkText15),
                (TextView) findViewById(R.id.homeworkAnswer15),
                (Button) findViewById(R.id.homeworkButton15)));
        Questions.add(new Question(
                (TextView) findViewById(R.id.homeworkText16),
                (TextView) findViewById(R.id.homeworkAnswer16),
                (Button) findViewById(R.id.homeworkButton16)));
        Questions.add(new Question(
                (TextView) findViewById(R.id.homeworkText17),
                (TextView) findViewById(R.id.homeworkAnswer17),
                (Button) findViewById(R.id.homeworkButton17)));
        Questions.add(new Question(
                (TextView) findViewById(R.id.homeworkText18),
                (TextView) findViewById(R.id.homeworkAnswer18),
                (Button) findViewById(R.id.homeworkButton18)));
        question18Image = (ImageView) findViewById(R.id.homeworkImage18);
        Questions.add(new Question(
                (TextView) findViewById(R.id.homeworkText18),
                (TextView) findViewById(R.id.homeworkAnswer18),
                (Button) findViewById(R.id.homeworkButton18)));
        Questions.add(new Question(
                (TextView) findViewById(R.id.homeworkText19),
                (TextView) findViewById(R.id.homeworkAnswer19),
                (Button) findViewById(R.id.homeworkButton19)));
        Questions.add(new Question(
                (TextView) findViewById(R.id.homeworkText20),
                (TextView) findViewById(R.id.homeworkAnswer20),
                (Button) findViewById(R.id.homeworkButton20)));
        Questions.add(new Question(
                (TextView) findViewById(R.id.homeworkText21),
                (TextView) findViewById(R.id.homeworkAnswer21),
                (Button) findViewById(R.id.homeworkButton21)));
        Questions.add(new Question(
                (TextView) findViewById(R.id.homeworkText22),
                (TextView) findViewById(R.id.homeworkAnswer22),
                (Button) findViewById(R.id.homeworkButton22)));
        Questions.add(new Question(
                (TextView) findViewById(R.id.homeworkText23),
                (TextView) findViewById(R.id.homeworkAnswer23),
                (Button) findViewById(R.id.homeworkButton23)));
        Questions.add(new Question(
                (TextView) findViewById(R.id.homeworkText24),
                (TextView) findViewById(R.id.homeworkAnswer24),
                (Button) findViewById(R.id.homeworkButton24)));
        Questions.add(new Question(
                (TextView) findViewById(R.id.homeworkText25),
                (TextView) findViewById(R.id.homeworkAnswer25),
                (Button) findViewById(R.id.homeworkButton25)));
        Questions.add(new Question(
                (TextView) findViewById(R.id.homeworkText26),
                (TextView) findViewById(R.id.homeworkAnswer26),
                (Button) findViewById(R.id.homeworkButton26)));
        Questions.add(new Question(
                (TextView) findViewById(R.id.homeworkText27),
                (TextView) findViewById(R.id.homeworkAnswer27),
                (Button) findViewById(R.id.homeworkButton27)));
        Questions.add(new Question(
                (TextView) findViewById(R.id.homeworkText28),
                (TextView) findViewById(R.id.homeworkAnswer28),
                (Button) findViewById(R.id.homeworkButton28)));
        Questions.add(new Question(
                (TextView) findViewById(R.id.homeworkText29),
                (TextView) findViewById(R.id.homeworkAnswer29),
                (Button) findViewById(R.id.homeworkButton29)));
        Questions.add(new Question(
                (TextView) findViewById(R.id.homeworkText30),
                (TextView) findViewById(R.id.homeworkAnswer30),
                (Button) findViewById(R.id.homeworkButton30)));
        Questions.add(new Question(
                (TextView) findViewById(R.id.homeworkText31),
                (TextView) findViewById(R.id.homeworkAnswer31),
                (Button) findViewById(R.id.homeworkButton31)));
        Questions.add(new Question(
                (TextView) findViewById(R.id.homeworkText32),
                (TextView) findViewById(R.id.homeworkAnswer32),
                (Button) findViewById(R.id.homeworkButton32)));
        Questions.add(new Question(
                (TextView) findViewById(R.id.homeworkText33),
                (TextView) findViewById(R.id.homeworkAnswer33),
                (Button) findViewById(R.id.homeworkButton33)));
        Questions.add(new Question(
                (TextView) findViewById(R.id.homeworkText34),
                (TextView) findViewById(R.id.homeworkAnswer34),
                (Button) findViewById(R.id.homeworkButton34)));
        Questions.add(new Question(
                (TextView) findViewById(R.id.homeworkText35),
                (TextView) findViewById(R.id.homeworkAnswer35),
                (Button) findViewById(R.id.homeworkButton35)));
        Questions.add(new Question(
                (TextView) findViewById(R.id.homeworkText36),
                (TextView) findViewById(R.id.homeworkAnswer36),
                (Button) findViewById(R.id.homeworkButton36)));
        Questions.add(new Question(
                (TextView) findViewById(R.id.homeworkText37),
                (TextView) findViewById(R.id.homeworkAnswer37),
                (Button) findViewById(R.id.homeworkButton37)));
        Questions.add(new Question(
                (TextView) findViewById(R.id.homeworkText38),
                (TextView) findViewById(R.id.homeworkAnswer38),
                (Button) findViewById(R.id.homeworkButton38)));
        Questions.add(new Question(
                (TextView) findViewById(R.id.homeworkText39),
                (TextView) findViewById(R.id.homeworkAnswer39),
                (Button) findViewById(R.id.homeworkButton39)));
        Questions.add(new Question(
                (TextView) findViewById(R.id.homeworkText40),
                (TextView) findViewById(R.id.homeworkAnswer40),
                (Button) findViewById(R.id.homeworkButton40)));
        Questions.add(new Question(
                (TextView) findViewById(R.id.homeworkText41),
                (TextView) findViewById(R.id.homeworkAnswer41),
                (Button) findViewById(R.id.homeworkButton41)));
        Questions.add(new Question(
                (TextView) findViewById(R.id.homeworkText42),
                (TextView) findViewById(R.id.homeworkAnswer42),
                (Button) findViewById(R.id.homeworkButton42)));
        Questions.add(new Question(
                (TextView) findViewById(R.id.homeworkText43),
                (TextView) findViewById(R.id.homeworkAnswer43),
                (Button) findViewById(R.id.homeworkButton43)));
        Questions.add(new Question(
                (TextView) findViewById(R.id.homeworkText44),
                (TextView) findViewById(R.id.homeworkAnswer44),
                (Button) findViewById(R.id.homeworkButton44)));
        Questions.add(new Question(
                (TextView) findViewById(R.id.homeworkText45),
                (TextView) findViewById(R.id.homeworkAnswer45),
                (Button) findViewById(R.id.homeworkButton45)));
        Questions.add(new Question(
                (TextView) findViewById(R.id.homeworkText46),
                (TextView) findViewById(R.id.homeworkAnswer46),
                (Button) findViewById(R.id.homeworkButton46)));
        Questions.add(new Question(
                (TextView) findViewById(R.id.homeworkText47),
                (TextView) findViewById(R.id.homeworkAnswer47),
                (Button) findViewById(R.id.homeworkButton47)));
        question47Image = (ImageView) findViewById(R.id.homeworkImage47);
        Questions.add(new Question(
                (TextView) findViewById(R.id.homeworkText48),
                (TextView) findViewById(R.id.homeworkAnswer48),
                (Button) findViewById(R.id.homeworkButton48)));
        question49Image = (ImageView) findViewById(R.id.homeworkImage49);
        Questions.add(new Question(
                (TextView) findViewById(R.id.homeworkText49),
                (TextView) findViewById(R.id.homeworkAnswer49),
                (Button) findViewById(R.id.homeworkButton49)));
        question50Image = (ImageView) findViewById(R.id.homeworkImage50);
        Questions.add(new Question(
                (TextView) findViewById(R.id.homeworkText50),
                (TextView) findViewById(R.id.homeworkAnswer50),
                (Button) findViewById(R.id.homeworkButton50)));

    }

    public void showResult(View view) {
        int visibility = Questions.get(POSITION).answerText.getVisibility();
        if (visibility == 0) {
            Questions.get(POSITION).answerText.setVisibility(4);
            Questions.get(POSITION).questionButton.setText("Show Answer");
            if (POSITION == 17) {
                question18Image.setVisibility(4);
            } else if (POSITION == 47) {
                question47Image.setVisibility(4);
            } else if (POSITION == 49) {
                question49Image.setVisibility(4);
            } else if (POSITION == 50) {
                question50Image.setVisibility(4);
            }

        } else {
            Questions.get(POSITION).answerText.setVisibility(0);
            Questions.get(POSITION).answerText.setTextColor(Color
                    .parseColor("#008000"));
            Questions.get(POSITION).questionButton.setText("Hide Answer");
            if (POSITION == 17) {
                question18Image.setVisibility(0);
            } else if (POSITION == 47) {
                question47Image.setVisibility(0);
            } else if (POSITION == 49) {
                question49Image.setVisibility(0);
            } else if (POSITION == 50) {
                question50Image.setVisibility(0);
            }
        }
    }

    public void resetHomework(View view) {
        for (int i = 0; i < Questions.size(); i++) {
            Questions.get(i).answerText.setVisibility(4);
            Questions.get(i).questionButton.setText("Show Answer");
        }
        POSITION = 0;
        question18Image.setVisibility(4);
        question47Image.setVisibility(4);
        question49Image.setVisibility(4);
        question50Image.setVisibility(4);
        relativeLayout.scrollTo(0,
                Questions.get(POSITION).questionText.getTop());
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
