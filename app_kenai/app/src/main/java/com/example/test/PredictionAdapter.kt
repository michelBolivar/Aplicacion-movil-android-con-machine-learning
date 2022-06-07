package com.example.test

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.CountDownTimer
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.animation.PathInterpolatorCompat
import com.example.test.Catego.ControladorCD
import com.example.test.DetalleCliente.DetalleCliente
import com.example.test.tflite.Classifier
import kotlin.math.min

class PredictionAdapter(context: Context) : BaseAdapter() {

    private val MAX_COUNT_SHOWN: Int = 1
    private val mInflator: LayoutInflater
    private val context: Context

    private var mList = arrayOf(
        Classifier.Recognition("0", "cero", 0.8f, null),
        Classifier.Recognition("1", "uno", 0.8f, null),
        Classifier.Recognition("2", "dos", 0.8f, null)

    )
    private var mCountToShow: Int = MAX_COUNT_SHOWN


    fun setItems(predictions: List<Classifier.Recognition>) {
        var num = min(predictions.count(), mCountToShow) -1
        var num_uno = 1
        mList = predictions.toTypedArray().sliceArray(0..num);
        (this.context as Activity).runOnUiThread(Runnable { notifyDataSetChanged() })
    }

    fun toggleCountShown() {
        this.mCountToShow = if (this.mCountToShow == 1) MAX_COUNT_SHOWN else 1
    }

    init {
        this.mInflator = LayoutInflater.from(context)
        this.context = context
    }

    override fun getCount(): Int {
        return mList.size
    }

    override fun getItem(position: Int): Any {
        return mList[position]

    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun getView(position: Int, convertView: View?, container: ViewGroup?): View? {

        var view: View? = convertView
        if (view == null) {
            view = this.mInflator.inflate(R.layout.customlistview, container, false)
        }

        var progressBar: ProgressBar? = view!!.findViewById(R.id.customProgressBar)
        var textView: TextView? = view.findViewById(R.id.customTextView)




        var item = (this.getItem(position) as Classifier.Recognition);
        textView!!.text = item.title

        item.id



        /*CRONOMETRO*/

       var txtTiempo= 6000
        // var tvCuentaAtras:TextView = view.findViewById(R.id.cronometro)
        var tiempoMilisegundo  = txtTiempo.toLong()
       object : CountDownTimer(tiempoMilisegundo,1000){
          override fun onTick(p0: Long) {
            }
            override fun onFinish() {
                //LLAMAMOS AL METODO
                var detalle = ControladorCD()
                detalle.ejecutar(item.title, context)
                ActivityCompat.finishAffinity(detalle);
                detalle.finish()
               this.cancel()
            }
        }.start()





        val displayMetrics: DisplayMetrics = this.context.resources.displayMetrics
        val dpWidth =
            Math.round(container!!.width / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))

        val minProgress = 32.0f / dpWidth * 100.0f

        val confidence: Float =
            ((100.0f - minProgress) / 100.0f) * (item.confidence * 100.0f) + minProgress

        val anim = ProgressBarAnimation(
            progressBar!!,
            if (position == 0) progressBar!!.progress * 1.0f else progressBar!!.secondaryProgress * 1.0f,
            confidence,
            position == 0
        )
       // anim.duration = 10
        anim.interpolator = PathInterpolatorCompat.create(0.420f, 0.000f, 0.580f, 1.000f)
        progressBar.startAnimation(anim)

        return view
    }


}