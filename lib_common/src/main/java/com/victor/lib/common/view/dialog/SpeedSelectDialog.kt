package com.victor.lib.common.view.dialog

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import com.victor.lib.common.R
import com.victor.lib.common.databinding.DlgSpeedSelectBinding
import com.victor.lib.common.interfaces.OnPlaySpeedSelectListener
import com.victor.lib.common.util.ResUtils
import com.victor.lib.common.util.ScreenUtils
import com.victor.lib.common.view.adapter.PlaySpeedAdapter

class SpeedSelectDialog (context: Context) : AbsBottomSheetDialog<DlgSpeedSelectBinding>
    (context,DlgSpeedSelectBinding::inflate),View.OnClickListener,
    AdapterView.OnItemClickListener{

    lateinit var mVerticalPlaySpeedAdapter: PlaySpeedAdapter
    var mOnPlaySpeedSelectListener: OnPlaySpeedSelectListener? = null

    override fun handleLayoutParams(wlp: WindowManager.LayoutParams?) {
        wlp?.width = ScreenUtils.getWidth(context)
//        wlp?.height = ScreenUtils.getHeight(context) * 8 / 10
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initData()
    }

    private fun initView() {
        mVerticalPlaySpeedAdapter = PlaySpeedAdapter(context,this)
        binding.mRvSpeed.adapter = mVerticalPlaySpeedAdapter

        binding.mIvClose.setOnClickListener(this)
    }

    private fun initData() {
        val playSpeeds = ResUtils.getStringArrayRes(R.array.play_speeds)
        mVerticalPlaySpeedAdapter?.showData(playSpeeds?.asList())
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mIvClose -> {
                dismiss()
            }
        }
    }

    override fun onItemClick(p0: AdapterView<*>?, v: View?, position: Int, id: Long) {
        val speedStr = mVerticalPlaySpeedAdapter?.getItem(position)
        mVerticalPlaySpeedAdapter?.playSpeed = speedStr
        mVerticalPlaySpeedAdapter?.notifyDataSetChanged()

        val speed = speedStr?.replace("X","")?.toFloat() ?: 0f
        mOnPlaySpeedSelectListener?.OnPlaySpeedSelect(speed)

        dismiss()
    }
}