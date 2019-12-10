package androidmvvm.ui.deals_dashboard.validation_alert_dialog


import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidmvvm.R
import androidmvvm.ui.base.BaseDialogFragment
import androidmvvm.utils.Constants
import androidmvvm.utils.LogUtils
import kotlinx.android.synthetic.main.fragment_validation_alert_dialog.*


class ValidationAlertDialogFragment : BaseDialogFragment(), View.OnClickListener {

    private val TAG = "VADFragment"

    companion object {

        @JvmStatic
        fun newInstance() =
                ValidationAlertDialogFragment().apply {
                    arguments = Bundle().apply {

                    }
                }
    }

    var message: String? = null

    override fun getFragmentTAG(): FragmentTAG {
        return FragmentTAG.VALIDATION_ALERT_DIALOG_FRAG
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_ok -> dismiss()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            message = arguments?.get(Constants.KEY.ALERT_MESSAGE) as String?
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (root == null) {
            getDialog().getWindow().setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            root = inflater.inflate(R.layout.fragment_validation_alert_dialog, container, false) as ViewGroup
            isCancelable = true
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (!isFragmentLoaded) {
            initInstances()
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initInstances() {
        try {
            btn_ok.setOnClickListener(this)

            if (!message.isNullOrEmpty()) {
                tv_message.visibility = View.GONE
                tv_title.text = message
            } else {

            }
        } catch (ex: Exception) {
            LogUtils.e(TAG, ex.message.toString())
        }
    }
}
