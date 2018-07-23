package com.xdeepakv.reactivecomponents;

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView

class State(open var message: String = "default") {}
class Props(open val message: String = "default") {
}

class TimeComponent(context: Context, attrs: AttributeSet) : ReactiveComponent<Props, State>(context, attrs) {
    override fun render(context: Context?, props: Props?, state: State?): View? {
        var v = view
        if (v == null) {
            v = LayoutInflater.from(context)
                    .inflate(R.layout.counter_component, this, true)
        }
        val counter = v?.findViewById<TextView>(R.id.counter)
        counter?.text = state?.message
        return v;
    }
}