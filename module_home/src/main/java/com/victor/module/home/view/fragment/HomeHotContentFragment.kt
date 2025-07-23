package com.victor.module.home.view.fragment

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import androidx.lifecycle.Observer
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.victor.lib.common.base.BaseFragment
import com.victor.lib.common.util.Constant
import com.victor.lib.common.util.ToastUtils
import com.victor.lib.coremodel.data.remote.entity.bean.HomeItemInfo
import com.victor.lib.coremodel.data.remote.entity.response.BaseRes
import com.victor.lib.coremodel.data.remote.vm.HomeVM
import com.victor.lib.coremodel.data.remote.vm.factory.HomeVMFactory
import com.victor.lib.coremodel.util.InjectorUtils
import com.victor.module.home.databinding.FragmentHomeHotContentBinding
import com.victor.module.home.view.adapter.HotAdapter
import com.victor.module.home.view.data.HotType
import org.victor.http.lib.data.HttpResult

class HomeHotContentFragment : BaseFragment<FragmentHomeHotContentBinding>(FragmentHomeHotContentBinding::inflate),
    OnItemClickListener,OnRefreshListener {

    companion object {
        fun newInstance(hotType: Int): HomeHotContentFragment {
            return newInstance(0,hotType)
        }
        fun newInstance(id: Int,hotType: Int): HomeHotContentFragment {
            val fragment = HomeHotContentFragment()
            val bundle = Bundle()
            bundle.putInt(ID_KEY, id)
            bundle.putInt(Constant.INTENT_DATA_KEY, hotType)
            fragment.setArguments(bundle)
            return fragment
        }
    }

    private lateinit var mHomeVM: HomeVM
    private var mHotAdapter: HotAdapter? = null
    private var hotType = HotType.RECOMMEND

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
        mHotAdapter = HotAdapter(requireContext(),this)
        binding.mRvHot.adapter = mHotAdapter

        binding.mSrlRefresh.setOnRefreshListener(this)

        subscribeUi()
        subscribeEvent()
    }

    private fun initData() {
        hotType = arguments?.getInt(Constant.INTENT_DATA_KEY,HotType.RECOMMEND) ?: HotType.RECOMMEND
        mHotAdapter?.hotType = hotType
        sendHotRequest()
    }

    private fun subscribeUi() {
        mHomeVM.hotRecommendData.observe(viewLifecycleOwner, Observer {
            binding.mSrlRefresh.isRefreshing = false
            when(it) {
                is HttpResult.Success -> {
                    showHotData(it.value)
                }
                is HttpResult.Error -> {
                    ToastUtils.show(it.message)
                }
            }
        })

        mHomeVM.hotPlayData.observe(viewLifecycleOwner, Observer {
            binding.mSrlRefresh.isRefreshing = false
            when(it) {
                is HttpResult.Success -> {
                    showHotData(it.value)
                }
                is HttpResult.Error -> {
                    ToastUtils.show(it.message)
                }
            }
        })

        mHomeVM.hotNewData.observe(viewLifecycleOwner, Observer {
            binding.mSrlRefresh.isRefreshing = false
            when(it) {
                is HttpResult.Success -> {
                    showHotData(it.value)
                }
                is HttpResult.Error -> {
                    ToastUtils.show(it.message)
                }
            }
        })

        mHomeVM.hotSearchData.observe(viewLifecycleOwner, Observer {
            binding.mSrlRefresh.isRefreshing = false
            when(it) {
                is HttpResult.Success -> {
                    showHotData(it.value)
                }
                is HttpResult.Error -> {
                    ToastUtils.show(it.message)
                }
            }
        })
    }

    private fun subscribeEvent() {
    }

    fun sendHotRequest() {
        when(hotType) {
            HotType.RECOMMEND -> {
                mHomeVM.fetchHotRecommend()
            }
            HotType.PLAY -> {
                mHomeVM.fetchHotPlay()
            }
            HotType.NEW -> {
                mHomeVM.fetchHotNew()
            }
            HotType.SEARCH -> {
                mHomeVM.fetchHotSearch()
            }
        }
    }

    fun showHotData(data: BaseRes<HomeItemInfo>) {
        //过滤新剧榜的textCard
        if (hotType == HotType.NEW) {
            val hotList = data.itemList?.filter { it.type != "textCard" }
            mHotAdapter?.showData(hotList)
        } else {
            mHotAdapter?.showData(data.itemList)
        }
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
    }

    override fun onRefresh() {
        sendHotRequest()
    }
}