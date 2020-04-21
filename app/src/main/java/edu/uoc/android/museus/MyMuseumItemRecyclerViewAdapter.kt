package edu.uoc.android.museus


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import edu.uoc.android.R
import edu.uoc.android.rest.models.Element
import edu.uoc.android.rest.models.Museums
import kotlinx.android.synthetic.main.fragment_museum_item.view.*

/**
 * [RecyclerView.Adapter] that can display a [Museums].
 */
class MyMuseumItemRecyclerViewAdapter(private val mValues: List<Element>) :
    RecyclerView.Adapter<MyMuseumItemRecyclerViewAdapter.ViewHolder>() {
    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_museum_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.mAdrecaNomView.text = item.adrecaNom
        Picasso
            .get()
            .load(item.imatge?.first())
            .into(holder.mImatgeView)
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val mAdrecaNomView: TextView = mView.tv_adreca_nom
        val mImatgeView: ImageView = mView.iv_imatge
    }
}