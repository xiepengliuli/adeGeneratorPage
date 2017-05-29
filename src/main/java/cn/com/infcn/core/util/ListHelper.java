package cn.com.infcn.core.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

public class ListHelper {
    /**
     * 将一个集合转成另外一种结合
     * 
     * @param fromList
     * @param cls
     * @return
     */
    public static <T, O> List<O> Convert(List<T> fromList, Class<O> cls) {

        List<O> tempList = new ArrayList<O>();
        for (T t : fromList) {
            O temp;
            try {
                temp = cls.newInstance();
                BeanUtils.copyProperties(t, temp);
                tempList.add(temp);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        return tempList;
    }
}
