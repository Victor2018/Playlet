package com.victor.module.home.view.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.AdapterView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Route
import com.hok.lib.common.base.ARouterPath
import com.victor.lib.common.app.App
import com.victor.lib.common.base.BaseActivity
import com.victor.lib.common.interfaces.OnLabelClickListener
import com.victor.lib.common.util.Constant
import com.victor.lib.common.util.KeyBoardUtil
import com.victor.lib.common.util.ToastUtils
import com.victor.lib.common.util.ViewUtils.hide
import com.victor.lib.common.util.ViewUtils.show
import com.victor.lib.coremodel.data.local.entity.SearchKeywordEntity
import com.victor.lib.coremodel.data.local.vm.SearchKeywordVM
import com.victor.lib.coremodel.util.InjectorUtils
import androidx.activity.viewModels
import com.victor.module.home.R
import com.victor.module.home.databinding.ActivitySearchBinding

@Route(path = ARouterPath.SearchAct)
class SearchActivity : BaseActivity<ActivitySearchBinding>(ActivitySearchBinding::inflate),
    OnLabelClickListener, TextWatcher, View.OnClickListener,
    TextView.OnEditorActionListener, AdapterView.OnItemClickListener {

    companion object {
        fun intentStart(activity: AppCompatActivity, searchKey: String?) {
            var intent = Intent(activity, SearchActivity::class.java)
            intent.putExtra(Constant.INTENT_DATA_KEY, searchKey)
            activity.startActivity(intent)
        }
    }

    private val searchKeywordVM: SearchKeywordVM by viewModels {
        var userId = App.get().getUserInfo()?.uid ?: ""
        InjectorUtils.provideSearchKeywordVMFactory(this, userId)
    }

//    private val searchVM: SearchVM by viewModels {
//        InjectorUtils.provideSearchVMFactory(this)
//    }

    var searchKey: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initialize()
        initData(intent)
    }

    fun initialize() {
        subscribeUi()

//        mSearchKeyAdapter = SearchKeyAdapter(this, this)
//        binding.mRvSearchKey.adapter = mSearchKeyAdapter

        binding.mEtSearch.setOnEditorActionListener(this)
        binding.mEtSearch.addTextChangedListener(this)
        binding.mFlHistory.mOnLabelClickListener = this
        binding.mIvCancel.setOnClickListener(this)
        binding.mTvCancel.setOnClickListener(this)
        binding.mIvDetete.setOnClickListener(this)
    }

    fun initData(intent: Intent?) {
        searchKey = intent?.getStringExtra(Constant.INTENT_DATA_KEY)

        binding.mEtSearch.hint = searchKey
        sendSearchTrendRequest("")
    }

    fun subscribeUi() {
        searchKeywordVM.searchKeywordData.observe(this, Observer {
            showSearchKeywordData(it)
        })

        searchKeywordVM.allSearchKeywordData.observe(this, Observer {
            if (it.size > 10) {
                searchKeywordVM.deleteSearchKeyword(it[0])
            }
        })

        /*searchVM.searchTrendData.observe(this, Observer {
            when (it) {
                is HttpResult.Success -> {
                    showSearchTrendData(it.value)
                }
                is HttpResult.Error -> {
                    ToastUtils.show(it.message)
                }
            }
        })*/
    }

    fun sendSearchTrendRequest(name: String?) {
//        searchVM.fetchSearchTrend(tenantId,name)
    }

    fun searchAction(keyword: String?) {
        var query: String? = binding.mEtSearch.text.toString()

        if (!TextUtils.isEmpty(keyword)) {
            query = keyword
        }

        if (TextUtils.isEmpty(query)) {
            ToastUtils.show("请输入搜索关键字")
            return
        }

        addSearchHistory(query)

//        SearchResultActivity.intentStart(this, query,tenantId)

        binding.mRvSearchKey.hide()
//        clearPasswordViewFocus()
    }

    fun addSearchHistory(searchKey: String?) {
        var userId = App.get().getUserInfo()?.uid ?: ""

        var item = SearchKeywordEntity(searchKey ?: "", userId)
        searchKeywordVM.insertSearchKeyword(item)
    }

    fun showSearchKeywordData(datas: List<SearchKeywordEntity>) {
        var count = datas.size
        if (count > 0) {//平台才有搜索历史
            binding.mFlHistory.show()
        } else {
            binding.mFlHistory.hide()
        }
        var inflater = LayoutInflater.from(this)
        var views = ArrayList<View>()

        binding.mFlHistory.removeAllViews()
        datas.forEach {
            val mTvKey = inflater?.inflate(R.layout.fl_search_history_cell, null) as TextView
            mTvKey.text = it.keyword
            views.add(mTvKey)
        }
        binding.mFlHistory.addLabelView(views)
    }

    /*fun showSearchTrendData(data: BaseReq<List<SearchTendInfo>>) {
        var query = mEtSearch.text.toString()
        if (TextUtils.isEmpty(query)) {
            mHotSearchAdapter?.showData(data.data)
            mRvSearchKey.hide()
        } else {
            mSearchKeyAdapter?.searchKey = query
            mSearchKeyAdapter?.showData(data.data)
            mRvSearchKey.show()
        }
    }*/

    fun requestPasswordViewFocus () {
        //设置可获得焦点
        binding.mEtSearch.isFocusable = true
        binding.mEtSearch.isFocusableInTouchMode = true
        //请求获得焦点
        binding.mEtSearch.requestFocus()

        binding.mEtSearch.postDelayed(Runnable {
            KeyBoardUtil.showKeyBoard(this,binding.mEtSearch)
        },300)
    }

    fun clearPasswordViewFocus () {
        binding.mEtSearch.clearFocus()
        binding.mEtSearch.isFocusable = false
        binding.mEtSearch.isFocusableInTouchMode = false

        KeyBoardUtil.hideKeyBoard(this,binding.mEtSearch)
    }

    override fun OnLabelClick(text: String?, position: Int) {
        searchAction(text)
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun afterTextChanged(e: Editable?) {
        var query = e?.toString()
        if (TextUtils.isEmpty(query)) {
            binding.mIvCancel.hide()
        } else {
            binding.mIvCancel.show()
        }
        sendSearchTrendRequest(query)
    }

    override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            searchAction("")
            return true
        }
        return false
    }

    override fun onItemClick(p0: AdapterView<*>?, v: View?, position: Int, id: Long) {
        when (v?.id) {
            /*R.id.mClSearchKey -> {
                var searchKey = mSearchKeyAdapter?.getItem(position)?.searchValue
                addSearchHistory(searchKey)
                SearchResultActivity.intentStart(this, searchKey,tenantId)
                binding.mRvSearchKey.hide()
                clearPasswordViewFocus()
            }*/
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mIvCancel -> {
                binding.mEtSearch.setText("")
            }
            R.id.mTvCancel -> {
                clearPasswordViewFocus()
                finish()
            }
            R.id.mIvDetete -> {
                searchKeywordVM.clearAllSearchKeyword()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        requestPasswordViewFocus()
    }

    override fun onPause() {
        super.onPause()
        clearPasswordViewFocus()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        initData(intent)
    }
}