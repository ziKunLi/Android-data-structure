package com.example.newbies.myapplication.activity.studyActivity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newbies.myapplication.R;
import com.example.newbies.myapplication.activity.BaseActivity;
import com.example.newbies.myapplication.util.CalculateRatio;

import java.util.ArrayList;

/**
 * @author NewBies
 * @date 2017/11/28
 */
public class PokerGameActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 显示用户输入的答案
     */
    private TextView inputAnswer;
    /**
     * 显示题目的四张牌
     */
    private ImageView img1;
    private ImageView img2;
    private ImageView img3;
    private ImageView img4;
    /**
     * 回退按钮
     */
    private Button cencle;
    /**
     * 四个数字按钮
     */
    private Button num1;
    private Button num2;
    private Button num3;
    private Button num4;
    /**
     * 左括号
     */
    private Button leftBracket;
    /**
     * 确定按钮
     */
    private Button sure;
    /**
     * 四个操作符按钮
     */
    private Button add;
    private Button sub;
    private Button mul;
    private Button div;
    /**
     * 右括号
     */
    private Button rightBracket;
    /**
     * 查找有解概率
     */
    private Button findProbability;
    /**
     * 显示所有正确的答案
     */
    private Button showAnswer;
    /**
     * 自定义开始
     */
    private Button customizeStart;
    /**
     * 自定义开始产生的数字
     */
    private int customizeNum1;
    private int customizeNum2;
    private int customizeNum3;
    private int customizeNum4;
    /**
     * 随机开始
     */
    private Button randomStart;
    /**
     * 随机开始产生的数字
     */
    private int random1;
    private int random2;
    private int random3;
    private int random4;
    /**
     * 显示所有答案的窗口
     */
    private PopupWindow showAnswerPop;
    /**
     * 显示提供选择的牌，用于自定义
     */
    private PopupWindow selectPokerPop;
    /**
     * 用于显示答案的文本标签
     */
    private TextView answerText;
    /**
     * 存储答案
     */
    private ArrayList<String> expressionList;
    /**
     * 13个自定义卡牌的按钮
     */
    private TextView inputPoker1;
    private TextView inputPoker2;
    private TextView inputPoker3;
    private TextView inputPoker4;
    private TextView inputPoker5;
    private TextView inputPoker6;
    private TextView inputPoker7;
    private TextView inputPoker8;
    private TextView inputPoker9;
    private TextView inputPoker10;
    private TextView inputPoker11;
    private TextView inputPoker12;
    private TextView inputPoker13;
    /**
     * 显示已选扑克牌的四个文本标签
     */
    private TextView answerPoker1;
    private TextView answerPoker2;
    private TextView answerPoker3;
    private TextView answerPoker4;
    /**
     * 文本标签索引
     */
    private int index = 0;
    /**
     * 关闭弹窗
     */
    private TextView closeSelectPokerPop;
    /**
     * 输入回退
     */
    private TextView back;
    /**
     * 手机屏幕的高
     */
    private int height;
    /**
     * 手机屏幕的宽
     */
    private int width;
    /**
     * 用户输入的表达式
     */
    private String expression;
    /**
     * 防止用户连续输入数字
     */
    private boolean canContinue = true;
    private Bitmap[] images = new Bitmap[52];

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏状态栏，该句话一定要放在setContentView(R.layout.huffman);之前
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.poker_game);
        initView();
        initListener();
        initData();
        randomStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        //设置为横屏
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    @Override
    public void initView() {
        inputAnswer = (TextView) findViewById(R.id.input_answer);
        img1 = (ImageView) findViewById(R.id.img1);
        img2 = (ImageView) findViewById(R.id.img2);
        img3 = (ImageView) findViewById(R.id.img3);
        img4 = (ImageView) findViewById(R.id.img4);
        cencle = (Button) findViewById(R.id.cencle);
        num1 = (Button) findViewById(R.id.num1);
        num2 = (Button) findViewById(R.id.num2);
        num3 = (Button) findViewById(R.id.num3);
        num4 = (Button) findViewById(R.id.num4);
        leftBracket = (Button) findViewById(R.id.leftBracket);
        sure = (Button) findViewById(R.id.sure);
        add = (Button) findViewById(R.id.add);
        sub = (Button) findViewById(R.id.sub);
        mul = (Button) findViewById(R.id.mul);
        div = (Button) findViewById(R.id.div);
        rightBracket = (Button) findViewById(R.id.rightBracket);
        findProbability = (Button) findViewById(R.id.findProbability);
        customizeStart = (Button) findViewById(R.id.customizeStart);
        showAnswer = (Button) findViewById(R.id.showAnswer);
        randomStart = (Button) findViewById(R.id.randomStart);

        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        height = windowManager.getDefaultDisplay().getHeight();
        width = windowManager.getDefaultDisplay().getWidth();
    }

    /**
     * 初始化相关组件的时间响应
     */
    @Override
    public void initListener() {
        cencle.setOnClickListener(this);
        num1.setOnClickListener(this);
        num2.setOnClickListener(this);
        num3.setOnClickListener(this);
        num4.setOnClickListener(this);
        add.setOnClickListener(this);
        sub.setOnClickListener(this);
        mul.setOnClickListener(this);
        div.setOnClickListener(this);
        leftBracket.setOnClickListener(this);
        rightBracket.setOnClickListener(this);
        findProbability.setOnClickListener(this);
        customizeStart.setOnClickListener(this);
        showAnswer.setOnClickListener(this);
        randomStart.setOnClickListener(this);
    }

    /**
     * 初始化数据
     */
    public void initData() {
        for (int i = 0; i < 13; i++) {
            images[i] = getImgByRes("hearts_" + (i + 1));
        }
        for (int i = 0; i < 13; i++) {
            images[i + 13] = getImgByRes("plum_blossom_" + (i + 1));
        }
        for (int i = 0; i < 13; i++) {
            images[i + 26] = getImgByRes("spades_" + (i + 1));
        }
        for (int i = 0; i < 13; i++) {
            images[i + 39] = getImgByRes("square_" + (i + 1));
        }
    }

    /**
     * 随机设置四张牌
     */
    public void randomStart() {
        random1 = (int) (Math.random() * 13);
        random2 = (int) (Math.random() * 13);
        random3 = (int) (Math.random() * 13);
        random4 = (int) (Math.random() * 13);

        img1.setImageBitmap(images[random1]);
        img2.setImageBitmap(images[random2 + 13]);
        img3.setImageBitmap(images[random3 + 26]);
        img4.setImageBitmap(images[random4 + 39]);

        num1.setText((random1 + 1) + "");
        num2.setText((random2 + 1) + "");
        num3.setText((random3 + 1) + "");
        num4.setText((random4 + 1) + "");
    }

    /**
     * 从res文件夹中获取图片资源
     *
     * @param name
     * @return
     */
    public Bitmap getImgByRes(String name) {
        //得到application对象
        ApplicationInfo appInfo = getApplicationInfo();
        //得到该图片的id(name 是该图片的名字，"drawable" 是该图片存放的目录，appInfo.packageName是应用程序的包)
        int resID = getResources().getIdentifier(name, "drawable", appInfo.packageName);

        return BitmapFactory.decodeResource(getResources(), resID);
    }

    /**
     * 设置并打开显示答案的popupWindow
     */
    public void setShowAnswerPop() {
        String expression = "";
        if (expressionList != null) {
            for (int i = 0; i < expressionList.size(); i++) {
                expression += expressionList.get(i) + "\n";
            }
        } else {
            expression = "无解";
        }
        if (showAnswerPop == null) {
            View popView = getLayoutInflater().inflate(R.layout.poker_answer_pop, null);
            //设置显示大小
            showAnswerPop = new PopupWindow(popView, width / 2, height / 2, true);
            //设置显示位置
            showAnswerPop.showAtLocation(randomStart, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            showAnswerPop.setBackgroundDrawable(new ColorDrawable());
            //设置点击外部关闭
            showAnswerPop.setOutsideTouchable(true);
            //查找组件
            answerText = (TextView) popView.findViewById(R.id.answerText);
            //设置显示答案
            answerText.setText(expression);
            setBackgroundAlpha(0.4f);
            //设置关闭事件
            showAnswerPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    setBackgroundAlpha(1.0f);
                }
            });
        } else {
            showAnswerPop.showAtLocation(randomStart, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            //设置显示答案
            answerText.setText(expression);
            setBackgroundAlpha(0.4f);
        }
    }

    public void setSelectPokerPop() {
        if (selectPokerPop == null) {
            View popView = getLayoutInflater().inflate(R.layout.poker_customize_start_pop, null);
            selectPokerPop = new PopupWindow(popView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, true);
            selectPokerPop.setBackgroundDrawable(new ColorDrawable());
            selectPokerPop.setOutsideTouchable(true);
            selectPokerPop.showAsDropDown(randomStart, 0, 0);
            selectPokerPop.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    setBackgroundAlpha(1.0f);
                }
            });
            setBackgroundAlpha(0.3f);

            //查找相关按钮
            inputPoker1 = (TextView) popView.findViewById(R.id.poker1);
            inputPoker2 = (TextView) popView.findViewById(R.id.poker2);
            inputPoker3 = (TextView) popView.findViewById(R.id.poker3);
            inputPoker4 = (TextView) popView.findViewById(R.id.poker4);
            inputPoker5 = (TextView) popView.findViewById(R.id.poker5);
            inputPoker6 = (TextView) popView.findViewById(R.id.poker6);
            inputPoker7 = (TextView) popView.findViewById(R.id.poker7);
            inputPoker8 = (TextView) popView.findViewById(R.id.poker8);
            inputPoker9 = (TextView) popView.findViewById(R.id.poker9);
            inputPoker10 = (TextView) popView.findViewById(R.id.poker10);
            inputPoker11 = (TextView) popView.findViewById(R.id.poker11);
            inputPoker12 = (TextView) popView.findViewById(R.id.poker12);
            inputPoker13 = (TextView) popView.findViewById(R.id.poker13);

            answerPoker1 = (TextView) popView.findViewById(R.id.answer_poker1);
            answerPoker2 = (TextView) popView.findViewById(R.id.answer_poker2);
            answerPoker3 = (TextView) popView.findViewById(R.id.answer_poker3);
            answerPoker4 = (TextView) popView.findViewById(R.id.answer_poker4);

            closeSelectPokerPop = (TextView) popView.findViewById(R.id.close_pop);
            back = (TextView) popView.findViewById(R.id.back);

            View.OnClickListener selectPokerPopLiistener = new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    switch (v.getId()) {
                        case R.id.poker1:
                            index++;
                            setInputAnswerText(1);
                            break;
                        case R.id.poker2:
                            index++;
                            setInputAnswerText(2);
                            break;
                        case R.id.poker3:
                            index++;
                            setInputAnswerText(3);
                            break;
                        case R.id.poker4:
                            index++;
                            setInputAnswerText(4);
                            break;
                        case R.id.poker5:
                            index++;
                            setInputAnswerText(5);
                            break;
                        case R.id.poker6:
                            index++;
                            setInputAnswerText(6);
                            break;
                        case R.id.poker7:
                            index++;
                            setInputAnswerText(7);
                            break;
                        case R.id.poker8:
                            index++;
                            setInputAnswerText(8);
                            break;
                        case R.id.poker9:
                            index++;
                            setInputAnswerText(9);
                            break;
                        case R.id.poker10:
                            index++;
                            setInputAnswerText(10);
                            break;
                        case R.id.poker11:
                            index++;
                            setInputAnswerText(11);
                            break;
                        case R.id.poker12:
                            index++;
                            setInputAnswerText(12);
                            break;
                        case R.id.poker13:
                            index++;
                            setInputAnswerText(13);
                            break;
                        case R.id.close_pop:
                            selectPokerPop.dismiss();
                            index = 0;
                            answerPoker1.setText("");
                            answerPoker2.setText("");
                            answerPoker3.setText("");
                            answerPoker4.setText("");
                            break;
                        case R.id.back:
                            break;
                        default:
                            break;
                    }
                }
            };

            //相关事件响应
            closeSelectPokerPop.setOnClickListener(selectPokerPopLiistener);
            inputPoker1.setOnClickListener(selectPokerPopLiistener);
            inputPoker2.setOnClickListener(selectPokerPopLiistener);
            inputPoker3.setOnClickListener(selectPokerPopLiistener);
            inputPoker4.setOnClickListener(selectPokerPopLiistener);
            inputPoker5.setOnClickListener(selectPokerPopLiistener);
            inputPoker6.setOnClickListener(selectPokerPopLiistener);
            inputPoker7.setOnClickListener(selectPokerPopLiistener);
            inputPoker8.setOnClickListener(selectPokerPopLiistener);
            inputPoker9.setOnClickListener(selectPokerPopLiistener);
            inputPoker10.setOnClickListener(selectPokerPopLiistener);
            inputPoker11.setOnClickListener(selectPokerPopLiistener);
            inputPoker12.setOnClickListener(selectPokerPopLiistener);
            inputPoker13.setOnClickListener(selectPokerPopLiistener);
        } else {
            selectPokerPop.showAsDropDown(randomStart, 0, 0);
            setBackgroundAlpha(0.3f);
        }
    }

    /**
     * 用于设置自定义开始面板4个数字框
     *
     * @param num
     */
    public void setInputAnswerText(int num) {
        switch (index) {
            case 1:
                answerPoker1.setText(num + "");
                break;
            case 2:
                answerPoker2.setText(num + "");
                break;
            case 3:
                answerPoker3.setText(num + "");
                break;
            case 4:
                answerPoker4.setText(num + "");
                index = 0;
                setCustomizeStart();
                break;
            default:
                break;
        }
    }

    /**
     * 自定义设置开始时的准备工作
     */
    public void setCustomizeStart() {
        customizeNum1 = Integer.parseInt(answerPoker1.getText().toString());
        customizeNum2 = Integer.parseInt(answerPoker2.getText().toString());
        customizeNum3 = Integer.parseInt(answerPoker3.getText().toString());
        customizeNum4 = Integer.parseInt(answerPoker4.getText().toString());

        img1.setImageBitmap(images[customizeNum1 - 1]);
        img2.setImageBitmap(images[customizeNum2 + 12]);
        img3.setImageBitmap(images[customizeNum3 + 25]);
        img4.setImageBitmap(images[customizeNum4 + 38]);

        this.num1.setText(customizeNum1 + "");
        this.num2.setText(customizeNum2 + "");
        this.num3.setText(customizeNum3 + "");
        this.num4.setText(customizeNum4 + "");

        answerPoker1.setText("");
        answerPoker2.setText("");
        answerPoker3.setText("");
        answerPoker4.setText("");

        selectPokerPop.dismiss();
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     * @auther 李自坤
     */
    @Override
    public void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        //0.0-1.0
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }

    public boolean computeResult() {
        int result = 0;
        expression = inputAnswer.getText().toString();
        for(int i = 0;i < expression.length(); i++){

        }
        return false;
    }

    /**
     * 所有点击事件集合
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        expression = inputAnswer.getText().toString();
        switch (v.getId()) {
            case R.id.cencle:
                if (inputAnswer.getText().toString().length() > 0) {
                    try {
                        int temp = 0;
                        //如果最后一个字符不是数字，那么就会自动报错，跳过以后的语句
                        temp = Integer.parseInt(expression.charAt(expression.length() - 1) + "");
                        //如果前面没报错才能执行到这一步，说明最后一个字符是数字，现在我们就可以判断倒数第二个字符是不是数字，从而判断该数是不是两位数
                        if (expression.length() > 1 && Character.isDigit(expression.charAt(expression.length() - 2))) {
                            temp = Integer.parseInt(expression.substring(expression.length() - 2, expression.length()) + "");
                            //如果是两位数，那么就应该一次截取两个数，这里先截取一位，然后后面马上就会截取第二位
                            expression = expression.substring(0, expression.length() - 1);
                            inputAnswer.setText(expression);
                        }

                        if (temp == random1 + 1) {
                            num1.setText(temp + "");
                        } else if (temp == random2 + 1) {
                            num2.setText(temp + "");
                        } else if (temp == random3 + 1) {
                            num3.setText(temp + "");
                        } else if (temp == random4 + 1) {
                            num4.setText(temp + "");
                        }
                    } catch (Exception e) {
                        System.out.println();
                        //一切报错尽在掌控之中
                    } finally {
                        inputAnswer.setText(expression.substring(0, expression.length() - 1));
                    }
                }
                break;
            case R.id.num1:
                //如果用户输入的表达式长度大于0，且最后一个不是数字，说明能够继续输入数字
                if ((expression.length() > 0&&!Character.isDigit(expression.charAt(expression.length() - 1)))||(expression.length() == 0)) {
                    inputAnswer.setText(expression + num1.getText().toString());
                    num1.setText("");
                }
                break;
            case R.id.num2:
                if ((expression.length() > 0&&!Character.isDigit(expression.charAt(expression.length() - 1)))||(expression.length() == 0)) {
                    inputAnswer.setText(expression + num2.getText().toString());
                    num2.setText("");
                }
                break;
            case R.id.num3:
                if ((expression.length() > 0&&!Character.isDigit(expression.charAt(expression.length() - 1)))||(expression.length() == 0)) {
                    inputAnswer.setText(expression + num3.getText().toString());
                    num3.setText("");
                }
                break;
            case R.id.num4:
                if ((expression.length() > 0&&!Character.isDigit(expression.charAt(expression.length() - 1)))||(expression.length() == 0)) {
                    inputAnswer.setText(expression + num4.getText().toString());
                    num4.setText("");
                }
                break;
            case R.id.add:
                inputAnswer.setText(expression + "+");
                break;
            case R.id.sub:
                inputAnswer.setText(expression + "-");
                break;
            case R.id.mul:
                inputAnswer.setText(expression + "*");
                break;
            case R.id.div:
                inputAnswer.setText(expression + "/");
                break;
            case R.id.leftBracket:
                inputAnswer.setText(expression + "(");
                break;
            case R.id.rightBracket:
                inputAnswer.setText(expression + ")");
                break;
            case R.id.findProbability:
                showProgressDialog();
                findProbability.setClickable(false);
                long startTime = System.currentTimeMillis();
                String result = CalculateRatio.allResult();
                long endTime = System.currentTimeMillis();
                result += "耗时：" + (endTime - startTime)/1000.0 + "秒";
                Snackbar.make(findProbability,result,Snackbar.LENGTH_INDEFINITE).setAction("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        findProbability.setClickable(true);
                    }
                }).show();
                dismissProgressDialog();

                break;
            case R.id.customizeStart:
                setSelectPokerPop();
                break;
            case R.id.showAnswer:
                expressionList = CalculateRatio.getExpression(Integer.parseInt(num1.getText().toString()),
                        Integer.parseInt(num2.getText().toString()), Integer.parseInt(num3.getText().toString()), Integer.parseInt(num4.getText().toString()));
                setShowAnswerPop();
                break;
            case R.id.randomStart:
                randomStart();
                break;
            default:
                break;
        }
    }
}
