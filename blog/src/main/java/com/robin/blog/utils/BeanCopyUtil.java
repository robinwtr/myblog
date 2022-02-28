package com.robin.blog.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author robin
 * @version 1.0
 * @date 2021/12/14 14:36
 */
public class BeanCopyUtil {

    /**
     * 根据现有对象的属性创建新对象，并赋值
     * @param source
     * @param target
     * @param <T>
     * @return
     */
    public static <T> T copyObject(Object source, Class<T> target) {
        T tmp = null;
        try {
            tmp = target.newInstance();
            if (null != source) {
                org.springframework.beans.BeanUtils.copyProperties(source, tmp);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return tmp;
    }

    /**
     * 拷贝集合
     * @param source
     * @param target
     * @param <T>
     * @param <S>
     * @return
     */
    public static <T, S> List<T> copyList(List<S> source, Class<T> target) {
        List<T> list = new ArrayList<>();
        if (null != source && source.size() > 0) {
            for (Object obj : source) {
                list.add(BeanCopyUtil.copyObject(obj, target));
            }
        }
        return list;
    }
}
