package com.apidemo.mac.myfinalstydyandroid.RxJava;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.apidemo.mac.myfinalstydyandroid.R;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;


/**
 * Created by cqj on 2018/4/13.
 * 响应式代码，组成部分是 Observable（可观察） 和 OnSubscribe（订阅）
 * Observable 发送消息，而SubCriber 用于消费消息
 * Observable 只有被SubCriber订阅才会开始发送消息。
 */
public class RxJavaActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);
        /**
         * Observable
         */
        Observable<String> observable = Observable.unsafeCreate(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                Log.i(RxJavaActivity.class.getSimpleName(), "call");
                subscriber.onNext("aaaaa");
                subscriber.onCompleted();
            }
        });
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.i(RxJavaActivity.class.getSimpleName(), "onCompleted:");
            }

            @Override
            public void onError(Throwable e) {
                Log.i(RxJavaActivity.class.getSimpleName(), e.getMessage());
            }

            @Override
            public void onNext(String s) {
                Log.i(RxJavaActivity.class.getSimpleName(), "onNext:" + s);
            }
        };
        observable.subscribe(subscriber);

        Observable<String> obs = Observable.just("Hello");//just 发送一个消息然后完成
        Action1<String> onNextAction = new Action1<String>() {
            @Override
            public void call(String s) {
                Log.i(RxJavaActivity.class.getSimpleName(), "OnNextAction call:" + s);
            }
        };

        obs.subscribe(onNextAction);

        Observable.just("Hello Word").subscribe(s -> Log.i(RxJavaActivity.class.getSimpleName(), "simple Observer"));


    }


}
