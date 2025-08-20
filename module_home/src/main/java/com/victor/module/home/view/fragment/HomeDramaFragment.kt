package com.victor.module.home.view.fragment

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import androidx.lifecycle.Observer
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.victor.lib.common.base.BaseFragment
import com.victor.lib.common.util.ToastUtils
import com.victor.lib.coremodel.data.remote.entity.bean.FollowItem
import com.victor.lib.coremodel.data.remote.entity.response.BaseRes
import com.victor.lib.coremodel.data.remote.vm.HomeVM
import com.victor.lib.coremodel.data.remote.vm.factory.HomeVMFactory
import com.victor.lib.coremodel.util.InjectorUtils
import com.victor.module.home.databinding.FragmentHomeDramaBinding
import com.victor.module.home.view.adapter.DramaAdapter
import org.victor.http.lib.data.HttpResult

class HomeDramaFragment : BaseFragment<FragmentHomeDramaBinding>(FragmentHomeDramaBinding::inflate),
    OnItemClickListener, OnRefreshListener {

    companion object {
        fun newInstance(): HomeDramaFragment {
            return newInstance(0)
        }
        fun newInstance(id: Int): HomeDramaFragment {
            val fragment = HomeDramaFragment()
            val bundle = Bundle()
            bundle.putInt(ID_KEY, id)
            fragment.setArguments(bundle)
            return fragment
        }
    }

    private lateinit var mHomeVM: HomeVM

    private var mDramaAdapter: DramaAdapter? = null

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
        mHomeVM = InjectorUtils.provideFragmentVM(this, HomeVMFactory(this), HomeVM::class.java)

        mDramaAdapter = DramaAdapter(requireContext(),this)
        binding.mRvDrama.adapter = mDramaAdapter
        subscribeUi()
        subscribeEvent()
    }

    private fun initData() {
        sendDramaListRequest()
    }

    private fun subscribeUi() {
        mHomeVM.dramaListData.observe(viewLifecycleOwner, Observer {
            binding.mSrlRefresh.isRefreshing = false
            when(it) {
                is HttpResult.Success -> {
                    showDramaListData(it.value)
                }
                is HttpResult.Error -> {
                    ToastUtils.show(it.message)
                }
            }
        })
    }

    private fun subscribeEvent() {
    }

    fun sendDramaListRequest() {
        mHomeVM.fetchDramaList()
    }

    fun showDramaListData(data: BaseRes<FollowItem>) {
        mDramaAdapter?.showData(data.itemList)
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
    }

    override fun onRefresh() {
        sendDramaListRequest()
    }


}