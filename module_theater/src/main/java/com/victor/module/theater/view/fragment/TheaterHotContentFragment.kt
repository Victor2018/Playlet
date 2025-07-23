package com.victor.module.theater.view.fragment

import android.os.Bundle
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
import com.victor.module.theater.databinding.FragmentTheaterHotContentBinding
import com.victor.module.theater.view.adapter.TheaterHotAdapter
import org.victor.http.lib.data.HttpResult

class TheaterHotContentFragment : BaseFragment<FragmentTheaterHotContentBinding>(FragmentTheaterHotContentBinding::inflate),
    OnItemClickListener, OnRefreshListener {

    companion object {
        fun newInstance(): TheaterHotContentFragment {
            return newInstance(0)
        }
        fun newInstance(id: Int): TheaterHotContentFragment {
            val fragment = TheaterHotContentFragment()
            val bundle = Bundle()
            bundle.putInt(ID_KEY, id)
            fragment.setArguments(bundle)
            return fragment
        }
    }

    private lateinit var mTheaterVM: TheaterVM
    private var mTheaterHotAdapter: TheaterHotAdapter? = null

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
        mTheaterHotAdapter = TheaterHotAdapter(requireContext(),this)
        binding.mRvHot.adapter = mTheaterHotAdapter

        binding.mSrlRefresh.setOnRefreshListener(this)

        subscribeUi()
        subscribeEvent()
    }

    private fun initData() {
        sendRankingRequest()
    }

    private fun subscribeUi() {
        mTheaterVM.rankingData.observe(viewLifecycleOwner, Observer {
            binding.mSrlRefresh.isRefreshing = false
            when(it) {
                is HttpResult.Success -> {
                    showRankingData(it.value)
                }
                is HttpResult.Error -> {
                    ToastUtils.show(it.message)
                }
            }
        })
    }

    private fun subscribeEvent() {
    }

    private fun sendRankingRequest() {
        mTheaterVM.fetchRanking()
    }
    fun showRankingData(data: BaseRes<HomeItemInfo>) {
        val rankingList = data.itemList?.filter { it.type != "textCard" }
        mTheaterHotAdapter?.showData(rankingList)
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
    }

    override fun onRefresh() {
        sendRankingRequest()
    }

}