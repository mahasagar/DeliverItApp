package com.mattricks.deliverit.utilities;

import android.content.Context;
import android.content.SharedPreferences;

import com.mattricks.deliverit.common.Constants;
import com.mattricks.deliverit.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mahasagar on 20/1/17.
 */
public class CommonService {

    public CommonService() {
        super();
    }


    public boolean isEmpty(List<Product> list) {
        if(list.size() > 0){
            return false;
        }else{
            return true;
        }
    }

}
