package hr.foi.rampu.fridgium

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import hr.foi.rampu.fridgium.adapters.MainPagerAdapter
import hr.foi.rampu.fridgium.fragments.FridgeFragment
import hr.foi.rampu.fridgium.fragments.RecipesFragment
import hr.foi.rampu.fridgium.fragments.ShoppingListFragment

class MainActivity : AppCompatActivity() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tabLayout = findViewById(R.id.tabs)
        viewPager2 = findViewById(R.id.viewpager)

        val mainPagerAdapter = MainPagerAdapter(supportFragmentManager, lifecycle)

        mainPagerAdapter.addFragment(
            MainPagerAdapter.FragmentItem(
                R.string.fridge,
                R.drawable.fridge,
                FridgeFragment::class
            )
        )
        mainPagerAdapter.addFragment(
            MainPagerAdapter.FragmentItem(
                R.string.recipes,
                R.drawable.recipes,
                RecipesFragment::class
            )
        )
        mainPagerAdapter.addFragment(
            MainPagerAdapter.FragmentItem(
                R.string.shopping_list,
                R.drawable.shopping_list,
                ShoppingListFragment::class
            )
        )

        viewPager2.adapter = mainPagerAdapter

        TabLayoutMediator(tabLayout, viewPager2,true,false,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                tab.setText(mainPagerAdapter.fragmentItems[position].titleRes)
                tab.setIcon(mainPagerAdapter.fragmentItems[position].iconRes)
            }).attach()
    }
}