package com.example.game1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;


import androidx.appcompat.app.AlertDialog;

import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ImageButton btn_scissors, btn_rock, btn_paper;
    ImageView user_select, image_vs, robot_select;
    TextView score_user, score_robot;
    Integer temp;
    TextView remainCnt;
    TextView message;
    Animation anim;
    //가위 0 , 바위1, 보2
    int win[][] = {{1, 0}, {2, 1}, {0, 2}};
    //user - robot의 쌍중에서 user가 이기는 경우만 배열에 저장
    //1,0 : 바위-가위
    //2,1 : 보-바위
    //0,2 : 가위 - 보

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_scissors = (ImageButton) findViewById(R.id.btn_scissors);
        btn_rock = (ImageButton) findViewById(R.id.btn_rock);
        btn_paper = (ImageButton) findViewById(R.id.btn_paper);

        user_select = (ImageView) findViewById(R.id.user_select);
        image_vs = (ImageView) findViewById(R.id.image_vs);
        robot_select = (ImageView) findViewById(R.id.robot_select);
        score_user = (TextView) findViewById(R.id.score_user);
        score_robot = (TextView) findViewById(R.id.score_robot);

        remainCnt = (TextView) findViewById(R.id.remainCnt);
        message = findViewById(R.id.message);

//        //글씨 깜빡이는 애니메이션
//        anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.alpha);
//        anim.setRepeatCount(Animation.INFINITE);
//        anim.setRepeatMode(Animation.RESTART);
//        command.startAnimation(anim);

    }

    public void goGame(View view) {
        int valRobot = doRobot();
        int valUser = 0;
        switch (view.getId()) {
            case R.id.btn_scissors:
                user_select.setImageResource(R.drawable.scissors);
                valUser = 0;    //가위
                break;
            case R.id.btn_rock:
                user_select.setImageResource(R.drawable.rock);
                valUser = 1;    //바위
                break;
            case R.id.btn_paper:
                user_select.setImageResource(R.drawable.paper);
                valUser = 2;    //보
                break;
        }

        if (valRobot == valUser)
            same();
        else if((win[0][0] == valUser && win[0][1] == valRobot) ||
                (win[1][0] == valUser && win[1][1] == valRobot) ||
                (win[2][0] == valUser && win[2][1] == valRobot)) {
            userWin();
        } else {
            userFail();
        }

        isGameOver();
    }

    void isGameOver() {
        Integer cnt = Integer.parseInt(remainCnt.getText().toString()) - 1;
        remainCnt.setText(cnt.toString());

        if (cnt == 0) {
            int userScore = Integer.parseInt(score_user.getText().toString());
            int robotScore = Integer.parseInt(score_robot.getText().toString());

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Game Over");

            if (userScore < robotScore) {
                builder.setMessage("컴퓨터가 이겼습니다.");
            } else if (userScore > robotScore) {
                builder.setMessage("축하합니다. 당신이 이겼습니다.");
            } else {
                builder.setMessage("비겼습니다.");
            }
            builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    remainCnt.setText("10");
                    score_robot.setText("0");
                    score_user.setText("0");
                    user_select.setImageResource(R.drawable.boy);
                    robot_select.setImageResource(R.drawable.robot);
                    image_vs.setImageResource(R.drawable.img_empty);
                }
            });
            builder.show();

        }
    }


    //컴퓨터가 랜덤하게 숫자를 뽑는다. 0:가위 , 1:바위, 2:보
    int doRobot() {
        Random r = new Random();
        int com = r.nextInt(3);
        if (com == 0) {
            robot_select.setImageResource(R.drawable.scissors);
        } else if (com == 1) {
            robot_select.setImageResource(R.drawable.rock);
        } else if (com == 2) {
            robot_select.setImageResource(R.drawable.paper);
        }
        return com;
    }

    void userWin() {  //내가 이겼을때 처리
        image_vs.setImageResource(R.drawable.win);
        temp = Integer.parseInt(score_user.getText().toString()) + 1;
        score_user.setText(temp.toString());
    }

    void userFail() { //컴퓨터가 이겼을때
        image_vs.setImageResource(R.drawable.fail);
        temp = Integer.parseInt(score_robot.getText().toString()) + 1;
        score_robot.setText(temp.toString());
    }

    void same() { //같은걸 냈을때 처리
        image_vs.setImageResource(R.drawable.same);
    }
}
