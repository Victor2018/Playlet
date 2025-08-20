package com.victor.module.theater.view.fragment

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
import com.victor.lib.coremodel.data.remote.vm.TheaterVM
import com.victor.lib.coremodel.data.remote.vm.factory.TheaterVMFactory
import com.victor.lib.coremodel.util.InjectorUtils
import com.victor.module.theater.data.HotType
import com.victor.module.theater.databinding.FragmentTheaterHotContentBinding
import com.victor.module.theater.view.adapter.TheaterHotAdapter
import org.victor.http.lib.data.HttpResult

class TheaterHotContentFragment : BaseFragment<FragmentTheaterHotContentBinding>(FragmentTheaterHotContentBinding::inflate),
    OnItemClickListener, OnRefreshListener {

    companion object {
        fun newInstance(hotType: Int): TheaterHotContentFragment {
            return newInstance(0,hotType)
        }
        fun newInstance(id: Int,hotType: Int): TheaterHotContentFragment {
            val fragment = TheaterHotContentFragment()
            val bundle = Bundle()
            bundle.putInt(ID_KEY, id)
            bundle.putInt(Constant.INTENT_DATA_KEY, hotType)
            fragment.setArguments(bundle)
            return fragment
        }
    }

    private lateinit var mTheaterVM: TheaterVM
    private var mTheaterHotAdapter: TheaterHotAdapter? = null
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
        mTheaterVM = InjectorUtils.provideFragmentVM(this, TheaterVMFactory(this), TheaterVM::class.java)
        mTheaterHotAdapter = TheaterHotAdapter(requireContext(),this)
        binding.mRvHot.adapter = mTheaterHotAdapter

        binding.mSrlRefresh.setOnRefreshListener(this)

        subscribeUi()
        subscribeEvent()
    }

    private fun initData() {
        hotType = arguments?.getInt(Constant.INTENT_DATA_KEY, HotType.RECOMMEND) ?: HotType.RECOMMEND
        mTheaterHotAdapter?.hotType = hotType
        sendHotRequest()
    }

    private fun subscribeUi() {
        mTheaterVM.hotRecommendData.observe(viewLifecycleOwner, Observer {
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

        mTheaterVM.hotPlayData.observe(viewLifecycleOwner, Observer {
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

        mTheaterVM.hotNewData.observe(viewLifecycleOwner, Observer {
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

        mTheaterVM.hotSearchData.observe(viewLifecycleOwner, Observer {
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
                mTheaterVM.fetchHotRecommend()
            }
            HotType.PLAY -> {
                mTheaterVM.fetchHotPlay()
            }
            HotType.NEW -> {
                mTheaterVM.fetchHotNew()
            }
            HotType.SEARCH -> {
                mTheaterVM.fetchHotSearch()
            }
        }
    }

    fun showHotData(data: BaseRes<HomeItemInfo>) {
        //过滤新剧榜的textCard
        if (hotType == HotType.NEW) {
            val hotList = data.itemList?.filter { it.type != "textCard" }
            mTheaterHotAdapter?.showData(hotList)
        } else {
            mTheaterHotAdapter?.showData(data.itemList)
        }
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
    }

    override fun onRefresh() {
        sendHotRequest()
    }

}