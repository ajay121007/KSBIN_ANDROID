package com.example.ks.activities.policydetail

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.transition.Slide
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.ks.R
import com.example.ks.adapters.CommonListAdapter
import com.example.ks.adapters.PolicyDropDownAdapter
import com.example.ks.common.BaseActivity
import com.example.ks.constants.UserConstants
import com.example.ks.databinding.ActivityChangePolicyDetials1Binding
import com.example.ks.models.DashBoardResponse
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class ChangePolicyDetials1Activity : BaseActivity() {
    lateinit var binding: ActivityChangePolicyDetials1Binding
    val viewModel: ChangePolicyDetailViewModel by viewModel { parametersOf(this) }
    lateinit var commonListAdapter: CommonListAdapter
    lateinit var changeTypeArrayList:ArrayList<String>
    var   popupWindow: PopupWindow?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding= DataBindingUtil.setContentView(this, R.layout.activity_change_policy_detials1)
        binding.apply {
            model=viewModel
            setSupportActionBar(binding.toolbar
            )
            executePendingBindings()
        }
        initView()
    }

    private fun initView() {
        clickEvent()
        changeTypeArrayList= ArrayList()
        changeTypeArrayList.add("Add/Remove Driver")
        changeTypeArrayList.add("Change Vehicle")
        changeTypeArrayList.add("Change Address")

        if (!UserConstants.policyArrayList.isNullOrEmpty()){
//            binding.txtPolicy.text = UserConstants.policyArrayList[0].policyNumber
        }

//        val staticSpinner = findViewById<View>(R.id.static_spinner) as Spinner

        // Create an ArrayAdapter using the string array and a default spinner

        // Create an ArrayAdapter using the string array and a default spinner
        val staticAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,changeTypeArrayList)
        val subjectArray=ArrayList<String>()

        intent.getStringArrayListExtra("policy")?.toMutableList()?.let { subjectArray.addAll(it) }
        val staticAdapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item,subjectArray)


        binding.spinner1.apply {
            adapter = staticAdapter
            onItemSelectedListener=object:AdapterView.OnItemSelectedListener{
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    viewModel.subject.postValue(p0?.getItemAtPosition(p2) as String?)
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }

            }
        }
        binding.spinner2.apply {
            adapter = staticAdapter2
            onItemSelectedListener=object:AdapterView.OnItemSelectedListener{
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    viewModel.policy.postValue(p0?.getItemAtPosition(p2) as String?)
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }

            }
        }
    }


    private fun clickEvent() {

        binding.txtVehcle.setOnClickListener {

            locationPopupWindow(binding.txtVehcle)
        }

//        binding.txtPolicy.setOnClickListener {
//            showPolicy(binding.txtPolicy)
//        }

//        binding.submitBtn.setOnClickListener {
//            if (binding.txtVehcle.text.isNotEmpty() && binding.txtPolicy.text.isNotEmpty()
//                && binding.editPolicyContent.text.isNotEmpty()){
//                viewModel.updatePolicy(
//                    binding.txtPolicy.text.toString(), binding.editPolicyContent.text.toString(),
//                    binding.txtVehcle.text.toString()
//                )
//            }
//            else{
//                Toast.makeText(this, "Please fill all details", Toast.LENGTH_SHORT).show()
//            }
//        }
    }




    fun locationPopupWindow(view1: View?) { // inflate the layout of the popup window
        val inflater: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        // Inflate a custom view using layout inflater
        val view = inflater.inflate(R.layout.popup, null)
//        val width = binding.txtPolicy.measuredWidth
        // Initialize a new instance of popup window
//        popupWindow = PopupWindow(
//            view, // Custom view to show in popup window
//            width, // Width of popup window
//            LinearLayout.LayoutParams.WRAP_CONTENT // Window height
//        )

        // Set an elevation for the popup window
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow?.elevation = 10.0F
        }


        // If API level 23 or higher then execute the code
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            // Create a new slide animation for popup window enter transition
            val slideIn = Slide()
            slideIn.slideEdge = Gravity.TOP
            popupWindow?.enterTransition = slideIn

            // Slide animation for popup window exit transition
            val slideOut = Slide()
            slideOut.slideEdge = Gravity.TOP
            popupWindow?.exitTransition = slideOut
            popupWindow?.isOutsideTouchable=true

        }


        // Get the widgets reference from custom view
        val commonListView = view.findViewById<RecyclerView>(R.id.commonList)

        commonListAdapter= CommonListAdapter(changeTypeArrayList,
            object : CommonListAdapter.CommonListAdapterListener {
                override fun onItemClickListener(itemData: String) {
                    popupWindow?.dismiss()
                    binding.txtVehcle.text = itemData
                }
            })
        commonListView.adapter= commonListAdapter
        // Set a dismiss listener for popup window
        popupWindow?.setOnDismissListener {
            //  Toast.makeText(applicationContext,"Popup closed",Toast.LENGTH_SHORT).show()
        }
        popupWindow?.showAsDropDown(binding.txtVehcle, 15, 15)

    }
}