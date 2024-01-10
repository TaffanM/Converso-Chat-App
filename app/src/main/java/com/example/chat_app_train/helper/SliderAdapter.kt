package com.example.chat_app_train.helper

import android.view.View
import android.content.Context
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.example.chat_app_train.R

class SliderAdapter(private val context: Context) : PagerAdapter() {

    private val images = intArrayOf(
        R.drawable.mobile_login_amico,
        R.drawable.android_rafiki,
        R.drawable.online_wishes_cuate
    )

    private val descriptions = intArrayOf(
        R.string.first_desc_ob,
        R.string.second_desc_ob,
        R.string.third_desc_ob
    )

    private val titles = intArrayOf(
        R.string.first_title,
        R.string.second_title,
        R.string.third_title
    )
    override fun getCount(): Int {
        return titles.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object` as View
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.slides_layout, container, false)

        val imageView = view.findViewById<ImageView>(R.id.imageSlider)
        val descView = view.findViewById<TextView>(R.id.textSlider)
        val titleView = view.findViewById<TextView>(R.id.titleSlider)

        imageView.setImageResource(images[position])
        descView.text = context.getString(descriptions[position])
        titleView.text = context.getString(titles[position])

        container.addView(view)
        return view
    }

}