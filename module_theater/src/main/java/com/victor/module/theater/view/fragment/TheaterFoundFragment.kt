package com.victor.module.theater.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import androidx.lifecycle.Observer
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.victor.lib.common.base.BaseFragment
import com.victor.lib.common.util.ToastUtils
import com.victor.lib.coremodel.data.remote.entity.bean.HomeItemInfo
import com.victor.lib.coremodel.data.remote.entity.response.BaseRes
import com.victor.lib.coremodel.data.remote.vm.TheaterVM
import com.victor.lib.coremodel.data.remote.vm.factory.TheaterVMFactory
import com.victor.lib.coremodel.util.InjectorUtils
import com.victor.module.theater.databinding.FragmentTheaterFoundBinding
import com.victor.module.theater.view.adapter.TheaterFoundAdapter
import org.victor.http.lib.data.HttpResult

class TheaterFoundFragment : BaseFragment<FragmentTheaterFoundBinding>(FragmentTheaterFoundBinding::inflate),
    OnItemClickListener, OnRefreshListener {

    companion object {
        fun newInstance(): TheaterFoundFragment {
            return newInstance(0)
        }
        fun newInstance(id: Int): TheaterFoundFragment {
            val fragment = TheaterFoundFragment()
            val bundle = Bundle()
            bundle.putInt(ID_KEY, id)
            fragment.setArguments(bundle)
            return fragment
        }
    }

    private lateinit var mTheaterVM: TheaterVM
    private var mTheaterFoundAdapter: TheaterFoundAdapter? = null

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
        mTheaterVM = InjectorUtils.provideFragmentVM(this, TheaterVMFactory(this), TheaterVM::class.java)
        mTheaterFoundAdapter = TheaterFoundAdapter(requireContext(),this)
        mTheaterFoundAdapter?.setHeaderVisible(true)
        binding.mRvHot.adapter = mTheaterFoundAdapter

        binding.mSrlRefresh.setOnRefreshListener(this)

        subscribeUi()
        subscribeEvent()
    }

    private fun initData() {
        sendFoundRequest()
    }

    private fun subscribeUi() {
        mTheaterVM.foundData.observe(viewLifecycleOwner, Observer {
            binding.mSrlRefresh.isRefreshing = false
            when(it) {
                is HttpResult.Success -> {
                    showFoundData(it.value)
                }
                is HttpResult.Error -> {
                    ToastUtils.show(it.message)
                }
            }
        })
    }


    private fun subscribeEvent() {
    }

    private fun sendFoundRequest() {
        mTheaterVM.fetchFound()
    }
    
    fun showFoundData(data: BaseRes<HomeItemInfo>) {
        val bannerList = data.itemList?.filter { it.type == "squareCardCollection" }
        mTheaterFoundAdapter?.mHomeItemInfo = bannerList?.firstOrNull()
        val rankingList = data.itemList?.filter { it.type != "textCard" && it.type != "squareCardCollection" }
        mTheaterFoundAdapter?.showData(rankingList)
        mTheaterFoundAdapter?.mHeaderCount
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
    }

    override fun onRefresh() {
        initData()
    }
}