package com.mattricks.deliverit.utilities;

import com.mattricks.deliverit.model.Product;

import java.util.List;

/**
 * Created by mahasagar on 20/1/17.
 */
public class CommonService {

    public CommonService() {
        super();
    }


    public boolean isEmpty(List<Product> list) {
        return list.size() <= 0;
    }

}
