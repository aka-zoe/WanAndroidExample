package com.zoe.wan.android.example.common.adapter

import android.view.ViewGroup
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.listener.OnBannerListener
import com.youth.banner.listener.OnPageChangeListener
import com.zoe.wan.android.example.R
import com.zoe.wan.android.example.databinding.ItemHomeBannerBinding
import com.zoe.wan.android.example.databinding.ItemHomeListBinding
import com.zoe.wan.android.example.repository.data.HomeBannerData
import com.zoe.wan.android.example.repository.data.HomeBannerDataItem
import com.zoe.wan.android.example.repository.data.HomeListItemData
import com.zoe.wan.base.adapter.BaseAdapter
import com.zoe.wan.base.adapter.BaseViewHolder

class HomeListAdapter : BaseAdapter<HomeListItemData?, BaseViewHolder<*>>() {

    private var bannerData: HomeBannerData? = null
    private var itemClickListener: HomeItemClickListener? = null

    class HomeListViewHolder(binding: ItemHomeListBinding) :
        BaseViewHolder<ItemHomeListBinding>(binding)

    class HomeBannerViewHolder(binding: ItemHomeBannerBinding) :
        BaseViewHolder<ItemHomeBannerBinding>(binding)


    interface HomeItemClickListener {
        fun itemCollect(id: String, position: Int, collect: Boolean)

        fun bannerClick(title: String?, link: String?)
    }

    companion object {
        //banner类型
        private val BannerItemType = 0

        //普通item类型
        private val NormalItemType = 1
    }

    /**
     * 设置banner数据
     */
    fun setBannerData(data: HomeBannerData?) {
        if (!data.isNullOrEmpty()) {
            bannerData = data
            notifyDataSetChanged()
        }
    }

    fun setCollect(collect: Boolean, position: Int) {
        if (getDataList()?.isNotEmpty() == true) {
            getDataList()?.get(position)?.collect = collect
            notifyDataSetChanged()
        }
    }

    fun registerHomeItemListener(listener: HomeItemClickListener?) {
        this.itemClickListener = listener
    }


    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return BannerItemType
        } else {
            return NormalItemType
        }
    }

    override fun getViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        if (viewType == BannerItemType) {
            return HomeBannerViewHolder(getBinding(parent, R.layout.item_home_banner))
        } else {
            return HomeListViewHolder(getBinding(parent, R.layout.item_home_list))
        }
    }

    override fun bindHolder(holder: BaseViewHolder<*>, position: Int) {
        if (holder is HomeBannerViewHolder) {
            holder.binding.itemHomeBanner.setAdapter(HomeBannerAdapter(bannerData))
                .setIndicator(CircleIndicator(holder.binding.itemHomeBanner.context))
                .addOnPageChangeListener(object : OnPageChangeListener {
                    override fun onPageScrolled(
                        position: Int,
                        positionOffset: Float,
                        positionOffsetPixels: Int
                    ) {

                    }

                    override fun onPageSelected(position: Int) {
                        //当banner切换时，标题也同步切换显示
                        holder.binding.itemHomeBannerTitle.text =
                            bannerData?.get(position)?.title
                    }

                    override fun onPageScrollStateChanged(state: Int) {

                    }

                }).setOnBannerListener(object : OnBannerListener<HomeBannerDataItem?> {
                    override fun OnBannerClick(data: HomeBannerDataItem?, position: Int) {
//                        Toast.makeText(holder.bannerBinding.itemHomeBannerTitle.context,
//                            "Banner点击",Toast.LENGTH_SHORT).show()
                        //banner点击事件
                        itemClickListener?.bannerClick(data?.title, data?.url)
                        ToastUtils.showShort("Banner点击")
                    }

                })

        } else if (holder is HomeListViewHolder) {
            val item = getDataList()?.get(position)
            holder.binding.item = item
            if (item?.collect == true) {
                holder.binding.itemHomeCollect.setBackgroundResource(R.drawable.img_collect)
            } else {
                holder.binding.itemHomeCollect.setBackgroundResource(R.drawable.img_collect_grey)
            }

            //收藏按钮事件
            holder.binding.itemHomeCollect.setOnClickListener {
                itemClickListener?.itemCollect("${item?.id}", position, item?.collect ?: false)
            }

        }
    }

    class HomeBannerAdapter(bannerData: HomeBannerData?) :
        BannerImageAdapter<HomeBannerDataItem>(bannerData) {
        override fun onBindView(
            holder: BannerImageHolder?,
            data: HomeBannerDataItem?,
            position: Int,
            size: Int
        ) {
            holder?.imageView?.let { img ->
                Glide.with(img).load(data?.imagePath).into(holder.imageView)
            }

        }

    }

}
