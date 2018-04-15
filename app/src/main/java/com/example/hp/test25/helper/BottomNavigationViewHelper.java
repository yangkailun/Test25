package com.example.hp.test25.helper;

import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.design.internal.BottomNavigationItemView;

import java.lang.reflect.Field;

/**
 * Created by HP on 2018-04-12.
 */

public class BottomNavigationViewHelper {
    public static void disableShiftMode(BottomNavigationView navigationView){
        BottomNavigationMenuView menuView = (BottomNavigationMenuView)navigationView.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView,false);
            shiftingMode.setAccessible(false);
            for(int i = 0; i < menuView.getChildCount(); i++){
                BottomNavigationItemView itemView = (BottomNavigationItemView)menuView.getChildAt(i);
                itemView.setShiftingMode(false);
                itemView.setChecked(itemView.getItemData().isChecked());
            }
        }catch (NoSuchFieldException|IllegalAccessException e){
            e.printStackTrace();
        }
    }
}
