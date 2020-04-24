package edu.uoc.android.quizz

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import edu.uoc.android.R
import kotlinx.android.synthetic.main.fragment_question_item.view.*

/**
 * [RecyclerView.Adapter] that can display a [Question].
 */
class MyquestionItemRecyclerViewAdapter(
    private val mValues: List<Question>,
    private val context: Context
) : RecyclerView.Adapter<MyquestionItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_question_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mTitleView.text = item.title
        Picasso
            .get()
            .load(item.image)
            .placeholder(R.drawable.noimage)
            .error(R.drawable.noimage)
            .into(holder.mImage)

        for ((index, c) in item.choices.withIndex()) {
            val tvChoice = TextView(context)
            tvChoice.text = c

            if (index == item.rightChoice) {
                tvChoice.setTypeface(null, Typeface.BOLD)
            }

            holder.mChoices.addView(tvChoice)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val mTitleView: TextView = mView.tv_title
        val mImage: ImageView = mView.iv_imatge
        val mChoices: LinearLayout = mView.lv_choices

        override fun toString(): String {
            return super.toString() + " '" + mTitleView.text + "'"
        }
    }
}
