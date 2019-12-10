package androidmvvm.ui.deals_dashboard

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidmvvm.R
import androidmvvm.data.model.Tab
import androidmvvm.utils.Helper
import kotlin.math.roundToInt


/**
 * Created by CIS Dev 816 on 6/4/18.*/

class DashboardTabsRVAdapter(var context: Context?, var tabs: ArrayList<Tab>?, var helper: Helper?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VT_TAB = 0
    private var listener: TabClickListener? = null
    private var currentCheckedTab: Tab? = null

    fun setTabClickListener(clickListener: TabClickListener) {
        this.listener = clickListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder: RecyclerView.ViewHolder
        val inflater = LayoutInflater.from(viewGroup.context)

        val v1 = inflater.inflate(R.layout.layout_custum_tab, viewGroup, false)
        viewHolder = TabViewHolder(v1)

        return viewHolder
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when (viewHolder.itemViewType) {
            VT_TAB -> {
                val tabVH = viewHolder as TabViewHolder

                val params = tabVH.ll_main?.layoutParams
                //Uncomment for fixed tab
                /*var screenWidth = helper.getScreenWidth(context)
                var updatedWidth = screenWidth / tabs!!.size*/

                //Uncomment for scrollable tabs
                val screenWidth = Helper.getScreenWidth(context)
                val currentWidth = Helper.dpToPx(70F)
                val tabNo = screenWidth / currentWidth?.roundToInt()!! + 0.5
                val updatedWidth = screenWidth / tabNo

                params?.width = updatedWidth.roundToInt()

                val tab = getItem(position)

                if (tab != null) {
                    tabVH.tv_tab_name.text = tab.name

                    if (tab.isChecked) {
                        currentCheckedTab = tab
                        tabVH.iv_tab_icon.setImageResource(tab.iconChecked)
                        tabVH.tv_tab_name.setTextColor(context!!.resources.getColor(R.color.white))
                    } else {
                        tabVH.iv_tab_icon.setImageResource(tab.iconUnChecked)
                        tabVH.tv_tab_name.setTextColor(context!!.resources.getColor(R.color.cornflower_blue))
                    }

                    tabVH.itemView.setOnClickListener(View.OnClickListener {
                        listener?.onTabClicked(tab)
                    })
                }
            }

            else -> {
            }
        }
    }

    override fun getItemCount(): Int {
        return tabs!!.size
    }

    override fun getItemViewType(position: Int): Int {
        return VT_TAB
    }

    fun getItem(position: Int): Tab? {
        return tabs!![position]
    }

    fun setTabChecked(tab: Tab) {
        if (currentCheckedTab != null && tabs!!.contains(currentCheckedTab!!)) {
            val index = tabs!!.indexOf(currentCheckedTab!!)
            currentCheckedTab!!.isChecked = false
            tabs!!.set(index, currentCheckedTab!!)
            notifyDataSetChanged()
        }

        if (tabs!!.contains(tab)) {
            val index = tabs!!.indexOf(tab)
            tab.isChecked = true
            tabs!!.set(index, tab)
            notifyDataSetChanged()
        }
    }


    class TabViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var ll_main: LinearLayout
        var tv_tab_name: TextView
        var iv_tab_icon: ImageView

        init {
            ll_main = itemView.findViewById(R.id.ll_main)
            tv_tab_name = itemView.findViewById(R.id.tv_tab_name)
            iv_tab_icon = itemView.findViewById(R.id.iv_tab_icon)
        }
    }

    interface TabClickListener {
        fun onTabClicked(tab: Tab)
    }
}
