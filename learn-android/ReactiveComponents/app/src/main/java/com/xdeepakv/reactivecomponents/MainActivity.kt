package com.xdeepakv.reactivecomponents

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private val disposables = CompositeDisposable()
    override fun onDestroy() {
        super.onDestroy()
        disposables.clear() // do not send event after activity has been destroyed
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sendMessage.setOnClickListener({ v ->
            (application as ReduxApp).bus().send(Events.Message("You clicked the email button!"))
        });
    }


}
