package androidmvvm.ui.deals_dashboard.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.SpannableString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidmvvm.R
import androidmvvm.data.model.Deal
import androidmvvm.data.remote.extensions.loadGIF
import androidmvvm.data.remote.extensions.loadUrl
import androidmvvm.utils.Constants
import androidmvvm.utils.Helper
import androidmvvm.utils.Helper.Companion.getDisplayDate
import androidmvvm.utils.Helper.Companion.getEngDate
import androidmvvm.utils.LogUtils
import java.util.*

/**
 * Created by CIS Dev 816 on 9/4/18.
 */
class DealsRVAdapter(var context: Context?, var deals: ArrayList<Deal>?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TAG = DealsRVAdapter::class.java.simpleName

    private val VT_PACKAGE = 1
    private val VT_FLIGHT = 2
    private val VT_TOUR = 3
    private val VT_PASSOVER = 4
    private val VT_SPORTS = 5
    private val VT_CONCERTS = 6
    private var listener: DealClickListener? = null

    fun setDealClickListener(clickListener: DealClickListener) {
        this.listener = clickListener
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewHolder: RecyclerView.ViewHolder
        val inflater = LayoutInflater.from(viewGroup.context)

        when (viewType) {
            VT_PACKAGE -> {
                val v1 = inflater.inflate(R.layout.item_package, viewGroup, false)
                viewHolder = PackageViewHolder(v1)
            }

            VT_FLIGHT -> {
                val v1 = inflater.inflate(R.layout.item_flight, viewGroup, false)
                viewHolder = FlightsViewHolder(v1)
            }

            VT_TOUR -> {
                val v1 = inflater.inflate(R.layout.item_tour, viewGroup, false)
                viewHolder = TourViewHolder(v1)
            }

            VT_PASSOVER -> {
                val v1 = inflater.inflate(R.layout.item_passover, viewGroup, false)
                viewHolder = PassoverViewHolder(v1)
            }

            VT_SPORTS -> {
                val v1 = inflater.inflate(R.layout.item_sport, viewGroup, false)
                viewHolder = SportsViewHolder(v1)
            }

            VT_CONCERTS -> {
                val v1 = inflater.inflate(R.layout.item_concerts, viewGroup, false)
                viewHolder = ConcertsViewHolder(v1)
            }

            else -> {
                val v1 = inflater.inflate(R.layout.item_deal, viewGroup, false)
                viewHolder = PackageViewHolder(v1)
            }
        }

        return viewHolder
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        when (viewHolder.itemViewType) {
            VT_PACKAGE -> {
                try {
                    val vh = viewHolder as PackageViewHolder

                    val deal = getItem(position)

                    if (deal != null) {
                        vh.tv_title.text = deal.PackageTitle
                        vh.tv_tags.text = deal.Tags
                        vh.tv_stay.text = deal.NumberOfNights + " " + context?.getString(R.string.night)
                        vh.tv_destination.text = deal.Destination

                        if (!deal.File_location.isNullOrEmpty()) {
                            vh.iv_thumbnail.loadUrl(context, deal.File_location!!)
                        }

                        vh.iv_arrow.loadGIF(context, R.raw.arrow)

                        if (deal.PackagePrice != null) {
                            var strPrice = ""
                            val ssPrice: SpannableString

                            if (!deal.Currency.isNullOrEmpty()) {
                                if (deal.Currency.equals(Constants.Currency.US, true)) {
                                    strPrice = Constants.Currency.DOLLER + deal.PackagePrice
                                } else if (deal.Currency.equals(Constants.Currency.EU, true)) {
                                    strPrice = Constants.Currency.EURO + deal.PackagePrice
                                } else {
                                    strPrice = deal.Currency + deal.PackagePrice
                                }

                                /*ssPrice = SpannableString(strPrice)*/
                            } else {
                                strPrice = Constants.Currency.DOLLER + deal.PackagePrice
                                /*ssPrice = SpannableString(strPrice)*/
                            }

                            /*ssPrice.setSpan(RelativeSizeSpan(Constants.DOLLER_SIZE), 0, 1, 0)*/
                            //vh.tv_price.text = ssPrice
                            //vh.tv_price.setAnimationListener(SimpleAnimationListener(this))
                            vh.tv_price.text = strPrice
                        }

                        if (deal.StartDate != null) {
                            val (dd, mmYYYY) = getDisplayDate(deal.StartDate)
                            vh.tv_departure_date.text = getEngDate(deal.StartDate)
                            /*vh.tv_departure_month_year.text = mmYYYY*/
                        }

                        if (deal.EndDate != null) {
                            val (dd, mmYYYY) = getDisplayDate(deal.EndDate)
                            vh.tv_return_date.text = getEngDate(deal.EndDate)
                            /*vh.tv_return_month_year.text = mmYYYY*/
                        }

                        if (deal.IsActive != null && deal.IsActive.equals("true")) {
                            vh.v_sold_out.visibility = View.GONE
                            vh.iv_sold_out.visibility = View.GONE
                        } else {
                            vh.v_sold_out.visibility = View.VISIBLE
                            vh.iv_sold_out.visibility = View.VISIBLE
                        }

                        vh.itemView.setOnClickListener {
                            listener?.onDealClicked(deal)
                        }
                    }
                } catch (ex: Exception) {
                    LogUtils.e(TAG, ex.message.toString())
                }
            }

            VT_FLIGHT -> {
                try {
                    val vh = viewHolder as FlightsViewHolder

                    val deal = getItem(position)

                    if (deal != null) {
                        vh.tv_title.text = deal.PackageTitle
                        vh.tv_tags.text = deal.Tags
                        vh.tv_stay.text = deal.NumberOfNights + " " + context?.getString(R.string.night)
                        vh.tv_destination.text = deal.Destination

                        if (!deal.File_location.isNullOrEmpty()) {
                            vh.iv_thumbnail.loadUrl(context, deal.File_location!!)
                        }

                        vh.iv_arrow.loadGIF(context, R.raw.arrow)

                        if (deal.PackagePrice != null) {
                            var strPrice = ""

                            if (!deal.Currency.isNullOrEmpty()) {
                                if (deal.Currency.equals(Constants.Currency.US, true)) {
                                    strPrice = Constants.Currency.DOLLER + deal.PackagePrice
                                } else if (deal.Currency.equals(Constants.Currency.EU, true)) {
                                    strPrice = Constants.Currency.EURO + deal.PackagePrice
                                } else {
                                    strPrice = deal.Currency + deal.PackagePrice
                                }
                            } else {
                                strPrice = Constants.Currency.DOLLER + deal.PackagePrice
                            }

                            vh.tv_price.text = strPrice
                        }

                        if (deal.StartDate != null) {
                            val (dd, mmYYYY) = getDisplayDate(deal.StartDate)
                            vh.tv_departure_date.text = getEngDate(deal.StartDate)
                        }

                        if (deal.EndDate != null) {
                            val (dd, mmYYYY) = getDisplayDate(deal.EndDate)
                            vh.tv_return_date.text = getEngDate(deal.EndDate)
                        }

                        if (deal.IsActive != null && deal.IsActive.equals("true")) {
                            vh.v_sold_out.visibility = View.GONE
                            vh.iv_sold_out.visibility = View.GONE
                        } else {
                            vh.v_sold_out.visibility = View.VISIBLE
                            vh.iv_sold_out.visibility = View.VISIBLE
                        }

                        vh.itemView.setOnClickListener({
                            listener?.onDealClicked(deal)
                        })
                    }
                } catch (ex: Exception) {
                    LogUtils.e(TAG, ex.message.toString())
                }
            }

            VT_PASSOVER -> {
                try {
                    val vh = viewHolder as PassoverViewHolder

                    val deal = getItem(position)

                    if (deal != null) {
                        vh.tv_title.text = deal.PackageTitle
                        vh.tv_tags.text = deal.Tags
                        vh.tv_stay.text = deal.NumberOfNights + " " + context?.getString(R.string.night)
                        vh.tv_destination.text = deal.Destination

                        if (!deal.File_location.isNullOrEmpty()) {
                            vh.iv_thumbnail.loadUrl(context, deal.File_location!!)
                        }

                        vh.iv_arrow.loadGIF(context, R.raw.arrow)

                        if (deal.PackagePrice != null) {
                            var strPrice = ""

                            if (!deal.Currency.isNullOrEmpty()) {
                                if (deal.Currency.equals(Constants.Currency.US, true)) {
                                    strPrice = Constants.Currency.DOLLER + deal.PackagePrice
                                } else if (deal.Currency.equals(Constants.Currency.EU, true)) {
                                    strPrice = Constants.Currency.EURO + deal.PackagePrice
                                } else {
                                    strPrice = deal.Currency + deal.PackagePrice
                                }
                            } else {
                                strPrice = Constants.Currency.DOLLER + deal.PackagePrice
                            }

                            vh.tv_price.text = strPrice
                        }

                        if (deal.StartDate != null) {
                            val (dd, mmYYYY) = getDisplayDate(deal.StartDate)
                            vh.tv_departure_date.text = getEngDate(deal.StartDate)
                        }

                        if (deal.EndDate != null) {
                            val (dd, mmYYYY) = getDisplayDate(deal.EndDate)
                            vh.tv_return_date.text = getEngDate(deal.EndDate)
                        }

                        if (deal.IsActive != null && deal.IsActive.equals("true")) {
                            vh.v_sold_out.visibility = View.GONE
                            vh.iv_sold_out.visibility = View.GONE
                        } else {
                            vh.v_sold_out.visibility = View.VISIBLE
                            vh.iv_sold_out.visibility = View.VISIBLE
                        }

                        vh.itemView.setOnClickListener {
                            listener?.onDealClicked(deal)
                        }
                    }
                } catch (ex: Exception) {
                    LogUtils.e(TAG, ex.message.toString())
                }
            }

            VT_TOUR -> {
                try {
                    val vh = viewHolder as TourViewHolder
                    val deal = getItem(position)

                    if (deal != null) {
                        vh.tv_title.text = deal.PackageTitle
                        vh.tv_tags.text = deal.Tags

                        vh.tv_stay.text = deal.NumberOfNights + " " + context?.getString(R.string.days)
                        vh.tv_destination.text = deal.Destination

                        if (!deal.File_location.isNullOrEmpty()) {
                            vh.iv_thumbnail.loadUrl(context, deal.File_location!!)
                        }

                        vh.iv_arrow.loadGIF(context, R.raw.arrow)

                        if (deal.PackagePrice != null) {
                            var strPrice = ""

                            if (!deal.Currency.isNullOrEmpty()) {
                                if (deal.Currency.equals(Constants.Currency.US, true)) {
                                    strPrice = Constants.Currency.DOLLER + deal.PackagePrice
                                } else if (deal.Currency.equals(Constants.Currency.EU, true)) {
                                    strPrice = Constants.Currency.EURO + deal.PackagePrice
                                } else {
                                    strPrice = deal.Currency + deal.PackagePrice
                                }
                            } else {
                                strPrice = Constants.Currency.DOLLER + deal.PackagePrice
                            }

                            vh.tv_price.text = strPrice
                        }

                        if (deal.StartDate != null) {
                            val (dd, mmYYYY) = getDisplayDate(deal.StartDate)
                            vh.tv_departure_date.text = getEngDate(deal.StartDate)
                        }

                        if (deal.EndDate != null) {
                            val (dd, mmYYYY) = getDisplayDate(deal.EndDate)
                            vh.tv_return_date.text = getEngDate(deal.EndDate)
                        }

                        if (deal.IsActive != null && deal.IsActive.equals("true")) {
                            vh.v_sold_out.visibility = View.GONE
                            vh.iv_sold_out.visibility = View.GONE
                        } else {
                            vh.v_sold_out.visibility = View.VISIBLE
                            vh.iv_sold_out.visibility = View.VISIBLE
                        }

                        vh.itemView.setOnClickListener {
                            listener?.onDealClicked(deal)
                        }
                    }
                } catch (ex: Exception) {
                    LogUtils.e(TAG, ex.message.toString())
                }
            }

            VT_SPORTS -> {
                try {
                    val vh = viewHolder as SportsViewHolder

                    val deal = getItem(position)

                    if (deal != null) {
                        vh.tv_title.text = deal.PackageTitle
                        vh.tv_tags.text = deal.Tags
                        vh.tv_stay.text = deal.NumberOfNights + " " + context?.getString(R.string.night)
                        vh.tv_destination.text = deal.Destination

                        if (!deal.File_location.isNullOrEmpty()) {
                            vh.iv_thumbnail.loadUrl(context, deal.File_location!!)
                        }

                        vh.iv_arrow.loadGIF(context, R.raw.arrow)

                        if (deal.PackagePrice != null) {
                            var strPrice = ""

                            if (!deal.Currency.isNullOrEmpty()) {
                                if (deal.Currency.equals(Constants.Currency.US, true)) {
                                    strPrice = Constants.Currency.DOLLER + deal.PackagePrice
                                } else if (deal.Currency.equals(Constants.Currency.EU, true)) {
                                    strPrice = Constants.Currency.EURO + deal.PackagePrice
                                } else {
                                    strPrice = deal.Currency + deal.PackagePrice
                                }
                            } else {
                                strPrice = Constants.Currency.DOLLER + deal.PackagePrice
                            }

                            vh.tv_price.text = strPrice
                        }

                        if (deal.StartDate != null) {
                            val (dd, mmYYYY) = getDisplayDate(deal.StartDate)
                            vh.tv_departure_date.text = getEngDate(deal.StartDate)
                        }

                        if (deal.EndDate != null) {
                            val (dd, mmYYYY) = getDisplayDate(deal.EndDate)
                            vh.tv_return_date.text = getEngDate(deal.EndDate)
                        }

                        if (deal.IsActive != null && deal.IsActive.equals("true")) {
                            vh.v_sold_out.visibility = View.GONE
                            vh.iv_sold_out.visibility = View.GONE
                        } else {
                            vh.v_sold_out.visibility = View.VISIBLE
                            vh.iv_sold_out.visibility = View.VISIBLE
                        }

                        vh.itemView.setOnClickListener {
                            listener?.onDealClicked(deal)
                        }
                    }
                } catch (ex: Exception) {
                    LogUtils.e(TAG, ex.message.toString())
                }
            }

            VT_CONCERTS -> {
                try {
                    val vh = viewHolder as ConcertsViewHolder

                    val deal = getItem(position)

                    if (deal != null) {
                        vh.tv_title.text = deal.PackageTitle
                        vh.tv_tags.text = deal.Tags
                        vh.tv_stay.text = deal.NumberOfNights + " " + context?.getString(R.string.night)
                        vh.tv_destination.text = deal.Destination

                        if (!deal.File_location.isNullOrEmpty()) {
                            vh.iv_thumbnail.loadUrl(context, deal.File_location!!)
                        }

                        vh.iv_arrow.loadGIF(context, R.raw.arrow)

                        if (deal.PackagePrice != null) {
                            var strPrice = ""

                            if (!deal.Currency.isNullOrEmpty()) {
                                if (deal.Currency.equals(Constants.Currency.US, true)) {
                                    strPrice = Constants.Currency.DOLLER + deal.PackagePrice
                                } else if (deal.Currency.equals(Constants.Currency.EU, true)) {
                                    strPrice = Constants.Currency.EURO + deal.PackagePrice
                                } else {
                                    strPrice = deal.Currency + deal.PackagePrice
                                }
                            } else {
                                strPrice = Constants.Currency.DOLLER + deal.PackagePrice
                            }

                            vh.tv_price.text = strPrice
                        }

                        if (deal.StartDate != null) {
                            val (dd, mmYYYY) = getDisplayDate(deal.StartDate)
                            vh.tv_departure_date.text = getEngDate(deal.StartDate)
                        }

                        if (deal.EndDate != null) {
                            val (dd, mmYYYY) = getDisplayDate(deal.EndDate)
                            vh.tv_return_date.text = getEngDate(deal.EndDate)
                        }

                        if (deal.IsActive != null && deal.IsActive.equals("true")) {
                            vh.v_sold_out.visibility = View.GONE
                            vh.iv_sold_out.visibility = View.GONE
                        } else {
                            vh.v_sold_out.visibility = View.VISIBLE
                            vh.iv_sold_out.visibility = View.VISIBLE
                        }

                        vh.itemView.setOnClickListener {
                            listener?.onDealClicked(deal)
                        }
                    }
                } catch (ex: Exception) {
                    LogUtils.e(TAG, ex.message.toString())
                }
            }

            else -> {
            }
        }
    }

    override fun getItemCount(): Int {
        return deals!!.size
    }

    override fun getItemViewType(position: Int): Int {
        val deal = deals?.get(position)

        if (deal?.Category_Id.equals(Constants.CATEGORY.PACKAGE)) {
            return VT_PACKAGE
        } else if (deal?.Category_Id.equals(Constants.CATEGORY.FLIGHT)) {
            return VT_FLIGHT
        } else if (deal?.Category_Id.equals(Constants.CATEGORY.TOUR)) {
            return VT_TOUR
        } else if (deal?.Category_Id.equals(Constants.CATEGORY.PASSOVER)) {
            return VT_PASSOVER
        } else if (deal?.Category_Id.equals(Constants.CATEGORY.SPORTS)) {
            return VT_SPORTS
        } else if (deal?.Category_Id.equals(Constants.CATEGORY.CONCERT)) {
            return VT_CONCERTS
        } else {
            return VT_PACKAGE
        }
    }

    fun getItem(position: Int): Deal? {
        return deals!![position]
    }

    class PackageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tv_price: TextView = itemView.findViewById(R.id.tv_price)
        /*var tv_price: TyperTextView = itemView.findViewById(R.id.tv_price)*/
        var tv_title: TextView = itemView.findViewById(R.id.tv_title)
        var iv_thumbnail: ImageView = itemView.findViewById(R.id.iv_thumbnail)
        var iv_arrow: ImageView = itemView.findViewById(R.id.iv_arrow)
        var tv_destination: TextView = itemView.findViewById(R.id.tv_destination)
        var tv_departure_date: TextView = itemView.findViewById(R.id.tv_departure_date)
        /*var tv_departure_month_year: TextView = itemView.findViewById(R.id.tv_departure_month_year)*/
        var tv_stay: TextView = itemView.findViewById(R.id.tv_stay)
        var tv_return_date: TextView = itemView.findViewById(R.id.tv_return_date)
        /*var tv_return_month_year: TextView = itemView.findViewById(R.id.tv_return_month_year)*/
        var tv_tags: TextView = itemView.findViewById(R.id.tv_tags)
        /*var tv_description: TextView = itemView.findViewById(R.id.tv_description)*/
        var v_sold_out: View = itemView.findViewById(R.id.v_sold_out)
        var iv_sold_out: ImageView = itemView.findViewById(R.id.iv_sold_out)

        init {

            if (Helper.isHebrewLanguage()) {

            }
        }
    }

    class FlightsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_price: TextView = itemView.findViewById(R.id.tv_price)
        var tv_title: TextView = itemView.findViewById(R.id.tv_title)
        var iv_thumbnail: ImageView = itemView.findViewById(R.id.iv_thumbnail)
        var iv_arrow: ImageView = itemView.findViewById(R.id.iv_arrow)
        var tv_destination: TextView = itemView.findViewById(R.id.tv_destination)
        var tv_departure_date: TextView = itemView.findViewById(R.id.tv_departure_date)
        var tv_stay: TextView = itemView.findViewById(R.id.tv_stay)
        var tv_return_date: TextView = itemView.findViewById(R.id.tv_return_date)
        var tv_tags: TextView = itemView.findViewById(R.id.tv_tags)
        var v_sold_out: View = itemView.findViewById(R.id.v_sold_out)
        var iv_sold_out: ImageView = itemView.findViewById(R.id.iv_sold_out)
    }

    class TourViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_price: TextView = itemView.findViewById(R.id.tv_price)
        var tv_title: TextView = itemView.findViewById(R.id.tv_title)
        var iv_thumbnail: ImageView = itemView.findViewById(R.id.iv_thumbnail)
        var iv_arrow: ImageView = itemView.findViewById(R.id.iv_arrow)
        var tv_destination: TextView = itemView.findViewById(R.id.tv_destination)
        var tv_departure_date: TextView = itemView.findViewById(R.id.tv_departure_date)
        var tv_stay: TextView = itemView.findViewById(R.id.tv_stay)
        var tv_return_date: TextView = itemView.findViewById(R.id.tv_return_date)
        var tv_tags: TextView = itemView.findViewById(R.id.tv_tags)
        var v_sold_out: View = itemView.findViewById(R.id.v_sold_out)
        var iv_sold_out: ImageView = itemView.findViewById(R.id.iv_sold_out)
    }

    class PassoverViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_price: TextView = itemView.findViewById(R.id.tv_price)
        var tv_title: TextView = itemView.findViewById(R.id.tv_title)
        var iv_thumbnail: ImageView = itemView.findViewById(R.id.iv_thumbnail)
        var iv_arrow: ImageView = itemView.findViewById(R.id.iv_arrow)
        var tv_destination: TextView = itemView.findViewById(R.id.tv_destination)
        var tv_departure_date: TextView = itemView.findViewById(R.id.tv_departure_date)
        var tv_stay: TextView = itemView.findViewById(R.id.tv_stay)
        var tv_return_date: TextView = itemView.findViewById(R.id.tv_return_date)
        var tv_tags: TextView = itemView.findViewById(R.id.tv_tags)
        var v_sold_out: View = itemView.findViewById(R.id.v_sold_out)
        var iv_sold_out: ImageView = itemView.findViewById(R.id.iv_sold_out)
    }

    class SportsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_price: TextView = itemView.findViewById(R.id.tv_price)
        var tv_title: TextView = itemView.findViewById(R.id.tv_title)
        var iv_thumbnail: ImageView = itemView.findViewById(R.id.iv_thumbnail)
        var iv_arrow: ImageView = itemView.findViewById(R.id.iv_arrow)
        var tv_destination: TextView = itemView.findViewById(R.id.tv_destination)
        var tv_departure_date: TextView = itemView.findViewById(R.id.tv_departure_date)
        var tv_stay: TextView = itemView.findViewById(R.id.tv_stay)
        var tv_return_date: TextView = itemView.findViewById(R.id.tv_return_date)
        var tv_tags: TextView = itemView.findViewById(R.id.tv_tags)
        var v_sold_out: View = itemView.findViewById(R.id.v_sold_out)
        var iv_sold_out: ImageView = itemView.findViewById(R.id.iv_sold_out)
    }

    class ConcertsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tv_price: TextView = itemView.findViewById(R.id.tv_price)
        var tv_title: TextView = itemView.findViewById(R.id.tv_title)
        var iv_thumbnail: ImageView = itemView.findViewById(R.id.iv_thumbnail)
        var iv_arrow: ImageView = itemView.findViewById(R.id.iv_arrow)
        var tv_destination: TextView = itemView.findViewById(R.id.tv_destination)
        var tv_departure_date: TextView = itemView.findViewById(R.id.tv_departure_date)
        var tv_stay: TextView = itemView.findViewById(R.id.tv_stay)
        var tv_return_date: TextView = itemView.findViewById(R.id.tv_return_date)
        var tv_tags: TextView = itemView.findViewById(R.id.tv_tags)
        var v_sold_out: View = itemView.findViewById(R.id.v_sold_out)
        var iv_sold_out: ImageView = itemView.findViewById(R.id.iv_sold_out)
    }

    interface DealClickListener {
        fun onDealClicked(deal: Deal)
    }
}