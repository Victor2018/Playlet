package com.victor.module.welfare.view.fragment

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.appbar.AppBarLayout
import com.hok.lib.common.base.ARouterPath
import com.victor.lib.common.base.BaseFragment
import com.victor.lib.common.view.widget.LMRecyclerView
import com.victor.module.welfare.R
import com.victor.module.welfare.databinding.FragmentWelfareBinding
import com.victor.module.welfare.view.adapter.EveryDayAdapter
import com.victor.module.welfare.view.adapter.NewComerAdapter

@Route(path = ARouterPath.WelfareFgt)
class WelfareFragment : BaseFragment<FragmentWelfareBinding>(FragmentWelfareBinding::inflate),
    OnItemClickListener, AppBarLayout.OnOffsetChangedListener {

    companion object {
        fun newInstance(): WelfareFragment {
            return newInstance(0)
        }
        fun newInstance(id: Int): WelfareFragment {
            val fragment = WelfareFragment()
            val bundle = Bundle()
            bundle.putInt(ID_KEY, id)
            fragment.setArguments(bundle)
            return fragment
        }
    }

    private lateinit var mEveryDayAdapter: EveryDayAdapter
    private lateinit var mNewComerAdapter: NewComerAdapter

    override fun handleBackEvent(): Boolean {
        return false
    }

    override fun freshFragData() {
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }

    private fun initView() {
        mEveryDayAdapter = EveryDayAdapter(context,this)
        mNewComerAdapter = NewComerAdapter(context,this)

        binding.root.findViewById<LMRecyclerView>(R.id.mRvEveryDay).adapter = mEveryDayAdapter
        binding.root.findViewById<LMRecyclerView>(R.id.mRvMeetingGift).adapter = mNewComerAdapter

        binding.appbar.addOnOffsetChangedListener(this)
    }

    private fun initData() {
        mEveryDayAdapter.clear()
        mNewComerAdapter.clear()
        for (i in 6 .. 12) {
            mEveryDayAdapter.add("${i * 100}")
            mNewComerAdapter.add("${i * 100}")
        }
        mEveryDayAdapter.notifyDataSetChanged()
        mNewComerAdapter.notifyDataSetChanged()
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int) {
        binding.mSrlRefresh.isEnabled = verticalOffset >= 0
    }
}