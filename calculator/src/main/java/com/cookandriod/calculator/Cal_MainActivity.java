package com.cookandriod.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Cal_MainActivity extends AppCompatActivity {

    boolean isFirstInput = true; // 입력 값이 처음 입력되는가를 체크
    int resultNumber = 0; // 계산된 결과 값을 저장하는 변수
    char operator = '+'; // 입력된 연산자를 저장하는 변수
    TextView resultText;  // 결과 창
    final String CLEAR_INPUT_TEXT = "0";  // 상수, 나중에 바꾸기 쉽도록

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cal_main);
        resultText = findViewById(R.id.result_text);
    }

    // TODO : AC, CE, BS, . 이 클릭 되었을 때 실행되는 메소드
    public void buttonClick(View view) {
        switch (view.getId()) {
            case R.id.all_clear_button:
                resultNumber = 0;  //결과 값 초기화
                operator = '+';  // 연산자 초기화
                setClearText(CLEAR_INPUT_TEXT); // 55줄로 이동
                break;

            case R.id.clear_entry_button:
                setClearText(CLEAR_INPUT_TEXT); // 55줄로 이동
                break;

            case R.id.back_space_button:
                if (resultText.getText().toString().length() > 1) {
                    String getResultText = resultText.getText().toString();
                    String subString = getResultText.substring(0, getResultText.length() - 1); // 길이에서 -1만큼 삭제
                    resultText.setText(subString);
                } else {
                    setClearText(CLEAR_INPUT_TEXT); // 55줄로 이동
                }
                break;

            case R.id.decimal_button:
                Toast.makeText(getApplicationContext(), "실수는 구현하지 않았습니다.", Toast.LENGTH_SHORT).show();
                // 구현하지 않음
                break;
        }
    }

    // TODO : 입력된 숫자를 클리어 시켜주는 메소드
    public void setClearText(String clearText) {
        isFirstInput = true; // boolean 초기화
        resultText.setTextColor(0xFF7A7A7A); // 색 초기화
        resultText.setText(clearText); // 0으로 초기화
    }

    // TODO : 0 ~ 9 버튼이 클릭되었을 때 실행되는 메소드
    public void numButtonClick(View view) {                             //xml onclick
        Button getButton = findViewById(view.getId()); // 버튼생성
        if (isFirstInput) {
            resultText.setTextColor(0xFF000000); // 텍스트뷰 검은색으로 변경
            resultText.setText(getButton.getText().toString());
            isFirstInput = false; // false로 변경해서 else로 이동 append
        } else {
            if (resultText.getText().toString().equals("0")) {           // TODO : 정수는 0 으로 시작할 수 없음 *예외처리
                Toast.makeText(getApplicationContext(), "0으로 시작할 수 없습니다.", Toast.LENGTH_SHORT).show();
                setClearText(CLEAR_INPUT_TEXT);
            } else {
                resultText.append(getButton.getText().toString());
            }
        }
    }

    // TODO : 연산자가 클릭 되었을 때 실행되는 메소드
    public void operatorClick(View view) {                              //xml onclick
        Button getButton = findViewById(view.getId());

        if (view.getId() == R.id.result_button) {
            if (isFirstInput) {      // TODO : "="이 두 번 눌렸을 경우 0으로 초기화 *예외처리
                resultNumber = 0;  //초기화
                operator = '+';  //초기화
                setClearText(CLEAR_INPUT_TEXT); // 55줄로 이동
            } else {
                resultNumber = intCal(resultNumber, Integer.parseInt(resultText.getText().toString()), operator);  // 91줄로 이동
                resultText.setText(String.valueOf(resultNumber));
                isFirstInput = true; //초기화
            }

        } else {
            if (isFirstInput) {  // TODO : "operator"이 두 번이상 눌렸을 경우 "operator"값만 저장함 *예외처리
                operator = getButton.getText().toString().charAt(0);
            } else {
                int lastNum = Integer.parseInt(resultText.getText().toString());
                resultNumber = intCal(resultNumber, lastNum, operator);         // 91줄로 이동
                operator = getButton.getText().toString().charAt(0); // addition버튼에 있기 때문에 초기화
                resultText.setText(String.valueOf(resultNumber));
                isFirstInput = true; //초기화
            }
        }
    }

    // TODO : 사칙연삭을 통해 값을 반환해주는 메소드
    public int intCal(int result, int lastNum, char operator) {
        if (operator == '+') {
            result += lastNum;
        } else if (operator == '-') {
            result -= lastNum;
        } else if (operator == '/') {
            result /= lastNum;
        } else if (operator == '*') {
            result *= lastNum;
        }
        return result;
    }
}