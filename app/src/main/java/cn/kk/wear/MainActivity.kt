package cn.kk.wear

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.wear.widget.WearableLinearLayoutManager
import cn.kk.wear.databinding.ActivityMainBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

class MainActivity : Activity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val MAX_ICON_PROGRESS = 0.65f


        binding.wrv.apply {
            isEdgeItemsCenteringEnabled = true
            bezelFraction = 0.5f
            scrollDegreesPerScreen = 90f
            layoutManager = WearableLinearLayoutManager(context).apply {
                layoutCallback = object : WearableLinearLayoutManager.LayoutCallback() {
                    private var progressToCenter: Float = 0f

                    override fun onLayoutFinished(child: View, parent: RecyclerView) {

                        child.apply {
                            // Figure out % progress from top to bottom
                            val centerOffset = height.toFloat() / 2.0f / parent.height.toFloat()
                            val yRelativeToCenterOffset = y / parent.height + centerOffset

                            // Normalize for center
                            progressToCenter = Math.abs(0.5f - yRelativeToCenterOffset)
                            // Adjust to the maximum scale
                            progressToCenter = Math.min(progressToCenter, MAX_ICON_PROGRESS)

                            scaleX = 1 - progressToCenter
                            scaleY = 1 - progressToCenter
                        }

                    }

                }
            }

            val dataList = mutableListOf<String>()
            for(i in 0 until 20) {
                dataList.add(i.toString())
             }

            adapter = object: BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_normal, dataList){
                override fun convert(holder: BaseViewHolder, item: String) {
                    holder.setText(R.id.tv_title, item)
                }

            }
        }
    }
}